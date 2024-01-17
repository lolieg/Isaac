package me.marvinweber.isaac.registry.client;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.particles.TearCollisionParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class ParticleRegistry {
    public static void initialize() {
        ParticleFactoryRegistry.getInstance().register(me.marvinweber.isaac.registry.common.ParticleRegistry.TEAR_COLLISION, TearCollisionParticle.Factory::new);
    }
}
