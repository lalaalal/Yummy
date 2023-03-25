package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ShowHerobrineMarkPacket {
    private final double x;
    private final double y;
    private final double z;

    public ShowHerobrineMarkPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ShowHerobrineMarkPacket(FriendlyByteBuf friendlyByteBuf) {
        this.x = friendlyByteBuf.readDouble();
        this.y = friendlyByteBuf.readDouble();
        this.z = friendlyByteBuf.readDouble();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeDouble(x);
        friendlyByteBuf.writeDouble(y);
        friendlyByteBuf.writeDouble(z);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null)
                return;
            RandomSource random = level.random;
            for (int i = 0; i < 20; i++) {
                double particleX = x + random.nextDouble() * 3.0 - 1.0;
                double particleY = y + random.nextDouble() * 0.2;
                double particleZ = z + random.nextDouble() * 3.0 - 1.0;
                level.addParticle(YummyParticleRegister.POLLUTED_PARTICLE.get(),
                        particleX, particleY, particleZ, 0, 1, 0);
            }
        });
    }
}
