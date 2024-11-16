package com.syric.shores_between.worldgen.feature.stump;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.util.DirectionUtil;
import com.syric.shores_between.worldgen.feature.log.FallenLogConfiguration;
import com.syric.shores_between.worldgen.feature.tree.NBTTreeConfiguration;
import com.syric.shores_between.worldgen.feature.tree.NBTTreeFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;

public class StumpFeature extends Feature<StumpConfiguration> {

    public StumpFeature(Codec<StumpConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StumpConfiguration> context) {
        RandomSource randomSource = context.random();
        WorldGenLevel level = context.level();
        BlockPos tree_origin = context.origin();
        StumpConfiguration config = context.config();

        //TODO check if buried. If so, displace all block placements down a ways.

        //Get height
        int height = config.height().sample(randomSource);

        //Check that no log is obstructed
        BlockPos.MutableBlockPos logObstructionChecker = new BlockPos.MutableBlockPos().set(tree_origin);
        for (int i = 0; i < height; i++) {
            if (!level.getBlockState(logObstructionChecker).canBeReplaced()) {
                return false;
            }
            logObstructionChecker.move(Direction.UP);
        }

        //Place main trunk logs
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos().set(tree_origin);
        for (int i = 0; i < height; i++) {
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

            int[] buttress_heights = NBTTreeFeature.getButtressHeights(buttress_count, config.buttressHeight(), randomSource);

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


        return true;
    }


}
