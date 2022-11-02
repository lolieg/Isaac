package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private float movementFovMultiplier;

    @Shadow
    private float lastMovementFovMultiplier;

    @Inject(at = @At("TAIL"), method = "updateMovementFovMultiplier")
    private void updateMovementFovMultiplier(CallbackInfo ci) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING) {
            this.movementFovMultiplier = 1.0f;
            this.lastMovementFovMultiplier = 1.0f;
        }
    }

/*    @ModifyVariable(method = "getFov", at = @At(value = "HEAD"), ordinal = 0)
    private boolean getFov(boolean b){
        return false;
    }*/
}
