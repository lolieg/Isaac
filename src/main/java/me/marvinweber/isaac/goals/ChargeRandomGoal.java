package me.marvinweber.isaac.goals;

import me.marvinweber.isaac.Utility;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ChargeRandomGoal extends Goal {
    protected final PathAwareEntity mob;
    protected Vec3d target;
    protected int cooldown;

    public ChargeRandomGoal(PathAwareEntity entity) {
        this.mob = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        this.cooldown = 0;
    }

    @Override
    public boolean canStart() {
        if (this.cooldown >= 0)
            this.cooldown--;
        Vec3d vec3d;
        if ((vec3d = this.getWanderTarget()) == null) {
            return false;
        }
        if (this.mob.getRandom().nextInt(20) != 0) {
            return false;
        }
        if (this.cooldown > 0) {
            return false;
        }
        this.target = vec3d;
        return true;
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        return this.mob.getPos().add(Utility.getRandomNumber(-2, 2, this.mob.getRandom()), this.mob.getPos().y, Utility.getRandomNumber(-2, 2, this.mob.getRandom()));
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers();
    }

    @Override
    public void start() {
        double f = this.target.x - this.mob.getPos().x;
        double h = this.target.z - this.mob.getPos().z;
        if(!this.mob.world.getBlockState(new BlockPos(new Vec3i((int) this.target.getX(), (int) this.target.getY(), (int) this.target.getZ()))).isAir()){
            f = -f;
            h = -h;
        }
        this.mob.setVelocity(f, 0, h);
        this.mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.target);
        this.cooldown = 20;
//        this.mob.getNavigation().startMovingTo(this.target.x, this.target.y, this.target.z, 0.6f);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

}
