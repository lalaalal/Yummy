package com.lalaalal.yummy.entity.skill;

import net.minecraft.world.entity.Mob;

public abstract class Skill {
    protected Mob usingEntity;
    private long cooldown;

    public Skill(Mob usingEntity, int cooldown) {
        this.usingEntity = usingEntity;
        setCooldown(cooldown);
    }

    public void setCooldown(long cooldown) {
        if (cooldown >= 0)
            this.cooldown = cooldown;
    }

    public long getCooldown() {
        return cooldown;
    }

    public void showEffect() {
    }

    public abstract boolean canUse();

    public abstract void useSkill();
}
