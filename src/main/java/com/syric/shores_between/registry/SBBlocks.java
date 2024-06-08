package com.syric.shores_between.registry;

import com.syric.shores_between.block.BreachPortalBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final DeferredBlock<Block> PORTAL_BLOCK = registerWithItem("portal_block", () -> new BreachPortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    //Beach blocks
    public static final DeferredBlock<Block> SHINGLE = registerWithItem("shingle",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> TANGLED_SHINGLE = registerWithItem("tangled_shingle",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PEBBLES = registerWithItem("pebbles",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> GRASSY_SHINGLE = registerWithItem("grassy_shingle",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Stone blocks
    public static final DeferredBlock<Block> SHALE = registerWithItem("shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DARK_SHALE = registerWithItem("dark_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> OVERGROWN_SHALE = registerWithItem("overgrown_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> SALTSTONE = registerWithItem("saltstone",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Wood and tree blocks
    //Driftwood set
    public static final DeferredBlock<Block> DRIFTWOOD_LOG = registerWithItem("driftwood_log",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_WOOD = registerWithItem("driftwood_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_DRIFTWOOD_LOG = registerWithItem("stripped_driftwood_log",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_DRIFTWOOD_WOOD = registerWithItem("stripped_driftwood_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_PLANKS = registerWithItem("driftwood_planks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_SLAB = registerWithItem("driftwood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_STAIRS = registerWithItem("driftwood_stairs",
            () -> new StairBlock(SBBlocks.DRIFTWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_DOOR = registerWithItem("driftwood_door",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_TRAPDOOR = registerWithItem("driftwood_trapdoor",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_FENCE = registerWithItem("driftwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_FENCE_GATE = registerWithItem("driftwood_fence_gate",
            () -> new FenceGateBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_BUTTON = registerWithItem("driftwood_button",
            () -> new ButtonBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, 10, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_PRESSURE_PLATE = registerWithItem("driftwood_pressure_plate",
            () -> new PressurePlateBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_SIGN = registerNoItem("driftwood_sign",
            () -> new StandingSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_WALL_SIGN = registerNoItem("driftwood_wall_sign",
            () -> new WallSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_HANGING_SIGN = registerNoItem("driftwood_hanging_sign",
            () -> new CeilingHangingSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_WALL_HANGING_SIGN = registerNoItem("driftwood_wall_hanging_sign",
            () -> new WallHangingSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_LADDER = registerWithItem("driftwood_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.of()));
    //Petrified set
    public static final DeferredBlock<Block> PETRIFIED_LOG = registerWithItem("petrified_log",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PETRIFIED_WOOD = registerWithItem("petrified_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_PETRIFIED_LOG = registerWithItem("stripped_petrified_log",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_PETRIFIED_WOOD = registerWithItem("stripped_petrified_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())));
    public static final DeferredBlock<Block> PETRIFIED_PLANKS = registerWithItem("petrified_planks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PETRIFIED_SLAB = registerWithItem("petrified_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_PLANKS.get())));
    public static final DeferredBlock<Block> PETRIFIED_STAIRS = registerWithItem("petrified_stairs",
            () -> new StairBlock(SBBlocks.PETRIFIED_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(PETRIFIED_PLANKS.get())));
    //Mistwood set
    public static final DeferredBlock<Block> MISTWOOD_LOG = registerWithItem("mistwood_log",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_WOOD = registerWithItem("mistwood_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_MISTWOOD_LOG = registerWithItem("stripped_mistwood_log",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get())));
    public static final DeferredBlock<Block> STRIPPED_MISTWOOD_WOOD = registerWithItem("stripped_mistwood_wood",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get())));
    public static final DeferredBlock<Block> MISTWOOD_PLANKS = registerWithItem("mistwood_planks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_SLAB = registerWithItem("mistwood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> MISTWOOD_STAIRS = registerWithItem("mistwood_stairs",
            () -> new StairBlock(SBBlocks.MISTWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(MISTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> MISTWOOD_DOOR = registerWithItem("mistwood_door",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_TRAPDOOR = registerWithItem("mistwood_trapdoor",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_FENCE = registerWithItem("mistwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_FENCE_GATE = registerWithItem("mistwood_fence_gate",
            () -> new FenceGateBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_BUTTON = registerWithItem("mistwood_button",
            () -> new ButtonBlock(SBWoodTypes.MISTWOOD_WOOD_SET, 10, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_PRESSURE_PLATE = registerWithItem("mistwood_pressure_plate",
            () -> new PressurePlateBlock(SBWoodTypes.MISTWOOD_WOOD_SET, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_SIGN = registerNoItem("mistwood_sign",
            () -> new StandingSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_WALL_SIGN = registerNoItem("mistwood_wall_sign",
            () -> new WallSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_HANGING_SIGN = registerNoItem("mistwood_hanging_sign",
            () -> new CeilingHangingSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_WALL_HANGING_SIGN = registerNoItem("mistwood_wall_hanging_sign",
            () -> new WallHangingSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_LADDER = registerWithItem("mistwood_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_LEAVES = registerWithItem("mistwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_SAPLING = registerWithItem("mistwood_sapling",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Corpse blocks
    public static final DeferredBlock<Block> ROTTING_FLESH_BLOCK = registerWithItem("rotting_flesh_block",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> INFESTED_FLESH_BLOCK = registerWithItem("infested_flesh_block",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BLOATED_FLESH = registerWithItem("bloated_flesh",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MUMMIFIED_FLESH = registerWithItem("mummified_flesh",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> LEVIATHAN_BONE = registerWithItem("leviathan_bone",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> LEVIATHAN_KERATIN = registerWithItem("leviathan_keratin",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> JELLIED_ICHOR = registerWithItem("jellied_ichor",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> TITAN_BONE = registerWithItem("titan_bone",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CORRODED_TITAN_BONE = registerWithItem("corroded_titan_bone",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Building blocks
        //Shale building set
    public static final DeferredBlock<Block> SHALE_SLAB = registerWithItem("shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SHALE.get())));
    public static final DeferredBlock<Block> SHALE_STAIRS = registerWithItem("shale_stairs",
            () -> new StairBlock(SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(SHALE.get())));
    public static final DeferredBlock<Block> SHALE_WALL = registerWithItem("shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SHALE.get())));

        //Polished shale building set
    public static final DeferredBlock<Block> POLISHED_SHALE = registerWithItem("polished_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> POLISHED_SHALE_SLAB = registerWithItem("polished_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_SHALE_STAIRS = registerWithItem("polished_shale_stairs",
            () -> new StairBlock(POLISHED_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_SHALE_WALL = registerWithItem("polished_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())));

        //Shale bricks building set
    public static final DeferredBlock<Block> SHALE_BRICKS = registerWithItem("shale_bricks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CRACKED_SHALE_BRICKS = registerWithItem("cracked_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_SLAB = registerWithItem("shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_STAIRS = registerWithItem("shale_brick_stairs",
            () -> new StairBlock(SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_WALL = registerWithItem("shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));

        //Mossy shale bricks building set
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICKS = registerWithItem("mossy_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_SLAB = registerWithItem("mossy_shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_STAIRS = registerWithItem("mossy_shale_brick_stairs",
            () -> new StairBlock(MOSSY_SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_WALL = registerWithItem("mossy_shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())));   
    
        //Dark shale building set
    public static final DeferredBlock<Block> DARK_SHALE_SLAB = registerWithItem("dark_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())));
    public static final DeferredBlock<Block> DARK_SHALE_STAIRS = registerWithItem("dark_shale_stairs",
            () -> new StairBlock(DARK_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())));
    public static final DeferredBlock<Block> DARK_SHALE_WALL = registerWithItem("dark_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())));

        //Polished dark shale building set
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE = registerWithItem("polished_dark_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_SLAB = registerWithItem("polished_dark_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_STAIRS = registerWithItem("polished_dark_shale_stairs",
            () -> new StairBlock(POLISHED_DARK_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_WALL = registerWithItem("polished_dark_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())));

        //Dark shale bricks building set
    public static final DeferredBlock<Block> DARK_SHALE_BRICKS = registerWithItem("dark_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CRACKED_DARK_SHALE_BRICKS = registerWithItem("cracked_dark_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_SLAB = registerWithItem("dark_shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_STAIRS = registerWithItem("dark_shale_brick_stairs",
            () -> new StairBlock(DARK_SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_WALL = registerWithItem("dark_shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));

        //Dark shale tile building set
    public static final DeferredBlock<Block> DARK_SHALE_TILES = registerWithItem("dark_shale_tiles",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CRACKED_DARK_SHALE_TILES = registerWithItem("cracked_dark_shale_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_SLAB = registerWithItem("dark_shale_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_STAIRS = registerWithItem("dark_shale_tile_stairs",
            () -> new StairBlock(DARK_SHALE_TILES.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_WALL = registerWithItem("dark_shale_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));

        //Chiseled shale and dark shale
    public static final DeferredBlock<Block> CHISELED_SHALE = registerWithItem("chiseled_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CHISELED_DARK_SHALE = registerWithItem("chiseled_dark_shale",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Flora blocks
    public static final DeferredBlock<Block> DUNEGRASS = registerWithItem("dunegrass",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> SEAWEED_SLAB = registerWithItem("seaweed_slab",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> SEAWEED_CARPET = registerWithItem("seaweed_carpet",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_MOSS_CARPET = registerWithItem("mistwood_moss_carpet",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_MOSS_BLOCK = registerWithItem("mistwood_moss_block",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Ruin blocks
    public static final DeferredBlock<Block> CORRODED_CONCRETE = registerWithItem("corroded_concrete",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> SUSPICIOUS_SHINGLE = registerWithItem("suspicious_shingle",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> ENGRAVED_DARK_SHALE = registerWithItem("engraved_dark_shale",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DARK_OBELISK = registerWithItem("dark_obelisk",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Functional blocks
    public static final DeferredBlock<Block> CRAB_POT = registerWithItem("crab_pot",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> WHALEBONE_TOTEM = registerWithItem("whalebone_totem",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_CHEST = registerWithItem("driftwood_chest",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DRIFTWOOD_BARREL = registerWithItem("driftwood_barrel",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_CHEST = registerWithItem("mistwood_chest",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MISTWOOD_BARREL = registerWithItem("mistwood_barrel",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> ANCIENT_CHEST = registerWithItem("ancient_chest",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CORRODED_LANTERN = registerWithItem("corroded_lantern",
            () -> new Block(BlockBehaviour.Properties.of()));


    //Miscellaneous
    public static final DeferredBlock<Block> SEAGULL_NEST = registerWithItem("seagull_nest",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> OYSTERS = registerWithItem("oysters",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BARNACLES = registerWithItem("barnacles",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> DEAD_FISH_SLAB = registerWithItem("dead_fish_slab",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BURIED_IRON_SCRAP = registerWithItem("buried_iron_scrap",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BURIED_RUSTY_SCRAP = registerWithItem("buried_rusty_scrap",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> IRON_SCRAP_BLOCK = registerWithItem("iron_scrap_block",
            () -> new Block(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> RUSTY_SCRAP_BLOCK = registerWithItem("rusty_scrap_block",
            () -> new Block(BlockBehaviour.Properties.of()));


    private static DeferredBlock<Block> registerNoItem(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }
    private static DeferredBlock<Block> registerWithItem(String name, Supplier<? extends Block> block) {
        DeferredBlock<Block> ret = registerNoItem(name, block);
        SBItems.ITEMS.registerSimpleBlockItem(ret);
        return ret;
    }

}
