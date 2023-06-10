package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class NarakaMagicCircle extends FlatImageEntity {
    private static final String IMAGE_NAME = "magic_circle";
    private int explodeTime = 20 * 10;
    private int tick = 0;
    private double deltaRotation = 360 / 24.0;
    private LivingEntity spawner;

    protected NarakaMagicCircle(EntityType<? extends NarakaMagicCircle> entityType, Level level) {
        super(entityType, level, 3, IMAGE_NAME);
    }

    public NarakaMagicCircle(Level level, BlockPos blockPos, LivingEntity spawner) {
        super(YummyEntities.NARAKA_MAGIC_CIRCLE.get(), level, 3, IMAGE_NAME);
        setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5);
        this.spawner = spawner;
    }

    public void setExplodeTime(int explodeTime) {
        this.explodeTime = explodeTime;
    }

    @Override
    public void tick() {
        rotationDegree += deltaRotation;
        if (tick % 24 == 0)
            deltaRotation += 5;

        if (tick == explodeTime) {
            explode();
            discard();
        }
        tick += 1;
    }

    public void explode() {
        if (!level().isClientSide) {
            AABB area = getBoundingBox().inflate(width);
            LivingEntity target = level().getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, spawner, getX(), getY(), getZ(), area);
            if (target != null && spawner != null) {
                level().explode(spawner, getX(), getY(), getZ(), 1, false, Level.ExplosionInteraction.NONE);
                target.hurt(YummyDamageSources.simple(level(), YummyMod.MOD_ID + ".naraka_magic", this, spawner), 166);
                HerobrineMark.overlapMark(target, spawner);
            }
            ((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION_EMITTER, getX(), getY(), getZ(), 1, 0, 0, 0, 1);
        }
    }
}
