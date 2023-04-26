package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.MarkFireball;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MarkFireballItem extends Item {
    public MarkFireballItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        Vec3 viewVector = player.getViewVector(1);

        MarkFireball markFireball = new MarkFireball(level, player, viewVector.x, viewVector.y, viewVector.z);
        markFireball.setPos(player.getX() + viewVector.x * 4.0D, player.getY(0.5D) + 0.5D, markFireball.getZ() + viewVector.z * 4.0D);
        level.addFreshEntity(markFireball);

        return super.use(level, player, pUsedHand);
    }
}
