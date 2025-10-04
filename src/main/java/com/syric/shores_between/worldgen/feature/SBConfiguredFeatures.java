package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.worldgen.feature.beached_corpse.BeachedCorpseConfiguration;
import com.syric.shores_between.worldgen.feature.bush.BushConfiguration;
import com.syric.shores_between.worldgen.feature.log.FallenLogConfiguration;
import com.syric.shores_between.worldgen.feature.mummified_corpse.MummifiedCorpseConfiguration;
import com.syric.shores_between.worldgen.feature.patch.PatchConfiguration;
import com.syric.shores_between.worldgen.feature.rocks.BoulderConfiguration;
import com.syric.shores_between.worldgen.feature.rocks.boulder_decoration.*;
import com.syric.shores_between.worldgen.feature.titan_egg.TitanEggConfiguration;
import com.syric.shores_between.worldgen.feature.tree.NBTTreeConfiguration;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Set;

public class SBConfiguredFeatures {

    public static ResourceKey<ConfiguredFeature<?, ?>> TANGLED_SHINGLE_KEY = registerKey("tangled_shingle");
    public static ResourceKey<ConfiguredFeature<?, ?>> RUSTY_SCRAP_KEY = registerKey("rusty_scrap");
    public static ResourceKey<ConfiguredFeature<?, ?>> SALTSTONE_KEY = registerKey("saltstone");

    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_FERN_PATCH = registerKey("mistwood_ferns");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_MOSS_PATCH = registerKey("mistwood_moss");
    public static ResourceKey<ConfiguredFeature<?, ?>> SMALL_MISTWOOD_BUSH = registerKey("small_mistwood_bush");
    public static ResourceKey<ConfiguredFeature<?, ?>> LARGE_MISTWOOD_BUSH = registerKey("large_mistwood_bush");

    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_TREE_EDGE = registerKey("mistwood_tree_edge");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_TREE_INTERIOR = registerKey("mistwood_tree_interior");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_EDGE_TREE = registerKey("mistwood_edge_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_TALL_TREE = registerKey("mistwood_tall_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_THIN_TREE = registerKey("mistwood_thin_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> WINDBLOWN_TREE = registerKey("windblown_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> DEAD_TREE = registerKey("dead_tree");
    public static ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_TREE = registerKey("petrified_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_BOULDER = registerKey("test_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_OUTCROP = registerKey("test_outcrop");
    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_PROW = registerKey("test_prow");
    public static ResourceKey<ConfiguredFeature<?, ?>> RAINBOW_BOULDER = registerKey("rainbow_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATCH_BOULDER = registerKey("moss_patch_boulder");

    public static ResourceKey<ConfiguredFeature<?, ?>> SMALL_BOULDER = registerKey("small_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> LARGE_BOULDER = registerKey("large_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> OUTCROP = registerKey("outcrop");
    public static ResourceKey<ConfiguredFeature<?, ?>> MOSSY_OUTCROP = registerKey("mossy_outcrop");
    public static ResourceKey<ConfiguredFeature<?, ?>> MOSSY_BOULDER = registerKey("mossy_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> EERIE_BOULDER = registerKey("eerie_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> CORPSE_BOULDER = registerKey("corpse_boulder");
    public static ResourceKey<ConfiguredFeature<?, ?>> SHELF = registerKey("shelf");


    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_PATCH = registerKey("test_patch");
    public static ResourceKey<ConfiguredFeature<?, ?>> SEAWEED_CLUMP = registerKey("seaweed_clump");
    public static ResourceKey<ConfiguredFeature<?, ?>> DEAD_FISH_PILE = registerKey("dead_fish_pile");
    public static ResourceKey<ConfiguredFeature<?, ?>> DRIFTWOOD_MAT = registerKey("driftwood_mat");
    public static ResourceKey<ConfiguredFeature<?, ?>> DRIFTWOOD_SCATTER = registerKey("driftwood_scatter");
    public static ResourceKey<ConfiguredFeature<?, ?>> SPARSE_DRIFTWOOD_SCATTER = registerKey("sparse_driftwood_scatter");

    public static ResourceKey<ConfiguredFeature<?, ?>> TEST_LOG = registerKey("test_log");
    public static ResourceKey<ConfiguredFeature<?, ?>> DRIFTWOOD_LOG = registerKey("driftwood_log");
    public static ResourceKey<ConfiguredFeature<?, ?>> MISTWOOD_LOG = registerKey("mistwood_log");
    public static ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_LOG = registerKey("petrified_log");
    public static ResourceKey<ConfiguredFeature<?, ?>> BURIED_PETRIFIED_LOG = registerKey("buried_petrified_log");


    public static ResourceKey<ConfiguredFeature<?, ?>> BEACHED_CORPSE = registerKey("beached_corpse");
    public static ResourceKey<ConfiguredFeature<?, ?>> SHALLOW_MUMMIFIED_CORPSE = registerKey("shallow_mummified_corpse");
    public static ResourceKey<ConfiguredFeature<?, ?>> MUMMIFIED_CORPSE = registerKey("mummified_corpse");
    public static ResourceKey<ConfiguredFeature<?, ?>> DEEP_MUMMIFIED_CORPSE = registerKey("deep_mummified_corpse");

    public static ResourceKey<ConfiguredFeature<?, ?>> TITAN_EGG = registerKey("titan_egg");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        ores(context);

        mistwoodTrees(context);

        otherTrees(context);

        foliage(context);

        rocks(context);

        patches(context);

        logs(context);

        corpses(context);

        misc(context);

    }

    private static void foliage(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MISTWOOD_FERN_PATCH, Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(32, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FERN))))
        );

        register(context, MISTWOOD_MOSS_PATCH, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 3, 2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(SBBlocks.MISTWOOD_MOSS_CARPET.get())),
                        BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), SBTags.Blocks.MOSS_CARPET_GROWS_ON), BlockPredicate.ONLY_IN_AIR_PREDICATE)))
        );

        register(context, SMALL_MISTWOOD_BUSH, SBFeatures.BUSH.get(),
                new BushConfiguration(
                        BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                        BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(0), 5).add(ConstantInt.of(1), 1).build()))
        );

        register(context, LARGE_MISTWOOD_BUSH, SBFeatures.BUSH.get(),
                new BushConfiguration(
                        BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                        BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 5).add(ConstantInt.of(2), 3).add(ConstantInt.of(3), 1).build()))
        );
    }


    private static void ores(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest tangledTest = new RandomBlockMatchTest(SBBlocks.SHINGLE.get(), 0.2F);
        RuleTest rustyScrapTest = new RandomBlockMatchTest(SBBlocks.SHINGLE.get(), 0.6F);
        RuleTest saltstoneTest = new BlockStateMatchTest(SBBlocks.SHALE.get().defaultBlockState());

        List<OreConfiguration.TargetBlockState> tangled_shingle = List.of(OreConfiguration.target(tangledTest, SBBlocks.TANGLED_SHINGLE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> metal_scrap = List.of(OreConfiguration.target(rustyScrapTest, SBBlocks.BURIED_RUSTY_SCRAP.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> saltstone = List.of(OreConfiguration.target(saltstoneTest, SBBlocks.SALTSTONE.get().defaultBlockState()));

        register(context, TANGLED_SHINGLE_KEY, Feature.ORE, new OreConfiguration(tangled_shingle, 20));
        register(context, RUSTY_SCRAP_KEY, Feature.ORE, new OreConfiguration(metal_scrap, 10));
        register(context, SALTSTONE_KEY, Feature.ORE, new OreConfiguration(saltstone, 30));
    }

    private static void mistwoodTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MISTWOOD_TREE_EDGE, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_4"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_5")
                ),
                UniformInt.of(1,2),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_WOOD.get()),
                true,
                ConstantInt.of(0),
                ConstantInt.of(0)
        ));

        register(context, MISTWOOD_TREE_INTERIOR, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_4"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood/mistwood_5")
                ),
                UniformInt.of(5, 6),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_WOOD.get()),
                true,
                BiasedToBottomInt.of(0, 2),
                UniformInt.of(0, 2)
        ));

        register(context, MISTWOOD_EDGE_TREE, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_edge/mistwood_edge_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_edge/mistwood_edge_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_edge/mistwood_edge_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_edge/mistwood_edge_4")
                ),
                ConstantInt.of(0),
//                UniformInt.of(0, 1),
//                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_WOOD.get()),
                false,
                ConstantInt.of(0),
                ConstantInt.of(0)
        ));

        register(context, MISTWOOD_TALL_TREE, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_tall/mistwood_tall_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_tall/mistwood_tall_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_tall/mistwood_tall_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/mistwood_tall/mistwood_tall_4")
                ),
                UniformInt.of(4, 6),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_WOOD.get()),
                true,
                BiasedToBottomInt.of(0, 3),
                BiasedToBottomInt.of(1, 3)
                ));

        register(context, MISTWOOD_THIN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(8, 2, 1),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new PineFoliagePlacer(ConstantInt.of(1), ConstantInt.of(1), UniformInt.of(2, 3)),
                new TwoLayersFeatureSize(2, 0, 1)
        ).build());
    }

    private static void otherTrees(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, WINDBLOWN_TREE, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/windblown/windblown_tree_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/windblown/windblown_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/windblown/windblown_tree_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/windblown/windblown_tree_4")
                ),
                ConstantInt.of(0),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LEAVES.get()),
                BlockStateProvider.simple(SBBlocks.MISTWOOD_WOOD.get()),
                true,
                ConstantInt.of(0),
                ConstantInt.of(0)
        ));

        register(context, DEAD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new ForkingTrunkPlacer(3, 2, 1),
                BlockStateProvider.simple(Blocks.AIR),
                new BlobFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), 0),
                new TwoLayersFeatureSize(2, 0, 2)
        ).build());

        register(context, PETRIFIED_TREE, SBFeatures.NBT_TREE.get(), new NBTTreeConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_1"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_2"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_3"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_4"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_5"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_6"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_7"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_8"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_9"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_10"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_11"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_12"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_12"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_13"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_14"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_15"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_16"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_16"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_17"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_17"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_17"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_18"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_18"),
                        new ResourceLocation(ShoresBetween.MODID, "features/nbt_trees/petrified/petrified_tree_18")
                ),
                UniformInt.of(1, 3),
                BlockStateProvider.simple(SBBlocks.PETRIFIED_LOG.get()),
                BlockStateProvider.simple(Blocks.AIR),
                BlockStateProvider.simple(SBBlocks.PETRIFIED_WOOD.get()),
                true,
                ConstantInt.of(0),
                ConstantInt.of(0)
        ));
    }

    private static void rocks(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, SMALL_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(4, 7), //Size (diameter)
                ConstantFloat.of(0.5F), //Eccentricity multiplier
                UniformFloat.of(0.9F, 1.1F), //Irregularity amplitude multiplier
                UniformFloat.of(0.6F, 1.0F), //Irregularity frequency multiplier
                ConstantInt.of(1), //Overlaps
                ConstantInt.of(0), //number of clefts
                0.6F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of() //Decorations
        ));

        register(context, LARGE_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(10, 16), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                UniformInt.of(-4, 3), //Overlaps
                UniformInt.of(0, 2), //number of clefts
                0.6F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
                        new SidesBoulderDecorator(0.1F, true, UniformInt.of(1, 3), BlockStateProvider.simple(SBBlocks.CHISELED_SHALE.get()), 0.05F),
                        new BarnaclesBoulderDecorator(UniformInt.of(1, 3), UniformFloat.of(1, 3), BlockStateProvider.simple(SBBlocks.BARNACLES.get()), 0.2F),
                        new BuriedBlobBoulderDecorator(BlockStateProvider.simple(SBBlocks.ROTTING_FLESH_BLOCK.get()), 0.02F),
                        new UndercutBoulderDecorator(0.7F, 0.5F)
                ) //Decorations
        ));

        register(context, OUTCROP, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(6, 12), //Size (diameter)
                ConstantFloat.of(0.7F), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                UniformInt.of(-6, 2), //Overlaps
                ConstantInt.of(0), //number of clefts
                0.6F, //restrict y
                true, //outcrop
                0.4F, //prow probability
                List.of() //Decorations
        ));

        register(context, MOSSY_OUTCROP, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(6, 12), //Size (diameter)
                ConstantFloat.of(0.7F), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                UniformInt.of(-6, 2), //Overlaps
                ConstantInt.of(0), //number of clefts
                0.6F, //restrict y
                true, //outcrop
                0.4F, //prow probability
                List.of(
                        new TopBoulderDecorator(UniformFloat.of(0.3F, 0.6F), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE.get()), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE_SLAB.get()), 0.7F),
                        new AboveBoulderDecorator(UniformFloat.of(0.3F, 0.6F), BlockStateProvider.simple(SBBlocks.MISTWOOD_MOSS_CARPET.get()), false, 0.4F)
                ) //Decorations
        ));

        register(context, MOSSY_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(6, 15), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                UniformInt.of(-4, 3), //Overlaps
                UniformInt.of(0, 2), //number of clefts
                0.6F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
                        new TopBoulderDecorator(UniformFloat.of(0.4F, 0.8F), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE.get()), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE_SLAB.get()), 0.8F),
                        new AboveBoulderDecorator(UniformFloat.of(0.4F, 0.8F), BlockStateProvider.simple(SBBlocks.MISTWOOD_MOSS_CARPET.get()), false, 0.6F)
                ) //Decorations
        ));

        register(context, EERIE_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(8, 15), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(1F, 1.5F), //Irregularity amplitude multiplier
                UniformFloat.of(1F, 1.5F), //Irregularity frequency multiplier
                UniformInt.of(-4, 3), //Overlaps
                UniformInt.of(0, 3), //number of clefts
                0.7F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
                        new SidesBoulderDecorator(0.15F, true, UniformInt.of(2, 4), BlockStateProvider.simple(SBBlocks.CHISELED_SHALE.get()), 0.3F),
                        new InteriorScatterBoulderDecorator(0.05F, Set.of(SBBlocks.SHALE.get()), BlockStateProvider.simple(SBBlocks.DARK_SHALE.get()), false, 0.2F),
                        new BarnaclesBoulderDecorator(UniformInt.of(1, 3), UniformFloat.of(1, 3), BlockStateProvider.simple(SBBlocks.BARNACLES.get()), 0.2F),
                        new BuriedBlobBoulderDecorator(BlockStateProvider.simple(SBBlocks.ROTTING_FLESH_BLOCK.get()), 0.3F),
                        new UndercutBoulderDecorator(0.7F, 0.5F)
                ) //Decorations
        ));

        register(context, CORPSE_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(6, 10), //Size (diameter)
                ConstantFloat.of(0.4F), //Eccentricity multiplier
                UniformFloat.of(0.6F, 1.0F), //Irregularity amplitude multiplier
                UniformFloat.of(0.6F, 1.0F), //Irregularity frequency multiplier
                ConstantInt.of(1), //Overlaps
                ConstantInt.of(0), //number of clefts
                0.6F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
                        new InteriorLayeredBoulderDecorator(List.of(SBBlocks.LEVIATHAN_KERATIN.get().defaultBlockState(), SBBlocks.ROTTING_FLESH_BLOCK.get().defaultBlockState()), 1, 1),
                        new InteriorScatterBoulderDecorator(0.15F, Set.of(SBBlocks.ROTTING_FLESH_BLOCK.get()), BlockStateProvider.simple(SBBlocks.INFESTED_FLESH_BLOCK.get()), true, 0.4F),
                        new InteriorScatterBoulderDecorator(0.15F, Set.of(SBBlocks.ROTTING_FLESH_BLOCK.get()), BlockStateProvider.simple(SBBlocks.BLOATED_FLESH.get()), true, 0.4F),
                        new InteriorScatterBoulderDecorator(0.1F, Set.of(SBBlocks.ROTTING_FLESH_BLOCK.get()), BlockStateProvider.simple(SBBlocks.LEVIATHAN_BONE.get()), true, 0.4F),
                        new BuriedBlobBoulderDecorator(BlockStateProvider.simple(SBBlocks.ROTTING_FLESH_BLOCK.get()), 0.1F),
                        new UndercutBoulderDecorator(0.3F, 0.5F)
                ) //Decorations
        ));

        register(context, SHELF, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(25, 35), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.0F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.0F), //Irregularity frequency multiplier
                UniformInt.of(-4, 2), //Overlaps
                UniformInt.of(0, 2), //number of clefts
                0.4F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
                        new SidesBoulderDecorator(0.1F, true, UniformInt.of(1, 3), BlockStateProvider.simple(SBBlocks.CHISELED_SHALE.get()), 0.05F),
                        new BarnaclesBoulderDecorator(UniformInt.of(1, 3), UniformFloat.of(1, 3), BlockStateProvider.simple(SBBlocks.BARNACLES.get()), 0.2F)
                ) //Decorations
        ));
    }

    private static void patches(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, SEAWEED_CLUMP, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(0,
                        new NormalNoise.NoiseParameters(-2, (List<Double>) List.of(1.0, 0.7, 0.5)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(SBBlocks.SEAWEED_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)),
                BlockStateProvider.simple(SBBlocks.SEAWEED_SLAB.get()),
                BlockStateProvider.simple(SBBlocks.SEAWEED_CARPET.get()),
                ConstantInt.of(5),
                UniformInt.of(3, 6),
                UniformFloat.of(10, 14),
                ConstantFloat.of(2),
                UniformFloat.of(0, 3)
        ));

        register(context, DEAD_FISH_PILE, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(5,
                        new NormalNoise.NoiseParameters(-3, (List<Double>) List.of(1.0, 0.7, 0.5)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(SBBlocks.DEAD_FISH_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)),
                BlockStateProvider.simple(SBBlocks.DEAD_FISH_SLAB.get()),
                BlockStateProvider.simple(SBBlocks.DEAD_FISH_SLAB.get()),
                ConstantInt.of(5),
                UniformInt.of(3, 6),
                UniformFloat.of(10, 20),
                ConstantFloat.of(4),
                UniformFloat.of(0, 5)
        ));

        register(context, DRIFTWOOD_MAT, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(12,
                        new NormalNoise.NoiseParameters(-3, (List<Double>) List.of(1.0, 1.0)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(Blocks.OAK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                ConstantInt.of(2),
                UniformInt.of(4, 8),
                ConstantFloat.of(8),
                ConstantFloat.of(2),
                UniformFloat.of(0, 5)
        ));

        register(context, DRIFTWOOD_SCATTER, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(55,
                        new NormalNoise.NoiseParameters(-4, (List<Double>) List.of(1.0, 1.0)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(Blocks.OAK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                ConstantInt.of(1),
                UniformInt.of(8, 16),
                ConstantFloat.of(2),
                ConstantFloat.of(2),
                UniformFloat.of(0, 16)
        ));

        register(context, SPARSE_DRIFTWOOD_SCATTER, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(55,
                        new NormalNoise.NoiseParameters(-5, (List<Double>) List.of(1.0, 1.0)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(Blocks.OAK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.DOUBLE)),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                BlockStateProvider.simple(Blocks.OAK_SLAB),
                ConstantInt.of(1),
                UniformInt.of(16, 32),
                ConstantFloat.of(2),
                ConstantFloat.of(2),
                UniformFloat.of(1, 16)
        ));
    }

    private static void logs(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MISTWOOD_LOG, SBFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get().defaultBlockState()),
                ClampedNormalInt.of(6, 1.5F, 4, 8),
                ConstantInt.of(0)
        ));

        register(context, DRIFTWOOD_LOG, SBFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(SBBlocks.DRIFTWOOD_LOG.get().defaultBlockState()),
                ClampedNormalInt.of(5, 1.5F, 4, 7),
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(0), 9).add(ConstantInt.of(1), 4).add(ConstantInt.of(2), 1).build())
        ));

        register(context, PETRIFIED_LOG, SBFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(SBBlocks.PETRIFIED_LOG.get().defaultBlockState()),
                ClampedNormalInt.of(6, 1.5F, 4, 9),
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 5).build())
        ));

        register(context, BURIED_PETRIFIED_LOG, SBFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(SBBlocks.PETRIFIED_LOG.get().defaultBlockState()),
                ClampedNormalInt.of(6, 1.5F, 4, 9),
                ConstantInt.of(2))
        );
    }

    private static void corpses(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, BEACHED_CORPSE, SBFeatures.BEACHED_CORPSE.get(), new BeachedCorpseConfiguration(true));

        register(context, SHALLOW_MUMMIFIED_CORPSE, SBFeatures.MUMMIFIED_CORPSE.get(), new MummifiedCorpseConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/anglerfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/armored_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab_turtle"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/digger"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/double_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/eurypterid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/false_hermit"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/giant_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/gulper_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/horseshoe_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/isopod"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/labyrinth_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_centipede"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/nautilus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/plesiosaur"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sabertooth_catfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_serpent"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_slug"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/spider_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/squid_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/swordspine"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/twin_shark")
                ),
                0.1F,
                ClampedNormalFloat.of(0.95F, 0.05F, 0, 1),
                ClampedNormalFloat.of(1, 0.1F, 0, 1),
                ClampedNormalFloat.of(0.8F, 0.2F, 0, 1)
        ));

        register(context, MUMMIFIED_CORPSE, SBFeatures.MUMMIFIED_CORPSE.get(), new MummifiedCorpseConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/anglerfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/armored_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab_turtle"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/digger"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/double_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/eurypterid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/false_hermit"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/giant_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/gulper_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/horseshoe_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/isopod"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/labyrinth_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_centipede"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/nautilus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/plesiosaur"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sabertooth_catfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_serpent"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_slug"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/spider_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/squid_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/swordspine"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/twin_shark")
                ),
                0.5F,
                ClampedNormalFloat.of(0.95F, 0.05F, 0, 1),
                ClampedNormalFloat.of(1, 0.1F, 0, 1),
                ClampedNormalFloat.of(0.7F, 0.2F, 0, 1)
        ));

        register(context, DEEP_MUMMIFIED_CORPSE, SBFeatures.MUMMIFIED_CORPSE.get(), new MummifiedCorpseConfiguration(
                List.of(
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/anglerfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/armored_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/crab_turtle"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/digger"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/double_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/dunkleosteus_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/eurypterid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/false_hermit"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/giant_squid"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/gulper_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/horseshoe_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/isopod"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/labyrinth_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_centipede"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/lobster_eel"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/nautilus"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/plesiosaur"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sabertooth_catfish"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_serpent"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/sea_slug"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/spider_crab"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/squid_whale"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/swordspine"),
                        new ResourceLocation(ShoresBetween.MODID, "features/mummified_corpses/twin_shark")
                ),
                0.8F,
                ClampedNormalFloat.of(0.95F, 0.05F, 0, 1),
                ClampedNormalFloat.of(0.7F, 0.1F, 0, 1),
                ClampedNormalFloat.of(0.4F, 0.2F, 0, 1)
        ));
    }

    private static void misc(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, TITAN_EGG, SBFeatures.TITAN_EGG.get(), new TitanEggConfiguration(BiasedToBottomInt.of(12, 25), UniformFloat.of(2.2F, 2.8F)));
    }


    private static void testing(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, TEST_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(8, 12), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                UniformInt.of(-5, 3), //Overlaps
                UniformInt.of(1, 6), //number of clefts
                0.67F, //restrict y
                false, //outcrop
                0.0F, //prow probability
                List.of(
//                        new InteriorScatterBoulderDecorator(0.05F, Set.of(SBBlocks.SHALE.get()), BlockStateProvider.simple(Blocks.DIAMOND_BLOCK), true, 0.8F)
                        new SidesBoulderDecorator(0.1F, false, ConstantInt.of(0), BlockStateProvider.simple(Blocks.LAPIS_BLOCK), 0.2F),
                        new SidesBoulderDecorator(0.05F, true, UniformInt.of(1, 3), BlockStateProvider.simple(Blocks.EMERALD_BLOCK), 0.5F),
                        new TopBoulderDecorator(ConstantFloat.of(0.2F), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE.get()), BlockStateProvider.simple(SBBlocks.OVERGROWN_SHALE_SLAB.get()), 0.8F),
                        new BarnaclesBoulderDecorator(UniformInt.of(2, 4), UniformFloat.of(2, 3), BlockStateProvider.simple(Blocks.PINK_CONCRETE), 0.8F),
                        new BuriedBlobBoulderDecorator(BlockStateProvider.simple(Blocks.SLIME_BLOCK), 1)
                ) //Decorations
        ));

        register(context, TEST_PROW, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(8, 12), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                ConstantInt.of(0), //Overlaps
                UniformInt.of(0, 2), //number of clefts
                1.0F, //restrict y
                true, //outcrop
                0.6F, //prow probability
                List.of() //Decorations
        ));

        register(context, RAINBOW_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()),
                false,
                BlockStateProvider.simple(Blocks.AIR),
                ConstantInt.of(40),
                ConstantFloat.of(0),
                ConstantFloat.of(0),
                ConstantFloat.of(0),
                ConstantInt.of(1),
                UniformInt.of(0, 3),
                1.0F,
                false,
                0.0F,
                List.of(
                        new InteriorLayeredBoulderDecorator(
                                List.of(
                                        Blocks.RED_CONCRETE.defaultBlockState(),
                                        Blocks.ORANGE_CONCRETE.defaultBlockState(),
                                        Blocks.YELLOW_CONCRETE.defaultBlockState(),
                                        Blocks.LIME_CONCRETE.defaultBlockState(),
                                        Blocks.LIME_CONCRETE.defaultBlockState(),
                                        Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState(),
                                        Blocks.BLUE_CONCRETE.defaultBlockState(),
                                        Blocks.PURPLE_CONCRETE.defaultBlockState()
                                ),
                                0,
                                1.0F
                        ),
                        new InteriorScatterBoulderDecorator(0.1F, Set.of(Blocks.RED_CONCRETE), BlockStateProvider.simple(Blocks.REDSTONE_BLOCK.defaultBlockState()), true, 1.0F),
                        new InteriorScatterBoulderDecorator(0.1F, Set.of(Blocks.LIME_CONCRETE), BlockStateProvider.simple(Blocks.EMERALD_BLOCK.defaultBlockState()), true, 1.0F)
                )
        ));

        register(context, MOSS_PATCH_BOULDER, SBFeatures.BOULDER.get(), new BoulderConfiguration(
                BlockStateProvider.simple(SBBlocks.SHALE.get()), //Base block
                true, //Use slabs
                BlockStateProvider.simple(SBBlocks.SHALE_SLAB.get()), //Base slab
                UniformInt.of(20, 30), //Size (diameter)
                ConstantFloat.of(1), //Eccentricity multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity amplitude multiplier
                UniformFloat.of(0.8F, 1.2F), //Irregularity frequency multiplier
                ConstantInt.of(0), //Overlaps
                UniformInt.of(0, 2), //number of clefts
                1.0F, //restrict y
                false, //outcrop
                0F, //prow probability
                List.of(
                        new TopBoulderDecorator(
                                UniformFloat.of(0.7F, 0.9F),
                                new NoiseThresholdProvider(0,
                                        new NormalNoise.NoiseParameters(-2, (List<Double>) List.of(1.0, 0.6, 0.3)),
                                        0.5F,
                                        0.1F,
                                        0,
                                        Blocks.AIR.defaultBlockState(),
                                        List.of(SBBlocks.OVERGROWN_SHALE.get().defaultBlockState()),
                                        List.of()
                                ),
                                new NoiseThresholdProvider(0,
                                        new NormalNoise.NoiseParameters(-2, (List<Double>) List.of(1.0, 0.6, 0.3)),
                                        0.5F,
                                        0.1F,
                                        0,
                                        Blocks.AIR.defaultBlockState(),
                                        List.of(SBBlocks.OVERGROWN_SHALE_SLAB.get().defaultBlockState()),
                                        List.of()
                                ),
                                1.0F
                        ),
                        new AboveBoulderDecorator(
                                UniformFloat.of(0.4F, 0.7F),
                                new NoiseThresholdProvider(0,
                                        new NormalNoise.NoiseParameters(-2, (List<Double>) List.of(1.0, 0.6, 0.3)),
                                        0.5F,
                                        0,
                                        0,
                                        Blocks.AIR.defaultBlockState(),
                                        List.of(SBBlocks.MISTWOOD_MOSS_CARPET.get().defaultBlockState()),
                                        List.of()
                                ),
                                false,
                                0.6F
                        )
                )
        ));

        register(context, TEST_PATCH, SBFeatures.PATCH.get(), new PatchConfiguration(
                new NoiseThresholdProvider(0,
                        new NormalNoise.NoiseParameters(-3, (List<Double>) List.of(1.0, 0.6, 0.3)),
                        1F,
                        0F,
                        0,
                        Blocks.BLACK_CONCRETE.defaultBlockState(),
                        List.of(Blocks.WHITE_CONCRETE.defaultBlockState()),
                        List.of()
                ),
                BlockStateProvider.simple(Blocks.GREEN_WOOL),
                BlockStateProvider.simple(Blocks.OXIDIZED_CUT_COPPER_SLAB),
                BlockStateProvider.simple(Blocks.GREEN_CARPET),
                ConstantInt.of(3),
                ConstantInt.of(10),
                ConstantFloat.of(12),
                ConstantFloat.of(2),
                ConstantFloat.of(0)
        ));

        register(context, TEST_LOG, SBFeatures.FALLEN_LOG.get(), new FallenLogConfiguration(
                BlockStateProvider.simple(SBBlocks.MISTWOOD_LOG.get().defaultBlockState()),
                ClampedNormalInt.of(6, 1, 4, 8),
                ConstantInt.of(0)
        ));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ShoresBetween.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
