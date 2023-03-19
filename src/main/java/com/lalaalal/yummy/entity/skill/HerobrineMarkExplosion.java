package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.effect.MarkEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HerobrineMarkExplosion extends HerobrineExplosion {
    public HerobrineMarkExplosion(Mob usingEntity) {
        super(usingEntity);
        setExplosionRadius(3f);
    }

    @Override
    public void showEffect() {
        super.showEffect();
    }

    @Override
    public void useSkill() {
        super.useSkill();

        for (LivingEntity entity : getNearbyEntities()) {
            MarkEffect.overlapMark(entity);
        }
    }

    private List<LivingEntity> getNearbyEntities() {
        Level level = usingEntity.getLevel();
        BlockPos start = new BlockPos(usingEntity.getX() + 5, usingEntity.getY() + 5, usingEntity.getZ() + 5);
        BlockPos end = new BlockPos(usingEntity.getX() - 5, usingEntity.getY() - 5, usingEntity.getZ() - 5);

        return level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, new AABB(start, end));
    }
}
