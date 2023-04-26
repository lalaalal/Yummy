package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ShowParticlePacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class KnockbackAndMarkSkill extends Skill {
    public KnockbackAndMarkSkill(Mob usingEntity) {
        super(usingEntity, 20, 5);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void showEffect() {
        LevelChunk levelChunk = usingEntity.getLevel().getChunkAt(usingEntity.getOnPos());
        YummyMessages.sendToPlayer(new ShowParticlePacket.Builder("polluted_particle_blue")
                .setXYZ(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ())
                .setSpeed(0, 5, 0)
                .setRange(0)
                .setParticleCount(20)
                .build(), levelChunk);
        YummyMessages.sendToPlayer(new ShowParticlePacket.Builder("polluted_particle_red")
                .setXYZ(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ())
                .setSpeed(0, 4, 0)
                .setRange(1)
                .setParticleCount(40)
                .build(), levelChunk);
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();
        AABB area = usingEntity.getBoundingBox().inflate(3.0);

        LevelChunk levelChunk = usingEntity.getLevel().getChunkAt(usingEntity.getOnPos());
        ShowParticlePacket packet = new ShowParticlePacket.Builder("explosion_emitter")
                .setParticleNamespace("minecraft")
                .setXYZ(usingEntity.getX(), usingEntity.getY(), usingEntity.getZ())
                .setSpeed(0, 0.3, 0)
                .setRange(1)
                .setParticleCount(1)
                .build();
        YummyMessages.sendToPlayer(packet, levelChunk);

        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
        for (LivingEntity entity : entities) {
            double dx = usingEntity.getX() - entity.getX();
            double dz = usingEntity.getZ() - entity.getZ();
            double strength = (dx * dx + dz * dz);
            entity.knockback(strength, dx, dz);
            HerobrineMark.overlapMark(entity);
            HerobrineMark.overlapMark(entity);
        }
    }
}
