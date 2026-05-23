package com.twojanazwa.freezesword.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import java.util.List;

public class ConfigDefaults {

    public static void load(CommentedFileConfig config) {

        setIfMissing(config, "freeze_duration", 5);
        setIfMissing(config, "freeze_cooldown", 100);

        setIfMissing(config, "attack_damage", 7.0F);
        setIfMissing(config, "attack_speed", 2.4F);

        setIfMissing(config, "particles_count", 5);
        setIfMissing(config, "particles_speed", 0.01);
        setIfMissing(config, "particles_offset_x", 0.3);
        setIfMissing(config, "particles_offset_y", 0.5);
        setIfMissing(config, "particles_offset_z", 0.3);
        setIfMissing( config, "particle", "minecraft:snowflake" );

        setIfMissing(config, "void_protection", true);
        setIfMissing(config, "void_message", true);
        setIfMissing(config, "void_message_text", "§6§lFreeze Sword§r: §cYou dropped me bro §abut don't worry, I am the god of teleportation.");
        setIfMissing(config, "void_sound", true);

        setIfMissing(config, "fireproof", true);
        setIfMissing(config, "freeze_effect", true);

        setIfMissing(config, "hit_volume", 1.0F);
        setIfMissing(config, "hit_pitch", 1.0F);

        setIfMissing(config, "lore", true);
        setIfMissing(config, "lore_lines", List.of(
                loreLine("type", "empty",    "text", ""),
                loreLine("type", "plain",    "text", "----------------------", "color", "WHITE"),
                loreLine("type", "empty",    "text", ""),
                loreLine("type", "gradient", "text", "⏳   ꜰʀᴇᴇᴢᴇꜱ ᴛʜᴇ ᴇɴᴇᴍʏ ꜰᴏʀ 5 ꜱᴇᴄᴏɴᴅꜱ",    "color_from", "GOLD",   "color_to", "WHITE"),
                loreLine("type", "gradient", "text", "♻  ɪᴛ'ꜱ ᴜɴʙʀᴇᴀᴋᴀʙʟᴇ",                       "color_from", "PURPLE", "color_to", "DARK_BLUE", "condition", "unbreakable"),
                loreLine("type", "gradient", "text", "❎   ɪᴛ'ꜱ ɴᴏᴛ ᴇɴᴄʜᴀɴᴛᴀʙʟᴇ",                 "color_from", "RED",    "color_to", "DARK_GRAY", "condition", "no_enchants"),
                loreLine("type", "empty",    "text", ""),
                loreLine("type", "plain",    "text", "----------------------", "color", "WHITE"),
                loreLine("type", "empty",    "text", "")
        ));
        setIfMissing(config, "custom_name", "Freeze Sword");
        setIfMissing(config, "animation_speed", 120);
        setIfMissing(config, "allowed_enchants", java.util.List.of(
        ));
        setIfMissing(config, "has_glint", true);
        setIfMissing(config, "unbreakable", true);
    }


    private static void setIfMissing(
            CommentedFileConfig config,
            String key,
            Object value
    ) {

        if (!config.contains(key)) {
            config.set(key, value);
        }
    }





    private static CommentedConfig loreLine(String... pairs) {
        CommentedConfig entry = CommentedConfig.inMemory();
        for (int i = 0; i < pairs.length; i += 2) {
            entry.set(pairs[i], pairs[i + 1]);
        }
        return entry;
    }
}