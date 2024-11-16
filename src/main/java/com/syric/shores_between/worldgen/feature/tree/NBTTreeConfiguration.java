package com.syric.shores_between.worldgen.feature.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record NBTTreeConfiguration(
            List<ResourceLocation> treeLocations,
            IntProvider bonusHeight,
            BlockStateProvider trunkProvider,
            BlockStateProvider foliageProvider,
            BlockStateProvider woodProvider,
            Boolean replaceExposedFoundation,
            IntProvider buttresses,
            IntProvider buttressHeight
    ) implements FeatureConfiguration {

    public static final Codec<Set<Block>> BLOCK_SET_CODEC = Codec.list(BuiltInRegistries.BLOCK.byNameCodec()).xmap(ObjectOpenHashSet::new, ArrayList::new);

    public static final Codec<NBTTreeConfiguration> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                    ResourceLocation.CODEC.listOf().fieldOf("tree_locations").forGetter(config -> config.treeLocations),
                    IntProvider.CODEC.fieldOf("bonus_height").forGetter(config -> config.bonusHeight),
                    BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter(config -> config.trunkProvider),
                    BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter(config -> config.foliageProvider),
                    BlockStateProvider.CODEC.fieldOf("wood_provider").forGetter(config -> config.woodProvider),
                    Codec.BOOL.fieldOf("replaceExposedFoundation").forGetter(config -> config.replaceExposedFoundation),
                    IntProvider.codec(0,4).fieldOf("buttresses").forGetter(config -> config.buttresses),
                    IntProvider.CODEC.fieldOf("buttress_height").forGetter(config -> config.buttressHeight)
            ).apply(builder, NBTTreeConfiguration::new)
    );

}
