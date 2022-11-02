package me.marvinweber.isaac.mixin;

import me.marvinweber.isaac.Isaac;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("HEAD"), method = "shutdown")
    private void shutdown(CallbackInfo ci) {
        Isaac.game.stop();
    }
}
