package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.Skill;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class SkillUseGoal extends Goal {
    protected final Mob mob;
    protected final Skill skill;
    private final Level level;
    private long prevUsedGameTime;
    private final int skillPrepareInterval;
    protected int tick;

    public SkillUseGoal(Mob mob, Function<Mob, Skill> skillProvider) {
        this.mob = mob;
        this.skill = skillProvider.apply(mob);

        this.level = mob.getLevel();
        this.skillPrepareInterval = 20;
    }

    public SkillUseGoal(Mob mob, Function<Mob, Skill> skillProvider, int skillPrepareInterval) {
        this.mob = mob;
        this.skill = skillProvider.apply(mob);

        this.level = mob.getLevel();
        this.skillPrepareInterval = skillPrepareInterval;
    }

    @Override
    public boolean canUse() {
        return skill.canUse() &&
                level.getGameTime() - prevUsedGameTime > skill.getCooldown();
    }

    @Override
    public boolean canContinueToUse() {
        if (tick < skillPrepareInterval)
            return true;
        return canUse();
    }

    @Override
    public void start() {
        super.start();
        tick = 0;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        if (tick == 0) {
            skill.showEffect();
            mob.swing(InteractionHand.OFF_HAND);
        } else if (tick >= skillPrepareInterval) {
            skill.useSkill();
            prevUsedGameTime = level.getGameTime();
        }
        tick += 1;
    }
}
