package com.lalaalal.yummy.effect;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, YummyMod.MOD_ID);

    public static final RegistryObject<MobEffect> MARK = EFFECTS.register("mark", MarkEffect::new);
}
