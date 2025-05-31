package com.syric.shores_between.entity.custom;

import com.syric.shores_between.registry.SBEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MosasaurusEntity extends Animal {

    /**
     * To do:
     * - Make AbstractBeachedCorpse and then have various subclasses
     * - Don't extend Animal?
     * - Don't take damage DONE
     * - Not pushable DONE
     * - Pushable only very slowly?
     * - Don't animate when pushed/taking damage DONE
     * - Solid hitbox DONE
     * - Better hitbox
     * - Right-click behavior: produce items, turn into skeleton then disappear BUGGY - 2X SPEED
     * - Add sounds to right-click behavior
     * - Disable frustum check
     * - Multiple animation states DONE
     * - Natural generation
     * - Natural generation randomizes pose and decay state DONE
     * - Bloated texture
     * - If it spawns as a skeleton, don't let the ribcage float DONE
     */

    public final AnimationState belly = new AnimationState();
    public final AnimationState side = new AnimationState();
    public final AnimationState back = new AnimationState();
    public final AnimationState sink = new AnimationState();
    public final AnimationState sink2 = new AnimationState();

    private static final EntityDataAccessor<Integer> COLLECTION_PROGRESS = SynchedEntityData.defineId(MosasaurusEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BLOATED = SynchedEntityData.defineId(MosasaurusEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> POSE = SynchedEntityData.defineId(MosasaurusEntity.class, EntityDataSerializers.INT);
    private static final int MAX_COLLECTION = 10;

    private boolean unposed = true;
    private boolean unsunk = true;


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
    public boolean isFood(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        return SBEntities.MOSASAURUS.get().create(level);
    }

    private void pose() {
        int pose = this.entityData.get(POSE) % 3;
        this.belly.animateWhen(pose == 0, this.tickCount);
        this.side.animateWhen(pose == 1, this.tickCount);
        this.back.animateWhen(pose == 2, this.tickCount);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (unposed) {
                this.pose();
                unposed = false;
            }
            if (unsunk && (double) this.entityData.get(COLLECTION_PROGRESS) / MAX_COLLECTION > 0.5) {
                if (this.entityData.get(POSE) == 1) {
                    this.sink2.start(this.tickCount);
                } else {
                    this.sink.start(this.tickCount);
                }
                unsunk = false;
            }
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        boolean hand_empty = player.getItemInHand(hand).isEmpty();
        if (hand_empty && this.getCollectionProgress() < this.getMaxCollection() && player.mayBuild() && !this.level().isClientSide()) {
            if (this.getCollectionProgress() < getMaxCollection() / 2) {
                ItemStack flesh = new ItemStack(Items.ROTTEN_FLESH.asItem());
                this.spawnAtLocation(flesh);
            } else {
                ItemStack bone = new ItemStack(Items.BONE.asItem());
                this.spawnAtLocation(bone);
            }
            this.entityData.set(COLLECTION_PROGRESS, this.getCollectionProgress() + 1);
            if (this.getCollectionProgress() == this.getMaxCollection()) {
                this.remove(RemovalReason.KILLED);
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.interactAt(player, vec, hand);
        }
    }

//    @Override
//    public void push(Entity entity) {
//        if (!this.isPassengerOfSameVehicle(entity)) {
//            if (!entity.noPhysics && !this.noPhysics) {
//                double d0 = entity.getX() - this.getX();
//                double d1 = entity.getZ() - this.getZ();
//                double d2 = Mth.absMax(d0, d1);
//                if (d2 >= 0.01F) {
//                    d2 = Math.sqrt(d2);
//                    d0 /= d2;
//                    d1 /= d2;
//                    double d3 = 1.0 / d2;
//                    if (d3 > 1.0) {
//                        d3 = 1.0;
//                    }
//
//                    d0 *= d3;
//                    d1 *= d3;
//                    d0 *= 0.05F;
//                    d1 *= 0.05F;
//                    if (!this.isVehicle() && this.isPushable()) {
//                        this.push(0.1 * -d0, 0.0, 0.1 * -d1);
//                    }
//
//                    if (!entity.isVehicle() && entity.isPushable()) {
//                        entity.push(d0, 0.0, d1);
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLLECTION_PROGRESS, 0);
        builder.define(BLOATED, false);
        builder.define(POSE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("CollectionProgress", this.entityData.get(COLLECTION_PROGRESS));
        compound.putBoolean("Bloated", this.entityData.get(BLOATED));
        compound.putInt("Pose", this.entityData.get(POSE));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(COLLECTION_PROGRESS, compound.getInt("CollectionProgress"));
        this.entityData.set(BLOATED, compound.getBoolean("Bloated"));
        this.entityData.set(POSE, compound.getInt("Pose"));
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.entityData.set(COLLECTION_PROGRESS, this.getRandom().nextInt(10));
        this.entityData.set(BLOATED, false);
        this.entityData.set(POSE, this.getRandom().nextInt(3));

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public boolean isBloated() {
        return this.entityData.get(BLOATED);
    }

    public int getCollectionProgress() {
        return this.entityData.get(COLLECTION_PROGRESS);
    }

    public int getMaxCollection() {
        return MAX_COLLECTION;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source != this.level().damageSources().fellOutOfWorld()) {
            return false;
        }
        return super.hurt(source, amount);
    }
}
