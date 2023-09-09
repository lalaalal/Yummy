package com.lalaalal.yummy.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class EbonyFruitItem extends Item {
    public EbonyFruitItem() {
        super(new Item.Properties().stacksTo(64)
                .rarity(Rarity.COMMON).food((new FoodProperties.Builder())
                        .nutrition(20).saturationMod(1.0f).alwaysEat().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        execute(entity);
        return retval;
    }

    public static void execute(Entity entity) {

        if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
            if (entity instanceof Player _player)
                _player.setHealth(Float.MAX_VALUE);

        }
    }
}