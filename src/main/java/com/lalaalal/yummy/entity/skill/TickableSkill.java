package com.lalaalal.yummy.entity.skill;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public abstract class TickableSkill {
    protected final PathfinderMob usingEntity;
    protected final Level level;
    protected final int cooldown;

    public TickableSkill(PathfinderMob usingEntity, int cooldown) {
        this.usingEntity = usingEntity;
        this.level = usingEntity.level;
        this.cooldown = cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    /**
     * @param tick Tick
     * @return True when animation end
     */
    public abstract boolean animationTick(final int tick);

    /**
     * @param tick Tick
     * @return True when using skill end
     */
    public abstract boolean tick(final int tick);
}
