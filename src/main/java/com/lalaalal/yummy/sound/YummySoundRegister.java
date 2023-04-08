package com.lalaalal.yummy.sound;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummySoundRegister {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, YummyMod.MOD_ID);

    public static final RegistryObject<SoundEvent> HEROBRINE_MUSIC = register("herobrine_music");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(YummyMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
