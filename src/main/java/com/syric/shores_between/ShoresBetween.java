package com.syric.shores_between;

import com.mojang.logging.LogUtils;
import com.syric.shores_between.registry.*;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ShoresBetween.MODID)
public class ShoresBetween
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "shores_between";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    public ShoresBetween(IEventBus modEventBus, ModContainer modContainer) {

        // Register ourselves for server and other game events we are interested in
//        NeoForge.EVENT_BUS.register(this);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        SBItems.register(modEventBus);
        SBBlocks.register(modEventBus);
        SBCreativeTabs.register(modEventBus);
        SBBlockEntities.register(modEventBus);
        SBBiomeSources.register(modEventBus);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (com.syric.shores_between.Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(com.syric.shores_between.Config.magicNumberIntroduction + com.syric.shores_between.Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event)
//    {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            Sheets.addWoodType(SBWoodTypes.DRIFTWOOD);
            Sheets.addWoodType(SBWoodTypes.MISTWOOD);
        }
    }
}
