package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public enum Element {
    EARTH(YummyEffects.EARTH),
    ELECTRICITY(YummyEffects.ELECTRICITY),
    FIRE(YummyEffects.FIRE),
    ICE(YummyEffects.ICE),
    LIFE(YummyEffects.LIFE),
    SOUND(YummyEffects.SOUND),
    WATER(YummyEffects.WATER),
    WIND(YummyEffects.WIND),
    PURITY(YummyEffects.HEROBRINE_MARK);

    private final Supplier<MobEffect> effectSupplier;

    Element(Supplier<MobEffect> effectSupplier) {
        this.effectSupplier = effectSupplier;
    }

    public MobEffect getEffect() {
        return effectSupplier.get();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
