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
    private final int phase;

    public ToggleHerobrineMusicPacket(boolean play) {
        this.play = play;
        this.phase = 0;
    }

    public ToggleHerobrineMusicPacket(boolean play, int phase) {
        this.play = play;
        this.phase = phase;
    }

    public ToggleHerobrineMusicPacket(FriendlyByteBuf buf) {
        play = buf.readBoolean();
        phase = buf.readInt();
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(play);
        buf.writeInt(phase);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handleWork(NetworkEvent.Context context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            SoundManager soundManager = Minecraft.getInstance().getSoundManager();
            soundManager.stop(HerobrineMusic.getResourceLocation(1), SoundSource.RECORDS);
            soundManager.stop(HerobrineMusic.getResourceLocation(2), SoundSource.RECORDS);
            soundManager.stop(HerobrineMusic.getResourceLocation(3), SoundSource.RECORDS);
            if (play)
                soundManager.play(new HerobrineMusic(phase));
        });
    }
}
