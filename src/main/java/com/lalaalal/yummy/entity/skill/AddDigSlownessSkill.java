package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.effect.YummyEffectRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AddDigSlownessSkill extends Skill {
    public AddDigSlownessSkill(Mob usingEntity) {
        super(usingEntity, 40, 5);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        AABB area = usingEntity.getBoundingBox().inflate(3.0);
        List<LivingEntity> livingEntities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity livingEntity : livingEntities) {
            if (livingEntity.getEffect(YummyEffectRegister.HEROBRINE_MARK.get()) == null)
                continue;
            MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 4);
            livingEntity.addEffect(mobEffectInstance);
            HerobrineMark.reduceMark(livingEntity);
        }
    }
}
