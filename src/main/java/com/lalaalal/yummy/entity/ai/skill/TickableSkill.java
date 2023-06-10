package com.lalaalal.yummy.entity.ai.skill;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class TickableSkill {
    protected final PathfinderMob usingEntity;
    protected final Level level;
    protected final int cooldown;
    protected final int animationDuration;
    protected final int tickDuration;

    public TickableSkill(PathfinderMob usingEntity, int cooldown, int animationDuration, int tickDuration) {
        this.usingEntity = usingEntity;
        this.level = usingEntity.level();
        this.cooldown = cooldown;
        this.animationDuration = animationDuration;
        this.tickDuration = tickDuration;
    }

    public abstract String getBaseName();

    public String getName() {
        return getBaseName();
    }

    public abstract boolean canUse();

    public int getCooldown() {
        return cooldown;
    }

    /**
     * @param tick Tick
     * @return True when animation end
     */
    public boolean animationTick(int tick) {
        return tick >= animationDuration;
    }

    /**
     * @param tick Tick
     * @return True when using skill end
     */
    public boolean tick(int tick) {
        return tick >= tickDuration;
    }

    public abstract void interrupt();

    @Nullable
    public TickableSkill getNextSkill() {
        return null;
    }
}
