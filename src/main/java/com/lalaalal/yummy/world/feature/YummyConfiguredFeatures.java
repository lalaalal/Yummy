package com.lalaalal.yummy.world.feature;

import com.google.common.base.Suppliers;
import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class YummyConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES
            = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, YummyMod.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_RUBELLITE_ORES
            = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, YummyBlocks.RUBELLITE_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, YummyBlocks.DEEPSLATE_RUBELLITE_ORE.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_MANGANITE_ORES
            = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, YummyBlocks.MANGANITE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, YummyBlocks.MANGANITE.get().defaultBlockState())
    ));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RUBELLITE_ORE = CONFIGURED_FEATURES.register("rubellite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_RUBELLITE_ORES.get(), 9)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MANGANITE_ORE = CONFIGURED_FEATURES.register("manganite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_MANGANITE_ORES.get(), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MANGANITE_GEODE = CONFIGURED_FEATURES.register("manganite_geode",
            () -> new ConfiguredFeature<>(Feature.GEODE,
                    new GeodeConfiguration(
                            new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(Blocks.AIR),
                                    BlockStateProvider.simple(YummyBlocks.MANGANITE.get()),
                                    BlockStateProvider.simple(YummyBlocks.MANGANITE.get()),
                                    List.of(Blocks.AIR.defaultBlockState()),
                                    BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                            new GeodeLayerSettings(1.0D, 1.0D, 2.0D, 2.1D),
                            new GeodeCrackSettings(0.95D, 2.0D, 2),
                            0.35D,
                            0.083D,
                            true,
                            UniformInt.of(2, 4),
                            UniformInt.of(3, 4),
                            UniformInt.of(1, 2),
                            -16, 16, 0.05D, 1)
            )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> EBONY
            = CONFIGURED_FEATURES.register("ebony",
            () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(YummyBlocks.EBONY_LOG.get()),
                    new StraightTrunkPlacer(5, 6, 3),
                    BlockStateProvider.simple(YummyBlocks.EBONY_LEAVES.get()),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                    new TwoLayersFeatureSize(1, 0, 2)).build()
            ));

    public static final RegistryObject<ConfiguredFeature<?, ?>> EBONY_SPAWN
            = CONFIGURED_FEATURES.register("ebony_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                    YummyPlacedFeatures.EBONY_CHECKED.getHolder().get(),
                    0.5f)), YummyPlacedFeatures.EBONY_CHECKED.getHolder().get()
            )));

    public static void register(IEventBus eventBus) {
        CONFIGURED_FEATURES.register(eventBus);
    }
}
