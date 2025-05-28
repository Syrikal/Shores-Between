package com.syric.shores_between.entity.custom;

import com.syric.shores_between.registry.SBEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MosasaurusEntity extends Animal {

    /**
     * To do:
     * - Make AbstractBeachedCorpse and then have various subclasses
     * - Don't extend Animal?
     * - Don't take damage
     * - Not pushable
     * - Don't animate when pushed/taking damage
     * - Solid hitbox
     * - Better hitbox
     * - Right-click behavior: produce items, turn into skeleton then disappear
     * - Disable frustum check
     * - Multiple animation states
     * - Natural generation
     * - Natural generation randomizes pose and decay state
     * - Bloated texture
     */

    public final AnimationState idle = new AnimationState();
    private int idleAnimationTimeout = 0;

    public MosasaurusEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return SBEntities.MOSASAURUS.get().create(level);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 5;
            this.idle.start(this.tickCount);
        } else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

}
