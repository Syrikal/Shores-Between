package com.syric.shores_between.worldgen.feature.mummified_corpse;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class MummifiedCorpseFeature extends Feature<MummifiedCorpseConfiguration> {

    public MummifiedCorpseFeature(Codec<MummifiedCorpseConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MummifiedCorpseConfiguration> context) {
        RandomSource randomSource = context.random();
        WorldGenLevel level = context.level();
        StructureTemplateManager structureTemplateManager = level.getLevel().getServer().getStructureManager();
        BlockPos feature_origin = context.origin();
        Rotation rotation = Rotation.getRandom(randomSource);
        Mirror mirror = Util.getRandom(Mirror.values(), randomSource);


        StructurePlaceSettings placeSettings = new StructurePlaceSettings().setRotation(rotation);
        placeSettings = placeSettings.setMirror(mirror);
        MummifiedCorpseConfiguration config = context.config();

        ResourceLocation corpse = config.corpseLocations().get(randomSource.nextInt(config.corpseLocations().size()));
        Optional<StructureTemplate> corpseTemplateOptional = structureTemplateManager.get(corpse);

        if (corpseTemplateOptional.isEmpty()) {
            throw new IllegalArgumentException("Unknown corpse ResourceLocation: " + corpse);
        }

        StructureTemplate corpseTemplate = corpseTemplateOptional.get();
        StructureTemplate.Palette corpsePalette = placeSettings.getRandomPalette(corpseTemplate.palettes, feature_origin);

        BlockPos offset = new BlockPos(-16, -2, -16);

        boolean fossilized = randomSource.nextFloat() < config.fossilized_chance();
        float integrity = config.integrityProvider().sample(randomSource);
        float soft_tissue_integrity = config.softTissueIntegrityProvider().sample(randomSource);
        float ichor_integrity = config.ichorIntegrityProvider().sample(randomSource);

        if (fossilized) {
            soft_tissue_integrity = (float) Math.max(soft_tissue_integrity / 2 - 0.3, 0);
            ichor_integrity = (float) Math.max(ichor_integrity / 2 - 0.4, 0);
        }

        //Check that none are obstructed
        BlockPos.MutableBlockPos blockObstructionChecker = new BlockPos.MutableBlockPos().set(feature_origin);
        for (StructureTemplate.StructureBlockInfo block : corpsePalette.blocks()) {
            blockObstructionChecker.set(getModifiedPos(placeSettings, block, offset, feature_origin));
            if (!level.getBlockState(blockObstructionChecker).is(SBTags.Blocks.MUMMIFIED_CORPSE_REPLACEABLE)) {
                return false;
            }
        }

        //Place blocks
        for (StructureTemplate.StructureBlockInfo block : corpsePalette.blocks()) {
            BlockPos pos = getModifiedPos(placeSettings, block, offset, feature_origin);
            BlockState state = block.state(); //The state of the block in the template

            //All blocks have a chance of being removed entirely.
            //Flesh and ichor have a second chance of being removed.
            //Surviving ichor has a chance of being turned into flesh.
            float decay = randomSource.nextFloat();
            if (decay > integrity) {
                continue;
            } else if (state.is(SBBlocks.MUMMIFIED_FLESH.get()) || state.is(SBBlocks.JELLIED_ICHOR.get())) {
                float decay2 = randomSource.nextFloat();
                if (decay2 > soft_tissue_integrity) {
                    continue;
                } else if (state.is(SBBlocks.JELLIED_ICHOR.get())) {
                    float decay3 = randomSource.nextFloat();
                    if (decay3 > ichor_integrity) {
                        state = SBBlocks.MUMMIFIED_FLESH.get().defaultBlockState();
                    }
                }
            }

            level.setBlock(pos, state, 2);
        }


        return true;
    }

    public static BlockPos getModifiedPos(StructurePlaceSettings settings, StructureTemplate.StructureBlockInfo placing, BlockPos partCenter, BlockPos featureOrigin) {
        return StructureTemplate
                .calculateRelativePosition(settings, placing.pos())
                .offset(featureOrigin)
                .offset(StructureTemplate.calculateRelativePosition(settings, partCenter));
    }

}
