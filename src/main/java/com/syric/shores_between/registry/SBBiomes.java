package com.syric.shores_between.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBiomes {

    public static final ResourceKey<Biome> DESOLATE_STRAND_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "desolate_strand"));
    public static final ResourceKey<Biome> MISTWOOD_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "mistwood"));
    public static final ResourceKey<Biome> FORSAKEN_OCEAN_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation(MODID, "forsaken_ocean"));


    public static void bootstrapBiomes(BootstapContext<Biome> context) {
        context.register(DESOLATE_STRAND_BIOME, DesolateStrandBiome(context));
        context.register(MISTWOOD_BIOME, MistwoodBiome(context));
        context.register(FORSAKEN_OCEAN_BIOME, ForsakenOceanBiome(context));
    }

    public static Biome DesolateStrandBiome(BootstapContext<Biome> context) {

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

    public static Biome MistwoodBiome(BootstapContext<Biome> context) {

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

    public static Biome ForsakenOceanBiome(BootstapContext<Biome> context) {

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
