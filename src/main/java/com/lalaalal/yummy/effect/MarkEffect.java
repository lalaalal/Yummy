package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.misc.EffectDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.awt.*;

public class MarkEffect extends MobEffect {
    public static void overlapMark(LivingEntity entity) {
        if (entity instanceof Herobrine)
            return;

        final MobEffect MARK_EFFECT = YummyEffectRegister.MARK.get();

        MobEffectInstance mobEffectInstance = entity.getEffect(MARK_EFFECT);
        if (mobEffectInstance != null) {
            int amplifier = Math.min(mobEffectInstance.getAmplifier() + 1, 6);
            entity.addEffect(new MobEffectInstance(MARK_EFFECT, 666, amplifier));
        } else {
            entity.addEffect(new MobEffectInstance(MARK_EFFECT, 666, 0));
        }
    }

    protected MarkEffect() {
        super(MobEffectCategory.HARMFUL, Color.RED.getRGB());
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.getLevel().isClientSide && amplifier >= 6) {
            DamageSource damageSource = new EffectDamageSource("mark", this);
            livingEntity.removeAllEffects();
            livingEntity.hurt(damageSource, Float.MAX_VALUE);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
