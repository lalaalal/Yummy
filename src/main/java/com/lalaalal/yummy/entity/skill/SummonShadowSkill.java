package com.lalaalal.yummy.entity.skill;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.YummyEntityRegister;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;

public class SummonShadowSkill extends Skill {
    public static final int COOLDOWN = 20 * 40;
    public static final int WARMUP = 20 * 2;

    public SummonShadowSkill(Mob usingEntity) {
        super(usingEntity, COOLDOWN, WARMUP);
    }

    @Override
    public void showEffect() {
        Level level = usingEntity.getLevel();
        BlockPos blockPos = usingEntity.getOnPos();
        level.playSound(null, blockPos, YummySoundRegister.HEROBRINE_TELEPORT.get(), SoundSource.HOSTILE, 0.7f, 1f);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void useSkill() {
        Level level = usingEntity.getLevel();

        BlockPos blockPos = usingEntity.getOnPos();
        for (int i = 0; i < 3; i++) {
            BlockPos spawnBlockPos = YummyUtil.randomPos(blockPos, 5, level.getRandom());
            if (level instanceof ServerLevel serverLevel)
                YummyEntityRegister.SHADOW_HEROBRINE.get().spawn(serverLevel, null, null, spawnBlockPos, MobSpawnType.MOB_SUMMONED, true, false);
        }
    }
}
