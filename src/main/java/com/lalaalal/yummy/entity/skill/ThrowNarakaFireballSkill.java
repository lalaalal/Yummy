package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.Meteor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class ThrowNarakaFireballSkill extends TickableSkill {
    private Meteor meteor;
    private double originalKnockbackResistance;

    public ThrowNarakaFireballSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 20, 5);
    }

    private void increaseKnockbackResistance() {
        AttributeInstance attributeInstance = usingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attributeInstance != null) {
            originalKnockbackResistance = attributeInstance.getBaseValue();
            attributeInstance.setBaseValue(2);
        }
    }

    private void restoreKnockbackResistance() {
        AttributeInstance attributeInstance = usingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attributeInstance != null)
            attributeInstance.setBaseValue(originalKnockbackResistance);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = usingEntity.getTarget();
        return target != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0) {
            increaseKnockbackResistance();
            Vec3 offset = YummyUtil.calcXZRotation(usingEntity.getViewVector(1), Math.PI / 2, 1)
                    .multiply(0, 1, 0)
                    .add(0, 2, 0);
            meteor = new Meteor(level, usingEntity, 0, 0, 0, true);
            meteor.move(MoverType.SELF, offset);
            level.addFreshEntity(meteor);
        } else if (!meteor.isAlive()) {
            return true;
        } else if (tick < animationDuration) {
            meteor.setDeltaMovement(Vec3.ZERO);
        }

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (!meteor.isAlive())
            return true;
        if (tick == 0) {
            restoreKnockbackResistance();
            Vec3 viewVector = usingEntity.getViewVector(1);
            meteor.setPos(usingEntity.getEyePosition());
            meteor.shoot(viewVector.x, viewVector.y, viewVector.z, 1, 0);
        }

        return super.tick(tick);
    }
}
