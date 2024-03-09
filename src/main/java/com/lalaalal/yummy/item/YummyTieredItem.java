package com.lalaalal.yummy.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TridentItem;


public class YummyTieredItem extends TridentItem {
    private final Tier tier;

    public YummyTieredItem(Tier pTier, Item.Properties pProperties) {
        super(pProperties.defaultDurability(pTier.getUses()));
        this.tier = pTier;
    }

    public Tier getTier() {
        return this.tier;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return this.tier.getRepairIngredient().test(pRepair) || super.isValidRepairItem(pToRepair, pRepair);
    }
}