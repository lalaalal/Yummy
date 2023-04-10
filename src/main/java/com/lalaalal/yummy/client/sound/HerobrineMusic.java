package com.lalaalal.yummy.client.sound;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.sound.YummySoundRegister;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class HerobrineMusic extends AbstractSoundInstance {
    private static final ResourceLocation[] RESOURCE_LOCATIONS = {
            new ResourceLocation(YummyMod.MOD_ID, "herobrine_music_phase_1"),
            new ResourceLocation(YummyMod.MOD_ID, "herobrine_music_phase_2"),
            new ResourceLocation(YummyMod.MOD_ID, "herobrine_music_phase_3"),
    };

    public static ResourceLocation getResourceLocation(int phase) {
        if (phase < 1 || phase > 3)
            return RESOURCE_LOCATIONS[0];

        return RESOURCE_LOCATIONS[phase - 1];
    }

    public HerobrineMusic(int phase) {
        super(YummySoundRegister.HEROBRINE_MUSIC_PHASE_1.get(), SoundSource.MUSIC, RandomSource.create());
        this.looping = true;
    }
}
