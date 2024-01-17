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
    private float fovMultiplier;

    @Shadow
    private float lastFovMultiplier;

    @Inject(at = @At("TAIL"), method = "updateFovMultiplier")
    private void updateFovMultiplier(CallbackInfo ci) {
        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING) {
            this.fovMultiplier = 1.0f;
            this.lastFovMultiplier = 1.0f;
        }
    }

}
