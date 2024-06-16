package com.syric.shores_between.entity.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.syric.shores_between.entity.SBChestBlockEntity;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class SBChestItemRenderer extends BlockEntityWithoutLevelRenderer {
    private final BlockEntityRenderDispatcher dispatcher;

    public SBChestItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.dispatcher = pBlockEntityRenderDispatcher;
    }

    private ChestBlockEntity chest(ItemStack stack) {
        BlockState state = null;
        if (stack.getItem() instanceof BlockItem blockItem) {
            state = blockItem.getBlock().defaultBlockState();
        }
        if (state != null) {
            return new SBChestBlockEntity(BlockPos.ZERO, state);
        } else {
            return new ChestBlockEntity(BlockPos.ZERO, Blocks.CHEST.defaultBlockState());
        }
    }


    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        this.dispatcher.renderItem(chest(pStack), pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
    }
}
