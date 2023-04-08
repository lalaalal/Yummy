package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.block.PollutedBlock;
import com.lalaalal.yummy.effect.MarkEffect;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PollutedBlockEntity extends BlockEntity {
    protected int tickInterval = 20 * 10;
    protected int lifetime = 20 * 60;
    protected int tick = -60;
    private Herobrine herobrine;

    public PollutedBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get(), blockPos, blockState);
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

    private void serverTick(Level level, BlockPos blockPos) {

        if (tick % tickInterval == 0)
            affectEntities(level, blockPos);

        if (tick >= lifetime)
            destroyBlock(level, blockPos);

        tick += 1;
    }

    private void clientTick(Level level, BlockPos blockPos) {
        if ((tick + 20) % tickInterval == 0)
            level.setBlock(blockPos, getBlockState().setValue(PollutedBlock.POWERED, true), 10);
        if (tick % tickInterval == 0)
            showParticle(level, blockPos);
        if ((tick - 10) % tickInterval == 0)
            level.setBlock(blockPos, getBlockState().setValue(PollutedBlock.POWERED, false), 10);

        tick += 1;
    }

    private void affectEntities(Level level, BlockPos blockPos) {
        AABB area = YummyUtil.createArea(blockPos, 3);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities)
            MarkEffect.overlapMark(entity);
    }

    private void showParticle(Level level, BlockPos blockPos) {
        RandomSource randomSource = level.getRandom();

        double x = blockPos.getX() + randomSource.nextDouble();
        double y = blockPos.getY() + 1;
        double z = blockPos.getZ() + randomSource.nextDouble();

        level.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 0, 0.3, 0);
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
            pollutedBlockEntity.serverTick(level, blockPos);
    }

    public static <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
            pollutedBlockEntity.clientTick(level, blockPos);
    }
}
