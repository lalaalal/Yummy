package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EchoMark extends MobEffect {
    private static final Map<LivingEntity, LivingEntity> targetMap = new HashMap<>();

    public static void markTarget(LivingEntity target, LivingEntity attacker) {
        targetMap.put(target, attacker);
        MobEffectInstance effectInstance = new MobEffectInstance(YummyEffects.ECHO_MARK.get(), 1000000 * 20);
        target.addEffect(effectInstance);
    }

    public static void useMark(LivingEntity attacker) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targetMap.keySet()
                .stream()
                .filter(livingEntity -> targetMap.get(livingEntity).equals(attacker))
                .forEach(targets::add);

        for (LivingEntity target : targets) {
            targetMap.remove(target);
            target.moveTo(attacker.position());
            target.removeEffect(YummyEffects.ECHO_MARK.get());

            MobEffectInstance stunEffectInstance = new MobEffectInstance(YummyEffects.STUN.get(), 20);
            target.addEffect(stunEffectInstance);
            Echo.overlapEcho(target);
        }
    }

    protected EchoMark() {
        super(MobEffectCategory.HARMFUL, 0x29DFEB);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!targetMap.containsKey(livingEntity))
            livingEntity.removeEffect(YummyEffects.ECHO_MARK.get());
    }
}
