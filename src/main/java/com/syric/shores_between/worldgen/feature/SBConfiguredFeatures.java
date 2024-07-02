package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class SBConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> TANGLED_SHINGLE_KEY = registerKey("tangled_shingle");

    public static ResourceKey<ConfiguredFeature<?, ?>> NBT_TREE_KEY = registerKey("nbt_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_TREE = registerKey("mistwood_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_EDGE_TREE = registerKey("mistwood_edge_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_TALL_TREE = registerKey("mistwood_tall_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_THIN_TREE = registerKey("mistwood_thin_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_BUSH = registerKey("mistwood_bush");
    public static ResourceKey<ConfiguredFeature<?, ?>> WINDBLOWN_TREE = registerKey("windblown_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> DEAD_TREE = registerKey("dead_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_TREE = registerKey("petrified_tree");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        tangledShingle(context);

        mistwoodTrees(context);

        otherTrees(context);

        nbtTree(context);

    }

    private static void nbtTree(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        register(context, NBT_TREE_KEY, );
    }


    private static void tangledShingle(BootstrapContext<ConfiguredFeature<?, ?>> context) {
//        RuleTest shingleMatchTest = new RandomBlockMatchTest(SBBlocks.SHINGLE.get(), 0.2F);
        RuleTest shingleMatchTest = new RandomBlockMatchTest(Blocks.GRAY_CONCRETE, 0.15F);

        List<OreConfiguration.TargetBlockState> tangled_shingle = List.of(OreConfiguration.target(shingleMatchTest, SBBlocks.TANGLED_SHINGLE.get().defaultBlockState()));

        register(context, TANGLED_SHINGLE_KEY, Feature.ORE, new OreConfiguration(tangled_shingle, 20));
    }

    private static void mistwoodTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MISTWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(10, 2, 2),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new SpruceFoliagePlacer(UniformInt.of(1, 2), ConstantInt.of(1), UniformInt.of(2, 4)),
                new TwoLayersFeatureSize(2, 0, 2)
        ).build());

        register(context, MISTWOOD_EDGE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(6, 1, 1),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new SpruceFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), UniformInt.of(0, 1)),
                new TwoLayersFeatureSize(0, 0, 0)
        ).build());

        register(context, MISTWOOD_TALL_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(13, 1, 1),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new PineFoliagePlacer(ConstantInt.of(0), UniformInt.of(1, 2), UniformInt.of(3, 4)),
                new TwoLayersFeatureSize(2, 0, 2)
        ).build());

        register(context, MISTWOOD_THIN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(8, 2, 1),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(2, 3)),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build());

        register(context, MISTWOOD_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)
        ).build());
    }

    private static void otherTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, WINDBLOWN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new BendingTrunkPlacer(2, 1, 0, 1, UniformInt.of(2, 5)),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2), 20),
                new TwoLayersFeatureSize(1, 0, 1)
        ).build());

        register(context, DEAD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new ForkingTrunkPlacer(3, 2, 1),
                BlockStateProvider.simple(Blocks.AIR),
                new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0),
                new TwoLayersFeatureSize(2, 0, 2)
        ).build());

        register(context, PETRIFIED_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.STONE),
                new FancyTrunkPlacer(4, 2, 2),
                BlockStateProvider.simple(Blocks.AIR),
                new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ShoresBetween.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
