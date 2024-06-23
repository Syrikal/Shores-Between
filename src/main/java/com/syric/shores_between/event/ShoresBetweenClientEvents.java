package com.syric.shores_between.event;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.rendering.SBChestRenderer;
import com.syric.shores_between.registry.SBBlockEntities;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ShoresBetween.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShoresBetweenClientEvents {

    @SubscribeEvent
    public static void registerBERs(EntityRenderersEvent.RegisterRenderers renderersEvent) {
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_SIGN.get(), SignRenderer::new);
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_HANGING_SIGN.get(), HangingSignRenderer::new);
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_CHEST.get(), SBChestRenderer::new);
    }
}
