package com.lalaalal.yummy.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MixedElementEffect extends MobEffect {
    private final int damageMultiplier;

    protected MixedElementEffect(int damageMultiplier, int color) {
        super(MobEffectCategory.HARMFUL, color);
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(DamageSource.MAGIC, 2 * damageMultiplier);
    }
}
