package com.syric.shores_between.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class SBWoodTypes {

    public static final BlockSetType DRIFTWOOD_WOOD_SET = new BlockSetType(new ResourceLocation("shores_between", "driftwood").toString());
    public static final BlockSetType PETRIFIED_WOOD_SET = new BlockSetType(new ResourceLocation("shores_between", "petrified").toString());
    public static final BlockSetType MISTWOOD_WOOD_SET = new BlockSetType(new ResourceLocation("shores_between", "mistwood").toString());

    public static final WoodType DRIFTWOOD = WoodType.register(new WoodType((new ResourceLocation("shores_between", "driftwood")).toString(), DRIFTWOOD_WOOD_SET));
    public static final WoodType MISTWOOD = WoodType.register(new WoodType((new ResourceLocation("shores_between", "mistwood")).toString(), MISTWOOD_WOOD_SET));

}
