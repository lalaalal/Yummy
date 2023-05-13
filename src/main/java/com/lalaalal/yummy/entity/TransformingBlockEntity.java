package com.lalaalal.yummy.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TransformingBlockEntity extends FloatingBlockEntity {
    public static final Vec3 DEFAULT_VELOCITY = new Vec3(0, 0.8, 0);
    private BlockState transformingBlockState;

    @Nullable
    public static TransformingBlockEntity floatAndTransformBlock(Level level, BlockPos pos, Vec3 velocity, BlockState state, BlockState transformingBlockState) {
        if (!state.isFaceSturdy(level, pos, Direction.UP))
            return null;
        TransformingBlockEntity transformingBlockEntity = new TransformingBlockEntity(level, pos, velocity, state, transformingBlockState);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        level.addFreshEntity(transformingBlockEntity);
        return transformingBlockEntity;
    }

    public TransformingBlockEntity(EntityType<? extends TransformingBlockEntity> entityType, Level level) {
        this(entityType, level, BlockPos.ZERO, DEFAULT_VELOCITY, Blocks.SAND.defaultBlockState(), Blocks.DIAMOND_BLOCK.defaultBlockState());
    }

    protected TransformingBlockEntity(Level level, BlockPos pos, Vec3 velocity, BlockState state, BlockState transformingBlockState) {
        this(YummyEntities.TRANSFORMING_BLOCK_ENTITY.get(), level, pos, velocity, state, transformingBlockState);
    }

    protected TransformingBlockEntity(EntityType<? extends TransformingBlockEntity> entityType, Level level, BlockPos pos, Vec3 velocity, BlockState state, BlockState transformingBlockState) {
        super(entityType, level, pos, velocity, DEFAULT_ACCELERATION, state);
        this.transformingBlockState = transformingBlockState;
    }

    @Override
    public void tick() {
        if (getDeltaMovement().y > 0) {
            super.tick();
        } else if (!level.isClientSide) {
            discard();
            level.setBlock(getOnPos(), transformingBlockState, 3);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("TransformingBlockState", NbtUtils.writeBlockState(transformingBlockState));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("TransformingBlockState"))
            transformingBlockState = NbtUtils.readBlockState(compoundTag.getCompound("TransformingBlockState"));
        else
            setDeltaMovement(DEFAULT_VELOCITY);
    }
}
