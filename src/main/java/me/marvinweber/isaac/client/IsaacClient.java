package me.marvinweber.isaac.client;

import me.marvinweber.isaac.client.map.Map;
import me.marvinweber.isaac.registry.client.EntityRegistry;
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
    public static StateManager stateManager;

    public static Map map;

    @Override
    public void onInitializeClient() {
        hudRenderer = new HudRenderer();
        healthManager = new HealthManager();
        stateManager = new StateManager();
        map = new Map();
        EntityRegistry.initialize();
        ParticleRegistry.initialize();


    }

}
