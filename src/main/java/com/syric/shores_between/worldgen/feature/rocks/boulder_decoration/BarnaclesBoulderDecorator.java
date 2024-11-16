package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BarnaclesBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<BarnaclesBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            IntProvider.CODEC.fieldOf("number_of_patches").forGetter(x -> x.number_of_patches),
                            FloatProvider.CODEC.fieldOf("patch_radius").forGetter(x -> x.patch_radius),
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, BarnaclesBoulderDecorator::new)
    );
    private final IntProvider number_of_patches;
    private final FloatProvider patch_radius;
    private final BlockStateProvider block_provider;
    private final float probability;


    public BarnaclesBoulderDecorator(IntProvider number_of_patches, FloatProvider patch_radius, BlockStateProvider block_provider, float probability) {
        this.number_of_patches = number_of_patches;
        this.patch_radius = patch_radius;
        this.block_provider = block_provider;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.BARNACLES.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();
        int num_patches = number_of_patches.sample(randomSource);

        Set<BlockPos> targets = new HashSet<>();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability || num_patches < 1) {
            return;
        }

        //Collect a list of the block positions laterally adjacent to existing ones
        Set<BlockPos> sidePositions = new HashSet<>();

        for (BlockPos pos : placeTypeMap.keySet()) {
            //Block is on side if a block to the side is not in the set
            for (Direction dir : Direction.values()) {
                if (dir.getAxis() == Direction.Axis.Y) {
                    continue;
                }

                boolean not_in_set = !placeTypeMap.containsKey(pos.relative(dir));
                boolean replaceable = context.isAir(pos.relative(dir)) || context.isFluidSource(pos.relative(dir)) || undercutSet.contains(pos.relative(dir));
                boolean fullblock = placeTypeMap.get(pos) == PlaceTypes.PlaceType.FULL_BLOCK;
                if (not_in_set && replaceable && fullblock) {
                    sidePositions.add(pos.relative(dir));
                }
            }
        }

        if (sidePositions.isEmpty()) {
            return;
        }

        for (int i = 0; i < num_patches; i++) {
            BlockPos center_block = sidePositions.stream().skip(randomSource.nextInt(sidePositions.size())).findFirst().orElse(null);
            if (center_block == null) {
                continue;
            }
            float patchradius = patch_radius.sample(randomSource);
            int size = (int) (2 * Math.ceil(patchradius) + 1);
            AABB area = AABB.ofSize(center_block.getCenter(), size, size, size);
            BlockPos.betweenClosedStream(area).filter(pos -> {
                        float distance = Mth.sqrt((float) center_block.distToCenterSqr(pos.getCenter()));
                        float prob = 1 - (distance * distance * distance / (patchradius * patchradius * patchradius));
                        return prob > 0 && randomSource.nextFloat() < prob;
                    })
                    //Remove anything that isn't a valid position
                    .filter(sidePositions::contains)
                    .forEach(pos -> targets.add(pos.immutable()));
        }


        //Update all the blocks
        for (BlockPos pos : targets) {
            BlockState state = block_provider.getState(randomSource, pos);
            decorationPlacementMap.put(pos, state);
        }
    }
}
