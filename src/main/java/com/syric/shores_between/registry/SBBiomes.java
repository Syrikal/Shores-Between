package com.syric.shores_between.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBiomes {

    public static final ResourceKey<Biome> DESOLATE_STRAND_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "desolate_strand"));
    public static final ResourceKey<Biome> DROWNED_FOREST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "drowned_forest"));
    public static final ResourceKey<Biome> BARREN_STRAND_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "barren_strand"));
    public static final ResourceKey<Biome> ROCKY_STRAND_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "rocky_strand"));
    public static final ResourceKey<Biome> ROCK_FIELDS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "rock_fields"));
    public static final ResourceKey<Biome> CRAGS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "crags"));
    public static final ResourceKey<Biome> GRASSY_STRAND_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "grassy_strand"));
    public static final ResourceKey<Biome> DRIFTWOOD_BEACH_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "driftwood_beach"));
    public static final ResourceKey<Biome> MISTWOOD_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "mistwood"));
    public static final ResourceKey<Biome> MISTWOOD_EDGE = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "mistwood_edge"));
    public static final ResourceKey<Biome> FORSAKEN_OCEAN_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "forsaken_ocean"));
    public static final ResourceKey<Biome> SEAMOUNTS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "seamounts"));


    public static void bootstrapBiomes(BootstrapContext<Biome> context) {
        context.register(DESOLATE_STRAND_BIOME, DesolateStrandBiome(context));
        context.register(DROWNED_FOREST_BIOME, DrownedForestBiome(context));
        context.register(BARREN_STRAND_BIOME, BarrenStrandBiome(context));
        context.register(ROCKY_STRAND_BIOME, RockyStrandBiome(context));
        context.register(ROCK_FIELDS_BIOME, RockFieldsBiome(context));
        context.register(CRAGS_BIOME, CragsBiome(context));
        context.register(GRASSY_STRAND_BIOME, GrassyStrandBiome(context));
        context.register(DRIFTWOOD_BEACH_BIOME, DriftwoodBeachBiome(context));
        context.register(MISTWOOD_BIOME, MistwoodBiome(context));
        context.register(MISTWOOD_EDGE, MistwoodEdgeBiome(context));
        context.register(FORSAKEN_OCEAN_BIOME, ForsakenOceanBiome(context));
        context.register(SEAMOUNTS_BIOME, SeamountsBiome(context));
    }

    public static Biome DesolateStrandBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xc5cfd9)
                .skyColor(0xa3b0c7)
                .waterColor(0x5f6d78)
                .waterFogColor(0x405359)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome DrownedForestBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa8b0b3)
                .skyColor(0x9ba7ab)
                .waterColor(0x58696b)
                .waterFogColor(0x4b5d5e)
                .foliageColorOverride(0x4e5c55)
                .grassColorOverride(0x606661)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome BarrenStrandBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xbac9d1)
                .skyColor(0xafbdc4)
                .waterColor(0x566c6e)
                .waterFogColor(0x4a6163)
                .foliageColorOverride(0x4e5c55)
                .grassColorOverride(0x606661)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome RockyStrandBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa9b5c4)
                .skyColor(0xa9b5c4)
                .waterColor(0x5f7882)
                .waterFogColor(0x405359)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome RockFieldsBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa9b1ba)
                .skyColor(0xa9b1ba)
                .waterColor(0x5f7278)
                .waterFogColor(0x405359)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome CragsBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa9b1ba)
                .skyColor(0xa9b1ba)
                .waterColor(0x5f7278)
                .waterFogColor(0x405359)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome GrassyStrandBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xbfc9d6)
//                .skyColor(0x86abe3)
                .skyColor(0x337eff) //super intense blue for testing
                .waterColor(0x638099)
                .waterFogColor(0x4c5e6e)
                .foliageColorOverride(0x607d5a)
                .grassColorOverride(0xa7ad7d)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome DriftwoodBeachBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xbfc9d6)
                .skyColor(0x94aed6)
                .waterColor(0x5c7387)
                .waterFogColor(0x3c4d5c)
                .foliageColorOverride(0x607d5a)
                .grassColorOverride(0xa7ad7d)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome MistwoodEdgeBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xbfc9d6)
                .skyColor(0x94aed6)
                .waterColor(0x5c7387)
                .waterFogColor(0x3c4d5c)
                .foliageColorOverride(0x40613c)
                .grassColorOverride(0x728f64)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome MistwoodBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa9b6c7)
                .skyColor(0x9baecc)
                .waterColor(0x5c7387)
                .waterFogColor(0x3c4d5c)
                .foliageColorOverride(0x2a4f34)
                .grassColorOverride(0x4b6953)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome ForsakenOceanBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa6b1bf)
                .skyColor(0x94a3b3)
                .waterColor(0x5f7278)
                .waterFogColor(0x323f4a)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }

    public static Biome SeamountsBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xa3afbf)
                .skyColor(0xa3afbf)
                .waterColor(0x5f7278)
                .waterFogColor(0x405359)
                .foliageColorOverride(0x566b61)
                .grassColorOverride(0x727d74)
                .build();

        MobSpawnSettings mobSpawnSettings = new MobSpawnSettings.Builder()
                .build();

        BiomeGenerationSettings biomeGenerationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER))
                .build();

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.7f)
                .mobSpawnSettings(mobSpawnSettings)
                .generationSettings(biomeGenerationSettings)
                .specialEffects(specialEffects)
                .build();
    }


}
