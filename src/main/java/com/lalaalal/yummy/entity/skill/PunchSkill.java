package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class PunchSkill extends TickableSkill {
    public static final String BASE_NAME = "shadow_punch";
    private static final String[] RANDOM = {"one", "two", "three"};
    private static final double ATTACK_REACH = 2;
    private String currentSkillName = "shadow_punch_one";

    public PunchSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 5, 15);
    }

    @Override
    public String getBaseName() {
        return BASE_NAME;
    }

    @Override
    public String getName() {
        return currentSkillName;
    }

    @Override
    public boolean canUse() {
        currentSkillName = BASE_NAME + "_" + RANDOM[usingEntity.getRandom().nextInt(3)];
        LivingEntity target = usingEntity.getTarget();
        return target != null && usingEntity.distanceToSqr(target) < ATTACK_REACH * ATTACK_REACH;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0) {
            LivingEntity target = usingEntity.getTarget();
            if (target != null)
                usingEntity.doHurtTarget(target);
        }

        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);

        return super.tick(tick);
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
    }
}
