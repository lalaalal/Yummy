package com.lalaalal.yummy.entity.ai.skill;

import com.lalaalal.yummy.YummyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.block.Blocks;

public class TransformBlockSkill extends TickableSkill {
    public static final String NAME = "none";

    private int radius = 0;

    public TransformBlockSkill(PathfinderMob usingEntity) {
        super(usingEntity, 0, 0, 0);
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean tick(int tick) {
        BlockPos startPos = usingEntity.getOnPos().north(radius).west(radius);
        for (int row = 0; row < radius * 2 + 1; row++) {
            for (int col = 0; col < radius * 2 + 1; col++) {
                if (row == 0 || row == radius - 1 || col == 0 || col == radius) {
                    BlockPos targetPos = YummyUtil.findHorizonPos(startPos.east(col).south(row), level).below();
                    if (usingEntity.getRandom().nextInt(2) == 0)
                        level.setBlock(targetPos, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
                }
            }
        }
        radius += 1;

        return super.tick(tick);
    }

    @Override
    public void interrupt() {

    }
}
