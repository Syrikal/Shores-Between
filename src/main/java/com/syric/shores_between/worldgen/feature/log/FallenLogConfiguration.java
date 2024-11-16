package com.syric.shores_between.worldgen.feature.log;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record FallenLogConfiguration(
            BlockStateProvider trunkProvider,
            IntProvider length,
            IntProvider buried //0 for no, 1 for partial, 2 for fully
        ) implements FeatureConfiguration {

        public static final Codec<FallenLogConfiguration> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                        BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(config -> config.trunkProvider),
                        IntProvider.CODEC.fieldOf("length").forGetter(config -> config.length),
                        IntProvider.CODEC.fieldOf("buried").forGetter(config -> config.buried)
                ).apply(builder, FallenLogConfiguration::new)
        );


}
