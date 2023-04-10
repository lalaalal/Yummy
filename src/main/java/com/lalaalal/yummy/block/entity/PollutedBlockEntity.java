package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.PollutedBlock;
import com.lalaalal.yummy.effect.HerobrineMark;
import com.lalaalal.yummy.effect.YummyEffectRegister;
import com.lalaalal.yummy.entity.Herobrine;
import com.lalaalal.yummy.networking.YummyMessages;
import com.lalaalal.yummy.networking.packet.ShowParticlePacket;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PollutedBlockEntity extends BlockEntity {
    protected int tickInterval = 20 * 10;
    protected int lifetime = 20 * 40;
    protected int tick = -10;
    private Herobrine herobrine;

    public PollutedBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
    }

    public PollutedBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        tick -= ThreadLocalRandom.current().nextInt(0, 100);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("tick", tick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tick = tag.getInt("tick");
    }

    public void setHerobrine(Herobrine herobrine) {
        this.herobrine = herobrine;
    }

    public void destroyBlock(Level level, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
        if (herobrine != null)
            herobrine.removePollutedBlock(this);
    }

    private void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        if ((tick + 20) % tickInterval == 0) {
            level.setBlock(blockPos, getBlockState().setValue(PollutedBlock.POWERED, true), 10);
            level.playSound(null, blockPos, YummySoundRegister.POLLUTED_WAVE.get(), SoundSource.BLOCKS, 0.3f, 1);
        }
        if (tick % tickInterval == 0) {
            affectEntities(level, blockPos, blockState);
            sendParticlePacket(level, blockPos);
        }
        if ((tick - 10) % tickInterval == 0)
            level.setBlock(blockPos, getBlockState().setValue(PollutedBlock.POWERED, false), 10);
        if (tick >= lifetime)
            destroyBlock(level, blockPos);

        tick += 1;
    }

    private void affectEntities(Level level, BlockPos blockPos, BlockState blockState) {
        AABB area = YummyUtil.createArea(blockPos, 3);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities)
            affectEntity(entity, blockState);
    }

    protected void affectEntity(LivingEntity entity, BlockState blockState) {
        if (blockState.getValue(PollutedBlock.CORRUPTED))
            HerobrineMark.overlapMark(entity);

        MobEffectInstance mobEffectInstance = new MobEffectInstance(YummyEffectRegister.STUN.get(), 20 * 6, 0);
        if (entity instanceof Herobrine)
            return;
        entity.addEffect(mobEffectInstance);
    }

    private void sendParticlePacket(Level level, BlockPos blockPos) {
        ShowParticlePacket pollutedParticlePacket = new ShowParticlePacket.Builder("polluted_particle_blue")
                .setXYZ(blockPos)
                .setSpeed(0, 5, 0)
                .setRange(1)
                .setParticleCount(40)
                .build();
        ShowParticlePacket explosionEmitterPacket = new ShowParticlePacket.Builder("explosion_emitter")
                .setParticleNamespace("minecraft")
                .setXYZ(blockPos)
                .setSpeed(0, 0.3, 0)
                .setParticleCount(1)
                .build();
        YummyMessages.sendToPlayer(pollutedParticlePacket, level.getChunkAt(blockPos));
        YummyMessages.sendToPlayer(explosionEmitterPacket, level.getChunkAt(blockPos));
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
            pollutedBlockEntity.serverTick(level, blockPos, blockState);
    }
}
