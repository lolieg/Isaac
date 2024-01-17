package me.marvinweber.isaac.registry.common;


import io.wispforest.owo.registration.reflect.AutoRegistryContainer;
import me.marvinweber.isaac.Isaac;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;


public class SoundRegistry implements AutoRegistryContainer<SoundEvent> {
    public static final Identifier STOMP = new Identifier(Isaac.MOD_ID, "stomp");
    public static SoundEvent ISAAC_ENTITY_STOMP = SoundEvent.of(STOMP);

    @Override
    public Registry<SoundEvent> getRegistry() {
        return Registries.SOUND_EVENT;
    }

    @Override
    public Class<SoundEvent> getTargetFieldType() {
        return SoundEvent.class;
    }
}
