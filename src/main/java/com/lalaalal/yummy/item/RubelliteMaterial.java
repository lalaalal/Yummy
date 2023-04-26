package com.lalaalal.yummy.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;

public class RubelliteMaterial implements ArmorMaterial {
    public static final RubelliteMaterial INSTANCE = new RubelliteMaterial();

    private RubelliteMaterial() {

    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slot) {
        return ArmorMaterials.NETHERITE.getDurabilityForSlot(slot) * 2 / 3;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slot) {
        return ArmorMaterials.NETHERITE.getDefenseForSlot(slot);
    }

    @Override
    public int getEnchantmentValue() {
        return ArmorMaterials.NETHERITE.getEnchantmentValue();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(YummyItems.RUBELLITE.get());
    }

    @Override
    public String getName() {
        return "steel";
    }

    @Override
    public float getToughness() {
        return ArmorMaterials.NETHERITE.getToughness();
    }

    @Override
    public float getKnockbackResistance() {
        return 0.5f;
    }
}
