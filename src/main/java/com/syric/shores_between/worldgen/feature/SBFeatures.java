package com.syric.shores_between.worldgen.feature;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.beached_corpse.BeachedCorpseConfiguration;
import com.syric.shores_between.worldgen.feature.beached_corpse.BeachedCorpseFeature;
import com.syric.shores_between.worldgen.feature.bush.BushConfiguration;
import com.syric.shores_between.worldgen.feature.bush.BushFeature;
import com.syric.shores_between.worldgen.feature.log.FallenLogConfiguration;
import com.syric.shores_between.worldgen.feature.log.FallenLogFeature;
import com.syric.shores_between.worldgen.feature.mummified_corpse.MummifiedCorpseConfiguration;
import com.syric.shores_between.worldgen.feature.mummified_corpse.MummifiedCorpseFeature;
import com.syric.shores_between.worldgen.feature.patch.PatchConfiguration;
import com.syric.shores_between.worldgen.feature.patch.PatchFeature;
import com.syric.shores_between.worldgen.feature.rocks.BoulderConfiguration;
import com.syric.shores_between.worldgen.feature.rocks.BoulderFeature;
import com.syric.shores_between.worldgen.feature.tree.NBTTreeConfiguration;
import com.syric.shores_between.worldgen.feature.tree.NBTTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SBFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, ShoresBetween.MODID);

    public static final DeferredHolder<Feature<?>, Feature<NBTTreeConfiguration>> NBT_TREE = FEATURES.register("nbt_tree", () -> new NBTTreeFeature(NBTTreeConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<BoulderConfiguration>> BOULDER = FEATURES.register("boulder", () -> new BoulderFeature(BoulderConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<PatchConfiguration>> PATCH = FEATURES.register("patch", () -> new PatchFeature(PatchConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<FallenLogConfiguration>> FALLEN_LOG = FEATURES.register("fallen_log", () -> new FallenLogFeature(FallenLogConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<BushConfiguration>> BUSH = FEATURES.register("bush", () -> new BushFeature(BushConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<BeachedCorpseConfiguration>> BEACHED_CORPSE = FEATURES.register("beached_corpse", () -> new BeachedCorpseFeature(BeachedCorpseConfiguration.CODEC.stable()));
    public static final DeferredHolder<Feature<?>, Feature<MummifiedCorpseConfiguration>> MUMMIFIED_CORPSE = FEATURES.register("mummified_corpse", () -> new MummifiedCorpseFeature(MummifiedCorpseConfiguration.CODEC.stable()));


    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }

}
