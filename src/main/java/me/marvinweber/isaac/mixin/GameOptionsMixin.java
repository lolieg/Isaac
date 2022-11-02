package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Shadow
    private Perspective perspective;

    @Inject(at = @At("TAIL"), method = "setPerspective")
    private void setPerspective(Perspective perspective, CallbackInfo ci) {
//        if (Isaac.game != null && Isaac.game.gameState == Game.State.RUNNING)
//            this.perspective = Perspective.FIRST_PERSON;
    }
}
