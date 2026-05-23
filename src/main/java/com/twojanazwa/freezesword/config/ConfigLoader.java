package com.twojanazwa.freezesword.config;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.util.List;
import java.util.Map;

public class ConfigLoader {

    public static void load(CommentedFileConfig config) {

        ConfigValues.FREEZE_DURATION_SECONDS = config.getInt("freeze_duration");

        ConfigValues.FREEZE_COOLDOWN = config.getInt("freeze_cooldown");

        ConfigValues.ATTACK_DAMAGE = ((Number) config.get("attack_damage"
        )).floatValue();

        ConfigValues.ATTACK_SPEED = ((Number) config.get("attack_speed"
        )).floatValue();

        ConfigValues.PARTICLES_COUNT = config.getInt("particles_count");

        ConfigValues.PARTICLES_SPEED = ((Number) config.get("particles_speed"
        )).doubleValue();

        ConfigValues.PARTICLES_OFFSET_X = ((Number) config.get("particles_offset_x"
        )).doubleValue();

        ConfigValues.PARTICLES_OFFSET_Y = ((Number) config.get("particles_offset_y"
        )).doubleValue();

        ConfigValues.PARTICLES_OFFSET_Z = ((Number) config.get("particles_offset_z"
        )).doubleValue();

        ConfigValues.VOID_PROTECTION = config.get("void_protection");

        ConfigValues.HAS_GLINT = config.get("has_glint");

        ConfigValues.UNBREAKABLE = config.get("unbreakable");

        ConfigValues.FREEZE_EFFECT = config.get("freeze_effect");

        ConfigValues.HIT_VOLUME = ((Number) config.get("hit_volume"
        )).floatValue();

        ConfigValues.HIT_PITCH = ((Number) config.get("hit_pitch"
        )).floatValue();

        ConfigValues.LORE = config.get("lore");

        ConfigValues.VOID_MESSAGE = config.get("void_message");

        ConfigValues.VOID_MESSAGE_TEXT = config.get("void_message_text");

        ConfigValues.CUSTOM_NAME = config.get("custom_name");

        ConfigValues.ANIMATION_SPEED = config.get("animation_speed");

        ConfigValues.VOID_SOUND = config.get("void_sound");

        ConfigValues.FIREPROOF = config.get("fireproof");

        String particleId = config.get("particle");

        Identifier id = Identifier.of(particleId);

        if (!Registries.PARTICLE_TYPE.containsId(id)) {

            System.out.println(
                    "[FreezeSword] Invalid particle: "
                            + particleId
                            + " | Using snowflake instead."
            );
            ConfigValues.PARTICLE = ParticleTypes.SNOWFLAKE;
            return;
        }
        ConfigValues.PARTICLE =
                (net.minecraft.particle.ParticleEffect) Registries.PARTICLE_TYPE.get(id);


        List<?> rawList = config.get("allowed_enchants");

        if (rawList != null) {
            ConfigValues.ALLOWED_ENCHANTS = rawList.stream()
                    .map(Object::toString)
                    .toList();
        }

        List<?> rawLore = config.get("lore_lines");
        if (rawLore != null) {
            ConfigValues.LORE_LINES = rawLore.stream()
                    .filter(entry -> entry instanceof CommentedConfig)
                    .map(entry -> {
                        CommentedConfig cfg = (CommentedConfig) entry;
                        Map<String, String> line = new java.util.HashMap<>();
                        cfg.entrySet().forEach(e -> line.put(e.getKey(), e.getValue().toString()));
                        return line;
                    })
                    .toList();
        }

    }
}