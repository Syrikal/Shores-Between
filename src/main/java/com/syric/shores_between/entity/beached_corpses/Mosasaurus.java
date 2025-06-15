package com.syric.shores_between.entity.beached_corpses;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class Mosasaurus extends AbstractBeachedCorpse {

    /**
     * To do:
     * - Bloated texture
     */

    public Mosasaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractBeachedCorpse.createAttributes();
    }

    @Override
    public int getRandomMaxCollection() {
        return 10;
    }
}
