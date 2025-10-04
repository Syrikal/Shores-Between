package com.syric.shores_between.worldgen.feature.titan_egg;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

public record TitanEggTransformer(
        Float scale, //approx. diameter in blocks
        Vec3 rotation
) {

    public static TitanEggTransformer getTitanEggTransformer(FeaturePlaceContext<TitanEggConfiguration> context, RandomSource randomSource) {
        TitanEggConfiguration config = context.config();
        int size = config.size().sample(randomSource);
        float scale = (float) size;
        Vec3 rotation = getRotation(randomSource);
        return new TitanEggTransformer(scale, rotation);
    }

    public Vec3 transformPoint(Vec3 pos) {
        Vec3 scaledVector = pos.scale(1 / scale);

        Vec3 rotatedVector = scaledVector.xRot((float) rotation.x);
        rotatedVector = rotatedVector.yRot((float) rotation.y);
        rotatedVector = rotatedVector.zRot((float) rotation.z);

        return rotatedVector;
    }

    private static Vec3 getRotation(RandomSource random) {
        return new Vec3(
                Math.PI * (random.nextFloat() * 0.5 - 0.25),
                random.nextFloat() * 2 * Math.PI,
                Math.PI * (random.nextFloat() * 0.5 - 0.25)
                );
    }

}
