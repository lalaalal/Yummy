package com.lalaalal.yummy.world.damagesource;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

public class EffectDamageSource extends DamageSource {
    private final MobEffect effect;

    public EffectDamageSource(Holder<DamageType> damageType, MobEffect effect) {
        super(damageType);
        this.effect = effect;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        String messageId = "death.effect." + getMsgId();
        return Component.translatable(messageId, livingEntity.getDisplayName(), effect.getDisplayName());
    }
}
