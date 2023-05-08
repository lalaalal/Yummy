package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.NarakaStormEntity;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.world.entity.PathfinderMob;
import org.jetbrains.annotations.Nullable;

public class NarakaStormSkill extends TickableSkill {
    private final DescentAndFallMeteorSkill nextSkill;

    public NarakaStormSkill(PathfinderMob usingEntity, int cooldown, DescentAndFallMeteorSkill nextSkill) {
        super(usingEntity, cooldown, 10, 10);
        this.nextSkill = nextSkill;
    }

    @Override
    public String getBaseName() {
        return "naraka_storm";
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick % 4 == 0)
            wave();

        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);

        return super.tick(tick);
    }

    private void wave() {
        NarakaStormEntity narakaStormEntity = new NarakaStormEntity(level, 2, 4, usingEntity);
        narakaStormEntity.setMaxRadius(15);
        narakaStormEntity.setSpeed(0.4);

        level.addFreshEntity(narakaStormEntity);
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.IGNORE_KNOCKBACK);
    }

    @Override
    public @Nullable TickableSkill getNextSkill() {
        return nextSkill;
    }
}
