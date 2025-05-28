package com.syric.shores_between.event;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.client.models.MosasaurusModel;
import com.syric.shores_between.entity.custom.MosasaurusEntity;
import com.syric.shores_between.registry.SBEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = ShoresBetween.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MosasaurusModel.LAYER_LOCATION, MosasaurusModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SBEntities.MOSASAURUS.get(), MosasaurusEntity.createAttributes().build());
    }

}
