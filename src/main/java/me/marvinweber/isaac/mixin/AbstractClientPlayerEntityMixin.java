package me.marvinweber.isaac.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "getSkinTexture", cancellable = true)
    private void getSkinTexture(CallbackInfoReturnable<Identifier> cir) {
        cir.setReturnValue(new Identifier("isaac:textures/entity/isaac.png"));
    }
}
