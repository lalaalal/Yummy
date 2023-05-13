package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.SkillUsable;
import com.lalaalal.yummy.entity.skill.TickableSkill;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TickableSkillUseGoal<T extends PathfinderMob & SkillUsable> extends Goal {
    private final ArrayList<TickableSkill> skills = new ArrayList<>();
    private final ArrayList<TickableSkill> mustUseFistSkills = new ArrayList<>();
    private final ArrayList<SkillFinishListener> skillFinishListeners = new ArrayList<>();
    private final Map<TickableSkill, Long> skillUsedTimeMap = new HashMap<>();
    private final T usingEntity;
    private final Level level;
    private TickableSkill skillToUse;
    private TickableSkill usingSkill;
    private int tick = 0;
    private int animationTick = 0;
    private long lastSkillEndTime;
    private int skillUseInterval = 20;

    public TickableSkillUseGoal(T usingEntity) {
        this.usingEntity = usingEntity;
        this.level = usingEntity.level;
        this.lastSkillEndTime = level.getGameTime();
    }

    public void setSkillUseInterval(int skillUseInterval) {
        this.skillUseInterval = skillUseInterval;
    }

    public void addSkill(TickableSkill skill) {
        skills.add(skill);
        skillUsedTimeMap.put(skill, 0L);
    }

    public void addSkill(TickableSkill skill, boolean mustUseFirst) {
        if (!mustUseFirst) {
            addSkill(skill);
            return;
        }
        mustUseFistSkills.add(skill);
        skillUsedTimeMap.put(skill, 0L);
        mustUseFistSkills.sort((o1, o2) -> o2.getCooldown() - o1.getCooldown());
    }

    public void removeSkill(TickableSkill skill) {
        skills.remove(skill);
        skillUsedTimeMap.remove(skill);
    }

    protected void setUsingSkill(@Nullable TickableSkill skill) {
        this.usingSkill = skill;
        usingEntity.setUsingSkill(skill);
    }

    public void addSkillFinishListener(SkillFinishListener listener) {
        skillFinishListeners.add(listener);
    }

    @Nullable
    private TickableSkill findUsableSkill() {
        for (TickableSkill skill : mustUseFistSkills) {
            if (skill.getCooldown() < level.getGameTime() - skillUsedTimeMap.get(skill)
                    && skill.canUse())
                return skill;
        }

        Collections.shuffle(skills);
        for (TickableSkill skill : skills) {
            if (skill.getCooldown() < level.getGameTime() - skillUsedTimeMap.get(skill)
                    && skill.canUse())
                return skill;
        }
        return null;
    }

    @Override
    public boolean canUse() {
        if (level.getGameTime() - lastSkillEndTime < skillUseInterval)
            return false;
        skillToUse = findUsableSkill();

        return skillToUse != null;
    }

    @Override
    public boolean canContinueToUse() {
        return usingSkill != null;
    }

    @Override
    public void start() {
        tick = 0;
        animationTick = 0;
        setUsingSkill(skillToUse);
    }

    @Override
    public void tick() {
        if (usingSkill == null)
            return;

        usingEntity.getNavigation().stop();
        if (usingSkill.animationTick(animationTick++)) {
            if (usingSkill.tick(tick++))
                finishSkill();
        }
    }

    private void finishSkill() {
        for (SkillFinishListener skillFinishListener : skillFinishListeners)
            skillFinishListener.onSkillFinish(usingSkill, false);
        skillUsedTimeMap.put(usingSkill, level.getGameTime());
        lastSkillEndTime = level.getGameTime();
        TickableSkill nextSkill = usingSkill.getNextSkill();
        setUsingSkill(nextSkill);
        skillToUse = nextSkill;
        if (nextSkill != null)
            start();
    }

    public void interrupt() {
        if (usingSkill != null) {
            for (SkillFinishListener skillFinishListener : skillFinishListeners)
                skillFinishListener.onSkillFinish(usingSkill, true);
            usingSkill.interrupt();
        }
        setUsingSkill(null);
        skillToUse = null;
    }

    @FunctionalInterface
    public interface SkillFinishListener {
        void onSkillFinish(TickableSkill skill, boolean interrupted);
    }
}
