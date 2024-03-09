package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.block.HerobrineSpawnerBlock;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class HerobrineSpawnerBlockEntity extends BlockEntity {
    private static final Block[] STRUCTURE_BLOCK_TYPES = {Blocks.GOLD_BLOCK, Blocks.GOLD_BLOCK, YummyBlocks.HEROBRINE_SPAWNER_BLOCK.get(), Blocks.NETHERRACK};

    private static final int TEXTURE_CHANGE_INTERVAL = 20;
    private int tick = -20;
    private boolean structureExists = false;
    private boolean activated = false;
    private UUID triggeredPlayerUUID;

    public HerobrineSpawnerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }

    public void activate() {
        this.activated = true;
    }

    public void setTriggeredPlayerUUID(UUID triggeredPlayerUUID) {
        this.triggeredPlayerUUID = triggeredPlayerUUID;
    }

    public void tick(ServerLevel level, BlockPos pos, BlockState blockState) {
        if (!activated)
            return;
        if (tick == 0)
            structureExists = isStructureExists(level, pos);
        if (tick % TEXTURE_CHANGE_INTERVAL == 0) {
            int crack = blockState.getValue(HerobrineSpawnerBlock.CRACK);
            if (crack >= HerobrineSpawnerBlock.CRACK_MAX) {
                destroyStructure(level, pos);
                return;
            }
            BlockState newState = blockState.setValue(HerobrineSpawnerBlock.CRACK, crack + 1);
            level.setBlock(pos, newState, 10);
        }

        tick += 1;
    }

    private void destroyStructure(ServerLevel level, BlockPos pos) {
        /*if (!structureExists) {
            level.destroyBlock(pos, false);
            summonHerobrine(level, pos);
            return;
        }*/

        for (int i = 0; i < 4; i++) {
            int yOffset = i - 2;
            level.destroyBlock(pos.above(yOffset), false);
        }
        summonHerobrine(level, pos.below(2));
    }

    private void summonHerobrine(ServerLevel level, BlockPos summonPos) {
        EntityType.LIGHTNING_BOLT.spawn(level, (ItemStack) null, null, summonPos, MobSpawnType.TRIGGERED, true, true);
        Herobrine herobrine = new Herobrine(level, summonPos, summonPos);

        Player player = level.getPlayerByUUID(triggeredPlayerUUID);
        if (player instanceof ServerPlayer serverPlayer)
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, herobrine);
        level.addFreshEntity(herobrine);
    }

    private static boolean isStructureExists(Level level, BlockPos pos) {
        for (int i = 0; i < STRUCTURE_BLOCK_TYPES.length; i++) {
            if (!level.getBlockState(pos.above(i - 2)).is(STRUCTURE_BLOCK_TYPES[i]))
                return false;
        }
        return true;
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (blockEntity instanceof HerobrineSpawnerBlockEntity herobrineSpawnerBlockEntity && !level.isClientSide)
            herobrineSpawnerBlockEntity.tick((ServerLevel) level, blockPos, blockState);
    }
}
