package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.CameraShakingEntity;
import com.lalaalal.yummy.entity.NarakaMagicCircle;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class ExplosionMagicSkill extends TickableSkill {
    public static final String NAME = "explosion_spell";
    private static final int EXPLODE_TICK = 25;
    private static final int EXPLODE_INTERVAL = 5;
    private BlockPos usingPos = BlockPos.ZERO;

    public ExplosionMagicSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 20, 30);
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = usingEntity.getTarget();

        return livingEntity != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        if (tick == animationDuration)
            usingPos = usingEntity.getOnPos();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        createMagicCircles(tick);

        if (tick == EXPLODE_TICK) {
            if (usingEntity instanceof CameraShakingEntity cameraShakingEntity)
                cameraShakingEntity.setCameraShaking(true);
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        }
        if (tick == tickDuration && usingEntity instanceof CameraShakingEntity cameraShakingEntity)
            cameraShakingEntity.setCameraShaking(false);
        return super.tick(tick);
    }

    @Override
    public void interrupt() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    private void createMagicCircles(int tick) {
        if (tick % EXPLODE_INTERVAL == 0 && tick <= 15) {
            int circleNum = tick / EXPLODE_INTERVAL;
            int firstCircleBlockCount = 6;
            int radius = 3 * circleNum - 1;

            YummyUtil.doCircle(firstCircleBlockCount * circleNum, radius, usingPos,
                    blockPos -> createMagicCircle(blockPos, circleNum));
        }
    }

    private void createMagicCircle(BlockPos blockPos, int circleNum) {
        if (usingEntity.getRandom().nextInt(0, 2) == 0) {
            NarakaMagicCircle narakaMagicCircle = new NarakaMagicCircle(level, YummyUtil.findHorizonPos(blockPos, level), usingEntity);
            narakaMagicCircle.setWidth(1);
            narakaMagicCircle.setExplodeTime(EXPLODE_TICK - (circleNum - 2) * EXPLODE_INTERVAL);
            level.addFreshEntity(narakaMagicCircle);
        }
    }
}
