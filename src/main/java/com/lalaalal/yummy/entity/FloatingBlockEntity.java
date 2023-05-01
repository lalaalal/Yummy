package com.lalaalal.yummy.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FloatingBlockEntity extends Entity {
    private static final double GRAVITATIONAL_ACCELERATION_PER_TICK = -0.04903325;
    public static final Vec3 DEFAULT_ACCELERATION = new Vec3(0, GRAVITATIONAL_ACCELERATION_PER_TICK, 0);
    protected static final EntityDataAccessor<BlockPos> DATA_START_POS = SynchedEntityData.defineId(FloatingBlockEntity.class, EntityDataSerializers.BLOCK_POS);
    private BlockState blockState;
    private Vec3 acceleration;
    protected int time = 0;

    @Nullable
    public static FloatingBlockEntity floatBlock(Level level, BlockPos pos, Vec3 velocity, Vec3 acceleration, BlockState state) {
        if (!state.isFaceSturdy(level, pos, Direction.UP))
            return null;
        FloatingBlockEntity floatingBlockEntity = new FloatingBlockEntity(level, pos, velocity, acceleration, state);
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        level.addFreshEntity(floatingBlockEntity);
        return floatingBlockEntity;
    }

    public FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityType, Level level) {
        this(entityType, level, BlockPos.ZERO, Vec3.ZERO, DEFAULT_ACCELERATION, Blocks.SAND.defaultBlockState());
    }

    protected FloatingBlockEntity(Level level, BlockPos pos, Vec3 velocity, Vec3 acceleration, BlockState state) {
        this(YummyEntities.FLOATING_BLOCK_ENTITY.get(), level, pos, velocity, acceleration, state);
    }

    protected FloatingBlockEntity(EntityType<? extends FloatingBlockEntity> entityType, Level level, BlockPos pos, Vec3 velocity, Vec3 acceleration, BlockState state) {
        super(entityType, level);
        this.acceleration = acceleration;
        this.blockState = state.getBlock().defaultBlockState();
        this.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        this.setDeltaMovement(velocity);
        this.setStartPos(blockPosition());
    }

    public void setStartPos(BlockPos pStartPos) {
        this.entityData.set(DATA_START_POS, pStartPos);
    }

    public BlockPos getStartPos() {
        return this.entityData.get(DATA_START_POS);
    }

    public BlockState getBlockState() {
        return blockState;
    }

    @Override
    public void tick() {
        time += 1;
        setDeltaMovement(getDeltaMovement().add(acceleration));
        move(MoverType.SELF, getDeltaMovement());

        if (!level.isClientSide && isOnGround()) {
            level.setBlock(blockPosition(), blockState, 3);
            discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_START_POS, BlockPos.ZERO);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (!compoundTag.contains("BlockState"))
            return;

        blockState = NbtUtils.readBlockState(compoundTag.getCompound("BlockState"));
        time = compoundTag.getInt("Time");
        double vx = compoundTag.getDouble("VelocityX");
        double vy = compoundTag.getDouble("VelocityY");
        double vz = compoundTag.getDouble("VelocityZ");
        double ax = compoundTag.getDouble("AccelerationX");
        double ay = compoundTag.getDouble("AccelerationY");
        double az = compoundTag.getDouble("AccelerationY");
        setDeltaMovement(vx, vy, vz);
        acceleration = new Vec3(ax, ay, az);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.put("BlockState", NbtUtils.writeBlockState(blockState));
        compoundTag.putInt("Time", time);
        Vec3 velocity = getDeltaMovement();
        compoundTag.putDouble("VelocityX", velocity.x);
        compoundTag.putDouble("VelocityY", velocity.y);
        compoundTag.putDouble("VelocityZ", velocity.z);
        compoundTag.putDouble("AccelerationX", acceleration.x);
        compoundTag.putDouble("AccelerationY", acceleration.y);
        compoundTag.putDouble("AccelerationZ", acceleration.z);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.blockState = Block.stateById(packet.getData());
        this.blocksBuilding = true;
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        this.setPos(x, y, z);
        this.setStartPos(this.blockPosition());
    }
}
