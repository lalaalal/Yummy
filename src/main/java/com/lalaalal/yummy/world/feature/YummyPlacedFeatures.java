package com.lalaalal.yummy.world.feature;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class YummyPlacedFeatures {
    public static final ResourceKey<PlacedFeature> EBONY_PLACED_KEY = createKey("ebony_placed");
    public static final ResourceKey<PlacedFeature> RUBELLITE_ORE_PLACED_KEY = createKey("rubellite_ore_placed");
    public static final ResourceKey<PlacedFeature> MANGANITE_ORE_PLACED_KEY = createKey("manganite_ore_placed");
    public static final ResourceKey<PlacedFeature> MANGANITE_GEODE_PLACED_KEY = createKey("manganite_geode_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, EBONY_PLACED_KEY, configuredFeatures.getOrThrow(YummyConfiguredFeatures.EBONY_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), YummyBlocks.EBONY_SAPLING.get())
        );
        register(context, RUBELLITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(YummyConfiguredFeatures.RUBELLITE_ORE_KEY),
                commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(13)))
        );
        register(context, MANGANITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(YummyConfiguredFeatures.MANGANITE_ORE_KEY),
                commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(13)))
        );
        register(context, MANGANITE_GEODE_PLACED_KEY, configuredFeatures.getOrThrow(YummyConfiguredFeatures.MANGANITE_GEODE_KEY),
                RarityFilter.onAverageOnceEvery(24),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(35), VerticalAnchor.absolute(40)),
                BiomeFilter.biome()
        );
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(YummyMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }
}
