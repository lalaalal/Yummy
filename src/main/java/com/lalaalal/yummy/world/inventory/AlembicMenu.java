package com.lalaalal.yummy.world.inventory;

import com.lalaalal.yummy.block.entity.AlembicBlockEntity;
import com.lalaalal.yummy.item.distill.EssenceDistilling;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Predicate;

public class AlembicMenu extends AbstractContainerMenu {
    public static final int INGREDIENT_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    public static final int ALEMBIC_SLOT_COUNT = 3;

    private final Container container;
    private final ContainerData containerData;

    public AlembicMenu(int containerId, Inventory inventory, Container container, ContainerData containerData) {
        super(YummyMenuTypes.ALEMBIC_MENU.get(), containerId);
        checkContainerSize(inventory, ALEMBIC_SLOT_COUNT);

        this.container = container;
        this.containerData = containerData;

        this.addSlot(new ItemPredicateSlot(container, EssenceDistilling::isIngredient, INGREDIENT_SLOT, 56, 17));
        this.addSlot(new ItemPredicateSlot(container, item -> item == Items.BLAZE_ROD, FUEL_SLOT, 29, 57));
        this.addSlot(new ItemPredicateSlot(container, item -> false, RESULT_SLOT, 116, 35));

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        addDataSlots(containerData);
    }

    public AlembicMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, new SimpleContainer(ALEMBIC_SLOT_COUNT), new SimpleContainerData(4));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem())
            return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();

        if (index >= 3) {
            if (EssenceDistilling.isIngredient(sourceStack.getItem())) {
                if (!this.moveItemStackTo(sourceStack, INGREDIENT_SLOT, INGREDIENT_SLOT + 1, false))
                    return ItemStack.EMPTY;
            }
            if (sourceStack.is(Items.BLAZE_ROD)) {
                if (!this.moveItemStackTo(sourceStack, FUEL_SLOT, FUEL_SLOT + 1, false))
                    return ItemStack.EMPTY;
            }
        } else {
            this.moveItemStackTo(sourceStack, ALEMBIC_SLOT_COUNT, ALEMBIC_SLOT_COUNT + 36, false);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public boolean isDistilling() {
        return containerData.get(AlembicBlockEntity.DATA_DISTILL_PROGRESS) > 0;
    }

    public int getScaledProgress() {
        int progress = containerData.get(AlembicBlockEntity.DATA_PROGRESS);
        int maxProgress = containerData.get(AlembicBlockEntity.DATA_MAX_PROGRESS);
        int progressArrowSize = 24;

        return maxProgress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getScaledDistillProgress() {
        int progress = containerData.get(AlembicBlockEntity.DATA_DISTILL_PROGRESS);
        int maxProgress = containerData.get(AlembicBlockEntity.DATA_MAX_DISTILL_PROGRESS);
        int progressBubbleSize = 29;

        return maxProgress != 0 ? progress * progressBubbleSize / maxProgress : 0;
    }

    public int getScaledFuelDistillProgress() {
        int progress = containerData.get(AlembicBlockEntity.DATA_DISTILL_PROGRESS);
        int maxProgress = containerData.get(AlembicBlockEntity.DATA_MAX_DISTILL_PROGRESS);
        int progressBubbleSize = 18;

        return maxProgress != 0 ? progress * progressBubbleSize / maxProgress : 0;
    }

    private static class ItemPredicateSlot extends Slot {
        private final Predicate<Item> predicate;

        public ItemPredicateSlot(Container container, Predicate<Item> predicate, int slot, int x, int y) {
            super(container, slot, x, y);
            this.predicate = predicate;
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return predicate.test(itemStack.getItem());
        }
    }
}
