package com.lalaalal.yummy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class YummyUtil {
    public static AABB createArea(BlockPos blockPos, int range) {
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        BlockPos start = new BlockPos(x - range, y - range, z - range);
        BlockPos end = new BlockPos(x + range + 1, y + range + 1, z + range + 1);

        return new AABB(start, end);
    }

    public static AABB createArea(double x, double y, double z, int range) {
        return createArea(new BlockPos(x, y, z), range);
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
        if (pos.getY() < -64 || pos.getY() > 256)
            return pos;

        if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
            if (!level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP))
                return findHorizonPos(pos.below(), level);
            return pos;
        }
        return findHorizonPos(pos.above(), level);
    }

    public static byte makeUnit(double value) {
        if (value > 0)
            return 1;
        if (value < 0)
            return -1;
        return 0;
    }

    public static Vec3 calcOrthogonal(Vec3 vec3, double angle, double scale) {
        double x = vec3.x * Math.cos(angle) - vec3.z * Math.sin(angle);
        double z = vec3.x * Math.sin(angle) + vec3.z * Math.cos(angle);

        return new Vec3(x * scale, 0, z * scale);
    }
}
