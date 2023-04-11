package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ShowParticlePacket;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ExplosionSkill extends Skill {
    public static final int COOLDOWN = 600;

    public static final int WARMUP = 20;
    protected double attackReach = 50;
    protected float explosionRadius = 27f;
    protected boolean explosionCauseFire = false;

    public ExplosionSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
    }

    public ExplosionSkill(Mob usingEntity, int cooldown) {
        super(usingEntity, cooldown, WARMUP);
    }

    protected boolean isAttackReachable() {
        return getDistanceWithTarget() < attackReach;
    }

    public void setAttackReach(double attackReach) {
        this.attackReach = attackReach;
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
        LevelChunk levelChunk = usingEntity.getLevel().getChunkAt(usingEntity.getOnPos());
        ShowParticlePacket packet = new ShowParticlePacket.Builder("polluted_particle_blue")
                .setXYZ(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ())
                .setSpeed(0, 5, 0)
                .setRange(1)
                .setParticleCount(20)
                .build();
        YummyMessages.sendToPlayer(packet, levelChunk);
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        level.explode(usingEntity, usingEntity.getX(), usingEntity.getY(), usingEntity.getZ(),
                explosionRadius, explosionCauseFire, Explosion.BlockInteraction.NONE);
        LevelChunk levelChunk = usingEntity.getLevel().getChunkAt(usingEntity.getOnPos());
        ShowParticlePacket packet = new ShowParticlePacket.Builder("explosion_emitter")
                .setParticleNamespace("minecraft")
                .setXYZ(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ())
                .setSpeed(0, 0.3, 0)
                .setRange(1)
                .setParticleCount(1)
                .build();
        YummyMessages.sendToPlayer(packet, levelChunk);
    }
}
