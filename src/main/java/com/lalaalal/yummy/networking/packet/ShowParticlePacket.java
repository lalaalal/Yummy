package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ShowParticlePacket extends PositionPacket {
    private final String particleNamespace;
    private final String particleName;
    private final int particleCount;
    private final double range;
    private final double xSpeed;
    private final double ySpeed;
    private final double zSpeed;

    protected ShowParticlePacket(double x, double y, double z, String particleNamespace, String particleName, int particleCount, double range,
                                 double xSpeed, double ySpeed, double zSpeed) {
        super(x, y, z);
        this.particleNamespace = particleNamespace;
        this.particleName = particleName;
        this.particleCount = particleCount;
        this.range = range;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public ShowParticlePacket(FriendlyByteBuf buf) {
        super(buf);
        particleNamespace = new String(buf.readByteArray());
        particleName = new String(buf.readByteArray());
        particleCount = buf.readInt();
        range = buf.readDouble();
        xSpeed = buf.readDouble();
        ySpeed = buf.readDouble();
        zSpeed = buf.readDouble();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        super.encode(buf);
        buf.writeByteArray(particleNamespace.getBytes())
                .writeByteArray(particleName.getBytes())
                .writeInt(particleCount)
                .writeDouble(range)
                .writeDouble(xSpeed)
                .writeDouble(ySpeed)
                .writeDouble(zSpeed);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleWork(NetworkEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            Level level = Minecraft.getInstance().level;
            if (level == null)
                return;
            RandomSource random = level.random;
            for (int i = 0; i < particleCount; i++) {
                double particleX = x + random.nextDouble() * (range * 2 + 1) - range;
                double particleY = y + random.nextDouble() * 0.2;
                double particleZ = z + random.nextDouble() * (range * 2 + 1) - range;
                level.addParticle(getParticle(), particleX, particleY, particleZ, xSpeed, ySpeed, zSpeed);
            }
        });
    }

    private ParticleOptions getParticle() {
        RegistryObject<SimpleParticleType> particleRegistryObject
                = RegistryObject.create(new ResourceLocation(particleNamespace, particleName), ForgeRegistries.PARTICLE_TYPES);

        return particleRegistryObject.get();
    }

    public static class Builder {
        private String particleNamespace = YummyMod.MOD_ID;
        private final String particleName;
        private int particleCount = 1;
        private double x;
        private double y;
        private double z;
        private double range = 0;
        private double xSpeed;
        private double ySpeed;
        private double zSpeed;

        public Builder(String particleName) {
            this.particleName = particleName;
        }

        public Builder setXYZ(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;

            return this;
        }

        public Builder setParticleNamespace(String namespace) {
            this.particleNamespace = namespace;

            return this;
        }

        public Builder setXYZ(BlockPos blockPos) {
            return this.setXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public Builder setParticleCount(int count) {
            this.particleCount = count;

            return this;
        }

        public Builder setRange(int range) {
            this.range = range;

            return this;
        }

        public Builder setSpeed(double xSpeed, double ySpeed, double zSpeed) {
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.zSpeed = zSpeed;

            return this;
        }

        public ShowParticlePacket build() {
            return new ShowParticlePacket(x, y, z, particleNamespace, particleName, particleCount, range, xSpeed, ySpeed, zSpeed);
        }
    }
}
