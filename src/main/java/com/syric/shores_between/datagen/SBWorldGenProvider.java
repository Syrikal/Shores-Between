package com.syric.shores_between.datagen;

import com.syric.shores_between.registry.SBBiomes;
import com.syric.shores_between.registry.SBDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, SBBiomes::bootstrap)
            .add(Registries.DIMENSION_TYPE, SBDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, SBDimensions::bootstrapStem);

    public SBWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MODID));
    }
}
