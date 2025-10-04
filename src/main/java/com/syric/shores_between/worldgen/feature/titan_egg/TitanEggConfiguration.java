package com.syric.shores_between.worldgen.feature.titan_egg;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record TitanEggConfiguration(
        IntProvider size, //Approx diameter in blocks. Default 8.
        FloatProvider elongation //How much longer the long end is. Default 2.
) implements FeatureConfiguration {

    public static final Codec<TitanEggConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    IntProvider.CODEC.fieldOf("size").orElse(ConstantInt.of(8)).forGetter(config -> config.size),
                    FloatProvider.CODEC.fieldOf("elongation").orElse(ConstantFloat.of(2)).forGetter(config -> config.elongation)
            ).apply(builder, TitanEggConfiguration::new)
    );

}
