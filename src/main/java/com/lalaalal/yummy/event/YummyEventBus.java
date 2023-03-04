package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.particle.EchoSilverBlockParticle;
import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YummyEventBus {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(YummyParticleRegister.DRIPPING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverHangProvider::new);
        event.register(YummyParticleRegister.FALLING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverFallProvider::new);
        event.register(YummyParticleRegister.LANDING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverLandProvider::new);
    }
}
