package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.models.HerobrineEntityModel;
import com.lalaalal.yummy.client.renderer.HerobrineEntityRenderer;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.particle.EchoSilverBlockParticle;
import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YummyClientEventBus {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(YummyParticleRegister.DRIPPING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverHangProvider::new);
        event.register(YummyParticleRegister.FALLING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverFallProvider::new);
        event.register(YummyParticleRegister.LANDING_ECHO_SILVER.get(),
                EchoSilverBlockParticle.EchoSilverLandProvider::new);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YummyEntityRegister.HEROBRINE.get(), HerobrineEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HerobrineEntityModel.LAYER_LOCATION, HerobrineEntityModel::createBodyLayer);
    }
}
