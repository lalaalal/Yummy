package com.lalaalal.yummy.particle;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES
            = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_ECHO_SILVER
            = PARTICLE_TYPES.register(EchoSilverBlockParticle.EchoSilverHangProvider.NAME,
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FALLING_ECHO_SILVER
            = PARTICLE_TYPES.register(EchoSilverBlockParticle.EchoSilverFallProvider.NAME,
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LANDING_ECHO_SILVER
            = PARTICLE_TYPES.register(EchoSilverBlockParticle.EchoSilverLandProvider.NAME,
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> POLLUTED_PARTICLE
            = PARTICLE_TYPES.register(PollutedParticle.PollutedParticleProvider.NAME,
            () -> new SimpleParticleType(true));
}
