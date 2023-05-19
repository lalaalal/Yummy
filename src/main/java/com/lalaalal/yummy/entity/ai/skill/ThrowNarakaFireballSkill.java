package com.lalaalal.yummy.entity.ai.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.Meteor;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class ThrowNarakaFireballSkill extends TickableSkill {
    public static final String NAME = "throw_naraka_fireball";

    private Meteor meteor;

    public ThrowNarakaFireballSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 10, 5);
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = usingEntity.getTarget();
        return target != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0) {
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
            Vec3 offset = YummyUtil.calcXZRotation(usingEntity.getViewVector(1), Math.PI / -2, 0.8)
                    .add(0, 1.4, 0);
            meteor = new Meteor(level, usingEntity, 0, 0, 0, true);
            meteor.move(MoverType.SELF, offset);
            level.addFreshEntity(meteor);
        } else if (tick < animationDuration) {
            meteor.setDeltaMovement(Vec3.ZERO);
        }

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0) {
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
            Vec3 viewVector = usingEntity.getViewVector(0);
            meteor.setPos(usingEntity.getEyePosition());
            meteor.shoot(viewVector.x, viewVector.y, viewVector.z, 1, 0);
        }

        return super.tick(tick);
    }

    @Override
    public void interrupt() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }
}
