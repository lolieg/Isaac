package me.marvinweber.isaac.entities.monsters.bosses.monstro;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MonstroEntityRenderer extends GeoEntityRenderer<MonstroEntity> {
    public MonstroEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MonstroEntityModel());
    }
}
