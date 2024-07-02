package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SBFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ShoresBetween.MODID);

//    public static final DeferredHolder<Feature<?>, Feature<NBTTreeConfiguration>> NBT_TREE = FEATURES.register("nbt_tree", () -> {
////        return new NBTTreeFeature(NBTTreeConfiguration.CODEC.stable());
//    });

}
