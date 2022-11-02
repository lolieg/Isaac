package me.marvinweber.isaac.blocks.multiblock;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.Player;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Multiblock extends Block {
    public static final EnumProperty<MultiblockPart> PART = EnumProperty.of("part", MultiblockPart.class);

    public Multiblock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    public void place(World world, BlockPos pos, BlockState state, List<MultiblockPart> blocks) {
        for (MultiblockPart part :
                blocks) {
            BlockPos newPos = pos.add(part.getPos());
            world.setBlockState(newPos, state.with(PART, part));
        }
    }
    public void place(World world, BlockPos pos, BlockState state) {
        place(world, pos, state, Arrays.asList(MultiblockPart.values()));
    }

    public void breakBlocks(World world, BlockPos pos, BlockState blockState, DamageSource source, @Nullable Player breaker) {
        BlockPos parentPos = pos.subtract(blockState.get(PART).getPos());
        ArrayList<MultiblockPart> parts = new ArrayList<>(Arrays.asList(MultiblockPart.values()));
        Collections.shuffle(parts);

        int toBreak = 0;
        if(source.isExplosive()) toBreak = parts.size();
        if(source.isProjectile()) toBreak = (int) (parts.size() - blockState.getBlock().getHardness());

        for (int i = 0; i < toBreak; i++) {
            MultiblockPart part = parts.get(i);
            BlockPos newPos = parentPos.add(part.getPos());
            world.setBlockState(newPos, Blocks.AIR.getDefaultState());
        }
    }

}














