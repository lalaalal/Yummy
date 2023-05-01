package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EbonyChestBoat extends ChestBoat {
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
    public Item getDropItem() {
        return YummyItems.EBONY_CHEST_BOAT_ITEM.get();
    }
}
