package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShadowHerobrine extends Herobrine implements PowerableMob {
    private Herobrine herobrine;

    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MAX_HEALTH, 66)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3);
    }

    protected ShadowHerobrine(EntityType<? extends Herobrine> entityType, Level level) {
        super(entityType, level);
    }

    public boolean hasOwner() {
        return herobrine != null;
    }

    public void setHerobrine(Herobrine herobrine) {
        this.herobrine = herobrine;
        this.goalSelector.addGoal(1, new FollowHerobrineGoal(herobrine));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return !source.isBypassInvul();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Herobrine.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, false,
                (livingEntity) -> !livingEntity.getType().is(YummyTags.HEROBRINE)));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            HerobrineMark.overlapMark(livingEntity);
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.WEAKNESS, 120, 5);
            livingEntity.addEffect(mobEffectInstance);
        }

        return super.doHurtTarget(entity);
    }

    @Override
    public boolean isPowered() {
        return true;
    }

    private class FollowHerobrineGoal extends Goal {
        public static final double START_FOLLOWING_DISTANCE = 12;

        private final Herobrine herobrine;
        private int timeToRecalculatePath;

        public FollowHerobrineGoal(Herobrine herobrine) {
            this.herobrine = herobrine;
        }

        @Override
        public boolean canUse() {
            return distanceToSqr(herobrine) > START_FOLLOWING_DISTANCE * START_FOLLOWING_DISTANCE;
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

            getLookControl().setLookAt(herobrine, 10.0F, (float) getMaxHeadXRot());
            if (--this.timeToRecalculatePath <= 0) {
                this.timeToRecalculatePath = this.adjustedTickDelay(10);
                if (distanceToSqr(herobrine) >= (20 * 20)) {
                    teleportToHerobrine();
                } else {
                    navigation.moveTo(herobrine, 1);
                }
            }
        }

        private void teleportToHerobrine() {
            BlockPos blockpos = this.herobrine.blockPosition();
            BlockPos teleportPos = YummyUtil.randomPos(blockpos, 5, level.getRandom());

            moveTo(teleportPos, 0, 0);
        }
    }
}
