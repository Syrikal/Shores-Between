package com.syric.shores_between.worldgen.feature.log;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.util.DirectionUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;

public class FallenLogFeature extends Feature<FallenLogConfiguration> {

    public FallenLogFeature(Codec<FallenLogConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FallenLogConfiguration> context) {

        //Try: 2x in position
        //Switch axis, try twice
        //If too obstructed, try 1 up twice per axis
        //If too unsupported, try 1 down twice per axis
        //1 each in the 4 adjacent directions

        int buried = context.config().buried().sample(context.random());
        ArrayList<BlockPos> candidate;
        ArrayList<LogPlacementTestResult> results = new ArrayList<>();

        BlockPos original_origin = context.origin();
        int below = 0;
        if (buried == 1) {
            double rand_double = context.random().nextDouble();
            if (rand_double < 0.1) {
                below = 0;
            } else if (rand_double < 0.9) {
                below = 1;
            } else {
                below = 2;
            }
        } else if (buried == 2) {
            double rand_double = context.random().nextDouble();
            if (rand_double < 0.3) {
                below = 2;
            } else if (rand_double < 0.6) {
                below = 3;
            } else if (rand_double < 0.85) {
                below = 4;
            } else {
                below = 5;
            }
        }
        original_origin = original_origin.below(below);

        //Try twice in the original spot
        candidate = tryTwicePerAxis(original_origin, buried, context, results, context.random());

        //If that fails, try up or down depending on why it failed
        if (candidate == null) {
            BlockPos origin;
            if (results.stream().filter(x -> x == LogPlacementTestResult.OBSTRUCTED).count() > results.stream().filter(x -> x == LogPlacementTestResult.UNSUPPORTED).count()) {
                origin = original_origin.above();
            } else {
                origin = original_origin.below();
            }

            candidate = tryTwicePerAxis(origin, buried, context, results, context.random());

            //If THAT fails, try twice in each horizontal position around the origin until it works
            if (candidate == null) {
                for (Direction direction : Direction.allShuffled(context.random())) {
                    if (direction.getAxis().equals(Direction.Axis.Y)) {
                        continue;
                    }
                    origin = original_origin.relative(direction);
                    candidate = tryTwicePerAxis(origin, buried, context, results, context.random());
                    if (candidate != null) {
                        break;
                    }
                }
            }
        }

        //If candidate is still null, all attempts failed. Give up.
        if (candidate == null) {
            return false;
        }

        //Find the axis
        BlockPos firstPos = candidate.getFirst();
        Direction.Axis axis = null;
        for (Direction direction : Direction.values()) {
            if (candidate.contains(firstPos.relative(direction))) {
                axis = direction.getAxis();
                break;
            }
        }
        if (axis == null) {
            return false;
        }


        //Placement
        for (BlockPos pos : candidate) {
            BlockState placeState = context.config().trunkProvider().getState(context.random(), pos);
            if (placeState.getBlock() instanceof RotatedPillarBlock) {
                placeState = placeState.setValue(RotatedPillarBlock.AXIS, axis);
            }

            if (context.level().getBlockState(pos).canBeReplaced() && context.level() instanceof Level) {
                context.level().removeBlock(pos, true);
            }

            context.level().setBlock(pos, placeState, 2);
        }

        return true;
    }

    //Given a point, tries generating logs twice in each horizontal axis. Returns the list if it succeeds and null if it fails.
    private ArrayList<BlockPos> tryTwicePerAxis(BlockPos originPos, int buried, FeaturePlaceContext<FallenLogConfiguration> context, ArrayList<LogPlacementTestResult> results, RandomSource randomSource) {
        Direction dir = DirectionUtil.randomHorizontalDirection(context.random());
        Direction.Axis axis = dir.getAxis();

        ArrayList<BlockPos> candidate;

        for (int i = 0; i < 2; i++) {
            candidate = generateLogPlacement(originPos, dir, context, context.random());
            LogPlacementTestResult result = testPlacement(candidate, axis, context, buried);
            if (result == LogPlacementTestResult.CLEAR) {
                return candidate;
            } else {
                results.add(result);
            }
        }

        dir = dir.getClockWise();
        axis = dir.getAxis();

        for (int i = 0; i < 2; i++) {
            candidate = generateLogPlacement(originPos, dir, context, context.random());
            LogPlacementTestResult result = testPlacement(candidate, axis, context, buried);
            if (result == LogPlacementTestResult.CLEAR) {
                return candidate;
            } else {
                results.add(result);
            }
        }
        return null;
    }

    //Generates a set of BlockPos as a candidate log placement.
    private ArrayList<BlockPos> generateLogPlacement(BlockPos originPos, Direction dir, FeaturePlaceContext<FallenLogConfiguration> context, RandomSource randomSource) {

        int length = context.config().length().sample(randomSource);
        int half_length = (length - 1) / 2;

        ArrayList<BlockPos> output = new ArrayList<>();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        mutableBlockPos.set(originPos.relative(dir.getOpposite(), half_length));
        for (int i = 0; i < length; i++) {
            output.add(mutableBlockPos.immutable());
            mutableBlockPos.move(dir);
        }

        return output;
    }

    /**
     * UNSUPPORTEDNESS:
     * - The number of unsupported blocks on either end can't be more than 1/3 the log's length
     * - The total number of unsupported blocks on the ends can't be more than 1/2 the log's length
     * <p>
     * OBSTRUCTEDNESS:
     * - The log can't be placed under blocks unless buried or partially buried. If partially buried no more than half of the blocks can be under a block.
     * - It can't be placed in blocks except certain specific ones (dirt, podzol, gravel, that kind of thing)
     * - It can't replace a block flanked by two solid blocks in the direction perpendicular to the log's axis unless buried or partially buried
     */

    //Tests a set of blocks to decide whether to place the log there.
    //Returns CLEAR if it should do so.
    //Returns OBSTRUCTED if there's stuff in the way (try higher).
    //Returns UNSUPPORTED if there isn't stuff under it (try lower).
    private LogPlacementTestResult testPlacement(ArrayList<BlockPos> candidate, Direction.Axis axis, FeaturePlaceContext<FallenLogConfiguration> context, int buried) {

        //Check unsupported
        double length = candidate.size();
        double max_unsupported_one_end = length / 3.0;
        double max_unsupported_ends_total = length / 2.0;

        double unsupported_at_first_end = 0;
        double unsupported_at_second_end = 0;
        for (BlockPos pos : candidate) {
            BlockState belowState = context.level().getBlockState(pos.below());
            if (!belowState.isFaceSturdy(context.level(), pos.below(), Direction.UP)) {
                unsupported_at_first_end++;
            } else {
                break;
            }
        }
        for (BlockPos pos : candidate.reversed()) {
            BlockState belowState = context.level().getBlockState(pos.below());
            if (!belowState.isFaceSturdy(context.level(), pos.below(), Direction.UP)) {
                unsupported_at_second_end++;
            } else {
                break;
            }
        }

        if (unsupported_at_first_end > max_unsupported_one_end || unsupported_at_second_end > max_unsupported_one_end) {
            return LogPlacementTestResult.UNSUPPORTED;
        } else if (unsupported_at_first_end + unsupported_at_second_end > max_unsupported_ends_total) {
            return LogPlacementTestResult.UNSUPPORTED;
        }


        //Check obstructed
        int num_buried_blocks = 0;
        double max_buried = 0;
        if (buried == 1) {
            max_buried = length / 2;
        } else if (buried == 2) {
            max_buried = length;
        }
        boolean direct_obstruction = false;
        boolean flanked_obstruction = false;

        for (BlockPos pos : candidate) {
            if (!context.level().getBlockState(pos).canBeReplaced() && !context.level().getBlockState(pos).is(SBTags.Blocks.FALLEN_LOGS_REPLACE)) {
                direct_obstruction = true;
                break;
            }
            if (!context.level().getBlockState(pos.above()).canBeReplaced()) {
                num_buried_blocks++;
            }
            Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.NORTH;
            BlockPos flank1 = pos.relative(dir.getClockWise());
            BlockPos flank2 = pos.relative(dir.getCounterClockWise());

            if (!context.level().getBlockState(flank1).canBeReplaced() && !context.level().getBlockState(flank2).canBeReplaced()) {
                flanked_obstruction = true;
            }
        }

        if (direct_obstruction) {
            return LogPlacementTestResult.OBSTRUCTED;
        } else if (flanked_obstruction && buried == 0) {
            return LogPlacementTestResult.OBSTRUCTED;
        } else if (num_buried_blocks > max_buried) {
            return LogPlacementTestResult.OBSTRUCTED;
        }

        return LogPlacementTestResult.CLEAR;
    }

    private enum LogPlacementTestResult {
        OBSTRUCTED,
        UNSUPPORTED,
        CLEAR
    }
}
