package com.lalaalal.yummy.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;

public class FollowTargetGoal extends Goal {
    private final Mob mob;
    private final PathNavigation navigation;
    private final double stopDistance;
    private final double speedModifier;
    private int timeToRecalculatePath;

    public FollowTargetGoal(Mob mob) {
        this(mob, 1, 1);
    }

    public FollowTargetGoal(Mob mob, double stopDistance, double speedModifier) {
        this.mob = mob;
        this.navigation = mob.getNavigation();
        this.stopDistance = stopDistance;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null;
    }

    @Override
    public void start() {
        this.timeToRecalculatePath = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().setLookAt(target, 10.0F, (float) this.mob.getMaxHeadXRot());
            if (--this.timeToRecalculatePath <= 0) {
                this.timeToRecalculatePath = this.adjustedTickDelay(10);
                double dx = this.mob.getX() - target.getX();
                double dy = this.mob.getY() - target.getY();
                double dz = this.mob.getZ() - target.getZ();
                double distanceSquare = dx * dx + dy * dy + dz * dz;
                if (!(distanceSquare <= (this.stopDistance * this.stopDistance))) {
                    navigation.moveTo(target, this.speedModifier);
                } else {
                    navigation.stop();
                }
            }
        }
    }

    @Override
    public void stop() {
        navigation.stop();
    }
}
