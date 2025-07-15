package com.syric.shores_between.worldgen.feature.tree;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBBlocks;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.*;

public class NBTTreeFeature extends Feature<NBTTreeConfiguration> {

    public NBTTreeFeature(Codec<NBTTreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NBTTreeConfiguration> context) {
        RandomSource randomSource = context.random();
        WorldGenLevel level = context.level();
        StructureTemplateManager structureTemplateManager = level.getLevel().getServer().getStructureManager();
        BlockPos tree_origin = context.origin();
        Rotation rotation = Rotation.getRandom(randomSource);
        Mirror mirror = Util.getRandom(Mirror.values(), randomSource);


        StructurePlaceSettings placeSettings = new StructurePlaceSettings().setRotation(rotation);
        placeSettings = placeSettings.setMirror(mirror);
        NBTTreeConfiguration config = context.config();

        ResourceLocation tree = config.treeLocations().get(randomSource.nextInt(config.treeLocations().size()));
        Optional<StructureTemplate> treeTemplateOptional = structureTemplateManager.get(tree);

        if (treeTemplateOptional.isEmpty()) {
            throw new IllegalArgumentException("Unknown tree ResourceLocation: " + tree);
        }

        StructureTemplate treeTemplate = treeTemplateOptional.get();
        StructureTemplate.Palette treePalette = placeSettings.getRandomPalette(treeTemplate.palettes, tree_origin);

        List<StructureTemplate.StructureBlockInfo> trunkCalibrator = treePalette.blocks(Blocks.OBSIDIAN);
        if (trunkCalibrator.size() != 1) {
            throw new IllegalArgumentException(String.format("Trunk Calibrator found %s obsidian blocks instead of 1", trunkCalibrator.size()));
        }

        BlockPos calibratorPos = trunkCalibrator.getFirst().pos();
//        level.setBlock(tree_origin, Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
        BlockPos offset = new BlockPos(-calibratorPos.getX(), -  1, -calibratorPos.getZ());
//        level.setBlock(new BlockPos(tree_origin.getX() + offset.getX(), tree_origin.getY(), tree_origin.getZ() + offset.getZ()), Blocks.LAPIS_BLOCK.defaultBlockState(), 2);

        //Locate leaves and logs in the structure
        List<StructureTemplate.StructureBlockInfo> template_logs = treePalette.blocks(SBBlocks.MISTWOOD_LOG.get());
        List<StructureTemplate.StructureBlockInfo> template_leaves = treePalette.blocks(SBBlocks.MISTWOOD_LEAVES.get());

        //Get bonus height
        int bonus_height = config.bonusHeight().sample(randomSource);
        if (bonus_height != 0) {
            offset = offset.above(bonus_height);
        }

        //Check that no log is obstructed
        BlockPos.MutableBlockPos logObstructionChecker = new BlockPos.MutableBlockPos().set(tree_origin);
        for (int i = 0; i < bonus_height; i++) {
            if (!level.getBlockState(logObstructionChecker).canBeReplaced()) {
                return false;
            }
            logObstructionChecker.move(Direction.UP);
        }
        for (StructureTemplate.StructureBlockInfo log : template_logs) {
            logObstructionChecker.set(getModifiedPos(placeSettings, log, offset, tree_origin));
            if (!level.getBlockState(logObstructionChecker).canBeReplaced()) {
                return false;
            }
        }


        //Place bonus height logs
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos().set(tree_origin);
        for (int i = 0; i < bonus_height; i++) {
            if (level.getBlockState(mutableBlockPos).canBeReplaced()) {
                if (level instanceof Level) {
                    level.removeBlock(mutableBlockPos, true);
                }
                level.setBlock(mutableBlockPos, config.trunkProvider().getState(randomSource, mutableBlockPos), 2);
                mutableBlockPos.move(Direction.UP);
            }
        }

        List<BlockPos> bottommost_logs = new ArrayList<>(List.of(tree_origin));

        //Place buttresses
        //Decide if there are any buttresses
        int buttress_count = config.buttresses().sample(randomSource);
        List<BlockPos> buttress_base_positions = new ArrayList<>(List.of());
        if (buttress_count > 0) {
            //If there are, decide which directions will have buttresses.
            Direction direction = Direction.getNearest(randomSource.nextFloat() - 0.5, 0, randomSource.nextFloat() - 0.5);
            boolean clockwise = randomSource.nextBoolean();
            for (int i = 0; i < buttress_count; i++) {
                buttress_base_positions.add(tree_origin.relative(direction));
                direction = clockwise ? direction.getClockWise() : direction.getCounterClockWise();
            }

            int[] buttress_heights = getButtressHeights(buttress_count, config.buttressHeight(), randomSource);

            List<BlockPos> buttress_blocks = new ArrayList<>(List.of());
            List<BlockPos> buttress_top_blocks = new ArrayList<>(List.of());
            List<BlockPos> buttress_bottom_blocks = new ArrayList<>(List.of());

            //For each buttress, get a list of the blocks that will form it.
            BlockPos.MutableBlockPos analysis_pos = new BlockPos.MutableBlockPos().set(tree_origin);
            int height_index = -1;
            for (BlockPos buttress_pos : buttress_base_positions) {
                height_index++;

                if (!level.getBlockState(buttress_pos).canBeReplaced()) {
                    continue;
                }

                buttress_top_blocks.add(buttress_pos);
                buttress_bottom_blocks.add(buttress_pos);

                //Go up by 'buttress_height'. The current highest block is in buttress_top_blocks, the rest in buttress_blocks.
                //If you ever run into an obstacle, terminate buttress immediately.
                int buttress_height = buttress_heights[height_index];
                for (int i = 1; i < buttress_height; i++) {
                    analysis_pos.set(buttress_pos.above(i));
                    if (level.getBlockState(analysis_pos).canBeReplaced()) {
                        buttress_top_blocks.add(new BlockPos(analysis_pos));
                        buttress_top_blocks.remove(analysis_pos.below());
                        buttress_blocks.add(analysis_pos.below());
                    } else {
                        break;
                    }
                }

                //Go down by 2 blocks. The current lowest block is in buttress_bottom_blocks, the rest in buttress_blocks.
                //If you ever run into an obstacle, terminate buttress immediately.
                for (int i = 1; i < 3; i++) {
                    analysis_pos.set(buttress_pos.below(i));
                    if (level.getBlockState(analysis_pos).canBeReplaced()) {
                        buttress_bottom_blocks.add(new BlockPos(analysis_pos));
                        buttress_bottom_blocks.remove(analysis_pos.above());
                        buttress_blocks.add(analysis_pos.above());
                    } else {
                        break;
                    }
                }
            }

            buttress_blocks.removeAll(buttress_top_blocks);
            buttress_blocks.removeAll(buttress_bottom_blocks);

            //We now have a list of all buttress positions. Place wood there.
            BlockPos.MutableBlockPos modified_pos = new BlockPos.MutableBlockPos();
            for (BlockPos pos : buttress_blocks) {
                modified_pos.set(pos);
                if (level instanceof Level) {
                    level.removeBlock(modified_pos, true);
                }
                level.setBlock(modified_pos, config.trunkProvider().getState(randomSource, modified_pos), 2);
            }
            for (BlockPos pos : buttress_bottom_blocks) {
                modified_pos.set(pos);
                boolean wood = level.getBlockState(pos.below()).canBeReplaced();
                if (level instanceof Level) {
                    level.removeBlock(modified_pos, true);
                }
                level.setBlock(modified_pos, wood ? config.woodProvider().getState(randomSource, modified_pos) : config.trunkProvider().getState(randomSource, modified_pos), 2);
            }
            for (BlockPos pos : buttress_top_blocks) {
                modified_pos.set(pos);
                if (level instanceof Level) {
                    level.removeBlock(modified_pos, true);
                }
                level.setBlock(modified_pos, config.woodProvider().getState(randomSource, modified_pos), 2);
            }

            bottommost_logs.addAll(buttress_bottom_blocks);

        }

        //Replace underblocks if only 1 high
        //To do this, the block underneath the log must be solid and horizontally exposed, and the block two below must not be.
        if (config.replaceExposedFoundation()) {
            for (BlockPos pos : bottommost_logs) {
                BlockPos below_pos = pos.below();
                if (level.getBlockState(below_pos).canBeReplaced()) {
                    continue;
                }

                boolean block_below_is_exposed = false;
                for (Direction direction : Direction.values()) {
                    if (direction.getAxis() == Direction.Axis.Y) {
                        continue;
                    }
                    BlockPos adjacent = below_pos.relative(direction);
                    if (level.getBlockState(adjacent).canBeReplaced()) {
                        block_below_is_exposed = true;
                        break;
                    }
                }
                BlockPos two_below_pos = pos.below(2);
                boolean block_two_below_is_exposed = false;
                for (Direction direction : Direction.values()) {
                    if (direction.getAxis() == Direction.Axis.Y) {
                        continue;
                    }
                    BlockPos adjacent = two_below_pos.relative(direction);
                    if (level.getBlockState(adjacent).canBeReplaced()) {
                        block_two_below_is_exposed = true;
                        break;
                    }
                }
                if (block_below_is_exposed && !block_two_below_is_exposed) {
                    boolean wood = level.getBlockState(two_below_pos).canBeReplaced();
                    if (level instanceof Level) {
                        level.removeBlock(below_pos, true);
                    }
//                    level.setBlock(below_pos, Blocks.REDSTONE_BLOCK.defaultBlockState(), 2);
                    level.setBlock(below_pos, wood ? config.woodProvider().getState(randomSource, below_pos) : config.trunkProvider().getState(randomSource, below_pos), 2);
                }
            }
        }


        //Place logs
        if (config.trunkProvider().getState(randomSource, calibratorPos).getBlock() instanceof RotatedPillarBlock) {
            for (StructureTemplate.StructureBlockInfo log : template_logs) {
                BlockPos pos = getModifiedPos(placeSettings, log, offset, tree_origin);
                BlockState state = log.state(); //The state of the log in the template
                BlockState toBePlaced = config.trunkProvider().getState(randomSource, pos); //The state of the log to be placed

                //Make state to be placed match template
                for (Property property : state.getProperties()) {
                    if (toBePlaced.hasProperty(property)) {
                        toBePlaced = toBePlaced.setValue(property, state.getValue(property));
                    }
                }

                //If the state has the Axis property and it's horizontal, and the rotation isn't 180 degrees,
                //switch the axis from X to Z or vice versa.
                if (toBePlaced.hasProperty(RotatedPillarBlock.AXIS) && toBePlaced.getValue(RotatedPillarBlock.AXIS).isHorizontal() && (placeSettings.getRotation() == Rotation.CLOCKWISE_90 || placeSettings.getRotation() == Rotation.COUNTERCLOCKWISE_90)) {
                    toBePlaced = toBePlaced.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.X ? toBePlaced.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z) : toBePlaced.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
                }

                level.setBlock(pos, toBePlaced, 2);
            }
        }

        //Place leaves
        if (config.foliageProvider().getState(randomSource, calibratorPos).getBlock() instanceof LeavesBlock) {
            List<Runnable> leavesRequiringDistanceUpdate = new ArrayList<>(template_leaves.size());
            for (StructureTemplate.StructureBlockInfo leaf : template_leaves) {
                BlockPos pos = getModifiedPos(placeSettings, leaf, offset, tree_origin);
                BlockState state = leaf.state();
                BlockState toBePlaced = config.foliageProvider().getState(randomSource, pos);
                BlockState existingState = level.getBlockState(pos);

                if (existingState.getTags().anyMatch(t -> t == BlockTags.REPLACEABLE_BY_TREES) || existingState.canBeReplaced()) {
                    //Make state to be placed match template
                    for (Property property : state.getProperties()) {
                        if (toBePlaced.hasProperty(property)) {
                            toBePlaced = toBePlaced.setValue(property, state.getValue(property));
                        }
                    }
                    if (toBePlaced.hasProperty(LeavesBlock.PERSISTENT)) {
                        toBePlaced.setValue(LeavesBlock.PERSISTENT, false);
                    }

                    level.setBlock(pos, toBePlaced, 2);

                    if (toBePlaced.hasProperty(LeavesBlock.DISTANCE)) {
                        BlockState finalState = toBePlaced;
                        Runnable setDistance = () -> {
                            BlockState blockState = LeavesBlock.updateDistance(finalState, level, pos);
                            if (blockState.getValue(LeavesBlock.DISTANCE) < LeavesBlock.DECAY_DISTANCE) {
                                level.setBlock(pos, blockState, 2);
                                level.scheduleTick(pos, blockState.getBlock(), 0);
                            } else {
//                                level.removeBlock(pos, false);
                                level.setBlock(pos, Blocks.PINK_CONCRETE.defaultBlockState(), 2);
                            }
                        };
                        leavesRequiringDistanceUpdate.add(setDistance);
                    }
                }
            }
            leavesRequiringDistanceUpdate.forEach(Runnable::run);
        }
//        level.setBlock(tree_origin, Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);


        return true;
    }

    public static BlockPos getModifiedPos(StructurePlaceSettings settings, StructureTemplate.StructureBlockInfo placing, BlockPos partCenter, BlockPos featureOrigin) {
        return StructureTemplate
                .calculateRelativePosition(settings, placing.pos())
                .offset(featureOrigin)
                .offset(StructureTemplate.calculateRelativePosition(settings, partCenter));
    }

    public static int[] getButtressHeights(int number_of_buttresses, IntProvider max_height, RandomSource random) {
        Integer[] raw_heights = new Integer[number_of_buttresses + 2];
        int[] output = new int[number_of_buttresses];

        for (int i = 0; i < number_of_buttresses + 2; i++) {
            raw_heights[i] = max_height.sample(random);
        }
        Arrays.sort(raw_heights, Collections.reverseOrder());

        int tallest = raw_heights[0];
        if (number_of_buttresses == 1) {
            output[0] = Math.min(tallest, raw_heights[1]);
            return output;
        }

        int second_tallest = raw_heights[2];
        if (number_of_buttresses == 2) {
            output[0] = tallest;
            output[1] = second_tallest;
            return output;
        }

        int third_tallest = raw_heights[3];
        if (number_of_buttresses == 3) {
            output[0] = second_tallest;
            output[1] = tallest;
            output[2] = third_tallest;
            return output;
        }

        int fourth_tallest = raw_heights[5];
        if (number_of_buttresses == 4) {
            output[0] = tallest;
            output[1] = second_tallest;
            output[3] = third_tallest;
            output[2] = fourth_tallest;
            return output;
        }
        throw new IllegalArgumentException("Number of buttresses was not 1-4!");
    }

}
