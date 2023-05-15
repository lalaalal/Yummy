package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.Meteor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MarkFireballItem extends ChargableItem {
    public MarkFireballItem(Properties properties) {
        super(properties, 20);
    }

    @Override
    protected void itemUse(Level level, Player player, InteractionHand usedHand) {
        Vec3 viewVector = player.getViewVector(0);

        Meteor meteor = new Meteor(level, player, viewVector.x, viewVector.y, viewVector.z, true);
        meteor.setPos(player.getX() + viewVector.x * 4.0, player.getY(0.5) + 0.5, meteor.getZ() + viewVector.z * 4.0);
        level.addFreshEntity(meteor);
    }
}
