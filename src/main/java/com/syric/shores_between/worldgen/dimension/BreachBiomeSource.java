package com.syric.shores_between.worldgen.dimension;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.stream.Stream;

public class BreachBiomeSource extends BiomeSource {
    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return null;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return null;
    }

    private static String getBiome() {
        float continentalness = 0;
        float vitality = 0;
        float rockiness = 0;

        if (continentalness >= 1) {
            return "mistwood";
        } else if (-0.5 < continentalness && continentalness < -0.1) {
            return "ocean";
        } else if (-0.5 >= continentalness) {
            return getDeepOceanBiome(rockiness);
        } else if (0.8 < continentalness) {
            return getBorderBeachBiome(vitality, rockiness);
        } else {
            return getBeachBiome(vitality, rockiness);
        }

    }

    private static String getDeepOceanBiome(float rockiness) {
        if (rockiness >= 0.4) {
            return "seamounts";
        } else {
            return "deep_ocean";
        }
    }

    private static String getBeachBiome(float vitality, float rockiness) {
        if (-0.2 < vitality && 0.2 > vitality) {
            return "desolate_strand";
        } else if (-0.6 < vitality && 0.6 > vitality) {
            return (0.6 < rockiness) ? "rocky_strand" : "desolate_strand";
        } else if (-0.6 >= vitality) {
            if (-0.2 >= rockiness) {
                return "drowned_forest";
            } else if (.2 >= rockiness) {
                return "barren_strand";
            } else if (.6 >= rockiness) {
                return "rocky_strand";
            } else {
                return "rock_fields";
            }
        } else {
            if (-0.2 >= rockiness) {
                return "grassy_strand";
            } else if (.2 >= rockiness) {
                return "driftwood_beach";
            } else if (.6 >= rockiness) {
                return "rocky_strand";
            } else {
                return "crags";
            }
        }
    }

    private static String getBorderBeachBiome(float vitality, float rockiness) {
        if (0.6 < rockiness) {
            return Math.abs(vitality) < 0.2 ? "desolate_strand" : "rocky_strand";
        } else if (0.2 < rockiness) {
            return Math.abs(vitality) < 0.6 ? "desolate_strand" : "rocky_strand";
        } else {
            if (0.2 > vitality) {
                return "desolate_strand";
            } else if (-0.2 < rockiness) {
                return "driftwood_beach";
            } else {
                return "grassy_strand";
            }
        }
    }

    @Override
    public Holder<Biome> getNoiseBiome(int p_204238_, int p_204239_, int p_204240_, Climate.Sampler p_204241_) {
        return null;
    }
}
