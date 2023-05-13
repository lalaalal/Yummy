package com.lalaalal.yummy.block;

import com.lalaalal.yummy.block.entity.PollutedBlockEntity;
import com.lalaalal.yummy.block.entity.YummyBlockEntities;
import com.lalaalal.yummy.particle.YummyParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

public class PollutedBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty CORRUPTED = BooleanProperty.create("corrupted");
    public static final BooleanProperty FOR_DISPLAY = BooleanProperty.create("for_display");
    public static final BooleanProperty SHOW_PARTICLE = BooleanProperty.create("show_particle");

    private int animateTick = 0;

    public static String getName(boolean corrupted) {
        return corrupted ? "corrupted_polluted_block" : "polluted_block";
    }

    public PollutedBlock(Properties properties) {
        this(properties, false);
    }

    public PollutedBlock(Properties properties, boolean corrupted) {
        this(properties, false, corrupted, false, true);
    }

    public PollutedBlock(Properties properties, boolean powered, boolean corrupted, boolean forDisplay, boolean showParticle) {
        super(properties.lightLevel((blockState) -> blockState.getValue(POWERED) ? 15 : 0));
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(POWERED, powered)
                        .setValue(CORRUPTED, corrupted)
                        .setValue(FOR_DISPLAY, forDisplay)
                        .setValue(SHOW_PARTICLE, showParticle)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        builder.add(CORRUPTED);
        builder.add(FOR_DISPLAY);
        builder.add(SHOW_PARTICLE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(CORRUPTED))
            return new PollutedBlockEntity(YummyBlockEntities.CORRUPTED_POLLUTED_BLOCK_ENTITY_TYPE.get(), pos, state,
                    60, 80, 20);
        return new PollutedBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (!level.isClientSide && !blockState.getValue(FOR_DISPLAY))
            return PollutedBlockEntity::serverTick;
        return null;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        if (!blockState.getValue(SHOW_PARTICLE))
            return;

        if (animateTick++ >= 5) {
            int repeat = random.nextInt(7, 10);
            for (int i = 0; i < repeat; i++) {
                double particleX = blockPos.getX() + random.nextDouble() * 7.0 - 3.0;
                double particleY = blockPos.getY() + random.nextDouble() * 0.2;
                double particleZ = blockPos.getZ() + random.nextDouble() * 7.0 - 3.0;

                level.addParticle(getParticle(blockState), particleX, particleY, particleZ, 0, 1, 0);
            }
            animateTick = 0;
        }
    }

    private ParticleOptions getParticle(BlockState blockState) {
        if (blockState.getValue(PollutedBlock.CORRUPTED))
            return YummyParticles.POLLUTED_PARTICLE_PURPLE.get();
        return YummyParticles.POLLUTED_PARTICLE_RED.get();
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        destroyBlockEntity(level, pos);
        super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        destroyBlockEntity(level, pos);
        return true;
    }

    private void destroyBlockEntity(Level level, BlockPos pos) {
        if (level.isClientSide)
            return;
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PollutedBlockEntity pollutedBlockEntity)
            pollutedBlockEntity.destroyBlock(level, pos);
    }
}
