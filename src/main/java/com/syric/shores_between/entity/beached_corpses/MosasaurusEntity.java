package com.syric.shores_between.entity.beached_corpses;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class MosasaurusEntity extends AbstractBeachedCorpseEntity {

    /**
     * To do:
     * - Better hitbox
     * - Natural generation
     * - Bloated texture
     */

    public MosasaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractBeachedCorpseEntity.createAttributes();
    }

    @Override
    public int getRandomMaxCollection() {
        return 10;
    }
}
