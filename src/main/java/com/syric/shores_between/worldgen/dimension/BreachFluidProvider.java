package com.syric.shores_between.worldgen.dimension;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public abstract class BreachFluidProvider implements Aquifer.FluidPicker {

    private static final int lava_level = -93;

    public static Aquifer.FluidPicker breachFluidPicker(NoiseGeneratorSettings settings) {
        Aquifer.FluidStatus deep_lava = new Aquifer.FluidStatus(lava_level, Blocks.PEARLESCENT_FROGLIGHT.defaultBlockState());
        int i = settings.seaLevel();
        Aquifer.FluidStatus otherwise = new Aquifer.FluidStatus(i, settings.defaultFluid());
        return (x, y, z) -> y < Math.min(lava_level, i) ? deep_lava : otherwise;
    }

}
