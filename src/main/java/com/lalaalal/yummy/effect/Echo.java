package com.lalaalal.yummy.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class Echo extends MobEffect {
    public static final int MAX_AMPLIFIER = 3;

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
        if (amplifier >= MAX_AMPLIFIER) {
            float damage = livingEntity.getHealth() * 0.25f;
            livingEntity.hurt(DamageSource.MAGIC.bypassInvul(), damage);
            livingEntity.removeEffect(YummyEffects.ECHO.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
