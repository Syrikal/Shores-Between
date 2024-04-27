package com.syric.shores_between.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBBlocks {

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<T> ret = registerNoItem(name, block);
        SBItems.register(name, () -> new BlockItem(ret.get(), new Item.Properties()), tab);
        return ret;
    }

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path, with matching item
    public static final RegistryObject<Block> EXAMPLE_BLOCK = register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)), CreativeModeTabs.BUILDING_BLOCKS);


}
