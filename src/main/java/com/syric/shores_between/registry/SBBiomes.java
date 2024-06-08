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
    public static final ResourceKey<Biome> MISTWOOD_COAST_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "mistwood_coast"));
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
        context.register(MISTWOOD_COAST_BIOME, MistwoodCoastBiome(context));
        context.register(FORSAKEN_OCEAN_BIOME, ForsakenOceanBiome(context));
        context.register(SEAMOUNTS_BIOME, SeamountsBiome(context));
    }

    public static Biome DesolateStrandBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xffffff)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0x4f5763)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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

    public static Biome MistwoodCoastBiome(BootstrapContext<Biome> context) {

        BiomeSpecialEffects specialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
                .fogColor(0xffffff)
                .waterColor(0xffffff)
                .waterFogColor(0xffffff)
                .skyColor(0xadadad)
//                .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
                .foliageColorOverride(0xffffff)
                .grassColorOverride(0xffffff)
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
