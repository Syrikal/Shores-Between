package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SBBlockTagProvider extends BlockTagsProvider {
    public SBBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ShoresBetween.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        //Minecraft block tags
        this.tag(BlockTags.PLANKS)
                .add(SBBlocks.DRIFTWOOD_PLANKS.get(), SBBlocks.MISTWOOD_PLANKS.get());
        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(SBBlocks.DRIFTWOOD_BUTTON.get(), SBBlocks.MISTWOOD_BUTTON.get());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(SBBlocks.DRIFTWOOD_DOOR.get(), SBBlocks.MISTWOOD_DOOR.get());
        this.tag(BlockTags.WOODEN_STAIRS)
                .add(SBBlocks.DRIFTWOOD_STAIRS.get(), SBBlocks.MISTWOOD_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(SBBlocks.DRIFTWOOD_SLAB.get(), SBBlocks.MISTWOOD_SLAB.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(SBBlocks.DRIFTWOOD_FENCE.get(), SBBlocks.MISTWOOD_FENCE.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(SBBlocks.DRIFTWOOD_PRESSURE_PLATE.get(), SBBlocks.MISTWOOD_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(SBBlocks.DRIFTWOOD_TRAPDOOR.get(), SBBlocks.MISTWOOD_TRAPDOOR.get());
        this.tag(BlockTags.SAPLINGS)
                .add(SBBlocks.MISTWOOD_SAPLING.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(SBBlocks.DRIFTWOOD_LOG.get(), SBBlocks.DRIFTWOOD_WOOD.get(), SBBlocks.STRIPPED_DRIFTWOOD_LOG.get(), SBBlocks.STRIPPED_DRIFTWOOD_WOOD.get(), SBBlocks.MISTWOOD_LOG.get(), SBBlocks.MISTWOOD_WOOD.get(), SBBlocks.STRIPPED_MISTWOOD_LOG.get(), SBBlocks.STRIPPED_MISTWOOD_WOOD.get());
        this.tag(BlockTags.STAIRS)
                .add(SBBlocks.SHALE_STAIRS.get(), SBBlocks.POLISHED_SHALE_STAIRS.get(), SBBlocks.SHALE_BRICK_STAIRS.get(), SBBlocks.MOSSY_SHALE_BRICK_STAIRS.get(), SBBlocks.DARK_SHALE_STAIRS.get(), SBBlocks.POLISHED_SHALE_STAIRS.get(), SBBlocks.DARK_SHALE_BRICK_STAIRS.get(), SBBlocks.DARK_SHALE_TILE_STAIRS.get(), SBBlocks.PETRIFIED_STAIRS.get());
        this.tag(BlockTags.SLABS)
                .add(SBBlocks.SHALE_SLAB.get(), SBBlocks.POLISHED_SHALE_SLAB.get(), SBBlocks.SHALE_BRICK_SLAB.get(), SBBlocks.MOSSY_SHALE_BRICK_SLAB.get(), SBBlocks.DARK_SHALE_SLAB.get(), SBBlocks.POLISHED_SHALE_SLAB.get(), SBBlocks.DARK_SHALE_BRICK_SLAB.get(), SBBlocks.DARK_SHALE_TILE_SLAB.get(), SBBlocks.PETRIFIED_SLAB.get(), SBBlocks.SEAWEED_SLAB.get(), SBBlocks.DEAD_FISH_SLAB.get());
        this.tag(BlockTags.WALLS)
                .add(SBBlocks.SHALE_WALL.get(), SBBlocks.POLISHED_SHALE_WALL.get(), SBBlocks.SHALE_BRICK_WALL.get(), SBBlocks.MOSSY_SHALE_BRICK_WALL.get(), SBBlocks.DARK_SHALE_WALL.get(), SBBlocks.POLISHED_SHALE_WALL.get(), SBBlocks.DARK_SHALE_BRICK_WALL.get(), SBBlocks.DARK_SHALE_TILE_WALL.get());
        this.tag(BlockTags.LEAVES)
                .add(SBBlocks.MISTWOOD_LEAVES.get());
        this.tag(BlockTags.DIRT)
                .add(SBBlocks.GRASSY_SHINGLE.get());
        this.tag(BlockTags.ENDERMAN_HOLDABLE)
                .add(SBBlocks.SHINGLE.get(), SBBlocks.PEBBLES.get());
        this.tag(BlockTags.VALID_SPAWN)
                .add(SBBlocks.GRASSY_SHINGLE.get(), SBBlocks.OVERGROWN_SHALE.get());
        this.tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .add(SBBlocks.SHINGLE.get(), SBBlocks.PEBBLES.get(), SBBlocks.GRASSY_SHINGLE.get(), SBBlocks.OVERGROWN_SHALE.get());
        this.tag(BlockTags.STANDING_SIGNS)
                .add(SBBlocks.DRIFTWOOD_SIGN.get(), SBBlocks.MISTWOOD_SIGN.get());
        this.tag(BlockTags.WALL_SIGNS)
                .add(SBBlocks.DRIFTWOOD_WALL_SIGN.get(), SBBlocks.MISTWOOD_WALL_SIGN.get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(SBBlocks.DRIFTWOOD_HANGING_SIGN.get(), SBBlocks.MISTWOOD_HANGING_SIGN.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS)
                .add(SBBlocks.DRIFTWOOD_WALL_HANGING_SIGN.get(), SBBlocks.MISTWOOD_WALL_HANGING_SIGN.get());
        this.tag(BlockTags.DRAGON_IMMUNE)
                .add(SBBlocks.TITAN_BONE.get(), SBBlocks.CORRODED_TITAN_BONE.get(), SBBlocks.ABERRANT_SHELL.get());
        this.tag(BlockTags.WITHER_IMMUNE)
                .add(SBBlocks.TITAN_BONE.get(), SBBlocks.CORRODED_TITAN_BONE.get(), SBBlocks.ABERRANT_SHELL.get());
        this.tag(BlockTags.PORTALS)
                .add(SBBlocks.PORTAL_BLOCK.get());
        this.tag(BlockTags.CLIMBABLE)
                .add(SBBlocks.DRIFTWOOD_LADDER.get(), SBBlocks.MISTWOOD_LADDER.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(SBBlocks.DRIFTWOOD_FENCE_GATE.get(), SBBlocks.MISTWOOD_FENCE_GATE.get());
        this.tag(BlockTags.CAULDRONS)
                .add(SBBlocks.ICHOR_CAULDRON.get());
        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS)
                .add(SBBlocks.SEAWEED_CARPET.get());
        this.tag(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
                .add(SBBlocks.MISTWOOD_MOSS_CARPET.get(), SBBlocks.BARNACLES.get(), SBBlocks.OYSTERS.get());
        this.tag(BlockTags.OCCLUDES_VIBRATION_SIGNALS)
                .add(SBBlocks.MUMMIFIED_FLESH.get());
        this.tag(BlockTags.DAMPENS_VIBRATIONS)
                .add(SBBlocks.MUMMIFIED_FLESH.get());
        this.tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(SBBlocks.TITAN_BONE.get(), SBBlocks.CORRODED_TITAN_BONE.get(), SBBlocks.ABERRANT_SHELL.get());
        this.tag(BlockTags.REPLACEABLE)
                .add(SBBlocks.DUNEGRASS.get());
        
        //My block tags
        this.tag(SBTags.Blocks.DRIFTWOOD_LOGS)
                .add(SBBlocks.DRIFTWOOD_LOG.get(), SBBlocks.DRIFTWOOD_WOOD.get(), SBBlocks.STRIPPED_DRIFTWOOD_LOG.get(), SBBlocks.STRIPPED_DRIFTWOOD_WOOD.get());
        this.tag(SBTags.Blocks.MISTWOOD_LOGS)
                .add(SBBlocks.MISTWOOD_LOG.get(), SBBlocks.MISTWOOD_WOOD.get(), SBBlocks.STRIPPED_MISTWOOD_LOG.get(), SBBlocks.STRIPPED_MISTWOOD_WOOD.get());
        this.tag(SBTags.Blocks.PETRIFIED_LOGS)
                .add(SBBlocks.PETRIFIED_LOG.get(), SBBlocks.PETRIFIED_WOOD.get(), SBBlocks.STRIPPED_PETRIFIED_LOG.get(), SBBlocks.STRIPPED_PETRIFIED_WOOD.get());

        //Neoforge block tags
        this.tag(Tags.Blocks.BARRELS_WOODEN)
                .add(SBBlocks.DRIFTWOOD_BARREL.get(), SBBlocks.MISTWOOD_BARREL.get());
        this.tag(Tags.Blocks.CHESTS_WOODEN)
                .add(SBBlocks.DRIFTWOOD_CHEST.get(), SBBlocks.MISTWOOD_CHEST.get());
        this.tag(Tags.Blocks.CHESTS)
                .add(SBBlocks.ANCIENT_CHEST.get());
        this.tag(Tags.Blocks.FENCE_GATES_WOODEN)
                .add(SBBlocks.DRIFTWOOD_FENCE_GATE.get(), SBBlocks.MISTWOOD_FENCE_GATE.get());
        this.tag(Tags.Blocks.FENCES_WOODEN)
                .add(SBBlocks.DRIFTWOOD_FENCE.get(), SBBlocks.MISTWOOD_FENCE.get());
        this.tag(Tags.Blocks.GRAVELS)
                .add(SBBlocks.SHINGLE.get(), SBBlocks.PEBBLES.get(), SBBlocks.TANGLED_SHINGLE.get(), SBBlocks.GRASSY_SHINGLE.get());
        this.tag(Tags.Blocks.ORE_RATES_SINGULAR)
                .add(SBBlocks.CORRODED_TITAN_BONE.get());
        this.tag(Tags.Blocks.ORES)
                .add(SBBlocks.CORRODED_TITAN_BONE.get());

        //Mineability tags
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(SBBlocks.DRIFTWOOD_CHEST.get(), SBBlocks.MISTWOOD_CHEST.get(), SBBlocks.SEAGULL_NEST.get(), SBBlocks.MISTWOOD_BARREL.get(), SBBlocks.DRIFTWOOD_BARREL.get(), SBBlocks.DRIFTWOOD_LADDER.get(), SBBlocks.MISTWOOD_LADDER.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE)
                .add(SBBlocks.ROTTING_FLESH_BLOCK.get(), SBBlocks.INFESTED_FLESH_BLOCK.get(), SBBlocks.BLOATED_FLESH.get(), SBBlocks.MUMMIFIED_FLESH.get(), SBBlocks.JELLIED_ICHOR.get(), SBBlocks.SEAWEED_SLAB.get(), SBBlocks.SEAWEED_CARPET.get(), SBBlocks.MISTWOOD_MOSS_BLOCK.get(), SBBlocks.MISTWOOD_MOSS_CARPET.get(), SBBlocks.DEAD_FISH_SLAB.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(SBBlocks.SHINGLE.get(), SBBlocks.TANGLED_SHINGLE.get(), SBBlocks.PEBBLES.get(), SBBlocks.GRASSY_SHINGLE.get(), SBBlocks.SUSPICIOUS_SHINGLE.get(), SBBlocks.BURIED_IRON_SCRAP.get(), SBBlocks.BURIED_RUSTY_SCRAP.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(SBBlocks.ANCIENT_CHEST.get(), SBBlocks.BARNACLES.get(), SBBlocks.CHISELED_DARK_SHALE.get(), SBBlocks.CHISELED_SHALE.get(), SBBlocks.CORRODED_CONCRETE.get(), SBBlocks.CORRODED_LANTERN.get(), SBBlocks.CORRODED_TITAN_BONE.get(), SBBlocks.CRAB_POT.get(), SBBlocks.CRACKED_DARK_SHALE_BRICKS.get(), SBBlocks.CRACKED_DARK_SHALE_TILES.get(), SBBlocks.CRACKED_SHALE_BRICKS.get(), SBBlocks.DARK_OBELISK.get(), SBBlocks.DARK_SHALE.get(), SBBlocks.DARK_SHALE_BRICK_SLAB.get(), SBBlocks.DARK_SHALE_BRICK_STAIRS.get(), SBBlocks.DARK_SHALE_BRICK_WALL.get(), SBBlocks.DARK_SHALE_BRICKS.get(), SBBlocks.DARK_SHALE_SLAB.get(), SBBlocks.DARK_SHALE_STAIRS.get(), SBBlocks.DARK_SHALE_TILE_SLAB.get(), SBBlocks.DARK_SHALE_TILE_STAIRS.get(), SBBlocks.DARK_SHALE_TILE_WALL.get(), SBBlocks.DARK_SHALE_TILES.get(), SBBlocks.DARK_SHALE_WALL.get(), SBBlocks.ENGRAVED_DARK_SHALE.get(), SBBlocks.IRON_SCRAP_BLOCK.get(), SBBlocks.LEVIATHAN_BONE.get(), SBBlocks.LEVIATHAN_KERATIN.get(), SBBlocks.MOSSY_SHALE_BRICK_SLAB.get(), SBBlocks.MOSSY_SHALE_BRICK_STAIRS.get(), SBBlocks.MOSSY_SHALE_BRICK_WALL.get(), SBBlocks.MOSSY_SHALE_BRICKS.get(), SBBlocks.OVERGROWN_SHALE.get(), SBBlocks.OYSTERS.get(), SBBlocks.PETRIFIED_LOG.get(), SBBlocks.PETRIFIED_PLANKS.get(), SBBlocks.PETRIFIED_SLAB.get(), SBBlocks.PETRIFIED_STAIRS.get(), SBBlocks.PETRIFIED_WOOD.get(), SBBlocks.POLISHED_DARK_SHALE.get(), SBBlocks.POLISHED_DARK_SHALE_SLAB.get(), SBBlocks.POLISHED_DARK_SHALE_STAIRS.get(), SBBlocks.POLISHED_DARK_SHALE_WALL.get(), SBBlocks.POLISHED_SHALE.get(), SBBlocks.POLISHED_SHALE_SLAB.get(), SBBlocks.POLISHED_SHALE_STAIRS.get(), SBBlocks.POLISHED_SHALE_WALL.get(), SBBlocks.RUSTY_SCRAP_BLOCK.get(), SBBlocks.SALTSTONE.get(), SBBlocks.SHALE.get(), SBBlocks.SHALE_BRICK_SLAB.get(), SBBlocks.SHALE_BRICK_STAIRS.get(), SBBlocks.SHALE_BRICK_WALL.get(), SBBlocks.SHALE_BRICKS.get(), SBBlocks.SHALE_SLAB.get(), SBBlocks.SHALE_STAIRS.get(), SBBlocks.SHALE_WALL.get(), SBBlocks.STRIPPED_PETRIFIED_LOG.get(), SBBlocks.STRIPPED_PETRIFIED_WOOD.get(), SBBlocks.WHALEBONE_TOTEM.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(SBBlocks.IRON_SCRAP_BLOCK.get(), SBBlocks.RUSTY_SCRAP_BLOCK.get());
        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(SBBlocks.DARK_OBELISK.get(), SBBlocks.CORRODED_TITAN_BONE.get());
    }
}
