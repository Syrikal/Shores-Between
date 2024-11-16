package com.syric.shores_between.worldgen.feature.rocks;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public record BoulderParameters(
        float x_stretch,
        float y_stretch,
        float z_stretch,
        float irregularity_amplitude,
        float irregularity_frequency,
        float x_coefficient,
        float y_coefficient,
        float z_coefficient,
        float offset,
        float cutoff,
        Map<Vec3, Double> clefts) {

    public static BoulderParameters getBoulderParameters(FeaturePlaceContext<BoulderConfiguration> context, RandomSource random) {
        BoulderConfiguration config = context.config();

        float eccentricity = config.eccentricity().sample(random);
        boolean prow_boolean = random.nextFloat() < config.prow();

        float x_stretch = generateStretch(random, eccentricity, 1.0F, prow_boolean);
        float y_stretch = generateStretch(random, eccentricity, (config.outcrop() ? 1.0F : config.compress_vertical()), false);
        float z_stretch = generateStretch(random, eccentricity, 1.0F, false);
        Vec3 stretch_vector = new Vec3(x_stretch, y_stretch, z_stretch);

        float irregularity_amplitude = config.irregularity_amplitude().sample(random);
        irregularity_amplitude = Math.clamp(irregularity_amplitude, 0, 2);

        float irregularity_frequency = config.irregularity_frequency().sample(random);
        irregularity_frequency = Math.clamp(irregularity_frequency, 0, 2);

        float cutoff = 1 + 0.25F * irregularity_amplitude;

        float x_coefficient = -3F + random.nextFloat() * 6F;
        float y_coefficient = -3F + random.nextFloat() * 6F;
        float z_coefficient = -3F + random.nextFloat() * 6F;

        float offset = (float) (random.nextFloat() * 2 * Math.PI);

        HashMap<Vec3, Double> clefts_map = new HashMap<>();
        int number_of_clefts = config.clefts().sample(random);
        for (int i = 0; i < number_of_clefts; i++) {
            Map.Entry<Vec3, Double> entry = generateCleft(random, stretch_vector, cutoff, config.outcrop());
            clefts_map.put(entry.getKey(), entry.getValue());
        }
        if (config.outcrop()) {
            Map.Entry<Vec3, Double> entry = generateOutcropCleft(random, stretch_vector, cutoff);
            clefts_map.put(entry.getKey(), entry.getValue());
            if (prow_boolean) {
                Map.Entry<Vec3, Double> prow = generateProwCleft(random, stretch_vector);
                Vec3 prow_vector = prow.getKey();
                Double prow_cutoff = prow.getValue();
                Vec3 mirrored_prow_vector = new Vec3(prow_vector.x, prow_vector.y, -prow_vector.z);

                clefts_map.put(prow_vector, prow_cutoff);
                clefts_map.put(mirrored_prow_vector, prow_cutoff);
            }
        }


        return new BoulderParameters(x_stretch, y_stretch, z_stretch, irregularity_amplitude, irregularity_frequency, x_coefficient, y_coefficient, z_coefficient, offset, cutoff, clefts_map);
    }


    //(ax)^2 + (by)^2 + (cz)^2 + 0.25*d*cos(ix+jy+kz) < cutoff
    public boolean testPoint(Vec3 point) {
        double x = Math.pow(x_stretch*point.x, 2);
        double y = Math.pow(y_stretch * point.y, 2);
        double z = Math.pow(z_stretch * point.z, 2);
        float cos_internal = (float) (x_coefficient*point.x + y_coefficient * point.y + z_coefficient * point.z);
        float cos_multiplied = 0.75F * irregularity_frequency * cos_internal + offset;
        double cos = 0.25 * irregularity_amplitude * Mth.cos(cos_multiplied);
        boolean within_bounds = x+y+z+cos < cutoff;

        boolean clefts_check = clefts.entrySet().stream().noneMatch(cleft -> point.dot(cleft.getKey()) > cleft.getValue());

        return within_bounds && clefts_check;
    }

    //Generates a multiplier between 0.67 and 1.5 by default.
    //'eccentricity' intensifies the multiplier - eccentricity 2 changes the range to 0.5 to 2, 0.5 to 0.8 to 1.25, etc.
    //if 'restrict' is true, the intensity is dialed back by a third.
    //if 'prow' is true, roll twice and take the higher, and always make it a stretch instead of a squeeze.
    private static float generateStretch(RandomSource random, float eccentricity, float restrict_multiplier, boolean prow) {
        float baseline = random.nextFloat()*0.5F;
        if (prow) {
            baseline = Math.max(baseline, random.nextFloat() * 0.5F);
        }
        baseline *= eccentricity;
        float multiplier = 1 + baseline;
        if (random.nextBoolean() || prow) {
            multiplier = 1 / multiplier;
        }
        multiplier /= restrict_multiplier;
//        ShoresBetween.LOGGER.debug("Generated multiplier of " + multiplier);
        return multiplier;
    }

    public float maxStretch() {
        return 1 / Math.min(x_stretch, Math.min(y_stretch, z_stretch));
    }

    //Generates a cleft that cuts off part of the boulder
    private static Map.Entry<Vec3, Double> generateCleft(RandomSource random, Vec3 stretch_vector, float cutoff, boolean outcrop) {
        Vec3 normal_vector = new Vec3(random.nextFloat() - 0.5F, random.nextFloat() - 0.5F, random.nextFloat() - 0.5F);
        //Distance in radii is between 0.75 and 0.95
        float distance_multiplier = random.nextFloat() * 0.2F + 0.75F;

        //Outcrops' clefts (other than the main one) cut from the bottom or sides, not top.
        if (outcrop) {
            normal_vector = new Vec3(normal_vector.x, random.nextFloat() - 1, normal_vector.z);
        }

        double denominator = normal_vector.x * normal_vector.x * stretch_vector.x * stretch_vector.x
                + normal_vector.y * normal_vector.y * stretch_vector.y * stretch_vector.y
                + normal_vector.z * normal_vector.z * stretch_vector.z * stretch_vector.z;

        double final_distance = (distance_multiplier * normal_vector.dot(normal_vector) * Mth.sqrt((float) (cutoff / denominator)));

        return Map.entry(normal_vector, final_distance);

    }


    //Generates a cleft that cuts off the top half of the boulder at a slight angle.
    //Always slopes up in the positive X direction (it gets rotated anyway).
    private static Map.Entry<Vec3, Double> generateOutcropCleft(RandomSource random, Vec3 stretch_vector, float cutoff) {
        //Vector needs to be mostly but not completely vertical. An outcrop's slope should be between 1:4 and 1:2.
        //The y is 1. This means the x-component must be between -.5 and -.25.
        Vec3 normal_vector = new Vec3(random.nextFloat() * 0.25 - 0.5, 1, 0);

        float distance_multiplier = random.nextFloat() * 0.1F - 0.2F;

        double denominator = normal_vector.x * normal_vector.x * stretch_vector.x * stretch_vector.x
                + normal_vector.y * normal_vector.y * stretch_vector.y * stretch_vector.y
                + normal_vector.z * normal_vector.z * stretch_vector.z * stretch_vector.z;

        double final_distance = (distance_multiplier * normal_vector.dot(normal_vector) * Mth.sqrt((float) (cutoff / denominator)));

        return Map.entry(normal_vector, final_distance);
    }

    private static Map.Entry<Vec3, Double> generateProwCleft(RandomSource random, Vec3 stretch_vector) {
        float x_length = (float) (1 / stretch_vector.x);

        //Prow clefts range from 1:3 to 1:1 horizontally. Vertically, they range from sheer to overhanging at a 2:1 ratio.
        //Their horizontal angle is between 22.5 and 45 degrees. Their XZ component is length 1. Their Y ranges from 0 to -0.6.
        double angle = Math.PI * (random.nextDouble() + 1) / 8; //Angle from pi/8 to pi/4
        double x = Mth.cos((float) angle); //These x and z components are a point on the unit circle
        double z = Mth.sin((float) angle);
        double y = random.nextDouble() * -0.6;

        Vec3 normal_vector = new Vec3(x, y, z);

        //Distance of 1 means they meet at the x-length. They can be up to 10% above or 10% below that.
        float distance_multiplier = random.nextFloat() * 0.2F + 0.9F;
        float target_point = x_length * distance_multiplier;

        double final_cutoff = x * target_point;

        return Map.entry(normal_vector, final_cutoff);
    }

}
