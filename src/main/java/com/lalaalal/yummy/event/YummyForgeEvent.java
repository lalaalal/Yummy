package com.lalaalal.yummy.event;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.block.entity.HerobrineSpawnerBlockEntity;
import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.effect.Echo;
import com.lalaalal.yummy.effect.YummyEffects;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class YummyForgeEvent {
    @SubscribeEvent
    public static void FireCheck(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide)
            return;
        BlockPos blockPos = event.getHitVec().getBlockPos();
        ItemStack item = event.getEntity().getMainHandItem();
        Direction direction = event.getHitVec().getDirection();
        if ((item.is(Items.FLINT_AND_STEEL) || item.is(Items.FIRE_CHARGE))
                && direction == Direction.UP
                && Herobrine.canSummonHerobrine(level, blockPos)) {
            level.setBlock(blockPos.above(), YummyBlocks.PURIFIED_SOUL_FIRE_BLOCK.get().defaultBlockState(), 10);
            Optional<HerobrineSpawnerBlockEntity> optional = level.getBlockEntity(blockPos.below(), YummyBlockEntities.HEROBRINE_SPAWNER_BLOCK_ENTITY.get());
            if (optional.isPresent()) {
                HerobrineSpawnerBlockEntity spawner = optional.get();
                spawner.setTriggeredPlayerUUID(event.getEntity().getUUID());
                spawner.activate();
            }
        }
    }

    @SubscribeEvent
    public static void echoCheck(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffectInstance instance = livingEntity.getEffect(YummyEffects.ECHO.get());
        if (instance != null && instance.getAmplifier() < Echo.MAX_AMPLIFIER) {
            float multiple = 0.1f * instance.getAmplifier();
            float amount = event.getAmount() * multiple;
            if (event.getAmount() != Float.MAX_VALUE)
                livingEntity.hurt(YummyDamageSources.echoMark(livingEntity.level(), YummyEffects.ECHO.get()), amount);
        }
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
