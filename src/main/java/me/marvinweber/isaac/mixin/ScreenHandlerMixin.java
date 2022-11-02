package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(at = @At("HEAD"), method = "internalOnSlotClick", cancellable = true)
    private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
            ci.cancel();
    }
}
