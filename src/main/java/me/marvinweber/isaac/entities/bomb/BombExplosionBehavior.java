package me.marvinweber.isaac.entities.bomb;

import me.marvinweber.isaac.blocks.IsaacObstacleBlock;
import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class BombExplosionBehavior extends ExplosionBehavior {

    public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        return Optional.empty();
    }

    public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        return state.getBlock() instanceof Multiblock;
    }

}
