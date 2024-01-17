package me.marvinweber.isaac.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.client.IsaacClient;
import me.marvinweber.isaac.stats.HealthManager;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow private int scaledWidth;

    @Shadow protected abstract void renderOverlay(MatrixStack matrices, Identifier texture, float opacity);

    @Shadow @Final private static Identifier POWDER_SNOW_OUTLINE;

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        IsaacClient.hudRenderer.render(matrices);
        IsaacClient.map.render(matrices);
        if(IsaacClient.healthManager != null && IsaacClient.healthManager.getHealthAmount() <= 1 && IsaacClient.stateManager.gameState != Game.State.STOPPED) {
            this.renderOverlay(matrices, POWDER_SNOW_OUTLINE, 1f);
//            RenderSystem.setShaderColor(1f, 0.9f, 0.9f, 1f);
//            System.out.println("here");
        } else {
//            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            System.out.println("here");
        }

    }

    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    private void renderHealthBar(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        ci.cancel();

        if (IsaacClient.healthManager == null || IsaacClient.stateManager.gameState == Game.State.STOPPED) return;
        lines = IsaacClient.healthManager.hearts.size() > 6 ? 10 : 11;

        int j = IsaacClient.healthManager.getHeartsAmount();
        for (int m = j - 1; m >= 0; --m) {
            int n = m / 6;
            int o = m % 6;
            int p = o * 8 + this.scaledWidth / 2 - (j * 8 / 2);
            int q = y + n * lines - (lines <= 10 ? 10 : 0);

            // Container
            this.drawHeart(matrices, 16, p, q, false);
            if (IsaacClient.healthManager.hearts.size() < 1) return;
            HealthManager.Heart heart = IsaacClient.healthManager.hearts.get(m);
            if (heart instanceof HealthManager.RedHeartContainer && heart.heartSate != HealthManager.HeartSate.EMPTY) {
                // Red Heart
                this.drawHeart(matrices, 16 + 4 * 9, p, q, heart.heartSate == HealthManager.HeartSate.HALF);
            }
            if (heart instanceof HealthManager.SoulHeart && heart.heartSate != HealthManager.HeartSate.EMPTY) {
                // Soul Heart
                this.drawHeart(matrices, 16 + 6 * 9, p, q, heart.heartSate == HealthManager.HeartSate.HALF);
            }
        }
    }
    private void drawHeart(MatrixStack matrices, int type, int x, int y, boolean halfHeart) {
        this.drawTexture(matrices, x, y, type + (halfHeart ? 9 : 0), 0, 9, 9);
    }
}
