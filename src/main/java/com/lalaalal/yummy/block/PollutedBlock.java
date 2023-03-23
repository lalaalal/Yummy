package com.lalaalal.yummy.block;

import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.block.entity.YummyBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PollutedBlock extends BaseEntityBlock {
    public static final String NAME = "polluted_block";


    public PollutedBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .strength(2.7f, 14f)
                .requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PollutedBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get() ? PollutedBlockEntity::tick : null;
    }
}
