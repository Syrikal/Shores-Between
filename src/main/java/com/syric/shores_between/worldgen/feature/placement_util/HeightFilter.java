package com.syric.shores_between.worldgen.feature.placement_util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.worldgen.feature.SBPlacementModifierTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

public class HeightFilter extends PlacementFilter {

    public static final MapCodec<HeightFilter> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                            Codec.INT.optionalFieldOf("min_inclusive", Integer.MIN_VALUE).forGetter(x -> x.minInclusive),
                            Codec.INT.optionalFieldOf("max_inclusive", Integer.MAX_VALUE).forGetter(x -> x.maxInclusive)
                    )
                    .apply(builder, HeightFilter::new)
    );
    private final int minInclusive;
    private final int maxInclusive;

    private HeightFilter() {
        this.minInclusive = 0;
        this.maxInclusive = 0;
    }

    private HeightFilter(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public static HeightFilter of(int minInclusive, int maxInclusive) {
        return new HeightFilter(minInclusive, maxInclusive);
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return this.minInclusive <= pos.getY() && this.maxInclusive >= pos.getY();
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return SBPlacementModifierTypes.HEIGHT_FILTER.get();
    }
}
