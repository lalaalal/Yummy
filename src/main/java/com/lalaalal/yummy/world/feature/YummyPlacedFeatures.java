package com.lalaalal.yummy.world.feature;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class YummyPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES
            = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, YummyMod.MOD_ID);

    public static final RegistryObject<PlacedFeature> RUBELLITE_ORE_PLACED = PLACED_FEATURES.register("rubellite_ore_placed",
            () -> new PlacedFeature(YummyConfiguredFeatures.RUBELLITE_ORE.getHolder().get(),
                    commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(13)))
            )
    );

    public static final RegistryObject<PlacedFeature> MANGANITE_ORE = PLACED_FEATURES.register("manganite_ore",
            () -> new PlacedFeature(YummyConfiguredFeatures.MANGANITE_ORE.getHolder().get(),
                    commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(13)))
            )
    );

    public static final RegistryObject<PlacedFeature> MANGANITE_GEODE = PLACED_FEATURES.register("manganite_geode",
            () -> new PlacedFeature(YummyConfiguredFeatures.MANGANITE_GEODE.getHolder().get(),
                    List.of(RarityFilter.onAverageOnceEvery(24),
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(35), VerticalAnchor.absolute(40)),
                            BiomeFilter.biome()
                    )
            )
    );

    public static final RegistryObject<PlacedFeature> EBONY_CHECKED = PLACED_FEATURES.register("ebony_checked",
            () -> new PlacedFeature(YummyConfiguredFeatures.EBONY.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(YummyBlocks.EBONY_SAPLING.get()))
            )
    );

    public static final RegistryObject<PlacedFeature> EBONY_PLACED = PLACED_FEATURES.register("ebony_placed",
            () -> new PlacedFeature(YummyConfiguredFeatures.EBONY_SPAWN.getHolder().get(), VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(3, 0.1f, 2))
            )
    );

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
