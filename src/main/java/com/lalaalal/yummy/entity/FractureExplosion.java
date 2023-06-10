package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.particle.YummyParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FractureExplosion extends Entity {
    private static final int[] FRAME_ORDER = {0, 1, 2, 3, 4, 5, 6, 5, 0};
    private static final int[] FRAME_DURATION = {20, 4, 3, 1, 1, 4, 15, 4, 20};
    private int tick = 0;
    private int currentFrame = 0;
    private int scalePercentage = 100;

    public FractureExplosion(EntityType<? extends FractureExplosion> entityType, Level level) {
        super(entityType, level);
    }

    public FractureExplosion(Level level, int scalePercentage) {
        super(YummyEntities.FRACTURE_EXPLOSION.get(), level);
        this.scalePercentage = scalePercentage;
    }

    public int getCurrentFrame() {
        return FRAME_ORDER[Math.min(FRAME_ORDER.length - 1, currentFrame)];
    }

    public float getScale() {
        return scalePercentage / 100.0f;
    }

    @Override
    public void tick() {
        super.tick();

        if (currentFrame >= FRAME_ORDER.length) {
            level().addParticle(YummyParticles.CORRUPTED_SMALL_FLAME.get(), getX(), getY(), getZ(), 0, 0, 0);
            discard();
            return;
        }
        if (tick < FRAME_DURATION[currentFrame] / 4) {
            tick += 1;
        } else {
            tick = 0;
            currentFrame += 1;
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, scalePercentage);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.scalePercentage = packet.getData();
    }
}
