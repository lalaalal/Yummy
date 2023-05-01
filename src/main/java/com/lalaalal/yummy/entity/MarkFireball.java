package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.effect.HerobrineMark;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class MarkFireball extends Fireball {
    protected boolean explosion = true;
    protected float explosionPower = 1;
    protected boolean markEntities = true;
    protected int time = 0;
    private int discardTime = 20 * 7;

    public MarkFireball(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    public MarkFireball(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
        super(YummyEntities.MARK_FIREBALL.get(), shooter, offsetX, offsetY, offsetZ, level);
    }

    public MarkFireball(EntityType<? extends Fireball> entityType, Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, int explosionPower, boolean markEntities) {
        super(entityType, shooter, offsetX, offsetY, offsetZ, level);
        this.explosionPower = explosionPower;
        this.markEntities = markEntities;
    }

    public void setExplosion(boolean explosion) {
        this.explosion = explosion;
    }

    public void setExplosionPower(float explosionPower) {
        this.explosionPower = explosionPower;
    }

    public void setMarkEntities(boolean markEntities) {
        this.markEntities = markEntities;
    }

    public void setDiscardTime(int discardTime) {
        this.discardTime = discardTime;
    }

    public void explodeAndDiscard() {
        if (!this.level.isClientSide) {
            if (markEntities)
                markEntities(level);
            if (explosion)
                this.level.explode(this.getOwner(), DamageSource.fireball(this, getOwner()), null, this.getX(), this.getY(), this.getZ(), explosionPower, true, Explosion.BlockInteraction.DESTROY);
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        explodeAndDiscard();
    }

    @Override
    public void tick() {
        if (time++ > discardTime)
            explodeAndDiscard();

        super.tick();
    }

    private void markEntities(Level level) {
        AABB area = getBoundingBox().inflate(3);
        for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, area))
            HerobrineMark.overlapMark(livingEntity);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            entity.hurt(DamageSource.fireball(this, getOwner()), 6.0F);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("ExplosionPower", (byte) this.explosionPower);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("ExplosionPower", 99)) {
            this.explosionPower = pCompound.getByte("ExplosionPower");
        }
    }
}
