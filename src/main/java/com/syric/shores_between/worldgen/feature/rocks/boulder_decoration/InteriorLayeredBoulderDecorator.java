package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class InteriorLayeredBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<InteriorLayeredBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            BlockState.CODEC.listOf().fieldOf("layers").forGetter(x -> x.layers),
                            Codec.intRange(0, 2).fieldOf("layer_thickness").forGetter(x -> x.layer_thickness),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, InteriorLayeredBoulderDecorator::new)
    );
    private final List<BlockState> layers;
    private final int layer_thickness; //0 if only cardinal directions, 1 for also edges, 2 for also corners. None apply to the initial layer.
    private final float probability;

    public InteriorLayeredBoulderDecorator(List<BlockState> layers, int layer_thickness, float probability) {
        this.layers = layers;
        this.layer_thickness = layer_thickness;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.INTERIOR_LAYERED.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability) {
            return;
        }

        //Make lists of BlockPos that are on the interior.
        int number_of_layers = this.layers.size();
        List<Set<BlockPos>> layer_sets = new ArrayList<>();

        Set<BlockPos> full_blocks = new HashSet<>();
        placeTypeMap.entrySet().stream().filter(x -> x.getValue() == PlaceTypes.PlaceType.FULL_BLOCK).forEach(x -> full_blocks.add(x.getKey()));

        Set<BlockPos> previous = full_blocks;
//        ShoresBetween.LOGGER.debug("Generating concentric layers. Starting set contains {} blocks", previous.size());
        //A repeating loop that comes up with a set of blocks fully inside the previous set
        for (int i = 0; i < number_of_layers; i++) {

            Set<BlockPos> new_set = new HashSet<>();
            int finalI = i;
            Set<BlockPos> finalPrevious = previous;
            finalPrevious.forEach(candidate_pos -> {
                //This block of code decides whether to include the position in the new set.

                //Calculate if any blocks in a 3x3 space aren't in the previous set
                //Add these to an 'empty positions' set
                BlockPos negPos = candidate_pos.offset(-1, -1, -1);
                BlockPos posPos = candidate_pos.offset(1, 1, 1);
                Set<BlockPos> emptyPositions = new HashSet<>();
                BlockPos.betweenClosedStream(negPos, posPos).filter(pos1 -> !finalPrevious.contains(pos1)).forEach(pos -> emptyPositions.add(pos.immutable()));
//                ShoresBetween.LOGGER.debug("There are {} empty positions around the candidate", emptyPositions.size());
                //If no empty positions, we're done
                if (emptyPositions.isEmpty()) {
                    new_set.add(candidate_pos.immutable());
                    return;
                }

                //Once I have all the empty positions, I find the closest one
                Optional<BlockPos> closest_position = emptyPositions.stream().min(Comparator.comparingDouble(pos -> pos.distToCenterSqr(candidate_pos.getCenter())));
                double shortest_distance_squared = closest_position.get().distToCenterSqr(candidate_pos.getCenter());

//                ShoresBetween.LOGGER.debug("Shortest distance squared: {}", shortest_distance_squared);

                //Squared distance is 1 if it's adjacent via a face, 2 if it's adjacent via an edge, 3 if it's adjacent via a corner.
                boolean success = false;
                //Side exposure is never OK; leave success false if shortest distance is 1
                if (shortest_distance_squared == 2) {
                    //Edge exposure is OK if it's the first layer or thickness is 0
                    success = (finalI == 0 || layer_thickness == 0);
                } else if (shortest_distance_squared == 3) {
                    //Corner exposure is OK if it's the first layer or thickness is 0 or 1
                    success = (finalI == 0 || layer_thickness < 2);
                }

                if (success) {
                    new_set.add(candidate_pos.immutable());
//                    ShoresBetween.LOGGER.debug("Added position to new layer");
                } else {
//                    ShoresBetween.LOGGER.debug("Failed to add position to new layer");
                }
            });

            //We now have the new set. If it's empty, we're done.
            if (new_set.isEmpty()) {
//                ShoresBetween.LOGGER.debug("New layer is nonexistent, aborting");
                break;
            } else {
//                ShoresBetween.LOGGER.debug("Generated new layer containing {} blocks", new_set.size());
                layer_sets.add(new_set);
                previous = new_set;
            }

        }

        //We now have a list of concentric layer sets. Put them in the placement map.
        for (int i = 0; i < layer_sets.size(); i++) {
            BlockState placeState = layers.get(i);
            for (BlockPos pos : layer_sets.get(i)) {
                if (placementMap.containsKey(pos)) {
                    placementMap.put(pos, placeState);
                }
            }
        }

    }
}
