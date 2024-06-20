package com.syric.shores_between.registry;

import com.mojang.serialization.MapCodec;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.dimension.BreachBiomeSource;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SBBiomeSources {

    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(Registries.BIOME_SOURCE, ShoresBetween.MODID);

    public static void register(IEventBus bus) {
        BIOME_SOURCES.register(bus);
    }

    public static final DeferredHolder<MapCodec<? extends BiomeSource>, MapCodec<? extends BiomeSource>> BREACH_BIOME_SOURCE = BIOME_SOURCES.register("breach_biome_source",
            () -> BreachBiomeSource.CODEC);

}
