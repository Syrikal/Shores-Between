package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBItems;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;

public class SBRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public SBRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        woodSet(output,
                SBBlocks.DRIFTWOOD_LOG,
                SBTags.Items.DRIFTWOOD_LOGS,
                SBBlocks.DRIFTWOOD_WOOD,
                SBBlocks.STRIPPED_DRIFTWOOD_LOG,
                SBBlocks.STRIPPED_DRIFTWOOD_WOOD,
                SBBlocks.DRIFTWOOD_PLANKS,
                SBBlocks.DRIFTWOOD_SLAB,
                SBBlocks.DRIFTWOOD_STAIRS,
                SBBlocks.DRIFTWOOD_DOOR,
                SBBlocks.DRIFTWOOD_TRAPDOOR,
                SBBlocks.DRIFTWOOD_FENCE,
                SBBlocks.DRIFTWOOD_FENCE_GATE,
                SBBlocks.DRIFTWOOD_BUTTON,
                SBBlocks.DRIFTWOOD_PRESSURE_PLATE,
                SBBlocks.DRIFTWOOD_LADDER,
                SBItems.DRIFTWOOD_SIGN,
                SBItems.DRIFTWOOD_HANGING_SIGN,
                SBBlocks.DRIFTWOOD_CHEST,
                SBBlocks.DRIFTWOOD_BARREL);
        woodSet(output,
                SBBlocks.MISTWOOD_LOG,
                SBTags.Items.MISTWOOD_LOGS,
                SBBlocks.MISTWOOD_WOOD,
                SBBlocks.STRIPPED_MISTWOOD_LOG,
                SBBlocks.STRIPPED_MISTWOOD_WOOD,
                SBBlocks.MISTWOOD_PLANKS,
                SBBlocks.MISTWOOD_SLAB,
                SBBlocks.MISTWOOD_STAIRS,
                SBBlocks.MISTWOOD_DOOR,
                SBBlocks.MISTWOOD_TRAPDOOR,
                SBBlocks.MISTWOOD_FENCE,
                SBBlocks.MISTWOOD_FENCE_GATE,
                SBBlocks.MISTWOOD_BUTTON,
                SBBlocks.MISTWOOD_PRESSURE_PLATE,
                SBBlocks.MISTWOOD_LADDER,
                SBItems.MISTWOOD_SIGN,
                SBItems.MISTWOOD_HANGING_SIGN,
                SBBlocks.MISTWOOD_CHEST,
                SBBlocks.MISTWOOD_BARREL);
        
        petrifiedSet(output,
                SBBlocks.PETRIFIED_LOG,
                SBTags.Items.PETRIFIED_LOGS,
                SBBlocks.PETRIFIED_WOOD,
                SBBlocks.STRIPPED_PETRIFIED_LOG,
                SBBlocks.STRIPPED_PETRIFIED_WOOD,
                SBBlocks.PETRIFIED_PLANKS,
                SBBlocks.PETRIFIED_SLAB,
                SBBlocks.PETRIFIED_STAIRS);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SBBlocks.OVERGROWN_SHALE.get(), 1)
                .requires(SBBlocks.SHALE.get())
                .requires(SBBlocks.MISTWOOD_MOSS_CARPET.get())
                .unlockedBy(getHasName(SBBlocks.SHALE.get()), has(SBBlocks.SHALE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.SALTSTONE.get(), 1)
                .pattern("SS")
                .pattern("SS")
                .define('S', SBItems.SALT.get())
                .unlockedBy(getHasName(SBItems.SALT.get()), has(SBItems.SALT.get()))
                .save(output);

        stoneConversion(output, SBBlocks.SHALE, SBBlocks.POLISHED_SHALE);
        stoneConversion(output, SBBlocks.POLISHED_SHALE, SBBlocks.SHALE_BRICKS, SBBlocks.SHALE);
        stoneConversion(output, SBBlocks.DARK_SHALE, SBBlocks.POLISHED_DARK_SHALE);
        stoneConversion(output, SBBlocks.POLISHED_DARK_SHALE, SBBlocks.DARK_SHALE_BRICKS, SBBlocks.DARK_SHALE);
        stoneConversion(output, SBBlocks.DARK_SHALE_BRICKS, SBBlocks.DARK_SHALE_TILES, SBBlocks.POLISHED_DARK_SHALE, SBBlocks.DARK_SHALE);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SBBlocks.MOSSY_SHALE_BRICKS.get(), 1)
                .requires(SBBlocks.SHALE_BRICKS.get())
                .requires(SBBlocks.MISTWOOD_MOSS_BLOCK.get())
                .unlockedBy(getHasName(SBBlocks.SHALE_BRICKS.get()), has(SBBlocks.SHALE_BRICKS.get()))
                .save(output, ShoresBetween.MODID + ":" + "mossy_shale_bricks_with_moss");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SBBlocks.MOSSY_SHALE_BRICKS.get(), 1)
                .requires(SBBlocks.SHALE_BRICKS.get())
                .requires(Blocks.VINE)
                .unlockedBy(getHasName(SBBlocks.SHALE_BRICKS.get()), has(SBBlocks.SHALE_BRICKS.get()))
                .save(output, ShoresBetween.MODID + ":" + "mossy_shale_bricks_with_vines");

        stoneSet(output, SBBlocks.SHALE, SBBlocks.SHALE_SLAB, SBBlocks.SHALE_STAIRS, SBBlocks.SHALE_WALL);
        stoneSet(output, SBBlocks.POLISHED_SHALE, SBBlocks.POLISHED_SHALE_SLAB, SBBlocks.POLISHED_SHALE_STAIRS, SBBlocks.POLISHED_SHALE_WALL, SBBlocks.SHALE);
        stoneSet(output, SBBlocks.SHALE_BRICKS, SBBlocks.SHALE_BRICK_SLAB, SBBlocks.SHALE_BRICK_STAIRS, SBBlocks.SHALE_BRICK_WALL, SBBlocks.SHALE, SBBlocks.POLISHED_SHALE);
        stoneSet(output, SBBlocks.MOSSY_SHALE_BRICKS, SBBlocks.MOSSY_SHALE_BRICK_SLAB, SBBlocks.MOSSY_SHALE_BRICK_STAIRS, SBBlocks.MOSSY_SHALE_BRICK_WALL);
        stoneSet(output, SBBlocks.DARK_SHALE, SBBlocks.DARK_SHALE_SLAB, SBBlocks.DARK_SHALE_STAIRS, SBBlocks.DARK_SHALE_WALL);
        stoneSet(output, SBBlocks.POLISHED_DARK_SHALE, SBBlocks.POLISHED_DARK_SHALE_SLAB, SBBlocks.POLISHED_DARK_SHALE_STAIRS, SBBlocks.POLISHED_DARK_SHALE_WALL, SBBlocks.DARK_SHALE);
        stoneSet(output, SBBlocks.DARK_SHALE_BRICKS, SBBlocks.DARK_SHALE_BRICK_SLAB, SBBlocks.DARK_SHALE_BRICK_STAIRS, SBBlocks.DARK_SHALE_BRICK_WALL, SBBlocks.DARK_SHALE, SBBlocks.POLISHED_DARK_SHALE);
        stoneSet(output, SBBlocks.DARK_SHALE_TILES, SBBlocks.DARK_SHALE_TILE_SLAB, SBBlocks.DARK_SHALE_TILE_STAIRS, SBBlocks.DARK_SHALE_TILE_WALL, SBBlocks.DARK_SHALE, SBBlocks.POLISHED_DARK_SHALE, SBBlocks.DARK_SHALE_BRICKS);
        
        stonecutterChain(output, SBBlocks.SHALE_SLAB, SBBlocks.POLISHED_SHALE_SLAB, SBBlocks.SHALE_BRICK_SLAB);
        stonecutterChain(output, SBBlocks.SHALE_STAIRS, SBBlocks.POLISHED_SHALE_STAIRS, SBBlocks.SHALE_BRICK_STAIRS);
        stonecutterChain(output, SBBlocks.SHALE_WALL, SBBlocks.POLISHED_SHALE_WALL, SBBlocks.SHALE_BRICK_WALL);
        stonecutterChain(output, SBBlocks.DARK_SHALE_SLAB, SBBlocks.POLISHED_DARK_SHALE_SLAB, SBBlocks.DARK_SHALE_BRICK_SLAB, SBBlocks.DARK_SHALE_TILE_SLAB);
        stonecutterChain(output, SBBlocks.DARK_SHALE_STAIRS, SBBlocks.POLISHED_DARK_SHALE_STAIRS, SBBlocks.DARK_SHALE_BRICK_STAIRS, SBBlocks.DARK_SHALE_TILE_STAIRS);
        stonecutterChain(output, SBBlocks.DARK_SHALE_WALL, SBBlocks.POLISHED_DARK_SHALE_WALL, SBBlocks.DARK_SHALE_BRICK_WALL, SBBlocks.DARK_SHALE_TILE_WALL);

        stonecutting(output, RecipeCategory.BUILDING_BLOCKS, SBBlocks.CHISELED_SHALE, SBBlocks.SHALE, 1);
        stonecutting(output, RecipeCategory.BUILDING_BLOCKS, SBBlocks.CHISELED_DARK_SHALE, SBBlocks.DARK_SHALE, 1);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(SBBlocks.SHALE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, SBBlocks.CRACKED_SHALE_BRICKS.get(),
                        0.1F, 200)
                .unlockedBy(getHasName(SBBlocks.SHALE_BRICKS.get()), has(SBBlocks.SHALE_BRICKS.get()))
                .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(SBBlocks.DARK_SHALE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, SBBlocks.CRACKED_DARK_SHALE_BRICKS.get(),
                        0.1F, 200)
                .unlockedBy(getHasName(SBBlocks.DARK_SHALE_BRICKS.get()), has(SBBlocks.DARK_SHALE_BRICKS.get()))
                .save(output);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(SBBlocks.DARK_SHALE_TILES.get()), RecipeCategory.BUILDING_BLOCKS, SBBlocks.CRACKED_DARK_SHALE_TILES.get(),
                        0.1F, 200)
                .unlockedBy(getHasName(SBBlocks.DARK_SHALE_TILES.get()), has(SBBlocks.DARK_SHALE_TILES.get()))
                .save(output);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBBlocks.SEAWEED_CARPET.get(), 2)
                .requires(SBBlocks.SEAWEED_SLAB.get())
                .unlockedBy(getHasName(SBBlocks.SEAWEED_SLAB.get()), has(SBBlocks.SEAWEED_SLAB.get()))
                .save(output);
        carpet(output, SBBlocks.MISTWOOD_MOSS_CARPET, SBBlocks.MISTWOOD_MOSS_BLOCK);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SBBlocks.CORRODED_CONCRETE.get(), 8)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CCC")
                .define('C', Blocks.LIGHT_GRAY_CONCRETE)
                .define('S', SBItems.SALT.get())
                .unlockedBy(getHasName(SBBlocks.CORRODED_CONCRETE.get()), has(SBBlocks.CORRODED_CONCRETE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SBBlocks.ENGRAVED_DARK_SHALE.get(), 8)
                .pattern("CCC")
                .pattern("CSC")
                .pattern("CCC")
                .define('C', SBBlocks.CHISELED_DARK_SHALE.get())
                .define('S', SBItems.BOTTLE_OF_ICHOR.get())
                .unlockedBy(getHasName(SBBlocks.CHISELED_DARK_SHALE.get()), has(SBBlocks.CHISELED_DARK_SHALE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, SBBlocks.DARK_OBELISK.get(), 1)
                .pattern("E")
                .pattern("E")
                .define('E', SBBlocks.ENGRAVED_DARK_SHALE.get())
                .unlockedBy(getHasName(SBBlocks.ENGRAVED_DARK_SHALE.get()), has(SBBlocks.ENGRAVED_DARK_SHALE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.CRAB_POT.get(), 2)
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .define('S', ItemTags.WOODEN_SLABS)
                .define('C', Items.CHAIN)
                .unlockedBy(getHasName(SBItems.CRAB.get()), has(SBItems.CRAB.get()))
                .unlockedBy(getHasName(SBItems.LOBSTER.get()), has(SBItems.LOBSTER.get()))
                .unlockedBy(getHasName(SBBlocks.CRAB_POT.get()), has(SBBlocks.CRAB_POT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.WHALEBONE_TOTEM.get(), 1)
                .pattern("SIS")
                .pattern("RWR")
                .pattern("WAW")
                .define('S', SBItems.SALT)
                .define('I', SBItems.BOTTLE_OF_ICHOR)
                .define('R', SBItems.RUST)
                .define('W', SBItems.WHALEBONE)
                .define('A', SBItems.AMBERGRIS)
                .unlockedBy(getHasName(SBItems.WHALEBONE.get()), has(SBItems.WHALEBONE.get()))
                .unlockedBy(getHasName(SBItems.BOTTLE_OF_ICHOR.get()), has(SBItems.BOTTLE_OF_ICHOR.get()))
                .unlockedBy(getHasName(SBItems.AMBERGRIS.get()), has(SBItems.AMBERGRIS.get()))
                .save(output);

        chest(output, SBBlocks.ANCIENT_CHEST, SBBlocks.DARK_SHALE);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SBBlocks.CORRODED_LANTERN.get(), 1)
                .pattern(" R ")
                .pattern("RTR")
                .pattern(" R ")
                .define('R', SBItems.RUSTY_SCRAP)
                .define('T', Items.TORCH)
                .unlockedBy(getHasName(SBItems.RUSTY_SCRAP.get()), has(SBItems.RUSTY_SCRAP.get()))
                .unlockedBy(getHasName(SBBlocks.CORRODED_LANTERN.get()), has(SBBlocks.CORRODED_LANTERN.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SBBlocks.SEAGULL_NEST.get(), 1)
                .pattern("SFS")
                .pattern("SSS")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', Tags.Items.FEATHERS)
                .unlockedBy(getHasName(SBBlocks.SEAGULL_NEST.get()), has(SBBlocks.SEAGULL_NEST.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.OYSTERS.get(), 1)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', SBItems.OYSTER.get())
                .unlockedBy(getHasName(SBItems.OYSTER), has(SBItems.OYSTER))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.OYSTER.get(), 6)
                .requires(SBBlocks.OYSTERS.get())
                .unlockedBy(getHasName(SBBlocks.OYSTERS.get()), has(SBBlocks.OYSTERS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.BARNACLES.get(), 1)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', SBItems.BARNACLE.get())
                .unlockedBy(getHasName(SBItems.BARNACLE), has(SBItems.BARNACLE))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.BARNACLE.get(), 6)
                .requires(SBBlocks.BARNACLES.get())
                .unlockedBy(getHasName(SBBlocks.BARNACLES.get()), has(SBBlocks.BARNACLES.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBBlocks.DEAD_FISH_SLAB.get(), 1)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', SBItems.ROTTEN_FISH.get())
                .unlockedBy(getHasName(SBItems.ROTTEN_FISH), has(SBItems.ROTTEN_FISH))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.ROTTEN_FISH.get(), 6)
                .requires(SBBlocks.DEAD_FISH_SLAB.get())
                .unlockedBy(getHasName(SBBlocks.DEAD_FISH_SLAB.get()), has(SBBlocks.DEAD_FISH_SLAB.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBBlocks.BURIED_IRON_SCRAP.get(), 1)
                .requires(SBBlocks.SHINGLE)
                .requires(SBItems.IRON_SCRAP, 4)
                .unlockedBy(getHasName(SBItems.IRON_SCRAP.get()), has(SBItems.IRON_SCRAP.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBBlocks.BURIED_RUSTY_SCRAP.get(), 1)
                .requires(SBBlocks.SHINGLE)
                .requires(SBItems.RUSTY_SCRAP, 4)
                .unlockedBy(getHasName(SBItems.RUSTY_SCRAP.get()), has(SBItems.RUSTY_SCRAP.get()))
                .save(output);

        threeByThreePacker(output, RecipeCategory.BUILDING_BLOCKS, SBBlocks.IRON_SCRAP_BLOCK.get(), SBItems.IRON_SCRAP.get());
        threeByThreePacker(output, RecipeCategory.BUILDING_BLOCKS, SBBlocks.RUSTY_SCRAP_BLOCK.get(), SBItems.RUSTY_SCRAP.get());

        cookFood(output, SBItems.CRAB.get(), SBItems.COOKED_CRAB.get(), 0.3F, 200);
        cookFood(output, SBItems.LOBSTER.get(), SBItems.COOKED_LOBSTER.get(), 0.3F, 200);
        cookFood(output, SBItems.OYSTER.get(), SBItems.COOKED_OYSTER.get(), 0.3F, 200);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SBItems.STRANGE_MEAT.get(), 4)
                .requires(SBBlocks.ROTTING_FLESH_BLOCK.get())
                .unlockedBy(getHasName(SBBlocks.ROTTING_FLESH_BLOCK.get()), has(SBBlocks.ROTTING_FLESH_BLOCK.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.IRON_SCRAP.get(), 4)
                .pattern("II")
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.IRON_SCRAP.get(), 9)
                .requires(SBBlocks.IRON_SCRAP_BLOCK.get())
                .unlockedBy(getHasName(SBBlocks.IRON_SCRAP_BLOCK.get()), has(SBBlocks.IRON_SCRAP_BLOCK.get()))
                .save(output, ShoresBetween.MODID + ":" + "iron_scrap_unpacking");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.RUSTY_SCRAP.get(), 9)
                .requires(SBBlocks.RUSTY_SCRAP_BLOCK.get())
                .unlockedBy(getHasName(SBBlocks.RUSTY_SCRAP_BLOCK.get()), has(SBBlocks.RUSTY_SCRAP_BLOCK.get()))
                .save(output, ShoresBetween.MODID + ":" + "rusty_scrap_unpacking");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, SBItems.RUST.get(), 4)
                .pattern("B")
                .define('B', SBItems.RUSTY_SCRAP.get())
                .unlockedBy(getHasName(SBItems.RUSTY_SCRAP.get()), has(SBItems.RUSTY_SCRAP.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, SBItems.TITAN_MARROW.get(), 1)
                .requires(Items.NETHERITE_SCRAP, 4)
                .requires(SBItems.BOTTLE_OF_ICHOR.get())
                .requires(SBItems.RAW_TITAN_MARROW.get(), 4)
                .unlockedBy(getHasName(SBItems.RAW_TITAN_MARROW.get()), has(SBItems.RAW_TITAN_MARROW.get()))
                .save(output);

        copySmithingTemplate(output, SBItems.STRAND_ARMOR_TRIM_SMITHING_TEMPLATE.get(), SBBlocks.SHINGLE);

    }


    private void woodSet(RecipeOutput output, DeferredBlock<Block> log, TagKey<Item> logs, DeferredBlock<Block> wood, DeferredBlock<Block> strippedLog, DeferredBlock<Block> strippedWood, DeferredBlock<Block> planks, DeferredBlock<Block> slab, DeferredBlock<Block> stairs, DeferredBlock<Block> door, DeferredBlock<Block> trapdoor, DeferredBlock<Block> fence, DeferredBlock<Block> fenceGate, DeferredBlock<Block> button, DeferredBlock<Block> plate, DeferredBlock<Block> ladder, DeferredItem<Item> sign, DeferredItem<Item> hangingSign, DeferredBlock<Block> chest, DeferredBlock<Block> barrel) {
        //Wood
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood.get(), 3).pattern("MM").pattern("MM").define('M', log.get()).unlockedBy(getHasName(log.get()), has(log.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, strippedWood.get(), 3).pattern("MM").pattern("MM").define('M', strippedLog.get()).unlockedBy(getHasName(log.get()), has(strippedLog.get())).save(output);
        
        //Planks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks.get(), 4).requires(logs).group("planks").unlockedBy("has_log", has(logs)).save(output);

        //Slab
        slab(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), planks.get());

        //Stairs
        stairs(output, stairs.get(), planks.get());

        //Door
        door(output, door.get(), planks.get());

        //Trapdoor
        trapdoor(output, trapdoor.get(), planks.get());

        //Fence
        fence(output, fence.get(), planks.get());

        //Fence gate
        fenceGate(output, fenceGate.get(), planks.get());

        //Button
        button(output, button.get(), planks.get());

        //Pressure plate
        pressurePlate(output, plate.get(), planks.get());

        //Ladder
        ladder(output, ladder.get(), planks.get());

        //Signs
        sign(output, sign.get(), planks.get());
        hangingSign(output, hangingSign.get(), strippedLog.get());
        
        //Chest
        chest(output, chest.get(), planks.get());
        
        //Barrel
        barrel(output, barrel.get(), planks.get(), slab.get());
        
    }

    private void petrifiedSet(RecipeOutput output, DeferredBlock<Block> log, TagKey<Item> logs, DeferredBlock<Block> wood, DeferredBlock<Block> strippedLog, DeferredBlock<Block> strippedWood, DeferredBlock<Block> planks, DeferredBlock<Block> slab, DeferredBlock<Block> stairs) {
        //Wood
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood.get(), 3).pattern("MM").pattern("MM").define('M', log.get()).unlockedBy(getHasName(log.get()), has(log.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, strippedWood.get(), 3).pattern("MM").pattern("MM").define('M', strippedLog.get()).unlockedBy(getHasName(log.get()), has(strippedLog.get())).save(output);

        //Planks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks.get(), 4).requires(logs).group("planks").unlockedBy("has_log", has(logs)).save(output);

        //Slab
        slab(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), planks.get());

        //Stairs
        stairs(output, stairs.get(), planks.get());
    }

    @SafeVarargs
    private static void stoneSet(RecipeOutput output, DeferredBlock<Block> parent, DeferredBlock<Block> slab, DeferredBlock<Block> stairs, DeferredBlock<Block> wall, DeferredBlock<Block>... ancestors) {

        slab(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), parent.get());
        stairs(output, stairs.get(), parent.get());
        wall(output, RecipeCategory.BUILDING_BLOCKS, wall.get(), parent.get());

        stonecutting(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), parent.get(), 2);
        stonecutting(output, RecipeCategory.BUILDING_BLOCKS, stairs.get(), parent.get(), 1);
        stonecutting(output, RecipeCategory.BUILDING_BLOCKS, wall.get(), parent.get(), 1);

        for (DeferredBlock<Block> ancestor : ancestors) {
            stonecutting(output, RecipeCategory.BUILDING_BLOCKS, slab.get(), ancestor.get(), 2);
            stonecutting(output, RecipeCategory.BUILDING_BLOCKS, stairs.get(), ancestor.get(), 1);
            stonecutting(output, RecipeCategory.BUILDING_BLOCKS, wall.get(), ancestor.get(), 1);
        }

    }

    protected static void slab(RecipeOutput recipeOutput, RecipeCategory recipeCategory, ItemLike slab, ItemLike material) {
        slabBuilder(recipeCategory, slab, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void stairs(RecipeOutput recipeOutput, ItemLike stair, ItemLike material) {
        stairBuilder(stair, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void door(RecipeOutput recipeOutput, ItemLike door, ItemLike material) {
        doorBuilder(door, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void trapdoor(RecipeOutput recipeOutput, ItemLike trapdoor, ItemLike material) {
        trapdoorBuilder(trapdoor, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void fence(RecipeOutput recipeOutput, ItemLike fence, ItemLike material) {
        fenceBuilder(fence, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void fenceGate(RecipeOutput recipeOutput, ItemLike fenceGate, ItemLike material) {
        fenceGateBuilder(fenceGate, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void button(RecipeOutput recipeOutput, ItemLike button, ItemLike material) {
        buttonBuilder(button, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void pressurePlate(RecipeOutput recipeOutput, ItemLike pressurePlate, ItemLike material) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, pressurePlate, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    protected static void ladder(RecipeOutput recipeOutput, ItemLike ladder, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ladder, 4)
                .group("sign")
                .define('#', material)
                .define('X', Items.STICK)
                .pattern("X X")
                .pattern("X#X")
                .pattern("X X")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void sign(RecipeOutput recipeOutput, ItemLike pSign, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pSign, 3)
                .group("sign")
                .define('#', material)
                .define('X', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void hangingSign(RecipeOutput recipeOutput, ItemLike sign, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, sign, 6)
                .group("hanging_sign")
                .define('#', material)
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(material))
                .save(recipeOutput);
    }

    protected static void chest(RecipeOutput recipeOutput, ItemLike chest, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, chest, 1)
                .group("chest")
                .define('#', material)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void barrel(RecipeOutput recipeOutput, ItemLike barrel, ItemLike material, ItemLike slab) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, barrel, 1)
                .group("chest")
                .define('#', material)
                .define('_', slab)
                .pattern("#_#")
                .pattern("# #")
                .pattern("#_#")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput);
    }

    protected static void wall(RecipeOutput recipeOutput, RecipeCategory recipeCategory, ItemLike wall, ItemLike material) {
        wallBuilder(recipeCategory, wall, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    private static void stoneConversion(RecipeOutput recipeOutput, ItemLike parent, ItemLike child, ItemLike... ancestors) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, child, 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', parent)
                .unlockedBy(getHasName(parent), has(parent))
                .save(recipeOutput);

        stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, child, parent, 1);

        for (ItemLike ancestor : ancestors) {
            stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, child, ancestor, 1);
        }
    }
    
    private static void stonecutterChain(RecipeOutput recipeOutput, ItemLike... items) {
        for (int i = 1; i < items.length; i++) {
            for (int j = 0; j < i; j++) {
                stonecutting(recipeOutput, RecipeCategory.BUILDING_BLOCKS, items[i], items[j], 1);
            }
        }
    }

    private static void cookFood(RecipeOutput recipeOutput, ItemLike raw, ItemLike cooked, float experience, int cookingTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.FOOD, cooked,
                        experience, cookingTime)
                .unlockedBy(getHasName(raw), has(raw))
                .save(recipeOutput, ShoresBetween.MODID + ":" + getItemName(cooked) + "_from_smelting_" + getItemName(raw));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(raw), RecipeCategory.FOOD, cooked,
                        experience, cookingTime / 2)
                .unlockedBy(getHasName(raw), has(raw))
                .save(recipeOutput, ShoresBetween.MODID + ":" + getItemName(cooked) + "_from_smoking_" + getItemName(raw));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(raw), RecipeCategory.FOOD, cooked,
                        experience, cookingTime)
                .unlockedBy(getHasName(raw), has(raw))
                .save(recipeOutput, ShoresBetween.MODID + ":" + getItemName(cooked) + "_from_campfire_" + getItemName(raw));
    }

    protected static void stonecutting(RecipeOutput recipeOutput, RecipeCategory recipeCategory, ItemLike material, ItemLike pMaterial, int resultCount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(pMaterial), recipeCategory, material, resultCount)
                .unlockedBy(getHasName(pMaterial), has(pMaterial))
                .save(recipeOutput, ShoresBetween.MODID + ":" + getConversionRecipeName(material, pMaterial) + "_stonecutting");
    }


}
