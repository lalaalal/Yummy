package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.SkillUsable;
import com.lalaalal.yummy.entity.skill.TickableSkill;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TickableSkillUseGoal<T extends PathfinderMob & SkillUsable> extends Goal {
    private final ArrayList<TickableSkill> skills = new ArrayList<>();
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
        skills.sort((o1, o2) -> o2.getCooldown() - o1.getCooldown());
    }

    protected void setUsingSkill(@Nullable TickableSkill skill) {
        this.usingSkill = skill;
        usingEntity.setUsingSkill(skill);
    }

    @Nullable
    private TickableSkill findUsableSkill() {
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
            if (usingSkill.tick(tick++)) {
                skillUsedTimeMap.put(usingSkill, level.getGameTime());
                setUsingSkill(null);
                skillToUse = null;
                lastSkillEndTime = level.getGameTime();
            }
        }
    }
}
