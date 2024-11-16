package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.*;

public class InteriorScatterBoulderDecorator extends BoulderDecorator {

    public static final Codec<Set<Block>> BLOCK_SET_CODEC = Codec.list(BuiltInRegistries.BLOCK.byNameCodec()).xmap(ObjectOpenHashSet::new, ArrayList::new);

    public static final MapCodec<InteriorScatterBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            Codec.floatRange(0.0F, 5.0F).fieldOf("proportion").forGetter(x -> x.proportion),
                            BLOCK_SET_CODEC.fieldOf("replaceable").forGetter(config -> config.replaceable),
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            Codec.BOOL.fieldOf("interior_only").forGetter(config -> config.interior_only),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, InteriorScatterBoulderDecorator::new)
    );
    private final float proportion;
    private final Set<Block> replaceable;
    private final BlockStateProvider block_provider;
    private final boolean interior_only;
    private final float probability;


    public InteriorScatterBoulderDecorator(float proportion, Set<Block> replaceable, BlockStateProvider block_provider, boolean interior_only, float probability) {
        this.proportion = proportion;
        this.replaceable = replaceable;
        this.block_provider = block_provider;
        this.interior_only = interior_only;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.INTERIOR_SCATTER.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability) {
            return;
        }

        //Collect a list of the full blocks
        Set<BlockPos> initial_targets = new HashSet<>();
        placeTypeMap.entrySet().stream().filter(x -> x.getValue() == PlaceTypes.PlaceType.FULL_BLOCK).forEach(x -> initial_targets.add(x.getKey()));

        //If interior only, collect the ones that are only adjacent to other ones in the set
        Set<BlockPos> refined_targets = new HashSet<>();

        if (interior_only) {
            for (BlockPos pos : initial_targets) {
                //Block is interior if all adjacent positions are also in the initial_targets set
                boolean interior = Direction.stream().allMatch(dir ->
                        initial_targets.contains(pos.relative(dir)));
                if (interior) {
                    refined_targets.add(pos);
                }
            }
        } else {
            refined_targets = initial_targets;
        }

        //Further refine selection by proportion and allowed replacements
        refined_targets.removeIf(pos -> randomSource.nextFloat() > proportion);
        refined_targets.removeIf(pos -> !replaceable.contains(placementMap.get(pos).getBlock()));

        //Update all the blocks
        for (BlockPos pos : refined_targets) {
            placementMap.put(pos, block_provider.getState(randomSource, pos));
        }
    }
}
