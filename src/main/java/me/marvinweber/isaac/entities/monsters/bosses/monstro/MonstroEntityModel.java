package me.marvinweber.isaac.entities.monsters.bosses.monstro;


import me.marvinweber.isaac.Isaac;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class MonstroEntityModel extends DefaultedEntityGeoModel<MonstroEntity> {

    public MonstroEntityModel() {
        super(new Identifier(Isaac.MOD_ID, "monstro/monstro_entity"));
    }
}
