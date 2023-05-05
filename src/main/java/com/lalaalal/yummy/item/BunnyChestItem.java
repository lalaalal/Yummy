package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.BunnyChest;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BunnyChestItem extends Item {
    public BunnyChestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        itemStack.shrink(1);
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        if (!level.isClientSide) {
            BunnyChest bunnyChest = new BunnyChest(level);
            bunnyChest.setPos(clickedPos.getX(), clickedPos.getY() + 1, clickedPos.getZ());
            level.addFreshEntity(bunnyChest);
            CompoundTag compoundTag = itemStack.getTag();
            if (compoundTag != null)
                bunnyChest.readAdditionalSaveData(compoundTag);
        }

        return super.useOn(context);
    }
}
