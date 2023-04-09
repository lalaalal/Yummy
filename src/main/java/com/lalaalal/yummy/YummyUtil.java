package com.lalaalal.yummy;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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

    public static BlockPos randomPos(BlockPos pos, int range, RandomSource random) {
        return randomPos(pos.getX(), pos.getY(), pos.getZ(), range, random);
    }

    public static BlockPos randomPos(int x, int y, int z, int range, RandomSource random) {
        int newX = x + random.nextInt(range * 2 + 1) - range;
        int newY = y + random.nextInt(range);
        int newZ = z + random.nextInt(range * 2 + 1) - range;

        return new BlockPos(newX, newY, newZ);
    }

    public static BlockPos findHorizonPos(BlockPos pos, Level level) {
        if (level.getBlockState(pos).is(Blocks.AIR)) {
            if (level.getBlockState(pos.below()).is(Blocks.AIR))
                return findHorizonPos(pos.below(), level);
            return pos;
        }
        return findHorizonPos(pos.above(), level);
    }
}
