package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.block.entity.YummyBlockEntityRegister;
import com.lalaalal.yummy.effect.YummyEffectRegister;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.item.YummyItemRegister;
import com.lalaalal.yummy.particle.YummyParticleRegister;
import com.lalaalal.yummy.sound.YummySoundRegister;
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
            return YummyItemRegister.RUBELLITE.get().getDefaultInstance();
        }
    };

    public YummyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        YummyBlockRegister.register(modEventBus);
        YummyBlockEntityRegister.register(modEventBus);
        YummyItemRegister.register(modEventBus);
        YummyParticleRegister.register(modEventBus);
        YummyEntityRegister.register(modEventBus);
        YummyEffectRegister.register(modEventBus);
        YummySoundRegister.register(modEventBus);
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
