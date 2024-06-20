package com.syric.shores_between.datagen;

import com.syric.shores_between.registry.SBBiomes;
import com.syric.shores_between.registry.SBDimensions;
import com.syric.shores_between.worldgen.dimension.generation_formulae.RockFields;
import com.syric.shores_between.worldgen.dimension.generation_formulae.Strands;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, SBBiomes::bootstrapBiomes)
            .add(Registries.DIMENSION_TYPE, SBDimensions::bootstrapDimensionType)
            .add(Registries.LEVEL_STEM, SBDimensions::bootstrapLevelStem)
            .add(Registries.NOISE, SBWorldGenProvider::bootstrapAllNoise)
            .add(Registries.DENSITY_FUNCTION, SBWorldGenProvider::bootstrapAllDensity)
            .add(Registries.NOISE_SETTINGS, SBDimensions::bootstrapNoiseSettings)
            .add(Registries.BIOME_SOURCE, SBDimensions::bootstrapBiomeSource)
            ;

    public SBWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MODID));
    }

    private static void bootstrapAllNoise(BootstrapContext<NormalNoise.NoiseParameters> context) {
        SBDimensions.bootstrapBiomeNoise(context);
        RockFields.bootstrapRockFieldsNoise(context);
        Strands.bootstrapStrandNoise(context);
    }

    private static void bootstrapAllDensity(BootstrapContext<DensityFunction> context) {
        List<DensityFunction> biomeDensityFunctions = SBDimensions.bootstrapBiomeDensity(context);
        RockFields.bootstrapRockFieldsDensity(context, biomeDensityFunctions);
        Strands.bootstrapStrandDensity(context);
    }

}
