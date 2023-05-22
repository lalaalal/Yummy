package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.Meteor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MeteorStaffItem extends ChargableItem {
    public MeteorStaffItem(Properties properties) {
        super(properties, 20);
    }

    @Override
    protected void itemUse(Level level, Player player, InteractionHand usedHand) {
        Vec3 viewVector = player.getViewVector(1);
        double offsetY = -Math.pow(1.5, -(viewVector.y + 1));

        Meteor meteor = new Meteor(level, player, viewVector.x, offsetY, viewVector.z, false);
        meteor.setPos(player.getX() + viewVector.x * 4.0D, player.getY() + 10D, meteor.getZ() + viewVector.z * 4.0D);
        level.addFreshEntity(meteor);
    }
}
