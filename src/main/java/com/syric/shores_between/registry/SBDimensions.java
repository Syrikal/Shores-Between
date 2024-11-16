package com.syric.shores_between.registry;

import com.syric.shores_between.datagen.SBWorldGenProvider;
import com.syric.shores_between.worldgen.dimension.BreachBiomeSource;
import com.syric.shores_between.worldgen.dimension.generation_formulae.DensityUtil;
import com.syric.shores_between.worldgen.dimension.generation_formulae.Mistwood;
import com.syric.shores_between.worldgen.dimension.generation_formulae.RockFields;
import com.syric.shores_between.worldgen.dimension.generation_formulae.Strands;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.OptionalLong;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBDimensions {

    public static final ResourceKey<LevelStem> BREACH_DIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(MODID, "breach"));
    public static final ResourceKey<Level> BREACH_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(MODID, "breach"));
    public static final ResourceKey<DimensionType> BREACH_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(MODID, "breach_dim_type"));
    public static final ResourceKey<NoiseGeneratorSettings> BREACH_NOISE = ResourceKey.create(Registries.NOISE_SETTINGS,
            new ResourceLocation(MODID, "breach_noise_settings"));

    public static final ResourceKey<NormalNoise.NoiseParameters> ROCKINESS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "rockiness"));
    public static final ResourceKey<NormalNoise.NoiseParameters> VITALITY_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "vitality"));
    public static final ResourceKey<NormalNoise.NoiseParameters> BREACH_CONTINENTAL_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "breach_continentalness_noise"));

    public static final ResourceKey<DensityFunction> SPLINED_ROCKINESS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "splined_rockiness"));
    public static final ResourceKey<DensityFunction> SPLINED_VITALITY = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "splined_vitality"));
    public static final ResourceKey<DensityFunction> CONTINENTALNESS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "breach_continentalness"));


    //Generates the Breach dimension type.
    public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
        context.register(BREACH_DIM_TYPE,
                new DimensionType(
                        OptionalLong.empty(), //fixed time
                        true, //has skylight
                        false, //has ceiling
                        false, //ultrawarm (evaporation)
                        false, //natural
                        16.0, //coordinate scale
                        false, //bed works
                        false, //respawn anchor works
                        -64, //min Y
                        256, //height
                        256, //logical height
                        SBTags.Blocks.INFINIBURN_BREACH, //Infiniburn blocks
                        SBDimensionEffects.BREACH_EFFECTS, //effects (mostly sky stuff)
                        0.03F, //Ambient light
                        new DimensionType.MonsterSettings( //Monster settings
                                false, //safe for piglins
                                false, //has raids
                                ConstantInt.of(0),
                                0 //MonsterSpawnBlockLightLimit
                        )
                )
        );
    }

    //Generates the Breach dimension
    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                BreachBiomeSource.create(biomeRegistry),
                noiseGenSettings.getOrThrow(BREACH_NOISE));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(SBDimensions.BREACH_DIM_TYPE), wrappedChunkGenerator);

        context.register(BREACH_DIM_KEY, stem);
    }

    //Generates the Breach noise settings, responsible for most terrain.
    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        DensityFunction shift_x = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_X);
        DensityFunction shift_z = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_Z);

        context.register(BREACH_NOISE, new NoiseGeneratorSettings(
                NoiseSettings.create( //Noise Settings
                        -64, //min_y
                        384, //height
                        1, //size_horizontal
                        1), //size_vertical

                SBBlocks.SHINGLE.get().defaultBlockState(), //default block

                Blocks.WATER.defaultBlockState(), //default fluid

                //Noise router
                new NoiseRouter(
                        DensityFunctions.zero(), //barrier noise
                        DensityFunctions.zero(), //fluid level floodedness noise
                        DensityFunctions.zero(), //fluid level spread noise
                        DensityFunctions.zero(), //lava noise
                        new DensityFunctions.HolderHolder(functions.getOrThrow(SPLINED_ROCKINESS)), //Temperature (Rockiness)
//                        DensityFunctions.shiftedNoise2d(shift_x, shift_z, 0.25, noises.getOrThrow(Noises.TEMPERATURE)),
                        new DensityFunctions.HolderHolder(functions.getOrThrow(SPLINED_VITALITY)), //Vegetation (Vitality)
//                        DensityFunctions.shiftedNoise2d(shift_x, shift_z, 0.25, noises.getOrThrow(Noises.VEGETATION)),
                        new DensityFunctions.HolderHolder(functions.getOrThrow(CONTINENTALNESS)), //Continents
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.CONTINENTS),
                        new DensityFunctions.HolderHolder(functions.getOrThrow(Mistwood.MISTWOOD_ISLANDS)), //Erosion (Mistwood Islands)
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION),

                        NoiseRouterData.getFunction(functions, NoiseRouterData.DEPTH), //Depth
                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES), //Ridges
//                        new DensityFunctions.HolderHolder(functions.getOrThrow(RockFields.ROCK_FIELDS_FINAL)), //initial density without jaggedness
                        DensityFunctions.zero(), //initial density without jaggedness
                        DensityFunctions.interpolated(DensityUtil.applyAll(DensityFunctions::max,
                                new DensityFunctions.HolderHolder(functions.getOrThrow(Strands.STRANDS_FINAL)),
                                new DensityFunctions.HolderHolder(functions.getOrThrow(RockFields.ROCK_FIELDS_FINAL)),
                                new DensityFunctions.HolderHolder(functions.getOrThrow(Mistwood.MISTWOOD_FINAL))
                        )), //Final density
                        DensityFunctions.zero(), //vein toggle
                        DensityFunctions.zero(), //vein ridged
                        DensityFunctions.zero() //vein gap
                ),

                SurfaceRules.sequence(
                        //Bedrock floor
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(5)),
                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
                        ),

                        //Dark Shale
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:dark_slate_layer", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                                SurfaceRules.state(SBBlocks.DARK_SHALE.get().defaultBlockState())
                        ),

                        //Shale
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:slate_layer", VerticalAnchor.absolute(45), VerticalAnchor.absolute(55)),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(
                                                SurfaceRules.stoneDepthCheck(-4, true, 0, CaveSurface.FLOOR)
                                        ),
                                        SurfaceRules.state(SBBlocks.SHALE.get().defaultBlockState())
                                )
                        ),

                        //Biome Painting
                        //Colors each biome with a layer of concrete
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.waterBlockCheck(-1, 0),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.DESOLATE_STRAND_BIOME),
//                                                        SurfaceRules.state(Blocks.GRAY_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.BARREN_STRAND_BIOME),
//                                                        SurfaceRules.state(Blocks.WHITE_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.ROCKY_STRAND_BIOME),
//                                                        SurfaceRules.state(Blocks.BLACK_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.ROCK_FIELDS_BIOME),
//                                                        SurfaceRules.state(Blocks.PURPLE_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.CRAGS_BIOME),
//                                                        SurfaceRules.state(Blocks.PINK_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.GRASSY_STRAND_BIOME),
//                                                        SurfaceRules.state(Blocks.LIME_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.DRIFTWOOD_BEACH_BIOME),
//                                                        SurfaceRules.state(Blocks.BROWN_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.DROWNED_FOREST_BIOME),
//                                                        SurfaceRules.state(Blocks.YELLOW_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.MISTWOOD_EDGE_BIOME),
//                                                        SurfaceRules.state(Blocks.ORANGE_CONCRETE.defaultBlockState())
//                                                ),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.isBiome(SBBiomes.MISTWOOD_BIOME),
//                                                        SurfaceRules.state(Blocks.RED_CONCRETE.defaultBlockState())
//                                                )
//                                        )
//                                )
//                        ),




                        //Shoreline pebbles
                                //Tapers from 55 to 63 and back down to 65
                                //Then a surface depth check

                        /**
                         * In barren strand and drowned forest, high Patch noise is pale pebbles, low is regular.
                         * In grassy strand and driftwood beach, high is regular, low is dark.
                         * Everywhere else, very high is pale, very low is dark, in between is regular.
                         */
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:shoreline_gravel_top", VerticalAnchor.absolute(60), VerticalAnchor.absolute(67)),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:shoreline_gravel_bottom", VerticalAnchor.absolute(57), VerticalAnchor.absolute(64))),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(Noises.ICE, 0.1, 2),
                                                        SurfaceRules.sequence(
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.isBiome(SBBiomes.BARREN_STRAND_BIOME, SBBiomes.DROWNED_FOREST_BIOME),
                                                                    SurfaceRules.sequence(
                                                                            SurfaceRules.ifTrue(
                                                                                    SurfaceRules.noiseCondition(Noises.PATCH, 0, 2),
                                                                                    SurfaceRules.state(SBBlocks.PALE_PEBBLES.get().defaultBlockState())
                                                                            ),
                                                                            SurfaceRules.ifTrue(
                                                                                    SurfaceRules.noiseCondition(Noises.PATCH, -2, -0.6),
                                                                                    SurfaceRules.state(SBBlocks.DARK_PEBBLES.get().defaultBlockState())
                                                                            ),
                                                                            SurfaceRules.state(SBBlocks.PEBBLES.get().defaultBlockState())
                                                                    )
                                                            ),
                                                            SurfaceRules.ifTrue(
                                                                    SurfaceRules.isBiome(SBBiomes.DRIFTWOOD_BEACH_BIOME, SBBiomes.GRASSY_STRAND_BIOME, SBBiomes.MISTWOOD_BIOME, SBBiomes.MISTWOOD_EDGE_BIOME),
                                                                    SurfaceRules.sequence(
                                                                            SurfaceRules.ifTrue(
                                                                                    SurfaceRules.noiseCondition(Noises.PATCH, 0.6, 2),
                                                                                    SurfaceRules.state(SBBlocks.PALE_PEBBLES.get().defaultBlockState())
                                                                            ),
                                                                            SurfaceRules.ifTrue(
                                                                                    SurfaceRules.noiseCondition(Noises.PATCH, -2, 0),
                                                                                    SurfaceRules.state(SBBlocks.DARK_PEBBLES.get().defaultBlockState())
                                                                            ),
                                                                            SurfaceRules.state(SBBlocks.PEBBLES.get().defaultBlockState())
                                                                    )
                                                            ),
                                                            SurfaceRules.sequence(
                                                                    SurfaceRules.ifTrue(
                                                                            SurfaceRules.noiseCondition(Noises.PATCH, 0.3, 2),
                                                                            SurfaceRules.state(SBBlocks.PALE_PEBBLES.get().defaultBlockState())
                                                                    ),
                                                                    SurfaceRules.ifTrue(
                                                                            SurfaceRules.noiseCondition(Noises.PATCH, -2, -0.3),
                                                                            SurfaceRules.state(SBBlocks.DARK_PEBBLES.get().defaultBlockState())
                                                                    ),
                                                                    SurfaceRules.state(SBBlocks.PEBBLES.get().defaultBlockState())
                                                            )

                                                        )

                                                )
                                        )
                                )
                        ),


                        //Surface Things
                        SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                                //Split by biome
                                SurfaceRules.sequence(
                                        //Mistwood
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(SBBiomes.MISTWOOD_BIOME, SBBiomes.MISTWOOD_EDGE_BIOME),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:mistwood_stuff", VerticalAnchor.absolute(62), VerticalAnchor.absolute(64))),
                                                        SurfaceRules.sequence(
                                                                //Moss
                                                                SurfaceRules.ifTrue(
                                                                        SurfaceRules.waterBlockCheck(-1, 0),
                                                                        SurfaceRules.sequence(
                                                                                SurfaceRules.ifTrue(
                                                                                        SurfaceRules.noiseCondition(Noises.PATCH, 0.35, 100),
                                                                                        SurfaceRules.state(SBBlocks.MISTWOOD_MOSS_BLOCK.get().defaultBlockState())),
                                                                                SurfaceRules.ifTrue(
                                                                                        SurfaceRules.noiseCondition(Noises.PATCH, -100, -0.35),
                                                                                        SurfaceRules.state(SBBlocks.MISTWOOD_MOSS_BLOCK.get().defaultBlockState()))
                                                                        )
                                                                ),
                                                                //Coarse dirt
                                                                SurfaceRules.ifTrue(
                                                                        SurfaceRules.noiseCondition(Noises.SURFACE, 0.5, 100),
                                                                        SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())
                                                                        //TODO Replace with dirt shingle
                                                                ),
                                                                //Podzol and grass
                                                                SurfaceRules.ifTrue(
                                                                        SurfaceRules.waterBlockCheck(-1, 0),
                                                                        SurfaceRules.sequence(
                                                                                //Podzol
                                                                                SurfaceRules.ifTrue(
                                                                                        SurfaceRules.noiseCondition(Noises.SURFACE, 0.1, 100),
                                                                                        SurfaceRules.state(Blocks.PODZOL.defaultBlockState())
                                                                                        //TODO Replace with podzol shingle
                                                                                ),
                                                                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                                                                //TODO Replace with grassy shingle
                                                                        )
                                                                )
                                                        )
                                                )
                                        ),
                                        //Grassy Strand
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(SBBiomes.GRASSY_STRAND_BIOME),
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:grassy_strand_grass", VerticalAnchor.absolute(63), VerticalAnchor.absolute(70))),
                                                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                                                //TODO Replace with grassy shingle
                                                        ),
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:grassy_strand_dirt", VerticalAnchor.absolute(63), VerticalAnchor.absolute(75))),
                                                                SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())
                                                                //TODO Replace with dirt shingle
                                                        )
                                                )
                                        ),
                                        //Driftwood Beach
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(SBBiomes.DRIFTWOOD_BEACH_BIOME),
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:driftwood_beach_grass", VerticalAnchor.absolute(65), VerticalAnchor.absolute(78))),
                                                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
                                                                //TODO Replace with dirt shingle
                                                        ),
                                                        SurfaceRules.ifTrue(
                                                                SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:driftwood_beach_dirt", VerticalAnchor.absolute(63), VerticalAnchor.absolute(73))),
                                                                SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState())
                                                                //TODO Replace with dirt shingle
                                                        )
                                                )
                                        )
                                )
                        ),

                        //Undersurface rules
                        SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
                                SurfaceRules.sequence(
                                        //Mistwood
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.isBiome(SBBiomes.MISTWOOD_BIOME, SBBiomes.MISTWOOD_EDGE_BIOME),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:mistwood_stuff", VerticalAnchor.absolute(62), VerticalAnchor.absolute(64))),
                                                        SurfaceRules.state(Blocks.COARSE_DIRT.defaultBlockState()))
                                                        //TODO Replace with dirt shingle
                                                )
                                )
                        )

                        //Rock fields obsidian
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(SBBiomes.MISTWOOD_BIOME),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.verticalGradient()
//                                )
//                        )

                        // Deep ocean ore - run as ore instead?
                ),

                List.of(), //spawn target (whatever that is)

                63, //sea level
                true, //disable mob generation
                true, //aquifers enabled
                false, //ore veins enabled
                false //use legacy random source
        ));
    }

    //Generates the noise functions used to place biomes.
    public static void bootstrapBiomeNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        context.register(ROCKINESS_NOISE, new NormalNoise.NoiseParameters(-9, DoubleList.of(1, 2, 0, 2, 1)));
        context.register(VITALITY_NOISE, new NormalNoise.NoiseParameters(-10, DoubleList.of(1.5, 1, 1)));
        context.register(BREACH_CONTINENTAL_NOISE, new NormalNoise.NoiseParameters(-10, DoubleList.of(1.5, 1, 1, 0, 1, 1, 0, 1)));
    }

    //Generates the altered density functions used to modify the biome noise.
    public static List<DensityFunction> bootstrapBiomeDensity(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        DensityFunction splined_rockiness = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(DensityFunctions.noise(noises.getOrThrow(ROCKINESS_NOISE), 1, 0))))
                .addPoint(-1, -1, 0)
                .addPoint(-0.6F, -0.7F, 0.5F)
                .addPoint(0.6F, 0.7F, 0.5F)
                .addPoint(1, 1, 0)
                .build());

        Holder<DensityFunction> splined_rockiness_holder = context.register(SPLINED_ROCKINESS, DensityFunctions.flatCache(splined_rockiness));
//        Holder<DensityFunction> splined_rockiness = context.register(SPLINED_ROCKINESS, DensityFunctions.noise(noises.getOrThrow(ROCKINESS_NOISE), 1, 0));

        DensityFunction splined_vitality = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(DensityFunctions.noise(noises.getOrThrow(VITALITY_NOISE), 1, 0))))
                .addPoint(-1, -1, 0)
                .addPoint(-0.6F, -0.7F, 0.5F)
                .addPoint(0.6F, 0.7F, 0.5F)
                .addPoint(1, 1, 0)
                .build());
        Holder<DensityFunction> splined_vitality_holder = context.register(SPLINED_VITALITY, DensityFunctions.flatCache(splined_vitality));
//        Holder<DensityFunction> splined_vitality = context.register(SPLINED_VITALITY, DensityFunctions.noise(noises.getOrThrow(VITALITY_NOISE), 1, 0));


        DensityFunction breach_continentalness = DensityFunctions.noise(noises.getOrThrow(BREACH_CONTINENTAL_NOISE), 1, 0);
        Holder<DensityFunction> breach_continentalness_holder = context.register(CONTINENTALNESS, DensityFunctions.flatCache(breach_continentalness));

//        context.register(SPLINED_ROCKINESS, splined_rockiness);
//        context.register(SPLINED_VITALITY, splined_vitality);

        return List.of(new DensityFunctions.HolderHolder(splined_rockiness_holder), new DensityFunctions.HolderHolder(splined_vitality_holder), new DensityFunctions.HolderHolder(breach_continentalness_holder));
    }

}
