package com.syric.shores_between.registry;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.client.BreachSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

@EventBusSubscriber(modid = ShoresBetween.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SBDimensionEffects {
    public static final ResourceLocation BREACH_EFFECTS = new ResourceLocation(ShoresBetween.MODID, "breach_effects");

    @SubscribeEvent
    public static void registerBreachEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(BREACH_EFFECTS, new BreachSpecialEffects());
    }

}
