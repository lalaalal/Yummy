package com.lalaalal.yummy.item;

import com.lalaalal.yummy.effect.YummyEffects;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public enum Element {
    EARTH(YummyEffects.STUN, Map.of()),
    ELECTRICITY(YummyEffects.STUN, Map.of()),
    FIRE(YummyEffects.STUN, Map.of()),
    ICE(YummyEffects.STUN, Map.of()),
    LIFE(YummyEffects.STUN, Map.of()),
    SOUND(YummyEffects.STUN, Map.of()),
    WATER(YummyEffects.STUN, Map.of()),
    WIND(YummyEffects.STUN, Map.of());

    private final Supplier<MobEffect> effectSupplier;
    private final Map<Element, Supplier<MobEffect>> reaction;

    Element(Supplier<MobEffect> effectSupplier, Map<Element, Supplier<MobEffect>> reaction) {
        this.effectSupplier = effectSupplier;
        this.reaction = reaction;
    }

    public MobEffect getEffect() {
        return effectSupplier.get();
    }

    @Nullable
    public MobEffect reactionResult(Element element) {
        return reaction.get(element).get();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
