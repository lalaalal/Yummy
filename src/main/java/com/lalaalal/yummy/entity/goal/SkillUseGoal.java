package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.LegacySkillUsable;
import com.lalaalal.yummy.entity.skill.Skill;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public class SkillUseGoal extends Goal {
    protected final Mob mob;
    protected LegacySkillUsable skillUsable;
    protected final Skill skill;
    private final Level level;
    private long prevUsedGameTime;
    protected int tick;

    public SkillUseGoal(Mob mob, Skill skill) {
        this.mob = mob;
        this.skill = skill;

        this.level = mob.getLevel();
        if (mob instanceof LegacySkillUsable skillUsableMob)
            this.skillUsable = skillUsableMob;
    }

    private boolean canUseSkill() {
        return skill.canUse() &&
                level.getGameTime() - prevUsedGameTime > skill.getCooldown();
    }

    @Override
    public boolean canUse() {
        if (skillUsable != null && skillUsable.isUsingSkill())
            return false;
        return canUseSkill();
    }

    @Override
    public boolean canContinueToUse() {
        if (tick <= skill.getWarmup())
            return true;
        return canUseSkill();
    }

    @Override
    public void start() {
        super.start();
        tick = 0;
    }

    @Override
    public void tick() {
        if (tick == 0) {
            if (skillUsable != null)
                skillUsable.setUsingSkill(true);
            skill.showEffect();
        } else if (tick >= skill.getWarmup()) {
            skill.useSkill();
            skill.endEffect();
            if (skillUsable != null)
                skillUsable.setUsingSkill(false);

            prevUsedGameTime = level.getGameTime();
        }
        tick += 1;
    }

    @Override
    public void stop() {
        super.stop();
        if (skillUsable != null)
            skillUsable.setUsingSkill(false);
    }
}
