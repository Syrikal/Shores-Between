package com.syric.shores_between.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBCreativeTabs {

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.shoresBetween"))
                    .icon(() -> SBItems.EXAMPLE_ITEM.get().getDefaultInstance())
                    .displayItems((((pParameters, pOutput) -> {
                        pOutput.accept(SBItems.EXAMPLE_ITEM);
                        pOutput.accept(SBBlocks.PORTAL_BLOCK.asItem());
                    })))
                    .build());
}
