package com.syric.shores_between.registry;

import com.mojang.datafixers.types.Type;
import com.syric.shores_between.entity.SBHangingSignBlockEntity;
import com.syric.shores_between.entity.SBSignBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBlockEntities {
    // Create a Deferred Register to hold BlockEntityTypes which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SBSignBlockEntity>> SHORES_BETWEEN_SIGN =
        BLOCK_ENTITIES.register("shores_between_sign", () ->
                BlockEntityType.Builder.of(SBSignBlockEntity::new, SBBlocks.DRIFTWOOD_SIGN.get(), SBBlocks.DRIFTWOOD_WALL_SIGN.get(), SBBlocks.MISTWOOD_SIGN.get(), SBBlocks.MISTWOOD_WALL_SIGN.get()).build((Type) null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SBHangingSignBlockEntity>> SHORES_BETWEEN_HANGING_SIGN =
            BLOCK_ENTITIES.register("shores_between_hanging_sign", () ->
                    BlockEntityType.Builder.of(SBHangingSignBlockEntity::new, SBBlocks.DRIFTWOOD_HANGING_SIGN.get(), SBBlocks.DRIFTWOOD_WALL_HANGING_SIGN.get(), SBBlocks.MISTWOOD_HANGING_SIGN.get(), SBBlocks.MISTWOOD_WALL_HANGING_SIGN.get()).build((Type) null));


}
