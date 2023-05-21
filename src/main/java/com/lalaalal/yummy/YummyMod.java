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
import com.lalaalal.yummy.world.inventory.YummyMenuTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YummyMod.MOD_ID)
public class YummyMod
{
    public static final String MOD_ID = "yummy";

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
        YummyMenuTypes.register(modEventBus);
    }
}
