package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.entity.MarkFireball;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShootFireballSkill extends Skill {
    public static final int WARMUP = 20;
    public static final int COOLDOWN = 20 * 13;
    public static final int PREFERRED_DISTANCE = 150;

    public ShootFireballSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
    }

    private boolean isFarEnough() {
        return getDistanceWithTarget() > PREFERRED_DISTANCE;
    }

    @Override
    public boolean canUse() {
        return usingEntity.getTarget() != null && isFarEnough();
    }

    @Override
    public void showEffect() {
        Level level = usingEntity.getLevel();
        level.playSound(null, usingEntity.getOnPos(), YummySoundRegister.SHOOT_FIREBALL.get(), SoundSource.HOSTILE, 0.3f, 1);
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();

        Vec3 viewVector = usingEntity.getViewVector(1.0F);

        MarkFireball markFireball = new MarkFireball(level, usingEntity, viewVector.x, viewVector.y, viewVector.z);
        markFireball.setPos(usingEntity.getX() + viewVector.x * 4.0D, usingEntity.getY(0.5D) + 0.5D, markFireball.getZ() + viewVector.z * 4.0D);
        level.addFreshEntity(markFireball);
    }
}
