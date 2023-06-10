package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.entity.ai.goal.FollowTargetGoal;
import com.lalaalal.yummy.entity.ai.skill.FractureRushSkill;
import com.lalaalal.yummy.entity.ai.skill.PunchSkill;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShadowHerobrine extends AbstractHerobrine {
    public static final float DEFAULT_MOVEMENT_SPEED = 0.18f;
    private static final int FIRST_MOVING_DURATION = 150;
    private static final int START_MOVING_TICK = 8;

    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private Herobrine herobrine;
    private Vec3 firstPos;
    private int firstMovingTick = 0;
    private int tickOffset = 0;

    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MAX_HEALTH, 66)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, DEFAULT_MOVEMENT_SPEED)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3);
    }

    protected ShadowHerobrine(EntityType<? extends ShadowHerobrine> entityType, Level level) {
        super(entityType, level, true);
        setPersistenceRequired();
    }

    public ShadowHerobrine(Level level, Vec3 position) {
        this(YummyEntities.SHADOW_HEROBRINE.get(), level);
        setPos(position);
        setPersistenceRequired();
    }

    public void setTickOffset(int tickOffset) {
        this.tickOffset = tickOffset;
    }

    public ShadowHerobrine(Level level, Vec3 position, Vec3 firstPos) {
        this(YummyEntities.SHADOW_HEROBRINE.get(), level);
        setPos(position);
        this.firstPos = firstPos;
    }

    public boolean hasParent() {
        return herobrine != null;
    }

    public void setHerobrine(Herobrine herobrine) {
        this.herobrine = herobrine;
        this.goalSelector.addGoal(2, new FollowParentGoal(herobrine));
    }

    public void changeSpeed(double value) {
        AttributeInstance attributeInstance = getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeInstance != null)
            attributeInstance.setBaseValue(value);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY);
    }

    @Override
    protected void registerSkills() {
        registerSkill(new PunchSkill(this, 0));

        addSkillFinishListener((skill, interrupted) -> {
            if (!interrupted && skill.getBaseName().equals(FractureRushSkill.NAME)) {
                if (herobrine != null)
                    herobrine.increaseCorruptedWaveStack();
            }
        });
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new FollowTargetGoal(this));
        goalSelector.addGoal(3, new RandomStrollGoal(this, 1));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Herobrine.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, false,
                (livingEntity) -> !livingEntity.getType().is(YummyTags.HEROBRINE)));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            HerobrineMark.overlapMark(livingEntity, herobrine);
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.WEAKNESS, 120, 5);
            livingEntity.addEffect(mobEffectInstance);
        }

        return super.doHurtTarget(entity);
    }

    @Override
    public void aiStep() {
        if (tickCount > START_MOVING_TICK + tickOffset)
            super.aiStep();
        else if (!level().isClientSide && firstPos != null)
            moveTo(firstPos);
    }

    @Override
    protected void customServerAiStep() {
        if ((!hasParent() || !herobrine.isAlive()))
            kill();
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        if (firstMovingTick < FIRST_MOVING_DURATION) {
            firstMovingTick += 1;
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().thenPlay("animation.shadow_herobrine.summoned_shadow"));
            return PlayState.CONTINUE;
        }

        String skillName = getUsingSkillName();
        if (!skillName.equals(SKILL_NONE)) {
            String animationName = "animation.shadow_herobrine." + skillName;
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().thenPlay(animationName));
            return PlayState.CONTINUE;
        }

        if (!geoAnimatableAnimationState.isMoving())
            return PlayState.STOP;

        geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("animation.shadow_herobrine.walk"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "shadow_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }

    private class FollowParentGoal extends Goal {
        public static final double START_FOLLOWING_DISTANCE = 24;

        private final LivingEntity parent;
        private int timeToRecalculatePath;

        public FollowParentGoal(LivingEntity parent) {
            this.parent = parent;
        }

        @Override
        public boolean canUse() {
            return distanceToSqr(parent) > START_FOLLOWING_DISTANCE * START_FOLLOWING_DISTANCE;
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }

        public void start() {
            this.timeToRecalculatePath = 0;
        }

        @Override
        public void tick() {
            super.tick();

            getLookControl().setLookAt(parent, 10.0F, (float) getMaxHeadXRot());
            if (--this.timeToRecalculatePath <= 0) {
                this.timeToRecalculatePath = this.adjustedTickDelay(10);
                if (distanceToSqr(parent) >= (START_FOLLOWING_DISTANCE * START_FOLLOWING_DISTANCE)) {
                    teleportToHerobrine();
                } else {
                    navigation.moveTo(parent, 1);
                }
            }
        }

        private void teleportToHerobrine() {
            BlockPos blockpos = this.parent.blockPosition();
            BlockPos teleportPos = YummyUtil.randomPos(blockpos, 5, level().getRandom());

            moveTo(teleportPos, 0, 0);
        }
    }
}
