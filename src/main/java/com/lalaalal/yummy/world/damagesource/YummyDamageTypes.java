package com.lalaalal.yummy.world.damagesource;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class YummyDamageTypes {
    public static final ResourceKey<DamageType> SPEAR_OF_LONGINUS_KEY = registerKey("spear_of_longinus");
    public static final ResourceKey<DamageType> THROWN_SPEAR_OF_LONGINUS_KEY = registerKey("thrown_spear_of_longinus");
    public static final ResourceKey<DamageType> THROWN_SPEAR_KEY = registerKey("thrown_spear");
    public static final ResourceKey<DamageType> GOD_ECHO_SWORD_KEY = registerKey("god_echo_sword");
    public static final ResourceKey<DamageType> HEROBRINE_MARK_KEY = registerKey("herobrine_mark");
    public static final ResourceKey<DamageType> ECHO_MARK_KEY = registerKey("echo_mark");
    public static final ResourceKey<DamageType> SIMPLE_DAMAGE_TYPE_KEY = registerKey("simple_damage_type");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SPEAR_OF_LONGINUS_KEY, new DamageType(YummyMod.MOD_ID + ".spear_of_longinus", 0.1f));
        context.register(THROWN_SPEAR_OF_LONGINUS_KEY, new DamageType(YummyMod.MOD_ID + ".thrown_spear_of_longinus", 0.1f));
        context.register(THROWN_SPEAR_KEY, new DamageType(YummyMod.MOD_ID + ".thrown_spear", 0.1f));
        context.register(GOD_ECHO_SWORD_KEY, new DamageType(YummyMod.MOD_ID + ".god_echo_sword", 0.1f));
        context.register(HEROBRINE_MARK_KEY, new DamageType(YummyMod.MOD_ID + ".herobrine_mark", 0.1f));
        context.register(ECHO_MARK_KEY, new DamageType(YummyMod.MOD_ID + ".echo_mark", 0.1f));
        context.register(SIMPLE_DAMAGE_TYPE_KEY, new DamageType(YummyMod.MOD_ID + ".simple_damage_type", 0.1f));
    }

    private static ResourceKey<DamageType> registerKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(YummyMod.MOD_ID, name));
    }
}
