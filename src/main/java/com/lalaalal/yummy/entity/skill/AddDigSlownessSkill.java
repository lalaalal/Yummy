package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.effect.YummyEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AddDigSlownessSkill extends Skill {
    private boolean used;

    public AddDigSlownessSkill(Mob usingEntity) {
        super(usingEntity, 40, 5);
    }

    @Override
    public boolean canUse() {
        return !used;
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        AABB area = usingEntity.getBoundingBox().inflate(3.0);
        List<LivingEntity> livingEntities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity livingEntity : livingEntities) {
            if (livingEntity.getEffect(YummyEffects.HEROBRINE_MARK.get()) == null)
                continue;
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 1000000, 4);
            livingEntity.addEffect(mobEffectInstance);
            livingEntity.removeEffect(YummyEffects.HEROBRINE_MARK.get());
        }

        used = true;
    }
}
