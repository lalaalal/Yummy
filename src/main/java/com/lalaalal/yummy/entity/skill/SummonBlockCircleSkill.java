package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.entity.LegacyHerobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SummonBlockCircleSkill extends Skill {
    private final LegacyHerobrine herobrine;
    private final Block block;
    private final int limit;
    private final int blockNumPerCircle;
    private int circleNum = 1;
    private final boolean increaseCircle;

    public SummonBlockCircleSkill(LegacyHerobrine usingEntity, Block block) {
        this(usingEntity, block, 4, 6, 20 * 6, 0, true);
    }

    public SummonBlockCircleSkill(LegacyHerobrine usingEntity, Block block, int limit, int cooldown, boolean increaseCircle) {
        this(usingEntity, block, limit, 6, cooldown, 0, increaseCircle);
    }

    public SummonBlockCircleSkill(LegacyHerobrine usingEntity, Block block, int limit, int blockNumPerCircle, int cooldown, int warmup, boolean increaseCircle) {
        super(usingEntity, cooldown, warmup);
        this.herobrine = usingEntity;
        this.block = block;
        this.limit = limit;
        this.blockNumPerCircle = blockNumPerCircle;
        this.increaseCircle = increaseCircle;
    }

    @Override
    public boolean canUse() {
        return circleNum <= limit
                && (herobrine.getPhase() == 3 || herobrine.getTarget() != null);
    }

    @Override
    public void useSkill() {
        summonBlock();
        if (increaseCircle)
            circleNum += 1;
    }

    private void summonBlock() {
        Level level = usingEntity.getLevel();
        BlockPos entityPos = usingEntity.getOnPos();
        int radius = 3 * circleNum + circleNum - 1;
        YummyUtil.doCircle(circleNum * blockNumPerCircle, radius, entityPos, blockPos -> {
            level.setBlock(blockPos, block.defaultBlockState(), 10);
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
                herobrine.addPollutedBlock(pollutedBlockEntity);
        });
    }
}
