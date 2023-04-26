package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.SkillUsable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FollowTargetGoal<T extends Mob & SkillUsable> extends Goal {
    private final T skillUsableMob;

    public FollowTargetGoal(T skillUsableMob) {
        this.skillUsableMob = skillUsableMob;
    }

    @Override
    public boolean canUse() {
        return skillUsableMob.isUsingSkill();
    }

    @Override
    public void start() {
        LivingEntity target = skillUsableMob.getTarget();
        if (target != null) {
            skillUsableMob.getNavigation().moveTo(target, 1);
            skillUsableMob.lookAt(target, -0.3f, 0);
        }
    }

    @Override
    public void stop() {
//        skillUsableMob.getNavigation().stop();
    }
}
