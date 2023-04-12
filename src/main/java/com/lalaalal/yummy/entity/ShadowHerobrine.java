package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.effect.HerobrineMark;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ShadowHerobrine extends PathfinderMob {
    public static AttributeSupplier.Builder getHerobrineAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 66)
                .add(Attributes.ARMOR, 6)
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.38);
    }

    protected ShadowHerobrine(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, false,
                (livingEntity) -> !(livingEntity instanceof Herobrine)));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof LivingEntity livingEntity)
            HerobrineMark.overlapMark(livingEntity);

        return super.doHurtTarget(entity);
    }
}
