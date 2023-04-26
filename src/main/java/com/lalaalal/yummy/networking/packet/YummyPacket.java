package com.lalaalal.yummy.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class YummyPacket {
    public abstract void encode(FriendlyByteBuf buf);

    protected abstract void handleWork(NetworkEvent.Context context);

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> handleWork(supplier.get()));
        supplier.get().setPacketHandled(true);
    }
}
