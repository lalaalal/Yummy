package com.lalaalal.yummy.sound;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummySounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS
            = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, YummyMod.MOD_ID);

    public static final RegistryObject<SoundEvent> HEROBRINE_MUSIC_PHASE_1 = register("herobrine_music_phase_1");
    public static final RegistryObject<SoundEvent> HEROBRINE_MUSIC_PHASE_2 = register("herobrine_music_phase_2");
    public static final RegistryObject<SoundEvent> HEROBRINE_MUSIC_PHASE_3 = register("herobrine_music_phase_3");
    public static final RegistryObject<SoundEvent> HEROBRINE_SUMMON = register("herobrine_summon");
    public static final RegistryObject<SoundEvent> HEROBRINE_TELEPORT = register("herobrine_teleport");
    public static final RegistryObject<SoundEvent> HEROBRINE_RUSH = register("herobrine_rush");
    public static final RegistryObject<SoundEvent> POLLUTED_WAVE = register("polluted_wave");
    public static final RegistryObject<SoundEvent> SHADOW_SUMMON = register("shadow_summon");
    public static final RegistryObject<SoundEvent> SHOOT_FIREBALL = register("shoot_fireball");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(YummyMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
