package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "dropSelectedItem", cancellable = true)
    private void dropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
            cir.cancel();
    }

    @Inject(at = @At("HEAD"), method = "dropItem", cancellable = true)
    private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
            cir.cancel();
    }

}
