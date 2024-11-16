package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.MapCodec;
import com.syric.shores_between.ShoresBetween;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BoulderDecoratorType<P extends BoulderDecorator> {

    public static final DeferredRegister<BoulderDecoratorType<?>> BOULDER_DECORATOR_TYPES = DeferredRegister.create(ResourceKey.createRegistryKey(new ResourceLocation(ShoresBetween.MODID, "boulder_decorator_types")), ShoresBetween.MODID);
    public static final Registry<BoulderDecoratorType<?>> BOULDER_DECORATOR_TYPE_REGISTRY = BOULDER_DECORATOR_TYPES.makeRegistry(x -> {});

    public static void register(IEventBus bus) {
        BOULDER_DECORATOR_TYPES.register(bus);
    }

    /**
     * Undercut: digs out a wide, shallow trench on the downhill side of the boulder.
     *      Parameters: size, probability
     * Top: replaces a specified percentage of the boulder's top blocks with other ones.
     *      Parameters: the proportion, a block to replace with, a slab to replace with, probability
     *      For patches, set the proportion to 1 and use NoiseThresholdProviders for the BlockStateProviders.
     * Above: places blocks on top of the boulder's upper side.
     *      Parameters: the proportion, the block, whether it's OK to place on slabs, probability
     * Treasure: buries treasure under the boulder
     *      Parameters: chances to be each of the treasure types (coffin, valuable, mid, junk, eerie, rotten, barrels)
     * Buried Blob: buries a blob of flesh under the boulder
     *      Parameters: block to use, probability
     * Interior Scatter: replaces some of the interior blocks with a different one.
     *      Parameters: the proportion, blocks that can be replaced, the block to replace with, whether it's allowed to replace exterior, probability
     * Interior Layered: replaces the interior blocks with concentric layers.
     *      Parameters: list of blocks in order (outside in), probability
     * Barnacles: places barnacles on the outer edges of the boulder
     *      Parameters: IntProvider for number of patches, IntProvider for their sizes, probability
     * Sides: replaces some of the blocks on the sides of the boulder
     *      Parameters: proportion, whether to do it in scatters, number of blobs otherwise, the block to replace with, probability
     */

    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<UndercutBoulderDecorator>> UNDERCUT = BOULDER_DECORATOR_TYPES.register("undercut", () -> new BoulderDecoratorType<>(UndercutBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<TopBoulderDecorator>> TOP = BOULDER_DECORATOR_TYPES.register("top_scatter", () -> new BoulderDecoratorType<>(TopBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<AboveBoulderDecorator>> ABOVE = BOULDER_DECORATOR_TYPES.register("above", () -> new BoulderDecoratorType<>(AboveBoulderDecorator.CODEC));
//    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<TreasureBoulderDecorator>> TREASURE = BOULDER_DECORATOR_TYPES.register("treasure", () -> new BoulderDecoratorType<>(TreasureBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<BuriedBlobBoulderDecorator>> BURIED_BLOB = BOULDER_DECORATOR_TYPES.register("buried_blob", () -> new BoulderDecoratorType<>(BuriedBlobBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<InteriorScatterBoulderDecorator>> INTERIOR_SCATTER = BOULDER_DECORATOR_TYPES.register("interior_scatter", () -> new BoulderDecoratorType<>(InteriorScatterBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<InteriorLayeredBoulderDecorator>> INTERIOR_LAYERED = BOULDER_DECORATOR_TYPES.register("interior_layered", () -> new BoulderDecoratorType<>(InteriorLayeredBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<BarnaclesBoulderDecorator>> BARNACLES = BOULDER_DECORATOR_TYPES.register("barnacles", () -> new BoulderDecoratorType<>(BarnaclesBoulderDecorator.CODEC));
    public static final DeferredHolder<BoulderDecoratorType<?>, BoulderDecoratorType<SidesBoulderDecorator>> SIDES = BOULDER_DECORATOR_TYPES.register("sides", () -> new BoulderDecoratorType<>(SidesBoulderDecorator.CODEC));
    private final MapCodec<P> codec;


    private BoulderDecoratorType(MapCodec<P> codec) {
        this.codec = codec;
    }

    public MapCodec<P> codec() {
        return this.codec;
    }
}
