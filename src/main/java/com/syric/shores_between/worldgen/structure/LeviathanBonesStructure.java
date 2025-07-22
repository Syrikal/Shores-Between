package com.syric.shores_between.worldgen.structure;

import com.mojang.serialization.MapCodec;
import com.syric.shores_between.registry.SBStructures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.Optional;

public class LeviathanBonesStructure extends Structure {

    public static final MapCodec<LeviathanBonesStructure> CODEC = simpleCodec(LeviathanBonesStructure::new);
    public int variant;

    public LeviathanBonesStructure(Structure.StructureSettings settings) {
        super(settings);
        variant = 0;
    }


    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

//        variant = context.random().nextInt(52);
//
//        StructurePlaceSettings placeSettings = new StructurePlaceSettings();
//        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
//
//        Optional<StructureTemplate> treeTemplateOptional = structureTemplateManager.get(tree);
//
//        StructureTemplate treeTemplate = treeTemplateOptional.get();
//        StructureTemplate.Palette treePalette = placeSettings.getRandomPalette(treeTemplate.palettes, context.chunkPos().getWorldPosition());
//
//        List<StructureTemplate.StructureBlockInfo> obsidian = palette.blocks(Blocks.OBSIDIAN);


        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return SBStructures.LEVIATHAN_BONES.get();
    }
}
