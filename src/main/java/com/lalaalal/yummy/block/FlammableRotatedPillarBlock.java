package com.lalaalal.yummy.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class FlammableRotatedPillarBlock extends RotatedPillarBlock {
    private final Map<Block, Block> toolModifiedStates = new HashMap<>();
    private final int flammability;
    private final int fireSpreadSpeed;

    public FlammableRotatedPillarBlock(Properties properties) {
        this(properties, 5, 5);
    }

    public FlammableRotatedPillarBlock(Properties properties, int flammability, int fireSpreadSpeed) {
        super(properties);
        this.flammability = flammability;
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    public void addAxeModifiedState(Block from, Block to) {
        toolModifiedStates.put(from, to);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return fireSpreadSpeed;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            Block modifiedBlock = toolModifiedStates.get(state.getBlock());
            if (modifiedBlock == YummyBlocks.EBONY_LOG.get() || modifiedBlock == YummyBlocks.EBONY_WOOD.get()) {
                return modifiedBlock.defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
