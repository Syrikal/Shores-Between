package com.syric.shores_between.worldgen.feature.beached_corpse;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BeachedCorpseConfiguration(
            Boolean dummy //completely meaningless
        ) implements FeatureConfiguration {

        public static final Codec<BeachedCorpseConfiguration> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                        Codec.BOOL.fieldOf("dummy").forGetter(config -> config.dummy)
                ).apply(builder, BeachedCorpseConfiguration::new)
        );


}
