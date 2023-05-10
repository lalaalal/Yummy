package com.lalaalal.yummy.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AnimatedImageEntity extends Entity {
    private int tick;

    protected AnimatedImageEntity(EntityType<? extends AnimatedImageEntity> entityType, Level level) {
        super(entityType, level);
    }

    public abstract int getCurrentTextureIndex();

    @Override
    public void tick() {


        tick += 1;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains("AnimationTick"))
            tick = compoundTag.getInt("AnimationTick");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("AnimationTick", tick);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
