package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyTypes;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.client.model.*;
import com.lalaalal.yummy.client.renderer.*;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.particle.PollutedParticle;
import com.lalaalal.yummy.particle.YummyParticles;
import com.lalaalal.yummy.screen.AlembicScreen;
import com.lalaalal.yummy.world.inventory.YummyMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class YummyClientEventBus {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        Sheets.addWoodType(YummyTypes.WOOD_EBONY);
        WoodType.register(YummyTypes.WOOD_EBONY);

        MenuScreens.register(YummyMenuTypes.ALEMBIC_MENU.get(), AlembicScreen::new);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tint) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(),
                YummyBlocks.EBONY_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Item event) {
        event.register((itemStack, tint) -> {
            BlockState blockstate = ((BlockItem) itemStack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, tint);
        }, YummyBlocks.EBONY_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.register(YummyParticles.POLLUTED_PARTICLE_RED.get(),
                (spriteSet) -> new PollutedParticle.Provider(spriteSet, 0x441f0b));
        event.register(YummyParticles.POLLUTED_PARTICLE_BLUE.get(),
                (spriteSet) -> new PollutedParticle.Provider(spriteSet, 0x019ea3));
        event.register(YummyParticles.POLLUTED_PARTICLE_PURPLE.get(),
                (spriteSet) -> new PollutedParticle.Provider(spriteSet, 0x201a33));
        event.register(YummyParticles.CORRUPTED_SMALL_FLAME.get(),
                FlameParticle.SmallFlameProvider::new);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YummyEntities.HEROBRINE.get(), HerobrineRenderer::new);
        event.registerEntityRenderer(YummyEntities.SHADOW_HEROBRINE.get(), ShadowHerobrineRenderer::new);
        event.registerEntityRenderer(YummyEntities.BUNNY_CHEST.get(), BunnyChestRenderer::new);
        event.registerEntityRenderer(YummyEntities.SPEAR.get(), ThrownSpearRenderer::new);
        event.registerEntityRenderer(YummyEntities.MIGHTY_HOLY_SPEAR.get(), context -> new ThrownSpearRenderer<>(context, "mighty_holy_spear", () -> new ThrownMightyHolySpearModel<>(
                context.bakeLayer(ThrownMightyHolySpearModel.LAYER_LOCATION)
        )));
        event.registerEntityRenderer(YummyEntities.THROWN_SPEAR_OF_LONGINUS.get(), ThrownSpearOfLonginusRenderer::new);
        event.registerEntityRenderer(YummyEntities.MARK_FIREBALL.get(),
                (context) -> new YummyItemEntityRenderer<>(context, "mark_fireball", 1));
        event.registerEntityRenderer(YummyEntities.METEOR.get(), MeteorRenderer::new);
        event.registerEntityRenderer(YummyEntities.FLOATING_BLOCK_ENTITY.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(YummyEntities.TRANSFORMING_BLOCK_ENTITY.get(), FloatingBlockRenderer::new);
        event.registerEntityRenderer(YummyEntities.NARAKA_MAGIC_CIRCLE.get(), FlatImageEntityRenderer::new);
        event.registerEntityRenderer(YummyEntities.NARAKA_STORM.get(), FlatImageEntityRenderer::new);
        event.registerEntityRenderer(YummyEntities.FRACTURE_ENTITY.get(), FractureEntityRenderer::new);
        event.registerEntityRenderer(YummyEntities.FRACTURE_EXPLOSION.get(), FractureExplosionRenderer::new);
        event.registerEntityRenderer(YummyEntities.EBONY_BOAT.get(), (context) -> new EbonyBoatRenderer(context, false));
        event.registerEntityRenderer(YummyEntities.EBONY_CHEST_BOAT.get(), (context) -> new EbonyBoatRenderer(context, true));

        event.registerBlockEntityRenderer(YummyBlockEntities.YUMMY_SIGN_BLOCK_ENTITY.get(), SignRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BunnyChestModel.LAYER_LOCATION, BunnyChestModel::createBodyLayer);
        event.registerLayerDefinition(ThrownSpearModel.LAYER_LOCATION, ThrownSpearModel::createBodyLayer);
        event.registerLayerDefinition(ThrownMightyHolySpearModel.LAYER_LOCATION, ThrownMightyHolySpearModel::createBodyLayer);
        event.registerLayerDefinition(ThrownSpearOfLonginusModel.LAYER_LOCATION, ThrownSpearOfLonginusModel::createBodyLayer);
        event.registerLayerDefinition(MeteorModel.LAYER_LOCATION, MeteorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onTexture(TextureStitchEvent.Pre event) {
        ResourceLocation location = event.getAtlas().location();
        if (location.equals(new ResourceLocation("textures/atlas/blocks.png"))) {
            event.addSprite(new ResourceLocation(YummyMod.MOD_ID, "item/spear"));
            event.addSprite(new ResourceLocation(YummyMod.MOD_ID, "item/mighty_holy_spear"));
            event.addSprite(new ResourceLocation(YummyMod.MOD_ID, "item/spear_of_longinus"));
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.RegisterAdditional event) {
        event.register(new ModelResourceLocation(YummyMod.MOD_ID, "spear_inventory", "inventory"));
        event.register(new ModelResourceLocation(YummyMod.MOD_ID, "mighty_holy_spear_inventory", "inventory"));
        event.register(new ModelResourceLocation(YummyMod.MOD_ID, "spear_of_longinus_inventory", "inventory"));
    }
}
