package com.syric.shores_between.entity.beached_corpses;

import com.syric.shores_between.util.WeightedTable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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

public class AbstractBeachedCorpseEntity extends Animal {

    /**
     * To do:
     * - Make AbstractBeachedCorpse and then have various subclasses DONE
     * - Pushable only very slowly?
     * - Right-click behavior: produce items, turn into skeleton then disappear BUGGY - 2X SPEED
     * - Add sounds to right-click behavior TEST
     * - Disable frustum check
     */

    public final AnimationState belly = new AnimationState();
    public final AnimationState side = new AnimationState();
    public final AnimationState back = new AnimationState();

    public final AnimationState bellysink = new AnimationState();
    public final AnimationState sidesink = new AnimationState();
    public final AnimationState backsink = new AnimationState();

    /**
     * 'Collection Progress' is how far along in collection it is.
     * 'Max Collection' is how many times it can be collected from total.
     * When progress is over half max, the corpse is skeletal.
     * 'State' is whether it's dead, bloated, or skeletal (0, 1, 2 respectively).
     * 'Pose' is whether it's on its belly, side or back (0, 1, 2 respectively).
     */

    private static final EntityDataAccessor<Integer> COLLECTION_PROGRESS = SynchedEntityData.defineId(AbstractBeachedCorpseEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_COLLECTION = SynchedEntityData.defineId(AbstractBeachedCorpseEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(AbstractBeachedCorpseEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> POSE = SynchedEntityData.defineId(AbstractBeachedCorpseEntity.class, EntityDataSerializers.INT);

    private boolean unposed = true;
    private boolean unsunk = true;


    public AbstractBeachedCorpseEntity(EntityType<? extends Animal> entityType, Level level) {
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
        return null;
    }

    public int getRandomState() {
        WeightedTable<Integer> table = new WeightedTable<Integer>()
                .add(0, 1)
                .add(1, 2)
                .add(2, 5);
        return table.getRandom(this.random);
    }

    public int getRandomPose(int state) {
        //If bloated (state == 1) back is more likely
        WeightedTable<Integer> table = new WeightedTable<Integer>();
        if (state == 1) {
            table.add(0, 1).add(1,2).add(2,6);
        } else {
            table.add(0,1).add(1, 2).add(2, 2);
        }
        return table.getRandom(this.random);
    }

    public int getRandomProgress(int state) {
        int half = this.getMaxCollection() / 2;
        //Skeleton
        if (state == 2) {
            return this.random.nextInt(half, getMaxCollection());
        } else {
            return this.random.nextInt(half);
        }
    }

    public int getRandomMaxCollection() {
        return 10;
    }

    private ItemStack getItem(Player player, Vec3 vec, InteractionHand hand) {
        //Skeleton
        if (this.getState() == 2) {
            return new ItemStack(Items.BONE.asItem());
//            return new ItemStack(SBItems.WHALEBONE.asItem());
        }
        //Corpse or bloated
        else {
            return new ItemStack(Items.ROTTEN_FLESH.asItem());
        }
    }

    private SoundEvent getSound(ItemStack droppedItem) {
        return SoundEvents.BONE_BLOCK_BREAK;
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
            if (unsunk && this.getState() == 2) {
                switch (this.entityData.get(POSE)) {
                    case 0:
                        this.bellysink.start(this.tickCount);
                        break;
                    case 1:
                        this.sidesink.start(this.tickCount);
                        break;
                    case 2:
                        this.backsink.start(this.tickCount);
                        break;
                }
                unsunk = false;
            }
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        boolean hand_empty = player.getItemInHand(hand).isEmpty();
        if (hand_empty && player.mayBuild() && !this.level().isClientSide()) {
            ItemStack dropStack = this.getItem(player, vec, hand);
            this.spawnAtLocation(dropStack);
            this.playSound(getSound(dropStack));
            setCollectionProgress(this.getCollectionProgress() + 1);
            return InteractionResult.SUCCESS_NO_ITEM_USED;
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
        builder.define(MAX_COLLECTION, 0);
        builder.define(STATE, 0);
        builder.define(POSE, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("CollectionProgress", this.entityData.get(COLLECTION_PROGRESS));
        compound.putInt("MaxCollection", this.entityData.get(MAX_COLLECTION));
        compound.putInt("State", this.entityData.get(STATE));
        compound.putInt("Pose", this.entityData.get(POSE));
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(COLLECTION_PROGRESS, compound.getInt("CollectionProgress"));
        this.entityData.set(MAX_COLLECTION, compound.getInt("MaxCollection"));
        this.entityData.set(STATE, compound.getInt("State"));
        this.entityData.set(POSE, compound.getInt("Pose"));
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        int state = getRandomState();
        this.entityData.set(STATE, state);
        this.entityData.set(COLLECTION_PROGRESS, this.getRandomProgress(state));
        this.entityData.set(POSE, this.getRandomPose(state));
        this.entityData.set(MAX_COLLECTION, this.getRandomMaxCollection());

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public int getCollectionProgress() {
        return this.entityData.get(COLLECTION_PROGRESS);
    }

    public int getMaxCollection() {
        return this.entityData.get(MAX_COLLECTION);
    }

    public int getState() {
        return this.entityData.get(STATE);
    }

    private void setCollectionProgress(int progress) {
        this.entityData.set(COLLECTION_PROGRESS, progress);
        if (progress > (getMaxCollection() / 2)) {
            this.entityData.set(STATE, 2);
        }
        if (progress >= getMaxCollection()) {
            this.remove(RemovalReason.KILLED);
        }
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
