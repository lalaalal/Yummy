package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BunnyChest extends TamableAnimal implements ContainerEntity, OwnableEntity {
    private static final int CONTAINER_SIZE = 45;
    private static final EntityDataAccessor<Boolean> DATA_OPENED = SynchedEntityData.defineId(BunnyChest.class, EntityDataSerializers.BOOLEAN);
    private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    private long lootTableSeed;
    public final Color color;
    private int idleTick = 0;

    public static AttributeSupplier.Builder getBunnyChestAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.2);
    }

    protected BunnyChest(EntityType<? extends BunnyChest> entityType, Level level) {
        super(entityType, level);
        this.color = Color.PINK;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OPENED, false);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
                float f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(f));
            }
        }

        this.calculateEntityAnimation(this, false);
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addChestVehicleSaveData(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.getItemStacks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readChestVehicleSaveData(compoundTag);
        ContainerHelper.loadAllItems(compoundTag, this.getItemStacks());
    }

    public boolean isOpened() {
        return this.entityData.get(DATA_OPENED);
    }

    public void setOpened(boolean opened) {
        this.entityData.set(DATA_OPENED, opened);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.75, 4, 3, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10));
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);
        this.chestVehicleDestroyed(pCause, level, this);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
            return;
        double height = getY() - YummyUtil.findHorizonPos(getOnPos(), level).getY();
        if (height > 2.2)
            setDeltaMovement(getDeltaMovement().add(0, -0.03, 0));
        else {
            if (idleTick < 20)
                setDeltaMovement(getDeltaMovement().add(0, 0.005, 0));
            else
                setDeltaMovement(getDeltaMovement().add(0, -0.005, 0));
        }
        move(MoverType.SELF, getDeltaMovement());
        setDeltaMovement(getDeltaMovement().multiply(0.9, 1, 0.9));
        idleTick = (idleTick + 1) % 40;
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (getOwner() == null)
            setOwnerUUID(player.getUUID());
    }

    @Override
    public void setLootTable(@Nullable ResourceLocation lootTable) {

    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (Objects.equals(source.getEntity(), getOwner()))
            return false;
        return !source.isBypassInvul();
    }

    @Override
    public long getLootTableSeed() {
        return lootTableSeed;
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @Override
    public NonNullList<ItemStack> getItemStacks() {
        return itemStacks;
    }

    @Override
    public void clearItemStacks() {
        itemStacks.clear();
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemStacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return this.removeChestVehicleItem(slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return this.removeChestVehicleItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.setChestVehicleItem(slot, stack);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return this.isChestVehicleStillValid(player);
    }

    @Override
    public void clearContent() {
        this.clearChestVehicleContent();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return this.interactWithChestVehicle(this::gameEvent, player);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        if (!Objects.equals(getOwnerUUID(), player.getUUID()) || player.isSpectator()) {
            return null;
        } else {
            this.unpackChestVehicleLootTable(playerInventory.player);
            return new ChestMenu(MenuType.GENERIC_9x5, containerId, playerInventory, this, 5);
        }
    }

    public enum Color {
        PINK
    }
}
