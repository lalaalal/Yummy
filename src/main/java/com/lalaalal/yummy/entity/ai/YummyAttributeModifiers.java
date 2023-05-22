package com.lalaalal.yummy.entity.ai;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class YummyAttributeModifiers {
    private static final Map<AttributeModifier, Attribute> MODIFIER_DEFINITION = new HashMap<>();

    public static final AttributeModifier IGNORE_KNOCKBACK = defineModifier(Attributes.KNOCKBACK_RESISTANCE, "IgnoreKnockback", 1.0, AttributeModifier.Operation.ADDITION);
    public static final AttributeModifier PREVENT_MOVING = defineModifier(Attributes.MOVEMENT_SPEED, "StopMoving", 0.0, AttributeModifier.Operation.MULTIPLY_TOTAL);


    public static float calcItemAttribute(float baseDamage, ItemStack itemStack, EquipmentSlot slot, Attribute attribute) {
        Collection<AttributeModifier> attributeModifiers = itemStack.getAttributeModifiers(slot).get(attribute);
        for (AttributeModifier attributeModifier : attributeModifiers)
            baseDamage += attributeModifier.getAmount();
        return baseDamage;
    }

    public static AttributeModifier defineModifier(Attribute attribute, String name, double amount, AttributeModifier.Operation operation) {
        AttributeModifier modifier = new AttributeModifier(name, amount, operation);
        MODIFIER_DEFINITION.put(modifier, attribute);

        return modifier;
    }

    public static void addPermanentModifier(LivingEntity livingEntity, AttributeModifier modifier) {
        Attribute attribute = MODIFIER_DEFINITION.get(modifier);
        if (attribute == null)
            return;
        AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
        if (attributeInstance != null && !attributeInstance.hasModifier(modifier))
            attributeInstance.addPermanentModifier(modifier);
    }

    public static void addTransientModifier(LivingEntity livingEntity, AttributeModifier modifier) {
        Attribute attribute = MODIFIER_DEFINITION.get(modifier);
        if (attribute == null)
            return;
        AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
        if (attributeInstance != null && !attributeInstance.hasModifier(modifier))
            attributeInstance.addTransientModifier(modifier);
    }

    public static void removeModifier(LivingEntity livingEntity, AttributeModifier modifier) {
        Attribute attribute = MODIFIER_DEFINITION.get(modifier);
        if (attribute == null)
            return;
        AttributeInstance attributeInstance = livingEntity.getAttribute(attribute);
        if (attributeInstance != null && attributeInstance.hasModifier(modifier))
            attributeInstance.removeModifier(modifier);
    }
}
