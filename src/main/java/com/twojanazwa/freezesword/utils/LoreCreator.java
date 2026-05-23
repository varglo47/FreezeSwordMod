package com.twojanazwa.freezesword.utils;

import com.twojanazwa.freezesword.config.ConfigValues;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

import static com.twojanazwa.freezesword.utils.TextHelpers.*;

public class LoreCreator {

    public static List<Text> createLore() {

        if (!ConfigValues.LORE) return List.of();

        List<Text> lore = new java.util.ArrayList<>();

        for (Map<String, String> line : ConfigValues.LORE_LINES) {

            // check condition
            String condition = line.getOrDefault("condition", "");
            if (condition.equals("unbreakable") && !ConfigValues.UNBREAKABLE) continue;
            if (condition.equals("no_enchants") && !ConfigValues.ALLOWED_ENCHANTS.isEmpty()) continue;

            String type = line.getOrDefault("type", "plain");
            String text = line.getOrDefault("text", "");

            switch (type) {

                case "empty" -> lore.add(Text.literal(" "));

                case "plain" -> {
                    String colorName = line.getOrDefault("color", "WHITE");
                    Formatting color = formattingFromString(colorName);
                    lore.add(Text.literal(text).setStyle(
                            Style.EMPTY.withColor(color).withItalic(false)
                    ));
                }

                case "gradient" -> {
                    String from = line.getOrDefault("color_from", "WHITE");
                    String to   = line.getOrDefault("color_to",   "WHITE");
                    lore.add(gradientText(text,
                            colorFromString(from),
                            colorFromString(to)
                    ));
                }
            }
        }

        return lore;
    }
}
