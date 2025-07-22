package com.syric.shores_between.worldgen.dimension;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class BreachFluidProvider {

    private static final int lava_level = -93;

    public static Aquifer.FluidPicker breachFluidPicker(NoiseGeneratorSettings settings) {
        Aquifer.FluidStatus deep_lava = new Aquifer.FluidStatus(lava_level, Blocks.LAVA.defaultBlockState());
        int i = settings.seaLevel();
        Aquifer.FluidStatus otherwise = new Aquifer.FluidStatus(i, settings.defaultFluid());
        return (p_224274_, p_224275_, p_224276_) -> p_224275_ < Math.min(lava_level, i) ? deep_lava : otherwise;
    }

}
