package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.NarakaMagicCircle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

import java.util.ArrayList;

public class ExplosionMagicSkill extends TickableSkill {
    private BlockPos usingPos = BlockPos.ZERO;
    private final ArrayList<NarakaMagicCircle> narakaMagicCircles = new ArrayList<>();

    public ExplosionMagicSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 20, 80);
    }

    @Override
    public boolean canUse() {
        LivingEntity livingEntity = usingEntity.getTarget();

        return livingEntity != null && usingEntity.distanceToSqr(livingEntity) < 100;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == animationDuration)
            usingPos = usingEntity.getOnPos();

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        createMagicCircles(tick);

        if (tick == tickDuration)
            explodeMagicCircles();
        return super.tick(tick);
    }

    private void createMagicCircles(int tick) {
        if (tick % 10 == 0 && tick <= 60) {
            int circleNum = tick / 10;
            int firstCircleBlockCount = 6;
            int radius = 3 * circleNum - 1;

            YummyUtil.doCircle(firstCircleBlockCount * circleNum, radius, usingPos, this::createMagicCircle);
        }
    }

    private void createMagicCircle(BlockPos blockPos) {
        if (usingEntity.getRandom().nextInt(0, 3) == 0) {
            NarakaMagicCircle narakaMagicCircle = new NarakaMagicCircle(level, YummyUtil.findHorizonPos(blockPos, level), usingEntity);
            narakaMagicCircle.setRadius(1);
            level.addFreshEntity(narakaMagicCircle);
            narakaMagicCircles.add(narakaMagicCircle);
        }
    }

    private void explodeMagicCircles() {
        for (NarakaMagicCircle narakaMagicCircle : narakaMagicCircles) {
            narakaMagicCircle.explode();
            narakaMagicCircle.discard();
        }
        narakaMagicCircles.clear();
    }
}
