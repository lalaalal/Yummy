package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class SummonShadowHerobrineSkill extends TickableSkill {
    public static final String NAME = "summon_shadow";
    private static final int SHADOW_LIMIT = 3;
    private final Herobrine herobrine;
    private final ArrayList<ShadowHerobrine> shadowHerobrines = new ArrayList<>(SHADOW_LIMIT);

    public SummonShadowHerobrineSkill(Herobrine usingEntity, int cooldown) {
        super(usingEntity, cooldown, 10, 10);
        this.herobrine = usingEntity;
    }

    public ArrayList<ShadowHerobrine> getShadowHerobrines() {
        return shadowHerobrines;
    }

    @Override
    public String getBaseName() {
        return NAME;
    }

    @Override
    public boolean canUse() {
        ArrayList<ShadowHerobrine> deadHerobrines = new ArrayList<>(SHADOW_LIMIT);
        for (ShadowHerobrine shadowHerobrine : shadowHerobrines) {
            if (shadowHerobrine.isDeadOrDying())
                deadHerobrines.add(shadowHerobrine);
        }
        shadowHerobrines.removeAll(deadHerobrines);

        return shadowHerobrines.size() < SHADOW_LIMIT;
    }

    @Override
    public boolean animationTick(int tick) {
        if (tick % 3 == 0 && shadowHerobrines.size() < SHADOW_LIMIT) {
            int index = tick / 3;
            double theta = Math.PI * 2 / 3 * index;
            double x = Math.cos(theta) * 3 + usingEntity.getX();
            double z = Math.sin(theta) * 3 + usingEntity.getZ();
            int y = YummyUtil.findHorizonPos(new BlockPos(x, usingEntity.getY(), z), level).getY() + 1;
            Vec3 targetPos = new Vec3(x, y, z);
            ShadowHerobrine shadowHerobrine = new ShadowHerobrine(level, usingEntity.position(), targetPos);
            shadowHerobrine.changeSpeed(herobrine.calcCurrentShadowSpeed());
            shadowHerobrine.setHerobrine(herobrine);
            shadowHerobrine.setTickOffset(index);
            if (herobrine.getPhase() == 3)
                shadowHerobrine.registerSkill(new FractureRushSkill(shadowHerobrine, 20 * 5));
            shadowHerobrines.add(shadowHerobrine);
        }

        return super.animationTick(tick);
    }

    @Override
    public boolean tick(int tick) {
        if (tick < SHADOW_LIMIT) {
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
            ShadowHerobrine shadowHerobrine = shadowHerobrines.get(tick);
            if (shadowHerobrine != null)
                level.addFreshEntity(shadowHerobrine);
        }

        if (tick == tickDuration)
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
        return super.tick(tick);
    }

    @Override
    public void interrupt() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
    }
}
