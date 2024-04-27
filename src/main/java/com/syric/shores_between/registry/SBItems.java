package com.syric.shores_between.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBItems {

    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<Item> ret = ITEMS.register(name, item);
        SBCreativeTabs.CREATIVE_TAB_ITEM_MAP.put(tab, ret);
        return (RegistryObject<T>) ret;
    }

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())), SBCreativeTabs.EXAMPLE_TAB.getKey());





}
