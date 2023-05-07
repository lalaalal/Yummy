package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
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
    private static final EntityDataAccessor<String> DATA_USING_SKILL_NAME = SynchedEntityData.defineId(SkillUsableMob.class, EntityDataSerializers.STRING);

    private final Map<TickableSkill, String> skillNames = new HashMap<>();
    private final Map<String, TickableSkill> skills = new HashMap<>();
    private final TickableSkillUseGoal<SkillUsableMob> skillUseGoal = new TickableSkillUseGoal<>(this);

    protected SkillUsableMob(EntityType<? extends SkillUsableMob> entityType, Level level) {
        super(entityType, level);
        this.goalSelector.addGoal(1, skillUseGoal);
        registerSkills();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_USING_SKILL_NAME, "none");
    }

    @Override
    public void setUsingSkill(@Nullable TickableSkill skill) {
        if (skill == null) {
            this.entityData.set(DATA_USING_SKILL_NAME, "none");
            return;
        }
        String name = skillNames.get(skill);
        YummyMod.LOGGER.debug("Setting using skill to " + name);
        this.entityData.set(DATA_USING_SKILL_NAME, name);
    }

    @Override
    public String getUsingSkillName() {
        return this.entityData.get(DATA_USING_SKILL_NAME);
    }

    protected abstract void registerSkills();

    public void registerSkill(TickableSkill skill, String name) {
        if (!skills.containsKey(name)) {
            skills.put(name, skill);
            skillNames.put(skill, name);
            skillUseGoal.addSkill(skill);
        }
    }

    public void removeSkill(String name) {
        TickableSkill skill = skills.get(name);
        if (skill != null) {
            skills.remove(name);
            skillNames.remove(skill);
            skillUseGoal.removeSkill(skill);
        }
    }

    @Nullable
    public TickableSkill getSkill(String name) {
        return skills.get(name);
    }

    public void interrupt() {
        skillUseGoal.interrupt();
    }
}
