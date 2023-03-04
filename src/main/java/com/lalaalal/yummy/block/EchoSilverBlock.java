package com.lalaalal.yummy.block;

import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class EchoSilverBlock extends Block {
    public static final String NAME = "echo_silver_block";

    public EchoSilverBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(2.5f, 14f)
                .requiresCorrectToolForDrops());
    }

    public EchoSilverBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = Direction.getRandom(random);
            if (direction != Direction.UP) {
                BlockPos blockpos = blockPos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockState.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, direction.getOpposite())) {
                    double d0 = direction.getStepX() == 0 ? random.nextDouble() : 0.5D + (double) direction.getStepX() * 0.6D;
                    double d1 = direction.getStepY() == 0 ? random.nextDouble() : 0.5D + (double) direction.getStepY() * 0.6D;
                    double d2 = direction.getStepZ() == 0 ? random.nextDouble() : 0.5D + (double) direction.getStepZ() * 0.6D;
                    level.addParticle(YummyParticleRegister.DRIPPING_ECHO_SILVER.get(), (double) blockPos.getX() + d0, (double) blockPos.getY() + d1, (double) blockPos.getZ() + d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
