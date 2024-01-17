package me.marvinweber.isaac.registry.client;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.bomb.BombEntityRenderer;
import me.marvinweber.isaac.entities.monsters.bosses.monstro.MonstroEntityRenderer;
import me.marvinweber.isaac.entities.monsters.dip.DipEntityModel;
import me.marvinweber.isaac.entities.monsters.dip.DipEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class EntityRegistry {
    public static void initialize() {
        EntityRendererRegistry.register(me.marvinweber.isaac.registry.common.EntityRegistry.TEAR_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(me.marvinweber.isaac.registry.common.EntityRegistry.DIP_ENTITY_TYPE, DipEntityRenderer::new);
        EntityRendererRegistry.register(me.marvinweber.isaac.registry.common.EntityRegistry.BOMB_ENTITY_TYPE, BombEntityRenderer::new);

        EntityRendererRegistry.register(me.marvinweber.isaac.registry.common.EntityRegistry.MONSTRO_ENTITY_TYPE, MonstroEntityRenderer::new);

    }
}
