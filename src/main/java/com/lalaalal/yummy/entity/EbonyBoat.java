package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EbonyBoat extends Boat {
    public EbonyBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
    }

    public EbonyBoat(Level level, double x, double y, double z) {
        super(YummyEntities.EBONY_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public Item getDropItem() {
        return YummyItems.EBONY_BOAT_ITEM.get();
    }
}
