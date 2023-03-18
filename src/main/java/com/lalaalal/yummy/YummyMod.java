package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.item.YummyItemRegister;
import com.lalaalal.yummy.particle.YummyParticleRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(YummyMod.MOD_ID)
public class YummyMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "yummy";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab TAB = new CreativeModeTab(MOD_ID) {
        @Override
        @NotNull
        public ItemStack makeIcon() {
            return YummyItemRegister.NEMETHYST_ITEM.get().getDefaultInstance();
        }
    };

    public YummyMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        YummyBlockRegister.BLOCKS.register(modEventBus);
        YummyBlockRegister.ITEMS.register(modEventBus);
        YummyItemRegister.ITEMS.register(modEventBus);
        YummyItemRegister.VANILLA_ITEMS.register(modEventBus);
        YummyParticleRegister.PARTICLE_TYPES.register(modEventBus);
        YummyEntityRegister.ENTITIES.register(modEventBus);

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
