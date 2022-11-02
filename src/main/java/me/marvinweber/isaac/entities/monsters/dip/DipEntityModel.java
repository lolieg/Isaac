package me.marvinweber.isaac.entities.monsters.dip;

import me.marvinweber.isaac.Isaac;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShapes;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DipEntityModel extends AnimatedGeoModel<DipEntity> {

    @Override
    public Identifier getModelLocation(DipEntity object) {
        return new Identifier(Isaac.MOD_ID, "geo/dip_entity.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DipEntity object) {
        return new Identifier(Isaac.MOD_ID, "textures/entity/dip/dip.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DipEntity animatable) {
        return new Identifier(Isaac.MOD_ID, "animations/dip_entity.animation.json");
    }
}

