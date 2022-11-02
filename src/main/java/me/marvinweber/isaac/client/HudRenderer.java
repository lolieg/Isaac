package me.marvinweber.isaac.client;

import me.marvinweber.isaac.stats.PlayerStats;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;


@Environment(EnvType.CLIENT)
public class HudRenderer {
    private final String[] baseStrings = new String[]{"Speed: ", "Damage: ", "Tears: ", "Range: ", "Shot Speed: ", "Luck: ", "Knockback: "};

    public void render(MatrixStack matrices) {
        PlayerStats playerStats = IsaacClient.playerStats;
        if (playerStats == null) return;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        float[] stats = new float[]{playerStats.speed.getCurrentRounded(), playerStats.damage.getCurrentRounded(), playerStats.tears.getTearsPerSecond(true), playerStats.range.getCurrentRounded(), playerStats.shotSpeed.getCurrentRounded(), playerStats.luck.getCurrentRounded(), playerStats.knockback.getCurrentRounded()};
        for (int i = 0; i < baseStrings.length; i++) {
            String name = baseStrings[i];
            name += stats[i];
            minecraftClient.inGameHud.getTextRenderer().draw(matrices, name, minecraftClient.getWindow().getScaledWidth() / 300f, minecraftClient.getWindow().getScaledWidth() / 5f + i * 10, 0xFFFFFF);
        }
    }
}
