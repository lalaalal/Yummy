package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.entity.CameraShakingEntity;
import com.lalaalal.yummy.entity.Meteor;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedList;
import java.util.Queue;

public class DescentAndFallMeteorSkill extends TickableSkill {
    private static final int DESCENT_TICK = 14;
    private boolean meteorMark = false;
    private final Queue<Meteor> meteors = new LinkedList<>();
    private final int meteorGroupNum = 4;
    private double rotationFactor;


    public DescentAndFallMeteorSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 10, 60);
    }

    @Override
    public String getBaseName() {
        return "descent_fall_meteor";
    }

    public void setMeteorMark(boolean meteorMark) {
        this.meteorMark = meteorMark;
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public boolean animationTick(int tick) {
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0) {
            teleportAboveTarget();
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        }
        if (tick == DESCENT_TICK)
            descend();
        if (tick == DESCENT_TICK + 1)
            summonMeteors();
        if (tick >= DESCENT_TICK + 3)
            fallMeteor(tick - DESCENT_TICK + 3);
        if (tick == DESCENT_TICK + 3 && usingEntity instanceof CameraShakingEntity cameraShakingEntity)
            cameraShakingEntity.setCameraShaking(false);
        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);

        return super.tick(tick);
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    private void teleportAboveTarget() {
        LivingEntity target = usingEntity.getTarget();
        if (target == null)
            return;
        usingEntity.setNoGravity(true);
        Vec3 viewVector = usingEntity.getViewVector(0);
        rotationFactor = Math.atan(viewVector.z / viewVector.x) - Math.PI / 2.25;
        if (viewVector.x < 0)
            rotationFactor += Math.PI;
        usingEntity.moveTo(target.getX() - viewVector.x, target.getY() + 10, target.getZ() - viewVector.z);
    }

    private void descend() {
        BlockPos pos = YummyUtil.findHorizonPos(usingEntity.getOnPos(), level).above();
        Vec3 targetPos = new Vec3(usingEntity.getX(), pos.getY(), usingEntity.getZ());
        usingEntity.moveTo(targetPos);
        AABB area = usingEntity.getBoundingBox().inflate(4);
        LivingEntity entity = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, targetPos.x, targetPos.y, targetPos.z, area);
        if (entity != null) {
            entity.hurt(new EntityDamageSource("descent", usingEntity), 1);
            HerobrineMark.overlapMark(entity, usingEntity);
        }
        usingEntity.setNoGravity(false);
        if (usingEntity instanceof CameraShakingEntity cameraShakingEntity)
            cameraShakingEntity.setCameraShaking(true);
    }

    private void summonMeteors() {
        Vec3 basePos = new Vec3(usingEntity.getX(), usingEntity.getY() + 10, usingEntity.getZ());
        for (int groupIndex = 0; groupIndex < meteorGroupNum; groupIndex++) {
            double xGroupOffset = Math.cos(Math.PI / meteorGroupNum * groupIndex + rotationFactor) * 7;
            double zGroupOffset = Math.sin(Math.PI / meteorGroupNum * groupIndex + rotationFactor) * 7;
            Vec3 groupOffset = new Vec3(xGroupOffset, 0, zGroupOffset);
            Vec3 groupPos = basePos.add(groupOffset);
            int meteorPerGroupNum = 3;
            for (int i = 0; i < meteorPerGroupNum; i++) {
                double xOffset = Math.cos(2 * Math.PI / meteorPerGroupNum * i);
                double zOffset = Math.sin(2 * Math.PI / meteorPerGroupNum * i);
                Vec3 offset = new Vec3(xOffset, 0, zOffset);
                Meteor meteor = createMeteor(groupPos, offset, i == meteorPerGroupNum - 1);
                level.addFreshEntity(meteor);
                meteors.add(meteor);
            }
        }
    }

    private Meteor createMeteor(Vec3 basePos, Vec3 offset, boolean explosion) {
        Meteor meteor = new Meteor(level, usingEntity, 0, 0, 0, meteorMark);
        meteor.setPos(basePos.add(offset));
        meteor.setExplosion(explosion);
        meteor.setExplosionPower(2);
        return meteor;
    }

    private void fallMeteor(int relativeTick) {
        if (relativeTick % 10 == 0) {
            int groupIndex = relativeTick / 10;
            double theta = (Math.PI / meteorGroupNum * groupIndex) + Math.PI * 0.75 + rotationFactor;
            double x = Math.cos(theta) * 0.03;
            double z = Math.sin(theta) * 0.03;

            for (int i = 0; i < 3; i++) {
                if (meteors.isEmpty())
                    return;
                Meteor meteor = meteors.poll();
                meteor.xPower = x;
                meteor.yPower = -0.1;
                meteor.zPower = z;
                meteor.setDeltaMovement(Vec3.ZERO);
            }
        }
    }
}
