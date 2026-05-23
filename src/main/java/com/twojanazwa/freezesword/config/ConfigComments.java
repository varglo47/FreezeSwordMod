package com.twojanazwa.freezesword.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

public class ConfigComments {

    public static void load(CommentedFileConfig config) {

        config.setComment(
                "freeze_duration",

                """
                 Game / server restart required (the /freezesword reload won't instantly change this)
                 How long the freeze effect lasts. (in seconds)
                   Default value: 5"""
        );

        config.setComment(
                "freeze_cooldown",

                """
                
                Game / server restart required (the /freezesword reload won't instantly change this)
                Cooldown after using freeze ability. (in seconds)
                   Default value: 100"""
        );

        config.setComment(
                "attack_damage",

                """
                
                
                Game / server restart required (the /freezesword reload won't instantly change this)
                Sword Damage - It works based on heart damage system.
                For example, 3.0 means that it's standard damage is 1.5 hearts. (works exactly like normal minecraft weapon stats)
                   Default value: 7.0""");

        config.setComment(
                "attack_speed",

                """
                
                Game / server restart required (the /freezesword reload won't instantly change this)
                Sword hit speed
                The lower the number is the faster the speed is.
                   Default value: 2.4
                   Range:         0  -  4.0""");

        config.setComment(
                "particle",
                """
               
                This config changes the particle type when it's freeze ability is used on someone.
                Full vanilla particles list: https://minecraft.wiki/w/Particles
                It needs to be the Particle ID, AND MAKE SURE YOU ARE TYPING THE JAVA ID, NOT THE BEDROCK ONE!
                   Examples:
                   minecraft:firefly
                   minecraft:heart
                   minecraft:cherry_leaves
                   Default value: minecraft:snowflake""");

        config.setComment(
                "particles_count",
                """
               
               
                Particles count
                   Default value: 5""");

        config.setComment(
                "particles_speed",
                """
               
                Particles speed
                   Default value: 0.01""");

        config.setComment(
                "particles_offset_x",
                """
               
                Particles offset
                  Default value X / Z: 0.3
                   Default value Y: 0.5""");

        config.setComment(
                "particles_offset_y",
                """
                        """);

        config.setComment(
                "particles_offset_z",
                """
                        """);

        config.setComment(
                "void_protection",
                """
               
               
               Game / server restart required (the /freezesword reload won't instantly change this)
               Void protection. false means that it won't be protected from getting destroyed in the void.
                  Default value: true""");

        config.setComment(
                "freeze_effect",
                """
               
               If it's set to true the sword will have its ability to freeze players
                  Default value: true""");

        config.setComment(
                "hit_volume",
                """
               
               
                Freeze sword hit volume. (mainly made so you can mute the sound because increasing its volume doesn't do much).
                   Default value: 1.0""");

        config.setComment(
                "hit_pitch",
                """
               
                Freeze sword hit pitch.
                The lower the value is the lower the pitch is.
                   Default value: 1.0""");

        config.setComment(
                "void_message",
                """
               
                If it's true, players will get a chat message when their sword gets saved from disappearing in the void.
                   Default value: true""");

        config.setComment(
                "void_message_text",
                """
                
                Here you can customize the message that players get when their sword gets saved from disappearing in the void.
                these things: "§4" are something like a style changer. When you type "§4 Apple", the Apple becomes a dark red text.
                example: "§4 Apple §9 Apple" will type a dark red apple text, and then a blue apple text.
                here is a simple wiki about this if you still don't understand: https://minecraft.fandom.com/wiki/Formatting_codes
                   Default value: "§6§lFreeze Sword§r: §cYou dropped me bro §abut don't worry, I am the god of teleportation."\"""");

        config.setComment(
                "void_sound",
                """
               
                If it's true, a sound will play (XP ORB) when the freeze sword gets saved from disappearing in the void.
                   Default value: true""");

        config.setComment(
                "custom_name",
                """
                
                You can type your custom name here.
                It needs to be typed in quotation marks.
                   Default value: "Freeze Sword"\s""");

        config.setComment(
                "animation_speed",
                """
               
                The speed of the gradient-name animation.
                The higher the number the slower the animation is.
                   Default value: 120""");

        config.setComment(
                "fireproof",
                """
               
               
                Game / server restart required (the /freezesword reload won't instantly change this)
                If it's true, the sword won't disappear in lava / fire.
                   Default value: true""");

        config.setComment(
                "allowed_enchants",
                """
               
               
                Here you can config what enchantments players can apply for the freeze sword.
                It's easy to set up -> just put your enchant in the square brackets, and if you want more than one enchant, put another one after a comma
                   Examples:
                   ["minecraft:sharpness", "minecraft:unbreaking", "minecraft:mending"]
                   ["minecraft:smite", "minecraft:efficiency", "minecraft:unbreaking"]
                If you want to have no enchants, remove the quotation marks too.
                   Default value: No enchants""");

        config.setComment(
                "has_glint",
               """
               
               If it's set to true the sword will have the enchantment glint even without enchants.
                  Default value: true""");

        config.setComment(
                "unbreakable",
                """
                
                Game / server restart required (the /freezesword reload won't instantly change this)
                If it's set to true the sword will never break.
                if you want to use the unbreaking enchantment on this sword you need to set it to false of course.
                   Default value: true""");


        config.setComment(
                "lore",
                """

                Game / server restart required (the /freezesword reload won't instantly change this)
                If it's true, the sword description will be on.
                   Default value: true""");

        config.setComment(
                "lore_lines",
                """
                
                  Game / server restart required (the /freezesword reload won't instantly change this)
                  Here, you can customize the lore.
                  "Why would you use that instead of a custom lore mod?", Well, because of the additional functions it has.
                  You can just use one of the example systems. I'll write it down and explain it:
                      First mode: PLAIN - It's just a standard description line.
                        it requires one color, the text, and you need to type the line type = plain.
                        for example:
                                                [[lore_lines]]
                        	color = "WHITE"
                        	text = "----------------------"
                        	type = "plain"
                      Second mode: GRADIENT
                         It requires two colors:
                         The "color_from" is the color on the left side of the text, while the "color_to" is the right side of the text. In this example, the text will be a gradient RED -> DARK GRAY
                         It also requires a text of course.
                         And you also need to type line type = gradient.
                         for example:
                        [[lore_lines]]
                        	condition = "no_enchants"
                        	color_to = "DARK_GRAY"
                        	text = "❎   ɪᴛ'ꜱ ɴᴏᴛ ᴇɴᴄʜᴀɴᴛᴀʙʟᴇ"
                        	type = "gradient"
                        	color_from = "RED"
                        Third, last mode: EMPTY
                        It's only the type and the text. I made it to make empty lines, because it's a bit easier for the pc to process this code instead of a whole gradient/plain code with an empty line.
                        the only way to place the empty line: (putting a text won't change anything, the line still will be empty)
                        [[lore_lines]]
                        	text = ""
                        	type = "empty"
                  As you can see, there is also something named "condition." It's mostly useful in the default settings because there is (for now) only 2 conditions available in my mod.
                  First condition: no_enchants (which means that the line will appear only if you have zero enchants available for the sword)
                  Second condition: unbreakable (which means that the line will only appear if the sword in your config has set unbreakable = true)
                  Maybe I'll add more conditions in the future.
                  Remember: Every value here needs to be typed in quotation marks.
                  """);

    }
}

