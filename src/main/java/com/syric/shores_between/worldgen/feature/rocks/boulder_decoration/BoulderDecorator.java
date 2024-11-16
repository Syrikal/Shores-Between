package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.syric.shores_between.worldgen.feature.rocks.BoulderConfiguration;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.HashMap;
import java.util.Set;

public abstract class BoulderDecorator {
    public static final Codec<BoulderDecorator> CODEC = BoulderDecoratorType.BOULDER_DECORATOR_TYPE_REGISTRY
            .byNameCodec()
            .dispatch(BoulderDecorator::type, BoulderDecoratorType::codec);

    public abstract BoulderDecoratorType<?> type();

    public abstract void place(BoulderDecorator.Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap);

    public static final class Context {
        private final LevelSimulatedReader level;
        private final RandomSource random;
        private final BlockPos origin;
        private final BoulderConfiguration config;

        public Context(
                LevelSimulatedReader level,
                RandomSource random,
                BlockPos origin,
                BoulderConfiguration config
        ) {
            this.level = level;
            this.random = random;
            this.origin = origin;
            this.config = config;
        }

        public boolean isAir(BlockPos pos) {
            return this.level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir);
        }

        public boolean isFluidSource(BlockPos pos) {
            return this.level.isFluidAtPosition(pos, FluidState::isSource);
        }

        public LevelSimulatedReader level() {
            return this.level;
        }

        public RandomSource random() {
            return this.random;
        }

        public BlockPos origin() { return this.origin; }

        public BoulderConfiguration config() { return this.config; }
    }
}
