package com.twojanazwa.freezesword.mixin;

import com.twojanazwa.freezesword.FreezeSword;
import com.twojanazwa.freezesword.config.ConfigValues;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void freezeSword$filterAnvilEnchantments(CallbackInfo ci) {

        AnvilScreenHandler handler = (AnvilScreenHandler)(Object)this;
        ItemStack result = handler.getSlot(2).getStack();

        if (result.isEmpty()) return;
        if (result.getItem() != FreezeSword.FREEZE_SWORD) return;

        ItemStack left  = handler.getSlot(0).getStack();
        ItemStack right = handler.getSlot(1).getStack();

        // Collect the enchants carried by your right hand (book or second sword)
        ItemEnchantmentsComponent rightEnchants = right.getOrDefault(
                DataComponentTypes.STORED_ENCHANTMENTS,   // for enchanted book
                right.getOrDefault(
                        DataComponentTypes.ENCHANTMENTS,  // for enchanted item
                        ItemEnchantmentsComponent.DEFAULT
                )
        );

        // Check if ANY enchant from the right slot is not allowed
        boolean hasIllegalEnchant = rightEnchants.getEnchantments().stream().anyMatch(entry -> {
            Identifier id = entry.getKey().get().getValue();
            return !ConfigValues.ALLOWED_ENCHANTS.contains(id.toString());
        });

        if (hasIllegalEnchant) {
            // Clear the result slot - the anvil will show an empty hand and will not collect XP
            handler.getSlot(2).setStack(ItemStack.EMPTY);
            return;
        }

        // No illegal enchants - can optionally filter further here
        // (below is the original builder if you want to keep it as a second line of defense)
        ItemEnchantmentsComponent enchants = result.getOrDefault(
                DataComponentTypes.ENCHANTMENTS,
                ItemEnchantmentsComponent.DEFAULT
        );

        ItemEnchantmentsComponent.Builder builder =
                new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

        enchants.getEnchantments().forEach(entry -> {
            Identifier id = entry.getKey().get().getValue();
            if (ConfigValues.ALLOWED_ENCHANTS.contains(id.toString())) {
                builder.add(entry, enchants.getLevel(entry));
            }
        });

        result.set(DataComponentTypes.ENCHANTMENTS, builder.build());
    }
}