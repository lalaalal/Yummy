package com.lalaalal.yummy.item;

import com.lalaalal.yummy.effect.YummyEffects;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public enum Element {
    REDSTONE(YummyEffects.STUN, Map.of());

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
}
