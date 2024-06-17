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
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BREACH_BLOCKS = CREATIVE_MODE_TABS.register("breach_blocks",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.breach_blocks"))
                    .icon(() -> SBBlocks.SHINGLE.get().asItem().getDefaultInstance())
                    .displayItems((((pParameters, pOutput) -> {
                        pOutput.accept(SBBlocks.SHINGLE.asItem());
                        pOutput.accept(SBBlocks.TANGLED_SHINGLE.asItem());
                        pOutput.accept(SBBlocks.PEBBLES.asItem());
                        pOutput.accept(SBBlocks.GRASSY_SHINGLE.asItem());
                        pOutput.accept(SBBlocks.SHALE.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE.asItem());
                        pOutput.accept(SBBlocks.OVERGROWN_SHALE.asItem());
                        pOutput.accept(SBBlocks.SALTSTONE.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_LOG.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_WOOD.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_DRIFTWOOD_LOG.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_DRIFTWOOD_WOOD.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_PLANKS.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_SLAB.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_STAIRS.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_DOOR.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_TRAPDOOR.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_FENCE.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_FENCE_GATE.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_BUTTON.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_PRESSURE_PLATE.asItem());
                        pOutput.accept(SBItems.DRIFTWOOD_SIGN);
                        pOutput.accept(SBItems.DRIFTWOOD_HANGING_SIGN);
                        pOutput.accept(SBBlocks.DRIFTWOOD_LADDER.asItem());
                        pOutput.accept(SBBlocks.PETRIFIED_LOG.asItem());
                        pOutput.accept(SBBlocks.PETRIFIED_WOOD.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_PETRIFIED_LOG.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_PETRIFIED_WOOD.asItem());
                        pOutput.accept(SBBlocks.PETRIFIED_PLANKS.asItem());
                        pOutput.accept(SBBlocks.PETRIFIED_SLAB.asItem());
                        pOutput.accept(SBBlocks.PETRIFIED_STAIRS.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_LOG.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_WOOD.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_MISTWOOD_LOG.asItem());
                        pOutput.accept(SBBlocks.STRIPPED_MISTWOOD_WOOD.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_PLANKS.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_SLAB.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_STAIRS.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_DOOR.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_TRAPDOOR.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_FENCE.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_FENCE_GATE.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_BUTTON.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_PRESSURE_PLATE.asItem());
                        pOutput.accept(SBItems.MISTWOOD_SIGN);
                        pOutput.accept(SBItems.MISTWOOD_HANGING_SIGN);
                        pOutput.accept(SBBlocks.MISTWOOD_LADDER.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_LEAVES.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_SAPLING.asItem());
                        pOutput.accept(SBBlocks.ROTTING_FLESH_BLOCK.asItem());
                        pOutput.accept(SBBlocks.INFESTED_FLESH_BLOCK.asItem());
                        pOutput.accept(SBBlocks.BLOATED_FLESH.asItem());
                        pOutput.accept(SBBlocks.MUMMIFIED_FLESH.asItem());
                        pOutput.accept(SBBlocks.LEVIATHAN_BONE.asItem());
                        pOutput.accept(SBBlocks.LEVIATHAN_KERATIN.asItem());
                        pOutput.accept(SBBlocks.JELLIED_ICHOR.asItem());
                        pOutput.accept(SBBlocks.TITAN_BONE.asItem());
                        pOutput.accept(SBBlocks.CORRODED_TITAN_BONE.asItem());
                        pOutput.accept(SBBlocks.ABERRANT_SHELL.asItem());
                        pOutput.accept(SBBlocks.DUNEGRASS.asItem());
                        pOutput.accept(SBBlocks.SEAWEED_SLAB.asItem());
                        pOutput.accept(SBBlocks.SEAWEED_CARPET.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_MOSS_CARPET.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_MOSS_BLOCK.asItem());
                        pOutput.accept(SBBlocks.CORRODED_CONCRETE.asItem());
                        pOutput.accept(SBBlocks.SUSPICIOUS_SHINGLE.asItem());
                        pOutput.accept(SBBlocks.SEAGULL_NEST.asItem());
                        pOutput.accept(SBBlocks.OYSTERS.asItem());
                        pOutput.accept(SBBlocks.BARNACLES.asItem());
                        pOutput.accept(SBBlocks.DEAD_FISH_SLAB.asItem());
                    })))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BREACH_BUILDING = CREATIVE_MODE_TABS.register("breach_building",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.breach_building"))
                    .icon(() -> SBBlocks.PORTAL_BLOCK.get().asItem().getDefaultInstance())
                    .displayItems((((pParameters, pOutput) -> {
                        pOutput.accept(SBBlocks.PORTAL_BLOCK.asItem());
                        pOutput.accept(SBBlocks.SHALE_SLAB.asItem());
                        pOutput.accept(SBBlocks.SHALE_STAIRS.asItem());
                        pOutput.accept(SBBlocks.SHALE_WALL.asItem());
                        pOutput.accept(SBBlocks.POLISHED_SHALE.asItem());
                        pOutput.accept(SBBlocks.POLISHED_SHALE_SLAB.asItem());
                        pOutput.accept(SBBlocks.POLISHED_SHALE_STAIRS.asItem());
                        pOutput.accept(SBBlocks.POLISHED_SHALE_WALL.asItem());
                        pOutput.accept(SBBlocks.SHALE_BRICKS.asItem());
                        pOutput.accept(SBBlocks.CRACKED_SHALE_BRICKS.asItem());
                        pOutput.accept(SBBlocks.SHALE_BRICK_SLAB.asItem());
                        pOutput.accept(SBBlocks.SHALE_BRICK_STAIRS.asItem());
                        pOutput.accept(SBBlocks.SHALE_BRICK_WALL.asItem());
                        pOutput.accept(SBBlocks.MOSSY_SHALE_BRICKS.asItem());
                        pOutput.accept(SBBlocks.MOSSY_SHALE_BRICK_SLAB.asItem());
                        pOutput.accept(SBBlocks.MOSSY_SHALE_BRICK_STAIRS.asItem());
                        pOutput.accept(SBBlocks.MOSSY_SHALE_BRICK_WALL.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_SLAB.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_STAIRS.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_WALL.asItem());
                        pOutput.accept(SBBlocks.POLISHED_DARK_SHALE.asItem());
                        pOutput.accept(SBBlocks.POLISHED_DARK_SHALE_SLAB.asItem());
                        pOutput.accept(SBBlocks.POLISHED_DARK_SHALE_STAIRS.asItem());
                        pOutput.accept(SBBlocks.POLISHED_DARK_SHALE_WALL.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_BRICKS.asItem());
                        pOutput.accept(SBBlocks.CRACKED_DARK_SHALE_BRICKS.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_BRICK_SLAB.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_BRICK_STAIRS.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_BRICK_WALL.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_TILES.asItem());
                        pOutput.accept(SBBlocks.CRACKED_DARK_SHALE_TILES.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_TILE_SLAB.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_TILE_STAIRS.asItem());
                        pOutput.accept(SBBlocks.DARK_SHALE_TILE_WALL.asItem());
                        pOutput.accept(SBBlocks.CHISELED_SHALE.asItem());
                        pOutput.accept(SBBlocks.CHISELED_DARK_SHALE.asItem());
                        pOutput.accept(SBBlocks.ENGRAVED_DARK_SHALE.asItem());
                        pOutput.accept(SBBlocks.DARK_OBELISK.asItem());
                        pOutput.accept(SBBlocks.CRAB_POT.asItem());
                        pOutput.accept(SBBlocks.WHALEBONE_TOTEM.asItem());
                        pOutput.accept(SBItems.DRIFTWOOD_CHEST.asItem());
                        pOutput.accept(SBBlocks.DRIFTWOOD_BARREL.asItem());
                        pOutput.accept(SBItems.MISTWOOD_CHEST.asItem());
                        pOutput.accept(SBBlocks.MISTWOOD_BARREL.asItem());
                        pOutput.accept(SBItems.ANCIENT_CHEST.asItem());
                        pOutput.accept(SBBlocks.CORRODED_LANTERN.asItem());
                        pOutput.accept(SBBlocks.BURIED_IRON_SCRAP.asItem());
                        pOutput.accept(SBBlocks.BURIED_RUSTY_SCRAP.asItem());
                        pOutput.accept(SBBlocks.IRON_SCRAP_BLOCK.asItem());
                        pOutput.accept(SBBlocks.RUSTY_SCRAP_BLOCK.asItem());
                    })))
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BREACH_ITEMS = CREATIVE_MODE_TABS.register("breach_items",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.breach_items"))
                    .icon(SBItems.WHALEBONE::toStack)
                    .displayItems((((pParameters, pOutput) -> {
                        pOutput.accept(SBItems.CRAB);
                        pOutput.accept(SBItems.COOKED_CRAB);
                        pOutput.accept(SBItems.LOBSTER);
                        pOutput.accept(SBItems.COOKED_LOBSTER);
                        pOutput.accept(SBItems.ODD_FISH);
                        pOutput.accept(SBItems.SEAGULL_EGG);
                        pOutput.accept(SBItems.OYSTER);
                        pOutput.accept(SBItems.COOKED_OYSTER);
                        pOutput.accept(SBItems.BARNACLE);
                        pOutput.accept(SBItems.WRITHING_TENDRILS);
                        pOutput.accept(SBItems.ROTTEN_FISH);
                        pOutput.accept(SBItems.STRANGE_MEAT);
                        pOutput.accept(SBItems.IRON_SCRAP);
                        pOutput.accept(SBItems.RUSTY_SCRAP);
                        pOutput.accept(SBItems.SALT);
                        pOutput.accept(SBItems.WHALEBONE);
                        pOutput.accept(SBItems.AMBERGRIS);
                        pOutput.accept(SBItems.RAW_TITAN_MARROW);
                        pOutput.accept(SBItems.TITAN_MARROW);
                        pOutput.accept(SBItems.BOTTLE_OF_ICHOR);
                        pOutput.accept(SBItems.MESSAGE_IN_A_BOTTLE);
                        pOutput.accept(SBItems.TOTEM_OF_WARNING);
                        pOutput.accept(SBItems.TOTEM_OF_REVEALING);
                        pOutput.accept(SBItems.STRAND_ARMOR_TRIM_SMITHING_TEMPLATE);
                    })))
                    .build());
}
