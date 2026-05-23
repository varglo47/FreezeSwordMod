package com.twojanazwa.freezesword.utils;

import com.twojanazwa.freezesword.config.ConfigValues;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

// =========================================================
// LORE HELPERS
// =========================================================

public class TextHelpers {
    public static Text separator() {
        return Text.literal("----------------------")
                .setStyle(
                        Style.EMPTY
                                .withColor(Formatting.WHITE)
                                .withItalic(false)
                );
    }

    public static Text emptyLine() {
        return Text.literal(" ");
    }


    // =========================================================
    // COLOR INTERPOLATION
    // =========================================================

      /**
     * Smoothly blends between 2 colors.
     * t = 0.0 -> first color
     * t = 1.0 -> second color
     */
    public static int lerp(int c1, int c2, float t) {

        int r1 = (c1 >> 16) & 0xFF;
        int g1 = (c1 >> 8) & 0xFF;
        int b1 = c1 & 0xFF;

        int r2 = (c2 >> 16) & 0xFF;
        int g2 = (c2 >> 8) & 0xFF;
        int b2 = c2 & 0xFF;

        int r = (int) (r1 + (r2 - r1) * t);
        int g = (int) (g1 + (g2 - g1) * t);
        int b = (int) (b1 + (b2 - b1) * t);

        return (r << 16) | (g << 8) | b;
    }

    // =========================================================
    // COLORS (used in gradients & lore)
    // =========================================================

    // Basic
    public static final int WHITE      = 0xFFFFFF;
    public static final int BLACK      = 0x000000;
    public static final int GRAY       = 0x888888;
    public static final int DARK_GRAY  = 0x444444;

    // Warm
    public static final int RED        = 0xFF4444;
    public static final int DARK_RED   = 0x8B0000;
    public static final int ORANGE     = 0xFF8C00;
    public static final int GOLD       = 0xFFD700;
    public static final int YELLOW     = 0xFFFF55;

    // Cold
    public static final int BLUE       = 0x5555FF;
    public static final int DARK_BLUE  = 0x1E3A8A;
    public static final int AQUA       = 0x55FFFF;
    public static final int CYAN       = 0x00BCD4;
    public static final int NAVY       = 0x001F5B;

    // Purple
    public static final int PURPLE     = 0xD8B4FF;
    public static final int DARK_PURPLE= 0x6A0DAD;
    public static final int MAGENTA    = 0xFF55FF;
    public static final int PINK       = 0xFF85C2;

    // Green
    public static final int GREEN      = 0x55FF55;
    public static final int DARK_GREEN = 0x1A7A1A;
    public static final int LIME       = 0xAAFF00;
    public static final int MINT       = 0x00E5A0;

    // Icy
    public static final int FROST      = 0xB0E0E6;
    public static final int GLACIER    = 0xCCEEFF;
    public static final int DEEP_ICE   = 0x4A90D9;


    // Animated name colors
    public static final int ICE_BLUE = 0x00BFFF;
    public static final int LIGHT_ICE = 0xE0FFFF;

    // =========================================================
    // GRADIENT TEXT
    // =========================================================

    public static Text gradientText(String text, int startColor, int endColor) {

        Text result = Text.literal("");

        int length = text.length();

        for (int i = 0; i < length; i++) {

            float t = (float) i / (length - 1);

            int color = lerp(startColor, endColor, t);

            result.getSiblings().add(
                    Text.literal(String.valueOf(text.charAt(i)))
                            .styled(style -> style
                                    .withColor(color)
                                    .withItalic(false)
                            )
            );
        }

        return result;
    }

    // =========================================================
    // ANIMATED NAME CREATOR
    // =========================================================

    public static Text createAnimatedName(String text) {

        long time = System.currentTimeMillis() / ConfigValues.ANIMATION_SPEED;

        Text result = Text.literal("");

        int length = text.length();

        for (int i = 0; i < length; i++) {

            float t = ((i + time) % length) / (float) length;

            int color = (t < 0.5f)
                    ? lerp(ICE_BLUE, LIGHT_ICE, t * 2)
                    : lerp(LIGHT_ICE, WHITE, (t - 0.5f) * 2);

            result.getSiblings().add(
                    Text.literal(String.valueOf(text.charAt(i)))
                            .styled(style -> style
                                    .withColor(color)
                                    .withBold(true)
                                    .withItalic(false)
                            )
            );
        }

        return result;
    }
    public static Formatting formattingFromString(String name) {
        try {
            return Formatting.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Formatting.WHITE;
        }
    }

    public static int colorFromString(String name) {
        return switch (name.toUpperCase()) {
            case "GOLD"      -> GOLD;
            case "WHITE"     -> WHITE;
            case "RED"       -> RED;
            case "AQUA"       -> AQUA;
            case "DARK_GRAY" -> DARK_GRAY;
            case "PURPLE"    -> PURPLE;
            case "DARK_BLUE" -> DARK_BLUE;
            case "GREEN"     -> GREEN;
            case "YELLOW"    -> YELLOW;
            case "BLUE"      -> BLUE;
            case "GRAY"      -> GRAY;
            case "BLACK"     -> BLACK;
            default          -> WHITE;
        };
    }

}