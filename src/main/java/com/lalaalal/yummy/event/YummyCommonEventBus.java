package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlockRegister;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.networking.YummyMessages;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YummyCommonEventBus {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(YummyBlockRegister.CYAN_FLOWER.getId(), YummyBlockRegister.POTTED_CYAN_FLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(YummyBlockRegister.LIME_FLOWER.getId(), YummyBlockRegister.POTTED_LIME_FLOWER);
        });

        YummyMessages.register();
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(YummyEntityRegister.HEROBRINE.get(), Herobrine.getHerobrineAttributes().build());
        event.put(YummyEntityRegister.SHADOW_HEROBRINE.get(), ShadowHerobrine.getHerobrineAttributes().build());
    }
}
