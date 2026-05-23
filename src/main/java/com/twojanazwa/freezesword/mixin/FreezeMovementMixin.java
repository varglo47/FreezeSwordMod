package com.twojanazwa.freezesword.mixin;

import com.twojanazwa.freezesword.config.ConfigValues;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class FreezeMovementMixin {

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void freezeSword$blockMovement(CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity)(Object)this;

        if (!ConfigValues.FREEZE_EFFECT) return;

        if (ConfigValues.FROZEN_PLAYERS.containsKey(player.getUuid())) {
            ci.cancel();
        }
    }
}