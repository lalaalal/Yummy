package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SummonPollutedBlockSkill extends Skill {
    public static final int COOLDOWN = 300;

    public SummonPollutedBlockSkill(Mob usingEntity) {
        this(usingEntity, COOLDOWN);
    }

    public SummonPollutedBlockSkill(Mob usingEntity, int cooldown) {
        super(usingEntity, cooldown);
    }

    @Override
    public boolean canUse() {
        if (usingEntity instanceof Herobrine herobrine)
            return herobrine.canSummonPollutedBlock();
        return true;
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        BlockPos pos = YummyUtil.randomPos(usingEntity.getOnPos(), 5, usingEntity.getRandom());
        BlockPos fixedPos = YummyUtil.findHorizonPos(pos, level);
        level.setBlock(fixedPos, YummyBlockRegister.POLLUTED_BLOCK.get().defaultBlockState(), 3);
        BlockEntity blockEntity = level.getBlockEntity(fixedPos);

        if (usingEntity instanceof Herobrine herobrine) {
            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity) {
                herobrine.addPollutedBlock(pollutedBlockEntity);
            }
        }
    }
}
