package com.lalaalal.yummy.item;

import com.lalaalal.yummy.effect.Echo;
import com.lalaalal.yummy.effect.EchoMark;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float damage = target.getMaxHealth() * 0.4f;
        target.hurt(DamageSource.mobAttack(attacker).bypassInvul(), damage);
        EchoMark.markTarget(target, attacker);
        Echo.overlapEcho(target);

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && EchoMark.useMark(player)) {
            level.playSound(null, player.getOnPos(), SoundEvents.AMETHYST_BLOCK_FALL, SoundSource.PLAYERS, 1, 2);
            level.playSound(null, player.getOnPos(), SoundEvents.BELL_RESONATE, SoundSource.PLAYERS, 1, 2);
            player.getCooldowns().addCooldown(this, 20 * 20);
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }

        return super.use(level, player, usedHand);
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc1").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc2").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable(getDescriptionId() + ".desc3").withStyle(ChatFormatting.GRAY));
    }
}
