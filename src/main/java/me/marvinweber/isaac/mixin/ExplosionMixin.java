package me.marvinweber.isaac.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Objects;

@Mixin(Explosion.class)
public abstract class ExplosionMixin{

    @Shadow @Final private World world;

    @Shadow @Nullable public abstract Entity getEntity();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"), method = "affectWorld", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void affectWorld(boolean particles, CallbackInfo ci, boolean bl, ObjectArrayList objectArrayList, boolean bl2, ObjectListIterator var5, BlockPos blockPos, BlockState blockState, Block block) {
        if (blockState.getBlock() instanceof Multiblock) {
            ((Multiblock) blockState.getBlock()).breakBlocks(world, blockPos, blockState, Objects.requireNonNull(this.getEntity()).getDamageSources().explosion((Explosion)(Object)this), null);
        }
    }
}
