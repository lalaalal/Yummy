package com.lalaalal.yummy.item;

import com.lalaalal.yummy.effect.Echo;
import com.lalaalal.yummy.effect.EchoMark;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EchoSwordItem extends SwordItem {
    public EchoSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (player.level().isClientSide || !(entity instanceof LivingEntity target))
            return super.onLeftClickEntity(stack, player, entity);
        float damage = getTier().getAttackDamageBonus() + (float) (Math.floor(target.getMaxHealth() / 5) * 2);
        DamageSource damageSource = player.damageSources().mobAttack(player);
        if (getTier() == YummyTiers.GOD) {
            damageSource = YummyDamageSources.godEchoSword(player);
            damage = damage * 1.5f + target.getMaxHealth() * 0.02f;
        }
        target.hurt(damageSource, damage);
        EchoMark.markTarget(target, player);
        Echo.overlapEcho(target);

        return false;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide && EchoMark.useMark(player)) {
            level.playSound(null, player.getOnPos(), SoundEvents.AMETHYST_BLOCK_FALL, SoundSource.PLAYERS, 1, 2);
            level.playSound(null, player.getOnPos(), SoundEvents.BELL_RESONATE, SoundSource.PLAYERS, 1, 2);
            player.getCooldowns().addCooldown(this, 20 * 10);
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc3").withStyle(ChatFormatting.GRAY));
    }
}
