package com.lalaalal.yummy.block;

import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PurifiedSoulFireBlock extends BaseFireBlock {
    public PurifiedSoulFireBlock(Properties properties) {
        super(properties, 2);
    }

    @Override
    protected boolean canBurn(BlockState state) {
        return true;
    }
}
