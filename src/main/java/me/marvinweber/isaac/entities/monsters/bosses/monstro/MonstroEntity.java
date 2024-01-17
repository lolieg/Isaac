package me.marvinweber.isaac.entities.monsters.bosses.monstro;

import io.wispforest.owo.ui.core.Animatable;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.registry.common.SoundRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.util.GeckoLibUtil;


import java.util.EnumSet;

public class MonstroEntity extends BaseEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public MonstroEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.moveControl = new MonstroMoveControl(this);

    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_MAX_HEALTH, 250);
    }

    @Override
    public int getSafeFallDistance() {
        return super.getSafeFallDistance();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MonstroEntity.FaceTowardTargetGoal(this));
        this.goalSelector.add(2, new MonstroEntity.MoveGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, (livingEntity) -> true));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    @Override
    public void tick() {
        super.tick();
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "idle", 5, state -> state.setAndContinue(DefaultAnimations.IDLE)),
                new AnimationController<>(this, "move", 5, state -> state.setAndContinue(DefaultAnimations.JUMP)),
                new AnimationController<>(this, "attack", 5, state -> state.setAndContinue(DefaultAnimations.IDLE))
                        .triggerableAnim("vomit", DefaultAnimations.ATTACK_SHOOT)
                );

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    protected int getTicksUntilNextJump() {
        return 15;
    }

    protected SoundEvent getJumpSound() {
        return SoundRegistry.ISAAC_ENTITY_STOMP;
    }

    float getJumpSoundPitch() {
        float f = 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }




    private static class MonstroMoveControl extends MoveControl {
        private float targetYaw;
        private int ticksUntilJump;
        private final MonstroEntity monstro;


        public MonstroMoveControl(MonstroEntity monstro) {
            super(monstro);
            this.monstro = monstro;
            this.targetYaw = 180.0F * monstro.getYaw() / 3.1415927F;
        }

        public void look(float targetYaw) {
            this.targetYaw = targetYaw;
        }

        public void move(double speed) {
            this.speed = speed;
            this.state = State.MOVE_TO;
        }

        public void tick() {
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.targetYaw, 90.0F));
            this.entity.headYaw = this.entity.getYaw();
            this.entity.bodyYaw = this.entity.getYaw();
            if (this.state != State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = State.WAIT;
                if (this.entity.isOnGround()) {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.monstro.getTicksUntilNextJump();
//                        this.monstro.getJumpControl().setActive();
                        this.monstro.setVelocity(this.monstro.getVelocity().add(0, 0.4, 0));

                    } else {
                        this.monstro.sidewaysSpeed = 0.0F;
                        this.monstro.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
                }

            }
        }
    }


    static class FaceTowardTargetGoal extends Goal {
        private final MonstroEntity monstro;
        private int ticksLeft;

        public FaceTowardTargetGoal(MonstroEntity monstro) {
            this.monstro = monstro;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.monstro.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                return this.monstro.canTarget(livingEntity) && this.monstro.getMoveControl() instanceof MonstroMoveControl;
            }
        }

        public void start() {
            this.ticksLeft = toGoalTicks(300);
            super.start();
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = this.monstro.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!this.monstro.canTarget(livingEntity)) {
                return false;
            } else {
                return --this.ticksLeft > 0;
            }
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.monstro.getTarget();
            if (livingEntity != null) {
                this.monstro.lookAtEntity(livingEntity, 10.0F, 10.0F);
            }

            ((MonstroEntity.MonstroMoveControl)this.monstro.getMoveControl()).look(this.monstro.getYaw());
        }
    }


    static class MoveGoal extends Goal {
        private final MonstroEntity monstro;

        public MoveGoal(MonstroEntity monstro) {
            this.monstro = monstro;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        public boolean canStart() {
            return !this.monstro.hasVehicle();
        }

        public void tick() {
            ((MonstroEntity.MonstroMoveControl)this.monstro.getMoveControl()).move(1.0D);
        }
    }
}
