package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEffectRegister {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, YummyMod.MOD_ID);

    public static final RegistryObject<MobEffect> MARK = MOB_EFFECTS.register("mark", MarkEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
