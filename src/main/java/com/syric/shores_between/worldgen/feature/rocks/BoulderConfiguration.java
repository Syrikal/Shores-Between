package com.syric.shores_between.worldgen.feature.rocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.boulder_decoration.BoulderDecorator;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record BoulderConfiguration(
        BlockStateProvider rockProvider,
        Boolean useSlabs,
        BlockStateProvider slabProvider,
        IntProvider size, //Approx diameter in blocks before clefts
        FloatProvider eccentricity,  //Multiplies divergence from a rough sphere
        FloatProvider irregularity_amplitude,
        FloatProvider irregularity_frequency,
        IntProvider overlaps,
        IntProvider clefts,
        Float compress_vertical,
        Boolean outcrop,
        Float prow, //Probability of an outcrop being a 'prow'
        List<BoulderDecorator>decorators
) implements FeatureConfiguration {

    public static final Codec<BoulderConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    BlockStateProvider.CODEC.fieldOf("rock_provider").forGetter(config -> config.rockProvider),
                    Codec.BOOL.fieldOf("use_slabs").orElse(false).forGetter(config -> config.useSlabs),
                    BlockStateProvider.CODEC.fieldOf("slab_provider").orElse(BlockStateProvider.simple(Blocks.AIR)).forGetter(config -> config.slabProvider),
                    IntProvider.CODEC.fieldOf("size").orElse(ConstantInt.of(5)).forGetter(config -> config.size),
                    FloatProvider.CODEC.fieldOf("eccentricity").orElse(ConstantFloat.of(1)).forGetter(config -> config.eccentricity),
                    FloatProvider.CODEC.fieldOf("irregularity_amplitude").orElse(ConstantFloat.of(1)).forGetter(config -> config.irregularity_amplitude),
                    FloatProvider.CODEC.fieldOf("irregularity_frequency").orElse(ConstantFloat.of(1)).forGetter(config -> config.irregularity_frequency),
                    IntProvider.CODEC.fieldOf("overlaps").orElse(ConstantInt.of(0)).forGetter(config -> config.overlaps),
                    IntProvider.CODEC.fieldOf("clefts").orElse(ConstantInt.of(0)).forGetter(config -> config.clefts),
                    Codec.FLOAT.fieldOf("compress_vertical").orElse(1F).forGetter(config -> config.compress_vertical),
                    Codec.BOOL.fieldOf("outcrop").orElse(false).forGetter(config -> config.outcrop),
                    Codec.FLOAT.fieldOf("prow").orElse(0F).forGetter(config -> config.prow),
                    BoulderDecorator.CODEC.listOf().fieldOf("decorators").forGetter(config -> config.decorators)
            ).apply(builder, BoulderConfiguration::new)
    );

}