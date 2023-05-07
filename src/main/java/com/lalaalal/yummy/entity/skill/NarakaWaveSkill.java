package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.CameraShakingEntity;
import com.lalaalal.yummy.entity.FloatingBlockEntity;
import com.lalaalal.yummy.entity.TransformingBlockEntity;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NarakaWaveSkill extends TickableSkill {
    public static final int WAVE_DURATION = 8;
    public static final Vec3 TRANSFORMING_BLOCK_VELOCITY = new Vec3(0, 0.8, 0);
    public static final Vec3 FLOATING_BLOCK_VELOCITY = new Vec3(0, 0.6, 0);
    private final int maxTransform = level.random.nextInt(6, 13);
    private int transformed = 0;
    private boolean prevTransformed = false;

    private BlockPos usingPos;

    public NarakaWaveSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 10, 30);
    }

    @Override
    public String getBaseName() {
        return "naraka_wave";
    }

    protected void prepare() {
        transformed = 0;
        usingPos = usingEntity.getOnPos();
    }

    @Override
    public boolean canUse() {
        LivingEntity target = usingEntity.getTarget();
        return target != null && usingEntity.distanceToSqr(target) < 100;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        if (tick == animationDuration)
            prepare();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        shakeEntities(tick);
        if (tick == WAVE_DURATION)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        if (tick > WAVE_DURATION)
            return super.tick(tick);

        floatBlock(tick);
        return false;
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    private void shakeEntities(int tick) {
        if (usingEntity instanceof CameraShakingEntity cameraShakingEntity) {
            if (tick == 0)
                cameraShakingEntity.setCameraShaking(true);
            if (tick == WAVE_DURATION)
                cameraShakingEntity.setCameraShaking(false);
        }

        AABB area = usingEntity.getBoundingBox().inflate(15);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities)
            entity.hurt(new EntityDamageSource("naraka_wave", usingEntity), 1);
    }

    private void floatBlock(int tick) {
        int blockNumPerCircle = 6;
        int radius = 2 * tick + 1;
        prevTransformed = false;
        YummyUtil.doCircle(tick * blockNumPerCircle, radius, usingPos, blockPos -> {
            BlockPos floatingBlockPos = YummyUtil.findHorizonPos(blockPos, level).below();
            BlockState state = level.getBlockState(floatingBlockPos);
            if (transformed < maxTransform
                    && !prevTransformed
                    && (tick == 2 || tick == 4)
                    && level.random.nextInt(3) == 0) {
                if (TransformingBlockEntity.floatAndTransformBlock(level, floatingBlockPos, TRANSFORMING_BLOCK_VELOCITY, state, YummyBlocks.POLLUTED_BLOCK.get().defaultBlockState()) != null) {
                    transformed += 1;
                    prevTransformed = true;
                }
            } else {
                FloatingBlockEntity.floatBlock(level, floatingBlockPos, FLOATING_BLOCK_VELOCITY, FloatingBlockEntity.DEFAULT_ACCELERATION, state);
                prevTransformed = false;
            }
        });
    }
}
