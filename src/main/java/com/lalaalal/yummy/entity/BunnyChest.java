package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
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
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BunnyChest extends TamableAnimal implements Container, MenuProvider, OwnableEntity {
    private static final int CONTAINER_SIZE = 45;
    private static final EntityDataAccessor<Boolean> DATA_FOLLOWING = SynchedEntityData.defineId(BunnyChest.class, EntityDataSerializers.BOOLEAN);
    private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    public final Color color;
    private int idleTick = 0;
    private FollowOwnerGoal followOwnerGoal;

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
        this.entityData.define(DATA_FOLLOWING, true);
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
        compoundTag.putBoolean("Following", isFollowing());
        ContainerHelper.saveAllItems(compoundTag, itemStacks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setFollowing(compoundTag.getBoolean("Following"));
        ContainerHelper.loadAllItems(compoundTag, itemStacks);
    }

    public boolean isFollowing() {
        return this.entityData.get(DATA_FOLLOWING);
    }

    public void setFollowing(boolean value) {
        if (value) {
            if (followOwnerGoal == null)
                followOwnerGoal = new FollowOwnerGoal(this, 1.75, 4, 3, true);
            goalSelector.addGoal(1, followOwnerGoal);
        } else {
            goalSelector.removeGoal(followOwnerGoal);
        }

        this.entityData.set(DATA_FOLLOWING, value);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        followOwnerGoal = new FollowOwnerGoal(this, 1.75, 5, 4, true);
        this.goalSelector.addGoal(1, followOwnerGoal);
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10));
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);
        Containers.dropContents(level, this, this);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
            return;
        if (!isFollowing()) {
            setDeltaMovement(getDeltaMovement().add(0, -0.005, 0));
            move(MoverType.SELF, getDeltaMovement());
            idleTick = 0;
            return;
        }
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
        idleTick = (idleTick + 1) % 40;
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (getOwner() == null)
            setOwnerUUID(player.getUUID());
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (Objects.equals(source.getEntity(), getOwner()))
            return false;
        return !source.isBypassInvul();
    }

    @Override
    public int getContainerSize() {
        return CONTAINER_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return itemStacks.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemStacks.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemstack = ContainerHelper.removeItem(itemStacks, slot, amount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(itemStacks, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemStacks.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return !this.isRemoved();
    }

    @Override
    public void clearContent() {
        itemStacks.clear();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        travel(Vec3.ZERO);
        if (!level.isClientSide && player.isShiftKeyDown()) {
            setFollowing(!isFollowing());
            String key = isFollowing() ? "info.yummy.bunny_chest.following" : "info.yummy.bunny_chest.not_following";
            player.sendSystemMessage(Component.translatable(key, this.getDisplayName(), player.getDisplayName()));
            return InteractionResult.SUCCESS;
        }

        player.openMenu(this);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        if (!Objects.equals(getOwnerUUID(), player.getUUID()) || player.isSpectator()) {
            return null;
        } else {
            return new ChestMenu(MenuType.GENERIC_9x5, containerId, playerInventory, this, 5);
        }
    }

    public enum Color {
        PINK
    }
}
