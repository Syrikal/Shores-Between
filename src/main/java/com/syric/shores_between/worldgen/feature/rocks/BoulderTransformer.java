package com.syric.shores_between.worldgen.feature.rocks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

public record BoulderTransformer(
        Vec3 displaced_center,
        Float scale, //approx. radius in blocks
        Vec3 rotation
) {

    public static BoulderTransformer getBoulderTransformer(FeaturePlaceContext<BoulderConfiguration> context, RandomSource random, boolean bury, boolean outcrop) {
        BoulderConfiguration config = context.config();
        int size = config.size().sample(random);
        float scale = (float) size / 3;
//        ShoresBetween.LOGGER.debug("Boulder scale has been generated: radius of " + scale + " blocks");
        float vertical_displacement_scale = scale * (config.compress_vertical() < 1 ? config.compress_vertical() * 0.5F : 1);

        Vec3 center = context.origin().getCenter();
//        ShoresBetween.LOGGER.debug("Boulder origin at " + center);
        Vec3 displacement = new Vec3(getDisplacement(random, scale, false, false), getDisplacement(random, vertical_displacement_scale, bury, outcrop), getDisplacement(random, scale, false, false));
        Vec3 displaced_center = new Vec3(center.x + displacement.x, center.y + displacement.y, center.z + displacement.z);
//        ShoresBetween.LOGGER.debug("Boulder displaced center at " + displaced_center);

        Vec3 rotation = getRotation(random, config.compress_vertical(), config.outcrop());

        return new BoulderTransformer(displaced_center, scale, rotation);
    }


    //Generates the rotated, scaled, displaced vector to put into the BoulderParameters function.
    public Vec3 transformPoint(Vec3 pos) {

        Vec3 displacedVector = new Vec3(pos.x - displaced_center.x, pos.y - displaced_center.y, pos.z - displaced_center.z);
        Vec3 scaledVector = displacedVector.scale(1 / scale);

        Vec3 rotatedVector = scaledVector.xRot((float) rotation.x);
        rotatedVector = rotatedVector.yRot((float) rotation.y);
        rotatedVector = rotatedVector.zRot((float) rotation.z);

        return rotatedVector;
    }


    //If y is restricted, y is between 0 and 2pi while the others are +- 1/4 pi.
    //This is because 'y' is rotation ABOUT THE Y-AXIS (yaw) and thus doesn't affect any y values, so it's the only one NOT constrained.
    //If y is not restricted, all three are between 0 and 2pi.
    //If it's an outcrop, it's not rotated vertically at all (so only y is nonzero, rotating it yaw-wise).
    private static Vec3 getRotation(RandomSource random, float restrict_y, boolean outcrop) {
        if (outcrop) {
            return new Vec3(0, random.nextFloat() * 2 * Math.PI, 0);
        }
        if (restrict_y == 1.0F) {
            return new Vec3(random.nextFloat()*2*Math.PI, random.nextFloat()*2*Math.PI, random.nextFloat()*2*Math.PI);
        } else {
            float multiplier = 0.125F*(1 + restrict_y);
            //+- 1 pi, times restrict_y cubed.
            return new Vec3((Math.pow(restrict_y, 3) * random.nextFloat()*2*Math.PI - Math.PI), random.nextFloat() * 2 * Math.PI, (Math.pow(restrict_y, 3) * random.nextFloat() * 2 * Math.PI - Math.PI));
        }
    }

    //Generates a number up to +- half scale. If 'bury', it's biased lower.
    private static float getDisplacement(RandomSource random, float scale, boolean bury, boolean outcrop) {
        //Can move up to half scale
        float displacement_coefficient = 0.5F;
        float max_displacement = scale * displacement_coefficient;
        if (bury) {
            //Generates a random number from -0.3x to 0.2x
            return max_displacement * (random.nextFloat() * 0.5F - 0.3F);
        } else if (outcrop) {
            return max_displacement * (random.nextFloat() * 0.2F);
        } else {
            //Generates a random number between +- max_displacement
            return max_displacement * (random.nextFloat() * 2 - 1);
        }
    }

}
