package com.syric.shores_between.worldgen.feature.patch;

import com.mojang.serialization.Codec;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.Tags;
import oshi.util.platform.unix.openbsd.FstatUtil;

import java.util.*;

public class PatchFeature extends Feature<PatchConfiguration> {

    /**
     * Patches are for washed-up bunches of stuff - seaweed, dead fish, that kind of thing.
     * Their base shape is defined by noise and limited by a radius cap.
     * Within this, they get thicker as they go away from the edge. Thickness is always measured in pixels - 1 for carpet, 8 for slab, 16 for block.
     */

    public PatchFeature(Codec<PatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PatchConfiguration> context) {

        PatchConfiguration config = context.config();
        RandomSource randomSource = context.random();

        int slope_length = config.slope_length().sample(randomSource);
        float edge_thickness = config.edge_thickness().sample(randomSource);
        float center_thickness = config.center_thickness().sample(randomSource);


        //Start by generating a shape with the generateShape method.
//        ShoresBetween.LOGGER.debug("Placing patch feature at origin " + context.origin());
        Set<BlockPos> black_and_white_shape = generateShape(config, context, randomSource);
//        ShoresBetween.LOGGER.debug("Generated black and white shape with " + black_and_white_shape.size() + " blocks");


        //We don't want a large vertical spread. The default height should be no more than 1 block above the lowest point (so it's spread across 2 y levels, max).
        //Find the lowest point. Then either cancel the attempt or lower the default height if necessary.
        int default_height = context.origin().getY();
        int min_height = 1000;
        for (BlockPos pos : black_and_white_shape) {
            int world_height = context.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
            min_height = Math.min(min_height, world_height);
        }
//        ShoresBetween.LOGGER.debug("Default height: " + default_height + ", minimum height in area: " + min_height);
        //Cancel the attempt if there's a drop of more than 5
        if (default_height - min_height > 5) {
//            ShoresBetween.LOGGER.debug("Cancelling patch placement; too great a height differential");
            return false;
        }
        //Cancel the attempt if the chosen location is buried
        if (default_height - min_height < 0) {
//            ShoresBetween.LOGGER.debug("Cancelling patch placement; origin is buried");
            return false;
        }
        //If neither, lower the default height to 2 above the minimum if necessary
        default_height = Math.min(default_height, min_height + 1);
//        ShoresBetween.LOGGER.debug("Shifting default height to " + default_height);

        //The black and white shape gets relocated to the appropriate height
        Set<BlockPos> relocated_shape = new HashSet<>();
        for (BlockPos pos : black_and_white_shape) {
            relocated_shape.add(new BlockPos(pos.getX(), default_height, pos.getZ()));
        }
        black_and_white_shape = relocated_shape;
//        ShoresBetween.LOGGER.debug("Relocated black and white shape has " + black_and_white_shape.size() + " blocks");


        //Then, assign heights to each BlockPos.
            //First, find all the ones on the edge. Give them the edge height.
            //Find all the ones adjacent to them. Give them the edge height, plus 1/n of the difference to the interior height, with n being the radius.
            //Repeat until you're at the interior height; give all remaining BlockPos' that height.
            //For each pos, calculate sparseness and remove it (but only if it's positive).

        HashMap<BlockPos, Float> height_map = new HashMap<>(); //This is a map of all positions in the shape to the thickness of the patch there in pixels.

        //Loop that steadily moves inwards. Each time it collects all blocks on the edge, assigns linearly-interpolated heights, and removes them.
        Set<BlockPos> removable_set = new HashSet<>(black_and_white_shape);
        for (int i = 0; i <= slope_length; i++) {
            float height = Mth.lerp((float) i / slope_length, edge_thickness, center_thickness);
            Set<BlockPos> collection_set = new HashSet<>();

            //Go through the removable set. Anything with a laterally adjacent block not in the set goes in the collection set
            //Also, once i is the slope length, everything goes in the collection set
            for (BlockPos pos : removable_set) {

                if (i == slope_length) {
                    collection_set.add(pos);
                    continue;
                }

                for (Direction direction : Direction.values()) {
                    if (direction.getAxis() == Direction.Axis.Y) {
                        continue;
                    }

                    if (!removable_set.contains(pos.relative(direction))) {
                        collection_set.add(pos);
                        break;
                    }
                }
            }

            //Everything in the collection set gets its height set
            //and gets removed from the removable set
            for (BlockPos pos : collection_set) {
                height_map.put(pos, height);
                removable_set.remove(pos);
            }
        }

//        ShoresBetween.LOGGER.debug("Initial height map contains " + height_map.size() + " blocks");

        //Calculate sparseness for all blocks and remove it
        height_map.forEach((pos, thickness) -> {
            float sparse = config.sparseness().sample(randomSource);
            if (sparse > 0) {
                height_map.put(pos, thickness - sparse);
            }
        });

//        ShoresBetween.LOGGER.debug("After sparseness, height map contains " + height_map.size() + " blocks");

        //Start placing it in the world.
            //Assess each blockPos. Make a map of the world height relative to each: -1 for occupied, 0 for solid block under, 1 for one air block under, 2 for water under (any height) or a nonsolid block
            //Everything with -1 or 2 gets deleted.
            //Everything with 0 bleeds 2-3 pixels to each neighboring 1 to represent stuff sliding downhill. The recipient gets double the loss (positive-sum).
            //Make sure the recipient is no more than 16 higher than the donor.
        HashMap<BlockPos, Integer> underMap = new HashMap<>();
        height_map.keySet().forEach(blockPos -> {
            BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos();
            scanPos.set(blockPos);

            //Analyze block at default height
            BlockState currentState = context.level().getBlockState(scanPos);
            Block currentBlock = currentState.getBlock();
            if (currentBlock == Blocks.WATER) {
                underMap.put(blockPos, 2);
                return;
            } else if (!currentState.isAir()) {
                underMap.put(blockPos, -1);
                return;
            }

            //Analyze block below
            scanPos.move(Direction.DOWN);
            currentState = context.level().getBlockState(scanPos);
            if (currentState.getBlock() == Blocks.WATER || !currentState.isFaceSturdy(context.level(), scanPos, Direction.UP)) {
                underMap.put(blockPos, 2);
                return;
            }
            if (!currentState.isAir()) {
                underMap.put(blockPos, 0);
                return;
            }

            //Analyze block two below. Should be water or solid, or you messed up earlier.
            scanPos.move(Direction.DOWN);
            currentState = context.level().getBlockState(scanPos);
            if (currentState.getBlock() == Blocks.WATER || !currentState.isFaceSturdy(context.level(), scanPos, Direction.UP)) {
                underMap.put(blockPos, 2);
                return;
            }
            if (!currentState.isAir()) {
                underMap.put(blockPos, 1);
            }
            else {
//                ShoresBetween.LOGGER.error("Patch did not have solid block within two vertical blocks");
            }

        });

//        ShoresBetween.LOGGER.debug("After block-under checks, height map contains " + height_map.size() + " blocks");

        //Remove everything that's obstructed or over water
        height_map.entrySet().removeIf(entry -> {
            int undervalue = underMap.get(entry.getKey());
            return undervalue == 2 || undervalue == -1;
        });

//        ShoresBetween.LOGGER.debug("After removing illegal blocks, height map contains " + height_map.size() + " blocks");

        //Everything at a 0 bleeds 2-3 pixels to any neighboring 1s
        height_map.entrySet().stream().filter(entry -> underMap.get(entry.getKey()) == 0)
                .forEach(entry -> {
                    BlockPos pos = entry.getKey();
                    float height = entry.getValue();
                    for (Direction direction : Direction.values()) {
                        if (direction.getAxis() == Direction.Axis.Y) {
                            continue;
                        }
                        BlockPos adjacent_pos = pos.relative(direction);
                        if (!height_map.containsKey(adjacent_pos)) {
                            continue;
                        }
                        float adjacent_pos_height = height_map.get(adjacent_pos);
                        if (height_map.containsKey(adjacent_pos)) {
                            if (underMap.get(adjacent_pos) == 1) {
                                if (adjacent_pos_height < height + 13) {
                                    float bleed = randomSource.nextFloat() + 2;
                                    bleed = Math.min(bleed, height);
                                    height_map.put(pos, height - bleed);
                                    height_map.put(adjacent_pos, adjacent_pos_height + bleed + bleed);
                                }
                            }
                        }
                    }
                });

        //Remove any negative heights
//        ShoresBetween.LOGGER.debug("After slope bleed, height map contains " + height_map.size() + " blocks");
        height_map.entrySet().removeIf(entry -> entry.getValue() < 0);
//        ShoresBetween.LOGGER.debug("After removing negatives, height map contains " + height_map.size() + " blocks");

        //Generate final placement map. Any negative heights are removed. Other heights are rounded to 1, 8, 16, 17, or 24.
        HashMap<BlockPos, BlockState> placement_map = new HashMap<>();
        height_map.forEach((pos, height) -> {
            if (height < 4.5) {
                placement_map.put(pos, config.carpetProvider().getState(randomSource, pos));
            } else if (height < 12) {
                placement_map.put(pos, config.slabProvider().getState(randomSource, pos));
            } else {
                placement_map.put(pos, config.blockProvider().getState(randomSource, pos));
            }
        });

//        ShoresBetween.LOGGER.debug("Placement map generated with " + placement_map.size() + " blocks");

        //Drop blocks to ground
        //Theoretically this only needs to be run once 'cause it shouldn't be more than 1 block in the air anywhere but just to be sure
        for (int i = 0; i < 3; i++) {
            HashSet<BlockPos> blocksToDrop = new HashSet<>();
            placement_map.forEach((pos, state) -> {
                if (context.level().getBlockState(pos.below()).isAir()) {
                    blocksToDrop.add(pos);
                }
            });
            if (blocksToDrop.isEmpty()) {
                break;
            }
            blocksToDrop.forEach(pos -> {
                placement_map.put(pos.below(), placement_map.get(pos));
                placement_map.remove(pos);
            });
        }

        //Perform placement safety checks
        placement_map.forEach((pos, state) -> {
            if (!context.level().getBlockState(pos).is(BlockTags.REPLACEABLE)) {
//                ShoresBetween.LOGGER.debug("Removing patch block because block " + context.level().getBlockState(pos).getBlock() + " is not replaceable");
                placement_map.remove(pos);
            }
        });

        //Place all blocks
        //Place the decoration blocks
        WorldGenLevel level = context.level();
        BlockPos.MutableBlockPos mutableBlockPos3 = new BlockPos.MutableBlockPos();
        for (Map.Entry<BlockPos, BlockState> entry : placement_map.entrySet()) {
            mutableBlockPos3.set(entry.getKey());
            BlockState state = entry.getValue();

            if (level.getBlockState(mutableBlockPos3).canBeReplaced()) {
                if (level instanceof Level) {
                    level.removeBlock(mutableBlockPos3, true);
                }
            }
            level.setBlock(mutableBlockPos3, state, 2);
//            ShoresBetween.LOGGER.debug("Placed a block");
        }


        return true;
    }

    private Set<BlockPos> generateShape(PatchConfiguration config, FeaturePlaceContext<PatchConfiguration> context, RandomSource randomSource) {

        //Create a flat square AABB according to the max radius
        int max_radius = config.size_cap().sample(randomSource);
        AABB area = AABB.ofSize(context.origin().getCenter(), max_radius * 2, 1, max_radius * 2);

        //Within that, generate a black-and-white noise map with the config's noise provider. Select whichever color includes the origin.
        HashMap<BlockPos, Boolean> black_and_white = new HashMap<>();
        BlockPos.betweenClosedStream(area).forEach(pos -> {
//            ShoresBetween.LOGGER.debug("Generating black-and-white map at position " + pos);
            //Generate black or white using the noise function (white is true)
            black_and_white.put(pos.immutable(), config.noiseProvider().getState(randomSource, pos) == Blocks.WHITE_CONCRETE.defaultBlockState());
        });

        //Return a set containing all contiguous blocks of that color.
        HashSet<BlockPos> output = new HashSet<>();
        HashSet<BlockPos> new_candidates = new HashSet<>();
        boolean origin = black_and_white.get(context.origin());

        new_candidates.add(context.origin());
        boolean incomplete = true;
        while (incomplete) {
            HashSet<BlockPos> fresh_candidates = new HashSet<>();
            //For every block in the 'new candidates' list, collect all blocks adjacent to them that match the color and haven't already been collected.
            for (BlockPos pos : new_candidates) {
                for (Direction direction : Direction.values()) {
                    BlockPos testPos = pos.relative(direction);
                    //No vertical adjacency
                    if (direction.getAxis() == Direction.Axis.Y) {
                        continue;
                    }
                    //Only things in the black and white map
                    if (!black_and_white.containsKey(testPos)) {
                        continue;
                    }
                    //Only things that match the origin's color
                    if (black_and_white.get(testPos) != origin) {
                        continue;
                    }
                    //Only things that haven't yet been collected
                    if (output.contains(testPos)) {
                        continue;
                    }
                    //Only things that are close enough
                    if (testPos.getCenter().distanceTo(context.origin().getCenter()) > max_radius) {
                        continue;
                    }

                    fresh_candidates.add(pos.relative(direction));
                }
            }

            //Then, put all the candidates into the output (they're done), and use the fresh candidates as the new candidates for the next loop.
            output.addAll(new_candidates);
            new_candidates = fresh_candidates;

            //If there are no new candidates, we're done.
            if (new_candidates.isEmpty()) {
                incomplete = false;
            }
        }

        return output;
    }

}
