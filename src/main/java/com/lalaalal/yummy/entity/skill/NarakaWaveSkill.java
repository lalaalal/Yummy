package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.FloatingBlockEntity;
import com.lalaalal.yummy.entity.TransformingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NarakaWaveSkill extends TickableSkill {
    public static final int SKILL_DURATION = 8;
    public static final Vec3 MOTION_MULTIPLIER = new Vec3(0.1, 0.1, 0.1);
    public static final Vec3 TRANSFORMING_BLOCK_VELOCITY = new Vec3(0, 0.8, 0);
    public static final Vec3 FLOATING_BLOCK_VELOCITY = new Vec3(0, 0.6, 0);
    private final int maxTransform = level.random.nextInt(6, 13);
    private int transformed = 0;

    private BlockPos usingPos;

    public NarakaWaveSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 10, 20);
    }

    protected void prepare() {
        transformed = 0;
        usingPos = usingEntity.getOnPos();
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == animationDuration)
            prepare();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        shakeEntities(tick);
        if (tick > SKILL_DURATION)
            return super.tick(tick);

        floatBlock(tick);
        return false;
    }

    private void shakeEntities(int tick) {
        AABB area = usingEntity.getBoundingBox().inflate(15);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities) {
            Vec3 viewVector = entity.getViewVector(1);
            Vec3 deltaMovement = YummyUtil.calcOrthogonal(viewVector, Math.PI * (tick % 2 == 0 ? 0.5 : -0.5), 0.2);
            entity.setDeltaMovement(deltaMovement);
            entity.makeStuckInBlock(level.getBlockState(entity.getOnPos().above()), MOTION_MULTIPLIER);
            entity.move(MoverType.SELF, deltaMovement);
            if (entity instanceof ServerPlayer serverPlayer)
                serverPlayer.setShiftKeyDown(tick % 2 == 0);
            entity.hurt(new EntityDamageSource("naraka_wave", usingEntity), 1);
        }
    }

    private void floatBlock(int tick) {
        int blockNumPerCircle = 6;
        int radius = 2 * tick + 1;
        boolean prevTransformed = false;
        for (int i = 0; i < tick * blockNumPerCircle; i++) {
            double t = (2 * Math.PI) / (tick * blockNumPerCircle) * i;
            double x = Math.cos(t) * radius + usingPos.getX();
            double z = Math.sin(t) * radius + usingPos.getZ();

            if (0 < t && t < Math.PI)
                z += 1;
            if ((1.5 * Math.PI) < t || t < (0.5 * Math.PI))
                x += 1;
            if (t == Math.PI)
                x -= 1;
            if (t == Math.PI * 1.5)
                z -= 1;

            BlockPos pos = YummyUtil.findHorizonPos(new BlockPos(x, usingPos.getY(), z), level).below();
            BlockState state = level.getBlockState(pos);
            if (transformed < maxTransform
                    && !prevTransformed
                    && (tick == 2 || tick == 4)
                    && level.random.nextInt(4) == 0) {
                if (TransformingBlockEntity.floatAndTransformBlock(level, pos, TRANSFORMING_BLOCK_VELOCITY, state, YummyBlocks.POLLUTED_BLOCK.get().defaultBlockState()) != null) {
                    transformed += 1;
                    prevTransformed = true;
                }
            } else {
                FloatingBlockEntity.floatBlock(level, pos, FLOATING_BLOCK_VELOCITY, FloatingBlockEntity.DEFAULT_ACCELERATION, state);
                prevTransformed = false;
            }
        }
    }
}
