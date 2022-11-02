package me.marvinweber.isaac.blocks.bomb;

import com.google.common.collect.ImmutableMap;
import me.marvinweber.isaac.entities.bomb.BombEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.stream.Stream;

public class BombBlock extends Block {
    private final VoxelShape voxelShape = Stream.of(
            Block.createCuboidShape(6, 8, 8, 7, 9, 9),
            Block.createCuboidShape(7, 0, 7, 9, 1, 9),
            Block.createCuboidShape(6, 1, 6, 10, 2, 10),
            Block.createCuboidShape(6, 2, 6, 10, 4, 10),
            Block.createCuboidShape(6, 4, 6, 10, 5, 10),
            Block.createCuboidShape(7, 5, 7, 9, 6, 9),
            Block.createCuboidShape(5, 2, 6, 6, 4, 10),
            Block.createCuboidShape(10, 2, 6, 11, 4, 10),
            Block.createCuboidShape(6, 2, 5, 10, 4, 6),
            Block.createCuboidShape(6, 2, 10, 10, 4, 11),
            Block.createCuboidShape(7, 6, 8, 8, 8, 9)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    public BombBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ImmutableMap<BlockState, VoxelShape> getShapesForStates(Function<BlockState, VoxelShape> function) {
        return super.getShapesForStates(function);
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

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.isAir(pos.down());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            return;
        }
        assert placer != null;
        ((PlayerEntity) placer).getItemCooldownManager().set(this.asItem(), 35);
        BombEntity bombEntity = new BombEntity(world, pos.getX(), pos.getY(), pos.getZ(), placer);
        world.spawnEntity(bombEntity);
        world.playSound(null, bombEntity.getX(), bombEntity.getY(), bombEntity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
//        world.emitGameEvent(placer, GameEvent.PRIME_FUSE, pos);
        world.removeBlock(pos, false);
    }
}
