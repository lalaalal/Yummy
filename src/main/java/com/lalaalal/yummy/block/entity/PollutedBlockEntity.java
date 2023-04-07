package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.MarkEffect;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PollutedBlockEntity extends BlockEntity {
    protected int tickInterval = 20 * 10;
    protected int lifetime = 20 * 60;
    protected int tick = 0;
    private Herobrine herobrine;

    public PollutedBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("tick", tick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tick = tag.getInt("tick");
    }

    public void setHerobrine(Herobrine herobrine) {
        this.herobrine = herobrine;
    }

    private void destroyBlock(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
        if (herobrine != null)
            herobrine.removePollutedBlock(this);
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity
                && pollutedBlockEntity.tick++ % pollutedBlockEntity.tickInterval == 0) {
            if (pollutedBlockEntity.tick >= pollutedBlockEntity.lifetime)
                pollutedBlockEntity.destroyBlock(level, blockPos);

            AABB area = YummyUtil.createArea(blockPos, 2);

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                MarkEffect.overlapMark(entity);
            }
        }
    }
}
