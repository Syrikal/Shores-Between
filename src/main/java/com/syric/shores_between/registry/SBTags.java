package com.syric.shores_between.registry;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class SBTags {

    public static class Blocks {
        public static final TagKey<Block> DRIFTWOOD_LOGS = tag("driftwood_logs");
        public static final TagKey<Block> MISTWOOD_LOGS = tag("mistwood_logs");
        public static final TagKey<Block> PETRIFIED_LOGS = tag("petrified_logs");
        public static final TagKey<Block> CONCRETE = tag("concrete");
        public static final TagKey<Block> INFINIBURN_BREACH = tag("infiniburn_breach");
        public static final TagKey<Block> MOSS_CARPET_GROWS_ON = tag("moss_carpet_grows");
        public static final TagKey<Block> FALLEN_LOGS_REPLACE = tag("fallen_logs_replace");
        public static final TagKey<Block> BREACH_GROUND = tag("breach_ground");
        public static final TagKey<Block> SHINGLE = tag("shingle");
        public static final TagKey<Block> PEBBLES = tag("pebbles");

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

    public static class Biomes {
        public static final TagKey<Biome> IS_BREACH = tag("is_breach");
        public static final TagKey<Biome> MISTWOOD = tag("mistwood");

        public static final TagKey<Biome> HAS_TANGLED_SHINGLE = tag("has_tangled_shingle");
        public static final TagKey<Biome> HAS_RUSTY_SCRAP = tag("has_rusty_scrap");

        public static final TagKey<Biome> HAS_DRIFTWOOD_LOGS = tag("has_driftwood_logs");
        public static final TagKey<Biome> HAS_PETRIFIED_LOGS = tag("has_petrified_logs");

        public static final TagKey<Biome> HAS_SEAWEED_CLUMPS = tag("has_seaweed_clumps");
        public static final TagKey<Biome> HAS_DEAD_FISH = tag("has_dead_fish");

        public static final TagKey<Biome> HAS_SPARSE_BUSHES = tag("has_sparse_bushes");

        public static final TagKey<Biome> HAS_SMALL_BOULDERS = tag("has_small_boulders");
        public static final TagKey<Biome> HAS_LARGE_BOULDERS = tag("has_large_boulders");
        public static final TagKey<Biome> HAS_MORE_BOULDERS = tag("has_small_boulders");
        public static final TagKey<Biome> HAS_SMALL_OUTCROPS = tag("has_small_outcrops");
        public static final TagKey<Biome> HAS_MOSSY_OUTCROPS = tag("has_mossy_outcrops");
        public static final TagKey<Biome> HAS_RARE_MOSSY_OUTCROPS = tag("has_rare_mossy_outcrops");
        public static final TagKey<Biome> HAS_EERIE_BOULDERS = tag("has_eerie_boulders");
        public static final TagKey<Biome> HAS_CORPSE_BOULDERS = tag("has_corpse_boulders");


        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation(ShoresBetween.MODID, name));
        }
    }

}
