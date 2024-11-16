package com.syric.shores_between.util.intproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;

public class GeometricInt extends IntProvider {
    public static final MapCodec<GeometricInt> CODEC = RecordCodecBuilder.<GeometricInt>mapCodec(
                    builder -> builder.group(
                                    Codec.FLOAT.fieldOf("probability").forGetter(geom -> geom.probability),
                                    Codec.INT.fieldOf("max_inclusive").forGetter(geom -> geom.maxInclusive)
                            )
                            .apply(builder, GeometricInt::new)
            );
    private final float probability;
    private final int maxInclusive;

    private GeometricInt(float probability, int maxInclusive) {
        this.probability = probability;
        this.maxInclusive = maxInclusive;
    }

    public static GeometricInt of(float probability, int maxInclusive) {
        return new GeometricInt(probability, maxInclusive);
    }


    @Override
    public int sample(RandomSource random) {
        boolean done = false;
        int output = 0;
        while (!done) {
            output++;
            if (random.nextFloat() > probability) {
                done = true;
            }
            if (output >= maxInclusive) {
                return maxInclusive;
            }
        }
        return output;
    }

    @Override
    public int getMinValue() {
        return 1;
    }

    @Override
    public int getMaxValue() {
        return this.maxInclusive;
    }

    @Override
    public IntProviderType<?> getType() {
        return null;
    }
}
