package com.lalaalal.yummy.client.sound;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class HerobrineMusic extends AbstractSoundInstance {
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "herobrine_music");

    public HerobrineMusic() {
        super(YummySoundRegister.HEROBRINE_MUSIC.get(), SoundSource.MUSIC, RandomSource.create());
        this.looping = true;
    }
}
