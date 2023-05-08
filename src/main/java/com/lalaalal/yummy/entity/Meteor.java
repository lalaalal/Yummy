package com.lalaalal.yummy.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Meteor extends MarkFireball {
    private int tick = 0;
    private int autoFireTime = -1;
    private int rotationDegree = 0;
    private int rotationSpeed = 20;
    private Vec3 power;

    public Meteor(EntityType<? extends MarkFireball> entityType, Level level) {
        super(entityType, level);
    }

    public Meteor(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, boolean markEntities) {
        this(YummyEntities.METEOR.get(), level, shooter, offsetX, offsetY, offsetZ, 1, markEntities);
    }

    public Meteor(EntityType<? extends MarkFireball> entityType, Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ, int explosionPower, boolean markEntities) {
        super(entityType, level, shooter, offsetX, offsetY, offsetZ, explosionPower, markEntities);
        setDeltaMovement(0.01, 0.1, 0.01);
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public int getRotationDegree() {
        return rotationDegree;
    }

    @Override
    public void tick() {
        super.tick();

        if (tick == autoFireTime)
            fire();

        rotationDegree = (rotationDegree + rotationSpeed) % 360;
        tick += 1;
    }

    public void autoFire(Vec3 power, int tickAfter) {
        this.power = power;
        autoFireTime = tick + tickAfter;
    }

    private void fire() {
        setDeltaMovement(Vec3.ZERO);
        this.xPower = power.x;
        this.yPower = power.y;
        this.zPower = power.z;
    }

    @Override
    public Vec3 getDeltaMovement() {
        return super.getDeltaMovement();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Vec3 hitLocation = result.getLocation();
        this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, hitLocation.x, hitLocation.y, hitLocation.z, 0, 0.3, 0);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity hitEntity = result.getEntity();
        hitEntity.hurt(DamageSource.fireball(this, getOwner()), 66f);
    }
}
