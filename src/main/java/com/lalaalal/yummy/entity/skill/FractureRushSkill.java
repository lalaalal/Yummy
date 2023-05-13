package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.FractureEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class FractureRushSkill extends TickableSkill {
    public static final String NAME = "fracture_rush";
    private static final double RUSH_DISTANCE = 6.66;

    public FractureRushSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 11, 5);
    }

    @Override
    public String getBaseName() {
        return NAME;
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
            Vec3 viewVector = usingEntity.getViewVector(0);
            Vec3 targetPos = originalPos.add(viewVector.scale(RUSH_DISTANCE));
            int y = YummyUtil.findHorizonPos(new BlockPos(targetPos), level).above().getY();
            usingEntity.moveTo(new Vec3(targetPos.x, y, targetPos.z));
            Vec3 fracturePos = originalPos.add(targetPos).scale(0.5);

            createFracture(fracturePos, 75);
            LivingEntity target = usingEntity.getTarget();
            if (target != null)
                target.hurt(new EntityDamageSource(YummyMod.MOD_ID + ".herobrine.fracture_rush", usingEntity), 6);
        }

        return super.tick(tick);
    }

    private void createFracture(Vec3 fracturePos, int rotateDegree) {
        FractureEntity fractureEntity = new FractureEntity(level, fracturePos, rotateDegree);
        fractureEntity.setLifetime(100);
        fractureEntity.setXRot(usingEntity.getXRot());
        fractureEntity.setYRot(usingEntity.getYRot());
        fractureEntity.setSpawner(usingEntity);
        level.addFreshEntity(fractureEntity);
    }

    @Override
    public void interrupt() {

    }
}
