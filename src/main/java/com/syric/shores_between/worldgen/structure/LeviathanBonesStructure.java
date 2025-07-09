package com.syric.shores_between.worldgen.structure;

import com.syric.shores_between.registry.SBStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class LeviathanBonesStructure extends Structure {


    protected LeviathanBonesStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return StructureType.IGLOO;
//        return SBStructures.LEVIATHAN_BONES.get();
    }
}
