package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AboveBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<AboveBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            FloatProvider.CODEC.fieldOf("proportion").forGetter(x -> x.proportion),
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            Codec.BOOL.fieldOf("place_on_slabs").forGetter(x -> x.place_on_slabs),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, AboveBoulderDecorator::new)
    );
    private final FloatProvider proportion;
    private final BlockStateProvider block_provider;
    private final boolean place_on_slabs;
    private final float probability;


    public AboveBoulderDecorator(FloatProvider proportion, BlockStateProvider block_provider, boolean place_on_slabs, float probability) {
        this.proportion = proportion;
        this.block_provider = block_provider;
        this.place_on_slabs = place_on_slabs;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.ABOVE.get();
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
            boolean ok_to_place = placeTypeMap.get(pos) != PlaceTypes.PlaceType.LOWER_SLAB || place_on_slabs;
            if (top && ok_to_place) {
                targets.add(pos.above());
            }
        }

        //Further refine selection by proportion
        float prop = proportion.sample(randomSource);
        targets.removeIf(pos -> randomSource.nextFloat() > prop);

        //Update all the blocks
        for (BlockPos pos : targets) {
            BlockState state = block_provider.getState(randomSource, pos);
            decorationPlacementMap.put(pos, state);
        }
    }
}
