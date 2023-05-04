package com.lalaalal.yummy.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class CameraShakingEntity extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> DATA_CAMERA_SHAKING = SynchedEntityData.defineId(CameraShakingEntity.class, EntityDataSerializers.BOOLEAN);

    protected CameraShakingEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CAMERA_SHAKING, false);
    }

    public void setCameraShaking(boolean value) {
        this.entityData.set(DATA_CAMERA_SHAKING, value);
    }

    public boolean isCameraShaking() {
        return this.entityData.get(DATA_CAMERA_SHAKING);
    }
}
