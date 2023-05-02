package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.effect.HerobrineMark;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class NarakaMagicCircle extends Entity {
    private int radius = 3;
    private int lifetime = 20 * 10;
    private LivingEntity owner;

    public NarakaMagicCircle(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public NarakaMagicCircle(Level level, BlockPos blockPos, LivingEntity owner) {
        super(YummyEntities.NARAKA_MAGIC_CIRCLE.get(), level);
        setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.2, blockPos.getZ() + 0.5);
        this.owner = owner;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void tick() {
        if (lifetime == 0) {
            explode();
            discard();
        }
        lifetime -= 1;
    }

    public void explode() {
        if (!level.isClientSide) {
            AABB area = getBoundingBox().inflate(radius);
            LivingEntity target = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, owner, getX(), getY(), getZ(), area);
            if (target != null && owner != null) {
                target.hurt(new EntityDamageSource("explosion_magic", owner), 166);
                level.explode(owner, getX(), getY(), getZ(), 1, false, Explosion.BlockInteraction.NONE);
                HerobrineMark.overlapMark(target, owner);
            }
            ((ServerLevel) level).sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, radius);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        radius = pPacket.getData();
    }
}
