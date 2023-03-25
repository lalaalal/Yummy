package com.lalaalal.yummy.networking;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.networking.packet.HitResultPacket;
import com.lalaalal.yummy.networking.packet.ShowHerobrineMarkPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(YummyMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(HitResultPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HitResultPacket::new)
                .encoder(HitResultPacket::encode)
                .consumerMainThread(HitResultPacket::handle)
                .add();
        INSTANCE.messageBuilder(ShowHerobrineMarkPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ShowHerobrineMarkPacket::new)
                .encoder(ShowHerobrineMarkPacket::encode)
                .consumerMainThread(ShowHerobrineMarkPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToPlayer(MSG message, LevelChunk levelChunk) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> levelChunk), message);
    }
}
