package com.syric.shores_between.worldgen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBiomes;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.worldgen.feature.SBPlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SBBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_TANGLED_SHINGLE = registerKey("add_tangled_shingle");
    public static final ResourceKey<BiomeModifier> ADD_RUSTY_SCRAP = registerKey("add_rusty_scrap");

    //Desolate Strand
    public static final ResourceKey<BiomeModifier> ADD_DESOLATE_STRAND_ROCKS = registerKey("add_desolate_strand_rocks");
    public static final ResourceKey<BiomeModifier> ADD_DESOLATE_STRAND_ORES = registerKey("add_desolate_strand_ores");
    public static final ResourceKey<BiomeModifier> ADD_DESOLATE_STRAND_PLANTS = registerKey("add_desolate_strand_plants");
    public static final ResourceKey<BiomeModifier> ADD_DESOLATE_STRAND_MISC = registerKey("add_desolate_strand_misc");

    //Drowned Forest
    public static final ResourceKey<BiomeModifier> ADD_DROWNED_FOREST_ROCKS = registerKey("add_drowned_forest_rocks");
    public static final ResourceKey<BiomeModifier> ADD_DROWNED_FOREST_ORES = registerKey("add_drowned_forest_ores");
    public static final ResourceKey<BiomeModifier> ADD_DROWNED_FOREST_PLANTS = registerKey("add_drowned_forest_plants");
    public static final ResourceKey<BiomeModifier> ADD_DROWNED_FOREST_MISC = registerKey("add_drowned_forest_misc");

    //Barren Strand
    public static final ResourceKey<BiomeModifier> ADD_BARREN_STRAND_ROCKS = registerKey("add_barren_strand_rocks");
    public static final ResourceKey<BiomeModifier> ADD_BARREN_STRAND_ORES = registerKey("add_barren_strand_ores");
    public static final ResourceKey<BiomeModifier> ADD_BARREN_STRAND_PLANTS = registerKey("add_barren_strand_plants");
    public static final ResourceKey<BiomeModifier> ADD_BARREN_STRAND_MISC = registerKey("add_barren_strand_misc");

    //Rocky Strand
    public static final ResourceKey<BiomeModifier> ADD_ROCKY_STRAND_ROCKS = registerKey("add_rocky_strand_rocks");
    public static final ResourceKey<BiomeModifier> ADD_ROCKY_STRAND_ORES = registerKey("add_rocky_strand_ores");
    public static final ResourceKey<BiomeModifier> ADD_ROCKY_STRAND_PLANTS = registerKey("add_rocky_strand_plants");
    public static final ResourceKey<BiomeModifier> ADD_ROCKY_STRAND_MISC = registerKey("add_rocky_strand_misc");

    //Rock Fields
    public static final ResourceKey<BiomeModifier> ADD_ROCK_FIELDS_ROCKS = registerKey("add_rock_fields_rocks");
    public static final ResourceKey<BiomeModifier> ADD_ROCK_FIELDS_ORES = registerKey("add_rock_fields_ores");
    public static final ResourceKey<BiomeModifier> ADD_ROCK_FIELDS_PLANTS = registerKey("add_rock_fields_plants");
    public static final ResourceKey<BiomeModifier> ADD_ROCK_FIELDS_MISC = registerKey("add_rock_fields_misc");

    //Crags
    public static final ResourceKey<BiomeModifier> ADD_CRAGS_ROCKS = registerKey("add_crags_rocks");
    public static final ResourceKey<BiomeModifier> ADD_CRAGS_ORES = registerKey("add_crags_ores");
    public static final ResourceKey<BiomeModifier> ADD_CRAGS_PLANTS = registerKey("add_crags_plants");
    public static final ResourceKey<BiomeModifier> ADD_CRAGS_MISC = registerKey("add_crags_misc");
    
    //Grassy Strand
    public static final ResourceKey<BiomeModifier> ADD_GRASSY_STRAND_ROCKS = registerKey("add_grassy_strand_rocks");
    public static final ResourceKey<BiomeModifier> ADD_GRASSY_STRAND_ORES = registerKey("add_grassy_strand_ores");
    public static final ResourceKey<BiomeModifier> ADD_GRASSY_STRAND_PLANTS = registerKey("add_grassy_strand_plants");

    //Driftwood Beach
    public static final ResourceKey<BiomeModifier> ADD_DRIFTWOOD_BEACH_ROCKS = registerKey("add_driftwood_beach_rocks");
    public static final ResourceKey<BiomeModifier> ADD_DRIFTWOOD_BEACH_ORES = registerKey("add_driftwood_beach_ores");
    public static final ResourceKey<BiomeModifier> ADD_DRIFTWOOD_BEACH_PLANTS = registerKey("add_driftwood_beach_plants");

    //Mistwood and Mistwood Edge
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_ROCKS = registerKey("add_mistwood_rocks");
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_EDGE_TREES = registerKey("add_mistwood_edge_trees");
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_INTERIOR_TREES = registerKey("add_mistwood_interior_trees");
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_DECORATIONS = registerKey("add_mistwood_decorations");
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_INTERIOR_DECORATIONS = registerKey("add_mistwood_interior_decorations");
    public static final ResourceKey<BiomeModifier> ADD_MISTWOOD_ORES = registerKey("add_mistwood_ores");
    
    //Ocean
    public static final ResourceKey<BiomeModifier> ADD_FORSAKEN_OCEAN_ROCKS = registerKey("add_forsaken_ocean_rocks");
    public static final ResourceKey<BiomeModifier> ADD_FORSAKEN_OCEAN_ORES = registerKey("add_forsaken_ocean_ores");
    public static final ResourceKey<BiomeModifier> ADD_FORSAKEN_OCEAN_PLANTS = registerKey("add_forsaken_ocean_plants");
    public static final ResourceKey<BiomeModifier> ADD_FORSAKEN_OCEAN_MISC = registerKey("add_forsaken_ocean_misc");



//    //Logs
//    public static final ResourceKey<BiomeModifier> ADD_DRIFTWOOD_LOGS = registerKey("add_driftwood_logs");
//    public static final ResourceKey<BiomeModifier> ADD_EXTRA_DRIFTWOOD_LOGS = registerKey("add_extra_driftwood_logs");
//    public static final ResourceKey<BiomeModifier> ADD_PETRIFIED_LOGS = registerKey("add_petrified_logs");
//    public static final ResourceKey<BiomeModifier> ADD_EXTRA_PETRIFIED_LOGS = registerKey("add_extra_petrified_logs");
//
//    //Patches
//    public static final ResourceKey<BiomeModifier> ADD_SEAWEED_CLUMPS = registerKey("add_seaweed_clumps");
//    public static final ResourceKey<BiomeModifier> ADD_DEAD_FISH = registerKey("add_dead_fish");
//    public static final ResourceKey<BiomeModifier> ADD_EXTRA_DEAD_FISH = registerKey("add_extra_dead_fish");
//
//    //Rocks
//    public static final ResourceKey<BiomeModifier> ADD_SMALL_BOULDERS = registerKey("add_small_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_MORE_SMALL_BOULDERS = registerKey("add_more_small_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_EVEN_MORE_SMALL_BOULDERS = registerKey("add_even_more_small_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_LARGE_BOULDERS = registerKey("add_large_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_MORE_LARGE_BOULDERS = registerKey("add_more_large_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_EVEN_MORE_LARGE_BOULDERS = registerKey("add_even_more_large_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_SMALL_OUTCROPS = registerKey("add_small_outcrops");
//    public static final ResourceKey<BiomeModifier> ADD_MOSSY_OUTCROPS = registerKey("add_mossy_outcrops");
//    public static final ResourceKey<BiomeModifier> ADD_RARE_MOSSY_OUTCROPS = registerKey("add_rare_mossy_outcrops");
//    public static final ResourceKey<BiomeModifier> ADD_EERIE_BOULDERS = registerKey("add_eerie_boulders");
//    public static final ResourceKey<BiomeModifier> ADD_CORPSE_BOULDERS = registerKey("add_corpse_boulders");
//
//    //Foliage
//    public static final ResourceKey<BiomeModifier> ADD_SPARSE_BUSHES = registerKey("add_sparse_bushes");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        desolateStrand(context, placedFeatures, biomes);
        drownedForest(context, placedFeatures, biomes);
        barrenStrand(context, placedFeatures, biomes);
        rockyStrand(context, placedFeatures, biomes);
        rockFields(context, placedFeatures, biomes);
        crags(context, placedFeatures, biomes);
        grassyStrand(context, placedFeatures, biomes);
        driftwoodBeach(context, placedFeatures, biomes);
        mistwood(context, placedFeatures, biomes);
        ocean(context, placedFeatures, biomes);

    }

    private static void desolateStrand(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_DESOLATE_STRAND_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.CORPSE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.EERIE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_OUTCROP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_MOSSY_OUTCROPS_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_DESOLATE_STRAND_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RUSTY_SCRAP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DESOLATE_STRAND_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.BURIED_PETRIFIED_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_DRIFTWOOD_SCATTER_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_DESOLATE_STRAND_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DEAD_FISH_PATCH_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void drownedForest(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
//        context.register(ADD_DROWNED_FOREST_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME)),
//                HolderSet.direct(
//                ),
//                GenerationStep.Decoration.LOCAL_MODIFICATIONS
//        ));

        context.register(ADD_DROWNED_FOREST_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.RUSTY_SCRAP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DROWNED_FOREST_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.PETRIFIED_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.BURIED_PETRIFIED_LOG_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_DROWNED_FOREST_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DEAD_FISH_PATCH_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void barrenStrand(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
//        context.register(ADD_BARREN_STRAND_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(SBBiomes.BARREN_STRAND_BIOME)),
//                HolderSet.direct(
//                ),
//                GenerationStep.Decoration.LOCAL_MODIFICATIONS
//        ));

        context.register(ADD_BARREN_STRAND_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.BARREN_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.RUSTY_SCRAP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_BARREN_STRAND_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.BARREN_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.BURIED_PETRIFIED_LOG_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_BARREN_STRAND_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.BARREN_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DEAD_FISH_PATCH_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void rockyStrand(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_ROCKY_STRAND_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_SMALL_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.EVEN_MORE_SMALL_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_LARGE_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.EVEN_MORE_LARGE_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.CORPSE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.EERIE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_OUTCROP_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_ROCKY_STRAND_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RUSTY_SCRAP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_ROCKY_STRAND_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.BURIED_PETRIFIED_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_DRIFTWOOD_SCATTER_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_ROCKY_STRAND_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DEAD_FISH_PATCH_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void rockFields(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_ROCK_FIELDS_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_SMALL_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_LARGE_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.CORPSE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.EERIE_BOULDER_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_ROCK_FIELDS_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_ROCK_FIELDS_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_ROCK_FIELDS_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DEAD_FISH_PATCH_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void crags(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_CRAGS_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.CRAGS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_SMALL_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MORE_LARGE_BOULDERS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_OUTCROP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_MOSSY_OUTCROPS_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_CRAGS_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.CRAGS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RUSTY_SCRAP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_CRAGS_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.CRAGS_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.BURIED_PETRIFIED_LOG_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

//        context.register(ADD_CRAGS_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(SBBiomes.CRAGS_BIOME)),
//                HolderSet.direct(
//                ),
//                GenerationStep.Decoration.VEGETAL_DECORATION
//        ));
    }

    private static void grassyStrand(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_GRASSY_STRAND_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MOSSY_OUTCROP_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_GRASSY_STRAND_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_GRASSY_STRAND_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_MISTWOOD_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_MISTWOOD_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_DRIFTWOOD_SCATTER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_SCATTER_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void driftwoodBeach(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_DRIFTWOOD_BEACH_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_OUTCROP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MOSSY_OUTCROP_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_DRIFTWOOD_BEACH_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));

        context.register(ADD_DRIFTWOOD_BEACH_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_LOG_ON_DRIFTWOOD_BEACH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_MISTWOOD_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_MISTWOOD_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SEAWEED_CLUMP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.RARE_DRIFTWOOD_SCATTER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_SCATTER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SPARSE_DRIFTWOOD_SCATTER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.DRIFTWOOD_MAT_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static void mistwood(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {

        context.register(ADD_MISTWOOD_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.MISTWOOD_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MOSSY_OUTCROP_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MOSSY_BOULDER_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_MISTWOOD_INTERIOR_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.MISTWOOD_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_STANDARD_TREE_INTERIOR_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_TALL_TREE_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_MISTWOOD_EDGE_TREES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.MISTWOOD_EDGE_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_FLUFFY_TREE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_STANDARD_TREE_EDGE_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_MISTWOOD_INTERIOR_DECORATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.MISTWOOD_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_LOG_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_FERNS_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_LARGE_FERNS_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_MISTWOOD_DECORATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(SBTags.Biomes.MISTWOOD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_FOREST_SMALL_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_FOREST_LARGE_BUSH_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.MISTWOOD_MOSS_PLACED_KEY)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(ADD_MISTWOOD_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(SBTags.Biomes.MISTWOOD),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static void ocean(BootstrapContext<BiomeModifier> context, HolderGetter<PlacedFeature> placedFeatures, HolderGetter<Biome> biomes) {
        context.register(ADD_FORSAKEN_OCEAN_ROCKS, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SMALL_BOULDER_PLACED_KEY),
                        placedFeatures.getOrThrow(SBPlacedFeatures.LARGE_BOULDER_PLACED_KEY)
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS
        ));

        context.register(ADD_FORSAKEN_OCEAN_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME)),
                HolderSet.direct(
                        placedFeatures.getOrThrow(SBPlacedFeatures.SALTSTONE_PLACED_KEY)
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
//
//        context.register(ADD_FORSAKEN_OCEAN_PLANTS, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME)),
//                HolderSet.direct(
//                ),
//                GenerationStep.Decoration.VEGETAL_DECORATION
//        ));
//
//        context.register(ADD_FORSAKEN_OCEAN_MISC, new BiomeModifiers.AddFeaturesBiomeModifier(
//                HolderSet.direct(biomes.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME)),
//                HolderSet.direct(
//                ),
//                GenerationStep.Decoration.VEGETAL_DECORATION
//        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ShoresBetween.MODID, name));
    }

}