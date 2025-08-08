package com.syric.shores_between.worldgen.dimension.generation_formulae;

import com.syric.shores_between.registry.SBDensityFunctions;
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
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static com.syric.shores_between.ShoresBetween.MODID;

public class Deeps {

    public static final double period = 1500;
//    public static final double period = 500;
    public static final double period_multiplier = 2 * Math.PI / period;

    public static final ResourceKey<NormalNoise.NoiseParameters> DEEPS_CAVES_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "deeps_caves_noise"));
    public static final ResourceKey<NormalNoise.NoiseParameters> DEEPS_DIAGONAL_TUNNELS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "deeps_diagonal_tunnels_noise"));
    public static final ResourceKey<NormalNoise.NoiseParameters> DEEPS_LAVA_PITS_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "deeps_lava_pits_noise"));

    public static final ResourceKey<DensityFunction> DEEPS_DIAGONALS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_diagonals"));
    public static final ResourceKey<DensityFunction> DEEPS_GRID = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_grid"));
    public static final ResourceKey<DensityFunction> DEEPS_BASE = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_base"));
    public static final ResourceKey<DensityFunction> DEEPS_CAVES = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_caves"));
    public static final ResourceKey<DensityFunction> DEEPS_DIAGONAL_TUNNELS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_diagonal_tunnels"));
    public static final ResourceKey<DensityFunction> DEEPS_CIRCULAR_TUNNELS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_circular_tunnels"));
    public static final ResourceKey<DensityFunction> DEEPS_PILLARS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_pillars"));
    public static final ResourceKey<DensityFunction> DEEPS_STALACTITES = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_stalactites"));
    public static final ResourceKey<DensityFunction> DEEPS_LAVA_PITS = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_lava_pits"));
    public static final ResourceKey<DensityFunction> DEEPS_HEIGHT_FACTOR = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_height_factor"));
    public static final ResourceKey<DensityFunction> DEEPS_FINAL = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "deeps_final"));


    public static void bootstrapDeepsNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        context.register(DEEPS_CAVES_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(1, 1, 1)));
        context.register(DEEPS_DIAGONAL_TUNNELS_NOISE, new NormalNoise.NoiseParameters(-5, DoubleList.of(1, 0.5)));
        context.register(DEEPS_LAVA_PITS_NOISE, new NormalNoise.NoiseParameters(-6, DoubleList.of(1)));
    }

    public static void bootstrapDeepsDensity(BootstrapContext<DensityFunction> context) {

        Holder<DensityFunction> deeps_diagonals = context.register(DEEPS_DIAGONALS, DeepsDiagonals());
        Holder<DensityFunction> deeps_grid = context.register(DEEPS_GRID, DeepsGrid());
        Holder<DensityFunction> deeps_base = context.register(DEEPS_BASE, DeepsBase(deeps_grid, deeps_diagonals));
        Holder<DensityFunction> deeps_caves = context.register(DEEPS_CAVES, DeepsCaves(context, deeps_grid));
        Holder<DensityFunction> deeps_diagonal_tunnels = context.register(DEEPS_DIAGONAL_TUNNELS, DeepsDiagonalTunnels(context));
        Holder<DensityFunction> deeps_pillars = context.register(DEEPS_PILLARS, DeepsPillars(context, deeps_grid, false));
        Holder<DensityFunction> deeps_stalactites = context.register(DEEPS_STALACTITES, DeepsPillars(context, deeps_grid, true));
        Holder<DensityFunction> final_height_factor = context.register(DEEPS_HEIGHT_FACTOR, DeepsHeightFactor());
        Holder<DensityFunction> deeps_final = context.register(DEEPS_FINAL, DeepsFinal(deeps_base, deeps_caves, deeps_diagonal_tunnels, final_height_factor));
        Holder<DensityFunction> deeps_lava_pits = context.register(DEEPS_LAVA_PITS, DeepsLavaHoles(context, deeps_base, deeps_caves, deeps_diagonal_tunnels));
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
     * -80: widest point
     * 10: ceiling of caverns
     * */


    //A function that is 1 on the diagonal lines connecting caverns and declines to as low as -1 elsewhere.
    //Desmos version: g\left(x,y\right)\ =\ \min\left(\cos\left(\left(x-y\right)\cdot\frac{2\pi}{a}\right),-\cos\left(\left(x+y\right)\cdot\frac{2\pi}{a}\right)\right)
    private static DensityFunction DeepsDiagonals() {
        return DensityFunctions.min(
                //NE-SW diagonals
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
                //NW-SE diagonals
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
        );
    }

    //Deeps Grid produces the base product-of-sines function. It varies from -1 in the center of the big caverns to 1 between them.
    private static DensityFunction DeepsGrid() {
        return DensityFunctions.mul(
                new SBDensityFunctions.Sine(new SBDensityFunctions.GetX(), period),
                new SBDensityFunctions.Sine(new SBDensityFunctions.GetZ(), period)
        );
    }

    //Deeps Base combines Deeps Grid and Deeps Diagonals to produce the base density function. By default it has round holes at the cavern locations
    //and low (but still positive) values on the diagonals, for easier cavern formation.
    private static DensityFunction DeepsBase(Holder<DensityFunction> deeps_grid, Holder<DensityFunction> deeps_diagonals)  {
        return DensityFunctions.flatCache(
                DensityFunctions.add(
                    //Minimum of two things
                    DensityFunctions.min(
                            //Product of sinX and sinZ, for the general structure
                            new DensityFunctions.HolderHolder(deeps_grid),

                            //DeepsDiagonals + 0.6, to ensure caverns can form easily on the diagonals
                            DensityFunctions.add(
                                    DensityFunctions.constant(0.55),
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
    //It is reduced in intensity within major caverns
    private static DensityFunction DeepsCaves(BootstrapContext<DensityFunction> context, Holder<DensityFunction> deeps_grid) {
        double maximum = 0.2;
        double minimum = -1;

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
                        )
                )
        );

        //Positive values get flattened, negative ones intensified
        DensityFunction multiplied = DensityFunctions.rangeChoice(base, -100, 0,
                DensityFunctions.mul(base, DensityFunctions.constant(1)),
                DensityFunctions.mul(base, DensityFunctions.constant(0.5)));

        //This section is to reduce its intensity within major caverns, i.e. wherever Deeps Grid is less than -0.6.
        //It creates a multiplier that is 1 everywhere up to Deeps Grid = -0.6 and then smoothly decreases to 0.4 when Deeps Grid is -1.
        //i.e. 1 minus ((-1.5 * Deeps Grid) - 0.9)
        //i.e. 1.9 plus 1.5 * Deeps Grid
        //Then take minimum of that or 1
        DensityFunction caves_multiplier = DensityFunctions.min(
                DensityFunctions.constant(1),
                DensityFunctions.add(
                        DensityFunctions.constant(1.9),
                        DensityFunctions.mul(
                                DensityFunctions.constant(1.5),
                                new DensityFunctions.HolderHolder(deeps_grid)
                        )
                )
        );

        //Apply the new multiplier
        multiplied = DensityFunctions.mul(multiplied, caves_multiplier);

        return DensityFunctions.cacheOnce(multiplied.clamp(minimum, maximum));
    }

    //Deeps Diagonal Tunnels produces diagonally-running tunnels between major caverns.
    //It starts by producing two diagonally-stretched noises to produce spaghetti caves.
    //It squares and adds them, then subtracts a constant to produce tunnels.
    //Then it mixes in the diagonals to limit them to the correct diagonal rows.
    //Then it adds a height function to keep them at the correct heights.
    //It does all the above twice (one for each diagonal direction) and takes the minimum of the two.
    //Finally, it clamps everything to only be negative or zero, so that when added it never produces stone, only air.
    private static DensityFunction DeepsDiagonalTunnels(BootstrapContext<DensityFunction> context) {

        //Get both sets
        DensityFunction nesw_tunnels = DeepsDiagonalTunnelSet(context, true).clamp(-100, 0);
        DensityFunction nwse_tunnels = DeepsDiagonalTunnelSet(context, false).clamp(-100, 0);

        //Add in a height function
        //Ranges from -0.1 at y = -70 to 0 at y = -82 and -58
        //Desmos: g\left(x\right)=0.000005\left(x+75\right)^{4}-0.1
        DensityFunction y = new SBDensityFunctions.GetY();
        DensityFunction y_plus_75 = DensityFunctions.add(y, DensityFunctions.constant(75));
        DensityFunction y_plus_75_to_the_fourth = DensityFunctions.mul(DensityFunctions.mul(y_plus_75, y_plus_75), DensityFunctions.mul(y_plus_75, y_plus_75));
        DensityFunction tunnel_height = DensityFunctions.add(
                DensityFunctions.mul(
                        DensityFunctions.constant(0.000005),
                        y_plus_75_to_the_fourth
                ),
                DensityFunctions.constant(-0.1)
        );

        DensityFunction tunnels_final = DensityUtil.applyAll(DensityFunctions::add,
                nesw_tunnels,
                nwse_tunnels,
                tunnel_height
        );

        return DensityFunctions.cacheOnce(tunnels_final.clamp(-100, 0));
    }

    private static DensityFunction DeepsDiagonalTunnelSet(BootstrapContext<DensityFunction> context, boolean nesw) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        //Stretched Noises
        DensityFunction stretched_noise_1 = new SBDensityFunctions.DiagonalStretch(
                DensityFunctions.noise(noises.getOrThrow(DEEPS_DIAGONAL_TUNNELS_NOISE), 1),
                1,
                nesw ? 1 : -1,
                3
        );
        DensityFunction stretched_noise_2 = new SBDensityFunctions.DiagonalStretch(
                SBDensityFunctions.shiftedNoise(5000, -5000, 5000, -1, 1, noises.getOrThrow(DEEPS_DIAGONAL_TUNNELS_NOISE)),
                1,
                nesw ? 1 : -1,
                3
        );

        DensityFunction tunnels_base = DensityUtil.applyAll(DensityFunctions::add,

                //x^2+y^2
                DensityFunctions.mul(stretched_noise_1, stretched_noise_1),
                DensityFunctions.mul(stretched_noise_2, stretched_noise_2),

                //Minus a constant. This affects the size of the tunnels.
                DensityFunctions.constant(-0.04)
        );

        //Intensify the tunnels so the diagonals and height don't decide everything
        tunnels_base = DensityFunctions.mul(tunnels_base, DensityFunctions.constant(8));

        //Add in the diagonals
        DensityFunction diagonals;
        if (nesw) {
            diagonals = DensityFunctions.mul(
                    DensityFunctions.constant(-1),
                    new SBDensityFunctions.Cosine(
                            DensityFunctions.add(
                                    new SBDensityFunctions.GetX(),
                                    new SBDensityFunctions.GetZ()
                            ),
                            period
                    )
            );
        } else {
            diagonals = new SBDensityFunctions.Cosine(
                    DensityFunctions.add(
                            new SBDensityFunctions.GetX(),
                            DensityFunctions.mul(
                                    DensityFunctions.constant(-1),
                                    new SBDensityFunctions.GetZ()
                            )
                    ),
                    period
            );
        }
        diagonals = DensityFunctions.add(diagonals, DensityFunctions.constant(0.8));

        return DensityFunctions.add(tunnels_base, diagonals);
    }

    //Deeps Pillars produces stalagmites, stalactites, and pillars.
    //It combines a basic noise function,
    //A wider function to affect thickness,
    //A function to put it only in small clumps,
    //A function to keep it in the Deeps Grid between 0.5 and 0.8 region,
    //and a function to modify its height. (Mostly to make the bottom thicker.)
    private static DensityFunction DeepsPillars(BootstrapContext<DensityFunction> context, Holder<DensityFunction> deeps_grid, boolean stalactites_only) {
        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        DensityFunction base_pillars = DensityFunctions.mul(
                DensityFunctions.constant(2),
                DensityFunctions.noise(noises.getOrThrow(Noises.PILLAR), 20, 0)
        );

        //Add a function to keep it in small clumps. This uses the Deeps Diagonal Tunnels noise, but not stretched;
        // the two features have no relationship with each other.
        //Equivalent of the Pillar Rareness noise.
        DensityFunction rarity_function = DensityFunctions.mul(
                DensityFunctions.constant(stalactites_only ? 1 : 2),
                DensityFunctions.noise(noises.getOrThrow(DEEPS_DIAGONAL_TUNNELS_NOISE), 0)
        );

        DensityFunction output = DensityUtil.applyAll(DensityFunctions::add,
                base_pillars,
                rarity_function,
                DensityFunctions.constant(stalactites_only ? -1 : -1.5)
            );

        //Add a function that's 0 at Deeps Grid = -0.7 and falls off rapidly
        //it's -400(x + 0.7)^4
        //If stalactites only, it's + 0.9 instead
        DensityFunction factor = DensityFunctions.add(new DensityFunctions.HolderHolder(deeps_grid), DensityFunctions.constant(stalactites_only ? 0.9 : 0.7));
        DensityFunction to_the_fourth = DensityUtil.applyAll(DensityFunctions::mul, factor, factor, factor, factor);
        DensityFunction final_deeps_grid_limiter = DensityFunctions.mul(
                DensityFunctions.constant(-400),
                to_the_fourth
        );

        //The thickness function
        DensityFunction thickness_multiplier = DensityFunctions.add(
                DensityFunctions.constant(0.55),
                DensityFunctions.mul(
                        DensityFunctions.constant(0.55),
                        DensityFunctions.noise(noises.getOrThrow(Noises.PILLAR_THICKNESS))
                )
        );
        //Cube it
        thickness_multiplier = DensityUtil.applyAll(DensityFunctions::mul,
                thickness_multiplier,
                thickness_multiplier,
                thickness_multiplier
        );

        //A bit of gentle massaging away from the middle to produce thicker tops and bottoms
        DensityFunction pillar_height = DensityUtil.applyAll(DensityFunctions::mul,
                DensityFunctions.add(new SBDensityFunctions.GetY(), DensityFunctions.constant(70)),
                DensityFunctions.add(new SBDensityFunctions.GetY(), DensityFunctions.constant(50)),
                DensityFunctions.constant(0.0003)
                ).clamp(-0.1, 0.1);

        //If stalactites only, doesn't go back up on the lower side
        if (stalactites_only) {
            pillar_height = DensityFunctions.rangeChoice(new SBDensityFunctions.GetY(),
                    -10000, -59,
                    DensityFunctions.add(
                            DensityFunctions.mul(
                                    new SBDensityFunctions.GetY(),
                                    DensityFunctions.constant(0.01)
                            ),
                            DensityFunctions.constant(0.57)
                    ),
                    pillar_height);
        }

        //Removes the bottom of stalactites
        //Produces zero if it's not just stalactites
        //Produces zero for stalactites above -60, and a decreasing negative the further down it goes

        //Stalactite_base ranges from around 30 to 60 and represents the (negative) y-level of the bottom of the stalactites.
        DensityFunction stalactite_base = DensityFunctions.add(
                DensityFunctions.constant(45),
                DensityFunctions.mul(
                        DensityFunctions.noise(noises.getOrThrow(Noises.ICE), 0.3, 0),
                        DensityFunctions.constant(18.75)
                )
        );

        //Removes the bottom of stalactites
        //Produces zero if it's not just stalactites
        //Produces zero for stalactites above negative stalactite_base, and a decreasing negative the further down it goes
        DensityFunction stalactite_base_removal = stalactites_only ?
                DensityUtil.applyAll(DensityFunctions::mul,
                        DensityFunctions.constant(0.001),
                        DensityFunctions.add(new SBDensityFunctions.GetY(), stalactite_base),
                        DensityFunctions.add(new SBDensityFunctions.GetY(), stalactite_base),
                        DensityFunctions.add(new SBDensityFunctions.GetY(), stalactite_base)
                )
                : DensityFunctions.zero();

        stalactite_base_removal = stalactite_base_removal.clamp(-10, 0);

        output = DensityUtil.applyAll(DensityFunctions::add,
                final_deeps_grid_limiter,
                stalactite_base_removal,
//                pillar_height,
                DensityFunctions.mul(
                        output,
                        thickness_multiplier
                )
        );

        //Only exist when above 0.03
        output = DensityFunctions.rangeChoice(output, -100000, 0.03,
                DensityFunctions.constant(-100000),
                output);


        return DensityFunctions.cacheOnce(output);
    }

//    private static DensityFunction OverworldPillarsExample(BootstrapContext<DensityFunction> context) {
//        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
//
//        NormalNoise.NoiseParameters pillar_noise = noises.getOrThrow(Noises.PILLAR).value();
//        NormalNoise.NoiseParameters pillar_rareness = noises.getOrThrow(Noises.PILLAR_RARENESS).value();
//        NormalNoise.NoiseParameters pillar_thickness = noises.getOrThrow(Noises.PILLAR_THICKNESS).value();
//
//
//        DensityFunction output = DensityFunctions.cacheOnce(
//                DensityFunctions.mul(
//
//                        //2x pillar noise, minus pillar-rareness noise, minus 1.
//                        DensityFunctions.add(
//                                DensityFunctions.mul(
//                                        DensityFunctions.constant(2),
//                                        DensityFunctions.noise(new Holder.Direct<>(pillar_noise), 25, 0.3)
//                                ),
//                                DensityFunctions.add(
//                                        DensityFunctions.constant(-1),
//                                        DensityFunctions.mul(
//                                                DensityFunctions.constant(-1),
//                                                DensityFunctions.noise(new Holder.Direct<>(pillar_rareness), 1, 1)
//                                        )
//                                )
//
//                        ),
//
//                        //Take 0.55 * (pillar_thickness + 1). Cube it. This produces a multiplier between 0 and just over 1.
//                        //Multiplying by this produces areas where they're smeared, and areas where they're sharply distinct.
//                        DensityFunctions.map(
//                                DensityFunctions.add(
//                                        DensityFunctions.constant(0.55),
//                                        DensityFunctions.mul(
//                                                DensityFunctions.constant(0.55),
//                                                DensityFunctions.noise(new Holder.Direct<>(pillar_thickness))
//                                        )
//                                ),
//                                DensityFunctions.Mapped.Type.CUBE
//                        )
//                )
//        );
//
//
//    }

    //Deeps Lava Holes' baseline functions much like pillars: one noise function for the holes and one for their frequency.
    //Then, it is restricted to only the Mist Pools biome (rockiness > 0.2) and areas where Deeps Final is open at y = -85.
    //Finally, it is given a y-based height function to produce holes with a bottom around -102, steep sides that then widen from -94 to -90,
    //and then curve back in gently (though that doesn't do much, considering this is minned with the caves).
    private static DensityFunction DeepsLavaHoles(BootstrapContext<DensityFunction> context, Holder<DensityFunction> deeps_base, Holder<DensityFunction> deeps_caves, Holder<DensityFunction> deeps_tunnels) {

        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);

        Holder.Reference<NormalNoise.NoiseParameters> pits_noise = noises.getOrThrow(DEEPS_LAVA_PITS_NOISE);
        Holder.Reference<NormalNoise.NoiseParameters> pits_rareness = noises.getOrThrow(Noises.PILLAR_RARENESS);

        DensityFunction pits_texture = DensityUtil.applyAll(DensityFunctions::add,
                DensityFunctions.mul(
                        DensityFunctions.constant(2),
                        DensityFunctions.noise(pits_noise, 1.2, 0)
                ),
                DensityFunctions.mul(
                        DensityFunctions.constant(1),
                        DensityFunctions.noise(pits_rareness, 1, 0)
                ),
                DensityFunctions.constant(0.6)
        );

        DensityFunction deeps_final_at_y_85 = new SBDensityFunctions.SampleAtGivenY(
                DensityUtil.applyAll(DensityFunctions::add,
                        new DensityFunctions.HolderHolder(deeps_base),
                        new DensityFunctions.HolderHolder(deeps_caves),
                        new DensityFunctions.HolderHolder(deeps_tunnels)
                ), DensityFunctions.constant(-85));

        DensityFunction smooth_vitality = DensityFunctions.spline(CubicSpline.builder(new DensityFunctions.Spline.Coordinate(Holder.direct(DensityFunctions.noise(noises.getOrThrow(SBDimensions.VITALITY_NOISE), 1, 0))))
                .addPoint(-1, -1, 0)
                .addPoint(-0.6F, -0.7F, 0.5F)
                .addPoint(0.6F, 0.7F, 0.5F)
                .addPoint(1, 1, 0)
                .build());

        //Pits Presence is 0 when the pits should be there and rapidly increases elsewhere.
        DensityFunction pits_presence = DensityFunctions.max(
                //Only where rockiness > 0.15
                //Old: \left\{x\ge0.23:0,x<0.23:1000\left(x-0.23\right)^{2}\right\}
                //New: \max\left(0,\left\{x\ge0.15:\frac{-0.2\left(x-0.25\right)}{x-0.15},x<0.15:100000\right\}\right)
                DensityFunctions.max(
                        DensityFunctions.zero(),
                        DensityFunctions.rangeChoice(smooth_vitality, -10, 0.12,
                                DensityFunctions.constant(100000),
                                new SBDensityFunctions.Divide(
                                        DensityFunctions.mul(
                                                DensityFunctions.constant(-0.2),
                                                DensityFunctions.add(smooth_vitality, DensityFunctions.constant(-0.22))
                                        ),
                                        DensityFunctions.add(smooth_vitality, DensityFunctions.constant(-0.12))
                                )
                        )
                ),

                //Only where Deeps Final is open at y = -86, i.e. <= -0.06
                //Old: \left\{x\ \le\ -0.03:0,x>-0.03:500\left(x+0.03\right)^{2}\right\}
                //New: \max\left(0,\left\{x\ge-0.05:100000,x<-0.05:\frac{-0.1\left(x+0.2\right)}{x+0.05}\right\}\right)
                DensityFunctions.max(
                        DensityFunctions.zero(),
                        DensityFunctions.rangeChoice(deeps_final_at_y_85, -0.05, 100,
                                DensityFunctions.constant(100000),
                                new SBDensityFunctions.Divide(
                                        DensityFunctions.mul(
                                                DensityFunctions.constant(-0.1),
                                                DensityFunctions.add(deeps_final_at_y_85, DensityFunctions.constant(0.2))
                                        ),
                                        DensityFunctions.add(deeps_final_at_y_85, DensityFunctions.constant(0.05))
                                )
                        )
                )
        );

        DensityFunction y = new SBDensityFunctions.GetY();
        //Pits Height produces the vertical shape.
        //\left\{x\le-94:0.0001\left(x+94\right)^{4},x>-94:0.004\left(x+94.5\right)^{2}\left(x+85\right)+0.009\right\}
        DensityFunction pits_height = DensityFunctions.rangeChoice(y, -200, -93,
                DensityUtil.applyAll(DensityFunctions::mul,
                        DensityFunctions.constant(0.0001),
                        DensityFunctions.add(y, DensityFunctions.constant(94)),
                        DensityFunctions.add(y, DensityFunctions.constant(94)),
                        DensityFunctions.add(y, DensityFunctions.constant(94)),
                        DensityFunctions.add(y, DensityFunctions.constant(94))
                ),
                DensityFunctions.add(
                        DensityUtil.applyAll(DensityFunctions::mul,
                                DensityFunctions.constant(0.0015),
                                DensityFunctions.add(y, DensityFunctions.constant(95)),
                                DensityFunctions.add(y, DensityFunctions.constant(95)),
                                DensityFunctions.add(y, DensityFunctions.constant(82))
                        ),
                        DensityFunctions.constant(0.018)
                )

        );


        return DensityFunctions.cacheOnce(
                DensityUtil.applyAll(DensityFunctions::add,
                    pits_texture,
                    pits_presence,
                    pits_height
                )
        );
    }

    private static DensityFunction DeepsHeightFactor() {
        DensityFunction y = new SBDensityFunctions.GetY();

        DensityFunction y_plus_80_squared = DensityFunctions.mul(
                DensityFunctions.add(DensityFunctions.constant(80), y),
                DensityFunctions.add(DensityFunctions.constant(80), y)
        );

        //Height Factor produces flat floors from ~-95 to -85, and tall, rounded ceilings.
        DensityFunction height_factor = DensityFunctions.rangeChoice(
                y,
                -200,
                -80,
                DensityUtil.applyAll(DensityFunctions::mul,
                        DensityFunctions.constant(0.00004),
                        y_plus_80_squared,
                        y_plus_80_squared
                ),
                DensityFunctions.mul(
                        DensityFunctions.constant(0.0000816),
                        y_plus_80_squared
                )
        );

        //Hard ceiling adds a sharper ceiling in order to make sure caves don't go too high.
        DensityFunction hard_ceiling = DensityFunctions.rangeChoice(
                y,
                -10,
                100,
                DensityFunctions.add(
                        DensityUtil.applyAll(DensityFunctions::mul,
                                DensityFunctions.constant(0.01),
                                DensityFunctions.add(y, DensityFunctions.constant(10)),
                                DensityFunctions.add(y, DensityFunctions.constant(10))),
                        DensityFunctions.constant(0.4)
                ),
                DensityFunctions.zero()
        );

        //Final height factor
        //Desmos: f\left(x\right)=\left\{x\le-80:0.00004\left(x+80\right)^{4},-80\le x\le-10:0.0000816\left(x+80\right)^{2},-10\le x:0.004\left(x+20\right)^{2}\right\}
        DensityFunction final_height_factor = DensityFunctions.rangeChoice(
                y,
                -200,
                -10,
                height_factor,
                hard_ceiling
        );

        return DensityFunctions.cacheOnce(final_height_factor);
    }

    private static DensityFunction DeepsFinal(Holder<DensityFunction> deeps_base, Holder<DensityFunction> deeps_caves, Holder<DensityFunction> deeps_tunnels, Holder<DensityFunction> final_height_factor) {

        DensityFunction final_combination = DensityUtil.applyAll(DensityFunctions::add,
                new DensityFunctions.HolderHolder(deeps_base),
                new DensityFunctions.HolderHolder(deeps_caves),
                new DensityFunctions.HolderHolder(deeps_tunnels),
                new DensityFunctions.HolderHolder(final_height_factor)
        );

        return DensityFunctions.cacheOnce(final_combination);
    }


}
