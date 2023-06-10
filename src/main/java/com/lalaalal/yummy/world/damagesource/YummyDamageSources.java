package com.lalaalal.yummy.world.damagesource;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class YummyDamageSources {
    public static DamageSource longinus(Level level, @Nullable Entity source, ItemStack itemStack) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new ItemDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.SPEAR_OF_LONGINUS_KEY), source, itemStack);
    }

    public static DamageSource thrownLonginus(Level level, @Nullable Entity source, ItemStack itemStack) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new ItemDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.THROWN_SPEAR_OF_LONGINUS_KEY), source, itemStack);
    }

    public static DamageSource spear(Level level, @Nullable Entity source, ItemStack itemStack) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new ItemDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.THROWN_SPEAR_KEY), source, itemStack);
    }

    public static DamageSource godEchoSword(LivingEntity source) {
        Registry<DamageType> damageTypes = source.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new DamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.GOD_ECHO_SWORD_KEY), source);
    }

    public static DamageSource herobrineMark(Level level, MobEffect mobEffect) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new EffectDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.HEROBRINE_MARK_KEY), mobEffect);
    }

    public static DamageSource echoMark(Level level, MobEffect mobEffect) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new EffectDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.ECHO_MARK_KEY), mobEffect);
    }

    public static DamageSource simple(Level level, String id, LivingEntity source) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new SimpleDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.SIMPLE_DAMAGE_TYPE_KEY), id, source);
    }

    public static DamageSource simple(Level level, String id, Entity source, LivingEntity indirectSource) {
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return new SimpleDamageSource(damageTypes.getHolderOrThrow(YummyDamageTypes.SIMPLE_DAMAGE_TYPE_KEY), id, source, indirectSource);
    }
}
