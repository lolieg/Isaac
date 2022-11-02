package me.marvinweber.isaac.registry.common;

import me.marvinweber.isaac.Isaac;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleRegistry {
    public static final DefaultParticleType TEAR_COLLISION = FabricParticleTypes.simple();


    public static void initialize() {
        Registry.register(net.minecraft.util.registry.Registry.PARTICLE_TYPE, new Identifier(Isaac.MOD_ID, "tear_collision"), TEAR_COLLISION);
    }
}
