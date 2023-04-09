package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.misc.EffectDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class HerobrineMark extends MobEffect {
    public static void overlapMark(LivingEntity entity) {
        if (entity instanceof Herobrine)
            return;

        final MobEffect HEROBRINE_MARK = YummyEffectRegister.HEROBRINE_MARK.get();

        MobEffectInstance mobEffectInstance = entity.getEffect(HEROBRINE_MARK);
        if (mobEffectInstance != null) {
            int amplifier = Math.min(mobEffectInstance.getAmplifier() + 1, 6);
            entity.addEffect(new MobEffectInstance(HEROBRINE_MARK, 666, amplifier));
        } else {
            entity.addEffect(new MobEffectInstance(HEROBRINE_MARK, 666, 0));
        }
    }

    protected HerobrineMark() {
        super(MobEffectCategory.HARMFUL, 0x441f0b);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.getLevel().isClientSide && amplifier >= 6) {
            DamageSource damageSource = new EffectDamageSource("mark", this);
            damageSource.bypassArmor().bypassInvul();
            livingEntity.removeAllEffects();
            livingEntity.hurt(damageSource, Float.MAX_VALUE);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}