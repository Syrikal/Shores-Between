package com.syric.shores_between.worldgen.feature.titan_egg;

import com.mojang.serialization.Codec;
import com.syric.shores_between.registry.SBTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TitanEggFeature extends Feature<TitanEggConfiguration> {

    public TitanEggFeature(Codec<TitanEggConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TitanEggConfiguration> context) {
        RandomSource randomSource = context.random();
        WorldGenLevel level = context.level();
        TitanEggConfiguration config = context.config();

//        ShoresBetween.LOGGER.debug("Placing titan egg");

        //Generate transformation (handles size + rotation)
        TitanEggTransformer transformer = TitanEggTransformer.getTitanEggTransformer(context, randomSource);
        float diameter = transformer.scale();
        float elongation = config.elongation().sample(randomSource);
        HashMap<BlockPos, BlockState> placementMap = new HashMap<>();

        //Find where to place
        //The egg must have solid ground underneath it between 3 and (diameter) blocks below.
        //It must have this directly underneath, and also at the eight points five blocks off in each cardinal direction.
        //If unsupported, it has a 1/6 chance of being able to "fall" to find a better position.
//        ShoresBetween.LOGGER.debug("Finding supported point");
        boolean can_fall = randomSource.nextFloat() < 0.15F;
        int y = context.origin().getY();
        boolean can_place = false;
        while (!can_place) {
            if (y < level.getMinBuildHeight()) {
                break;
            }
            boolean all_solid = true;
            BlockPos top_pos;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    top_pos = context.origin().north(5*i).east(5*j);
                    for (int k = 3; k <= diameter; k += 2) {
                        if (y - k < level.getMinBuildHeight()) {
                            break;
                        } else if (level.getBlockState(top_pos.atY(y - k)).isAir()){
                            all_solid = false;
                            break;
                        }
                    }
                    if (!all_solid) {
                        break;
                    }
                }
                if (!all_solid) {
                    break;
                }
            }

            if (all_solid) {
                can_place = true;
            } else if (can_fall) {
                y -= 3;
            } else {
                break;
            }
        }

        if (!can_place || !level.getBiome(context.origin().atY(y)).is(SBTags.Biomes.HAS_TITAN_EGGS)) {
//            ShoresBetween.LOGGER.debug("Failed to place egg");
            return false;
        }
        BlockPos newOrigin = context.origin().atY(y);

        //Fill placement map
//        ShoresBetween.LOGGER.debug("Generating placement map");
        double potential_radius = diameter * elongation * 0.6;
        Vec3 first_pos = newOrigin.getCenter().add(-potential_radius, -potential_radius * 0.5, -potential_radius);
        Vec3 second_pos = newOrigin.getCenter().add(potential_radius, potential_radius, potential_radius);

        AtomicInteger successes = new AtomicInteger();
        AtomicInteger tries = new AtomicInteger();
        BlockPos.betweenClosedStream(BlockPos.containing(first_pos), BlockPos.containing(second_pos)).forEach(pos -> {
//            ShoresBetween.LOGGER.debug("Stream is analyzing point " + pos.toShortString());
            tries.getAndIncrement();
            Vec3 center_position = pos.getCenter();
            Vec3 difference = center_position.subtract(newOrigin.getCenter());
            boolean center = testPoint(transformer.transformPoint(difference), elongation);
            if (center) {
                successes.getAndIncrement();
                placementMap.put(pos.immutable(), Blocks.OBSIDIAN.defaultBlockState());
            }
        });
//        ShoresBetween.LOGGER.debug("Success rate: " + successes.get() / tries.get());
//        ShoresBetween.LOGGER.debug("Placement map generated with " + placementMap.entrySet().size() + " entries");



        //Verify that the egg is legal to place
//        ShoresBetween.LOGGER.debug("Checking whether egg is legal to place");
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (Map.Entry<BlockPos, BlockState> entry : placementMap.entrySet()) {
            mutableBlockPos.set(entry.getKey());
            if (!(level.getBlockState(mutableBlockPos).is(SBTags.Blocks.TITAN_EGG_REPLACEABLE) || level.getBlockState(mutableBlockPos).isAir())) {
                return false;
            }
        }

        //Place the blocks
//        ShoresBetween.LOGGER.debug("Placing the blocks");
//        ShoresBetween.LOGGER.debug("There are " + placementMap.entrySet().size() + " blocks to place");
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

//        ShoresBetween.LOGGER.debug("Complete");

        return true;
    }

    private boolean testPoint(Vec3 point, float elongation) {


//        ShoresBetween.LOGGER.debug("Running testPoint on point " + point.toString());

        double x = point.x;
        double y = point.y;
        double z = point.z;
        if (point.y < 0) {
            return (x * x + z * z + y * y < 0.25);
        } else {
            return (x * x + z * z + (y / elongation) * (y / elongation) < 0.25);
        }
    }

}
