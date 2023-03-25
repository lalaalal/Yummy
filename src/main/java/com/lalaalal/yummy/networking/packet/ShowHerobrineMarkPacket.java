package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.particle.YummyParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ShowHerobrineMarkPacket extends PositionPacket {
    public ShowHerobrineMarkPacket(double x, double y, double z) {
        super(x, y, z);
    }

    public ShowHerobrineMarkPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleWork(NetworkEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
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
