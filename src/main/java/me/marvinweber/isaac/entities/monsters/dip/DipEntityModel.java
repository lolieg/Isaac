package me.marvinweber.isaac.entities.monsters.dip;

import me.marvinweber.isaac.Isaac;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;


public class DipEntityModel extends DefaultedEntityGeoModel<DipEntity> {

    public DipEntityModel() {
        super(new Identifier(Isaac.MOD_ID, "dip/dip_entity"));
    }
}

