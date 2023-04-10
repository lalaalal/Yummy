package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
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
import net.minecraft.world.phys.Vec3;

public class MarkFireball extends Fireball {
    protected float explosionPower = 25;

    public MarkFireball(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    public MarkFireball(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
        super(EntityType.FIREBALL, shooter, offsetX, offsetY, offsetZ, level);
    }

    public MarkFireball(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, int explosionPower) {
        super(EntityType.FIREBALL, shooter, offsetX, offsetY, offsetZ, level);
        this.explosionPower = explosionPower;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            Vec3 hitLocation = result.getLocation();
            this.level.explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), explosionPower, true, Explosion.BlockInteraction.DESTROY);
            AABB area = YummyUtil.createArea(hitLocation.x, hitLocation.y, hitLocation.z, 5);
            for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, area))
                HerobrineMark.overlapMark(livingEntity);

            this.discard();
        }
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
