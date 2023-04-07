package com.lalaalal.yummy.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EchoSilverBlockParticle extends TextureSheetParticle {
    private final Fluid type;
    protected boolean isGlowing;

    protected EchoSilverBlockParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType) {
        super(pLevel, pX, pY, pZ);
        this.setSize(0.01F, 0.01F);
        this.setColor(0.2f, 0.2f, 0.3f);
        this.gravity = 0.06F;
        this.type = pType;
    }

    protected Fluid getType() {
        return this.type;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float pPartialTick) {
        return this.isGlowing ? 240 : super.getLightColor(pPartialTick);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
                BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidstate = this.level.getFluidState(blockpos);
                if (fluidstate.getType() == this.type && this.y < (double) ((float) blockpos.getY() + fluidstate.getHeight(this.level, blockpos))) {
                    this.remove();
                }

            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }

    }

    protected void postMoveUpdate() {
    }

    @OnlyIn(Dist.CLIENT)
    static class DripHangParticle extends EchoSilverBlockParticle {
        private final ParticleOptions fallingParticle;

        DripHangParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, ParticleOptions pFallingParticle) {
            super(pLevel, pX, pY, pZ, pType);
            this.fallingParticle = pFallingParticle;
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        protected void preMoveUpdate() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }

        }

        protected void postMoveUpdate() {
            this.xd *= 0.02D;
            this.yd *= 0.02D;
            this.zd *= 0.02D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class DripLandParticle extends EchoSilverBlockParticle {
        DripLandParticle(ClientLevel p_106102_, double p_106103_, double p_106104_, double p_106105_, Fluid p_106106_) {
            super(p_106102_, p_106103_, p_106104_, p_106105_, p_106106_);
            this.lifetime = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallAndLandParticle extends EchoSilverBlockParticle.FallingParticle {
        protected final ParticleOptions landParticle;

        FallAndLandParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, ParticleOptions pLandParticle) {
            super(pLevel, pX, pY, pZ, pType);
            this.landParticle = pLandParticle;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingParticle extends EchoSilverBlockParticle {
        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType) {
            this(pLevel, pX, pY, pZ, pType, (int) (64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        FallingParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType, int pLifetime) {
            super(pLevel, pX, pY, pZ, pType);
            this.lifetime = pLifetime;
        }

        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class EchoSilverFallProvider implements ParticleProvider<SimpleParticleType> {
        public static final String NAME = "falling_echo_silver";

        protected final SpriteSet sprite;

        public EchoSilverFallProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel,
                                       double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            EchoSilverBlockParticle fallAndLandParticle = new EchoSilverBlockParticle.FallAndLandParticle(pLevel, pX, pY, pZ, Fluids.EMPTY, YummyParticleRegister.LANDING_ECHO_SILVER.get());
            fallAndLandParticle.isGlowing = true;
            fallAndLandParticle.gravity = 0.01F;
            fallAndLandParticle.pickSprite(this.sprite);
            return fallAndLandParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class EchoSilverHangProvider implements ParticleProvider<SimpleParticleType> {
        public static final String NAME = "dripping_echo_silver";

        protected final SpriteSet sprite;

        public EchoSilverHangProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel,
                                       double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            EchoSilverBlockParticle.DripHangParticle dripHangParticle = new EchoSilverBlockParticle.DripHangParticle(pLevel, pX, pY, pZ, Fluids.EMPTY, YummyParticleRegister.FALLING_ECHO_SILVER.get());
            dripHangParticle.isGlowing = true;
            dripHangParticle.gravity *= 0.01F;
            dripHangParticle.lifetime = 100;
            dripHangParticle.pickSprite(this.sprite);
            return dripHangParticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class EchoSilverLandProvider implements ParticleProvider<SimpleParticleType> {
        public static final String NAME = "landing_echo_silver";

        protected final SpriteSet sprite;

        public EchoSilverLandProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel,
                                       double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            EchoSilverBlockParticle dripLandParticle = new EchoSilverBlockParticle.DripLandParticle(pLevel, pX, pY, pZ, Fluids.EMPTY);
            dripLandParticle.isGlowing = true;
            dripLandParticle.lifetime = (int) (28.0D / (Math.random() * 0.8D + 0.2D));
            dripLandParticle.pickSprite(this.sprite);
            return dripLandParticle;
        }
    }
}
