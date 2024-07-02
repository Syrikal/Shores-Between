package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.SBOrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class SBPlacedFeatures {

    public static ResourceKey<PlacedFeature> TANGLED_SHINGLE_PLACED_KEY = registerKey("tangled_shingle_placed");


    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, TANGLED_SHINGLE_PLACED_KEY, configuredFeatures.getOrThrow(SBConfiguredFeatures.TANGLED_SHINGLE_KEY),
                SBOrePlacement.commonOrePlacement(2,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(68))));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ShoresBetween.MODID, name));
    }

    private static  void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                                                                          Holder<ConfiguredFeature<?, ?>> holder, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(holder, List.copyOf(modifiers)));
    }

}
