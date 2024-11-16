package com.syric.shores_between.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class SBOrePlacement {

    public static List<PlacementModifier> orePlacement(PlacementModifier frequency, PlacementModifier height) {
        return List.of(frequency, InSquarePlacement.spread(), height, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier height) {
        return orePlacement(CountPlacement.of(count), height);
    }

    public static List<PlacementModifier> rareOrePlacement(int once_per_x, PlacementModifier height) {
        return orePlacement(RarityFilter.onAverageOnceEvery(once_per_x), height);
    }

}
