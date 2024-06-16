package com.syric.shores_between.block;

import com.syric.shores_between.entity.SBChestBlockEntity;
import com.syric.shores_between.registry.SBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SBChestBlock extends ChestBlock{
    public String material;

    public SBChestBlock(Properties pProperties, String material) {
        super(pProperties, SBBlockEntities.SHORES_BETWEEN_CHEST::get);
        this.material = material;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SBChestBlockEntity(pPos, pState);
    }
}