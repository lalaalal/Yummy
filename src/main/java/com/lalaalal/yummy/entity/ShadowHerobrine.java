package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.entity.goal.FollowTargetGoal;
import com.lalaalal.yummy.entity.skill.PunchSkill;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ShadowHerobrine extends AbstractHerobrine {
    private static final int FIRST_MOVING_DURATION = 150;
    private static final int START_MOVING_TICK = 8;

    private final AnimationFactory animationFactory = GeckoLibUtil.createFactory(this, false);
    private LivingEntity parent;
    private Vec3 firstPos;
    private int firstMovingTick = 0;
    private int tickOffset = 0;

    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MAX_HEALTH, 66)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3);
    }

    protected ShadowHerobrine(EntityType<? extends ShadowHerobrine> entityType, Level level) {
        super(entityType, level, true);
    }

    public ShadowHerobrine(Level level, Vec3 position) {
        this(YummyEntities.SHADOW_HEROBRINE.get(), level);
        setPos(position);
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
        return parent != null;
    }

    public void setParent(LivingEntity parent) {
        this.parent = parent;
        this.goalSelector.addGoal(2, new FollowParentGoal(parent));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.isBypassInvul();
    }

    @Override
    protected void registerSkills() {
        registerSkill(new PunchSkill(this, 0));
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
            HerobrineMark.overlapMark(livingEntity, parent);
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.WEAKNESS, 120, 5);
            livingEntity.addEffect(mobEffectInstance);
        }

        return super.doHurtTarget(entity);
    }

    @Override
    public void aiStep() {
        if (tickCount > START_MOVING_TICK + tickOffset)
            super.aiStep();
        else if (!level.isClientSide && firstPos != null)
            moveTo(firstPos);
    }

    @Override
    protected void customServerAiStep() {
        if ((!hasParent() || !parent.isAlive()) && tickCount > 200)
            kill();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (firstMovingTick < FIRST_MOVING_DURATION) {
            firstMovingTick += 1;
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shadow_herobrine.summoned_shadow", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        String skillName = getUsingSkillName();
        if (!skillName.equals(SKILL_NONE)) {
            String animationName = "animation.shadow_herobrine." + skillName;
            event.getController().setAnimation(new AnimationBuilder().addAnimation(animationName, ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        if (!event.isMoving())
            return PlayState.STOP;

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.shadow_herobrine.walk", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "shadow_controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
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
            BlockPos teleportPos = YummyUtil.randomPos(blockpos, 5, level.getRandom());

            moveTo(teleportPos, 0, 0);
        }
    }
}
