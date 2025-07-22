package com.syric.shores_between.worldgen.feature.beached_corpse;

import com.mojang.serialization.Codec;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.beached_corpses.AbstractBeachedCorpse;
import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBEntities;
import com.syric.shores_between.util.WeightedTable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.acos;

public class BeachedCorpseFeature extends Feature<BeachedCorpseConfiguration> {

    public BeachedCorpseFeature(Codec<BeachedCorpseConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BeachedCorpseConfiguration> context) {
        BlockPos origin = context.origin();

        //Get entity type. Until implementing beached corpses that change based on the viewer's Insight,
        //corpses are unusual at a 1-in-5 rate. Mosasaurs are less common than usual.
        WeightedTable<EntityType<? extends AbstractBeachedCorpse>> table = new WeightedTable<EntityType<? extends AbstractBeachedCorpse>>()
                .add(SBEntities.MOSASAURUS.get(), 1);

        EntityType<? extends AbstractBeachedCorpse> selected_corpse_type = table.getRandom(context.random());
        AbstractBeachedCorpse entity = selected_corpse_type.create(context.level().getLevel());
        if (entity == null) {
            ShoresBetween.LOGGER.debug("Failed to place beached corpse");
            return false;
        }
        entity.setPos(origin.getCenter().add(0, -0.5, 0));

        //Should face between 60 and 180 degrees away from 'directly towards water'.
        /**
         * Step 1: Identify direction of water.
         * At each of three distances, check the eight cardinal and semicardinal points for water.
         * Any point that is water pulls the vector towards itself; further points have less of an effect.
         * */
        Vec3 water_direction = new Vec3(0, 0, 0);
        Integer[] distances = {3, 6, 12};
        boolean water_found = false;

        for (int d : distances) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    BlockPos waterCandidate = origin.south(j*d).east(i*d).atY(62);
                    boolean isWater = context.level().isWaterAt(waterCandidate);

//                    ShoresBetween.LOGGER.debug("Tested point " + waterCandidate.toShortString() + "; it " + (isWater ? "was" : "was not") + " water.");

                    if (isWater) {
                        water_found = true;
                        double intensity = 1 / (Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2)));
                        Vec3 contribution = new Vec3(i, 0, j);
                        contribution = contribution.scale(intensity / contribution.length());
                        water_direction = water_direction.add(contribution);
                    }

                }
            }
        }

        //If water direction is still 0, 0, 0 (rare but possible!) add a little wiggle to it
        if (water_direction.length() == 0) {
            water_direction = new Vec3(context.random().nextDouble() - 0.5, 0, context.random().nextDouble() - 0.5);
        }

        //Add an angle between pi/3 and 5pi/3
        //if it's got no water around (strange but possible) randomize it completely.
        Vec3 facing_direction;
        if (water_found) {
//            double angle = Math.PI; //Testing purposes
        double angle = (Math.PI / 3) + context.random().nextDouble() * 4 * Math.PI / 3;
            facing_direction = water_direction.yRot((float) angle);
            facing_direction = facing_direction.scale(1 / facing_direction.length());
//            ShoresBetween.LOGGER.debug("Facing direction chosen: (%s, %s)".formatted(facing_direction.x, facing_direction.z));

        } else {
            water_direction = new Vec3(1, 0, 0);
            double angle = context.random().nextDouble() * 2 * Math.PI;
            facing_direction = water_direction.yRot((float) angle);
            facing_direction = facing_direction.scale(1 / facing_direction.length());
//            ShoresBetween.LOGGER.debug("Facing direction chosen: (%s, %s)".formatted(facing_direction.x, facing_direction.z));
        }

        float facing_angle = (float) Math.toDegrees(acos(facing_direction.z));
        facing_angle = facing_angle * (facing_direction.x < 0 ? 1 : -1);
//        ShoresBetween.LOGGER.debug("Converted to angle: %s".formatted(facing_angle));


        //Beached corpses can spawn at sea level or one above it. (rare variant high up??)
        //If it spawns above sea level, test whether all the blocks under it in a 7x7 radius are solid, plus a 3x3 area 2/3 of the way down the tail.
        //If at least five of these are empty and none of them are rock, sink the corpse one block into the ground, producing a half-buried one.
        boolean bury = false;
        if (context.origin().getY() == 64) {
            AtomicInteger empty = new AtomicInteger();

            AABB first_area = AABB.ofSize(origin.below().getCenter(), entity.getArea(false), 0, entity.getArea(false));
            AABB second_area = AABB.ofSize(origin.below().getCenter().add(facing_direction.scale(-1 * entity.getTailDistance())), entity.getArea(true), 0, entity.getArea(true));

            BlockPos.betweenClosedStream(first_area).filter(x -> context.level().isEmptyBlock(x)).forEach(x -> empty.getAndIncrement());
            BlockPos.betweenClosedStream(second_area).filter(x -> context.level().isEmptyBlock(x)).forEach(x -> empty.getAndIncrement());

            boolean rock = BlockPos.betweenClosedStream(first_area).anyMatch(x -> context.level().getBlockState(x).is(SBBlocks.SHALE)) ||
                    BlockPos.betweenClosedStream(second_area).anyMatch(x -> context.level().getBlockState(x).is(SBBlocks.SHALE));

            if (empty.get() >= 5 && !rock) {
//                ShoresBetween.LOGGER.debug("Burying beached corpse; number of empty supporting blocks: %s".formatted(empty.get()));
                bury = true;
            }

            if (bury) {
                origin = context.origin().below();
            }
        }

        entity.setPos(origin.getCenter().add(0, -0.5, 0));
        entity.setYRot(facing_angle);
        entity.setBuried(bury);
        entity.finalizeSpawn(context.level().getLevel(), context.level().getCurrentDifficultyAt(origin), MobSpawnType.CHUNK_GENERATION, null);

        MinecraftServer server = context.level().getLevel().getServer();
        Runnable spawnCorpse = () -> {
            context.level().getLevel().addFreshEntity(entity);
        };
        server.submit(spawnCorpse);


        return true;
    }


}
