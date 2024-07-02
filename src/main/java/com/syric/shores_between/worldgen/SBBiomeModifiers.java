package com.syric.shores_between.worldgen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.worldgen.feature.SBPlacedFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SBBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_TANGLED_SHINGLE = registerKey("add_tangled_shingle");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_TANGLED_SHINGLE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(SBTags.Biomes.HAS_TANGLED_SHINGLE),
                HolderSet.direct(placedFeatures.getOrThrow(SBPlacedFeatures.TANGLED_SHINGLE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(ShoresBetween.MODID, name));
    }

}
