package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class EbonyChestBoat extends ChestBoat implements HasCustomInventoryScreen, ContainerEntity {
    public EbonyChestBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }
    public EbonyChestBoat(Level level, double x, double y, double z) {
        super(YummyEntities.EBONY_CHEST_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (this.canAddPassenger(player) && !player.isSecondaryUseActive()) {
            return super.interact(player, hand);
        } else {
            InteractionResult interactionresult = this.interactWithContainerVehicle(player);
            if (interactionresult.consumesAction()) {
                this.gameEvent(GameEvent.CONTAINER_OPEN, player);
                PiglinAi.angerNearbyPiglins(player, true);
            }

            return interactionresult;
        }
    }

    @Override
    public Item getDropItem() {
        return YummyItems.EBONY_CHEST_BOAT_ITEM.get();
    }
}
