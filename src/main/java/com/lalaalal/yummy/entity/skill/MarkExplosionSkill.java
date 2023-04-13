package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.effect.YummyEffectRegister;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ShowParticlePacket;
import net.minecraft.world.effect.MobEffectInstance;
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
        ShowParticlePacket packet = new ShowParticlePacket.Builder("polluted_particle_blue")
                .setXYZ(x, y, z)
                .setSpeed(0, 5, 0)
                .setRange(1)
                .setParticleCount(20)
                .build();
        YummyMessages.sendToPlayer(packet, levelChunk);
    }

    @Override
    public void useSkill() {
        super.useSkill();

        for (LivingEntity entity : getNearbyEntities()) {
            HerobrineMark.overlapMark(entity);
            MobEffectInstance effectInstance = new MobEffectInstance(YummyEffectRegister.STUN.get(), 60, 0);
            entity.addEffect(effectInstance);
        }
    }

    private List<LivingEntity> getNearbyEntities() {
        Level level = usingEntity.getLevel();
        AABB area = usingEntity.getBoundingBox().inflate(5);
        return level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, usingEntity, area);
    }
}
