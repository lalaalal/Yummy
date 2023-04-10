package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.entity.Meteor;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TeleportAndShootMeteorSkill extends Skill {
    public static final int COOLDOWN = 20 * 15;
    public static final int WARMUP = 20;

    public TeleportAndShootMeteorSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null;
    }

    @Override
    public void showEffect() {
        if (usingEntity instanceof Herobrine herobrine)
            herobrine.setArmPose(Herobrine.ArmPose.RAISE_BOTH);
        Level level = usingEntity.getLevel();
        level.playSound(null, usingEntity.getOnPos(), YummySoundRegister.HEROBRINE_TELEPORT.get(), SoundSource.HOSTILE, 0.5f, 1);
    }

    @Override
    public void useSkill() {
        LivingEntity target = usingEntity.getTarget();
        Level level = usingEntity.getLevel();
        if (target == null)
            return;

        Vec3 viewVector = usingEntity.getViewVector(1f);
        Meteor meteor = new Meteor(level, usingEntity, viewVector.x, -0.8, viewVector.z);
        meteor.setPos(usingEntity.getX() + viewVector.x * 4.0D, usingEntity.getY() + 5D, meteor.getZ() + viewVector.z * 4.0D);
        level.addFreshEntity(meteor);

        usingEntity.moveTo(target.getOnPos().above(), 0, 0);
    }

    @Override
    public void endEffect() {
        if (usingEntity instanceof Herobrine herobrine)
            herobrine.setArmPose(Herobrine.ArmPose.NORMAL);
    }
}
