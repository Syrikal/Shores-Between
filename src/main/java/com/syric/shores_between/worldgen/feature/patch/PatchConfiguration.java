package com.syric.shores_between.worldgen.feature.patch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PatchConfiguration(
        BlockStateProvider noiseProvider, //a block provider that returns black concrete and white concrete depending on noise.
        BlockStateProvider blockProvider,
        BlockStateProvider slabProvider,
        BlockStateProvider carpetProvider,
        IntProvider slope_length, //How quickly it goes from edge to center
        IntProvider size_cap, //Maximum radius in blocks from the origin
        FloatProvider center_thickness, //Approx thickness in center, in pixels. Default 16
        FloatProvider edge_thickness, //Approx thickness at edge, in pixels. Default 1.
        FloatProvider sparseness //Reduces thickness randomly across the patch. To disable, return 0.
) implements FeatureConfiguration {

    public static final Codec<PatchConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    BlockStateProvider.CODEC.fieldOf("noise_provider").forGetter(config -> config.noiseProvider),
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.blockProvider),
                    BlockStateProvider.CODEC.fieldOf("slab_provider").forGetter(config -> config.slabProvider),
                    BlockStateProvider.CODEC.fieldOf("carpet_provider").forGetter(config -> config.carpetProvider),
                    IntProvider.CODEC.fieldOf("slope_length").orElse(ConstantInt.of(5)).forGetter(config -> config.slope_length),
                    IntProvider.CODEC.fieldOf("size_cap").orElse(ConstantInt.of(5)).forGetter(config -> config.size_cap),
                    FloatProvider.CODEC.fieldOf("center_thickness").orElse(ConstantFloat.of(8)).forGetter(config -> config.center_thickness),
                    FloatProvider.CODEC.fieldOf("edge_thickness").orElse(ConstantFloat.of(1)).forGetter(config -> config.edge_thickness),
                    FloatProvider.CODEC.fieldOf("sparseness").orElse(ConstantFloat.of(0)).forGetter(config -> config.sparseness)
            ).apply(builder, PatchConfiguration::new)
    );

}
