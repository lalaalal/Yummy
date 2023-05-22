package com.lalaalal.yummy.entity.ai.skill;

import org.jetbrains.annotations.Nullable;

public interface SkillUsable {
    void setUsingSkill(@Nullable TickableSkill skill);

    String getUsingSkillName();
}
