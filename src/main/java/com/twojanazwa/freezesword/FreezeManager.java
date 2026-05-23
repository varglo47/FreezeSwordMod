package com.twojanazwa.freezesword;

import com.twojanazwa.freezesword.config.ConfigValues;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

public class FreezeManager {

    // =========================================================
    // INITIALIZATION
    // =========================================================

    public static void init() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerWorld world : server.getWorlds()) {

                world.iterateEntities().forEach(entity -> {

                    // Only players can be frozen
                    if (!(entity instanceof ServerPlayerEntity player)) {
                        return;
                    }

                    // Skip players that are not frozen
                    if (!ConfigValues.FROZEN_PLAYERS.containsKey(player.getUuid())) return;

                    if(ConfigValues.FREEZE_EFFECT){freezePlayerMovement(player, world);
                    }
                    if (player instanceof ServerPlayerEntity sp) {
                        tickFreeze(sp);
                    }
                    spawnFreezeParticles(player, world);
                });
            }
        });
    }

    private static void tickFreeze(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();

        if (!ConfigValues.FROZEN_PLAYERS.containsKey(uuid)) return;

        int ticks = ConfigValues.FROZEN_PLAYERS.get(uuid) - 1;

        if (ticks <= 0) {
            ConfigValues.FROZEN_PLAYERS.remove(uuid);
            ConfigValues.FROZEN_POSITIONS.remove(uuid); // <-- wyczyść pozycję
            player.setFrozenTicks(0);
            return;
        }

        ConfigValues.FROZEN_PLAYERS.put(uuid, ticks);
        player.setFrozenTicks(ticks);
    }

    // =========================================================
    // FREEZE MOVEMENT
    // =========================================================

    private static void freezePlayerMovement(ServerPlayerEntity player, ServerWorld world) {
        player.fallDistance = 0;

        UUID uuid = player.getUuid();

        if (!ConfigValues.FROZEN_POSITIONS.containsKey(uuid)) return;

        double[] pos = ConfigValues.FROZEN_POSITIONS.get(uuid);
        double dx = player.getX() - pos[0];
        double dy = player.getY() - pos[1];
        double dz = player.getZ() - pos[2];

        if (dx * dx + dy * dy + dz * dz > 0.0001) {
            player.requestTeleport(pos[0], pos[1], pos[2]);
            player.setVelocity(0, 0, 0);
            player.velocityDirty = true;
        }
    }

    // =========================================================
    // PARTICLES
    // =========================================================

    private static void spawnFreezeParticles(ServerPlayerEntity player, ServerWorld world) {

        world.spawnParticles(

                ConfigValues.PARTICLE,

                player.getX(),
                player.getBodyY(0.5),
                player.getZ(),

                ConfigValues.PARTICLES_COUNT,

                ConfigValues.PARTICLES_OFFSET_X,
                ConfigValues.PARTICLES_OFFSET_Y,
                ConfigValues.PARTICLES_OFFSET_Z,

                ConfigValues.PARTICLES_SPEED
        );
    }
}
