package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.effect.YummyEffects;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.SpawnHerobrinePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class YummyForgeEvent {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        MobEffectInstance stunEffectInstance;
        if (player != null && (stunEffectInstance = player.getEffect(YummyEffects.STUN.get())) != null) {
            float ticksExistedDelta = player.tickCount;
            double shakeAmplitude = (1 + stunEffectInstance.getAmplifier()) * 0.01;
            event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0F + 2.0F) * 25.0));
            event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0F + 1.0F) * 25.0));
            event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0F) * 25.0));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void soulFireCheck(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        ItemStack item = event.getEntity().getMainHandItem();
        if (item.is(Items.FLINT_AND_STEEL) || item.is(Items.FIRE_CHARGE)
                && Herobrine.canSummonHerobrine(level, blockPos))
            YummyMessages.sendToServer(new SpawnHerobrinePacket(blockPos));
    }

    @SubscribeEvent
    public static void onPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (event.isCancelable() && livingEntity.hasEffect(YummyEffects.STUN.get())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemUse(LivingEntityUseItemEvent event) {
        if (event.isCancelable() && event.getEntity().hasEffect(YummyEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingEntityJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(YummyEffects.STUN.get())) {
            livingEntity.setDeltaMovement(0, 0, 0);
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if (event.isCancelable() && event.getEntity().hasEffect(YummyEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.isCancelable() && event.getEntity().hasEffect(YummyEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        if (event.isCancelable() && event.getEntity().hasEffect(YummyEffects.STUN.get())) {
            event.setCanceled(true);
        }
    }
}
