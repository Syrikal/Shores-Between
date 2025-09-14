package com.syric.shores_between.worldgen.feature.mummified_corpse;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

public record MummifiedCorpseConfiguration(
        List<ResourceLocation> corpseLocations,
        Float fossilized_chance,
        FloatProvider integrityProvider,
        FloatProvider softTissueIntegrityProvider,
        FloatProvider ichorIntegrityProvider
    ) implements FeatureConfiguration {

    public static final Codec<MummifiedCorpseConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("corpse_locations").forGetter(config -> config.corpseLocations),
                    Codec.FLOAT.fieldOf("fossilized_chance").forGetter(config -> config.fossilized_chance),
                    FloatProvider.CODEC.fieldOf("integrity_provider").forGetter(config -> config.integrityProvider),
                    FloatProvider.CODEC.fieldOf("soft_tissue_integrity_provider").forGetter(config -> config.softTissueIntegrityProvider),
                    FloatProvider.CODEC.fieldOf("ichor_integrity_provider").forGetter(config -> config.ichorIntegrityProvider)
            ).apply(instance, MummifiedCorpseConfiguration::new)
    );

}
