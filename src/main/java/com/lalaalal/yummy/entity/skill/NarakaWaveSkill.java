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
    public static final int ANIMATION_DURATION = 10;
    public static final int SKILL_DURATION = 8;
    public static final int SKILL_END_TICK = 20;

    private BlockPos usingPos;

    public NarakaWaveSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown);
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == ANIMATION_DURATION)
            usingPos = usingEntity.getOnPos();

        return tick > ANIMATION_DURATION;
    }

    @Override
    public boolean tick(int tick) {
        shakeEntities(tick);
        if (tick > SKILL_DURATION)
            return tick > SKILL_END_TICK;

        floatBlock(tick);
        return false;
    }

    private void shakeEntities(int tick) {
        AABB area = usingEntity.getBoundingBox().inflate(15);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities) {
            Vec3 viewVector = entity.getViewVector(1);
            Vec3 deltaMovement = calcOrthogonal(viewVector, Math.PI * (tick % 2 == 0 ? 0.5 : -0.5), 0.2);
            entity.setDeltaMovement(deltaMovement);
            entity.makeStuckInBlock(level.getBlockState(entity.getOnPos().above()), new Vec3(0.2, 1, 0.2));
            entity.move(MoverType.SELF, deltaMovement);
            if (entity instanceof ServerPlayer serverPlayer)
                serverPlayer.setShiftKeyDown(tick % 2 == 0);
            entity.hurt(new EntityDamageSource("naraka_wave", usingEntity), 1);
        }
    }

    private Vec3 calcOrthogonal(Vec3 vec3, double angle, double scale) {
        double x = vec3.x * Math.cos(angle) - vec3.z * Math.sin(angle);
        double z = vec3.x * Math.sin(angle) + vec3.z * Math.cos(angle);

        return new Vec3(x * scale, 0, z * scale);
    }

    private void floatBlock(int tick) {
        int blockNumPerCircle = 8;
        int radius = 2 * tick + 1;
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
            if ((tick == 2 || tick == 4) && i % (tick) == 0)
                TransformingBlockEntity.floatAndTransformBlock(level, pos, new Vec3(0, 0.8, 0), state, YummyBlocks.POLLUTED_BLOCK.get().defaultBlockState());
            else
                FloatingBlockEntity.floatBlock(level, pos, new Vec3(0, 0.6, 0), FloatingBlockEntity.DEFAULT_ACCELERATION, state);
        }
    }
}
