package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SummonPollutedBlockSkill extends Skill {
    public static final int COOLDOWN = 300;
    public static final int WARMUP = 20;
    private final Herobrine herobrine;
    private final int range;

    public SummonPollutedBlockSkill(Herobrine herobrine) {
        this(herobrine, 5);
    }

    public SummonPollutedBlockSkill(Herobrine herobrine, int range) {
        super(herobrine, COOLDOWN, WARMUP);
        this.herobrine = herobrine;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        return herobrine.canSummonPollutedBlock();
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        while (herobrine.canSummonPollutedBlock()) {
            BlockPos pos = YummyUtil.randomPos(usingEntity.getOnPos(), range, usingEntity.getRandom());
            BlockPos fixedPos = YummyUtil.findHorizonPos(pos, level);
            level.setBlock(fixedPos, getPollutedBlockState(), 10);
            BlockEntity blockEntity = level.getBlockEntity(fixedPos);

            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
                herobrine.addPollutedBlock(pollutedBlockEntity);
        }
    }

    private BlockState getPollutedBlockState() {
        if (herobrine.getPhase() >= 3)
            return YummyBlocks.CORRUPTED_POLLUTED_BLOCK.get().defaultBlockState();
        return YummyBlocks.POLLUTED_BLOCK.get().defaultBlockState();
    }
}
