package com.lalaalal.yummy.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public class SteelTier implements Tier {
    public static final SteelTier INSTANCE = new SteelTier();

    private static final int USES = Tiers.NETHERITE.getUses() * 2 / 3;

    private SteelTier() {

    }

    @Override
    public int getUses() {
        return USES;
    }

    @Override
    public float getSpeed() {
        return Tiers.DIAMOND.getSpeed();
    }

    @Override
    public float getAttackDamageBonus() {
        return Tiers.DIAMOND.getAttackDamageBonus();
    }

    @Override
    public int getLevel() {
        return Tiers.IRON.getLevel();
    }

    @Override
    public int getEnchantmentValue() {
        return Tiers.DIAMOND.getEnchantmentValue();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(YummyItems.RUBELLITE.get());
    }
}
