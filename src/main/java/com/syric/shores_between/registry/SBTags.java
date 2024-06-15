package com.syric.shores_between.registry;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SBTags {

    public static class Blocks {
        public static final TagKey<Block> DRIFTWOOD_LOGS = tag("driftwood_logs");
        public static final TagKey<Block> MISTWOOD_LOGS = tag("mistwood_logs");
        public static final TagKey<Block> PETRIFIED_LOGS = tag("petrified_logs");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(ShoresBetween.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> DRIFTWOOD_LOGS = tag("driftwood_logs");
        public static final TagKey<Item> MISTWOOD_LOGS = tag("mistwood_logs");
        public static final TagKey<Item> PETRIFIED_LOGS = tag("petrified_logs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(ShoresBetween.MODID, name));
        }

    }

}
