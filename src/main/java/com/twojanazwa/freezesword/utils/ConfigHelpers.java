package com.twojanazwa.freezesword.utils;

import net.minecraft.util.math.MathHelper;

import static com.twojanazwa.freezesword.config.ConfigValues.*;

public class ConfigHelpers {


    public static int getFreezeCooldownTicks() {

        return MathHelper.clamp(
                FREEZE_COOLDOWN * 20,
                0,
                20 * 60 * 60
        );
    }

    public static int getFreezeTicks() {
        return FREEZE_DURATION_SECONDS * 20;
    }


    public static float getAttackSpeed() {
        return ATTACK_SPEED * -1;
    }

}
