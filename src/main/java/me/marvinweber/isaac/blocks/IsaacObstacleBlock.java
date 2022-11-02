package me.marvinweber.isaac.blocks;

import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import net.minecraft.world.explosion.Explosion;
import oshi.util.tuples.Triplet;

public class IsaacObstacleBlock extends Multiblock {

    public IsaacObstacleBlock(Settings settings) {
        super(settings);

    }

    @Override
    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }




}
