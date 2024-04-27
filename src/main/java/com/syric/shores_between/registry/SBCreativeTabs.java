package com.syric.shores_between.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

import static com.syric.shores_between.ShoresBetween.MODID;

@Mod.EventBusSubscriber(
        modid = "shores_between",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class SBCreativeTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Map<ResourceKey<CreativeModeTab>, RegistryObject<Item>> CREATIVE_TAB_ITEM_MAP = new HashMap<>();

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SBItems.EXAMPLE_ITEM.get().getDefaultInstance())
            .build());

    @SubscribeEvent
    //Places vanilla-tab items into vanilla tabs
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CREATIVE_TAB_ITEM_MAP.entrySet().stream().filter(x -> event.getTabKey() == x.getKey()).forEach(x -> event.accept(x.getValue()));
    }


}
