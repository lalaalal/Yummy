package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SummonPollutedBlockSkill extends Skill {
    public static final int COOLDOWN = 300;
    public static final int WARMUP = 40;
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
        BlockPos pos = YummyUtil.randomPos(usingEntity.getOnPos(), 5, usingEntity.getRandom());
        BlockPos fixedPos = YummyUtil.findHorizonPos(pos, level);
        level.setBlock(fixedPos, YummyBlockRegister.POLLUTED_BLOCK.get().defaultBlockState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(fixedPos);

        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
            herobrine.addPollutedBlock(pollutedBlockEntity);
    }
}
