package com.syric.shores_between.registry;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.beached_corpses.Mosasaurus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SBEntities {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ShoresBetween.MODID);

    public static final Supplier<EntityType<Mosasaurus>> MOSASAURUS = ENTITY_TYPES.register("mosasaurus", () -> EntityType.Builder.of(Mosasaurus::new, MobCategory.CREATURE)
            .sized(1.8F, 1.9F).build("mosasaurus"));

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

}
