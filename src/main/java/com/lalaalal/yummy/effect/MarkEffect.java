package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.entity.HerobrineEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MarkEffect extends MobEffect {
    public static void overlapMark(LivingEntity entity) {
        if (entity instanceof HerobrineEntity)
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
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (amplifier >= 6) {
            livingEntity.hurt(new DamageSource("mark"), Float.MAX_VALUE);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
