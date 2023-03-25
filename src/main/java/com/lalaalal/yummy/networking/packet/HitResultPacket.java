package com.lalaalal.yummy.networking.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HitResultPacket {
    private final double x;
    private final double y;
    private final double z;

    public HitResultPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public HitResultPacket(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // ON SERVER
            ServerPlayer player = context.getSender();
            if (player == null)
                return;
            ServerLevel level = player.getLevel();

            InteractionHand interactionHand = player.getUsedItemHand();

            provideFlower(level, player, player.getItemInHand(interactionHand));
        });
    }

    private void provideFlower(Level level, Player player, ItemStack itemStack) {
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = level.getBlockState(blockPos);
        if (blockState.is(BlockTags.SMALL_FLOWERS)) {
            level.addFreshEntity(new ItemEntity(level, x, y, z, new ItemStack(blockState.getBlock(), 1)));
            if (!player.isCreative())
                itemStack.shrink(1);
        }
    }
}
