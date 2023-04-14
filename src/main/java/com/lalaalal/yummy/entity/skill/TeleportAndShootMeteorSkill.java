package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.Meteor;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TeleportAndShootMeteorSkill extends Skill {
    public static final int COOLDOWN = 20 * 17;
    public static final int WARMUP = 20;
    private final Herobrine herobrine;

    public TeleportAndShootMeteorSkill(Herobrine usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
        this.herobrine = usingEntity;
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public void showEffect() {
        herobrine.setArmPose(Herobrine.ArmPose.RAISE_BOTH);
        Level level = usingEntity.getLevel();
        level.playSound(null, usingEntity.getOnPos(), YummySoundRegister.HEROBRINE_TELEPORT.get(), SoundSource.HOSTILE, 0.5f, 1);
    }

    @Override
    public void useSkill() {
        LivingEntity target = usingEntity.getTarget();
        Level level = usingEntity.getLevel();
        if (target != null)
            usingEntity.moveTo(target.getOnPos().above(), 0, 0);

        Vec3 viewVector = usingEntity.getViewVector(1f);

        for (int i = 0; i < 6; i++) {
            double x = usingEntity.getX() + level.random.nextDouble() * 12 - 2;
            double y = usingEntity.getY() + 20 + level.random.nextDouble() * 8 - 6;
            double z = usingEntity.getZ() + level.random.nextDouble() * 12 - 2;

            boolean mark = herobrine.getPhase() >= 2;
            Meteor meteor = new Meteor(level, usingEntity, YummyUtil.makeUnit(viewVector.x) * 0.1, -0.7, YummyUtil.makeUnit(viewVector.z) * 0.1, mark);
            meteor.setPos(x, y, z);
            level.addFreshEntity(meteor);
        }
    }

    @Override
    public void endEffect() {
        herobrine.setArmPose(Herobrine.ArmPose.NORMAL);
    }
}
