package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlocks;

import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.effect.YummyEffects;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.item.YummyItems;
import com.lalaalal.yummy.particle.YummyParticles;
import com.lalaalal.yummy.sound.YummySounds;
import com.lalaalal.yummy.world.inventory.YummyMenuTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;

@Mod(YummyMod.MOD_ID)
public class YummyMod
{
    public static final String MOD_ID = "yummy";

    public YummyMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //modEventBus.addListener(this::commonSetup);
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
    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(YummyBlocks::registerFlammability);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(YummyMod.MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}
