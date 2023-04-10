package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.HerobrineModel;
import com.lalaalal.yummy.client.model.ThrownSpearOfLonginusModel;
import com.lalaalal.yummy.client.renderer.HerobrineRenderer;
import com.lalaalal.yummy.client.renderer.ThrownSpearOfLonginusRenderer;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.particle.PollutedParticle;
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
        event.register(YummyParticleRegister.POLLUTED_PARTICLE_RED.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x441f0b));
        event.register(YummyParticleRegister.POLLUTED_PARTICLE_BLUE.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x019ea3));
        event.register(YummyParticleRegister.POLLUTED_PARTICLE_PURPLE.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x201a33));
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YummyEntityRegister.HEROBRINE.get(), HerobrineRenderer::new);
        event.registerEntityRenderer(YummyEntityRegister.THROWN_SPEAR_OF_LONGINUS.get(), ThrownSpearOfLonginusRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HerobrineModel.LAYER_LOCATION, HerobrineModel::createBodyLayer);
        event.registerLayerDefinition(ThrownSpearOfLonginusModel.LAYER_LOCATION, ThrownSpearOfLonginusModel::createBodyLayer);
    }
}
