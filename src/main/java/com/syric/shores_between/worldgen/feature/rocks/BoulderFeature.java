package com.syric.shores_between.worldgen.feature.rocks;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBTags;
import com.syric.shores_between.worldgen.feature.rocks.boulder_decoration.BoulderDecorator;
import com.syric.shores_between.worldgen.feature.rocks.boulder_decoration.BoulderDecoratorType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes.PlaceType;

import java.util.*;

public class BoulderFeature extends Feature<BoulderConfiguration> {

    public BoulderFeature(Codec<BoulderConfiguration> codec) {
        super(codec);
    }

    //TODO: can I make boulders place or not place depending on the rockiness or vitality of their location?



    @Override
    public boolean place(FeaturePlaceContext<BoulderConfiguration> context) {
        RandomSource randomSource = context.random();
        WorldGenLevel level = context.level();
        BoulderConfiguration config = context.config();

        HashMap<BlockPos, PlaceType> placeTypeMap = new HashMap<>();
        HashMap<BlockPos, BlockState> placementMap = new HashMap<>();
        Set<BlockPos> undercutSet = new HashSet<>();
        HashMap<BlockPos, BlockState> decorationPlacementMap = new HashMap<>();


        //Add X boulders to the placeType map, where X is the number of overlaps
        //Outcrops can't overlap
        int overlaps = config.overlaps().sample(randomSource);
        if (config.outcrop() || overlaps <= 0) {
            overlaps = 1;
        }
        for (int i = 0; i < overlaps; i++) {
            generateBoulder(placeTypeMap, config, context, randomSource);
        }

        //Handle the undercut, if present
        if (!config.decorators().isEmpty()) {
            //(there should only be one, but you never know)
            config.decorators().stream().filter(x -> x.type() == BoulderDecoratorType.UNDERCUT.get()).forEach(x -> {
                BoulderDecorator.Context decorator_context = new BoulderDecorator.Context(level, randomSource, context.origin(), config);
                x.place(decorator_context, placeTypeMap, placementMap, undercutSet, decorationPlacementMap);
            });
        }

        //Check blocks for placement. If it's a solid block in the world, and *not* going to be removed by undercutting, update slabs to be full blocks.
        for (Map.Entry<BlockPos, PlaceType> entry : placeTypeMap.entrySet()) {
            //If it's going to place a slab,
            if (entry.getValue() != PlaceType.FULL_BLOCK) {
                //And it isn't going to be undercut,
                if (!undercutSet.contains(entry.getKey())) {
                    //And that position isn't air (or another replaceable block),
                    if (!level.getBlockState(entry.getKey()).canBeReplaced()) {
                        //Then switch the place type to full block.
                        entry.setValue(PlaceType.FULL_BLOCK);
                    }
                }
            }
        }

        //Set all placement blockstates to the appropriate defaults
        for (BlockPos pos : placeTypeMap.keySet()) {
            if (placeTypeMap.get(pos) == PlaceType.FULL_BLOCK) {
                placementMap.put(pos, config.rockProvider().getState(randomSource, pos));
            } else if (placeTypeMap.get(pos) == PlaceType.UPPER_SLAB) {
//                ShoresBetween.LOGGER.debug("Placing an upper slab");
                placementMap.put(pos, config.slabProvider().getState(randomSource, pos).setValue(SlabBlock.TYPE, SlabType.TOP).setValue(SlabBlock.WATERLOGGED, level.isFluidAtPosition(pos, FluidState::isSource)));
            } else if (placeTypeMap.get(pos) == PlaceType.LOWER_SLAB) {
//                ShoresBetween.LOGGER.debug("Placing a lower slab");
                placementMap.put(pos, config.slabProvider().getState(randomSource, pos).setValue(SlabBlock.TYPE, SlabType.BOTTOM).setValue(SlabBlock.WATERLOGGED, level.isFluidAtPosition(pos, FluidState::isSource)));
            }
        }

        //Do the non-undercut decorators
        if (!config.decorators().isEmpty()) {
            //Then, everything else
            config.decorators().stream().filter(x -> x.type() != BoulderDecoratorType.UNDERCUT.get()).forEach(x -> {
                BoulderDecorator.Context decorator_context = new BoulderDecorator.Context(level, randomSource, context.origin(), config);
                x.place(decorator_context, placeTypeMap, placementMap, undercutSet, decorationPlacementMap);
            });
        }

        //Place the boulder blocks
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (Map.Entry<BlockPos, BlockState> entry : placementMap.entrySet()) {
            mutableBlockPos.set(entry.getKey());
            BlockState state = entry.getValue();

            if (level.getBlockState(mutableBlockPos).canBeReplaced()) {
                if (level instanceof Level) {
                    level.removeBlock(mutableBlockPos, true);
                }
            }

            level.setBlock(mutableBlockPos, state, 2);

        }

        //Place the undercut
        BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
        for (BlockPos pos : undercutSet) {
            mutableBlockPos2.set(pos);
            BlockState state = pos.getY() < 63 ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();

            if (level.getBlockState(mutableBlockPos2).is(SBTags.Blocks.BREACH_GROUND)) {
                level.setBlock(mutableBlockPos2, state, 2);
            }
        }

        //Place the decoration blocks
        BlockPos.MutableBlockPos mutableBlockPos3 = new BlockPos.MutableBlockPos();
        for (Map.Entry<BlockPos, BlockState> entry : decorationPlacementMap.entrySet()) {
            mutableBlockPos3.set(entry.getKey());
            BlockState state = entry.getValue();

            if (level.getBlockState(mutableBlockPos3).canBeReplaced()) {
                if (level instanceof Level) {
                    level.removeBlock(mutableBlockPos3, true);
                }
            }

            level.setBlock(mutableBlockPos3, state, 2);

        }

        return true;
    }

    //Generates a boulder and adds it to the placement map. (Simply adds full / topslab / bottomslab.)
    private void generateBoulder(HashMap<BlockPos, PlaceType> input, BoulderConfiguration config, FeaturePlaceContext<BoulderConfiguration> context, RandomSource randomSource) {
        //Create parameters for unrotated boulder
        BoulderParameters parameters = BoulderParameters.getBoulderParameters(context, randomSource);

        //Outcrops should get buried, i.e. have their displacement biased lower.
        //TODO: This should be revisited!!! Possibly it's bad to bury outcrops 'cause their clefts're too low already.
        //Probably other scenarios should bury as well.
        boolean bury = config.outcrop();

        //Create transformation to rotate boulder
        BoulderTransformer transformer = BoulderTransformer.getBoulderTransformer(context, randomSource, false, config.outcrop());


        //Generate placing map (draft version that only uses full blocks)
        //First, find the whole box to test
        double potential_radius = transformer.scale() * parameters.maxStretch() * parameters.cutoff();
        Vec3 first_pos = transformer.displaced_center().add(-potential_radius, -potential_radius, -potential_radius);
        Vec3 second_pos = transformer.displaced_center().add(potential_radius, potential_radius, potential_radius);
        //Outcrops are missing most of their top half, so we trim them down to save time by removing half a potential radius above.
        if (config.outcrop()) {
            second_pos = second_pos.add(0, -0.5 * potential_radius, 0);
        }

        HashMap<BlockPos, PlaceType> new_map = new HashMap<>();

        //Analyze every position
        BlockPos.betweenClosedStream(BlockPos.containing(first_pos), BlockPos.containing(second_pos)).forEach(pos -> {

            //First, test the center of the block. If it's true, put a full block there.
            Vec3 center_position = pos.getCenter();
            boolean center = parameters.testPoint(transformer.transformPoint(center_position));
            if (center) {
                new_map.put(pos.immutable(), PlaceType.FULL_BLOCK);
                return;
            }

            //If not, and slabs are enabled, check the top and bottom separately.
            //In the wildly unlikely scenario that the center's false but the top and bottom are both true, that's a full block also.
            if (config.useSlabs()) {
                Vec3 top_half_position = pos.getCenter().add(0, 0.25, 0);
                Vec3 bottom_half_position = pos.getCenter().add(0, -0.25, 0);
                boolean top_half = parameters.testPoint(transformer.transformPoint(top_half_position));
                boolean bottom_half = parameters.testPoint(transformer.transformPoint(bottom_half_position));
                if (top_half && bottom_half) {
                    new_map.put(pos.immutable(), PlaceType.FULL_BLOCK);
                } else if (top_half) {
                    new_map.put(pos.immutable(), PlaceType.UPPER_SLAB);
                } else if (bottom_half) {
                    new_map.put(pos.immutable(), PlaceType.LOWER_SLAB);
                }
            }

        });

        //Combine new and old maps
        new_map.forEach((pos, newType) -> {
            PlaceType existingType = input.get(pos);
            //If either's a full block, full block.
            if (existingType == PlaceType.FULL_BLOCK || newType == PlaceType.FULL_BLOCK) {
                input.put(pos, PlaceType.FULL_BLOCK);
            }
            //If they're the two opposite slabs, full block.
            else if (existingType == PlaceType.LOWER_SLAB && newType == PlaceType.UPPER_SLAB) {
                input.put(pos, PlaceType.FULL_BLOCK);
            } else if (newType == PlaceType.LOWER_SLAB && existingType == PlaceType.UPPER_SLAB) {
                input.put(pos, PlaceType.FULL_BLOCK);
            }
            //Otherwise, put the new in (either it matches, or there's nothing there yet).
            else {
                input.put(pos, newType);
            }
        });
    }


}

