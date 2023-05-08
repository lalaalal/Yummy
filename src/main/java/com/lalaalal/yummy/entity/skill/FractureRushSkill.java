package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.FractureEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class FractureRushSkill extends TickableSkill {
    private static final double RUSH_DISTANCE = 6.66;

    public FractureRushSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 11, 5);
    }

    @Override
    public String getBaseName() {
        return "fracture_rush";
    }

    @Override
    public boolean canUse() {
        LivingEntity target = usingEntity.getTarget();
        return target != null && usingEntity.distanceToSqr(target) < RUSH_DISTANCE * RUSH_DISTANCE;
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0) {
            Vec3 originalPos = usingEntity.position();
            Vec3 targetPos = originalPos.add(usingEntity.getViewVector(0).scale(RUSH_DISTANCE));
            usingEntity.moveTo(targetPos);
            Vec3 fracturePos = originalPos.add(targetPos).scale(0.5);
            FractureEntity fractureEntity = new FractureEntity(level, fracturePos);
            fractureEntity.setXRot(usingEntity.getXRot());
            fractureEntity.setYRot(usingEntity.getYRot());
            fractureEntity.setSpawner(usingEntity);
            level.addFreshEntity(fractureEntity);
            LivingEntity target = usingEntity.getTarget();
            if (target != null)
                usingEntity.doHurtTarget(target);
        }

        return super.tick(tick);
    }

    @Override
    public void interrupted() {

    }
}
