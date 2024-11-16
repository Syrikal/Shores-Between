package com.syric.shores_between.worldgen.feature.tree;

import com.syric.shores_between.worldgen.feature.SBConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class SBTreeGrowers {
    public static final TreeGrower MISTWOOD_TREE = tree("mistwood_tree", SBConfiguredFeatures.MISTWOOD_TREE_INTERIOR);
    public static final TreeGrower MISTWOOD_EDGE_TREE = tree("mistwood_edge_tree", SBConfiguredFeatures.MISTWOOD_EDGE_TREE);
    public static final TreeGrower MISTWOOD_TALL_TREE = tree("mistwood_tall_tree", SBConfiguredFeatures.MISTWOOD_TALL_TREE);
    public static final TreeGrower MISTWOOD_THIN_TREE = tree("mistwood_thin_tree", SBConfiguredFeatures.MISTWOOD_THIN_TREE);
//
    public static final TreeGrower PETRIFIED_TREE = tree("petrified_tree", SBConfiguredFeatures.PETRIFIED_TREE);
    public static final TreeGrower WINDBLOWN_TREE = tree("windblown_tree", SBConfiguredFeatures.WINDBLOWN_TREE);
    public static final TreeGrower DEAD_TREE = tree("dead_tree", SBConfiguredFeatures.DEAD_TREE);

    public SBTreeGrowers() {}

    private static TreeGrower tree(String name, ResourceKey<ConfiguredFeature<?, ?>> tree) {
        return new TreeGrower(name, Optional.empty(), Optional.of(tree), Optional.empty());
    }

}
