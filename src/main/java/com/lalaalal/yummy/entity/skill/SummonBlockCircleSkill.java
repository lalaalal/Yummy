package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SummonBlockCircleSkill extends Skill {
    private final Herobrine herobrine;
    private final Block block;
    private final int limit;
    private final int blockNumPerCircle;
    private int circleNum = 1;
    private final boolean increaseCircle;

    public SummonBlockCircleSkill(Herobrine usingEntity, Block block) {
        this(usingEntity, block, 4, 6, 20 * 6, 0, true);
    }

    public SummonBlockCircleSkill(Herobrine usingEntity, Block block, int limit, int cooldown, boolean increaseCircle) {
        this(usingEntity, block, limit, 6, cooldown, 0, increaseCircle);
    }

    public SummonBlockCircleSkill(Herobrine usingEntity, Block block, int limit, int blockNumPerCircle, int cooldown, int warmup, boolean increaseCircle) {
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
        BlockPos blockPos = usingEntity.getOnPos();
        int radius = 3 * circleNum + circleNum - 1;
        for (int i = 0; i < circleNum * blockNumPerCircle; i++) {
            double t = (2 * Math.PI) / (circleNum * blockNumPerCircle) * i;
            double x = Math.cos(t) * radius + blockPos.getX();
            double z = Math.sin(t) * radius + blockPos.getZ();

            if (0 < t && t < Math.PI)
                z += 1;
            if ((1.5 * Math.PI) < t || t < (0.5 * Math.PI))
                x += 1;
            if (t == Math.PI)
                x -= 1;
            if (t == Math.PI * 1.5)
                z -= 1;

            BlockPos pos = new BlockPos(x, blockPos.getY() + 3, z);
            level.setBlock(pos, block.defaultBlockState(), 10);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
                herobrine.addPollutedBlock(pollutedBlockEntity);
        }
    }
}
