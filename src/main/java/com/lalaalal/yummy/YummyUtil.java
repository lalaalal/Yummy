package com.lalaalal.yummy;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class YummyUtil {
    public static BlockPos blockPos(double x, double y, double z) {
        return new BlockPos((int) x, (int) y, (int) z);
    }

    public static BlockPos blockPos(Vec3 vec3) {
        return blockPos(vec3.x, vec3.y, vec3.z);
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

    public static Vec3 calcXZRotation(Vec3 vec3, double angle, double scale) {
        double x = vec3.x * Math.cos(angle) - vec3.z * Math.sin(angle);
        double z = vec3.x * Math.sin(angle) + vec3.z * Math.cos(angle);

        return new Vec3(x * scale, vec3.y, z * scale);
    }

    public static void doCircle(int count, int radius, BlockPos basePos, Consumer<BlockPos> consumer) {
        for (int i = 0; i < count; i++) {
            double t = (2 * Math.PI) / count * i;
            double x = Math.cos(t) * radius + basePos.getX();
            double z = Math.sin(t) * radius + basePos.getZ();

            if (0 < t && t < Math.PI)
                z += 1;
            if ((1.5 * Math.PI) < t || t < (0.5 * Math.PI))
                x += 1;
            if (t == Math.PI)
                x -= 1;
            if (t == Math.PI * 1.5)
                z -= 1;

            consumer.accept(new BlockPos((int) x, basePos.getY(), (int) z));
        }
    }

    public static void saveBlockPos(CompoundTag compoundTag, String name, BlockPos blockPos) {
        compoundTag.putInt(name + "X", blockPos.getX());
        compoundTag.putInt(name + "Y", blockPos.getY());
        compoundTag.putInt(name + "Z", blockPos.getZ());
    }

    @Nullable
    public static BlockPos readBlockPos(CompoundTag compoundTag, String name) {
        if (compoundTag.contains(name + "X")
                && compoundTag.contains(name + "Y")
                && compoundTag.contains(name + "Z")) {
            int x = compoundTag.getInt(name + "X");
            int y = compoundTag.getInt(name + "Y");
            int z = compoundTag.getInt(name + "Z");
            return new BlockPos(x, y, z);
        }

        return null;
    }

    public static void vertex(VertexConsumer vertexConsumer, Matrix4f pose, Matrix3f normal, float poseX, float poseY, float poseZ, float u, float v, float normalX, float normalY, float normalZ, int packedLight) {
        vertexConsumer.vertex(pose, poseX, poseY, poseZ)
                .color(255, 255, 255, 255)
                .uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight)
                .normal(normal, normalX, normalY, normalZ).endVertex();
    }
}
