package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.entity.goal.TickableSkillUseGoal;
import com.lalaalal.yummy.entity.skill.SkillUsable;
import com.lalaalal.yummy.entity.skill.TickableSkill;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class SkillUsableMob extends PathfinderMob implements SkillUsable {
    protected static final String SKILL_NONE = "none";
    private static final EntityDataAccessor<String> DATA_USING_SKILL_NAME = SynchedEntityData.defineId(SkillUsableMob.class, EntityDataSerializers.STRING);

    private final Map<String, TickableSkill> skills = new HashMap<>();
    private final TickableSkillUseGoal<SkillUsableMob> skillUseGoal = new TickableSkillUseGoal<>(this);

    protected SkillUsableMob(EntityType<? extends SkillUsableMob> entityType, Level level) {
        super(entityType, level);
        if (!level.isClientSide)
            this.goalSelector.addGoal(1, skillUseGoal);
        registerSkills();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_USING_SKILL_NAME, SKILL_NONE);
    }

    @Override
    public void setUsingSkill(@Nullable TickableSkill skill) {
        if (skill == null) {
            this.entityData.set(DATA_USING_SKILL_NAME, SKILL_NONE);
            return;
        }
        String name = skill.getName();
        this.entityData.set(DATA_USING_SKILL_NAME, name);
    }

    @Override
    public String getUsingSkillName() {
        return this.entityData.get(DATA_USING_SKILL_NAME);
    }

    protected abstract void registerSkills();

    public void registerSkill(TickableSkill skill) {
        registerSkill(skill, false);
    }

    public void registerSkill(TickableSkill skill, boolean mustUseFirst) {
        String name = skill.getBaseName();
        if (!skills.containsKey(name)) {
            skills.put(name, skill);
            skillUseGoal.addSkill(skill, mustUseFirst);
        }
    }

    public void removeSkill(String name) {
        TickableSkill skill = skills.get(name);
        if (skill != null) {
            skills.remove(name);
            skillUseGoal.removeSkill(skill);
        }
    }

    @Nullable
    public TickableSkill getSkill(String name) {
        return skills.get(name);
    }

    public void addSkillFinishListener(TickableSkillUseGoal.SkillFinishListener listener) {
        skillUseGoal.addSkillFinishListener(listener);
    }

    public void interrupt() {
        skillUseGoal.interrupt();
    }
}
