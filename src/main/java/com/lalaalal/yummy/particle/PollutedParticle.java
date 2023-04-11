package com.lalaalal.yummy.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PollutedParticle extends TextureSheetParticle {

    protected PollutedParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.gravity = 0;
        this.xd = 0;
        this.yd = (this.random.nextDouble() * 2 + 1) * 0.01 * ySpeed;
        this.zd = 0;
        double lifetime = (240 - this.random.nextInt(0, 40) * ySpeed) / Math.max(ySpeed, 1);
        setLifetime(Math.max((int) lifetime, 40));
    }

    @NotNull
    @Override
    public Particle scale(float pScale) {
        return this;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return super.getLightColor(240);
    }

    @NotNull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class PollutedParticleProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;
        private final float r;
        private final float g;
        private final float b;

        public PollutedParticleProvider(SpriteSet spriteSet, float r, float g, float b) {
            this.spriteSet = spriteSet;
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public PollutedParticleProvider(SpriteSet spriteSet, int color) {
            this(spriteSet, ((color & 0xFF0000) >> 16) / 255f, ((color & 0x00FF00) >> 8) / 255f, (color & 0x0000FF) / 255f);
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double pY, double pZ, double xSpeed, double ySpeed, double zSpeed) {
            PollutedParticle particle = new PollutedParticle(level, x, pY, pZ, xSpeed, ySpeed, zSpeed);
            particle.setSpriteFromAge(spriteSet);
            particle.pickSprite(spriteSet);
            particle.setColor(r, g, b);

            return particle;
        }
    }
}
