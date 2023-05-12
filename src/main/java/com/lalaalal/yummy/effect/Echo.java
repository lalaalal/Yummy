package com.lalaalal.yummy.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class Echo extends MobEffect {
    public static void overlapEcho(LivingEntity livingEntity) {
        MobEffectInstance existingInstance = livingEntity.getEffect(YummyEffects.ECHO.get());
        int amplifier = 0;
        if (existingInstance != null)
            amplifier = existingInstance.getAmplifier() + 1;

        MobEffectInstance effectInstance = new MobEffectInstance(YummyEffects.ECHO.get(), 20 * 30, amplifier);
        livingEntity.addEffect(effectInstance);
    }

    protected Echo() {
        super(MobEffectCategory.HARMFUL, 0xC8DFEB);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (amplifier >= 19) {
            float damage = livingEntity.getHealth() / 2;
            livingEntity.hurt(DamageSource.MAGIC, damage);
        }
    }
}
