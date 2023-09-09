package com.lalaalal.yummy.block;

/**
 *
 * YummyCeilingHangingSign is Ebony hanging sign's entity class.
 *
 * @author GuEunso
 *
 */

import com.lalaalal.yummy.block.entity.YummyHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YummyCeilingHangingSign extends CeilingHangingSignBlock {
    public YummyCeilingHangingSign(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new YummyHangingSignBlockEntity(pos, state);
    }
}
