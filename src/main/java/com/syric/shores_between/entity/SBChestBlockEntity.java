package com.syric.shores_between.entity;

import com.syric.shores_between.registry.SBBlockEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@MethodsReturnNonnullByDefault
public class SBChestBlockEntity extends ChestBlockEntity {
    public SBChestBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SBBlockEntities.SHORES_BETWEEN_CHEST.get(), pPos, pBlockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return SBBlockEntities.SHORES_BETWEEN_CHEST.get();
    }
}
