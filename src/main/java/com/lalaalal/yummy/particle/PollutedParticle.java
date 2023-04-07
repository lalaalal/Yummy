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
        this.yd = this.random.nextInt(1, 3) * 0.01;
        this.zd = 0;
        setLifetime(240);
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
        public static final String NAME = "polluted_particle";

        protected final SpriteSet spriteSet;

        public PollutedParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double pY, double pZ, double xSpeed, double ySpeed, double zSpeed) {
            PollutedParticle particle = new PollutedParticle(level, x, pY, pZ, xSpeed, ySpeed, zSpeed);
            particle.setSpriteFromAge(spriteSet);
            particle.pickSprite(spriteSet);

            return particle;
        }
    }
}
