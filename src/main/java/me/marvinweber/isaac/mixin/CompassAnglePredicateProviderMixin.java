package me.marvinweber.isaac.mixin;

import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.GlobalPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompassAnglePredicateProvider.class)
public class CompassAnglePredicateProviderMixin {
    @Inject(at = @At("HEAD"), method = "<init>")
    private static void init(CompassAnglePredicateProvider.CompassTarget compassTarget, CallbackInfo ci) {

    }
}
