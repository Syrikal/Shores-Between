package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ShoresBetween.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new SBWorldGenProvider(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), new SBRecipeProvider(packOutput, lookupProvider));
//        SBBlockTagProvider blockTags = generator.addProvider(event.includeServer(), new SBBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
//        generator.addProvider(event.includeServer(), new SBItemTagProvider(packOutput, lookupProvider, blockTags.contentsGetter()));
//        generator.addProvider(event.includeClient(), new SBBlockStateProvider(packOutput, existingFileHelper));
//        generator.addProvider(event.includeClient(), new SBItemModelProvider(packOutput, existingFileHelper));
    }
}

