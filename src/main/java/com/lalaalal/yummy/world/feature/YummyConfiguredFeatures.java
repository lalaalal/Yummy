package com.lalaalal.yummy.world.feature;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;
import java.util.OptionalInt;

public class YummyConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY_KEY = registerKey("ebony");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBELLITE_ORE_KEY = registerKey("rubellite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGANITE_ORE_KEY = registerKey("manganite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MANGANITE_GEODE_KEY = registerKey("manganite_geode");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, EBONY_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(YummyBlocks.EBONY_LOG.get()),
                new FancyTrunkPlacer(3,11,0),
                BlockStateProvider.simple(YummyBlocks.EBONY_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());


        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> rubelliteOres = List.of(
                OreConfiguration.target(stoneReplaceables, YummyBlocks.RUBELLITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, YummyBlocks.DEEPSLATE_RUBELLITE_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> manganite = List.of(
                OreConfiguration.target(stoneReplaceables, YummyBlocks.MANGANITE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, YummyBlocks.MANGANITE.get().defaultBlockState())
        );

        register(context, RUBELLITE_ORE_KEY, Feature.ORE, new OreConfiguration(rubelliteOres, 9));
        register(context, MANGANITE_ORE_KEY, Feature.ORE, new OreConfiguration(manganite, 4));
        register(context, MANGANITE_GEODE_KEY, Feature.GEODE, new GeodeConfiguration(
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
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(YummyMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
