package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SBBlockStateProvider extends BlockStateProvider {
    public SBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ShoresBetween.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
