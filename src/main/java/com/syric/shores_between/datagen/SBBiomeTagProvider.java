package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBiomes;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SBBiomeTagProvider extends BiomeTagsProvider {

    public SBBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ShoresBetween.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        HolderGetter<Biome> biomes = provider.lookupOrThrow(Registries.BIOME);

        this.tag(SBTags.Biomes.IS_BREACH)
                .add(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.DROWNED_FOREST_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.BARREN_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.ROCK_FIELDS_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.CRAGS_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.MISTWOOD_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.MISTWOOD_EDGE_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.FORSAKEN_OCEAN_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.SEAMOUNTS_BIOME).key())
        ;

        this.tag(SBTags.Biomes.HAS_TANGLED_SHINGLE)
                .add(biomes.getOrThrow(SBBiomes.DESOLATE_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.ROCKY_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.CRAGS_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.GRASSY_STRAND_BIOME).key())
                .add(biomes.getOrThrow(SBBiomes.DRIFTWOOD_BEACH_BIOME).key());
    }
}
