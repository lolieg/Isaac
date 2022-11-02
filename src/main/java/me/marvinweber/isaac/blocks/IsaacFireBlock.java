package me.marvinweber.isaac.blocks;

import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import me.marvinweber.isaac.entities.TearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class IsaacFireBlock extends Multiblock{
    public IsaacFireBlock(Settings settings) {
        super(settings);
    }

}
