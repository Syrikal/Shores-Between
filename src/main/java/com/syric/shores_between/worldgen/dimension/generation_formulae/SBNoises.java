package com.syric.shores_between.worldgen.dimension.generation_formulae;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBNoises {

    public static final ResourceKey<NormalNoise.NoiseParameters> BARREN_PALE_STREAK_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "barren_streaks"));
    public static final ResourceKey<NormalNoise.NoiseParameters> BARREN_PALE_STREAK_2_NOISE = ResourceKey.create(Registries.NOISE, new ResourceLocation(MODID, "barren_streaks_2"));

    public static void bootstrapNoises(BootstrapContext<NormalNoise.NoiseParameters> context) {

        context.register(BARREN_PALE_STREAK_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(1, 1, 1)));
        context.register(BARREN_PALE_STREAK_2_NOISE, new NormalNoise.NoiseParameters(-7, DoubleList.of(1, 1, 1)));

    }
}
