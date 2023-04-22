package com.lalaalal.yummy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PurifiedSoulFireBlock extends BaseFireBlock {
    public PurifiedSoulFireBlock(Properties properties) {
        super(properties, 2);
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return canSurvive(pState, pLevel, pCurrentPos) ? defaultBlockState() : Blocks.AIR.defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return !level.getBlockState(pos.below()).is(Blocks.AIR);
    }
}
