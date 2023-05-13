package com.lalaalal.yummy.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

public class PlayerDeltaMovePacket extends YummyPacket {
    private final Vec3 deltaMovement;

    public PlayerDeltaMovePacket(Vec3 deltaMovement) {
        this.deltaMovement = deltaMovement;
    }

    public PlayerDeltaMovePacket(FriendlyByteBuf buf) {
        double dx = buf.readDouble();
        double dy = buf.readDouble();
        double dz = buf.readDouble();

        this.deltaMovement = new Vec3(dx, dy, dz);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(deltaMovement.x);
        buf.writeDouble(deltaMovement.y);
        buf.writeDouble(deltaMovement.z);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleWork(NetworkEvent.Context context) {
        Player player = Minecraft.getInstance().player;
        if (player != null)
            player.setDeltaMovement(deltaMovement);
    }
}
