package me.marvinweber.isaac.entities;

import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class BaseEnemyNearbyPredicate implements Predicate<BaseEntity> {
    private final Vec3d pos;

    public BaseEnemyNearbyPredicate(Vec3d pos) {
        this.pos = pos;
    }

    @Override
    public boolean test(BaseEntity enemy) {
        return this.pos.distanceTo(enemy.getPos()) < 10;
    }
}
