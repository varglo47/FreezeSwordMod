package com.twojanazwa.freezesword.item;

import com.twojanazwa.freezesword.config.ConfigValues;
import com.twojanazwa.freezesword.ModToolMaterials;
import com.twojanazwa.freezesword.registry.ModSounds;
import com.twojanazwa.freezesword.utils.ConfigHelpers;
import com.twojanazwa.freezesword.utils.TextHelpers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

public class FreezeSwordItem extends Item {

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public FreezeSwordItem(Settings settings) {

        super(settings.sword(
                ModToolMaterials.ICE,
                ConfigValues.ATTACK_DAMAGE,
                ConfigHelpers.getAttackSpeed()
        ));
    }

    // =========================================================
    // FREEZE EFFECT
    // =========================================================

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // Only players can be frozen
        if (!(target instanceof ServerPlayerEntity playerTarget)) {
            return;
        }

        // Only players can use the freeze ability
        if (!(attacker instanceof ServerPlayerEntity playerAttacker)) {
            return;
        }

        // Prevents freeze spam while cooldown is active
        if (playerAttacker.getItemCooldownManager().isCoolingDown(stack)) {
            return;
        }

        applyFreezeEffect(playerTarget);

        // Starts cooldown after successful hit
        playerAttacker.getItemCooldownManager().set(
                stack,
                ConfigHelpers.getFreezeCooldownTicks()
        );
    }

    // =========================================================
    // FREEZE LOGIC
    // =========================================================

    private void applyFreezeEffect(ServerPlayerEntity target) {
        target.setFrozenTicks(ConfigHelpers.getFreezeTicks());

        ConfigValues.FROZEN_POSITIONS.put(
                target.getUuid(),
                new double[]{target.getX(), target.getY(), target.getZ()}
        );

        ConfigValues.FROZEN_PLAYERS.put(
                target.getUuid(),
                ConfigHelpers.getFreezeTicks()
        );

        target.getEntityWorld().playSound(
                null,
                target.getBlockPos(),
                ModSounds.FREEZE_HIT,
                SoundCategory.PLAYERS,
                ConfigValues.HIT_VOLUME,
                ConfigValues.HIT_PITCH
        );
    }

    // =========================================================
    // CUSTOM ITEM NAME
    // =========================================================

    @Override
    public Text getName(ItemStack stack) {

        // Change the text below to rename the weapon
        return TextHelpers.createAnimatedName(ConfigValues.CUSTOM_NAME);
    }

    // =========================================================
    // ENCHANTMENT GLINT
    // =========================================================

    @Override
    public boolean hasGlint(ItemStack stack) {

        // Makes the sword glow like an enchanted item
        return ConfigValues.HAS_GLINT;

    }
}