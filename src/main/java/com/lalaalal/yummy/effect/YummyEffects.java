package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEffects {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, YummyMod.MOD_ID);

    public static final RegistryObject<MobEffect> HEROBRINE_MARK = MOB_EFFECTS.register("herobrine_mark", HerobrineMark::new);
    public static final RegistryObject<MobEffect> ECHO_MARK = MOB_EFFECTS.register("echo_mark", EchoMark::new);
    public static final RegistryObject<MobEffect> ECHO = MOB_EFFECTS.register("echo", Echo::new);
    public static final RegistryObject<MobEffect> STUN = MOB_EFFECTS.register("stun", Stun::new);
    public static final RegistryObject<MobEffect> CRYSTAL = MOB_EFFECTS.register("crystal", () -> new MixedElementEffect(2, 0xFFFF00));
    public static final RegistryObject<MobEffect> EMISSION = MOB_EFFECTS.register("emission", () -> new MixedElementEffect(3, 0xFFFF00));
    public static final RegistryObject<MobEffect> ELECTRIC_SHOCK = MOB_EFFECTS.register("electric_shock", () -> new MixedElementEffect(2, 0xFFFF00));
    public static final RegistryObject<MobEffect> SOUND_WAVE = MOB_EFFECTS.register("sound_wave", Stun::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
