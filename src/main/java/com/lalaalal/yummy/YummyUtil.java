package com.lalaalal.yummy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class YummyUtil {
    public static AABB createArea(BlockPos blockPos, int offset) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        BlockPos start = new BlockPos(x - offset, y - offset, z - offset);
        BlockPos end = new BlockPos(x + offset, y + offset, z + offset);

        return new AABB(start, end);
    }
}
