package com.syric.shores_between.worldgen.dimension.generation_formulae;

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
import static com.syric.shores_between.registry.SBDimensions.CONTINENTALNESS;

public class DrownedForest {

    public static final ResourceKey<NormalNoise.NoiseParameters> DROWNED_FOREST_PLATEAUS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "drowned_forest_plateaus"));
    public static final ResourceKey<NormalNoise.NoiseParameters> DROWNED_FOREST_SINKING_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "drowned_forest_sinking"));

    public static final ResourceKey<DensityFunction> DROWNED_FOREST_PLATEAUS_TEXTURE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "drowned_forest_plateaus_texture"));
    public static final ResourceKey<DensityFunction> DROWNED_FOREST_PRESENCE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "drowned_forest_presence"));
    public static final ResourceKey<DensityFunction> DROWNED_FOREST_PLATEAUS_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "drowned_forest_plateaus_final"));
    public static final ResourceKey<DensityFunction> DROWNED_FOREST_SINKING = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "drowned_forest_sinking"));

    public static void bootstrapDrownedForestNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        //Drowned Forest Plateaus Noise is used to decide where the gaps between plateaus are.
        context.register(DROWNED_FOREST_PLATEAUS_NOISE, new NormalNoise.NoiseParameters(-6, DoubleList.of(3, 2, 1)));
        //Drowned Forest Sinking Noise is a roughness function that is subtracted from the strand terrain.
        context.register(DROWNED_FOREST_SINKING_NOISE, new NormalNoise.NoiseParameters(-5, DoubleList.of(2, 1, 1)));
    }

    public static void bootstrapDrownedForestDensity(BootstrapContext<DensityFunction> context, List<DensityFunction> biomeDensityFunctions) {

        Holder<DensityFunction> drowned_forest_plateaus_texture = context.register(DROWNED_FOREST_PLATEAUS_TEXTURE, DrownedForestPlateauTexture(context));

        Holder<DensityFunction> drowned_forest_presence = context.register(DROWNED_FOREST_PRESENCE, DrownedForestPresence(context, biomeDensityFunctions));

        Holder<DensityFunction> drowned_forest_final = context.register(DROWNED_FOREST_PLATEAUS_FINAL, DrownedForestPlateausFinal(context, drowned_forest_presence, drowned_forest_plateaus_texture));

        Holder<DensityFunction> drowned_forest_sinking = context.register(DROWNED_FOREST_SINKING, DrownedForestSinking(context, drowned_forest_presence));

    }


    private static DensityFunction DrownedForestPlateauTexture(BootstrapContext<DensityFunction> context) {

        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        Holder<DensityFunction> plateaus_noise = Holder.direct(DensityFunctions.noise(noises.getOrThrow(DROWNED_FOREST_PLATEAUS_NOISE)));

        //Drowned Forest Plateau Texture simply produces plateaus.
        DensityFunction texture = DensityFunctions.spline(
                CubicSpline.builder(new DensityFunctions.Spline.Coordinate(plateaus_noise))
                        .addPoint(-1, 1, 0)
                        .addPoint(0, -1, 0)
                        .addPoint(1, 1, 0)
                        .build()
        );
        return DensityFunctions.flatCache(texture);
    }

    private static DensityFunction DrownedForestPresence(BootstrapContext<DensityFunction> context, List<DensityFunction> biomeDensityFunctions) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        DensityFunction rockiness = biomeDensityFunctions.get(0);
        DensityFunction vitality = biomeDensityFunctions.get(1);

        /* Drowned Forest Presence decides where the Drowned Forest biome is.
        * i.e., Rockiness < -0.2, Vitality < -0.6, Continentalness between -0.1 and 0.85
        * It is 0 here and -10 elsewhere, with a smooth falloff.
        * Take three splines, one for each of those noises;
        * take the minimum of these,
        * clamp between -10 and 0.
        * */
        DensityFunction presence = DensityUtil.applyAll(DensityFunctions::min,

                        //The rockiness one
//                        DensityFunctions.add(
//                                DensityFunctions.mul(
//                                        DensityFunctions.constant(-20),
//                                        rockiness
//                                ),
//                                DensityFunctions.constant(-4)
//                        ),
                        DensityUtil.linearDF(rockiness, -20, -4),

                        //The vitality one
//                        DensityFunctions.add(
//                                DensityFunctions.mul(
//                                        DensityFunctions.constant(-60),
//                                        vitality
//                                ),
//                                DensityFunctions.constant(-36)
//                        ),
                        DensityUtil.linearDF(vitality, -60, -36),

                        //The continentalness one
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(CONTINENTALNESS)))
                                .addPoint(-0.1F, 0, 50)
                                .addPoint(0.85F, 0, -50)
                                .build())

                        ).clamp(-10, 0);

        return DensityFunctions.flatCache(presence);
    }

    private static DensityFunction DrownedForestPlateausFinal(BootstrapContext<DensityFunction> context, Holder<DensityFunction> drownedForestPresence, Holder<DensityFunction> drownedForestPlateausTexture) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        /* Drowned Forest Plateaus Final is a 3D density function that decides which blocks to place as part of rock field generation.
         * It is the sum of three things:
         * 1) DrownedForestPlateauTexture, for the baseline
         * 2) DrownedForestPresence, to remove them in unwanted areas
         * 3) A spline based on the Y value, to put the terrain at the appropriate height based on its 2D density.*/

        DensityFunction forest_plateaus_final = DensityUtil.applyAll(DensityFunctions::add,
                new DensityFunctions.HolderHolder(drownedForestPlateausTexture),
                new DensityFunctions.HolderHolder(drownedForestPresence),
//                DensityFunctions.spline(CubicSpline.builder(
//                        new DensityFunctions.Spline.Coordinate(functions.getOrThrow(NoiseRouterData.Y)))
//                        .addPoint(58, 0.3F, -0.1F)
//                        .addPoint(60, 0.1F, -0.2F)
//                        .addPoint(62, -1F, -1)
//                        .addPoint(64, -4F, -5)
//                        .build()));
                DensityFunctions.spline(CubicSpline.builder(
                        new DensityFunctions.Spline.Coordinate(functions.getOrThrow(NoiseRouterData.Y)))
                        .addPoint(56, 0.3F, -0.05F)
                        .addPoint(58, 0.1F, -0.2F)
                        .addPoint(60, -1F, -1)
                        .addPoint(62, -4F, -2.5F)
                        .build()));

        return DensityFunctions.cacheOnce(forest_plateaus_final);

    }

    private static DensityFunction DrownedForestSinking(BootstrapContext<DensityFunction> context, Holder<DensityFunction> drownedForestPresence) {
        //Produces a small negative number in Drowned Forest areas to be subtracted from the strand, causing it to sink
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        //PresenceMultiplier is 1 in Drowned Forest and 0 elsewhere, with a smooth interpolation between.
//        DensityFunction presenceMultiplier = DensityFunctions.add(
//                DensityFunctions.mul(
//                        new DensityFunctions.HolderHolder(drownedForestPresence),
//                        DensityFunctions.constant(0.2F)
//                ),
//                DensityFunctions.constant(1)
//        ).clamp(0, 1);
        DensityFunction presenceMultiplier = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                        new DensityFunctions.HolderHolder(drownedForestPresence)
                )))
                .addPoint(-5, 0, 0)
                .addPoint(0, 1, 0)
                .build());


        DensityFunction roughness = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                        DensityFunctions.noise(noises.getOrThrow(DROWNED_FOREST_SINKING_NOISE), 1, 0))))
                .addPoint(-2.5F, -1, 0)
                .addPoint(-1, -0.5F, 1)
                .addPoint(0, 0, 0)
                .addPoint(1, -0.5F, -1)
                .addPoint(2.5F, -1, 0)
                .build());

        //Old version:
//        DensityFunction roughness = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
//                        DensityFunctions.noise(noises.getOrThrow(DROWNED_FOREST_SINKING_NOISE), 1, 0))))
//                .addPoint(-2.5F, -1, 0.1F)
//                .addPoint(-1, -0.5F, 0.5F)
//                .addPoint(1, 0.5F, 0.5F)
//                .addPoint(2.5F, 1, 0.1F)
//                .build());

        //Scale it appropriately
//        DensityFunction sink = DensityFunctions.mul(
//                DensityFunctions.constant(0.05F),
////                DensityFunctions.constant(3),
//                roughness
//        );

        //Make it negative (redundant; modified the spline instead)
//        sink = DensityFunctions.mul(
//                DensityFunctions.constant(-1),
//                sink.abs()
//        );

        //Add a small constant
//        sink = DensityFunctions.add(
//                DensityFunctions.constant(-0.05),
//                sink
//        );

        //Scale it and subtract a small constant
        DensityFunction sink = DensityUtil.linearDF(roughness, 0.05, -0.05);

        //Multiply by presence multiplier and ensure it's negative
        sink = DensityFunctions.mul(presenceMultiplier, sink).clamp(-100, 0);

        return DensityFunctions.flatCache(sink);
    }
}
