package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class SidesBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<SidesBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            Codec.floatRange(0.0F, 5.0F).fieldOf("proportion").forGetter(x -> x.proportion),
                            Codec.BOOL.fieldOf("blobs").forGetter(x -> x.blobs),
                            IntProvider.CODEC.fieldOf("number_of_patches").forGetter(config -> config.number_of_patches),
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, SidesBoulderDecorator::new)
    );
    private final float proportion;
    private final boolean blobs;
    private final IntProvider number_of_patches;
    private final BlockStateProvider block_provider;
    private final float probability;

    public SidesBoulderDecorator(float proportion, boolean blobs, IntProvider number_of_patches, BlockStateProvider blockProvider, float probability) {
        this.proportion = proportion;
        this.blobs = blobs;
        this.number_of_patches = number_of_patches;
        this.block_provider = blockProvider;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.SIDES.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability) {
            return;
        }

        //Get a set of blocks on the sides of the boulder
        Set<BlockPos> side_blocks = new HashSet<>();
        Set<BlockPos> targets = new HashSet<>();
        placeTypeMap.entrySet().stream().filter(x -> x.getValue() == PlaceTypes.PlaceType.FULL_BLOCK).forEach(x -> side_blocks.add(x.getKey()));
        //Remove anything that doesn't have a horizontally adjacent empty block
        side_blocks.removeIf(pos ->
            Direction.stream().filter(dir -> dir.getAxis() != Direction.Axis.Y).allMatch(dir ->
               placeTypeMap.containsKey(pos.relative(dir)))
        );
        //Remove anything that's at the top or bottom
        Set<BlockPos> strict_side_blocks = new HashSet<>(side_blocks);
        strict_side_blocks.removeIf(pos -> !placementMap.containsKey(pos.above()) || !placementMap.containsKey(pos.below()));

        //If there are no side blocks, leave
        if (side_blocks.isEmpty()) {
            return;
        }

        //If blobs, pick a number of blobs and create them
        if (blobs) {
            int blobs_number = number_of_patches.sample(randomSource);
            for (int i = 0; i < blobs_number; i++) {
                BlockPos center_block = strict_side_blocks.stream().skip(randomSource.nextInt(strict_side_blocks.size())).findFirst().orElse(null);
                if (center_block == null) {
                    continue;
                }
                AABB area = AABB.ofSize(center_block.getCenter(), 5, 5, 5);
                BlockPos.betweenClosedStream(area).filter(pos -> {
                            float distance = (float) center_block.distToCenterSqr(pos.getCenter());
                            float prob = (-0.15F * distance) + 1;
                            return prob > 0 && randomSource.nextFloat() < prob;
                        })
                        //Remove anything that isn't a side block
                        .filter(strict_side_blocks::contains)
                        .forEach(pos -> targets.add(pos.immutable()));
            }


        //If scatter, randomly choose blocks from the set
        } else {
            targets.addAll(strict_side_blocks);
            targets.removeIf(pos -> randomSource.nextFloat() > proportion);
        }

        //Add all the blocks to the placement map
        for (BlockPos pos : targets) {
            placementMap.put(pos, block_provider.getState(randomSource, pos));
        }

    }

}
