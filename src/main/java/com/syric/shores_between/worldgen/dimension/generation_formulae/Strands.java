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

import static com.syric.shores_between.ShoresBetween.MODID;
import static com.syric.shores_between.registry.SBDimensions.CONTINENTALNESS;

public class Strands {

    public static final ResourceKey<NormalNoise.NoiseParameters> STRANDS_ROUGHNESS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "strands_roughness"));
    public static final ResourceKey<NormalNoise.NoiseParameters> OCEAN_ROUGHNESS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "ocean_roughness"));

    public static final ResourceKey<DensityFunction> BASE_STRAND_DENSITY = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "base_strand_density"));
    public static final ResourceKey<DensityFunction> STRANDS_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "strands_final"));

    public static void bootstrapStrandDensity(BootstrapContext<DensityFunction> context) {

        Holder<DensityFunction> strands_density = context.register(BASE_STRAND_DENSITY, StrandsDensity(context));
        Holder<DensityFunction> strands_final = context.register(STRANDS_FINAL, StrandsFinal(context, strands_density));

    }

    public static void bootstrapStrandNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        //Strands Roughness Noise is applied to alter the height of strand terrain.
        context.register(STRANDS_ROUGHNESS_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(2, 2, 1, 2, 1)));
        //Ocean Roughness Noise is applied to alter the height of oceanic terrain.
        context.register(OCEAN_ROUGHNESS_NOISE, new NormalNoise.NoiseParameters(-6, DoubleList.of(2,2, 2, 2, 1, 1, 1, 1, 1)));
   }

    private static DensityFunction StrandsDensity(BootstrapContext<DensityFunction> context) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);


        /*
        * Base Strand Density is a 2D density function that calculates beach height.
        * It consists of three overlapping splined noise functions combined with smoothMax.
        * Two roughness functions are then applied, one for ocean and one for land.
        * */

        //Strands Major is a spline of the breach_continentalness noise.
        //It has beaches at 0 and 0.37.
        Holder<DensityFunction> strands_major = Holder.direct(DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(
                functions.getOrThrow(CONTINENTALNESS)))
                .addPoint(-0.5F, -0.8F, 0.5F)
                .addPoint(-0.2F,-0.4F,2)
                .addPoint(-0.03F,0,0.7F)
                .addPoint(0,0.02F,0)
                .addPoint(0.03F,0,-0.7F)
                .addPoint(0.15F,-0.2F,0)
                .addPoint(0.34F,0,1)
                .addPoint(0.37F,0.03F,0)
                .addPoint(0.4F, 0, 1)
                .addPoint(0.5F,-0.3F,-0.5F)
                .build()));

        //Strands Minor is a spline of a much smaller version of the breach_continentalness noise.
        //The rangeChoice is so that it only exists in positive continental areas.
        Holder<DensityFunction> strands_minor = Holder.direct(
                DensityFunctions.rangeChoice(
                        new DensityFunctions.HolderHolder(functions.getOrThrow(CONTINENTALNESS)),
                        0, //Min value
                        5, //Max value

                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.shiftedNoise2d(
                                        DensityFunctions.constant(5000), //X shift
                                        DensityFunctions.zero(), //Z shift
                                        6, //Scale
                                        noises.getOrThrow(SBDimensions.BREACH_CONTINENTAL_NOISE))))) //Noise
                        .addPoint(-0.4F, -0.3F, 0.5F)
                        .addPoint(-0.08F, 0, 0.25F)
                        .addPoint(0, 0.015F, 0)
                        .addPoint(0.08F, 0, -0.25F)
                        .addPoint(0.4F, -0.3F, -0.5F)
                        .build()),

                        DensityFunctions.constant(-1000)

                )
        );

        //Sandbars is like Strands Minor. They're smaller and typically submerged.
        Holder<DensityFunction> sandbars = Holder.direct(
                DensityFunctions.rangeChoice(
                        new DensityFunctions.HolderHolder(functions.getOrThrow(CONTINENTALNESS)),
                        0, //Min value
                        5, //Max value

                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                        DensityFunctions.shiftedNoise2d(
                                                DensityFunctions.constant(0), //X shift
                                                DensityFunctions.constant(5000), //Z shift
                                                6, //Scale
                                                noises.getOrThrow(SBDimensions.BREACH_CONTINENTAL_NOISE))))) //Noise
                                .addPoint(-0.25F, -0.2F, 0.5F)
                                .addPoint(-0.05F, -0.02F, 0.3F)
                                .addPoint(0, -0.005F, 0)
                                .addPoint(0.05F, -0.02F, -0.3F)
                                .addPoint(0.25F, -0.2F, -0.5F)
                                .build()),

                        DensityFunctions.constant(-1000)

                )
        );

        //Strands Roughness is a thin, wide layer of roughness applied to continental areas.
        //It consists of a coefficient, the roughness noise, and a spline to put it only on continents.
        Holder<DensityFunction> strands_roughness = Holder.direct(
                DensityUtil.applyAll(DensityFunctions::mul,
                        DensityFunctions.constant(0.015), //Coefficient (it's very thin)
                        DensityFunctions.noise(noises.getOrThrow(STRANDS_ROUGHNESS_NOISE), 1, 0), //Roughness
                        //Spline to put it only in continental areas
                        DensityFunctions.spline(CubicSpline.builder(
                                new DensityFunctions.Spline.Coordinate(functions.getOrThrow(CONTINENTALNESS)))
                                .addPoint(-0.3F,0,0)
                                .addPoint(0,1,0)
                                .build())
                )
        );

        //Ocean Roughness is a thicker, rougher layer of roughness applied to oceanic areas.
        //It consists of a coefficient, the roughness noise, and a spline to put it only on oceans.
        Holder<DensityFunction> ocean_roughness = Holder.direct(
                DensityUtil.applyAll(DensityFunctions::mul,
                        DensityFunctions.constant(0.1), //Coefficient
                        DensityFunctions.noise(noises.getOrThrow(OCEAN_ROUGHNESS_NOISE), 1, 0), //Roughness
                        //Spline to put it only in continental areas
                        DensityFunctions.spline(CubicSpline.builder(
                                new DensityFunctions.Spline.Coordinate(functions.getOrThrow(CONTINENTALNESS)))
                                .addPoint(-0.7F, 1, 0)
                                .addPoint(-0.3F, 0.35F, -1)
                                .addPoint(-0.05F,0,0)
                                .build())
                )
        );

        //The final density is the (smooth) max of the three strand systems, plus the two roughness functions.
        DensityFunction strands_density = DensityUtil.applyAll(DensityFunctions::add,
                DensityUtil.applyAll(DensityFunctions::max,
                        new DensityFunctions.HolderHolder(strands_major),
                        new DensityFunctions.HolderHolder(strands_minor),
                        new DensityFunctions.HolderHolder(sandbars)),
                new DensityFunctions.HolderHolder(strands_roughness),
                new DensityFunctions.HolderHolder(ocean_roughness)
                );

        return strands_density;
    }

    private static DensityFunction StrandsFinal(BootstrapContext<DensityFunction> context, Holder<DensityFunction> strandsDensity) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        /* Strands Final is a 3D density function that decides which blocks to place as part of strand generation.
         * It is the sum of two things:
         * 1) StrandsDensity, for the baseline
         * 2) A spline based on the Y value, to put the terrain at the appropriate height based on its 2D density.
         * */

        DensityFunction strandsFinal = DensityFunctions.add(
                new DensityFunctions.HolderHolder(strandsDensity),
                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(NoiseRouterData.Y)))
                        .addPoint(33, 1, -0.04F)
                        .addPoint(51, 0.45F, -0.04F)
                        .addPoint(58, 0.15F, -0.03F)
                        .addPoint(61, 0.05F, -0.025F)
                        .addPoint(63, 0, -0.01F)
                        .addPoint(65, -0.02F, -0.01F)
                        .addPoint(67, -0.04F, -0.02F)
                        .addPoint(69, -0.07F, -0.05F)
                        .addPoint(70, -0.15F, -0.15F)
                        .addPoint(71,-0.3F,-0.3F)
                        .build())
        );

        return strandsFinal;

    }

}
