package com.syric.shores_between.entity;

import com.syric.shores_between.registry.SBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SBSignBlockEntity extends SignBlockEntity {
    public SBSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SBBlockEntities.SHORES_BETWEEN_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return SBBlockEntities.SHORES_BETWEEN_SIGN.get();
    }
}
