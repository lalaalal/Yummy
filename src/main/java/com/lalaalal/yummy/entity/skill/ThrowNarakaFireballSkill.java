package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.MarkFireball;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class ThrowNarakaFireballSkill extends TickableSkill {
    private MarkFireball markFireball;
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
        return usingEntity.getTarget() != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0) {
            increaseKnockbackResistance();
            Vec3 offset = YummyUtil.calcOrthogonal(usingEntity.getViewVector(1), Math.PI / 2, 1).add(0, 2, 0);
            markFireball = new MarkFireball(level, usingEntity, 0, 0, 0);
            markFireball.move(MoverType.SELF, offset);
            level.addFreshEntity(markFireball);
        } else if (!markFireball.isAlive()) {
            return true;
        } else if (tick < animationDuration) {
            markFireball.setDeltaMovement(Vec3.ZERO);
        }

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (!markFireball.isAlive())
            return true;
        if (tick == 0) {
            restoreKnockbackResistance();
            Vec3 viewVector = usingEntity.getViewVector(1);
            markFireball.setPos(usingEntity.getEyePosition());
            markFireball.shoot(viewVector.x, viewVector.y, viewVector.z, 1, 0);
        }

        return super.tick(tick);
    }
}
