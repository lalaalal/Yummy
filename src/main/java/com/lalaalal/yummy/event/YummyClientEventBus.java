package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.HerobrineModel;
import com.lalaalal.yummy.client.model.ShadowHerobrineModel;
import com.lalaalal.yummy.client.model.ThrownSpearOfLonginusModel;
import com.lalaalal.yummy.client.renderer.HerobrineRenderer;
import com.lalaalal.yummy.client.renderer.ShadowHerobrineRenderer;
import com.lalaalal.yummy.client.renderer.ThrownSpearOfLonginusRenderer;
import com.lalaalal.yummy.client.renderer.YummyItemEntityRenderer;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.particle.PollutedParticle;
import com.lalaalal.yummy.particle.YummyParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YummyClientEventBus {
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(YummyParticles.POLLUTED_PARTICLE_RED.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x441f0b));
        event.register(YummyParticles.POLLUTED_PARTICLE_BLUE.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x019ea3));
        event.register(YummyParticles.POLLUTED_PARTICLE_PURPLE.get(),
                (spriteSet) -> new PollutedParticle.PollutedParticleProvider(spriteSet, 0x201a33));
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YummyEntities.HEROBRINE.get(), HerobrineRenderer::new);
        event.registerEntityRenderer(YummyEntities.SHADOW_HEROBRINE.get(), ShadowHerobrineRenderer::new);
        event.registerEntityRenderer(YummyEntities.THROWN_SPEAR_OF_LONGINUS.get(), ThrownSpearOfLonginusRenderer::new);
        event.registerEntityRenderer(YummyEntities.MARK_FIREBALL.get(),
                (context) -> new YummyItemEntityRenderer<>(context, "mark_fireball", 3));
        event.registerEntityRenderer(YummyEntities.METEOR.get(),
                (context) -> new YummyItemEntityRenderer<>(context, "meteor", 5));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HerobrineModel.LAYER_LOCATION, HerobrineModel::createBodyLayer);
        event.registerLayerDefinition(ShadowHerobrineModel.LAYER_LOCATION, ShadowHerobrineModel::createBodyLayer);
        event.registerLayerDefinition(ThrownSpearOfLonginusModel.LAYER_LOCATION, ThrownSpearOfLonginusModel::createBodyLayer);
    }
}
