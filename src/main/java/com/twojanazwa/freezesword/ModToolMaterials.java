package com.twojanazwa.freezesword;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModToolMaterials {

    public static final TagKey<Item> ICE_REPAIR = TagKey.of(
            RegistryKeys.ITEM,
            Identifier.of("freezesword", "ice_repair")
    );

    public static final ToolMaterial ICE = new ToolMaterial(

            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,

            2031,

            9.0F,

            -1.0F,

            22,

            ICE_REPAIR
    );

}