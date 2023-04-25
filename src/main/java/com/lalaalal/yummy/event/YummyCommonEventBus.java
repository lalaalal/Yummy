package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyTypes;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.networking.YummyMessages;
import net.minecraft.client.renderer.Sheets;
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
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(YummyBlocks.CYAN_FLOWER.getId(), YummyBlocks.POTTED_CYAN_FLOWER);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(YummyBlocks.LIME_FLOWER.getId(), YummyBlocks.POTTED_LIME_FLOWER);

            YummyBlocks.EBONY_LOG.get().addAxeModifiedState(YummyBlocks.EBONY_LOG.get(), YummyBlocks.STRIPPED_EBONY_LOG.get());
            YummyBlocks.EBONY_WOOD.get().addAxeModifiedState(YummyBlocks.EBONY_WOOD.get(), YummyBlocks.STRIPPED_EBONY_WOOD.get());

            Sheets.addWoodType(YummyTypes.EBONY);
        });

        YummyMessages.register();
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(YummyEntities.HEROBRINE.get(), Herobrine.getHerobrineAttributes().build());
        event.put(YummyEntities.SHADOW_HEROBRINE.get(), ShadowHerobrine.getHerobrineAttributes().build());
    }
}
