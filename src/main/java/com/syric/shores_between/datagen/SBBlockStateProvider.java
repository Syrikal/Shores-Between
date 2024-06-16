package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class SBBlockStateProvider extends BlockStateProvider {


    public SBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ShoresBetween.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        block(SBBlocks.DEFAULT_BLOCK);
        block(SBBlocks.SHINGLE);
        block(SBBlocks.TANGLED_SHINGLE);
        block(SBBlocks.PEBBLES);
        block(SBBlocks.GRASSY_SHINGLE);
        block(SBBlocks.SHALE);
        block(SBBlocks.DARK_SHALE);
        block(SBBlocks.OVERGROWN_SHALE);
        block(SBBlocks.SALTSTONE);
        log(SBBlocks.DRIFTWOOD_LOG);
        wood(SBBlocks.DRIFTWOOD_WOOD, SBBlocks.DRIFTWOOD_LOG);
        log(SBBlocks.STRIPPED_DRIFTWOOD_LOG);
        wood(SBBlocks.STRIPPED_DRIFTWOOD_WOOD, SBBlocks.STRIPPED_DRIFTWOOD_LOG);
        block(SBBlocks.DRIFTWOOD_PLANKS);
        halfSlab(SBBlocks.DRIFTWOOD_SLAB, SBBlocks.DRIFTWOOD_PLANKS);
        stairs(SBBlocks.DRIFTWOOD_STAIRS, SBBlocks.DRIFTWOOD_PLANKS);
        door(SBBlocks.DRIFTWOOD_DOOR);
        trapdoor(SBBlocks.DRIFTWOOD_TRAPDOOR);
        fence(SBBlocks.DRIFTWOOD_FENCE, SBBlocks.DRIFTWOOD_PLANKS);
        fenceGate(SBBlocks.DRIFTWOOD_FENCE_GATE, SBBlocks.DRIFTWOOD_PLANKS);
        button(SBBlocks.DRIFTWOOD_BUTTON, SBBlocks.DRIFTWOOD_PLANKS);
        pressurePlate(SBBlocks.DRIFTWOOD_PRESSURE_PLATE, SBBlocks.DRIFTWOOD_PLANKS);
        sign(SBBlocks.DRIFTWOOD_SIGN, SBBlocks.DRIFTWOOD_WALL_SIGN, SBBlocks.DRIFTWOOD_PLANKS);
        hangingSign(SBBlocks.DRIFTWOOD_HANGING_SIGN, SBBlocks.DRIFTWOOD_WALL_HANGING_SIGN, SBBlocks.DRIFTWOOD_PLANKS);
        ladder(SBBlocks.DRIFTWOOD_LADDER, SBBlocks.DRIFTWOOD_PLANKS);
        log(SBBlocks.PETRIFIED_LOG);
        wood(SBBlocks.PETRIFIED_WOOD, SBBlocks.PETRIFIED_LOG);
        log(SBBlocks.STRIPPED_PETRIFIED_LOG);
        wood(SBBlocks.STRIPPED_PETRIFIED_WOOD, SBBlocks.STRIPPED_PETRIFIED_LOG);
        block(SBBlocks.PETRIFIED_PLANKS);
        halfSlab(SBBlocks.PETRIFIED_SLAB, SBBlocks.PETRIFIED_PLANKS);
        stairs(SBBlocks.PETRIFIED_STAIRS, SBBlocks.PETRIFIED_PLANKS);
        log(SBBlocks.MISTWOOD_LOG);
        wood(SBBlocks.MISTWOOD_WOOD, SBBlocks.MISTWOOD_LOG);
        log(SBBlocks.STRIPPED_MISTWOOD_LOG);
        wood(SBBlocks.STRIPPED_MISTWOOD_WOOD, SBBlocks.STRIPPED_MISTWOOD_LOG);
        block(SBBlocks.MISTWOOD_PLANKS);
        halfSlab(SBBlocks.MISTWOOD_SLAB, SBBlocks.MISTWOOD_PLANKS);
        stairs(SBBlocks.MISTWOOD_STAIRS, SBBlocks.MISTWOOD_PLANKS);
        door(SBBlocks.MISTWOOD_DOOR);
        trapdoor(SBBlocks.MISTWOOD_TRAPDOOR);
        fence(SBBlocks.MISTWOOD_FENCE, SBBlocks.MISTWOOD_PLANKS);
        fenceGate(SBBlocks.MISTWOOD_FENCE_GATE, SBBlocks.MISTWOOD_PLANKS);
        button(SBBlocks.MISTWOOD_BUTTON, SBBlocks.MISTWOOD_PLANKS);
        pressurePlate(SBBlocks.MISTWOOD_PRESSURE_PLATE, SBBlocks.MISTWOOD_PLANKS);
        sign(SBBlocks.MISTWOOD_SIGN, SBBlocks.MISTWOOD_WALL_SIGN, SBBlocks.MISTWOOD_PLANKS);
        hangingSign(SBBlocks.MISTWOOD_HANGING_SIGN, SBBlocks.MISTWOOD_WALL_HANGING_SIGN, SBBlocks.MISTWOOD_PLANKS);
        ladder(SBBlocks.MISTWOOD_LADDER, SBBlocks.MISTWOOD_PLANKS);
        block(SBBlocks.MISTWOOD_LEAVES);
        crossBlock(SBBlocks.MISTWOOD_SAPLING); //Needs attention
        block(SBBlocks.ROTTING_FLESH_BLOCK);
        block(SBBlocks.INFESTED_FLESH_BLOCK);
        block(SBBlocks.BLOATED_FLESH);
        block(SBBlocks.MUMMIFIED_FLESH);
        block(SBBlocks.LEVIATHAN_BONE);
        block(SBBlocks.LEVIATHAN_KERATIN);
        block(SBBlocks.JELLIED_ICHOR); //Needs attention
        block(SBBlocks.ICHOR_CAULDRON); //Needs attention
        block(SBBlocks.TITAN_BONE);
        block(SBBlocks.CORRODED_TITAN_BONE);
        block(SBBlocks.ABERRANT_SHELL);
        crossBlock(SBBlocks.DUNEGRASS); //Needs attention
        block(SBBlocks.SEAWEED_BLOCK); //Needs custom model
        halfSlab(SBBlocks.SEAWEED_SLAB, SBBlocks.SEAWEED_BLOCK); //Needs custom model
        carpet(SBBlocks.SEAWEED_CARPET); //Needs custom model
        carpet(SBBlocks.MISTWOOD_MOSS_CARPET); //Needs custom model
        block(SBBlocks.MISTWOOD_MOSS_BLOCK);
        block(SBBlocks.CORRODED_CONCRETE);
        block(SBBlocks.SUSPICIOUS_SHINGLE);
        block(SBBlocks.SEAGULL_NEST); //Needs custom model
        block(SBBlocks.OYSTERS); //Needs custom model
        block(SBBlocks.BARNACLES); //Needs custom model
        block(SBBlocks.DEAD_FISH_BLOCK); //Needs custom model
        halfSlab(SBBlocks.DEAD_FISH_SLAB, SBBlocks.DEAD_FISH_BLOCK); //Needs custom model
        block(SBBlocks.PORTAL_BLOCK); //Needs custom model
        halfSlab(SBBlocks.SHALE_SLAB, SBBlocks.SHALE);
        stairs(SBBlocks.SHALE_STAIRS, SBBlocks.SHALE);
        wall(SBBlocks.SHALE_WALL, SBBlocks.SHALE);
        block(SBBlocks.POLISHED_SHALE);
        halfSlab(SBBlocks.POLISHED_SHALE_SLAB, SBBlocks.POLISHED_SHALE);
        stairs(SBBlocks.POLISHED_SHALE_STAIRS, SBBlocks.POLISHED_SHALE);
        wall(SBBlocks.POLISHED_SHALE_WALL, SBBlocks.POLISHED_SHALE);
        block(SBBlocks.SHALE_BRICKS);
        block(SBBlocks.CRACKED_SHALE_BRICKS);
        halfSlab(SBBlocks.SHALE_BRICK_SLAB, SBBlocks.SHALE_BRICKS);
        stairs(SBBlocks.SHALE_BRICK_STAIRS, SBBlocks.SHALE_BRICKS);
        wall(SBBlocks.SHALE_BRICK_WALL, SBBlocks.SHALE_BRICKS);
        block(SBBlocks.MOSSY_SHALE_BRICKS);
        halfSlab(SBBlocks.MOSSY_SHALE_BRICK_SLAB, SBBlocks.MOSSY_SHALE_BRICKS);
        stairs(SBBlocks.MOSSY_SHALE_BRICK_STAIRS, SBBlocks.MOSSY_SHALE_BRICKS);
        wall(SBBlocks.MOSSY_SHALE_BRICK_WALL, SBBlocks.MOSSY_SHALE_BRICKS);
        halfSlab(SBBlocks.DARK_SHALE_SLAB, SBBlocks.DARK_SHALE);
        stairs(SBBlocks.DARK_SHALE_STAIRS, SBBlocks.DARK_SHALE);
        wall(SBBlocks.DARK_SHALE_WALL, SBBlocks.DARK_SHALE);
        block(SBBlocks.POLISHED_DARK_SHALE);
        halfSlab(SBBlocks.POLISHED_DARK_SHALE_SLAB, SBBlocks.POLISHED_DARK_SHALE);
        stairs(SBBlocks.POLISHED_DARK_SHALE_STAIRS, SBBlocks.POLISHED_DARK_SHALE);
        wall(SBBlocks.POLISHED_DARK_SHALE_WALL, SBBlocks.POLISHED_DARK_SHALE);
        block(SBBlocks.DARK_SHALE_BRICKS);
        block(SBBlocks.CRACKED_DARK_SHALE_BRICKS);
        halfSlab(SBBlocks.DARK_SHALE_BRICK_SLAB, SBBlocks.DARK_SHALE_BRICKS);
        stairs(SBBlocks.DARK_SHALE_BRICK_STAIRS, SBBlocks.DARK_SHALE_BRICKS);
        wall(SBBlocks.DARK_SHALE_BRICK_WALL, SBBlocks.DARK_SHALE_BRICKS);
        block(SBBlocks.DARK_SHALE_TILES);
        block(SBBlocks.CRACKED_DARK_SHALE_TILES);
        halfSlab(SBBlocks.DARK_SHALE_TILE_SLAB, SBBlocks.DARK_SHALE_TILES);
        stairs(SBBlocks.DARK_SHALE_TILE_STAIRS, SBBlocks.DARK_SHALE_TILES);
        wall(SBBlocks.DARK_SHALE_TILE_WALL, SBBlocks.DARK_SHALE_TILES);
        block(SBBlocks.CHISELED_SHALE);
        block(SBBlocks.CHISELED_DARK_SHALE);
        block(SBBlocks.ENGRAVED_DARK_SHALE);
        block(SBBlocks.DARK_OBELISK); //Needs custom model
        block(SBBlocks.CRAB_POT); //Needs custom model
        totem(SBBlocks.WHALEBONE_TOTEM); //Needs custom model
        chest(SBBlocks.DRIFTWOOD_CHEST); //Needs attention
        barrel(SBBlocks.DRIFTWOOD_BARREL); //Needs attention
        chest(SBBlocks.MISTWOOD_CHEST); //Needs attention
        barrel(SBBlocks.MISTWOOD_BARREL); //Needs attention
        chest(SBBlocks.ANCIENT_CHEST); //Needs attention
        lantern(SBBlocks.CORRODED_LANTERN); //Needs custom model
        block(SBBlocks.BURIED_IRON_SCRAP); //Needs custom model
        block(SBBlocks.BURIED_RUSTY_SCRAP); //Needs custom model
        block(SBBlocks.IRON_SCRAP_BLOCK);
        block(SBBlocks.RUSTY_SCRAP_BLOCK);
    }

    private void block(DeferredBlock<Block> block) {
        simpleBlock(block.get());
    }

    private void halfSlab(DeferredBlock<Block> slab, DeferredBlock<Block> fullBlock) {
        this.slabBlock((SlabBlock) slab.get(), texture(fullBlock), texture(fullBlock));
    }

    private void stairs(DeferredBlock<Block> stairs, DeferredBlock<Block> fullBlock) {
        stairsBlock((StairBlock) stairs.get(), texture(fullBlock));
    }

    private void wall(DeferredBlock<Block> wall, DeferredBlock<Block> fullBlock) {
        wallBlock((WallBlock) wall.get(), texture(fullBlock));
    }

    private void log(DeferredBlock<Block> block) {
        logBlock((RotatedPillarBlock) block.get());
    }

    private void wood(DeferredBlock<Block> block, DeferredBlock<Block> log) {
        axisBlock((RotatedPillarBlock) block.get(), texture(log), texture(log));
    }

    private void fence(DeferredBlock<Block> fence, DeferredBlock<Block> fullBlock) {
        fenceBlock((FenceBlock) fence.get(), texture(fullBlock));
    }

    private void fenceGate(DeferredBlock<Block> fenceGate, DeferredBlock<Block> fullBlock) {
        fenceGateBlock((FenceGateBlock) fenceGate.get(), texture(fullBlock));
    }

    private void door(DeferredBlock<Block> door) {
        doorBlock((DoorBlock) door.get(), texture(door.getId().getPath() + "_bottom"), texture(door.getId().getPath() + "_top"));
    }

    private void trapdoor(DeferredBlock<Block> trapdoor) {
        trapdoorBlock((TrapDoorBlock) trapdoor.get(), texture(trapdoor), false);
    }

    private void carpet(DeferredBlock<Block> carpet) {
        simpleBlockWithItem(carpet.get(), models().carpet(carpet.getRegisteredName(), texture(carpet)));
    }

    private void crossBlock(DeferredBlock<Block> block) {
        ModelFile crossBlockModel = models().cross(block.getId().getPath(), texture(block));
        simpleBlock(block.get(), crossBlockModel);
    }

    private void pressurePlate(DeferredBlock<Block> pressurePlate, DeferredBlock<Block> fullBlock) {
        pressurePlateBlock((PressurePlateBlock) pressurePlate.get(), texture(fullBlock));
    }

    private void button(DeferredBlock<Block> button, DeferredBlock<Block> fullBlock) {
        buttonBlock((ButtonBlock) button.get(), texture(fullBlock));
    }

    private void sign(DeferredBlock<Block> standing, DeferredBlock<Block> wall, DeferredBlock<Block> fullBlock) {
        signBlock((StandingSignBlock) standing.get(), (WallSignBlock) wall.get(), texture(fullBlock));
    }

    public void hangingSign(DeferredBlock<Block> signBlock, DeferredBlock<Block> wallSignBlock, DeferredBlock<Block> fullBlock) {
        ModelFile sign =  models().getBuilder(signBlock.getRegisteredName()).texture("particle", texture(fullBlock));
        simpleBlock(signBlock.get(), sign);
        simpleBlock(wallSignBlock.get(), sign);
    }

    private void chest(DeferredBlock<Block> chest) {
        ModelFile chestModel = models().getBuilder(chest.getRegisteredName()).texture("particle", texture(chest));
        simpleBlock(chest.get(), chestModel);
    }

    private void ladder(DeferredBlock<Block> ladder, DeferredBlock<Block> fullBlock) {
        ModelFile ladderModel = models().withExistingParent(ladder.getId().getPath(), "ladder")
                .texture("particle", texture(fullBlock))
                .texture("texture", texture(ladder))
                .ao(false);
        getVariantBuilder(ladder.get())
                .partialState().with(LadderBlock.FACING, Direction.NORTH)
                    .modelForState().modelFile(ladderModel).addModel()
                .partialState().with(LadderBlock.FACING, Direction.EAST)
                        .modelForState().modelFile(ladderModel).rotationY(90).addModel()
                .partialState().with(LadderBlock.FACING, Direction.SOUTH)
                    .modelForState().modelFile(ladderModel).rotationY(180).addModel()
                .partialState().with(LadderBlock.FACING, Direction.WEST)
                    .modelForState().modelFile(ladderModel).rotationY(270).addModel();
    }

    private void lantern(DeferredBlock<Block> lantern) {
        ModelFile lanternModel = models().withExistingParent(lantern.getId().getPath(), "lantern")
                .texture("lantern", texture(lantern));
        ModelFile hangingModel = models().withExistingParent(lantern.getId().getPath() + "_hanging", "lantern_hanging")
                .texture("lantern", texture(lantern));
        getVariantBuilder(lantern.get())
                .partialState().with(LanternBlock.HANGING, Boolean.FALSE)
                    .modelForState().modelFile(lanternModel).addModel()
                .partialState().with(LanternBlock.HANGING, Boolean.TRUE)
                    .modelForState().modelFile(hangingModel).addModel();
    }

    private void totem(DeferredBlock<Block> totem) {
        ModelFile totemModel = models().withExistingParent(totem.getId().getPath(), "brewing_stand")
                .texture("particle", texture(totem))
                .texture("base", texture(totem, "base"))
                .texture("stand", texture(totem));
        simpleBlock(totem.get(), totemModel);
    }

    private void barrel(DeferredBlock<Block> barrel) {
        ModelFile barrelClosedModel = models().withExistingParent(barrel.getId().getPath(), "barrel")
                .texture("side", texture(barrel, "side"))
                .texture("bottom", texture(barrel, "bottom"))
                .texture("top", texture(barrel, "top"));
        ModelFile barrelOpenModel = models().withExistingParent(barrel.getId().getPath() + "_open", "barrel")
                .texture("side", texture(barrel, "side"))
                .texture("bottom", texture(barrel, "bottom"))
                .texture("top", texture(barrel, "top_open"));
        getVariantBuilder(barrel.get())
                .forAllStates(blockState -> {
                    Direction dir = blockState.getValue(BlockStateProperties.FACING);
                    Boolean open = blockState.getValue(BlockStateProperties.OPEN);
                    return ConfiguredModel.builder()
                            .modelFile(open ? barrelOpenModel : barrelClosedModel)
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot() + 180)) % 360)
                            .build();
                });
    }

    private ResourceLocation texture(String name) {
        return new ResourceLocation(ShoresBetween.MODID, "block/" + name);
    }

    private ResourceLocation texture(DeferredBlock<Block> block) {
        return new ResourceLocation(ShoresBetween.MODID, "block/" + block.getId().getPath());
    }

    private ResourceLocation texture(DeferredBlock<Block> block, String addon) {
        return new ResourceLocation(ShoresBetween.MODID, "block/" + block.getId().getPath() + "_" + addon);
    }

}
