package com.lalaalal.yummy.block;

import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class PurifiedSoulFireBlock extends BaseFireBlock {
    public PurifiedSoulFireBlock() {
        super(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_BLACK).noCollission().instabreak().lightLevel((blockState) -> 5).sound(SoundType.WOOL), 2);
    }

    @Override
    protected boolean canBurn(BlockState pState) {
        return true;
    }
}
