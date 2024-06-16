package com.syric.shores_between.registry;

import com.syric.shores_between.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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

    public static final DeferredBlock<Block> PORTAL_BLOCK = registerWithItem("portal_block",
            () -> new BreachPortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).noLootTable()));

    public static final DeferredBlock<Block> DEFAULT_BLOCK = registerWithItem("default_block",
            () -> new Block(BlockBehaviour.Properties.of()));

    //Beach blocks
    //TODO - alter color for all blocks here
    public static final DeferredBlock<Block> SHINGLE = registerWithItem("shingle",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).sound(SoundType.BASALT)));
    //TODO - tangled shingle should become regular shingle when broken
    public static final DeferredBlock<Block> TANGLED_SHINGLE = registerWithItem("tangled_shingle",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).sound(SoundType.BASALT)));
    public static final DeferredBlock<Block> PEBBLES = registerWithItem("pebbles",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL)));
    //TODO - grassy shingle should become regular shingle when it falls
    //TODO - grassy shingle should have hybrid grass / shingle sounds
    //TODO - grassy shingle needs a grassy model/texture
    public static final DeferredBlock<Block> GRASSY_SHINGLE = registerWithItem("grassy_shingle",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).sound(SoundType.MOSS).mapColor(MapColor.GRASS)));


    //Stone blocks
    //TODO - shale and dark shale need rotated pillar models/textures
    public static final DeferredBlock<Block> SHALE = registerWithItem("shale",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).sound(SoundType.TUFF).strength(1.3F, 3.0F)));
    public static final DeferredBlock<Block> DARK_SHALE = registerWithItem("dark_shale",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(2.5F, 6.0F)));
    //TODO - overgrown shale should turn to shale when something's on top
    //TODO - overgrown shale should have hybrid grass / shale sounds
    //TODO - overgrown shale needs a grassy model/texture
    public static final DeferredBlock<Block> OVERGROWN_SHALE = registerWithItem("overgrown_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE.get()).mapColor(MapColor.GRASS).randomTicks()));
    public static final DeferredBlock<Block> SALTSTONE = registerWithItem("saltstone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.SNARE)
                    .sound(SoundType.TUFF)
                    .requiresCorrectToolForDrops()
                    .strength(0.3F)
                    .isRedstoneConductor(SBBlocks::never)));


    //Wood and tree blocks
    //Driftwood set
    public static final DeferredBlock<Block> DRIFTWOOD_LOG = registerWithItem("driftwood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_WHITE : MapColor.RAW_IRON)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> DRIFTWOOD_WOOD = registerWithItem("driftwood_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get()).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> STRIPPED_DRIFTWOOD_LOG = registerWithItem("stripped_driftwood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get()).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> STRIPPED_DRIFTWOOD_WOOD = registerWithItem("stripped_driftwood_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_LOG.get()).mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_PLANKS = registerWithItem("driftwood_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
            ));
    public static final DeferredBlock<Block> DRIFTWOOD_SLAB = registerWithItem("driftwood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_STAIRS = registerWithItem("driftwood_stairs",
            () -> new StairBlock(SBBlocks.DRIFTWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_DOOR = registerWithItem("driftwood_door",
            () -> new DoorBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_TRAPDOOR = registerWithItem("driftwood_trapdoor",
            () -> new TrapDoorBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_FENCE = registerWithItem("driftwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_FENCE_GATE = registerWithItem("driftwood_fence_gate",
            () -> new FenceGateBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_BUTTON = registerWithItem("driftwood_button",
            () -> new ButtonBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> DRIFTWOOD_PRESSURE_PLATE = registerWithItem("driftwood_pressure_plate",
            () -> new PressurePlateBlock(SBWoodTypes.DRIFTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    public static final DeferredBlock<Block> DRIFTWOOD_SIGN = registerNoItem("driftwood_sign",
            () -> new SBSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> DRIFTWOOD_WALL_SIGN = registerNoItem("driftwood_wall_sign",
            () -> new SBWallSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_SIGN.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_HANGING_SIGN = registerNoItem("driftwood_hanging_sign",
            () -> new SBHangingSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_SIGN.get())
                    .mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> DRIFTWOOD_WALL_HANGING_SIGN = registerNoItem("driftwood_wall_hanging_sign",
            () -> new SBWallHangingSignBlock(SBWoodTypes.DRIFTWOOD, BlockBehaviour.Properties.ofFullCopy(DRIFTWOOD_HANGING_SIGN.get())));
    public static final DeferredBlock<Block> DRIFTWOOD_LADDER = registerWithItem("driftwood_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LADDER)));

    //Petrified set
    public static final DeferredBlock<Block> PETRIFIED_LOG = registerWithItem("petrified_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_ANDESITE)
                    .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_LIGHT_GRAY : MapColor.TERRACOTTA_BROWN)));
    public static final DeferredBlock<Block> PETRIFIED_WOOD = registerWithItem("petrified_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())
                    .mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final DeferredBlock<Block> STRIPPED_PETRIFIED_LOG = registerWithItem("stripped_petrified_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())
                    .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<Block> STRIPPED_PETRIFIED_WOOD = registerWithItem("stripped_petrified_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_LOG.get())
                    .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<Block> PETRIFIED_PLANKS = registerWithItem("petrified_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ANDESITE)
                    .mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<Block> PETRIFIED_SLAB = registerWithItem("petrified_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PETRIFIED_PLANKS.get())));
    public static final DeferredBlock<Block> PETRIFIED_STAIRS = registerWithItem("petrified_stairs",
            () -> new StairBlock(SBBlocks.PETRIFIED_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(PETRIFIED_PLANKS.get())));
    //Mistwood set
    public static final DeferredBlock<Block> MISTWOOD_LOG = registerWithItem("mistwood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(blockState -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.COLOR_BROWN : MapColor.TERRACOTTA_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> MISTWOOD_WOOD = registerWithItem("mistwood_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get()).mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final DeferredBlock<Block> STRIPPED_MISTWOOD_LOG = registerWithItem("stripped_mistwood_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get()).mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> STRIPPED_MISTWOOD_WOOD = registerWithItem("stripped_mistwood_wood",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_LOG.get()).mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_PLANKS = registerWithItem("mistwood_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
            ));
    public static final DeferredBlock<Block> MISTWOOD_SLAB = registerWithItem("mistwood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MISTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> MISTWOOD_STAIRS = registerWithItem("mistwood_stairs",
            () -> new StairBlock(SBBlocks.MISTWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(MISTWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> MISTWOOD_DOOR = registerWithItem("mistwood_door",
            () -> new DoorBlock(SBWoodTypes.MISTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
                    .mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_TRAPDOOR = registerWithItem("mistwood_trapdoor",
            () -> new TrapDoorBlock(SBWoodTypes.MISTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                    .mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_FENCE = registerWithItem("mistwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
                    .mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_FENCE_GATE = registerWithItem("mistwood_fence_gate",
            () -> new FenceGateBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
                    .mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_BUTTON = registerWithItem("mistwood_button",
            () -> new ButtonBlock(SBWoodTypes.MISTWOOD_WOOD_SET, 10, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final DeferredBlock<Block> MISTWOOD_PRESSURE_PLATE = registerWithItem("mistwood_pressure_plate",
            () -> new PressurePlateBlock(SBWoodTypes.MISTWOOD_WOOD_SET, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
                    .mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_SIGN = registerNoItem("mistwood_sign",
            () -> new SBSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollission()
                    .strength(1.0F)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> MISTWOOD_WALL_SIGN = registerNoItem("mistwood_wall_sign",
            () -> new SBWallSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.ofFullCopy(MISTWOOD_SIGN.get())));
    public static final DeferredBlock<Block> MISTWOOD_HANGING_SIGN = registerNoItem("mistwood_hanging_sign",
            () -> new SBHangingSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.ofFullCopy(MISTWOOD_SIGN.get())
                    .mapColor(MapColor.TERRACOTTA_BROWN)));
    public static final DeferredBlock<Block> MISTWOOD_WALL_HANGING_SIGN = registerNoItem("mistwood_wall_hanging_sign",
            () -> new SBWallHangingSignBlock(SBWoodTypes.MISTWOOD, BlockBehaviour.Properties.ofFullCopy(MISTWOOD_HANGING_SIGN.get())));
    public static final DeferredBlock<Block> MISTWOOD_LADDER = registerWithItem("mistwood_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LADDER)));
    public static final DeferredBlock<Block> MISTWOOD_LEAVES = registerWithItem("mistwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES)));
    public static final DeferredBlock<Block> MISTWOOD_SAPLING = registerWithItem("mistwood_sapling",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING)));


    //Corpse blocks
    public static final DeferredBlock<Block> ROTTING_FLESH_BLOCK = registerWithItem("rotting_flesh_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .friction(0.9F)
                    .speedFactor(0.9F)
                    .jumpFactor(0.8F)
                    .sound(SoundType.HONEY_BLOCK)
                    .strength(0.5F)));
    public static final DeferredBlock<Block> INFESTED_FLESH_BLOCK = registerWithItem("infested_flesh_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ROTTING_FLESH_BLOCK.get())));
    public static final DeferredBlock<Block> BLOATED_FLESH = registerWithItem("bloated_flesh",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_MAGENTA)
                    .friction(0.8F)
                    .speedFactor(0.8F)
                    .jumpFactor(0.7F)
                    .sound(SoundType.MUD)
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> MUMMIFIED_FLESH = registerWithItem("mummified_flesh",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .sound(SoundType.SPONGE)
                    .strength(1F, 5)));
    public static final DeferredBlock<Block> LEVIATHAN_BONE = registerWithItem("leviathan_bone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .sound(SoundType.BONE_BLOCK)
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .strength(2.5F, 30)
                    .requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> LEVIATHAN_KERATIN = registerWithItem("leviathan_keratin",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .requiresCorrectToolForDrops()
                    .strength(2.5F, 30)));
    public static final DeferredBlock<Block> JELLIED_ICHOR = registerWithItem("jellied_ichor",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .sound(SoundType.HONEY_BLOCK)
                    .requiresCorrectToolForDrops()
                    .strength(0.5F)
                    .pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> TITAN_BONE = registerWithItem("titan_bone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BLACK)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .noLootTable()
                    .strength(-1.0F, 3600000.0F)
                    .pushReaction(PushReaction.BLOCK)));
    //TODO - corroded titan bone should become titan bone when it breaks
    public static final DeferredBlock<Block> CORRODED_TITAN_BONE = registerWithItem("corroded_titan_bone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_BLACK)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .requiresCorrectToolForDrops()
                    .strength(50.0F, 1200.0F)
                    .pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> ABERRANT_SHELL = registerWithItem("aberrant_shell",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .noLootTable()
                    .strength(-1.0F, 3600000.0F)
                    .pushReaction(PushReaction.BLOCK)));


    //Building blocks
        //Shale building set
    public static final DeferredBlock<Block> SHALE_SLAB = registerWithItem("shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SHALE.get())));
    public static final DeferredBlock<Block> SHALE_STAIRS = registerWithItem("shale_stairs",
            () -> new StairBlock(SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(SHALE.get())));
    public static final DeferredBlock<Block> SHALE_WALL = registerWithItem("shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SHALE.get())
                    .forceSolidOn()));

        //Polished shale building set
    public static final DeferredBlock<Block> POLISHED_SHALE = registerWithItem("polished_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE.get())
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.POLISHED_TUFF)));
    public static final DeferredBlock<Block> POLISHED_SHALE_SLAB = registerWithItem("polished_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_SHALE_STAIRS = registerWithItem("polished_shale_stairs",
            () -> new StairBlock(POLISHED_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_SHALE_WALL = registerWithItem("polished_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())
                    .forceSolidOn()));

        //Shale bricks building set
    public static final DeferredBlock<Block> SHALE_BRICKS = registerWithItem("shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(POLISHED_SHALE.get())
                    .sound(SoundType.TUFF_BRICKS)));
    public static final DeferredBlock<Block> CRACKED_SHALE_BRICKS = registerWithItem("cracked_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_SLAB = registerWithItem("shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_STAIRS = registerWithItem("shale_brick_stairs",
            () -> new StairBlock(SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> SHALE_BRICK_WALL = registerWithItem("shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())
                    .forceSolidOn()));

        //Mossy shale bricks building set
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICKS = registerWithItem("mossy_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_SLAB = registerWithItem("mossy_shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_STAIRS = registerWithItem("mossy_shale_brick_stairs",
            () -> new StairBlock(MOSSY_SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> MOSSY_SHALE_BRICK_WALL = registerWithItem("mossy_shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(MOSSY_SHALE_BRICKS.get())
                    .forceSolidOn()));
    
        //Dark shale building set
    public static final DeferredBlock<Block> DARK_SHALE_SLAB = registerWithItem("dark_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())));
    public static final DeferredBlock<Block> DARK_SHALE_STAIRS = registerWithItem("dark_shale_stairs",
            () -> new StairBlock(DARK_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())));
    public static final DeferredBlock<Block> DARK_SHALE_WALL = registerWithItem("dark_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())
                    .forceSolidOn()));

        //Polished dark shale building set
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE = registerWithItem("polished_dark_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE.get())
                    .strength(3.0F, 6.0F)
                    .sound(SoundType.POLISHED_DEEPSLATE)));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_SLAB = registerWithItem("polished_dark_shale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_STAIRS = registerWithItem("polished_dark_shale_stairs",
            () -> new StairBlock(POLISHED_DARK_SHALE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())));
    public static final DeferredBlock<Block> POLISHED_DARK_SHALE_WALL = registerWithItem("polished_dark_shale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())
                    .forceSolidOn()));

        //Dark shale bricks building set
    public static final DeferredBlock<Block> DARK_SHALE_BRICKS = registerWithItem("dark_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())
                    .sound(SoundType.DEEPSLATE_BRICKS)));
    public static final DeferredBlock<Block> CRACKED_DARK_SHALE_BRICKS = registerWithItem("cracked_dark_shale_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_SLAB = registerWithItem("dark_shale_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_STAIRS = registerWithItem("dark_shale_brick_stairs",
            () -> new StairBlock(DARK_SHALE_BRICKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> DARK_SHALE_BRICK_WALL = registerWithItem("dark_shale_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())
                    .forceSolidOn()));

        //Dark shale tile building set
    public static final DeferredBlock<Block> DARK_SHALE_TILES = registerWithItem("dark_shale_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get())
                    .sound(SoundType.DEEPSLATE_TILES)));
    public static final DeferredBlock<Block> CRACKED_DARK_SHALE_TILES = registerWithItem("cracked_dark_shale_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_SLAB = registerWithItem("dark_shale_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_STAIRS = registerWithItem("dark_shale_tile_stairs",
            () -> new StairBlock(DARK_SHALE_TILES.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())));
    public static final DeferredBlock<Block> DARK_SHALE_TILE_WALL = registerWithItem("dark_shale_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_TILES.get())
                    .forceSolidOn()));

        //Chiseled shale and dark shale
    public static final DeferredBlock<Block> CHISELED_SHALE = registerWithItem("chiseled_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SHALE_BRICKS.get())));
    public static final DeferredBlock<Block> CHISELED_DARK_SHALE = registerWithItem("chiseled_dark_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(DARK_SHALE_BRICKS.get())));


    //Flora blocks
    public static final DeferredBlock<Block> DUNEGRASS = registerWithItem("dunegrass",
            () -> new TallGrassBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)));
    //Exists solely to be a model for doubleslabs
    public static final DeferredBlock<Block> SEAWEED_BLOCK = registerWithItem("seaweed_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .sound(SoundType.WET_GRASS)
                    .friction(0.8F)));
    public static final DeferredBlock<Block> SEAWEED_SLAB = registerWithItem("seaweed_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .sound(SoundType.WET_GRASS)
                    .friction(0.8F)));
    public static final DeferredBlock<Block> SEAWEED_CARPET = registerWithItem("seaweed_carpet",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET)
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .sound(SoundType.WET_GRASS)
                    .friction(0.8F)));
    public static final DeferredBlock<Block> MISTWOOD_MOSS_CARPET = registerWithItem("mistwood_moss_carpet",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET)));
    public static final DeferredBlock<Block> MISTWOOD_MOSS_BLOCK = registerWithItem("mistwood_moss_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)));


    //Ruin blocks
    public static final DeferredBlock<Block> CORRODED_CONCRETE = registerWithItem("corroded_concrete",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.LIGHT_GRAY_CONCRETE)
                    .sound(SoundType.BASALT)
                    .strength(1.4F)));
    public static final DeferredBlock<Block> SUSPICIOUS_SHINGLE = registerWithItem("suspicious_shingle",
            () -> new BrushableBlock(
                    SHINGLE.get(),
                    SoundEvents.BRUSH_GRAVEL,
                    SoundEvents.BRUSH_GRAVEL_COMPLETED,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.SUSPICIOUS_GRAVEL)));
    public static final DeferredBlock<Block> ENGRAVED_DARK_SHALE = registerWithItem("engraved_dark_shale",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(CHISELED_DARK_SHALE.get())
                    .strength(40.0F, 600.0F)
                    .lightLevel(state -> 5)));
    //TODO - needs to be a double block somehow!
    public static final DeferredBlock<Block> DARK_OBELISK = registerWithItem("dark_obelisk",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(ENGRAVED_DARK_SHALE.get())));


    //Functional blocks
    //TODO - needs custom model + texture
    public static final DeferredBlock<Block> CRAB_POT = registerWithItem("crab_pot",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sound(SoundType.CHAIN)
                    .ignitedByLava()));
    public static final DeferredBlock<Block> WHALEBONE_TOTEM = registerWithItem("whalebone_totem",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .forceSolidOn()
                    .strength(1.5F, 3.0F)
                    .sound(SoundType.TRIAL_SPAWNER)
                    .noOcclusion()
                    .lightLevel(x -> 2)
                    .isRedstoneConductor(SBBlocks::never)));
    //TODO - needs chest model + texture
    public static final DeferredBlock<Block> DRIFTWOOD_CHEST = registerNoItem("driftwood_chest",
            () -> new SBChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST)
                    .mapColor(MapColor.TERRACOTTA_WHITE),
                    "driftwood"
                    ));
    //TODO - needs barrel model + texture
    public static final DeferredBlock<Block> DRIFTWOOD_BARREL = registerWithItem("driftwood_barrel",
            () -> new BarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL)
                    .mapColor(MapColor.TERRACOTTA_WHITE)));
    //TODO - needs chest model + texture
    public static final DeferredBlock<Block> MISTWOOD_CHEST = registerNoItem("mistwood_chest",
            () -> new SBChestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHEST)
                    .mapColor(MapColor.COLOR_BROWN),
                    "mistwood"));
    //TODO - needs barrel model + texture
    public static final DeferredBlock<Block> MISTWOOD_BARREL = registerWithItem("mistwood_barrel",
            () -> new BarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL)
                    .mapColor(MapColor.COLOR_BROWN)));
    //TODO - make ancient chests unique somehow - special noises?
    public static final DeferredBlock<Block> ANCIENT_CHEST = registerNoItem("ancient_chest",
            () -> new SBChestBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED_DARK_SHALE.get()),
                    "ancient"));
    //TODO - needs lantern model + texture
    public static final DeferredBlock<Block> CORRODED_LANTERN = registerWithItem("corroded_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)
                    .mapColor(MapColor.DIRT)
                    .lightLevel(x -> 12)));
    //TODO - needs cauldron model + texture
    //TODO - needs functionality
    public static final DeferredBlock<Block> ICHOR_CAULDRON = registerNoItem("ichor_cauldron",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON)
                    .lightLevel(x -> 4)
                    .mapColor(MapColor.TERRACOTTA_YELLOW)));


    //Miscellaneous
    //TODO - needs custom model + texture, including egg blockstate
    public static final DeferredBlock<Block> SEAGULL_NEST = registerWithItem("seagull_nest",
            () -> new Block(BlockBehaviour.Properties.of()));
    //TODO - Add turtle egg sounds
    //TODO - custom model and texture
    public static final DeferredBlock<Block> OYSTERS = registerWithItem("oysters",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(DyeColor.GRAY)
                    .noOcclusion()
                    .strength(0.2F)
                    .sound(SoundType.METAL)
                    .forceSolidOn()
                    .pushReaction(PushReaction.DESTROY)));
    //TODO - custom model and texture
    public static final DeferredBlock<Block> BARNACLES = registerWithItem("barnacles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(OYSTERS.get())));
    //Exists solely to be a model for doubleslabs
    public static final DeferredBlock<Block> DEAD_FISH_BLOCK = registerWithItem("dead_fish_block",
            () -> new Block(BlockBehaviour.Properties.of().noLootTable()));
    public static final DeferredBlock<Block> DEAD_FISH_SLAB = registerWithItem("dead_fish_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.CLAY)
                    .noOcclusion()
                    .strength(1.0F)
                    .friction(0.6F)
                    .speedFactor(0.8F)
                    .sound(SoundType.MUD)));
    //TODO - alter colors
    //TODO - special sound effects?
    public static final DeferredBlock<Block> BURIED_IRON_SCRAP = registerWithItem("buried_iron_scrap",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(SHINGLE.get())));
    public static final DeferredBlock<Block> BURIED_RUSTY_SCRAP = registerWithItem("buried_rusty_scrap",
            () -> new ColoredFallingBlock(new ColorRGBA(-8356741),
                    BlockBehaviour.Properties.ofFullCopy(SHINGLE.get())));
    public static final DeferredBlock<Block> IRON_SCRAP_BLOCK = registerWithItem("iron_scrap_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(SoundType.ANCIENT_DEBRIS)));
    public static final DeferredBlock<Block> RUSTY_SCRAP_BLOCK = registerWithItem("rusty_scrap_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .mapColor(MapColor.DIRT)
                    .sound(SoundType.ANCIENT_DEBRIS)));


    private static DeferredBlock<Block> registerNoItem(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }
    private static DeferredBlock<Block> registerWithItem(String name, Supplier<? extends Block> block) {
        DeferredBlock<Block> ret = registerNoItem(name, block);
        SBItems.ITEMS.registerSimpleBlockItem(ret);
        return ret;
    }


    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

}
