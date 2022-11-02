package me.marvinweber.isaac.entities.monsters.dip;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.registry.client.EntityRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DipEntityRenderer extends GeoEntityRenderer<DipEntity> {

    public DipEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DipEntityModel());
    }

//    @Override
//    public Identifier getTexture(DipEntity entity) {
//        return new Identifier(Isaac.MOD_ID, "textures/entity/dip/dip.old.png");
//    }


}
