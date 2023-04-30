package com.lalaalal.yummy.entity.goal;

import com.lalaalal.yummy.entity.skill.SkillUsable;
import com.lalaalal.yummy.entity.skill.TickableSkill;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class TickableSkillUseGoal<T extends PathfinderMob & SkillUsable> extends Goal {
    private final Map<TickableSkill, Long> skillUsedTimeMap = new HashMap<>();
    private final T usingEntity;
    private final Level level;
    private TickableSkill usingSkill;
    private int tick = 0;
    private int animationTick = 0;

    public TickableSkillUseGoal(T usingEntity) {
        this.usingEntity = usingEntity;
        this.level = usingEntity.level;
    }

    public void addSkill(TickableSkill skill) {
        skillUsedTimeMap.put(skill, 0L);
    }

    protected void setUsingSkill(@Nullable TickableSkill skill) {
        this.usingSkill = skill;
        usingEntity.setUsingSkill(skill);
    }

    @Nullable
    private TickableSkill findUsableSkill() {
        for (TickableSkill skill : skillUsedTimeMap.keySet()) {
            if (skill.getCooldown() < level.getGameTime() - skillUsedTimeMap.get(skill)) {
                return skill;
            }
        }
        return null;
    }

    @Override
    public boolean canUse() {
        setUsingSkill(findUsableSkill());

        return usingSkill != null;
    }

    @Override
    public boolean canContinueToUse() {
        return usingSkill != null;
    }

    @Override
    public void start() {
        tick = 0;
        animationTick = 0;
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
            }
        }
    }
}
