package com.lalaalal.yummy.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class YummySignBlockEntity extends SignBlockEntity {
    public YummySignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return YummyBlockEntities.YUMMY_SIGN_BLOCK_ENTITY.get();
    }
}
