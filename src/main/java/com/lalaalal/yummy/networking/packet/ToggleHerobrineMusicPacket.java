package com.lalaalal.yummy.networking.packet;

import com.lalaalal.yummy.client.sound.HerobrineMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class ToggleHerobrineMusicPacket extends YummyPacket {
    private final boolean play;

    public ToggleHerobrineMusicPacket(boolean play) {
        this.play = play;
    }

    public ToggleHerobrineMusicPacket(FriendlyByteBuf buf) {
        play = buf.readBoolean();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(play);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleWork(NetworkEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            SoundManager soundManager = Minecraft.getInstance().getSoundManager();
            if (play) {
                soundManager.stop();
                soundManager.play(new HerobrineMusic());
            } else {
                soundManager.stop(HerobrineMusic.RESOURCE_LOCATION, SoundSource.MUSIC);
            }
        });
    }
}
