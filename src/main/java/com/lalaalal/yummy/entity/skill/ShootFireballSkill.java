package com.lalaalal.yummy.entity.skill;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ShootFireballSkill extends Skill {
    public static final int WARMUP = 10;
    public static final int COOLDOWN = 600;
    public static final int PREFERRED_DISTANCE = 200;

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
    public void useSkill() {
        LivingEntity target = usingEntity.getTarget();
        Level level = usingEntity.getLevel();
        if (target == null)
            return;

        Vec3 viewVector = usingEntity.getViewVector(1.0F);
        double x = target.getX() - (usingEntity.getX() + viewVector.x * 4.0D);
        double y = target.getY(0.5D) - (0.5D + usingEntity.getY(0.5D));
        double z = target.getZ() - (usingEntity.getZ() + viewVector.z * 4.0D);

        LargeFireball largefireball = new LargeFireball(level, usingEntity, x, y, z, 5);
        largefireball.setPos(usingEntity.getX() + viewVector.x * 4.0D, usingEntity.getY(0.5D) + 0.5D, largefireball.getZ() + viewVector.z * 4.0D);
        level.addFreshEntity(largefireball);
    }
}
