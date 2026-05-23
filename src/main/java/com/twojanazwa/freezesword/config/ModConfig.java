package com.twojanazwa.freezesword.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

    public static void load() {

        try {

            Files.createDirectories(Path.of("config"));

            Path path = Path.of("config/freezesword.toml");

            CommentedFileConfig config = CommentedFileConfig.builder(path)
                    .preserveInsertionOrder()
                    .sync()
                    .autosave()
                    .build();

            config.load();

            ConfigDefaults.load(config);

            ConfigComments.load(config);

            ConfigLoader.load(config);

            config.save();

            System.out.println("[FreezeSword] Config loaded!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}