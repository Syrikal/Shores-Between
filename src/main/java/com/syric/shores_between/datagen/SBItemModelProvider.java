package com.syric.shores_between.datagen;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class SBItemModelProvider extends ItemModelProvider {
    public SBItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShoresBetween.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
