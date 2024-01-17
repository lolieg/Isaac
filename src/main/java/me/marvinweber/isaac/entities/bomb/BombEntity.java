package me.marvinweber.isaac.entities.bomb;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.registry.common.BlockRegistry;
import me.marvinweber.isaac.registry.common.EntityRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;


public class BombEntity extends Entity {

    private static final TrackedData<Integer> FUSE = DataTracker.registerData(TntEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int DEFAULT_FUSE = 30;
    @Nullable
    private LivingEntity causingEntity;

    public BombEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    public BombEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(EntityRegistry.BOMB_ENTITY_TYPE, world);
        this.setPosition(x, y, z);
        this.setFuse(DEFAULT_FUSE);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.causingEntity = igniter;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(FUSE, DEFAULT_FUSE);
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }


    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(this.getVelocity().multiply(0.98));
        if (this.onGround) {
            this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
        }
        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.world.isClient) {
                this.explode();
            }
        } else {
            if (this.world.isClient) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, 0.0, 0.0, 0.0);
            }
        }
    }

    private void explode() {
        Explosion explosion = this.world.createExplosion(this, null, new BombExplosionBehavior(), this.getX(), this.getBodyY(0.0625), this.getZ(), 2.5f, false, World.ExplosionSourceType.BLOCK);
        explosion.getAffectedPlayers().forEach(((playerEntity, vec3d) -> {
            Player player = Player.findPlayer(playerEntity.getUuid());
            if (player == null) return;
            player.healthManager.damage(2);
            player.onUpdateHealth();
        }));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setFuse(nbt.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getCausingEntity() {
        return this.causingEntity;
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.15f;
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(FUSE, fuse);
    }

    public int getFuse() {
        return this.dataTracker.get(FUSE);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
