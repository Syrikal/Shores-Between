package com.syric.shores_between.worldgen.dimension.generation_formulae;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static com.syric.shores_between.ShoresBetween.MODID;
import static com.syric.shores_between.registry.SBDimensions.CONTINENTALNESS;

public class RockFields {

    public static final ResourceKey<NormalNoise.NoiseParameters> ROCK_FIELD_PATCHES_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "rock_field_patches"));
    public static final ResourceKey<NormalNoise.NoiseParameters> ROCK_GULLIES_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "rock_gullies"));
    public static final ResourceKey<NormalNoise.NoiseParameters> ROCK_GULLIES_FUZZIER_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "rock_gullies_fuzzier"));

    public static final ResourceKey<DensityFunction> ROCK_FIELDS_TEXTURE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "rock_fields_texture"));
    public static final ResourceKey<DensityFunction> ROCK_FIELDS_PRESENCE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "rock_fields_presence"));
    public static final ResourceKey<DensityFunction> ROCK_FIELDS_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "rock_fields_final"));

    public static void bootstrapRockFieldsNoise(BootstapContext<NormalNoise.NoiseParameters> context) {
        //Rock Field Patches Noise is used to decide where to put small rock fields outside the actual Rock Fields biome.
        context.register(ROCK_FIELD_PATCHES_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(1, 2, 2, 1)));
        //Rock Gullies is used to place smooth gullies in rock fields.
        context.register(ROCK_GULLIES_NOISE, new NormalNoise.NoiseParameters(-5, 3));
        //Rock Gullies Fuzzier is used to place fuzzier, more jagged gullies in rock fields.
        context.register(ROCK_GULLIES_FUZZIER_NOISE, new NormalNoise.NoiseParameters(-5, 3, 0, 0.5, 1.2, 1));
    }

    public static void bootstrapRockFieldsDensity(BootstapContext<DensityFunction> context, List<DensityFunction> biomeDensityFunctions) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        Holder<DensityFunction> major_gullies_alpha = Holder.direct(DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(DensityFunctions.constant(1000), DensityFunctions.zero(), -0.67, noises.getOrThrow(ROCK_GULLIES_FUZZIER_NOISE))));
        Holder<DensityFunction> major_gullies_beta = Holder.direct(DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(DensityFunctions.zero(), DensityFunctions.zero(), 1, noises.getOrThrow(ROCK_GULLIES_FUZZIER_NOISE))));

        Holder<DensityFunction> rock_fields_texture = context.register(ROCK_FIELDS_TEXTURE, RockFieldsTexture(context, major_gullies_alpha, major_gullies_beta));

        Holder<DensityFunction> rock_fields_presence = context.register(ROCK_FIELDS_PRESENCE, RockFieldsPresence(context, biomeDensityFunctions));

        Holder<DensityFunction> rock_fields_final = context.register(ROCK_FIELDS_FINAL, RockFieldsFinal(context, rock_fields_texture, rock_fields_presence));
    }

    private static DensityFunction RockFieldsTexture(BootstapContext<DensityFunction> context, Holder<DensityFunction> major_gullies_alpha, Holder<DensityFunction> major_gullies_beta) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        //Add together the base terrain and the gullies
        DensityFunction texture = DensityFunctions.add(

                /* Base Rock Fields terrain is the sum of six things:
                * - a baseline (0.4)
                * - a large continentalness for roughness
                * - a small continentalness for more roughness
                * - Major Gullies Alpha absolute value to make rocks taller in the middle
                * - Same as above for Major Gullies Beta
                * - A rangeChoice to make rocks in Major Gullies Alpha negative areas shorter than ones in positive areas
                *
                * Each of these is multiplied by a coefficient.
                *
                * The density is centered around 0.4. It can go up to about .61 and down to about .27.
                * The average rock's density in the middle is between 0.42 (low area) and 0.53 (high area).
                * At the edge, it's 0.35 to 0.45.
                *
                * Gullies can cut by up to 2.
                */
                DensityUtil.applyAll(DensityFunctions::add,

                        //Baseline
                        DensityFunctions.constant(0.4),

                        //Large continentalness
                        DensityFunctions.mul(
                                DensityFunctions.constant(0.05), //Coefficient
                                DensityFunctions.shiftedNoise2d(DensityFunctions.zero(), DensityFunctions.zero(), 1, noises.getOrThrow(Noises.CONTINENTALNESS))),

                        //Small continentalness
                        DensityFunctions.mul(
                                DensityFunctions.constant(0.03), //Coefficient
                                DensityFunctions.shiftedNoise2d(DensityFunctions.zero(), DensityFunctions.zero(), 4, noises.getOrThrow(Noises.CONTINENTALNESS))),

                        //MGA absval
                        DensityFunctions.mul(
                                DensityFunctions.constant(0.04), //Coefficient
                                new DensityFunctions.HolderHolder(major_gullies_alpha)
                                        .squeeze().abs()),

                        //MGB absval
                        DensityFunctions.mul(
                                DensityFunctions.constant(0.04), //Coefficient
                                new DensityFunctions.HolderHolder(major_gullies_beta)
                                        .squeeze().abs()),

                        //Major Gullies Alpha range choice
                        //Adds or subtracts 0.05 depending on whether it's in a positive or negative area
                        DensityFunctions.rangeChoice(
                                new DensityFunctions.HolderHolder(major_gullies_alpha),
                                0,
                                10,
                                DensityFunctions.constant(0.05),
                                DensityFunctions.constant(-0.05))
                ).clamp(0.15, 0.65),


                /*Gullies reduce the terrain's height. There are two major gully systems (alpha and beta) using the Fuzzier noise,
                * and several minor gully systems of varying size and intensity using the Smoother noise.
                * Each gully uses a spline to produce a sharp cut at the 0 mark.
                *
                * Remember that each 0.1 of density is *about* one and a half blocks.
                * -1 cuts all the way through the field to the substrate, but lower values can be used to sharpen the cut.*/
                DensityUtil.applyAll(DensityFunctions::min,

                        //Major gullies alpha
                        //MGA gullies aren't any bigger than MGB ones, but their overall pattern is larger.
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(major_gullies_alpha))
                                .addPoint(-0.33F, 0, 0)
                                .addPoint(-0.24F, -0.05F,-0.5F)
                                .addPoint(-0.14F,-0.2F, -1)
                                .addPoint(0,-1, 0)
                                .addPoint(0.14F,-0.2F, 1)
                                .addPoint(0.24F, -0.05F, 0.5F)
                                .addPoint(0.33F, 0, 0)
                                .build()),

                        //Major gullies beta (smaller pattern)
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(major_gullies_beta))
                                .addPoint(-0.5F, 0, 0)
                                .addPoint(-0.35F,-0.05F, -0.5F)
                                .addPoint(-0.2F, -0.2F,-1F)
                                .addPoint(0,-1, 0)
                                .addPoint(0.2F,-0.2F, 1)
                                .addPoint(0.35F,-0.05F, 0.5F)
                                .addPoint(0.5F,0,0)
                                .build()),

                        //Minor gullies 1
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(
                                        DensityFunctions.zero(), //X shift
                                        DensityFunctions.constant(-10000), //Z shift
                                        -0.2, //Scale (smaller is bigger)
                                        noises.getOrThrow(ROCK_GULLIES_NOISE)))))) //Noise
                                .addPoint(-0.03F,0,0)
                                .addPoint(0,-0.25F, 0)
                                .addPoint(0.03F,0,0)
                                .build()),

                        //Minor gullies 2
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(
                                        DensityFunctions.constant(-10000), //X shift
                                        DensityFunctions.zero(), //Z shift
                                        0.3, //Scale (smaller is bigger)
                                        noises.getOrThrow(ROCK_GULLIES_NOISE)))))) //Noise
                                .addPoint(-0.04F, 0, 0)
                                .addPoint(0, -0.2F, 0)
                                .addPoint(0.04F, 0, 0)
                                .build()),

                        //Minor gullies 3
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(
                                        DensityFunctions.zero(), //X shift
                                        DensityFunctions.constant(10000), //Z shift
                                        -0.4, //Scale (smaller is bigger)
                                        noises.getOrThrow(ROCK_GULLIES_NOISE)))))) //Noise
                                .addPoint(-0.03F, 0, 0)
                                .addPoint(0, -0.15F,0)
                                .addPoint(0.03F, 0, 0)
                                .build()),

                        //Minor gullies 4
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(
                                        DensityFunctions.constant(10000), //X shift
                                        DensityFunctions.zero(), //Z shift
                                        0.6, //Scale (smaller is bigger)
                                        noises.getOrThrow(ROCK_GULLIES_FUZZIER_NOISE)))))) //Noise
                                .addPoint(-0.1F, 0, 0)
                                .addPoint(0, -0.1F, 0)
                                .addPoint(0.1F, 0, 0)
                                .build()),

                        //Minor gullies 5
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                        DensityFunctions.cache2d(DensityFunctions.shiftedNoise2d(
                                                DensityFunctions.zero(), //X shift
                                                DensityFunctions.zero(), //Z shift
                                                -1, //Scale (smaller is bigger)
                                                noises.getOrThrow(ROCK_GULLIES_NOISE)))))) //Noise
                                .addPoint(-0.1F, 0, 0)
                                .addPoint(0, -0.07F, 0)
                                .addPoint(0.1F, 0, 0)
                                .build())
                )
        );
        return texture;
    }

    private static DensityFunction RockFieldsPresence(BootstapContext<DensityFunction> context, List<DensityFunction> biomeDensityFunctions) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        DensityFunction rockiness = biomeDensityFunctions.get(0);
        DensityFunction vitality = biomeDensityFunctions.get(1);

        /* Rock Fields Presence is for deciding where rock fields should be generated.
        * Rock fields should generate:
        * - In the Rock Fields biome at high Rockiness, low Vitality; ('biomes')
        * - Elsewhere in small patches determined by the ROCK_FIELD_PATCHES_NOISE, more commonly when Rockiness is higher; ('patches')
        *       - but not where Base Strand Terrain is below about y=56. ('validPatchAreas')
        * - Not where Breach Continentalness is less than -0.1 or more than 0.9 ('validAreas')
        *
        * Presence is max(biomes, patches) x validAreas.
        *
        * It combines these factors to produce a continuous value between 0 and 1, where 0 is no rock fields and 1 is yes rock fields.
        * Then, it splines this to get a continuous number from -5 (no rock fields) to 0 (yes rock fields).
        * This is added to the RockFieldsTexture, leaving it unchanged in rock fields areas and below -4 in non-rock-fields areas.
        * */

        //Biomes is 1 when rockiness is > 0.7 and vitality is < 0.7.
        DensityFunction biomes = DensityFunctions.min(
                DensityFunctions.add(DensityFunctions.mul(rockiness, DensityFunctions.constant(5)), DensityFunctions.constant(-2.5)),
                DensityFunctions.add(DensityFunctions.mul(vitality, DensityFunctions.constant(-5)), DensityFunctions.constant(-2.5)))
                .clamp(0,1);
        //Patches is 1 when 0.75*rockiness + patches_noise > 1.2
        /*It subtracts up to 1 from that value as the strand terrain drops.
        * Strand terrain above sea level: -0
        * Strand terrain of 60: -0.1
        * Strand terrain of 56: -0.4
        * 52: -1
         */
        DensityFunction patches = DensityUtil.applyAll(DensityFunctions::add,
                DensityFunctions.constant(-0.2),
                DensityFunctions.mul(rockiness, DensityFunctions.constant(0.75)),
                DensityFunctions.noise(noises.getOrThrow(ROCK_FIELD_PATCHES_NOISE), 1, 0)
                //TODO: add Base Strand Terrain requirement!
//                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(functions.getOrThrow(Strands.BASE_STRAND_TERRAIN))))
//                                .addPoint(y=63, 0, 0)
//                                .addPoint(y=60, -0.1, -something)
//                                .addPoint(y=56, -0.4, -something)
//                                .addPoint(Y=52, -1, -something)
//                                .build())
                        )
                .clamp(0, 1);

        // ValidAreas is 1 when Breach Continentalness is between -0.1 and 0.9 and falls off pretty sharply outside there.
        DensityFunction validAreas = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(CONTINENTALNESS)))
                        .addPoint(-0.3F, 0, 0)
                        .addPoint(-0.1F, 1, 0)
                        .addPoint(0.9F, 1, 0)
                        .addPoint(1.1F, 0, 0)
                        .build());

        DensityFunction presence = DensityFunctions.mul(DensityFunctions.max(biomes, patches), validAreas);
        DensityFunction splinedPresence = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(presence)))
                .addPoint(0, -5, 0)
                .addPoint(0.7F,-1,5F)
                .addPoint(1,0,0)
                .build());

        return splinedPresence;
    }

    private static DensityFunction RockFieldsFinal(BootstapContext<DensityFunction> context, Holder<DensityFunction> rock_fields_texture, Holder<DensityFunction> rock_fields_presence) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        /*Rock Fields Final is a 3D density function that decides which blocks to place as part of rock field generation.
        * It is the sum of three things:
        * 1) RockFieldsTexture, for the baseline
        * 2) RockFieldsPresence, to remove them in unwanted areas
        * 3) A spline based on the Y value, to put the terrain at the appropriate height based on its 2D density.*/

        DensityFunction rock_fields_final = DensityUtil.applyAll(DensityFunctions::add,
                new DensityFunctions.HolderHolder(rock_fields_texture),
                new DensityFunctions.HolderHolder(rock_fields_presence),
                DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(functions.getOrThrow(NoiseRouterData.Y)))
                        .addPoint(30,0.9F,-0.01F)
                        .addPoint(40,0.8F,-0.02F)
                        .addPoint(50,0.6F,-0.04F)
                        .addPoint(60,0.2F,-0.07F)
                        .addPoint(63,0,-0.075F)
                        .addPoint(65,-0.15F,-0.1F)
                        .addPoint(69,-0.5F,-0.25F)
                        .addPoint(71,-1,-0.5F)
                        .addPoint(73,-2,-1)
                        .build())
        );

        return rock_fields_final;
    }

}
