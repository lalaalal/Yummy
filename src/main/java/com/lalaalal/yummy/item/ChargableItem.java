package com.lalaalal.yummy.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public abstract class ChargableItem extends Item {
    protected final int cooldown;

    public ChargableItem(Properties pProperties, int cooldown) {
        super(pProperties);
        this.cooldown = cooldown;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage())
            return super.use(level, player, usedHand);

        itemUse(level, player, usedHand);

        player.getCooldowns().addCooldown(this, cooldown);
        if (!level.isClientSide)
            itemStack.hurt(1, level.random, (ServerPlayer) player);

        return InteractionResultHolder.success(itemStack);
    }

    protected abstract void itemUse(Level level, Player player, InteractionHand usedHand);

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }
}
