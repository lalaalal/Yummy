package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ElementReactions {
    private static final Map<Element, Reaction> REACTIONS = new HashMap<>();

    public static void init() {
        Reaction earthReaction = new ElementSpecificReaction(Map.of(
                Element.ELECTRICITY, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60),
                Element.FIRE, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60),
                Element.ICE, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60)
        ));
        Reaction lifeReaction = new ElementSpecificReaction(Map.of(
                Element.ELECTRICITY, () -> new MobEffectInstance(YummyEffects.EMISSION.get(), 40, 2)
        ));
        Reaction soundReaction = new ElementSpecificReaction(Map.of(
                Element.SOUND, () -> new MobEffectInstance(YummyEffects.SOUND_WAVE.get(), 100)
        ));
        Reaction waterReaction = new ElementSpecificReaction(Map.of(
                Element.ELECTRICITY, () -> new MobEffectInstance(YummyEffects.ELECTRIC_SHOCK.get(), 40)
        ));
        Reaction windReaction = (element) -> new MobEffectInstance(YummyEffects.DIFFUSION.get(), 40);

        REACTIONS.put(Element.EARTH, earthReaction);
        REACTIONS.put(Element.ELECTRICITY, Reaction.EMPTY);
        REACTIONS.put(Element.FIRE, Reaction.EMPTY);
        REACTIONS.put(Element.ICE, Reaction.EMPTY);
        REACTIONS.put(Element.LIFE, lifeReaction);
        REACTIONS.put(Element.SOUND, soundReaction);
        REACTIONS.put(Element.WATER, waterReaction);
        REACTIONS.put(Element.WIND, windReaction);
    }

    @Nullable
    public static MobEffectInstance getElementReaction(ElementEffect a, ElementEffect b) {
        return getElementReaction(a.element, b.element);
    }

    @Nullable
    public static MobEffectInstance getElementReaction(Element a, Element b) {
        Reaction reaction = REACTIONS.get(a);
        return reaction.getReactionMobEffect(b);
    }

    @FunctionalInterface
    private interface Reaction {
        Reaction EMPTY = element -> null;

        @Nullable MobEffectInstance getReactionMobEffect(Element element);
    }

    private static class ElementSpecificReaction implements Reaction {
        private final Map<Element, Supplier<MobEffectInstance>> reactions;

        public ElementSpecificReaction(Map<Element, Supplier<MobEffectInstance>> map) {
            reactions = map;
        }

        @Nullable
        public MobEffectInstance getReactionMobEffect(Element element) {
            Supplier<MobEffectInstance> supplier = reactions.get(element);
            if (supplier == null)
                return null;

            return supplier.get();
        }
    }
}
