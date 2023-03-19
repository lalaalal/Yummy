package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class HerobrineExplosion extends Skill {
    public static final int COOLDOWN = 200;
    protected static final double ATTACK_REACH = 25;
    private float explosionRadius = 6.0f;

    public HerobrineExplosion(Mob usingEntity) {
        super(usingEntity, COOLDOWN);
    }

    protected boolean isAttackReachable(LivingEntity target) {
        YummyMod.LOGGER.debug("distance to sqr : " + this.usingEntity.distanceToSqr(target.getX(), target.getY(), target.getZ()));
        return this.usingEntity.distanceToSqr(target.getX(), target.getY(), target.getZ()) < ATTACK_REACH;
    }

    public void setExplosionRadius(float explosionRadius) {
        if (explosionRadius > 0)
            this.explosionRadius = explosionRadius;
    }

    @Override
    public void showEffect() {

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
                explosionRadius, true, Explosion.BlockInteraction.NONE);
    }
}
