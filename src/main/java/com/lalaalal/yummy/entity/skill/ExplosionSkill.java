package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplosionSkill extends Skill {
    public static final int COOLDOWN = 600;

    public static final int WARMUP = 40;
    protected double ATTACK_REACH = 25;
    protected float explosionRadius = 6.0f;
    protected boolean explosionCauseFire = true;

    public ExplosionSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
    }

    public ExplosionSkill(Mob usingEntity, int cooldown) {
        super(usingEntity, cooldown, WARMUP);
    }

    protected boolean isAttackReachable() {
        return getDistanceWithTarget() < ATTACK_REACH;
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
                isAttackReachable();
    }

    @Override
    public void showEffect() {
        if (usingEntity instanceof Herobrine herobrine)
            herobrine.setArmPose(Herobrine.ArmPose.RAISE_BOTH);
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        level.explode(usingEntity, usingEntity.getX(), usingEntity.getY(), usingEntity.getZ(),
                explosionRadius, explosionCauseFire, Explosion.BlockInteraction.NONE);
    }

    @Override
    public void endEffect() {
        if (usingEntity instanceof Herobrine herobrine)
            herobrine.setArmPose(Herobrine.ArmPose.NORMAL);
    }
}
