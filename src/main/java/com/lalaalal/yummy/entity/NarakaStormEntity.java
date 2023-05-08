package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.effect.HerobrineMark;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NarakaStormEntity extends FlatImageEntity {
    public static final String IMAGE_NAME = "naraka_storm";
    private static final double ERROR_RANGE = 1;

    private final Set<Entity> markedEntities = new HashSet<>();
    private double initialRadius = 3;
    private double radius = initialRadius;
    private double maxRadius = 25;
    private double speed = 0.4;
    private LivingEntity spawner;

    protected NarakaStormEntity(EntityType<? extends NarakaStormEntity> entityType, Level level) {
        super(entityType, level, 2, IMAGE_NAME);
    }

    public NarakaStormEntity(Level level, int magicCircleRadius, double initialRadius, @Nullable LivingEntity spawner) {
        super(YummyEntities.NARAKA_STORM.get(), level, magicCircleRadius, IMAGE_NAME);
        this.initialRadius = initialRadius;
        this.radius = initialRadius;
        this.spawner = spawner;
        if (spawner != null)
            setPos(spawner.position());
        if (radius > maxRadius)
            maxRadius = radius * 2;
    }

    public void setMaxRadius(double maxRadius) {
        if (maxRadius > radius)
            this.maxRadius = maxRadius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide)
            return;
        radius += speed;
        for (int i = 0; i < 360; i++) {
            double theta = Math.PI / 180 * i;
            double x = Math.cos(theta) * radius + getX();
            double z = Math.sin(theta) * radius + getZ();

            ((ServerLevel) level).sendParticles(ParticleTypes.SMALL_FLAME, x, getY() + 1.5, z, 1, 0, 0, 0, 1);
        }
        List<Entity> entities = level.getEntities(this, getBoundingBox().inflate(radius - 0.5),
                entity -> !markedEntities.contains(entity)
                        && entity.distanceToSqr(this) > initialRadius * initialRadius
                        && entity.distanceTo(this) > radius - ERROR_RANGE);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setSecondsOnFire(6);
                HerobrineMark.overlapMark(livingEntity, spawner);
                markedEntities.add(livingEntity);
            }
        }

        if (radius > maxRadius)
            discard();
    }
}
