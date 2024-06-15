package com.syric.shores_between.datagen.loot;

import com.syric.shores_between.registry.SBBlocks;
import com.syric.shores_between.registry.SBItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SBBlockLootTables extends BlockLootSubProvider {
    public SBBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(SBBlocks.DEFAULT_BLOCK.get());
        dropSelf(SBBlocks.SHINGLE.get());
        dropSelf(SBBlocks.PEBBLES.get());
        dropSelf(SBBlocks.SHALE.get());
        dropSelf(SBBlocks.DARK_SHALE.get());
        dropSelf(SBBlocks.DRIFTWOOD_LOG.get());
        dropSelf(SBBlocks.DRIFTWOOD_WOOD.get());
        dropSelf(SBBlocks.STRIPPED_DRIFTWOOD_LOG.get());
        dropSelf(SBBlocks.STRIPPED_DRIFTWOOD_WOOD.get());
        dropSelf(SBBlocks.DRIFTWOOD_PLANKS.get());
        slab(SBBlocks.DRIFTWOOD_SLAB.get());
        dropSelf(SBBlocks.DRIFTWOOD_STAIRS.get());
        dropSelf(SBBlocks.DRIFTWOOD_DOOR.get());
        dropSelf(SBBlocks.DRIFTWOOD_TRAPDOOR.get());
        dropSelf(SBBlocks.DRIFTWOOD_FENCE.get());
        dropSelf(SBBlocks.DRIFTWOOD_FENCE_GATE.get());
        dropSelf(SBBlocks.DRIFTWOOD_BUTTON.get());
        dropSelf(SBBlocks.DRIFTWOOD_PRESSURE_PLATE.get());
        dropSelf(SBBlocks.DRIFTWOOD_SIGN.get());
        dropSelf(SBBlocks.DRIFTWOOD_HANGING_SIGN.get());
        dropSelf(SBBlocks.DRIFTWOOD_LADDER.get());
        dropSelf(SBBlocks.PETRIFIED_LOG.get());
        dropSelf(SBBlocks.PETRIFIED_WOOD.get());
        dropSelf(SBBlocks.STRIPPED_PETRIFIED_LOG.get());
        dropSelf(SBBlocks.STRIPPED_PETRIFIED_WOOD.get());
        dropSelf(SBBlocks.PETRIFIED_PLANKS.get());
        slab(SBBlocks.PETRIFIED_SLAB.get());
        dropSelf(SBBlocks.PETRIFIED_STAIRS.get());
        dropSelf(SBBlocks.MISTWOOD_LOG.get());
        dropSelf(SBBlocks.MISTWOOD_WOOD.get());
        dropSelf(SBBlocks.STRIPPED_MISTWOOD_LOG.get());
        dropSelf(SBBlocks.STRIPPED_MISTWOOD_WOOD.get());
        dropSelf(SBBlocks.MISTWOOD_PLANKS.get());
        slab(SBBlocks.MISTWOOD_SLAB.get());
        dropSelf(SBBlocks.MISTWOOD_STAIRS.get());
        dropSelf(SBBlocks.MISTWOOD_DOOR.get());
        dropSelf(SBBlocks.MISTWOOD_TRAPDOOR.get());
        dropSelf(SBBlocks.MISTWOOD_FENCE.get());
        dropSelf(SBBlocks.MISTWOOD_FENCE_GATE.get());
        dropSelf(SBBlocks.MISTWOOD_BUTTON.get());
        dropSelf(SBBlocks.MISTWOOD_PRESSURE_PLATE.get());
        dropSelf(SBBlocks.MISTWOOD_SIGN.get());
        dropSelf(SBBlocks.MISTWOOD_HANGING_SIGN.get());
        dropSelf(SBBlocks.MISTWOOD_LADDER.get());
        dropSelf(SBBlocks.MISTWOOD_SAPLING.get());
        dropSelf(SBBlocks.ROTTING_FLESH_BLOCK.get());
        dropSelf(SBBlocks.BLOATED_FLESH.get());
        dropSelf(SBBlocks.MUMMIFIED_FLESH.get());
        dropSelf(SBBlocks.LEVIATHAN_BONE.get());
        dropSelf(SBBlocks.LEVIATHAN_KERATIN.get());
        dropSelf(SBBlocks.SEAWEED_BLOCK.get());
        slab(SBBlocks.SEAWEED_SLAB.get());
        dropSelf(SBBlocks.SEAWEED_CARPET.get());
        dropSelf(SBBlocks.MISTWOOD_MOSS_CARPET.get());
        dropSelf(SBBlocks.MISTWOOD_MOSS_BLOCK.get());
        dropSelf(SBBlocks.CORRODED_CONCRETE.get());
        slab(SBBlocks.SHALE_SLAB.get());
        dropSelf(SBBlocks.SHALE_STAIRS.get());
        dropSelf(SBBlocks.SHALE_WALL.get());
        dropSelf(SBBlocks.POLISHED_SHALE.get());
        slab(SBBlocks.POLISHED_SHALE_SLAB.get());
        dropSelf(SBBlocks.POLISHED_SHALE_STAIRS.get());
        dropSelf(SBBlocks.POLISHED_SHALE_WALL.get());
        dropSelf(SBBlocks.SHALE_BRICKS.get());
        dropSelf(SBBlocks.CRACKED_SHALE_BRICKS.get());
        slab(SBBlocks.SHALE_BRICK_SLAB.get());
        dropSelf(SBBlocks.SHALE_BRICK_STAIRS.get());
        dropSelf(SBBlocks.SHALE_BRICK_WALL.get());
        dropSelf(SBBlocks.MOSSY_SHALE_BRICKS.get());
        slab(SBBlocks.MOSSY_SHALE_BRICK_SLAB.get());
        dropSelf(SBBlocks.MOSSY_SHALE_BRICK_STAIRS.get());
        dropSelf(SBBlocks.MOSSY_SHALE_BRICK_WALL.get());
        slab(SBBlocks.DARK_SHALE_SLAB.get());
        dropSelf(SBBlocks.DARK_SHALE_STAIRS.get());
        dropSelf(SBBlocks.DARK_SHALE_WALL.get());
        dropSelf(SBBlocks.POLISHED_DARK_SHALE.get());
        slab(SBBlocks.POLISHED_DARK_SHALE_SLAB.get());
        dropSelf(SBBlocks.POLISHED_DARK_SHALE_STAIRS.get());
        dropSelf(SBBlocks.POLISHED_DARK_SHALE_WALL.get());
        dropSelf(SBBlocks.DARK_SHALE_BRICKS.get());
        dropSelf(SBBlocks.CRACKED_DARK_SHALE_BRICKS.get());
        slab(SBBlocks.DARK_SHALE_BRICK_SLAB.get());
        dropSelf(SBBlocks.DARK_SHALE_BRICK_STAIRS.get());
        dropSelf(SBBlocks.DARK_SHALE_BRICK_WALL.get());
        dropSelf(SBBlocks.DARK_SHALE_TILES.get());
        dropSelf(SBBlocks.CRACKED_DARK_SHALE_TILES.get());
        slab(SBBlocks.DARK_SHALE_TILE_SLAB.get());
        dropSelf(SBBlocks.DARK_SHALE_TILE_STAIRS.get());
        dropSelf(SBBlocks.DARK_SHALE_TILE_WALL.get());
        dropSelf(SBBlocks.CHISELED_SHALE.get());
        dropSelf(SBBlocks.CHISELED_DARK_SHALE.get());
        dropSelf(SBBlocks.ENGRAVED_DARK_SHALE.get());
        dropSelf(SBBlocks.DARK_OBELISK.get());
        dropSelf(SBBlocks.WHALEBONE_TOTEM.get());
        dropSelf(SBBlocks.DRIFTWOOD_CHEST.get());
        dropSelf(SBBlocks.DRIFTWOOD_BARREL.get());
        dropSelf(SBBlocks.MISTWOOD_CHEST.get());
        dropSelf(SBBlocks.MISTWOOD_BARREL.get());
        dropSelf(SBBlocks.ANCIENT_CHEST.get());
        dropSelf(SBBlocks.CORRODED_LANTERN.get());
        dropSelf(SBBlocks.IRON_SCRAP_BLOCK.get());
        dropSelf(SBBlocks.RUSTY_SCRAP_BLOCK.get());

        add(SBBlocks.DRIFTWOOD_WALL_SIGN.get(),
                block -> createSingleItemTable(SBItems.DRIFTWOOD_SIGN));
        add(SBBlocks.DRIFTWOOD_WALL_HANGING_SIGN.get(),
                block -> createSingleItemTable(SBItems.DRIFTWOOD_HANGING_SIGN));
        add(SBBlocks.MISTWOOD_WALL_SIGN.get(),
                block -> createSingleItemTable(SBItems.MISTWOOD_SIGN));
        add(SBBlocks.MISTWOOD_WALL_HANGING_SIGN.get(),
                block -> createSingleItemTable(SBItems.MISTWOOD_HANGING_SIGN));

        otherUnlessSilkTouch(SBBlocks.GRASSY_SHINGLE.get(), SBBlocks.SHINGLE.asItem());
        otherUnlessSilkTouch(SBBlocks.OVERGROWN_SHALE.get(), SBBlocks.SHALE.asItem());
        glowstoneLike(SBBlocks.SALTSTONE.get(), SBItems.SALT.get());
        add(SBBlocks.MISTWOOD_LEAVES.get(),
                result -> createLeavesDrops(SBBlocks.MISTWOOD_LEAVES.get(), SBBlocks.MISTWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));
        otherUnlessSilkTouch(SBBlocks.INFESTED_FLESH_BLOCK.get(), SBBlocks.ROTTING_FLESH_BLOCK.asItem());
        dropWhenSilkTouch(SBBlocks.JELLIED_ICHOR.get());
        add(SBBlocks.ICHOR_CAULDRON.get(),
                result -> createSingleItemTable(Blocks.CAULDRON.asItem()));
        add(SBBlocks.CORRODED_TITAN_BONE.get(),
                result -> createSingleItemTable(SBItems.RAW_TITAN_MARROW.get()));
        add(SBBlocks.DUNEGRASS.get(),
                result -> createShearsOnlyDrop(SBBlocks.DUNEGRASS.asItem()));
        add(SBBlocks.SUSPICIOUS_SHINGLE.get(),
                result -> LootTable.lootTable());
        shellfish(SBBlocks.OYSTERS.get(), SBItems.OYSTER.get());
        shellfish(SBBlocks.BARNACLES.get(), SBItems.BARNACLE.get());
        fishSlab(SBBlocks.DEAD_FISH_SLAB.get(), SBItems.ROTTEN_FISH.get());
        glowstoneLike(SBBlocks.BURIED_IRON_SCRAP.get(), SBItems.IRON_SCRAP.get());
        scrap(SBBlocks.BURIED_RUSTY_SCRAP.get(), SBItems.RUSTY_SCRAP.get(), SBItems.RUST.get());


        //TODO: finish loot tables
        dropSelf(SBBlocks.SEAGULL_NEST.get()); //Special - drops self if silk touch, otherwise 2-4 sticks, 0-2 feathers, and one beachcombing roll. Drops eggs if present.
        dropSelf(SBBlocks.TANGLED_SHINGLE.get()); //Special - one beachcombing roll.
        dropSelf(SBBlocks.CRAB_POT.get()); //Special - drops itself, plus crabs if any

        /**Beachcombing loot table: one of the following
         * 2-4 sticks (6 weight)
         * 3-6 string (6 weight)
         * 2-4 seaweed (6 weight)
         * 1-2 leather (2 weight)
         * 1 rotten fish (2 weight)
         * 1 rotten flesh (2 weight)
         * 1-2 bone (2 weight)
         * 1-2 rusty scrap (2 weight)
         * 1 writhing tendrils
         * 1 message in a bottle
         */


//        dropSelf(SBBlocks.DEAD_FISH_BLOCK.get()); //No loot (doesn't exist)
//        dropSelf(SBBlocks.TITAN_BONE.get()); //No loot
//        dropSelf(SBBlocks.ABERRANT_SHELL.get()); //No loot
//        dropSelf(SBBlocks.PORTAL_BLOCK.get()); //No loot


    }

    private void otherUnlessSilkTouch(Block block, Item other) {
        add(block, (result) -> createSingleItemTableWithSilkTouch(block, other));
    }

    //Drops block if silk touch, else 2-4 of item
    //Each level of fortune adds 1 item but still max 4
    private void glowstoneLike(Block block, Item item) {
        add(block, (result) ->
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block.asItem()).when(HAS_SILK_TOUCH)
                                        .otherwise(
                                                applyExplosionDecay(
                                                        result,
                                                        LootItem.lootTableItem(item)
                                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                                                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE))
                                                                .apply(LimitCount.limitCount(IntRange.range(1, 4)))
                                                )
                                        )
                                )
                )
        );
    }

    //Drops block if silk touch, else 2-6 of item
    //Each level of fortune adds 1 item but still max 6
    private void shellfish(Block block, Item item) {
        add(block, (result) ->
                LootTable.lootTable().withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block.asItem()).when(HAS_SILK_TOUCH)
                                        .otherwise(
                                                applyExplosionDecay(
                                                        result,
                                                        LootItem.lootTableItem(item)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6)))
                                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE))
                                                .apply(LimitCount.limitCount(IntRange.range(1, 6))))
                                        )
                                )
                ));
    }


    private void scrap(Block block, Item item, Item secondItem) {
        add(block, (result) ->
                LootTable.lootTable()
                        .withPool(
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(block.asItem())
                                            .when(HAS_SILK_TOUCH)
                                            .otherwise(
                                                    applyExplosionDecay(
                                                            result,
                                                            LootItem.lootTableItem(item)
                                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                                                            .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE))
                                                            .apply(LimitCount.limitCount(IntRange.range(1, 4)))))
                                    )
                        )
                        .withPool(
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                applyExplosionDecay(
                                                        result,
                                                        LootItem.lootTableItem(secondItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                                        .when(HAS_NO_SILK_TOUCH))
                        ));
    }

    private void slab(Block block) {
        add(block, (result) -> createSlabItemTable(block));
    }

    private void fishSlab(Block block, Item item) {
        add(block, result ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        applyExplosionDecay(
                                                result,
                                                LootItem.lootTableItem(item)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8)))
                                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE, 2))
                                                .apply(LimitCount.limitCount(IntRange.range(2, 8))))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))
                                        .otherwise(
                                                applyExplosionDecay(
                                                        result,
                                                        LootItem.lootTableItem(item)
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                                                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.FORTUNE))
                                                        .apply(LimitCount.limitCount(IntRange.range(1, 4)))))
                                )
                                .when(HAS_NO_SILK_TOUCH)
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        applyExplosionDecay(
                                                result,
                                                LootItem.lootTableItem(block)
                                                        .apply(
                                                                SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                                        .when(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))
                                                                        )
                                                        )
                                        )
                                )
                                .when(HAS_SILK_TOUCH)
                        )
                );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return SBBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
