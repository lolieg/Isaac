package me.marvinweber.isaac.entities;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import me.marvinweber.isaac.blocks.multiblock.MultiblockPart;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.packets.EntitySpawnPacket;
import me.marvinweber.isaac.registry.common.EntityRegistry;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import me.marvinweber.isaac.registry.common.PacketRegistry;
import me.marvinweber.isaac.registry.common.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class TearEntity extends ThrownItemEntity {
    private Vec3d spawnPos;

    public TearEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public TearEntity(World world, LivingEntity owner) {
        super(EntityRegistry.TEAR_ENTITY_TYPE, owner, world);
        this.setNoGravity(true);
    }

    public TearEntity(World world, double x, double y, double z) {
        super(EntityRegistry.TEAR_ENTITY_TYPE, x, y, z, world);
        this.setNoGravity(true);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        this.spawnPos = this.getPos();
        return EntitySpawnPacket.create(this, PacketRegistry.SPAWN_PACKET);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.TEAR_ITEM;
    }

    @Override
    public void setVelocity(double x, double y, double z, float speed, float divergence) {
        Player player = Isaac.game.getPlayer((PlayerEntity) getOwner());
        if (!world.isClient() && player != null) {
            // ShotSpeed
            super.setVelocity(x, y, z, player.playerStats.shotSpeed.current * 0.9f, divergence);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        if (entityHitResult.getEntity() instanceof PlayerEntity) return;
        Player player = Isaac.game.getPlayer((PlayerEntity) getOwner());
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        if (entity instanceof LivingEntity livingEntity && !world.isClient() && player != null) {
            this.world.sendEntityStatus(this, (byte) 3);
            // Damage
            entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), player.playerStats.damage.current);
            // Knockback stat
            livingEntity.takeKnockback(player.playerStats.knockback.current, MathHelper.sin(Objects.requireNonNull(this.getOwner()).getYaw() * ((float) Math.PI / 180)), -MathHelper.cos(Objects.requireNonNull(this.getOwner()).getYaw() * ((float) Math.PI / 180)));
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState blockState = this.world.getBlockState(pos);
        if(blockState.getBlock() instanceof Multiblock) {
            ((Multiblock) blockState.getBlock()).breakBlocks(world, pos, blockState, DamageSource.thrownProjectile(this, this.getOwner()), Isaac.game.getPlayer((PlayerEntity) getOwner()));
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            this.world.addParticle(ParticleRegistry.TEAR_COLLISION, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.kill();
        }
    }

    public float distanceToSpawnPos() {
        float f = (float) (this.getX() - this.spawnPos.getX());
        float g = (float) (this.getY() - this.spawnPos.getY());
        float h = (float) (this.getZ() - this.spawnPos.getZ());
        return MathHelper.sqrt(f * f + g * g + h * h);
    }

    @Override
    public void tick() {
        super.tick();
        Player player = Isaac.game.getPlayer((PlayerEntity) getOwner());
        if (player == null) return;
        // Range Stat
        if (!world.isClient() && player.playerStats != null && this.spawnPos != null && this.distanceToSpawnPos() > player.playerStats.range.current * 2) {
            this.world.sendEntityStatus(this, (byte) 3);
            this.kill();
        }
        for (IsaacItem item :
                player.items) {
            item.tearTickEffect(this, player);
        }
    }
}
