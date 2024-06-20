package com.syric.shores_between.worldgen.dimension;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.registry.SBBiomes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.*;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.DensityFunction;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BreachBiomeSource extends BiomeSource {
    public static final MapCodec<BreachBiomeSource> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            RegistryOps.retrieveElement(SBBiomes.DESOLATE_STRAND_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.DROWNED_FOREST_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.BARREN_STRAND_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.ROCKY_STRAND_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.ROCK_FIELDS_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.CRAGS_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.GRASSY_STRAND_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.DRIFTWOOD_BEACH_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.MISTWOOD_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.MISTWOOD_COAST_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.FORSAKEN_OCEAN_BIOME),
                            RegistryOps.retrieveElement(SBBiomes.SEAMOUNTS_BIOME)
                    )
                    .apply(builder, builder.stable(BreachBiomeSource::new))
    );

    private final Holder<Biome> desolate_strand;
    private final Holder<Biome> drowned_forest;
    private final Holder<Biome> barren_strand;
    private final Holder<Biome> rocky_strand;
    private final Holder<Biome> rock_fields;
    private final Holder<Biome> crags;
    private final Holder<Biome> grassy_strand;
    private final Holder<Biome> driftwood_beach;
    private final Holder<Biome> mistwood;
    private final Holder<Biome> mistwood_coast;
    private final Holder<Biome> forsaken_ocean;
    private final Holder<Biome> seamounts;

    public static BreachBiomeSource create(HolderGetter<Biome> pBiomeGetter) {
        return new BreachBiomeSource(
                pBiomeGetter.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.BARREN_STRAND_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.CRAGS_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.MISTWOOD_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.MISTWOOD_COAST_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME),
                pBiomeGetter.getOrThrow(SBBiomes.SEAMOUNTS_BIOME)
        );
    }

    private BreachBiomeSource(Holder<Biome> desolateStrand, Holder<Biome> drownedForest, Holder<Biome> barrenStrand, Holder<Biome> rockyStrand, Holder<Biome> rockFields, Holder<Biome> crags, Holder<Biome> grassyStrand, Holder<Biome> driftwoodBeach, Holder<Biome> mistwood, Holder<Biome> mistwoodCoast, Holder<Biome> forsakenOcean, Holder<Biome> seamounts) {
        desolate_strand = desolateStrand;
        drowned_forest = drownedForest;
        barren_strand = barrenStrand;
        rocky_strand = rockyStrand;
        rock_fields = rockFields;
        this.crags = crags;
        grassy_strand = grassyStrand;
        driftwood_beach = driftwoodBeach;
        this.mistwood = mistwood;
        mistwood_coast = mistwoodCoast;
        forsaken_ocean = forsakenOcean;
        this.seamounts = seamounts;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.of(this.desolate_strand, this.drowned_forest, this.barren_strand, this.rocky_strand, this.rock_fields, this.crags, this.grassy_strand, this.driftwood_beach, this.mistwood, this.mistwood_coast, this.forsaken_ocean, this.seamounts);
    }

    @Override
    public MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        int i = QuartPos.toBlock(x);
        int j = QuartPos.toBlock(y);
        int k = QuartPos.toBlock(z);

        int i1 = (SectionPos.blockToSectionCoord(i) * 2 + 1) * 8;
        int k1 = (SectionPos.blockToSectionCoord(k) * 2 + 1) * 8;

        double continentalness = sampler.continentalness().compute(new DensityFunction.SinglePointContext(i1, j, k1));
        double vitality = sampler.humidity().compute(new DensityFunction.SinglePointContext(i1, j, k1));
        double rockiness = sampler.temperature().compute(new DensityFunction.SinglePointContext(i1, j, k1));

        return getBiome(continentalness, vitality, rockiness);
    }

    private Holder<Biome> getBiome(double continentalness, double vitality, double rockiness) {

        if (continentalness >= 1) {
            return this.mistwood;
        } else if (continentalness >= 0.98) {
            return this.mistwood_coast;
        } else if (-0.5 < continentalness && continentalness < -0.1) {
            return this.forsaken_ocean;
        } else if (-0.5 >= continentalness) {
            return getDeepOceanBiome(rockiness);
        } else if (0.8 < continentalness) {
            return getBorderBeachBiome(vitality, rockiness);
        } else {
            return getBeachBiome(vitality, rockiness);
        }

    }

    private Holder<Biome> getDeepOceanBiome(double rockiness) {
        if (rockiness >= 0.4) {
            return this.seamounts;
        } else {
            //Deep ocean?
            return this.forsaken_ocean;
        }
    }

    private Holder<Biome> getBeachBiome(double vitality, double rockiness) {
        if (-0.2 < vitality && 0.2 > vitality) {
            return this.desolate_strand;
        } else if (-0.6 < vitality && 0.6 > vitality) {
            return (0.6 < rockiness) ? this.rocky_strand : this.desolate_strand;
        } else if (-0.6 >= vitality) {
            if (-0.2 >= rockiness) {
                return this.drowned_forest;
            } else if (.2 >= rockiness) {
                return this.barren_strand;
            } else if (.6 >= rockiness) {
                return this.rocky_strand;
            } else {
                return this.rock_fields;
            }
        } else {
            if (-0.2 >= rockiness) {
                return this.grassy_strand;
            } else if (.2 >= rockiness) {
                return this.driftwood_beach;
            } else if (.6 >= rockiness) {
                return this.rocky_strand;
            } else {
                return this.crags;
            }
        }
    }

    private Holder<Biome> getBorderBeachBiome(double vitality, double rockiness) {
        if (0.6 < rockiness) {
            return Math.abs(vitality) < 0.2 ? this.desolate_strand : this.rocky_strand;
        } else if (0.2 < rockiness) {
            return Math.abs(vitality) < 0.6 ? this.desolate_strand : this.rocky_strand;
        } else {
            if (0.2 > vitality) {
                return this.desolate_strand;
            } else if (-0.2 < rockiness) {
                return this.driftwood_beach;
            } else {
                return this.grassy_strand;
            }
        }
    }

    @Override
    public void addDebugInfo(List<String> pInfo, BlockPos pPos, Climate.Sampler pSampler) {
        int i = QuartPos.fromBlock(pPos.getX());
        int j = QuartPos.fromBlock(pPos.getY());
        int k = QuartPos.fromBlock(pPos.getZ());
        Climate.TargetPoint climate$targetpoint = pSampler.sample(i, j, k);
        float f = Climate.unquantizeCoord(climate$targetpoint.continentalness());
        float f2 = Climate.unquantizeCoord(climate$targetpoint.temperature());
        float f3 = Climate.unquantizeCoord(climate$targetpoint.humidity());
        pInfo.add(
                "Breach Continentalness: "
                        + f
                        + ", Rockiness: "
                        + f2
                        + ", Vitality: "
                        + f3
        );
    }

}
