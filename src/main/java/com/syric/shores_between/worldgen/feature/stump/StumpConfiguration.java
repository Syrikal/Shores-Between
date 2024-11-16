package com.syric.shores_between.worldgen.feature.stump;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.Set;

public record StumpConfiguration(
            IntProvider height,
            BlockStateProvider trunkProvider,
            BlockStateProvider woodProvider,
            Boolean replaceExposedFoundation,
            IntProvider buttresses,
            IntProvider buttressHeight,
            Boolean buried
    ) implements FeatureConfiguration {

    public static final Codec<Set<Block>> BLOCK_SET_CODEC = Codec.list(BuiltInRegistries.BLOCK.byNameCodec()).xmap(ObjectOpenHashSet::new, ArrayList::new);

    public static final Codec<StumpConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    IntProvider.CODEC.fieldOf("height").forGetter(config -> config.height),
                    BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(config -> config.trunkProvider),
                    BlockStateProvider.CODEC.fieldOf("wood_provider").forGetter(config -> config.woodProvider),
                    Codec.BOOL.fieldOf("replaceExposedFoundation").forGetter(config -> config.replaceExposedFoundation),
                    IntProvider.codec(0,4).fieldOf("buttresses").forGetter(config -> config.buttresses),
                    IntProvider.CODEC.fieldOf("buttress_height").forGetter(config -> config.buttressHeight),
                    Codec.BOOL.fieldOf("buried").forGetter(config -> config.buried)
            ).apply(builder, StumpConfiguration::new)
    );

}
