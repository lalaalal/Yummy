package com.lalaalal.yummy.block;

import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.block.entity.YummyBlockEntityRegister;
import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class PollutedBlock extends BaseEntityBlock {
    public static final String NAME = "polluted_block";

    private int animateTick = 0;

    public PollutedBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .strength(2.7f, 14f)
                .requiresCorrectToolForDrops());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PollutedBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (blockEntityType == YummyBlockEntityRegister.POLLUTED_BLOCK_ENTITY_TYPE.get())
            return level.isClientSide ? PollutedBlockEntity::clientTick : PollutedBlockEntity::serverTick;
        return null;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        if (animateTick++ >= 5) {
            for (int i = 0; i < random.nextInt(10, 15); i++) {
                double particleX = blockPos.getX() + random.nextDouble() * 7.0 - 3.0;
                double particleY = blockPos.getY() + random.nextDouble() * 0.2;
                double particleZ = blockPos.getZ() + random.nextDouble() * 7.0 - 3.0;

                level.addParticle(YummyParticleRegister.POLLUTED_PARTICLE.get(), particleX, particleY, particleZ, 0, 1, 0);
            }
            animateTick = 0;
        }
    }
}
