package com.syric.shores_between.worldgen.dimension.generation_formulae;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;

import static com.syric.shores_between.ShoresBetween.MODID;

public class Strands {

    public static final ResourceKey<DensityFunction> BASE_STRAND_TERRAIN = ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(MODID, "base_strand_terrain"));

}
