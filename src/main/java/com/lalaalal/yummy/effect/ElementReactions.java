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
        REACTIONS.put(Element.EARTH, earthReaction);

        Reaction electricityReaction = new ElementSpecificReaction(Map.of(
                Element.EARTH, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60),
                Element.LIFE, () -> new MobEffectInstance(YummyEffects.EMISSION.get(), 40),
                Element.WATER, () -> new MobEffectInstance(YummyEffects.ELECTRIC_SHOCK.get(), 40)
        ));
        REACTIONS.put(Element.ELECTRICITY, electricityReaction);

        Reaction fireReaction = new ElementSpecificReaction(Map.of(
                Element.EARTH, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60)
        ));
        REACTIONS.put(Element.FIRE, fireReaction);

        Reaction iceReaction = new ElementSpecificReaction(Map.of(
                Element.EARTH, () -> new MobEffectInstance(YummyEffects.CRYSTAL.get(), 60)
        ));
        REACTIONS.put(Element.ICE, iceReaction);

        Reaction lifeReaction = new ElementSpecificReaction(Map.of(
                Element.ELECTRICITY, () -> new MobEffectInstance(YummyEffects.EMISSION.get(), 40, 2)
        ));
        REACTIONS.put(Element.LIFE, lifeReaction);

        Reaction soundReaction = new ElementSpecificReaction(Map.of(
                Element.SOUND, () -> new MobEffectInstance(YummyEffects.SOUND_WAVE.get(), 100)
        ));
        REACTIONS.put(Element.SOUND, soundReaction);

        Reaction waterReaction = new ElementSpecificReaction(Map.of(
                Element.ELECTRICITY, () -> new MobEffectInstance(YummyEffects.ELECTRIC_SHOCK.get(), 40)
        ));
        REACTIONS.put(Element.WATER, waterReaction);

        Reaction windReaction = new GeneralReaction(() -> new MobEffectInstance(YummyEffects.DIFFUSION.get(), 40));
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

    private interface Reaction {
        @Nullable MobEffectInstance getReactionMobEffect(Element element);
    }

    private static class GeneralReaction implements Reaction {
        private Supplier<MobEffectInstance> supplier;

        public GeneralReaction(Supplier<MobEffectInstance> supplier) {
            this.supplier = supplier;
        }

        public void setEffectSupplier(Supplier<MobEffectInstance> supplier) {
            this.supplier = supplier;
        }

        @Override
        public @Nullable MobEffectInstance getReactionMobEffect(Element element) {
            return supplier.get();
        }
    }

    private static class ElementSpecificReaction implements Reaction {
        private final Map<Element, Supplier<MobEffectInstance>> reactions;

        public ElementSpecificReaction(Map<Element, Supplier<MobEffectInstance>> map) {
            reactions = map;
        }

        public ElementSpecificReaction() {
            reactions = Map.of();
        }

        public void addReaction(Element element, Supplier<MobEffectInstance> supplier) {
            reactions.put(element, supplier);
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
