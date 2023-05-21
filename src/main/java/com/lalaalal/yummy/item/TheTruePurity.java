package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.effect.Element;
import com.lalaalal.yummy.world.damagesource.ItemDamageSource;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class TheTruePurity extends ElementSwordItem {
    public TheTruePurity(Tier tier, Element element, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, element, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        DamageSource damageSource = new ItemDamageSource(YummyMod.MOD_ID + ".the_true_purity", null, itemStack)
                .bypassInvul();
        target.hurt(damageSource, Float.MAX_VALUE);

        return super.hurtEnemy(itemStack, target, attacker);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId(stack)).withStyle(ChatFormatting.RED);
    }
}
