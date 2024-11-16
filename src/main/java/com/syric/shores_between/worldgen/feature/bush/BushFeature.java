package com.syric.shores_between.worldgen.feature.bush;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.util.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BushFeature extends Feature<BushConfiguration> {

    public BushFeature(Codec<BushConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BushConfiguration> context) {
        BlockPos origin = context.origin();
        RandomSource randomSource = context.random();
        BushConfiguration configuration = context.config();
        int size = configuration.size().sample(randomSource);

        HashMap<BlockPos, BlockState> placementMap = new HashMap<>();

        float adjacentProbability = 1; //This is for the five blocks directly attached to the log
        float horizontalEdgeProbability; //The four blocks on the same level as the log but diagonal to it
        float verticalEdgeProbability; //The four blocks that horizontally surround the block above the log
        float twoAwayProbability; //The four blocks placed two away from the log in a cardinal direction
        float knightProbability; //The eight blocks a chess knight's move from the log
        float aboveCornersProbability; //The four blocks above the horizontal edges, i.e. diagonal to the block above the log

        //Small bushes have the five adjacent ones, and then the four corners each have a 50% chance of being filled in.
        if (size == 0) {
            horizontalEdgeProbability = 0.5F;
            fillAdjacents(origin, placementMap, configuration, randomSource);
            fillHorizontalEdges(origin, placementMap, configuration, randomSource, horizontalEdgeProbability);
        } else if (size == 1) {
            horizontalEdgeProbability = 0.9F;
            verticalEdgeProbability = 0.8F;
            twoAwayProbability = 0.5F;
            fillAdjacents(origin, placementMap, configuration, randomSource);
            fillHorizontalEdges(origin, placementMap, configuration, randomSource, horizontalEdgeProbability);
            fillVerticalEdges(origin, placementMap, configuration, randomSource, verticalEdgeProbability);
            fillTwoAway(origin, placementMap, configuration, randomSource, twoAwayProbability);
        } else if (size == 2) {
            horizontalEdgeProbability = 1;
            verticalEdgeProbability = 1;
            twoAwayProbability = 1;
            knightProbability = 0.5F;
            aboveCornersProbability = 0.3F;
            fillAdjacents(origin, placementMap, configuration, randomSource);
            fillHorizontalEdges(origin, placementMap, configuration, randomSource, horizontalEdgeProbability);
            fillVerticalEdges(origin, placementMap, configuration, randomSource, verticalEdgeProbability);
            fillTwoAway(origin, placementMap, configuration, randomSource, twoAwayProbability);
            fillKnightMoves(origin, placementMap, configuration, randomSource, knightProbability);
            fillAboveCorners(origin, placementMap, configuration, randomSource, aboveCornersProbability);
        } else if (size == 3) {
            horizontalEdgeProbability = 1;
            verticalEdgeProbability = 1;
            twoAwayProbability = 1;
            knightProbability = 0.8F;
            aboveCornersProbability = 0.7F;
            fillAdjacents(origin, placementMap, configuration, randomSource);
            fillHorizontalEdges(origin, placementMap, configuration, randomSource, horizontalEdgeProbability);
            fillVerticalEdges(origin, placementMap, configuration, randomSource, verticalEdgeProbability);
            fillTwoAway(origin, placementMap, configuration, randomSource, twoAwayProbability);
            fillKnightMoves(origin, placementMap, configuration, randomSource, knightProbability);
            fillAboveCorners(origin, placementMap, configuration, randomSource, aboveCornersProbability);
        }

        //Place the bush blocks
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        WorldGenLevel level = context.level();

        //Don't place if the origin is obstructed (can't imagine why it would be though)
        //Otherwise place the log
        if (!level.getBlockState(context.origin()).canBeReplaced() && !level.getBlockState(context.origin()).isAir()) {
            return false;
        } else {
            level.setBlock(context.origin(), configuration.trunkProvider().getState(randomSource, context.origin()), 2);
        }

        if (configuration.leafProvider().getState(randomSource, origin).getBlock() instanceof LeavesBlock) {
            List<Runnable> leavesRequiringDistanceUpdate = new ArrayList<>(placementMap.size() - 1);
            for (Map.Entry<BlockPos, BlockState> entry : placementMap.entrySet()) {
                BlockPos pos = entry.getKey();
                BlockState state = configuration.leafProvider().getState(randomSource, pos);
                BlockState existingState = level.getBlockState(pos);

                if (existingState.getTags().anyMatch(t -> t == BlockTags.REPLACEABLE_BY_TREES) || existingState.canBeReplaced()) {
                    if (state.hasProperty(LeavesBlock.DISTANCE)) {
                        state = state.setValue(LeavesBlock.DISTANCE, 1);
                    }
                    level.setBlock(pos, state, 2);

                    if (state.hasProperty(LeavesBlock.DISTANCE)) {
                        BlockState finalState = state;
                        Runnable setDistance = () -> {
                            BlockState updatedState = LeavesBlock.updateDistance(finalState, level, pos);
                            if (updatedState.getValue(LeavesBlock.DISTANCE) < LeavesBlock.DECAY_DISTANCE) {
                                level.setBlock(pos, updatedState, 2);
                                level.scheduleTick(pos, updatedState.getBlock(), 0);
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

//        leavesRequiringDistanceUpdate.forEach(Runnable::run);
        return true;
    }


    //Fills the five adjacent blocks with leaves
    private static void fillAdjacents(BlockPos origin, Map<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource) {
        for (Direction direction : Direction.values()) {
            if (direction != Direction.DOWN) {
                BlockPos pos = origin.relative(direction);
                placementMap.put(pos, configuration.leafProvider().getState(randomSource, pos));
            }
        }
    }

    //Fills the four corner blocks
    private static void fillHorizontalEdges(BlockPos origin, Map<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource, float horizontalEdgeProbability) {
        for (Direction direction : DirectionUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos pos = origin.relative(direction).relative(direction.getCounterClockWise());
            if (randomSource.nextFloat() < horizontalEdgeProbability) {
                placementMap.put(pos, configuration.leafProvider().getState(randomSource, pos));
            }
        }
    }

    //Fills the four blocks above, IF both blocks below and to the sides are full.
    private static void fillVerticalEdges(BlockPos origin, Map<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource, float verticalEdgeProbability) {
        for (Direction direction : DirectionUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos pos = origin.relative(direction).above();
            BlockPos[] supportingPositions = {origin.relative(direction).relative(direction.getCounterClockWise()), origin.relative(direction).relative(direction.getClockWise())};
            if (randomSource.nextFloat() < verticalEdgeProbability && placementMap.containsKey(supportingPositions[0]) && placementMap.containsKey(supportingPositions[1])) {
                placementMap.put(pos, configuration.leafProvider().getState(randomSource, pos));
            }
        }
    }

    //Fills the four blocks two away laterally, IF the block behind and above is full.
    private static void fillTwoAway(BlockPos origin, HashMap<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource, float twoAwayProbability) {
        for (Direction direction : DirectionUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos pos = origin.relative(direction, 2);
            BlockPos supportingPos = origin.relative(direction).above();
            if (randomSource.nextFloat() < twoAwayProbability && placementMap.containsKey(supportingPos)) {
                placementMap.put(pos, configuration.leafProvider().getState(randomSource, pos));
            }
        }
    }

    //Fills the eight blocks a knight-move away, IF the block two away next to them is full.
    private static void fillKnightMoves(BlockPos origin, HashMap<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource, float knightProbability) {
        for (Direction direction : DirectionUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos pos1 = origin.relative(direction).relative(direction).relative(direction.getClockWise());
            BlockPos pos2 = origin.relative(direction).relative(direction).relative(direction.getCounterClockWise());
            BlockPos supportingPos = origin.relative(direction).relative(direction);
            if (placementMap.containsKey(supportingPos)) {
                if (randomSource.nextFloat() < knightProbability) {
                    placementMap.put(pos1, configuration.leafProvider().getState(randomSource, pos1));
                }
                if (randomSource.nextFloat() < knightProbability) {
                    placementMap.put(pos2, configuration.leafProvider().getState(randomSource, pos2));
                }
            }
        }
    }

    //Fills the four blocks above and diagonal to the log, IF the two outer blocks beneath them are full.
    private static void fillAboveCorners(BlockPos origin, HashMap<BlockPos, BlockState> placementMap, BushConfiguration configuration, RandomSource randomSource, float aboveCornersProbability) {
        for (Direction direction : DirectionUtil.HORIZONTAL_DIRECTIONS) {
            BlockPos pos = origin.relative(direction).relative(direction.getCounterClockWise()).above();
            BlockPos supportingPos1 = origin.relative(direction).relative(direction).relative(direction.getCounterClockWise());
            BlockPos supportingPos2 = origin.relative(direction).relative(direction.getCounterClockWise()).relative(direction.getCounterClockWise());
            if (randomSource.nextFloat() < aboveCornersProbability && placementMap.containsKey(supportingPos1) && placementMap.containsKey(supportingPos2)) {
                placementMap.put(pos, configuration.leafProvider().getState(randomSource, pos));
            }
        }
    }

}
