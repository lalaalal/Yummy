package com.lalaalal.yummy.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FractureEntity extends Entity {
    public static final int SHAPE_VARIETY = 2;
    private int tick = 0;
    private int lifetime = -1;
    private LivingEntity spawner;
    public final int shape;

    protected FractureEntity(EntityType<? extends FractureEntity> entityType, Level level) {
        super(entityType, level);
        shape = 0;
    }

    public FractureEntity(Level level, Vec3 pos) {
        super(YummyEntities.FRACTURE_ENTITY.get(), level);
        this.setPos(pos);
        shape = level.getRandom().nextInt(SHAPE_VARIETY);
    }

    public void setSpawner(LivingEntity spawner) {
        this.spawner = spawner;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public void tick() {
        if (tick == lifetime && !level.isClientSide) {
            level.explode(this, null, null, getX(), getY(), getZ(), 1, false, Explosion.BlockInteraction.NONE);
            if (spawner != null) {
                List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, spawner, getBoundingBox().inflate(2));
                for (LivingEntity entity : entities)
                    entity.hurt(new IndirectEntityDamageSource("fracture_explosion", this, spawner), 166);
            }

            discard();
        }
        tick += 1;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
