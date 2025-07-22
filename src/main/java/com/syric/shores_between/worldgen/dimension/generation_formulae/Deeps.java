package com.syric.shores_between.worldgen.dimension.generation_formulae;

import com.syric.shores_between.registry.SBDensityFunctions;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.client.gui.screens.DemoIntroScreen;
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

public class Deeps {

    public static final double period = 1500;
//    public static final double period = 500;
    public static final double period_multiplier = 2 * Math.PI / period;

    public static final ResourceKey<NormalNoise.NoiseParameters> DEEPS_CAVES_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "deeps_caves_noise"));

    public static final ResourceKey<DensityFunction> DEEPS_DIAGONALS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_diagonals"));
    public static final ResourceKey<DensityFunction> DEEPS_BASE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_base"));
    public static final ResourceKey<DensityFunction> DEEPS_CAVES = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_caves"));
    public static final ResourceKey<DensityFunction> DEEPS_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_final"));


    public static void bootstrapDeepsNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        context.register(DEEPS_CAVES_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(1, 1, 1)));
    }

    public static void bootstrapDeepsDensity(BootstrapContext<DensityFunction> context) {

        Holder<DensityFunction> deeps_diagonals = context.register(DEEPS_DIAGONALS, DeepsDiagonals(context));
        Holder<DensityFunction> deeps_base = context.register(DEEPS_BASE, DeepsBase(context, deeps_diagonals));
        Holder<DensityFunction> deeps_caves = context.register(DEEPS_CAVES, DeepsCaves(context));
        Holder<DensityFunction> deeps_final = context.register(DEEPS_FINAL, DeepsFinal(context, deeps_base, deeps_caves));
    }

    /**
     * The Deeps consists of six things.
     * 1) Deeps Base, which produces a perfectly tiling pattern.
     * 2) Deeps Caves, which modifies those and adds smaller irregular caves around the big ones.
     * 3) Deeps Diagonal Tunnels, which produces diagonal tunnels between the big caves. Only negative - only removes, never adds.
     * 4) Deeps Circular Tunnels, which produces tunnels in rings around the big caves. Only negative - only removes, never adds.
     * 5) Deeps Pillars, which produces columns in patches in the larger caves.
     * 6) Deeps Lava Pits, which produces sunken pits in certain areas.
     *
     * The Deeps consists of a grid of large caves. Each one is approximately 400 blocks across and 100 blocks tall.
     * The grid is 1500 blocks on a side.
     * -128: bottom of world
     * -102: bottom of 'lava' lakes
     * -94: surface of 'lava'
     * -90: floor of most caverns
     * -85: widest point
     * 10: ceiling of caverns
     * */


    //A function that is 1 on the diagonal lines connecting caverns and declines to as low as -1 elsewhere.
    //Desmos version: g\left(x,y\right)\ =\ \min\left(\cos\left(\left(x-y\right)\cdot\frac{2\pi}{a}\right),-\cos\left(\left(x+y\right)\cdot\frac{2\pi}{a}\right)\right)
    private static DensityFunction DeepsDiagonals(BootstrapContext<DensityFunction> context) {
        return DensityFunctions.flatCache(
                DensityFunctions.min(
                        DensityFunctions.mul(
                                DensityFunctions.constant(-1),
                                new SBDensityFunctions.Cosine(
                                        DensityFunctions.add(
                                                new SBDensityFunctions.GetX(),
                                                new SBDensityFunctions.GetZ()
                                        ),
                                        period
                                )
                        ),
                        new SBDensityFunctions.Cosine(
                                DensityFunctions.add(
                                        new SBDensityFunctions.GetX(),
                                        DensityFunctions.mul(
                                                DensityFunctions.constant(-1),
                                                new SBDensityFunctions.GetZ()
                                        )
                                ),
                                period
                        )
                )
        );
    }

    //Deeps Base produces a function that varies from -1 in the center of the big caverns to 1 between them.
    //The diagonals are protected by adding in a reduced form of deeps_diagonals.
    //The whole thing is increased by 0.6 to end up with the 0 mark being at the desired (loose) edge of the caverns
    private static DensityFunction DeepsBase(BootstrapContext<DensityFunction> context, Holder<DensityFunction> deeps_diagonals) {
        return DensityFunctions.flatCache(
                DensityFunctions.add(
                        //Minimum of two things
                        DensityFunctions.min(
                                //Product of sinX and sinZ, for the general structure
                                DensityFunctions.mul(
                                        new SBDensityFunctions.Sine(new SBDensityFunctions.GetX(), period),
                                        new SBDensityFunctions.Sine(new SBDensityFunctions.GetZ(), period)
                                ),

                                //DeepsDiagonals + 0.6, to ensure caverns can form easily on the diagonals
                                DensityFunctions.add(
                                        DensityFunctions.constant(0.6),
                                        new DensityFunctions.HolderHolder(deeps_diagonals)
                                )
                        ),
                        //Adding 0.6 to put the 0 mark at the desired edge of the caverns
                        DensityFunctions.constant(0.6)
                )
        );
    }

    //DeepsCaves produces a patchwork of caves.
    //It can add up to 0.2 (and therefore can't fill in the center of a big cave)
    //and it can subtract up to 1.2 (which puts a limit on how far its caves can be from the big ones)
    private static DensityFunction DeepsCaves(BootstrapContext<DensityFunction> context) {
        double maximum = 0.2;
        double minimum = -1.2;

        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        DensityFunction base = DensityFunctions.add(
                DensityFunctions.constant(0.2),
                DensityUtil.applyAll(DensityFunctions::min,
                        DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(
                                        DensityFunctions.noise(noises.getOrThrow(DEEPS_CAVES_NOISE), 1, 1.3)
                                )))
                                .addPoint(-0.6F, -0.4F, 1)
                                .addPoint(0, 0.2F, 0)
                                .addPoint(0.6F, -0.4F, -1)
                                .build()),
                        SBDensityFunctions.shiftedNoise(
                                10000,
                                0,
                                0,
                                1.5,
                                2,
                                noises.getOrThrow(DEEPS_CAVES_NOISE)
                        ),
                        SBDensityFunctions.shiftedNoise(
                                -10000,
                                0,
                                10000,
                                -1.5,
                                2,
                                noises.getOrThrow(DEEPS_CAVES_NOISE)
                        )
                )
        );

        //Positive values get flattened, negative ones intensified
        DensityFunction multiplier = DensityFunctions.rangeChoice(base, -100, 0,
                DensityFunctions.mul(base, DensityFunctions.constant(1.6)),
                DensityFunctions.mul(base, DensityFunctions.constant(0.5)));

        return DensityFunctions.cacheOnce(multiplier.clamp(minimum, maximum));
    }


    private static DensityFunction DeepsFinal(BootstrapContext<DensityFunction> context, Holder<DensityFunction> deeps_base, Holder<DensityFunction> deeps_caves) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        DensityFunction y = DensityFunctions.yClampedGradient(-128,256, -128, 256);

        DensityFunction y_plus_85_squared = DensityFunctions.mul(
                DensityFunctions.add(DensityFunctions.constant(85), y),
                DensityFunctions.add(DensityFunctions.constant(85), y)
        );

        //Height Factor produces flat floors from ~-95 to -85, and tall, rounded ceilings.
        DensityFunction height_factor = DensityFunctions.rangeChoice(
                y,
                -200,
                -85,
                DensityFunctions.mul(
                        DensityFunctions.constant(0.06),
                        y_plus_85_squared
                ),
                DensityFunctions.mul(
                        DensityFunctions.constant(0.000167),
                        y_plus_85_squared
                )
        );

        DensityFunction with_height = DensityUtil.applyAll(DensityFunctions::add,
                new DensityFunctions.HolderHolder(deeps_base),
                new DensityFunctions.HolderHolder(deeps_caves),
                height_factor
        );

        return DensityFunctions.cacheOnce(with_height);
    }


}
