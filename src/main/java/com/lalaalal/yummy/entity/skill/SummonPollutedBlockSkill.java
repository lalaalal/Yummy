package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlockRegister;
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

    public SummonPollutedBlockSkill(Herobrine herobrine) {
        this(herobrine, COOLDOWN);
    }

    public SummonPollutedBlockSkill(Herobrine herobrine, int cooldown) {
        super(herobrine, cooldown, WARMUP);
        this.herobrine = herobrine;
    }

    @Override
    public boolean canUse() {
        return herobrine.canSummonPollutedBlock();
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        for (int i = 0; i < 6 && herobrine.canSummonPollutedBlock(); i++) {
            BlockPos pos = YummyUtil.randomPos(usingEntity.getOnPos(), 5, usingEntity.getRandom());
            BlockPos fixedPos = YummyUtil.findHorizonPos(pos, level);
            level.setBlock(fixedPos, getPollutedBlockState(), 10);
            BlockEntity blockEntity = level.getBlockEntity(fixedPos);

            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
                herobrine.addPollutedBlock(pollutedBlockEntity);
        }
    }

    private BlockState getPollutedBlockState() {
        if (herobrine.getPhase() >= 3)
            return YummyBlockRegister.CORRUPTED_POLLUTED_BLOCK.get().defaultBlockState();
        return YummyBlockRegister.POLLUTED_BLOCK.get().defaultBlockState();
    }
}
