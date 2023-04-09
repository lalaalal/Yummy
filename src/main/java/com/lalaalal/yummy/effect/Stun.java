package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Stun extends MobEffect {
    protected Stun() {
        super(MobEffectCategory.HARMFUL, 0xFFFF00);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "E21757F0-D698-11ED-AFA1-0242AC120002", -15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
