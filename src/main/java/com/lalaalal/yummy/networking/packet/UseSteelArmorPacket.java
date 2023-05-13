package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;

public class UseSteelArmorPacket extends YummyPacket {
    public UseSteelArmorPacket() {

    }

    public UseSteelArmorPacket(FriendlyByteBuf buf) {

    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    @Override
    protected void handleWork(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        for (ItemStack armors : player.getArmorSlots()) {
            if (armors.is(YummyTags.STEEL_ARMORS)) {
                pushNearbyEntities(player.level, player);
                armors.hurtAndBreak(1, player, serverPlayer -> serverPlayer.hurt(new DamageSource("steel_armor_break"), 1));
                return;
            }
        }
    }

    private void pushNearbyEntities(Level level, Player player) {
        AABB area = player.getBoundingBox().inflate(4);
        List<LivingEntity> entities = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, area);
        for (LivingEntity entity : entities) {
            Vec3 entityPos = new Vec3(entity.getX(), 0, entity.getZ());
            Vec3 playerPos = new Vec3(player.getX(), 0, player.getZ());
            Vec3 knockBackDirection = playerPos.add(entityPos.reverse()).normalize();

            entity.knockback(1, knockBackDirection.x, knockBackDirection.z);
        }
    }
}
