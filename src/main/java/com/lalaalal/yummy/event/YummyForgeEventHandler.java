package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.SpawnHerobrinePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class YummyForgeEventHandler {
    @SubscribeEvent
    public static void soulFireCheck(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        ItemStack item = event.getEntity().getMainHandItem();
        if (item.is(Items.FLINT_AND_STEEL) || item.is(Items.FIRE_CHARGE)
                && Herobrine.canSummonHerobrine(level, blockPos))
            YummyMessages.sendToServer(new SpawnHerobrinePacket(blockPos));
    }
}
