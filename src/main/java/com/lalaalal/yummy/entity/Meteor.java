package com.lalaalal.yummy.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Meteor extends MarkFireball {

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
}
