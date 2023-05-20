package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.block.AlembicBlock;
import com.lalaalal.yummy.item.distill.EssenceDistilling;
import com.lalaalal.yummy.world.inventory.AlembicMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AlembicBlockEntity extends BaseContainerBlockEntity implements MenuProvider {
    public static final int INGREDIENT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public static final int DATA_DISTILL_PROGRESS = 0;
    public static final int DATA_MAX_DISTILL_PROGRESS = 1;
    public static final int DATA_PROGRESS = 2;
    public static final int DATA_MAX_PROGRESS = 3;

    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    protected final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case DATA_DISTILL_PROGRESS -> distillProgress;
                case DATA_MAX_DISTILL_PROGRESS -> getMaxDistillProgress();
                case DATA_PROGRESS -> progress;
                case DATA_MAX_PROGRESS -> getRequiredIngredientCount();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case DATA_DISTILL_PROGRESS -> distillProgress = value;
                case DATA_PROGRESS -> progress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private int distillProgress;
    private int progress;

    private Item currentIngredient = Items.AIR;

    public AlembicBlockEntity(BlockPos pos, BlockState blockState) {
        super(YummyBlockEntities.ALEMBIC_BLOCK_ENTITY_TYPE.get(), pos, blockState);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, items);
        if (tag.contains("Progress")) {
            progress = tag.getInt("Progress");
            distillProgress = tag.getInt("DistillProgress");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("Progress", progress);
        tag.putInt("DistillProgress", distillProgress);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.yummy.alembic");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AlembicMenu(containerId, inventory, this, data);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        if (0 <= slot && slot < items.size())
            return items.get(slot);
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        setChanged();
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        if (0 <= slot && slot < items.size())
            items.set(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        if (level != null && level.getBlockEntity(worldPosition) != this)
            return false;

        Vec3 position = new Vec3(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5);
        return player.distanceToSqr(position) < 64;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    public boolean isDistillable() {
        if (distillProgress > 0)
            return true;
        ItemStack fuel = items.get(FUEL_SLOT);
        if (!fuel.is(Items.BLAZE_ROD))
            return false;
        ItemStack ingredient = items.get(INGREDIENT_SLOT);
        ItemStack result = items.get(RESULT_SLOT);
        if (!EssenceDistilling.isIngredient(ingredient.getItem()))
            return false;

        return result.is(Items.AIR) || result.is(EssenceDistilling.getResult(ingredient.getItem()));
    }

    public void distill() {
        distillProgress = 0;
        progress += 1;
    }

    public void createEssence() {
        Item result = EssenceDistilling.getResult(currentIngredient);
        ItemStack resultSlot = items.get(RESULT_SLOT);
        if (resultSlot.is(result)) {
            resultSlot.grow(1);
        } else {
            ItemStack essence = new ItemStack(result);
            items.set(RESULT_SLOT, essence);
        }
        progress = 0;
        setChanged();
    }

    public int getMaxDistillProgress() {
        return 200;
    }

    public int getRequiredIngredientCount() {
        return EssenceDistilling.getRequiredCount(currentIngredient);
    }

    public void takeIngredientAndFuel() {
        Item prevIngredient = currentIngredient;
        currentIngredient = items.get(INGREDIENT_SLOT).getItem();
        if (prevIngredient != currentIngredient)
            progress = 0;

        this.removeItem(INGREDIENT_SLOT, 1);
        this.removeItem(FUEL_SLOT, 1);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AlembicBlockEntity alembicBlockEntity) {
        if (alembicBlockEntity.isDistillable()) {
            if (alembicBlockEntity.distillProgress == 0) {
                if (!state.getValue(AlembicBlock.ON))
                    level.setBlock(pos, state.setValue(AlembicBlock.ON, true), 3);
                alembicBlockEntity.takeIngredientAndFuel();
            }

            alembicBlockEntity.distillProgress += 1;
            setChanged(level, pos, state);

            if (alembicBlockEntity.distillProgress >= alembicBlockEntity.getMaxDistillProgress())
                alembicBlockEntity.distill();
            if (alembicBlockEntity.progress >= alembicBlockEntity.getRequiredIngredientCount())
                alembicBlockEntity.createEssence();
        } else {
            if (state.getValue(AlembicBlock.ON))
                level.setBlock(pos, state.setValue(AlembicBlock.ON, false), 3);
            alembicBlockEntity.distillProgress = 0;
        }
    }
}
