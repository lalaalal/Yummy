package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.CameraShakingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class YummyClientForgeEvent {
    @SubscribeEvent
    public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            AABB area = player.getBoundingBox().inflate(15);
            CameraShakingEntity cameraShakingEntity = player.level.getNearestEntity(CameraShakingEntity.class, TargetingConditions.forNonCombat(), player, player.getX(), player.getY(), player.getZ(), area);
            if (cameraShakingEntity != null && cameraShakingEntity.isCameraShaking()) {
                float ticksExistedDelta = player.tickCount;
                double shakeAmplitude = 0.03;
                event.setPitch((float) (event.getPitch() + shakeAmplitude * Math.cos(ticksExistedDelta * 3.0F + 2.0F) * 25.0));
                event.setYaw((float) (event.getYaw() + shakeAmplitude * Math.cos(ticksExistedDelta * 5.0F + 1.0F) * 25.0));
                event.setRoll((float) (event.getRoll() + shakeAmplitude * Math.cos(ticksExistedDelta * 4.0F) * 25.0));
            }
        }
    }
}
