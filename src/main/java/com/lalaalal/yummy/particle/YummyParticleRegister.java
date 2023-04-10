package com.lalaalal.yummy.particle;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyParticleRegister {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES
            = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> POLLUTED_PARTICLE_RED
            = PARTICLE_TYPES.register("polluted_particle_red",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> POLLUTED_PARTICLE_BLUE
            = PARTICLE_TYPES.register("polluted_particle_blue",
            () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> POLLUTED_PARTICLE_PURPLE
            = PARTICLE_TYPES.register("polluted_particle_purple",
            () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
