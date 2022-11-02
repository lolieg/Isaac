package me.marvinweber.isaac.registry.common;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.bomb.BombEntity;
import me.marvinweber.isaac.entities.TearEntity;
import me.marvinweber.isaac.entities.monsters.dip.DipEntity;
import me.marvinweber.isaac.mapgen.rooms.layouts.Entities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class EntityRegistry {
    public static final EntityType<TearEntity> TEAR_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Isaac.MOD_ID, "tear"),
            FabricEntityTypeBuilder.<TearEntity>create(SpawnGroup.MISC, TearEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BombEntity> BOMB_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Isaac.MOD_ID, "bomb"),
            FabricEntityTypeBuilder.<BombEntity>create(SpawnGroup.MISC, BombEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .build()
    );

    public static final EntityType<DipEntity> DIP_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Isaac.MOD_ID, "dip"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DipEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.5f)).build()
    );

    public static final HashMap<Entities, EntityType<?>> ENTITIES = new HashMap<>();

    public static void initialize() {
        ENTITIES.put(Entities.DIP_ENTITY, DIP_ENTITY_TYPE);

        FabricDefaultAttributeRegistry.register(DIP_ENTITY_TYPE, DipEntity.createMobAttributes());
    }
}
