package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.Herobrine;
import org.jetbrains.annotations.Nullable;

public class CorruptedWaveSkill extends NarakaWaveSkill {
    public static final String NAME = "corruption_wave";

    private final Herobrine herobrine;
    private final TickableSkill nextSkill;

    public CorruptedWaveSkill(Herobrine usingEntity, int cooldown) {
        super(usingEntity, cooldown, YummyBlocks.CORRUPTED_POLLUTED_BLOCK.get().defaultBlockState());
        this.herobrine = usingEntity;
        this.nextSkill = new TransformBlockSkill(usingEntity);
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        return herobrine.canUseCorruptedWave();
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick < animationDuration + NarakaWaveSkill.WAVE_DURATION)
            NarakaStormSkill.pullEntities(level, usingEntity);
        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0)
            NarakaStormSkill.wave(level, usingEntity, true);

        if (tick == tickDuration)
            herobrine.resetCorruptedWaveStack();
        return super.tick(tick);
    }

    @Override
    public void interrupt() {

    }

    @Override
    public @Nullable TickableSkill getNextSkill() {
        return nextSkill;
    }
}
