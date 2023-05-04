package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.YummyEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class BunnyChestItem extends Item {
    public BunnyChestItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        itemStack.shrink(1);
        if (context.getLevel() instanceof ServerLevel serverLevel)
            YummyEntities.BUNNY_CHEST.get().spawn(serverLevel, itemStack,
                    context.getPlayer(),
                    context.getClickedPos(),
                    MobSpawnType.SPAWN_EGG, true, true);

        return super.useOn(context);
    }
}
