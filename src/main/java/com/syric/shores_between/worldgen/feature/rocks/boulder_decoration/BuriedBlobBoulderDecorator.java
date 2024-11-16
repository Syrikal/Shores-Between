package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BuriedBlobBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<BuriedBlobBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(config -> config.block_provider),
                            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability)
                    )
                    .apply(builder, BuriedBlobBoulderDecorator::new)
    );
    private final BlockStateProvider block_provider;
    private final float probability;

    public BuriedBlobBoulderDecorator(BlockStateProvider block_provider, float probability) {
        this.block_provider = block_provider;
        this.probability = probability;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.BURIED_BLOB.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {

        RandomSource randomSource = context.random();

        Set<BlockPos> targets = new HashSet<>();

        //Check if we're even doing anything
        if (randomSource.nextFloat() > probability) {
            return;
        }

        //Find center position
        BlockPos center_pos = context.origin();
        boolean found_center = false;
        while (!found_center) {
            if (placementMap.containsKey(center_pos.below())) {
                center_pos = center_pos.below();
            } else {
                found_center = true;
            }
        }

        float maxRadius = context.config().size().getMinValue() * 0.4F;
        float minRadius = maxRadius * 0.4F;
        float radius = (randomSource.nextFloat() * maxRadius * 0.6F + minRadius);
        int size = (int) (2 * Math.ceil(radius) + 1);

        float x_distortion = randomSource.nextFloat() * 0.5F + 1;
        float z_distortion = randomSource.nextFloat() * 0.5F + 1;
        float y_distortion = randomSource.nextFloat() * 4 + 2.5F;
        float cutoff = radius * radius;

        AABB area = AABB.ofSize(center_pos.getCenter(), size, size, size);
        BlockPos final_center_pos = center_pos;
        BlockPos.betweenClosedStream(area).filter(pos -> {
                    Vec3 relative_vector = final_center_pos.getCenter().vectorTo(pos.getCenter());

                    float value = (float) (x_distortion * relative_vector.x * relative_vector.x +
                                                y_distortion * relative_vector.y * relative_vector.y +
                                                z_distortion * relative_vector.z * relative_vector.z);

                    return value < cutoff;
                })
                //Remove anything that isn't a valid position
                .forEach(pos -> targets.add(pos.immutable()));


        //Update all the blocks
        for (BlockPos pos : targets) {
            BlockState state = block_provider.getState(randomSource, pos);
            placementMap.put(pos, state);
        }
    }
}
