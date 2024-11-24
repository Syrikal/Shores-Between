package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.placement_util.HeightFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SBPlacementModifierTypes {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, ShoresBetween.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<HeightFilter>> HEIGHT_FILTER = PLACEMENT_MODIFIER_TYPES.register("height_filter", () -> () -> HeightFilter.CODEC);

    public static void register(IEventBus bus) {
        PLACEMENT_MODIFIER_TYPES.register(bus);
    }
}
