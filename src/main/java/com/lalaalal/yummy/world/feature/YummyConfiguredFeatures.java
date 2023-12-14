package com.lalaalal.yummy.world.feature;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DIAMOND_ORE_SMALL = registerKey("fancy_diamond_ore_small");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DIAMOND_ORE_LARGE = registerKey("fancy_diamond_ore_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_DIAMOND_ORE_BURIED = registerKey("fancy_diamond_ore_buried");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, EBONY_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(YummyBlocks.EBONY_LOG.get()),
                new FancyTrunkPlacer(3,11,0),
                BlockStateProvider.simple(YummyBlocks.EBONY_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build());


        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> FancyDiamondOres = List.of(
                OreConfiguration.target(stoneReplaceables, YummyBlocks.FANCY_DIAMOND_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, YummyBlocks.DEEPSLATE_FANCY_DIAMOND_ORE.get().defaultBlockState())
        );

        register(context, FANCY_DIAMOND_ORE_SMALL, Feature.ORE, new OreConfiguration(FancyDiamondOres, 4, 0.5F));
        register(context, FANCY_DIAMOND_ORE_LARGE, Feature.ORE, new OreConfiguration(FancyDiamondOres, 12, 0.7F));
        register(context, FANCY_DIAMOND_ORE_BURIED, Feature.ORE, new OreConfiguration(FancyDiamondOres, 8, 0.0F));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(YummyMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
