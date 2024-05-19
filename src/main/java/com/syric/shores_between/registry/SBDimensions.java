package com.syric.shores_between.registry;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
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
    public static final ResourceKey<NormalNoise.NoiseParameters> BREACH_CONTINENTAL_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "breach_continentalness"));

    public static final ResourceKey<DensityFunction> SPLINED_ROCKINESS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "splined_rockiness"));
    public static final ResourceKey<DensityFunction> SPLINED_VITALITY = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "splined_vitality"));


    //Generates the Breach dimension type.
    public static void bootstrapDimensionType(BootstapContext<DimensionType> context) {
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
    public static void bootstrapLevelStem(BootstapContext<LevelStem> context) {
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
    public static void bootstrapNoiseSettings(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        DensityFunction shift_x = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_X);
        DensityFunction shift_z = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_Z);

        context.register(BREACH_NOISE, new NoiseGeneratorSettings(
                NoiseSettings.create(
                        -64, //min_y
                        384, //height
                        1, //size_horizontal
                        1), //size_vertical

                Blocks.GRAVEL.defaultBlockState(), //default block

                Blocks.WATER.defaultBlockState(), //default fluid

                //Noise router
                new NoiseRouter(
                        DensityFunctions.zero(), //barrier noise
                        DensityFunctions.zero(), //fluid level floodedness noise
                        DensityFunctions.zero(), //fluid level spread noise
                        DensityFunctions.zero(), //lava noise
//                        functions.getOrThrow(SPLINED_ROCKINESS).get(),
                        DensityFunctions.shiftedNoise2d(shift_x, shift_z, 0.25, noises.getOrThrow(Noises.TEMPERATURE)),
//                        functions.getOrThrow(SPLINED_VITALITY).get(),
                        DensityFunctions.shiftedNoise2d(shift_x, shift_z, 0.25, noises.getOrThrow(Noises.VEGETATION)),
                        NoiseRouterData.getFunction(functions, NoiseRouterData.CONTINENTS), //Continents
                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION), //Erosion
                        NoiseRouterData.getFunction(functions, NoiseRouterData.DEPTH), //Depth
                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES), //Ridges
                        DensityFunctions.zero(), //initial density without jaggedness
                        DensityFunctions.zero(), //Final density
                        DensityFunctions.zero(), //vein toggle
                        DensityFunctions.zero(), //vein ridged
                        DensityFunctions.zero() //vein gap
                ),

                SurfaceRules.sequence(
                        SurfaceRules.state(Blocks.REDSTONE_BLOCK.defaultBlockState())
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
    public static void bootstrapBiomeNoise(BootstapContext<NormalNoise.NoiseParameters> context) {
        context.register(ROCKINESS_NOISE, new NormalNoise.NoiseParameters(-8, DoubleList.of(2, 1, 0, 2, 1, 1, 1)));
        context.register(VITALITY_NOISE, new NormalNoise.NoiseParameters(-9, DoubleList.of(2, 1, 1, 0, 1, 1, 1)));
        context.register(BREACH_CONTINENTAL_NOISE, new NormalNoise.NoiseParameters(-9, DoubleList.of(1.4, 1, 0.8, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5)));
    }

    //Generates the altered density functions used to modify the biome noise.
    public static List<DensityFunction> bootstrapBiomeDensity(BootstapContext<DensityFunction> context) {
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

//        context.register(SPLINED_ROCKINESS, splined_rockiness);
//        context.register(SPLINED_VITALITY, splined_vitality);

        return List.of(new DensityFunctions.HolderHolder(splined_rockiness), new DensityFunctions.HolderHolder(splined_vitality));
    }

//    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
//        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
//        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
//        DensityFunction densityfunction = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_X);
//        DensityFunction densityfunction1 = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_Z);
//        context.register(BREACH_NOISE, new NoiseGeneratorSettings(
//                NoiseSettings.create(-64, 384, 1, 1),
//
//                Blocks.GRAVEL.defaultBlockState(),
//
//                Blocks.WATER.defaultBlockState(),
//
//
//
//                new NoiseRouter(
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.TEMPERATURE)),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.VEGETATION)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.CONTINENTS),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION),
//                        DensityFunctions.rangeChoice(
//                                NoiseRouterData.getFunction(functions, NoiseRouterData.Y), 0.0, 32.0,
//                                DensityFunctions.constant(2.0),
//                                DensityFunctions.constant(-2.0)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES),
//                        DensityFunctions.zero(),
//                        DensityFunctions.mul(
//                                DensityFunctions.constant(0.64),
//                                DensityFunctions.interpolated(
//                                        DensityFunctions.blendDensity(
//                                                DensityFunctions.add(
//                                                        DensityFunctions.constant(2.5),
//                                                        DensityFunctions.mul(
//                                                                DensityFunctions.yClampedGradient(-8, 24, 0.0, 1.0),
//                                                                DensityFunctions.add(
//                                                                        DensityFunctions.constant(-2.5),
//                                                                        DensityFunctions.add(
//                                                                                DensityFunctions.constant(0.5),
//                                                                                DensityFunctions.mul(
//                                                                                        DensityFunctions.yClampedGradient(110, 128, 1.0, 0.0),
//                                                                                        DensityFunctions.add(
//                                                                                                DensityFunctions.constant(-0.5),
//                                                                                                BlendedNoise.createUnseeded(0.1, 0.3, 80.0, 60.0, 1.0)))))))))
//                        ).squeeze(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero()),
//
//
//
//                SurfaceRules.sequence(
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
//                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.not(
//                                        SurfaceRules.verticalGradient("minecraft:bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
//                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0),
//                                SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.not(
//                                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(33), 0)),
//                                        SurfaceRules.state(UGBlocks.SEDIMENT.get().defaultBlockState()))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.BLOOD_MUSHROOM_BOG),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                        SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.SMOG_SPIRES),
//                                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                        SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.ASHEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.BARREN_ABYSS, UGBiomes.DEAD_SEA),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())),
//                                                SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.POWDER_SNOW).key(), 0.45, 0.58),
//                                                        SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.FROZEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                SurfaceRules.state(UGBlocks.DEEPSOIL.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST),
//                                SurfaceRules.state(UGBlocks.SHIVERSTONE.get().defaultBlockState()))
//                ),
//
//
//
//                List.of(),
//                32,
//                false,
//                false,
//                false,
//                false));
//    }

//    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
//        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
//        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
//        DensityFunction densityfunction = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_X);
//        DensityFunction densityfunction1 = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_Z);
//        context.register(BREACH_NOISE, new NoiseGeneratorSettings(
//                NoiseSettings.create(0, 128, 2, 2),
//
//                UGBlocks.DEPTHROCK.get().defaultBlockState(),
//
//                Blocks.WATER.defaultBlockState(),
//
//
//                new NoiseRouter(
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.TEMPERATURE)),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.VEGETATION)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.CONTINENTS),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION),
//                        DensityFunctions.rangeChoice(NoiseRouterData.getFunction(functions, NoiseRouterData.Y), 0.0, 32.0, DensityFunctions.constant(2.0), DensityFunctions.constant(-2.0)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES),
//                        DensityFunctions.zero(),
//                        DensityFunctions.mul(DensityFunctions.constant(0.64), DensityFunctions.interpolated(DensityFunctions.blendDensity(DensityFunctions.add(DensityFunctions.constant(2.5), DensityFunctions.mul(DensityFunctions.yClampedGradient(-8, 24, 0.0, 1.0), DensityFunctions.add(DensityFunctions.constant(-2.5), DensityFunctions.add(DensityFunctions.constant(0.5), DensityFunctions.mul(DensityFunctions.yClampedGradient(110, 128, 1.0, 0.0), DensityFunctions.add(DensityFunctions.constant(-0.5), BlendedNoise.createUnseeded(0.1, 0.3, 80.0, 60.0, 1.0)))))))))).squeeze(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero()),
//
//
//                SurfaceRules.sequence(
//                        SurfaceRules.ifTrue(SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("minecraft:bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0), SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR), SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(33), 0)), SurfaceRules.state(UGBlocks.SEDIMENT.get().defaultBlockState()))),
//
//                        SurfaceRules.ifTrue(SurfaceRules.isBiome(UGBiomes.BLOOD_MUSHROOM_BOG), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8), SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(SurfaceRules.isBiome(UGBiomes.SMOG_SPIRES), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8), SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.ASHEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(SurfaceRules.isBiome(UGBiomes.BARREN_ABYSS, UGBiomes.DEAD_SEA), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8), SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())), SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())))),
//
//                        SurfaceRules.ifTrue(SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(noises.getOrThrow(Noises.POWDER_SNOW).key(), 0.45, 0.58), SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState())), SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.FROZEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR), SurfaceRules.state(UGBlocks.DEEPSOIL.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST), SurfaceRules.state(UGBlocks.SHIVERSTONE.get().defaultBlockState()))
//                ),
//
//
//                List.of(),
//                32,
//                false,
//                false,
//                false,
//                false));
//    }

}
