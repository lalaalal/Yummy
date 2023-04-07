package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.effect.MarkEffect;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ShowHerobrineMarkPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MarkExplosionSkill extends ExplosionSkill {
    public static int COOLDOWN = 100;

    public MarkExplosionSkill(Mob usingEntity) {
        this(usingEntity, COOLDOWN);
    }

    public MarkExplosionSkill(Mob usingEntity, int cooldown) {
        super(usingEntity, cooldown);
        setExplosionRadius(3f);
        setExplosionCauseFire(false);
    }

    @Override
    public void showEffect() {
        LevelChunk levelChunk = usingEntity.getLevel().getChunkAt(usingEntity.getOnPos());
        double x = usingEntity.getX();
        double y = usingEntity.getY();
        double z = usingEntity.getZ();
        YummyMessages.sendToPlayer(new ShowHerobrineMarkPacket(x, y, z), levelChunk);
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
        AABB area = YummyUtil.createArea(usingEntity.getOnPos(), 5);
        return level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
    }
}
