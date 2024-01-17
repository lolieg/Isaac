package me.marvinweber.isaac.registry.common;

import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import me.marvinweber.isaac.Isaac;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ParticleRegistry implements AutoRegistryContainer<ParticleType<?>> {
    public static final DefaultParticleType TEAR_COLLISION = FabricParticleTypes.simple();

    @Override
    public Registry<ParticleType<?>> getRegistry() {
        return Registries.PARTICLE_TYPE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ParticleType<?>> getTargetFieldType() {
        return (Class<ParticleType<?>>) (Object) ParticleType.class;
    }
}
