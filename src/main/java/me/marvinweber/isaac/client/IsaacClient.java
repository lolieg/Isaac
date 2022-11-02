package me.marvinweber.isaac.client;

import me.marvinweber.isaac.registry.client.BlockRegistry;
import me.marvinweber.isaac.registry.client.EntityRegistry;
import me.marvinweber.isaac.registry.client.PacketRegistry;
import me.marvinweber.isaac.registry.client.ParticleRegistry;
import me.marvinweber.isaac.stats.HealthManager;
import me.marvinweber.isaac.stats.PlayerStats;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class IsaacClient implements ClientModInitializer {
    public static PlayerStats playerStats;
    public static HudRenderer hudRenderer;
    public static HealthManager healthManager;

    @Override
    public void onInitializeClient() {
        hudRenderer = new HudRenderer();
        healthManager = new HealthManager();
        EntityRegistry.initialize();
        PacketRegistry.initialize();
        ParticleRegistry.initialize();
        BlockRegistry.initialize();
    }

}
