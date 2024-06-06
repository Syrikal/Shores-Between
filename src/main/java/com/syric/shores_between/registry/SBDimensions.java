package com.syric.shores_between.registry;

import com.syric.shores_between.worldgen.dimension.generation_formulae.DensityUtil;
import com.syric.shores_between.worldgen.dimension.generation_formulae.RockFields;
import com.syric.shores_between.worldgen.dimension.generation_formulae.Strands;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
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
    public static final TagKey<Block> INFINIBURN_BREACH = BlockTags.create(new ResourceLocation("infiniburn_breach"));

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
                        OptionalLong.of(0L), //fixed time
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
                        INFINIBURN_BREACH, //Infiniburn blocks
                        BuiltinDimensionTypes.NETHER_EFFECTS, //effects (mostly sky stuff)
                        0.1F, //Ambient light
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
                new FixedBiomeSource(biomeRegistry.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME)),
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

                Blocks.SMOOTH_BASALT.defaultBlockState(), //default block

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

                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION), //Erosion
                        NoiseRouterData.getFunction(functions, NoiseRouterData.DEPTH), //Depth
                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES), //Ridges
//                        new DensityFunctions.HolderHolder(functions.getOrThrow(RockFields.ROCK_FIELDS_FINAL)), //initial density without jaggedness
                        DensityFunctions.zero(), //initial density without jaggedness
                        DensityUtil.applyAll(DensityFunctions::max,
                                new DensityFunctions.HolderHolder(functions.getOrThrow(Strands.STRANDS_FINAL)),
                                new DensityFunctions.HolderHolder(functions.getOrThrow(RockFields.ROCK_FIELDS_FINAL))
                        ), //Final density
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

                        //Deepslate
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:deepslate_layer", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                                SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())
                        ),

                        //Stone
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:tougher_layer", VerticalAnchor.absolute(20), VerticalAnchor.absolute(30)),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(
                                                SurfaceRules.stoneDepthCheck(-4, true, 0, CaveSurface.FLOOR)
                                        ),
                                        SurfaceRules.state(Blocks.STONE.defaultBlockState())
                                )
                        ),

                        //Shoreline gravel
                                //Tapers from 55 to 63 and back down to 65
                                //Then a surface depth check
                        SurfaceRules.ifTrue(
                                SurfaceRules.verticalGradient("shores_between:shoreline_gravel_top", VerticalAnchor.absolute(63), VerticalAnchor.absolute(65)),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.not(SurfaceRules.verticalGradient("shores_between:shoreline_gravel_bottom", VerticalAnchor.absolute(55), VerticalAnchor.absolute(63))),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(Noises.ICE, 0.24, 1.8),
                                                        SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())
                                                )
                                        )
                                )
                        ),

                        //Strand gravel
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.abovePreliminarySurface(),
//                                SurfaceRules.state(Blocks.GRAVEL.defaultBlockState())
//                        ),

                        //Tangled shingle
                        //Do as ore instead?

                        //Grass
                        SurfaceRules.ifTrue(
                                SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.isBiome(SBBiomes.MISTWOOD_BIOME),
                                        SurfaceRules.ifTrue(
                                                SurfaceRules.verticalGradient("shores_between:dunegrass", VerticalAnchor.absolute(64),VerticalAnchor.absolute(66)),
                                                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
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
        context.register(ROCKINESS_NOISE, new NormalNoise.NoiseParameters(-8, DoubleList.of(2, 1, 0, 2, 1, 1, 1)));
        context.register(VITALITY_NOISE, new NormalNoise.NoiseParameters(-9, DoubleList.of(2, 1, 1, 0, 1, 1, 1)));
        context.register(BREACH_CONTINENTAL_NOISE, new NormalNoise.NoiseParameters(-9, DoubleList.of(1.4, 1, 0.8, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5)));
    }

    //Generates the altered density functions used to modify the biome noise.
    public static List<DensityFunction> bootstrapBiomeDensity(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        Holder<DensityFunction> splined_rockiness = context.register(SPLINED_ROCKINESS, DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(DensityFunctions.noise(noises.getOrThrow(ROCKINESS_NOISE), 1, 0))))
                .addPoint(-1, -1, 0)
                .addPoint(-0.6F, -0.7F, 0.5F)
                .addPoint(0.6F, 0.7F, 0.5F)
                .addPoint(1, 1, 0)
                .build()));

        Holder<DensityFunction> splined_vitality = context.register(SPLINED_VITALITY, DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(DensityFunctions.noise(noises.getOrThrow(VITALITY_NOISE), 1, 0))))
                .addPoint(-1, -1, 0)
                .addPoint(-0.6F, -0.7F, 0.5F)
                .addPoint(0.6F, 0.7F, 0.5F)
                .addPoint(1, 1, 0)
                .build()));

        Holder<DensityFunction> breach_continentalness = context.register(CONTINENTALNESS, DensityFunctions.noise(noises.getOrThrow(BREACH_CONTINENTAL_NOISE), 1, 0));

//        context.register(SPLINED_ROCKINESS, splined_rockiness);
//        context.register(SPLINED_VITALITY, splined_vitality);

        return List.of(new DensityFunctions.HolderHolder(splined_rockiness), new DensityFunctions.HolderHolder(splined_vitality), new DensityFunctions.HolderHolder(breach_continentalness));
    }
}
