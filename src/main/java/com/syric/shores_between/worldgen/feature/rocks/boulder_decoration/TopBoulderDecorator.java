package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

public class TopBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<TopBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            FloatProvider.CODEC.fieldOf("proportion").forGetter(x -> x.proportion),
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            BlockStateProvider.CODEC.fieldOf("slab_provider").forGetter(config -> config.slab_provider),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, TopBoulderDecorator::new)
    );
    private final FloatProvider proportion;
    private final BlockStateProvider block_provider;
    private final BlockStateProvider slab_provider;
    private final float probability;


    public TopBoulderDecorator(FloatProvider proportion, BlockStateProvider block_provider, BlockStateProvider slab_provider, float probability) {
        this.proportion = proportion;
        this.block_provider = block_provider;
        this.slab_provider = slab_provider;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.TOP.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability) {
            return;
        }

        //Collect a list of the top blocks
        Set<BlockPos> targets = new HashSet<>();

        for (BlockPos pos : placeTypeMap.keySet()) {
            //Block is on top if the block above is not in the set
            boolean top = !placeTypeMap.containsKey(pos.above());
            if (top) {
                targets.add(pos);
            }
        }

        //Further refine selection by proportion
        float prop = proportion.sample(randomSource);
        targets.removeIf(pos -> randomSource.nextFloat() > prop);

        //Update all the blocks
        for (BlockPos pos : targets) {
            BlockState state;
            switch (placeTypeMap.get(pos)) {
                case FULL_BLOCK -> state = block_provider.getState(randomSource, pos);
                case LOWER_SLAB -> { state = slab_provider.getState(randomSource, pos);
                                        if (state.hasProperty(SlabBlock.TYPE)) {
                                            state = state.setValue(SlabBlock.TYPE, SlabType.BOTTOM);
                                        }
                                    }
                case UPPER_SLAB -> {
                    state = slab_provider.getState(randomSource, pos);
                    if (state.hasProperty(SlabBlock.TYPE)) {
                        state = state.setValue(SlabBlock.TYPE, SlabType.TOP);
                    }
                }                case null -> state = block_provider.getState(randomSource, pos);
            }
            if (state.isAir()) {
                continue;
            }
//            ShoresBetween.LOGGER.debug("Placing a Top Block on the boulder");
            placementMap.put(pos, state);
        }
    }
}
