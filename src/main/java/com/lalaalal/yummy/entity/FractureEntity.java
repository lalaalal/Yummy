package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
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
    private static final double SCALE = 3;
    private int tick = 0;
    private int lifetime = 100;
    private LivingEntity spawner;
    private int rotateDegree = 25;
    private double width;
    private double height;
    public final int shape;

    protected FractureEntity(EntityType<? extends FractureEntity> entityType, Level level) {
        super(entityType, level);
        shape = level.getRandom().nextInt(SHAPE_VARIETY);
        setRotateDegree(75);
    }

    public FractureEntity(Level level, Vec3 pos, int rotateDegree) {
        super(YummyEntities.FRACTURE_ENTITY.get(), level);
        this.setPos(pos);
        shape = level.getRandom().nextInt(SHAPE_VARIETY);
        setRotateDegree(rotateDegree);
    }

    public void setRotateDegree(int rotateDegree) {
        this.rotateDegree = rotateDegree;
        double rotate = rotateDegree * Math.PI / 180;
        this.width = Math.sin(rotate) * SCALE * 1.8;
        this.height = Math.cos(rotate) * SCALE;
    }

    public int getRotateDegree() {
        return rotateDegree;
    }

    public void setSpawner(LivingEntity spawner) {
        this.spawner = spawner;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = Math.max(lifetime, 10);
    }

    @Override
    public void tick() {
        if (tick >= lifetime - 5 && !level.isClientSide) {
            double t = tick - lifetime + 5.0;
            double currentWidth = (width / 5.0) * t;
            double angle = (90 + getYRot()) * Math.PI / 180;
            double x = Math.cos(angle) * currentWidth;
            double z = Math.sin(angle) * currentWidth;
            double y = (height / 5.0) * t;
            double explosionScale = 1 + Math.sin((Math.PI / 5) * t);
            FractureExplosion explosion = new FractureExplosion(level, (int) (explosionScale * 50));
            explosion.setPos(new Vec3(getX() + x, getY() + y, getZ() + z));
            level.addFreshEntity(explosion);
        }
        if (tick == lifetime && !level.isClientSide) {
            level.explode(this, null, null, getX(), getY(), getZ(), 1, false, Explosion.BlockInteraction.NONE);
            if (spawner != null) {
                List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, spawner, getBoundingBox().inflate(0, 2, 2));
                for (LivingEntity entity : entities)
                    entity.hurt(new IndirectEntityDamageSource(YummyMod.MOD_ID + ".fracture", this, spawner), 166);
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
        return new ClientboundAddEntityPacket(this, rotateDegree);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.rotateDegree = packet.getData();
    }
}
