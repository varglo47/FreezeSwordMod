package com.twojanazwa.freezesword.mixin;

import com.twojanazwa.freezesword.FreezeSword;
import com.twojanazwa.freezesword.config.ConfigValues;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(
            method = "getPossibleEntries",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void freezeSword$filterEnchantments(
            int level,
            ItemStack stack,
            Stream<RegistryEntry<Enchantment>> possibleEnchantments,
            CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir
    ) {

        // Only affect Freeze Sword
        if (stack.getItem() != FreezeSword.FREEZE_SWORD) {
            return;
        }

        List<EnchantmentLevelEntry> list = cir.getReturnValue();

        list.removeIf(entry -> {

            Identifier id = entry.enchantment()
                    .getKey()
                    .get()
                    .getValue();

            return !ConfigValues.ALLOWED_ENCHANTS.contains(id.toString());
        });

        cir.setReturnValue(list);
    }
}
