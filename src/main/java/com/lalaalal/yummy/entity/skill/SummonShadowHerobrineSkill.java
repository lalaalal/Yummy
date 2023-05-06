package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyAttributeModifiers;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class SummonShadowHerobrineSkill extends TickableSkill {
    private static final int SHADOW_LIMIT = 3;
    private final ArrayList<ShadowHerobrine> shadowHerobrines = new ArrayList<>(SHADOW_LIMIT);

    public SummonShadowHerobrineSkill(PathfinderMob usingEntity, int cooldown) {
        super(usingEntity, cooldown, 0, 40);
    }

    @Override
    public boolean canUse() {
        return shadowHerobrines.size() < SHADOW_LIMIT;
    }

    @Override
    public boolean tick(int tick) {
        if (tick == 0)
            YummyAttributeModifiers.addTransientModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
        if (tick % 10 == 0 && shadowHerobrines.size() < SHADOW_LIMIT) {
            Vec3 position = new Vec3(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ());
            ShadowHerobrine shadowHerobrine = new ShadowHerobrine(level, position);
            shadowHerobrine.setParent(usingEntity);
            level.addFreshEntity(shadowHerobrine);
            shadowHerobrines.add(shadowHerobrine);
            YummyAttributeModifiers.addTransientModifier(shadowHerobrine, YummyAttributeModifiers.PREVENT_MOVING);
        }

        if (tick == tickDuration) {
            YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
            for (ShadowHerobrine shadowHerobrine : shadowHerobrines)
                YummyAttributeModifiers.removeModifier(shadowHerobrine, YummyAttributeModifiers.PREVENT_MOVING);
        }
        return super.tick(tick);
    }

    @Override
    public void interrupted() {
        YummyAttributeModifiers.removeModifier(usingEntity, YummyAttributeModifiers.PREVENT_MOVING);
    }
}
