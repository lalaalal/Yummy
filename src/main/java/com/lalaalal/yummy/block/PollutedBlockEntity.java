package com.lalaalal.yummy.block;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.blockentity.YummyBlockEntityRegister;
import com.lalaalal.yummy.effect.MarkEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PollutedBlockEntity extends BlockEntity {
    public static final long TICK_INTERVAL = 80;
    private long tick = 0;

    public PollutedBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity
                && pollutedBlockEntity.tick++ % TICK_INTERVAL == 0) {
            AABB area = YummyUtil.createArea(blockPos, 2);

            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                MarkEffect.overlapMark(entity);
            }
        }
    }
}
