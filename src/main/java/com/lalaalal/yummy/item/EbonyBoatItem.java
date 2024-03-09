package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.EbonyBoat;
import com.lalaalal.yummy.entity.EbonyChestBoat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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

public class EbonyBoatItem extends Item {
    private final boolean hasChest;

    public EbonyBoatItem(boolean hasChest, Properties properties) {
        super(properties);
        this.hasChest = hasChest;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS)
            return InteractionResultHolder.pass(itemStack);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Boat boat = getBoat(level, hitResult.getLocation());
            boat.setYRot(player.getYRot());

            Vec3 eyePosition = player.getEyePosition();

            if (boat.getBoundingBox().contains(eyePosition))
                return InteractionResultHolder.pass(itemStack);
            if (!level.isClientSide) {
                level.addFreshEntity(boat);
                level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!player.getAbilities().instabuild)
                    itemStack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }

    protected Boat getBoat(Level level, Vec3 location) {
        return hasChest ? new EbonyChestBoat(level, location.x, location.y, location.z) : new EbonyBoat(level, location.x, location.y, location.z);
    }
}
