package com.syric.shores_between.worldgen.dimension.generation_formulae;

import com.syric.shores_between.registry.SBDimensions;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static com.syric.shores_between.ShoresBetween.MODID;

public class Mistwood {

    public static final ResourceKey<NormalNoise.NoiseParameters> MISTWOOD_ISLANDS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "mistwood_islands_noise"));
    public static final ResourceKey<NormalNoise.NoiseParameters> MISTWOOD_TERRAIN_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "mistwood_terrain_noise"));

    public static final ResourceKey<DensityFunction> MISTWOOD_ISLANDS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "mistwood_islands"));
    public static final ResourceKey<DensityFunction> MISTWOOD_TERRAIN = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "mistwood_terrain"));
    public static final ResourceKey<DensityFunction> MISTWOOD_CHANNEL_ROUGHNESS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "mistwood_channel_roughness"));
    public static final ResourceKey<DensityFunction> MISTWOOD_INTERMEDIATE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "mistwood_intermediate"));
    public static final ResourceKey<DensityFunction> MISTWOOD_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "mistwood_final"));

    public static void bootstrapMistwoodNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        //Mistwood Islands is used to decide where islands go (by digging channels where it's 0).
        context.register(MISTWOOD_ISLANDS_NOISE, new NormalNoise.NoiseParameters(-8, DoubleList.of(2, 1)));
        //Mistwood Terrain is used to put hills and valleys in mistwoods.
        context.register(MISTWOOD_TERRAIN_NOISE, new NormalNoise.NoiseParameters(-6, DoubleList.of(1, 1, 1, 1)));
    }

    public static void bootstrapMistwoodDensity(BootstrapContext<DensityFunction> context, List<DensityFunction> biomeDensityFunctions) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        Holder<DensityFunction> mistwood_islands = context.register(MISTWOOD_ISLANDS, MistwoodIslands(context));
        Holder<DensityFunction> mistwood_terrain = context.register(MISTWOOD_TERRAIN, MistwoodTerrain(context, mistwood_islands));
        Holder<DensityFunction> mistwood_channel_roughness = context.register(MISTWOOD_CHANNEL_ROUGHNESS, MistwoodChannels(context, mistwood_islands));
        Holder<DensityFunction> mistwood_intermediate = context.register(MISTWOOD_INTERMEDIATE, MistwoodIntermediate(mistwood_islands, mistwood_terrain, mistwood_channel_roughness));
        Holder<DensityFunction> mistwood_final = context.register(MISTWOOD_FINAL, MistwoodFinal(context, mistwood_intermediate));
    }

    private static DensityFunction MistwoodIslands(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction islands = DensityFunctions.add(

                //Produces the baseline height
                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(SBDimensions.CONTINENTALNESS)))
                        .addPoint(0.4F, -2, 6)
                        .addPoint(0.7F, -0.4F, 0)
                        .addPoint(0.96F, 0, 0.5F)
                        .addPoint(1, 0.1F, 0.1F)
                        .addPoint(1.2F, 0.12F, 0)
                        .build()),

                //Uses the Breach Erosion density function to cut channels through mistwood areas
                DensityFunctions.mul(
                        //Only present in mistwood areas
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(SBDimensions.CONTINENTALNESS)))
                                .addPoint(0.9F, 0, 0)
                                .addPoint(1, 1, 0)
                                .build()),
                        //The channels themselves
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.noise(noises.getOrThrow(MISTWOOD_ISLANDS_NOISE), 1, 0)
                                )))
                                .addPoint(-1, 0.3F, 0)
                                .addPoint(-0.5F, 0, -0.5F)
                                .addPoint(0, -0.3F, 0)
                                .addPoint(0.5F, 0, 0.5F)
                                .addPoint(1, 0.3F, 0)
                                .build())
                )
        );
        return DensityFunctions.cache2d(islands);
    }

    //Mistwood terrain adds up to 0.07 and subtracts up to 0.05, depending on mistwood noise.
    //It gets more intense the higher the Mistwood Islands value is.
    private static DensityFunction MistwoodTerrain(BootstrapContext<DensityFunction> context, Holder<DensityFunction> islands) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction roughness = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                DensityFunctions.noise(noises.getOrThrow(MISTWOOD_TERRAIN_NOISE), 1, 0)
                )))
                        .addPoint(-1, -0.6F, 0.5F)
                        .addPoint(1, 0.5F, 0.5F)
                .build());

        //Only present when islands is positive and continentalness is over 0.96
        DensityFunction multiplier = DensityFunctions.mul(
                DensityFunctions.max(
                        DensityFunctions.constant(0),
                        //Only present when islands is positive
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(islands))
                                .addPoint(-0.3F, 0, 0.6F)
                                .addPoint(0, 0.2F, 2)
                                .addPoint(0.3F, 1, 0)
                            .build())
                ),
                //Only present when continentalness is over 0.99
                DensityFunctions.max(
                        DensityFunctions.constant(0),
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(SBDimensions.CONTINENTALNESS)))
                                .addPoint(0.9F, 0, 0)
                                .addPoint(0.96F, 0.2F, 10)
                                .addPoint(1, 1, 0)
                                .build())
                )
        );


        return DensityFunctions.cache2d(DensityFunctions.mul(roughness, multiplier));
    }

    private static DensityFunction MistwoodChannels(BootstrapContext<DensityFunction> context, Holder<DensityFunction> mistwood_islands) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction roughness = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                        DensityFunctions.noise(noises.getOrThrow(Strands.OCEAN_ROUGHNESS_NOISE), 1, 0)
                )))
                .addPoint(-1, -0.08F, 0F)
                .addPoint(1, 0.08F, 0F)
                .build());

        //Only present when islands is negative and continentalness is over 0.99
        DensityFunction multiplier = DensityFunctions.mul(
                //Only present when islands is positive
                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(mistwood_islands))
                        .addPoint(-0.2F, 1, 0)
                        .addPoint(-0.02F, 0, 0)
                        .build()),
                //Only present when continentalness is over 0.99
                DensityFunctions.max(
                        DensityFunctions.constant(0),
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(SBDimensions.CONTINENTALNESS)))
                                .addPoint(0.9F, 0, 0)
                                .addPoint(0.96F, 0.2F, 10)
                                .addPoint(1, 1, 0)
                                .build())
                )
        );

        return DensityFunctions.cache2d(DensityFunctions.mul(roughness, multiplier));
    }

    //Mistwood Intermediate does basically nothing but add the islands and terrain together.
    private static DensityFunction MistwoodIntermediate(Holder<DensityFunction> mistwood_islands, Holder<DensityFunction> mistwood_terrain, Holder<DensityFunction> mistwood_channels) {
        return DensityFunctions.cache2d(DensityUtil.applyAll(
                DensityFunctions::add,
                new DensityFunctions.HolderHolder(mistwood_islands),
                new DensityFunctions.HolderHolder(mistwood_terrain),
                new DensityFunctions.HolderHolder(mistwood_channels)
        ));
    }

        //Final mistwood density adds the islands to the terrain, then splines by Y to get actual terrain height
    private static DensityFunction MistwoodFinal(BootstrapContext<DensityFunction> context, Holder<DensityFunction> mistwood_intermediate) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction mistwood_final = DensityFunctions.add(
                new DensityFunctions.HolderHolder(mistwood_intermediate),
                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(NoiseRouterData.Y)))
                        //Identical to strand stuff below sea level
                        .addPoint(33, 1, -0.04F)
                        .addPoint(51, 0.45F, -0.04F)
                        .addPoint(58, 0.15F, -0.03F)
                        .addPoint(61, 0.05F, -0.025F)
                        .addPoint(63, 0, -0.05F)

                        //Different scale above sea level

                        .addPoint(66, -0.2F, -0.05F)
                        .addPoint(70, -0.5F, -0.07F)

                        .addPoint(75, -0.8F, -0.1F)
                        //Never goes above 78
                        .addPoint(78, -1.4F, -0.5F)


                        .build())
        );

        return DensityFunctions.cacheOnce(mistwood_final);
    }

}
