package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.effect.YummyEffects;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.item.YummyItems;
import com.lalaalal.yummy.particle.YummyParticles;
import com.lalaalal.yummy.sound.YummySounds;
import com.lalaalal.yummy.world.feature.YummyConfiguredFeatures;
import com.lalaalal.yummy.world.feature.YummyPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YummyMod.MOD_ID)
public class YummyMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "yummy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return YummyItems.RUBELLITE.get().getDefaultInstance();
        }
    };

    public YummyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        YummyBlocks.register(modEventBus);
        YummyBlockEntities.register(modEventBus);
        YummyItems.register(modEventBus);
        YummyParticles.register(modEventBus);
        YummyEntities.register(modEventBus);
        YummyEffects.register(modEventBus);
        YummySounds.register(modEventBus);
        YummyConfiguredFeatures.register(modEventBus);
        YummyPlacedFeatures.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
