package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.worldgen.SBOrePlacement;
import com.syric.shores_between.worldgen.feature.placement_util.HeightFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class SBPlacedFeatures {

    public static ResourceKey<PlacedFeature> TANGLED_SHINGLE_PLACED_KEY = registerKey("tangled_shingle_placed");
    public static ResourceKey<PlacedFeature> RUSTY_SCRAP_PLACED_KEY = registerKey("rusty_scrap_placed");
    public static ResourceKey<PlacedFeature> SALTSTONE_PLACED_KEY = registerKey("saltstone_placed");

    public static ResourceKey<PlacedFeature> MISTWOOD_FERNS_PLACED_KEY = registerKey("mistwood_ferns_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_LARGE_FERNS_PLACED_KEY = registerKey("mistwood_large_ferns_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_MOSS_PLACED_KEY = registerKey("mistwood_moss_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_FOREST_SMALL_BUSH_PLACED_KEY = registerKey("mistwood_small_mistwood_bush_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_FOREST_LARGE_BUSH_PLACED_KEY = registerKey("mistwood_large_mistwood_bush_placed");

    public static ResourceKey<PlacedFeature> MISTWOOD_STANDARD_TREE_EDGE_PLACED_KEY = registerKey("mistwood_standard_tree_edge_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_STANDARD_TREE_INTERIOR_PLACED_KEY = registerKey("mistwood_standard_tree_interior_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_TALL_TREE_PLACED_KEY = registerKey("mistwood_tall_tree_placed");
    public static ResourceKey<PlacedFeature> MISTWOOD_FLUFFY_TREE_PLACED_KEY = registerKey("mistwood_fluffy_tree_placed");

    public static ResourceKey<PlacedFeature> MISTWOOD_LOG_PLACED_KEY = registerKey("mistwood_log_placed");
    public static ResourceKey<PlacedFeature> DRIFTWOOD_LOG_PLACED_KEY = registerKey("driftwood_log_placed");
    public static ResourceKey<PlacedFeature> DRIFTWOOD_LOG_ON_DRIFTWOOD_BEACH_PLACED_KEY = registerKey("driftwood_log_on_driftwood_beach_placed");
    public static ResourceKey<PlacedFeature> PETRIFIED_LOG_PLACED_KEY = registerKey("petrified_log_placed");
    public static ResourceKey<PlacedFeature> BURIED_PETRIFIED_LOG_PLACED_KEY = registerKey("buried_petrified_log_placed");

    public static ResourceKey<PlacedFeature> SEAWEED_CLUMP_PLACED_KEY = registerKey("seaweed_clump_placed");
    public static ResourceKey<PlacedFeature> DEAD_FISH_PATCH_PLACED_KEY = registerKey("dead_fish_placed");
    public static ResourceKey<PlacedFeature> DRIFTWOOD_MAT_PLACED_KEY = registerKey("driftwood_mat_placed");
    public static ResourceKey<PlacedFeature> DRIFTWOOD_SCATTER_PLACED_KEY = registerKey("driftwood_scatter_placed");
    public static ResourceKey<PlacedFeature> SPARSE_DRIFTWOOD_SCATTER_PLACED_KEY = registerKey("sparse_driftwood_scatter_placed");
    public static ResourceKey<PlacedFeature> RARE_DRIFTWOOD_SCATTER_PLACED_KEY = registerKey("rare_driftwood_scatter_placed");

    public static ResourceKey<PlacedFeature> SMALL_BOULDER_PLACED_KEY = registerKey("small_boulder_placed");
    public static ResourceKey<PlacedFeature> MORE_SMALL_BOULDERS_PLACED_KEY = registerKey("more_small_boulders_placed");
    public static ResourceKey<PlacedFeature> EVEN_MORE_SMALL_BOULDERS_PLACED_KEY = registerKey("even_more_small_boulders_placed");
    public static ResourceKey<PlacedFeature> LARGE_BOULDER_PLACED_KEY = registerKey("large_boulder_placed");
    public static ResourceKey<PlacedFeature> MORE_LARGE_BOULDERS_PLACED_KEY = registerKey("more_large_boulders_placed");
    public static ResourceKey<PlacedFeature> EVEN_MORE_LARGE_BOULDERS_PLACED_KEY = registerKey("even_more_large_boulders_placed");
    public static ResourceKey<PlacedFeature> MOSSY_BOULDER_PLACED_KEY = registerKey("mossy_boulder_placed");
    public static ResourceKey<PlacedFeature> SMALL_OUTCROP_PLACED_KEY = registerKey("small_outcrop_placed");
    public static ResourceKey<PlacedFeature> MOSSY_OUTCROP_PLACED_KEY = registerKey("mossy_outcrop_placed");
    public static ResourceKey<PlacedFeature> RARE_MOSSY_OUTCROPS_PLACED_KEY = registerKey("rare_mossy_outcrops_placed");
    public static ResourceKey<PlacedFeature> EERIE_BOULDER_PLACED_KEY = registerKey("eerie_boulder_placed");
    public static ResourceKey<PlacedFeature> CORPSE_BOULDER_PLACED_KEY = registerKey("corpse_boulder_placed");

    public static ResourceKey<PlacedFeature> SMALL_MISTWOOD_BUSH_PLACED_KEY = registerKey("small_mistwood_bush_placed");
    public static ResourceKey<PlacedFeature> LARGE_MISTWOOD_BUSH_PLACED_KEY = registerKey("large_mistwood_bush_placed");

    public static ResourceKey<PlacedFeature> PETRIFIED_TREE_PLACED_KEY = registerKey("petrified_tree_placed");
    public static ResourceKey<PlacedFeature> SPARSE_PETRIFIED_TREE_PLACED_KEY = registerKey("sparse_petrified_tree_placed");


    public static ResourceKey<PlacedFeature> BEACHED_CORPSE_PLACED_KEY = registerKey("beached_corpse_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        ores(context, configuredFeatures);

        trees(context, configuredFeatures);

        mistwoodFoliage(context, configuredFeatures);

        logs(context, configuredFeatures);

        patches(context, configuredFeatures);

        rocks(context, configuredFeatures);

        foliage(context, configuredFeatures);

        corpses(context, configuredFeatures);

    }



    private static void ores(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?,?>> configuredFeatures) {

        register(context, TANGLED_SHINGLE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.TANGLED_SHINGLE_KEY),
                SBOrePlacement.commonOrePlacement(2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(68))));

        register(context, RUSTY_SCRAP_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.RUSTY_SCRAP_KEY),
                SBOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(60), VerticalAnchor.absolute(64))));

        register(context, SALTSTONE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SALTSTONE_KEY),
                SBOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(50))));

    }

    private static void rocks(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?,?>> configuredFeatures) {
        register(context, SMALL_BOULDER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SMALL_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(25),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, MORE_SMALL_BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SMALL_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(15),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );


        register(context, EVEN_MORE_SMALL_BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SMALL_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, LARGE_BOULDER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.LARGE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(35),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, MORE_LARGE_BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.LARGE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(25),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, EVEN_MORE_LARGE_BOULDERS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.LARGE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, MOSSY_BOULDER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MOSSY_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(6),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(1),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(64, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, SMALL_OUTCROP_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.OUTCROP),
                List.of(RarityFilter.onAverageOnceEvery(20),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(50, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, MOSSY_OUTCROP_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MOSSY_OUTCROP),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, RARE_MOSSY_OUTCROPS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MOSSY_OUTCROP),
                List.of(RarityFilter.onAverageOnceEvery(15),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(66, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, EERIE_BOULDER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.EERIE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(40),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(2),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(60, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, CORPSE_BOULDER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.CORPSE_BOULDER),
                List.of(RarityFilter.onAverageOnceEvery(80),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(3),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(60, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

    }

    private static void patches(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?,?>> configuredFeatures) {
        register(context, SEAWEED_CLUMP_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SEAWEED_CLUMP),
                List.of(RarityFilter.onAverageOnceEvery(30),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, DEAD_FISH_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DEAD_FISH_PILE),
                List.of(RarityFilter.onAverageOnceEvery(45),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, DRIFTWOOD_MAT_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DRIFTWOOD_MAT),
                List.of(RarityFilter.onAverageOnceEvery(15),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 63),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, DRIFTWOOD_SCATTER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DRIFTWOOD_SCATTER),
                List.of(RarityFilter.onAverageOnceEvery(10),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 65),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, RARE_DRIFTWOOD_SCATTER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DRIFTWOOD_SCATTER),
                List.of(RarityFilter.onAverageOnceEvery(35),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, SPARSE_DRIFTWOOD_SCATTER_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SPARSE_DRIFTWOOD_SCATTER),
                List.of(RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );
    }

    private static void logs(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?,?>> configuredFeatures) {

        register(context, MISTWOOD_LOG_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_LOG),
                List.of(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.ZERO, 8).add(ConstantInt.of(1), 3).add(ConstantInt.of(2), 1).build())),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));

        register(context, DRIFTWOOD_LOG_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DRIFTWOOD_LOG),
                List.of(RarityFilter.onAverageOnceEvery(15),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, DRIFTWOOD_LOG_ON_DRIFTWOOD_BEACH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.DRIFTWOOD_LOG),
                List.of(CountPlacement.of(BiasedToBottomInt.of(1, 4)),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 65),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, PETRIFIED_LOG_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.PETRIFIED_LOG),
                List.of(CountPlacement.of(BiasedToBottomInt.of(1, 6)),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 67),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                )
        );

        register(context, BURIED_PETRIFIED_LOG_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.BURIED_PETRIFIED_LOG),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 67),
                        BiomeFilter.biome()
                )
        );
    }

    private static void trees(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        register(context, MISTWOOD_STANDARD_TREE_EDGE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_TREE_EDGE),
                VegetationPlacements.treePlacement(CountPlacement.of(UniformInt.of(3, 5)), SBBlocks.MISTWOOD_SAPLING.get()));

        register(context, MISTWOOD_TALL_TREE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_TALL_TREE),
                List.of(CountPlacement.of(UniformInt.of(5, 7)),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 80),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(SBBlocks.MISTWOOD_TALL_SAPLING.get().defaultBlockState(), BlockPos.ZERO)),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));

        register(context, MISTWOOD_STANDARD_TREE_INTERIOR_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_TREE_INTERIOR),
                List.of(CountPlacement.of(UniformInt.of(2, 5)),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 80),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(SBBlocks.MISTWOOD_TALL_SAPLING.get().defaultBlockState(), BlockPos.ZERO)),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));

        register(context, MISTWOOD_FLUFFY_TREE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_EDGE_TREE),
                VegetationPlacements.treePlacement(CountPlacement.of(UniformInt.of(15, 25)), SBBlocks.MISTWOOD_EDGE_SAPLING.get()));

        register(context, PETRIFIED_TREE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.PETRIFIED_TREE),
                List.of(CountPlacement.of(UniformInt.of(4, 8)),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(2),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(61, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.SHINGLE))
                ));

        register(context, SPARSE_PETRIFIED_TREE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.PETRIFIED_TREE),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(2),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 70),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.SHINGLE))
                ));
    }

    private static void mistwoodFoliage(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        register(context, MISTWOOD_LARGE_FERNS_PLACED_KEY, configuredFeatures.getOrThrow(VegetationFeatures.PATCH_LARGE_FERN),
                List.of(RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        HeightFilter.of(64, 70),
                        BiomeFilter.biome()));

        register(context, MISTWOOD_FERNS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_FERN_PATCH),
                VegetationPlacements.worldSurfaceSquaredWithCount(6));
        register(context, MISTWOOD_MOSS_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.MISTWOOD_MOSS_PATCH),
                VegetationPlacements.worldSurfaceSquaredWithCount(8));

        register(context, MISTWOOD_FOREST_SMALL_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SMALL_MISTWOOD_BUSH),
                List.of(CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.ZERO, 1).add(ConstantInt.of(1), 4).add(ConstantInt.of(2), 3).build())),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(64, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));

        register(context, MISTWOOD_FOREST_LARGE_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.LARGE_MISTWOOD_BUSH),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(64, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));
    }

    private static void foliage(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        register(context, SMALL_MISTWOOD_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.SMALL_MISTWOOD_BUSH),
                List.of(RarityFilter.onAverageOnceEvery(10),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));


        register(context, LARGE_MISTWOOD_BUSH_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.LARGE_MISTWOOD_BUSH),
                List.of(RarityFilter.onAverageOnceEvery(20),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(65, 75),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));
    }

    private static void corpses(BootstrapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {

        register(context, BEACHED_CORPSE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.BEACHED_CORPSE),
                List.of(RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        HeightFilter.of(63, 64),
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockPos.ZERO.below(), SBTags.Blocks.BREACH_GROUND))
                ));

    }

    private static PlacementModifier placeOnSurface() {
        return SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 0);
    }


    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ShoresBetween.MODID, name));
    }

    private static  void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> holder, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(holder, List.copyOf(modifiers)));
    }

}
