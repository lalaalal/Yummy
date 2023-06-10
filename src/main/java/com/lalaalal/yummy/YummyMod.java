package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.effect.YummyEffects;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.item.YummyItems;
import com.lalaalal.yummy.particle.YummyParticles;
import com.lalaalal.yummy.sound.YummySounds;
import com.lalaalal.yummy.world.inventory.YummyMenuTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YummyMod.MOD_ID)
public class YummyMod
{
    public static final String MOD_ID = "yummy";

    public YummyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        YummyEntities.register(modEventBus);
        YummyBlocks.register(modEventBus);
        YummyBlockEntities.register(modEventBus);
        YummyItems.register(modEventBus);
        YummyParticles.register(modEventBus);
        YummyEffects.register(modEventBus);
        YummySounds.register(modEventBus);
        YummyMenuTypes.register(modEventBus);
        YummyTabs.register(modEventBus);
    }
}
