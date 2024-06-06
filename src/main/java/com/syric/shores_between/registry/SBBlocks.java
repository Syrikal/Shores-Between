package com.syric.shores_between.registry;

import com.syric.shores_between.block.BreachPortalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBlocks {

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final DeferredBlock<Block> PORTAL_BLOCK = registerWithItem("portal_block", () -> new BreachPortalBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));

    private static DeferredBlock<Block> registerNoItem(String name, Supplier<? extends Block> block) {
        return BLOCKS.register(name, block);
    }
    private static DeferredBlock<Block> registerWithItem(String name, Supplier<? extends Block> block) {
        DeferredBlock<Block> ret = registerNoItem(name, block);
        SBItems.ITEMS.registerSimpleBlockItem(ret);
        return ret;
    }

    private static DeferredBlock<Block> registerWithNoItem(String blockID, Supplier<? extends Block> sup) {
        return BLOCKS.register(blockID, sup);
    }

}
