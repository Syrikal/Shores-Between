package com.syric.shores_between.datagen;

import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBItems;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class SBItemTagProvider extends ItemTagsProvider {
    public SBItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //Minecraft block tags
        this.tag(ItemTags.PLANKS)
                .add(SBBlocks.DRIFTWOOD_PLANKS.get().asItem().asItem(), SBBlocks.MISTWOOD_PLANKS.get().asItem().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS)
                .add(SBBlocks.DRIFTWOOD_BUTTON.get().asItem(), SBBlocks.MISTWOOD_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS)
                .add(SBBlocks.DRIFTWOOD_DOOR.get().asItem(), SBBlocks.MISTWOOD_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS)
                .add(SBBlocks.DRIFTWOOD_STAIRS.get().asItem(), SBBlocks.MISTWOOD_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS)
                .add(SBBlocks.DRIFTWOOD_SLAB.get().asItem(), SBBlocks.MISTWOOD_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES)
                .add(SBBlocks.DRIFTWOOD_FENCE.get().asItem(), SBBlocks.MISTWOOD_FENCE.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(SBBlocks.DRIFTWOOD_PRESSURE_PLATE.get().asItem(), SBBlocks.MISTWOOD_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS)
                .add(SBBlocks.DRIFTWOOD_TRAPDOOR.get().asItem(), SBBlocks.MISTWOOD_TRAPDOOR.get().asItem());
        this.tag(ItemTags.SAPLINGS)
                .add(SBBlocks.MISTWOOD_SAPLING.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(SBBlocks.DRIFTWOOD_LOG.get().asItem(), SBBlocks.DRIFTWOOD_WOOD.get().asItem(), SBBlocks.STRIPPED_DRIFTWOOD_LOG.get().asItem(), SBBlocks.STRIPPED_DRIFTWOOD_WOOD.get().asItem(), SBBlocks.MISTWOOD_LOG.get().asItem(), SBBlocks.MISTWOOD_WOOD.get().asItem(), SBBlocks.STRIPPED_MISTWOOD_LOG.get().asItem(), SBBlocks.STRIPPED_MISTWOOD_WOOD.get().asItem());
        this.tag(ItemTags.STAIRS)
                .add(SBBlocks.SHALE_STAIRS.get().asItem(), SBBlocks.POLISHED_SHALE_STAIRS.get().asItem(), SBBlocks.SHALE_BRICK_STAIRS.get().asItem(), SBBlocks.MOSSY_SHALE_BRICK_STAIRS.get().asItem(), SBBlocks.DARK_SHALE_STAIRS.get().asItem(), SBBlocks.POLISHED_SHALE_STAIRS.get().asItem(), SBBlocks.DARK_SHALE_BRICK_STAIRS.get().asItem(), SBBlocks.DARK_SHALE_TILE_STAIRS.get().asItem(), SBBlocks.PETRIFIED_STAIRS.get().asItem());
        this.tag(ItemTags.SLABS)
                .add(SBBlocks.SHALE_SLAB.get().asItem(), SBBlocks.POLISHED_SHALE_SLAB.get().asItem(), SBBlocks.SHALE_BRICK_SLAB.get().asItem(), SBBlocks.MOSSY_SHALE_BRICK_SLAB.get().asItem(), SBBlocks.DARK_SHALE_SLAB.get().asItem(), SBBlocks.POLISHED_SHALE_SLAB.get().asItem(), SBBlocks.DARK_SHALE_BRICK_SLAB.get().asItem(), SBBlocks.DARK_SHALE_TILE_SLAB.get().asItem(), SBBlocks.PETRIFIED_SLAB.get().asItem(), SBBlocks.SEAWEED_SLAB.get().asItem(), SBBlocks.DEAD_FISH_SLAB.get().asItem());
        this.tag(ItemTags.WALLS)
                .add(SBBlocks.SHALE_WALL.get().asItem(), SBBlocks.POLISHED_SHALE_WALL.get().asItem(), SBBlocks.SHALE_BRICK_WALL.get().asItem(), SBBlocks.MOSSY_SHALE_BRICK_WALL.get().asItem(), SBBlocks.DARK_SHALE_WALL.get().asItem(), SBBlocks.POLISHED_SHALE_WALL.get().asItem(), SBBlocks.DARK_SHALE_BRICK_WALL.get().asItem(), SBBlocks.DARK_SHALE_TILE_WALL.get().asItem());
        this.tag(ItemTags.LEAVES)
                .add(SBBlocks.MISTWOOD_LEAVES.get().asItem());
        this.tag(ItemTags.DIRT)
                .add(SBBlocks.GRASSY_SHINGLE.get().asItem());
        this.tag(ItemTags.FENCE_GATES)
                .add(SBBlocks.DRIFTWOOD_FENCE_GATE.get().asItem(), SBBlocks.MISTWOOD_FENCE_GATE.get().asItem());
        this.tag(ItemTags.DAMPENS_VIBRATIONS)
                .add(SBBlocks.MUMMIFIED_FLESH.get().asItem());
        this.tag(ItemTags.FISHES)
                .add(SBItems.ODD_FISH.get(), SBItems.ROTTEN_FISH.get());
        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(SBItems.STRAND_ARMOR_TRIM_SMITHING_TEMPLATE.get());

        //My block tags
        this.tag(SBTags.Items.DRIFTWOOD_LOGS)
                .add(SBBlocks.DRIFTWOOD_LOG.get().asItem(), SBBlocks.DRIFTWOOD_WOOD.get().asItem(), SBBlocks.STRIPPED_DRIFTWOOD_LOG.get().asItem(), SBBlocks.STRIPPED_DRIFTWOOD_WOOD.get().asItem());
        this.tag(SBTags.Items.MISTWOOD_LOGS)
                .add(SBBlocks.MISTWOOD_LOG.get().asItem(), SBBlocks.MISTWOOD_WOOD.get().asItem(), SBBlocks.STRIPPED_MISTWOOD_LOG.get().asItem(), SBBlocks.STRIPPED_MISTWOOD_WOOD.get().asItem());
        this.tag(SBTags.Items.PETRIFIED_LOGS)
                .add(SBBlocks.PETRIFIED_LOG.get().asItem(), SBBlocks.PETRIFIED_WOOD.get().asItem(), SBBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), SBBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());


        //Neoforge block tags
        this.tag(Tags.Items.BARRELS_WOODEN)
                .add(SBBlocks.DRIFTWOOD_BARREL.get().asItem(), SBBlocks.MISTWOOD_BARREL.get().asItem());
        this.tag(Tags.Items.CHESTS_WOODEN)
                .add(SBBlocks.DRIFTWOOD_CHEST.get().asItem(), SBBlocks.MISTWOOD_CHEST.get().asItem());
        this.tag(Tags.Items.CHESTS)
                .add(SBBlocks.ANCIENT_CHEST.get().asItem());
        this.tag(Tags.Items.FENCE_GATES_WOODEN)
                .add(SBBlocks.DRIFTWOOD_FENCE_GATE.get().asItem(), SBBlocks.MISTWOOD_FENCE_GATE.get().asItem());
        this.tag(Tags.Items.FENCES_WOODEN)
                .add(SBBlocks.DRIFTWOOD_FENCE.get().asItem(), SBBlocks.MISTWOOD_FENCE.get().asItem());
        this.tag(Tags.Items.GRAVELS)
                .add(SBBlocks.SHINGLE.get().asItem(), SBBlocks.PEBBLES.get().asItem(), SBBlocks.TANGLED_SHINGLE.get().asItem(), SBBlocks.GRASSY_SHINGLE.get().asItem());
        this.tag(Tags.Items.ORE_RATES_SINGULAR)
                .add(SBBlocks.CORRODED_TITAN_BONE.get().asItem());
        this.tag(Tags.Items.ORES)
                .add(SBBlocks.CORRODED_TITAN_BONE.get().asItem());

        //Neoforge item tags
        this.tag(Tags.Items.EGGS)
                .add(SBItems.SEAGULL_EGG.get());
        this.tag(Tags.Items.FOODS_RAW_FISHES)
                .add(SBItems.CRAB.get(), SBItems.LOBSTER.get(), SBItems.OYSTER.get(), SBItems.ODD_FISH.get(), SBItems.ROTTEN_FISH.get());
        this.tag(Tags.Items.FOODS_COOKED_FISHES)
                .add(SBItems.COOKED_CRAB.get(), SBItems.COOKED_LOBSTER.get(), SBItems.COOKED_OYSTER.get());
        this.tag(Tags.Items.FOODS_FOOD_POISONING)
                .add(SBItems.CRAB.get(), SBItems.LOBSTER.get(), SBItems.ODD_FISH.get(), SBItems.ROTTEN_FISH.get(), SBItems.WRITHING_TENDRILS.get(), SBItems.OYSTER.get(), SBItems.STRANGE_MEAT.get());

    }
}
