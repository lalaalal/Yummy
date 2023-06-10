package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.ThrownSpearOfLonginus;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpearOfLonginusItem extends SpearItem {
    public SpearOfLonginusItem(Properties properties) {
        super(properties, YummyTiers.GOD, 599, 62, 6, ThrownSpearOfLonginus::new);
    }

    private void hurtUser(ItemStack itemStack, LivingEntity user, float damageRate) {
        if (user instanceof Player player && player.getAbilities().instabuild)
            return;

        DamageSource damageSource = YummyDamageSources.longinus(user.level(), null, itemStack);
        float damage = user.getMaxHealth() * damageRate;
        user.hurt(damageSource, damage);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, Player player, Entity entity) {
        if (!player.level().isClientSide) {
            DamageSource damageSource = YummyDamageSources.longinus(player.level(), player, itemStack);
            entity.hurt(damageSource, Float.MAX_VALUE);
            if (entity instanceof LivingEntity livingEntity && livingEntity.getHealth() > 0)
                livingEntity.kill();

            hurtUser(itemStack, player, 0.8f);
        }

        return true;
    }

    @Override
    protected void throwSpear(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        hurtUser(itemStack, livingEntity, 0.9f);
        super.throwSpear(itemStack, level, livingEntity);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        return Component.translatable(getDescriptionId()).withStyle(ChatFormatting.RED);
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return false;
    }
}
