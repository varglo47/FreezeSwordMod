
        package com.twojanazwa.freezesword.registry;

import com.twojanazwa.freezesword.FreezeSword;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.sound.SoundEvent;

import net.minecraft.util.Identifier;

public class ModSounds {

    // =========================================================
    // FREEZE HIT SOUND
    // =========================================================

    public static final SoundEvent FREEZE_HIT = register(
    );

    // =========================================================
    // REGISTER HELPER
    // =========================================================

    private static SoundEvent register() {

        Identifier identifier = Identifier.of(
                FreezeSword.MOD_ID,
                "freeze_hit"
        );

        return Registry.register(
                Registries.SOUND_EVENT,
                identifier,
                SoundEvent.of(identifier)
        );
    }

    // =========================================================
    // INIT
    // =========================================================

    public static void init() {

        System.out.println(
                "[FreezeSword] Sounds loaded!"
        );
    }
}

