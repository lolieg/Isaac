package me.marvinweber.isaac.mixin;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin {
    @Shadow public @Nullable abstract Team getPlayerTeam(String playerName);

    // NO IDEA WHY I HAVE TO DO THIS BUT THIS BRINGS UP ERRORS IF I DON'T.
    @Inject(at = @At("HEAD"), method = "removePlayerFromTeam", cancellable = true)
    private void removePlayerFromTeam(String playerName, Team team, CallbackInfo ci) {
        if (this.getPlayerTeam(playerName) != team) ci.cancel();
    }
}
