package com.lalaalal.yummy.block.entity;
/**
 *
 * YummyHangingSignBlockEntity is Ebony hanging sign's Entity class
 * @author GuEunso
 *
 */

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YummyHangingSignBlockEntity extends HangingSignBlockEntity {
    public YummyHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return YummyBlockEntities.YUMMY_HANGING_SIGN_BLOCK_ENTITY.get();
    }
}
