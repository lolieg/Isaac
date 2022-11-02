package me.marvinweber.isaac.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.stream.Stream;


public class PedestalBlock extends Block {
    private final VoxelShape voxelShape = Stream.of(
            Block.createCuboidShape(10, 0, -4, 11, 12, 4),
            Block.createCuboidShape(11, 0, -5, 21, 11, 5),
            Block.createCuboidShape(12, 0, 5, 20, 12, 6),
            Block.createCuboidShape(12, 0, -6, 20, 12, -5),
            Block.createCuboidShape(21, 0, -4, 22, 12, 4),
            Block.createCuboidShape(11, 11, -5, 21, 12, 5),
            Block.createCuboidShape(9, 2, -6, 10, 3, 6),
            Block.createCuboidShape(22, 2, -6, 23, 3, 6),
            Block.createCuboidShape(8, 1, -7, 9, 2, 7),
            Block.createCuboidShape(23, 1, -7, 24, 2, 7),
            Block.createCuboidShape(7, 0, -8, 8, 1, 8),
            Block.createCuboidShape(24, 0, -8, 25, 1, 8),
            Block.createCuboidShape(10, 2, -7, 22, 3, -6),
            Block.createCuboidShape(9, 1, -8, 23, 2, -7),
            Block.createCuboidShape(8, 0, -9, 24, 1, -8),
            Block.createCuboidShape(10, 2, 6, 22, 3, 7),
            Block.createCuboidShape(9, 1, 7, 23, 2, 8),
            Block.createCuboidShape(8, 0, 8, 24, 1, 9),
            Block.createCuboidShape(10, 3, -5, 11, 4, -4),
            Block.createCuboidShape(10, 2, -6, 11, 3, -5),
            Block.createCuboidShape(11, 3, -6, 12, 4, -5),
            Block.createCuboidShape(8, 0, -8, 9, 1, -7),
            Block.createCuboidShape(9, 1, -7, 10, 2, -6),
            Block.createCuboidShape(20, 3, -6, 21, 4, -5),
            Block.createCuboidShape(21, 3, -5, 22, 4, -4),
            Block.createCuboidShape(21, 2, -6, 22, 3, -5),
            Block.createCuboidShape(21, 2, 5, 22, 3, 6),
            Block.createCuboidShape(10, 2, 5, 11, 3, 6),
            Block.createCuboidShape(21, 3, 4, 22, 4, 5),
            Block.createCuboidShape(10, 3, 4, 11, 4, 5),
            Block.createCuboidShape(20, 3, 5, 21, 4, 6),
            Block.createCuboidShape(11, 3, 5, 12, 4, 6),
            Block.createCuboidShape(22, 1, -7, 23, 2, -6),
            Block.createCuboidShape(22, 1, 6, 23, 2, 7),
            Block.createCuboidShape(9, 1, 6, 10, 2, 7),
            Block.createCuboidShape(23, 0, -8, 24, 1, -7),
            Block.createCuboidShape(23, 0, 7, 24, 1, 8),
            Block.createCuboidShape(8, 0, 7, 9, 1, 8)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public PedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return voxelShape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return voxelShape;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return voxelShape;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return voxelShape;
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return voxelShape;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return voxelShape;
    }
}
