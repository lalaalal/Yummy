package com.lalaalal.yummy.block;

import com.lalaalal.yummy.block.entity.YummyHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class EbonyWallHangingSign extends WallHangingSignBlock {
    public EbonyWallHangingSign(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new YummyHangingSignBlockEntity(pos, state);
    }
}
