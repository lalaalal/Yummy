package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class DiffusionEffect extends MobEffect {
    protected DiffusionEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        for (MobEffectInstance activeEffect : livingEntity.getActiveEffects()) {
            if (activeEffect.getEffect() instanceof ElementEffect) {
                int duration = activeEffect.getDuration() * 2;
                int newAmplifier = activeEffect.getAmplifier() == 0 ? 1 : activeEffect.getAmplifier() * 2;
                MobEffectInstance effectInstance = new MobEffectInstance(activeEffect.getEffect(), duration, newAmplifier);
                livingEntity.addEffect(effectInstance);
            }
        }
    }
}
