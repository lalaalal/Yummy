package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.BunnyChest;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.item.distill.EssenceDistilling;
import com.lalaalal.yummy.networking.YummyMessages;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YummyCommonEventBus {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            YummyBlocks.EBONY_LOG.get().addAxeModifiedState(YummyBlocks.EBONY_LOG.get(), YummyBlocks.STRIPPED_EBONY_LOG.get());
            YummyBlocks.EBONY_WOOD.get().addAxeModifiedState(YummyBlocks.EBONY_WOOD.get(), YummyBlocks.STRIPPED_EBONY_WOOD.get());

            EssenceDistilling.init();
        });

        YummyMessages.register();
    }

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(YummyEntities.HEROBRINE.get(), Herobrine.getHerobrineAttributes().build());
        event.put(YummyEntities.SHADOW_HEROBRINE.get(), ShadowHerobrine.getHerobrineAttributes().build());
        event.put(YummyEntities.BUNNY_CHEST.get(), BunnyChest.getBunnyChestAttributes().build());
    }
}
