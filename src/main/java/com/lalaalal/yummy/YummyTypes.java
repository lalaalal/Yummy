package com.lalaalal.yummy;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class YummyTypes {
    public static final BlockSetType EBONY_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("ebony"));
    public static final WoodType EBONY_WOOD_TYPE = WoodType.register(new WoodType("ebony", EBONY_BLOCK_SET_TYPE));
}
