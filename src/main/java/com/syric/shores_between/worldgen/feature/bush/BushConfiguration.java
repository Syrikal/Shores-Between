package com.syric.shores_between.worldgen.feature.bush;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BushConfiguration(
            BlockStateProvider trunkProvider,
            BlockStateProvider leafProvider,
            IntProvider size //0 for small, 1 for medium, 2 for large, 3 for very large
        ) implements FeatureConfiguration {

        public static final Codec<BushConfiguration> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                        BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(config -> config.trunkProvider),
                        BlockStateProvider.CODEC.fieldOf("leaf_provider").forGetter(config -> config.leafProvider),
                        IntProvider.CODEC.fieldOf("size").forGetter(config -> config.size)
                ).apply(builder, BushConfiguration::new)
        );


}
