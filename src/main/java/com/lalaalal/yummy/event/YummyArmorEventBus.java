package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.item.PurifiedSoulArmor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID)
public class YummyArmorEventBus {
    @SubscribeEvent
    public static void onArmorUpdate(LivingEvent.LivingTickEvent event){
        LivingEntity livingEntity = event.getEntity();
        if (!event.isCanceled()) {
            PurifiedSoulArmor.FullSet(livingEntity);
        }
    }
}
