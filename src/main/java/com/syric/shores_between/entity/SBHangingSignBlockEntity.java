package com.syric.shores_between.entity;

import com.syric.shores_between.registry.SBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SBHangingSignBlockEntity extends SignBlockEntity {
    public SBHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SBBlockEntities.SHORES_BETWEEN_HANGING_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return SBBlockEntities.SHORES_BETWEEN_HANGING_SIGN.get();
    }
}
