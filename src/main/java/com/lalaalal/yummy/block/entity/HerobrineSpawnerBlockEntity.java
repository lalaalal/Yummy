package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class HerobrineSpawnerBlockEntity extends BlockEntity {
    private static final Block[] STRUCTURE_BLOCK_TYPES = {Blocks.GOLD_BLOCK, Blocks.GOLD_BLOCK, YummyBlocks.HEROBRINE_SPAWNER_BLOCK.get(), Blocks.SOUL_SAND};
    private static final BlockState[] REPLACE_BLOCK_STATES = {YummyBlocks.CORRUPTED_POLLUTED_BLOCK.get().defaultBlockState(), YummyBlocks.CORRUPTED_POLLUTED_BLOCK.get().defaultBlockState(), YummyBlocks.HEROBRINE_SPAWNER_BLOCK.get().defaultBlockState(), YummyBlocks.POLLUTED_BLOCK.get().defaultBlockState()};
    private static final int BLOCK_CHANGE_INTERVAL = 20;
    private int tick = -20;
    private boolean structureExists = false;

    public HerobrineSpawnerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public void tick(ServerLevel level, BlockPos pos) {
        if (tick == 0)
            structureExists = isStructureExists(level, pos);
        if (tick >= 0 && tick % BLOCK_CHANGE_INTERVAL == 0) {
            int index = tick / BLOCK_CHANGE_INTERVAL;
            int yOffset = index - 2;

            if (index == 4) {
                destroyStructure(level, pos);
                return;
            }

            if (structureExists) {
                BlockPos checkingPos = pos.above(yOffset);
                if (yOffset != 0 && level.getBlockState(checkingPos).is(STRUCTURE_BLOCK_TYPES[index])) {
                    level.setBlock(checkingPos, REPLACE_BLOCK_STATES[index], 3);
                }
            }
        }

        tick += 1;
    }

    private void destroyStructure(ServerLevel level, BlockPos pos) {
        if (!structureExists) {
            level.destroyBlock(pos, false);
            summonHerobrine(level, pos);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int yOffset = i - 2;
            level.destroyBlock(pos.above(yOffset), false);
        }
        summonHerobrine(level, pos.below(2));
    }

    private void summonHerobrine(ServerLevel level, BlockPos summonPos) {
        EntityType.LIGHTNING_BOLT.spawn(level, null, null, summonPos, MobSpawnType.TRIGGERED, true, true);
        Herobrine herobrine = new Herobrine(level, summonPos, summonPos);
        level.addFreshEntity(herobrine);
    }

    private static boolean isStructureExists(Level level, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            if (!level.getBlockState(pos.above(i - 2)).is(STRUCTURE_BLOCK_TYPES[i]))
                return false;
        }
        return true;
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof HerobrineSpawnerBlockEntity herobrineSpawnerBlockEntity && !level.isClientSide)
            herobrineSpawnerBlockEntity.tick((ServerLevel) level, blockPos);
    }
}
