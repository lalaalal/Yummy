package com.lalaalal.yummy.entity.skill;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public abstract class Skill {
    protected Mob usingEntity;
    private int cooldown = 0;
    private int warmup = 0;

    public Skill(Mob usingEntity, int cooldown, int warmup) {
        this.usingEntity = usingEntity;
        setCooldown(cooldown);
        setWarmup(warmup);
    }

    public void setCooldown(int cooldown) {
        if (cooldown >= 0)
            this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    public double getDistanceWithTarget() {
        LivingEntity target = usingEntity.getTarget();
        if (target == null)
            return Double.MAX_VALUE;

        return this.usingEntity.distanceToSqr(target.getX(), target.getY(), target.getZ());
    }

    public void setWarmup(int warmup) {
        if (warmup >= 0)
            this.warmup = warmup;
    }

    public int getWarmup() {
        return warmup;
    }

    public void showEffect() {

    }

    public abstract boolean canUse();

    public abstract void useSkill();

    public void endEffect() {

    }
}
