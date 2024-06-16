package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class SBItemModelProvider extends ItemModelProvider {

    public SBItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShoresBetween.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(SBItems.CRAB);
        simpleItem(SBItems.COOKED_CRAB);
        simpleItem(SBItems.LOBSTER);
        simpleItem(SBItems.COOKED_LOBSTER);
        simpleItem(SBItems.ODD_FISH);
        simpleItem(SBItems.SEAGULL_EGG);
        simpleItem(SBItems.OYSTER);
        simpleItem(SBItems.COOKED_OYSTER);
        simpleItem(SBItems.BARNACLE);
        simpleItem(SBItems.WRITHING_TENDRILS);
        simpleItem(SBItems.ROTTEN_FISH);
        simpleItem(SBItems.STRANGE_MEAT);
        simpleItem(SBItems.IRON_SCRAP);
        simpleItem(SBItems.RUSTY_SCRAP);
        simpleItem(SBItems.RUST);
        simpleItem(SBItems.SALT);
        simpleItem(SBItems.WHALEBONE);
        simpleItem(SBItems.AMBERGRIS);
        simpleItem(SBItems.RAW_TITAN_MARROW);
        simpleItem(SBItems.TITAN_MARROW);
        simpleItem(SBItems.BOTTLE_OF_ICHOR);
        simpleItem(SBItems.MESSAGE_IN_A_BOTTLE);
        simpleItem(SBItems.TOTEM_OF_WARNING);
        simpleItem(SBItems.TOTEM_OF_REVEALING);
        simpleItem(SBItems.STRAND_ARMOR_TRIM_SMITHING_TEMPLATE);


        simpleBlockItem(SBBlocks.SHINGLE);
        simpleBlockItem(SBBlocks.TANGLED_SHINGLE);
        simpleBlockItem(SBBlocks.PEBBLES);
        simpleBlockItem(SBBlocks.GRASSY_SHINGLE);
        simpleBlockItem(SBBlocks.SHALE);
        simpleBlockItem(SBBlocks.DARK_SHALE);
        simpleBlockItem(SBBlocks.OVERGROWN_SHALE);
        simpleBlockItem(SBBlocks.SALTSTONE);
        simpleBlockItem(SBBlocks.DRIFTWOOD_LOG);
        simpleBlockItem(SBBlocks.DRIFTWOOD_WOOD);
        simpleBlockItem(SBBlocks.STRIPPED_DRIFTWOOD_LOG);
        simpleBlockItem(SBBlocks.STRIPPED_DRIFTWOOD_WOOD);
        simpleBlockItem(SBBlocks.DRIFTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.DRIFTWOOD_SLAB);
        simpleBlockItem(SBBlocks.DRIFTWOOD_STAIRS);
        blockWithFlatTexture(SBBlocks.DRIFTWOOD_DOOR);
        simpleTrapdoorItem(SBBlocks.DRIFTWOOD_TRAPDOOR);
        simpleFenceItem(SBBlocks.DRIFTWOOD_FENCE, SBBlocks.DRIFTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.DRIFTWOOD_FENCE_GATE);
        simpleButtonItem(SBBlocks.DRIFTWOOD_BUTTON, SBBlocks.DRIFTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.DRIFTWOOD_PRESSURE_PLATE);
        simpleItem(SBItems.DRIFTWOOD_SIGN);
        simpleItem(SBItems.DRIFTWOOD_HANGING_SIGN);
        blockWithOwnFlatTexture(SBBlocks.DRIFTWOOD_LADDER);
        simpleBlockItem(SBBlocks.PETRIFIED_LOG);
        simpleBlockItem(SBBlocks.PETRIFIED_WOOD);
        simpleBlockItem(SBBlocks.STRIPPED_PETRIFIED_LOG);
        simpleBlockItem(SBBlocks.STRIPPED_PETRIFIED_WOOD);
        simpleBlockItem(SBBlocks.PETRIFIED_PLANKS);
        simpleBlockItem(SBBlocks.PETRIFIED_SLAB);
        simpleBlockItem(SBBlocks.PETRIFIED_STAIRS);
        simpleBlockItem(SBBlocks.MISTWOOD_LOG);
        simpleBlockItem(SBBlocks.MISTWOOD_WOOD);
        simpleBlockItem(SBBlocks.STRIPPED_MISTWOOD_LOG);
        simpleBlockItem(SBBlocks.STRIPPED_MISTWOOD_WOOD);
        simpleBlockItem(SBBlocks.MISTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.MISTWOOD_SLAB);
        simpleBlockItem(SBBlocks.MISTWOOD_STAIRS);
        blockWithFlatTexture(SBBlocks.MISTWOOD_DOOR);
        simpleTrapdoorItem(SBBlocks.MISTWOOD_TRAPDOOR);
        simpleFenceItem(SBBlocks.MISTWOOD_FENCE, SBBlocks.MISTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.MISTWOOD_FENCE_GATE);
        simpleButtonItem(SBBlocks.MISTWOOD_BUTTON, SBBlocks.MISTWOOD_PLANKS);
        simpleBlockItem(SBBlocks.MISTWOOD_PRESSURE_PLATE);
        simpleItem(SBItems.MISTWOOD_SIGN);
        simpleItem(SBItems.MISTWOOD_HANGING_SIGN);
        blockWithOwnFlatTexture(SBBlocks.MISTWOOD_LADDER);
        simpleBlockItem(SBBlocks.MISTWOOD_LEAVES);
        blockWithOwnFlatTexture(SBBlocks.MISTWOOD_SAPLING);
        simpleBlockItem(SBBlocks.ROTTING_FLESH_BLOCK);
        simpleBlockItem(SBBlocks.INFESTED_FLESH_BLOCK);
        simpleBlockItem(SBBlocks.BLOATED_FLESH);
        simpleBlockItem(SBBlocks.MUMMIFIED_FLESH);
        simpleBlockItem(SBBlocks.LEVIATHAN_BONE);
        simpleBlockItem(SBBlocks.LEVIATHAN_KERATIN);
        simpleBlockItem(SBBlocks.JELLIED_ICHOR);
        simpleBlockItem(SBBlocks.ICHOR_CAULDRON);
        simpleBlockItem(SBBlocks.TITAN_BONE);
        simpleBlockItem(SBBlocks.CORRODED_TITAN_BONE);
        simpleBlockItem(SBBlocks.ABERRANT_SHELL);
        blockWithOwnFlatTexture(SBBlocks.DUNEGRASS);
        simpleBlockItem(SBBlocks.SEAWEED_SLAB);
        simpleBlockItem(SBBlocks.SEAWEED_CARPET);
        simpleBlockItem(SBBlocks.MISTWOOD_MOSS_CARPET);
        simpleBlockItem(SBBlocks.MISTWOOD_MOSS_BLOCK);
        simpleBlockItem(SBBlocks.CORRODED_CONCRETE);
        simpleBlockItem(SBBlocks.SUSPICIOUS_SHINGLE);
        simpleBlockItem(SBBlocks.SEAGULL_NEST);
        simpleBlockItem(SBBlocks.OYSTERS);
        simpleBlockItem(SBBlocks.BARNACLES);
        simpleBlockItem(SBBlocks.DEAD_FISH_SLAB);
        simpleBlockItem(SBBlocks.PORTAL_BLOCK);
        simpleBlockItem(SBBlocks.SHALE_SLAB);
        simpleBlockItem(SBBlocks.SHALE_STAIRS);
        simpleWallItem(SBBlocks.SHALE_WALL, SBBlocks.SHALE);
        simpleBlockItem(SBBlocks.POLISHED_SHALE);
        simpleBlockItem(SBBlocks.POLISHED_SHALE_SLAB);
        simpleBlockItem(SBBlocks.POLISHED_SHALE_STAIRS);
        simpleWallItem(SBBlocks.POLISHED_SHALE_WALL, SBBlocks.POLISHED_SHALE);
        simpleBlockItem(SBBlocks.SHALE_BRICKS);
        simpleBlockItem(SBBlocks.CRACKED_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.SHALE_BRICK_SLAB);
        simpleBlockItem(SBBlocks.SHALE_BRICK_STAIRS);
        simpleWallItem(SBBlocks.SHALE_BRICK_WALL, SBBlocks.SHALE_BRICKS);
        simpleBlockItem(SBBlocks.MOSSY_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.MOSSY_SHALE_BRICK_SLAB);
        simpleBlockItem(SBBlocks.MOSSY_SHALE_BRICK_STAIRS);
        simpleWallItem(SBBlocks.MOSSY_SHALE_BRICK_WALL, SBBlocks.MOSSY_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.DARK_SHALE_SLAB);
        simpleBlockItem(SBBlocks.DARK_SHALE_STAIRS);
        simpleWallItem(SBBlocks.DARK_SHALE_WALL, SBBlocks.DARK_SHALE);
        simpleBlockItem(SBBlocks.POLISHED_DARK_SHALE);
        simpleBlockItem(SBBlocks.POLISHED_DARK_SHALE_SLAB);
        simpleBlockItem(SBBlocks.POLISHED_DARK_SHALE_STAIRS);
        simpleWallItem(SBBlocks.POLISHED_DARK_SHALE_WALL, SBBlocks.POLISHED_DARK_SHALE);
        simpleBlockItem(SBBlocks.DARK_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.CRACKED_DARK_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.DARK_SHALE_BRICK_SLAB);
        simpleBlockItem(SBBlocks.DARK_SHALE_BRICK_STAIRS);
        simpleWallItem(SBBlocks.DARK_SHALE_BRICK_WALL, SBBlocks.DARK_SHALE_BRICKS);
        simpleBlockItem(SBBlocks.DARK_SHALE_TILES);
        simpleBlockItem(SBBlocks.CRACKED_DARK_SHALE_TILES);
        simpleBlockItem(SBBlocks.DARK_SHALE_TILE_SLAB);
        simpleBlockItem(SBBlocks.DARK_SHALE_TILE_STAIRS);
        simpleWallItem(SBBlocks.DARK_SHALE_TILE_WALL, SBBlocks.DARK_SHALE_TILES);
        simpleBlockItem(SBBlocks.CHISELED_SHALE);
        simpleBlockItem(SBBlocks.CHISELED_DARK_SHALE);
        simpleBlockItem(SBBlocks.ENGRAVED_DARK_SHALE);
        simpleBlockItem(SBBlocks.DARK_OBELISK);
        simpleBlockItem(SBBlocks.CRAB_POT);
        blockWithOwnFlatTexture(SBBlocks.WHALEBONE_TOTEM);
        simpleBlockItem(SBBlocks.DRIFTWOOD_BARREL);
        simpleBlockItem(SBBlocks.MISTWOOD_BARREL);
        blockWithFlatTexture(SBBlocks.CORRODED_LANTERN);
        simpleBlockItem(SBBlocks.BURIED_IRON_SCRAP);
        simpleBlockItem(SBBlocks.BURIED_RUSTY_SCRAP);
        simpleBlockItem(SBBlocks.IRON_SCRAP_BLOCK);
        simpleBlockItem(SBBlocks.RUSTY_SCRAP_BLOCK);

        chestItem(SBItems.DRIFTWOOD_CHEST, SBBlocks.DRIFTWOOD_PLANKS);
        chestItem(SBItems.MISTWOOD_CHEST, SBBlocks.MISTWOOD_PLANKS);
        chestItem(SBItems.ANCIENT_CHEST, SBBlocks.POLISHED_DARK_SHALE);
    }

    private void simpleItem(DeferredItem<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ShoresBetween.MODID, "item/" + item.getId().getPath()));
    }

    private void simpleBlockItem(DeferredBlock<Block> block) {
        withExistingParent(block.getRegisteredName(),
                new ResourceLocation(ShoresBetween.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }

    private void simpleTrapdoorItem(DeferredBlock<Block> block) {
        withExistingParent(block.getRegisteredName(),
                new ResourceLocation(ShoresBetween.MODID, "block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath() + "_bottom"));
    }

    private void simpleFenceItem(DeferredBlock<Block> block, DeferredBlock<Block> fullBlock) {
        withExistingParent(block.getRegisteredName(), mcLoc("block/fence_inventory"))
                .texture("texture", blockTexture(fullBlock));
    }

    private void simpleButtonItem(DeferredBlock<Block> block, DeferredBlock<Block> fullBlock) {
        withExistingParent(block.getRegisteredName(), mcLoc("block/button_inventory"))
                .texture("texture", blockTexture(fullBlock));
    }

    private void blockWithFlatTexture(DeferredBlock<Block> block) {
        withExistingParent(block.getRegisteredName(),
                new ResourceLocation("item/generated")).texture("layer0",
                itemTexture(block));
    }

    private void blockWithOwnFlatTexture(DeferredBlock<Block> block) {
        withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                blockTexture(block));
    }

    private void simpleWallItem(DeferredBlock<Block> block, DeferredBlock<Block> fullBlock) {
        withExistingParent(block.getRegisteredName(), mcLoc("block/wall_inventory"))
                .texture("wall", blockTexture(fullBlock));
    }

    private void chestItem(DeferredItem<Item> chest, DeferredBlock<Block> fullBlock) {
        withExistingParent(chest.getRegisteredName(), mcLoc("item/chest"))
                .texture("particle", blockTexture(fullBlock));
    }

    private ResourceLocation blockTexture(DeferredBlock<Block> block) {
        return new ResourceLocation(ShoresBetween.MODID, "block/" + block.getId().getPath());
    }

    private ResourceLocation itemTexture(DeferredBlock<Block> block) {
        return new ResourceLocation(ShoresBetween.MODID, "item/" + block.getId().getPath());
    }

}
