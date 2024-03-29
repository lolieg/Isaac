package me.marvinweber.isaac.entities;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.mapgen.rooms.layouts.Entities;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BaseEntity extends PathAwareEntity {
    public static Entities isaacEntity;

    protected BaseEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (isDead()) {
            this.onDeath(this.getRecentDamageSource());
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getType().msgId().equals("explosion")) {
            this.setHealth(this.getHealth() - 100);
        } else {
            this.setHealth(this.getHealth() - amount);
        }
        if (this.isDead()){
            return false;
        }
        return true;
    }

    @Override
    public void onDeath(DamageSource source) {
        this.remove(RemovalReason.KILLED);
        if(Isaac.game.gameState == Game.State.RUNNING)
            Isaac.game.onEnemyDeath(this, source);
    }
}
