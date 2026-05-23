package com.twojanazwa.freezesword;

import com.twojanazwa.freezesword.config.ConfigValues;
import com.twojanazwa.freezesword.item.FreezeSwordItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import java.util.*;

public class VoidProtectionHandler {

    private static final Set<UUID> TO_REMOVE = new HashSet<>();

    // =========================================================
    // CONFIG (.TOML THING IS USELESS HERE)
    // =========================================================
    private static final Config CONFIG = new Config();

    private static class Config {
        double overworldVoidThreshold = -64;
        double netherVoidThreshold = -64;
        double endVoidThreshold = 0;

        int protectionCooldownSeconds = 3;
    }

    // =========================================================
    // DATA MODEL
    // =========================================================

    private static final Map<UUID, UUID> OWNER = new HashMap<>();
    private static final Map<UUID, Long> COOLDOWN = new HashMap<>();

    // ONLY active Freeze Swords
    private static final Set<TrackedItem> TRACKED = new HashSet<>();

    private enum WorldKey {
        OVERWORLD, NETHER, END
    }

    private record TrackedItem(
            UUID id,
            WorldKey world,
            ItemEntity entity
    ) {}

    // =========================================================
    // INIT
    // =========================================================
    public static void init() {

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (!(entity instanceof ItemEntity item)) return;
            if (!(item.getStack().getItem() instanceof FreezeSwordItem)) return;

            UUID id = item.getUuid();

            TRACKED.add(new TrackedItem(id, getWorldKey(world), item));

            if (item.getOwner() != null) {
                OWNER.put(id, item.getOwner().getUuid());
            }
        });

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof ItemEntity item) {
                TO_REMOVE.add(item.getUuid());
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(VoidProtectionHandler::tick);

    }

    // =========================================================
    // MAIN LOOP
    // =========================================================
    private static void tick(MinecraftServer server) {

        if (TRACKED.isEmpty()) return;

        Set<UUID> toRemoveNow = new HashSet<>();

        for (TrackedItem t : TRACKED) {

            ItemEntity item = t.entity();

            if (item == null || item.isRemoved()) {
                toRemoveNow.add(t.id());
                continue;
            }

            if (isOnCooldown(t.id())) continue;
            if (!isVoid(item)) continue;

            setCooldown(t.id());

            UUID owner = OWNER.get(t.id());
            ItemStack stack = item.getStack();

            if (owner != null) {
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(owner);

                if (player != null) {
                    give(player, stack);
                    msg(player);
                    sound(player);
                } else {
                    dropEnd(server, stack);
                }
            }

            item.discard();
            toRemoveNow.add(t.id());

            OWNER.remove(t.id());
            COOLDOWN.remove(t.id());
        }

        // CLEANUP
        if (!toRemoveNow.isEmpty()) {
            TRACKED.removeIf(t -> toRemoveNow.contains(t.id()));
            OWNER.keySet().removeAll(toRemoveNow);
            COOLDOWN.keySet().removeAll(toRemoveNow);
        }

        // ENTITY_UNLOAD cleanup
        if (!TO_REMOVE.isEmpty()) {
            TRACKED.removeIf(t -> TO_REMOVE.contains(t.id()));
            OWNER.keySet().removeAll(TO_REMOVE);
            COOLDOWN.keySet().removeAll(TO_REMOVE);
            TO_REMOVE.clear();
        }
    }

    // =========================================================
    // VOID CHECK
    // =========================================================
    private static boolean isVoid(ItemEntity item) {
        return item.getY() < threshold(getWorldKey(item.getEntityWorld()));
    }

    private static double threshold(WorldKey key) {
        return switch (key) {
            case END -> CONFIG.endVoidThreshold;
            case NETHER -> CONFIG.netherVoidThreshold;
            default -> CONFIG.overworldVoidThreshold;
        };
    }

    private static WorldKey getWorldKey(World world) {
        if (world.getRegistryKey() == World.END) return WorldKey.END;
        if (world.getRegistryKey() == World.NETHER) return WorldKey.NETHER;
        return WorldKey.OVERWORLD;
    }

    // =========================================================
    // COOLDOWN
    // =========================================================
    private static boolean isOnCooldown(UUID id) {
        Long last = COOLDOWN.get(id);
        if (last == null) return false;

        return System.currentTimeMillis() - last < CONFIG.protectionCooldownSeconds * 1000L;
    }

    private static void setCooldown(UUID id) {
        COOLDOWN.put(id, System.currentTimeMillis());
    }

    // =========================================================
    // ACTIONS
    // =========================================================
    private static void give(ServerPlayerEntity player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack.copy())) {
            player.dropItem(stack.copy(), false);
        } else {
            player.currentScreenHandler.sendContentUpdates();
        }
    }

    private static void msg(ServerPlayerEntity player) {
        if (ConfigValues.VOID_MESSAGE) {
            player.sendMessage(Text.literal(ConfigValues.VOID_MESSAGE_TEXT), false);
        }
    }

    private static void sound(ServerPlayerEntity player) {
        if (!ConfigValues.VOID_SOUND) return;

        ServerWorld world = player.getEntityWorld();

        world.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                net.minecraft.sound.SoundCategory.PLAYERS,
                0.5f,
                1.0f
        );



    }

    private static void dropEnd(MinecraftServer server, ItemStack stack) {
        ServerWorld end = server.getWorld(World.END);
        if (end == null) return;

        end.spawnEntity(new ItemEntity(end, 0, 65, 0, stack.copy()));
    }
}