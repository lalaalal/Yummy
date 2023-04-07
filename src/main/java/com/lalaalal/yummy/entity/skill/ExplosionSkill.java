package com.lalaalal.yummy.entity.skill;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplosionSkill extends Skill {
    public static final int COOLDOWN = 600;
    protected double ATTACK_REACH = 25;
    protected float explosionRadius = 6.0f;
    protected boolean explosionCauseFire = true;

    public ExplosionSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN);
    }

    public ExplosionSkill(Mob usingEntity, int cooldown) {
        super(usingEntity, cooldown);
    }

    protected boolean isAttackReachable(LivingEntity target) {
        return this.usingEntity.distanceToSqr(target.getX(), target.getY(), target.getZ()) < ATTACK_REACH;
    }

    public void setExplosionRadius(float explosionRadius) {
        if (explosionRadius > 0)
            this.explosionRadius = explosionRadius;
    }

    public void setExplosionCauseFire(boolean value) {
        explosionCauseFire = value;
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null &&
                usingEntity.getTarget().isAlive() &&
                isAttackReachable(usingEntity.getTarget());
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        level.explode(usingEntity, usingEntity.getX(), usingEntity.getY(), usingEntity.getZ(),
                explosionRadius, explosionCauseFire, Explosion.BlockInteraction.NONE);
    }
}
