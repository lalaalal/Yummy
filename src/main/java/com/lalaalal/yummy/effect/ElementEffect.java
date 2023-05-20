package com.lalaalal.yummy.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ElementEffect extends MobEffect {
    public final Element element;

    protected ElementEffect(Element element, int color) {
        super(MobEffectCategory.HARMFUL, color);
        this.element = element;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        boolean reactionSucceed = false;
        for (MobEffectInstance activeEffect : livingEntity.getActiveEffects()) {
            if (react(livingEntity, activeEffect))
                reactionSucceed = true;
        }
        if (reactionSucceed)
            livingEntity.removeEffect(this);
        livingEntity.hurt(DamageSource.MAGIC, 2 * amplifier);
    }

    private boolean react(LivingEntity livingEntity, MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect();
        if (effect instanceof ElementEffect elementEffect) {
            MobEffectInstance reactionEffect = ElementReactions.getElementReaction(elementEffect, this);
            if (reactionEffect != null) {
                livingEntity.addEffect(reactionEffect);
                livingEntity.removeEffect(effect);
                return true;
            }
        }

        return false;
    }
}
