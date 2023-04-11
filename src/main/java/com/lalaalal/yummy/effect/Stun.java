package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Stun extends MobEffect {
    protected Stun() {
        super(MobEffectCategory.HARMFUL, 0xFFFF00);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "E21757F0-D698-11ED-AFA1-0242AC120002", -100, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.getLevel();
        if (livingEntity.isOnGround())
            livingEntity.makeStuckInBlock(level.getBlockState(livingEntity.getOnPos()), new Vec3(0.01, 0.01, 0.01));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
