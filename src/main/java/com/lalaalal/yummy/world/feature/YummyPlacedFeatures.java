package com.lalaalal.yummy.world.feature;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.Registry;
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
                    commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80)))
            ));

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
