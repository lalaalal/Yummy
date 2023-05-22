package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EchoMark extends MobEffect {
    private static final Map<LivingEntity, LivingEntity> targetMap = new HashMap<>();

    public static void markTarget(LivingEntity target, LivingEntity attacker) {
        removeDeadTargets();

        targetMap.put(target, attacker);
        MobEffectInstance effectInstance = new MobEffectInstance(YummyEffects.ECHO_MARK.get(), 1000000 * 20);
        target.addEffect(effectInstance);
    }

    private static void removeDeadTargets() {
        ArrayList<LivingEntity> deadTargets = new ArrayList<>();
        for (LivingEntity target : targetMap.keySet()) {
            if (target.isDeadOrDying())
                deadTargets.add(target);
        }

        for (LivingEntity deadTarget : deadTargets)
            targetMap.remove(deadTarget);
    }

    public static boolean useMark(LivingEntity attacker) {
        ArrayList<LivingEntity> targets = new ArrayList<>();
        targetMap.keySet()
                .stream()
                .filter(target -> targetMap.get(target).equals(attacker))
                .forEach(targets::add);

        boolean succeed = false;
        for (LivingEntity target : targets) {
            if (!attacker.level.equals(target.level))
                continue;
            targetMap.remove(target);
            if (target.isDeadOrDying())
                continue;

            Vec3 position = attacker.position();
            target.teleportTo(position.x, position.y, position.z);
            target.removeEffect(YummyEffects.ECHO_MARK.get());

            MobEffectInstance stunEffectInstance = new MobEffectInstance(YummyEffects.STUN.get(), 20 * 4);
            target.addEffect(stunEffectInstance);
            Echo.overlapEcho(target);
            succeed = true;
        }

        return succeed;
    }

    protected EchoMark() {
        super(MobEffectCategory.HARMFUL, 0x29DFEB);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!targetMap.containsKey(livingEntity))
            livingEntity.removeEffect(YummyEffects.ECHO_MARK.get());
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
