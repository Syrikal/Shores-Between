package com.syric.shores_between.registry;

import com.mojang.serialization.Codec;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.structure.LeviathanBonesStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SBStructures {


    public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, ShoresBetween.MODID);

    public static void register(IEventBus bus) {
        DEFERRED_REGISTRY_STRUCTURE.register(bus);
    }

//    public static final DeferredHolder<StructureType<?>, StructureType<LeviathanBonesStructure>> LEVIATHAN_BONES = DEFERRED_REGISTRY_STRUCTURE.register("leviathan_bones", () -> explicitStructureTypeTyping(LeviathanBonesStructure.CODEC));

}
