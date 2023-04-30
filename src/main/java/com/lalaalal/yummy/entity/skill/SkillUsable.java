package com.lalaalal.yummy.entity.skill;

import org.jetbrains.annotations.Nullable;

public interface SkillUsable {
    void setUsingSkill(@Nullable TickableSkill skill);

    String getUsingSkillName();
}
