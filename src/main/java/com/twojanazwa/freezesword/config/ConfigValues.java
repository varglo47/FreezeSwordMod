package com.twojanazwa.freezesword.config;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigValues
{

    public static int FREEZE_DURATION_SECONDS = 5;
    public static int FREEZE_COOLDOWN = 100;
    public static float ATTACK_DAMAGE = 7.0F;
    public static float ATTACK_SPEED = 2.4F;
    public static int PARTICLES_COUNT = 5;
    public static double PARTICLES_SPEED = 0.01;
    public static double PARTICLES_OFFSET_X = 0.3;
    public static double PARTICLES_OFFSET_Y = 0.5;
    public static double PARTICLES_OFFSET_Z = 0.3;
    public static boolean VOID_PROTECTION = true;
    public static boolean HAS_GLINT = true;
    public static boolean FREEZE_EFFECT = true;
    public static float HIT_VOLUME = 1.0f;
    public static float HIT_PITCH = 1.0f;
    public static boolean LORE = true;
    public static boolean VOID_MESSAGE = true;
    public static String VOID_MESSAGE_TEXT = "§6§lFreeze Sword§r: §cYou dropped me bro §abut don't worry, I am the god of teleportation.";
    public static boolean VOID_SOUND = true;
    public static String CUSTOM_NAME = "Freeze Sword";
    public static int ANIMATION_SPEED = 120;
    public static boolean FIREPROOF = true;
    public static ParticleEffect PARTICLE = ParticleTypes.SNOWFLAKE;
    public static List<String> ALLOWED_ENCHANTS = new java.util.ArrayList<>();
    public static boolean UNBREAKABLE = true;

    public static final Map<UUID, Integer> FROZEN_PLAYERS = new HashMap<>();
    public static final Map<UUID, double[]> FROZEN_POSITIONS = new HashMap<>();

    public static List<Map<String, String>> LORE_LINES = new java.util.ArrayList<>();

}
