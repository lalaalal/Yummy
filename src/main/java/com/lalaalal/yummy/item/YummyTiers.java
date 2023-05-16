package com.lalaalal.yummy.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum YummyTiers implements Tier {
    PURIFIED_SOUL(5, 3096, 13.0f, 5.0f, 23, () -> Ingredient.of(YummyItems.PURIFIED_SOUL_METAL.get())),
    GOD(666, 6666, 6.66f, 66.6f, 66, () -> Ingredient.of(YummyItems.GOD_BLOOD.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> ingredient;

    YummyTiers(int level, int uses, float speed, float attackDamage, int enchantmentValue, Supplier<Ingredient> ingredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.enchantmentValue = enchantmentValue;
        this.ingredient = ingredient;
    }


    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return ingredient.get();
    }
}
