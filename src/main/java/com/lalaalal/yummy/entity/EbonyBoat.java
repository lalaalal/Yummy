package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

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
