package com.syric.shores_between.entity.beached_corpses;

import com.github.alexthe666.iceandfire.entity.util.IMultipartEntity;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.util.WeightedTable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbstractBeachedCorpse extends Animal {

    /**
     * To do:
     * - Natural generation ALMOST DONE, NEED TO FIX WORLDGEN
     * - Better hitbox
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

    private static final EntityDataAccessor<Integer> COLLECTION_PROGRESS = SynchedEntityData.defineId(AbstractBeachedCorpse.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_COLLECTION = SynchedEntityData.defineId(AbstractBeachedCorpse.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(AbstractBeachedCorpse.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> POSE = SynchedEntityData.defineId(AbstractBeachedCorpse.class, EntityDataSerializers.INT);

    private boolean unposed = true;
    private boolean unsunk = true;
    private boolean buried = false;

    private final AbstractBeachedCorpsePart[] parts;


    public AbstractBeachedCorpse(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        ShoresBetween.LOGGER.debug("Creating an AbstractBeachedCorpse");
        this.parts = this.getPartsList();
        this.noCulling = true;
        this.setId(ENTITY_COUNTER.getAndAdd(this.parts.length + 1) + 1); // Forge: Fix MC-158205: Make sure part ids are successors of parent mob id
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
        if (!this.level().isClientSide) {
            //Pushability despite hard hitbox
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
            if (!list.isEmpty()) {
                for (Entity entity : list) {
                    if (!(entity instanceof ItemEntity) && !(entity instanceof AbstractBeachedCorpsePart)) {
                        this.push(entity);
                    }
                }
            }
            //Expel item entities
//            List<Entity> list2 = this.level().getEntities(this, this.getBoundingBox(), x -> x instanceof ItemEntity);
//            if (!list2.isEmpty()) {
//                for (Entity entity : list2) {
//                    Vec3 offset = new Vec3(entity.getX() - this.getX(), (entity.getY() - this.getY()), entity.getZ() - this.getZ());
//                    Vec3 push = offset.scale(0.3 / offset.length());
//                    entity.moveTo(entity.position().add(push));
//                }
//            }
        }
    }

    @Override
    public void aiStep() {
        this.poseParts();
    }

    //region Default Entity Stuff
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
    //endregion

    //region Randomization

    //It is skeletal/bloated/dead at a 5/2/1 ratio. If buried, it's instead 10/2/0.
    public int getRandomState() {
        WeightedTable<Integer> table = new WeightedTable<Integer>()
                .add(0, buried ? 0 : 1)
                .add(1, 2)
                .add(2, buried ? 10 : 5);
        return table.getRandom(this.random);
    }

    //If bloated (state == 1) back is more likely
    public int getRandomPose(int state) {
        WeightedTable<Integer> table = new WeightedTable<>();
        if (state == 1) {
            table.add(0, 1).add(1,2).add(2,6);
        } else {
            table.add(0,1).add(1, 2).add(2, 2);
        }
        return table.getRandom(this.random);
    }

    //Its progress is randomized depending on whether or not it's skeletal. It always has at least 3 uses left.
    public int getRandomProgress(int state) {
        int half = this.getMaxCollection() / 2;
        //Skeleton
        if (state == 2) {
            return this.random.nextInt(half, Math.max(getMaxCollection() - 3, half + 1));
        } else {
            return this.random.nextInt(half);
        }
    }

    public int getRandomMaxCollection() {
        return 10;
    }

    //endregion

    //region Looting
    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
//        ShoresBetween.LOGGER.debug("Interacting with beached corpse on the " + (this.level().isClientSide ? "client" : "server") + " side");
        boolean hand_empty = player.getItemInHand(hand).isEmpty();
        if (hand_empty && player.mayBuild()) {

            ShoresBetween.LOGGER.debug("Interacted");
            this.poseParts();
            for (int i = 0; i < this.getPartsList().length; i++) {
                AbstractBeachedCorpsePart part = this.getPartsList()[i];
                ShoresBetween.LOGGER.debug("%s position: (%s, %s, %s)".formatted(part.name, part.getX(), part.getY(), part.getZ()));
            }

            ItemStack dropStack = this.getItem(player, vec, hand);
            this.playSound(getSound(dropStack));
            if (!this.level().isClientSide) {
                this.spawnItem(dropStack, player);
            }
            setCollectionProgress(this.getCollectionProgress() + 1);
            return InteractionResult.SUCCESS;
        } else {
            return super.interactAt(player, vec, hand);
        }
    }

    public ItemEntity spawnItem(ItemStack stack, Player player) {
        if (stack.isEmpty()) {
            return null;
        } else if (this.level().isClientSide) {
            return null;
        } else {
            Vec3 offset = new Vec3(player.getX() - this.getX(), player.getY() - this.getY(), player.getZ() - this.getZ());

            double offset_distance = Math.min(offset.length() / 2, 1.2);
            Vec3 position_offset = offset.scale(offset_distance / offset.length());

            Vec3 spawnPosition = this.getPosition(0).add(0, 1, 0).add(position_offset);

            double pushIntensity = Math.min(0.05 * offset.length(), 0.3);
            Vec3 push = offset.scale(pushIntensity / offset.length()).add(0, 0.2, 0);

            ItemEntity itementity = new ItemEntity(this.level(), spawnPosition.x, spawnPosition.y, spawnPosition.z, stack);
            itementity.setDeltaMovement(push);
            itementity.setDefaultPickUpDelay();
            if (captureDrops() != null) {
                captureDrops().add(itementity);
            } else {
                this.level().addFreshEntity(itementity);
            }
            return itementity;
        }
    }

    private ItemStack getItem(Player player, Vec3 vec, InteractionHand hand) {
        //Skeleton
        if (this.getState() == 2) {
            int count = this.random.nextInt(1, 3);
            return new ItemStack(Items.BONE.asItem(), count);
//            return new ItemStack(SBItems.WHALEBONE.asItem(), count);
        }
        //Corpse or bloated
        else {
            int count = this.random.nextInt(1, 4);
            return new ItemStack(Items.ROTTEN_FLESH.asItem(), count);
        }
    }

    private SoundEvent getSound(ItemStack droppedItem) {
        if (droppedItem.getItem() == Items.BONE) {
            return SoundEvents.SKELETON_AMBIENT;
        } else if (droppedItem.getItem() == Items.ROTTEN_FLESH) {
            return SoundEvents.HONEY_BLOCK_BREAK;
        } else {
            return SoundEvents.ARMOR_EQUIP_LEATHER.value();
        }
    }

    //endregion

    //region Data
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

    public int getCollectionProgress() {
        return this.entityData.get(COLLECTION_PROGRESS);
    }

    public int getMaxCollection() {
        return this.entityData.get(MAX_COLLECTION);
    }

    public int getState() {
        return this.entityData.get(STATE);
    }

    public int getCorpsePose() {
        return this.entityData.get(POSE);
    }

    private void setCollectionProgress(int progress) {
        this.entityData.set(COLLECTION_PROGRESS, progress);
        if (progress >= (getMaxCollection() / 2)) {
            this.entityData.set(STATE, 2);
        }
        if (progress >= getMaxCollection()) {
            this.remove(RemovalReason.KILLED);
        }
    }

    //endregion

    //region Collision and Pushing
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return (entity.canBeCollidedWith() || entity.isPushable());
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void push(Entity entity) {

        if (entity instanceof ItemEntity) {
            super.push(entity);
        } else if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
            if (!this.isPassengerOfSameVehicle(entity)) {
                if (!entity.noPhysics && !this.noPhysics) {
                    double d0 = entity.getX() - this.getX();
                    double d1 = entity.getZ() - this.getZ();
                    double d2 = Mth.absMax(d0, d1);
                    if (d2 >= 0.01F) {
                        d2 = Math.sqrt(d2);
                        d0 /= d2;
                        d1 /= d2;
                        double d3 = 1.0 / d2;
                        if (d3 > 1.0) {
                            d3 = 1.0;
                        }

                        d0 *= d3;
                        d1 *= d3;
                        d0 *= 0.05F;
                        d1 *= 0.05F;
                        this.push(0.08 * -d0, 0.0, 0.08 * -d1);

                        if (!entity.isVehicle() && entity.isPushable()) {
                            entity.push(2 * d0, 0.0, 2 * d1);
                        }
                    }
                }
            }
        }
    }
    //endregion

    //region Invulnerability
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

    @Override
    public void checkDespawn() {
    }
    //endregion

    //region Placement
    //Returns the side length of the square under the creature's hitbox that should be checked for support
    public double getArea(boolean tail) {
        return tail ? 2 : 6;
    }

    //Returns a distance approximately two-thirds of the lenth of its tail
    public double getTailDistance() {
        return 4;
    }

    public void setBuried(boolean buried) {
        this.buried = buried;
    }
    //endregion

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        int state = getRandomState();
        this.entityData.set(MAX_COLLECTION, this.getRandomMaxCollection());
        this.entityData.set(STATE, state);
        this.entityData.set(COLLECTION_PROGRESS, this.getRandomProgress(state));
        this.entityData.set(POSE, this.getRandomPose(state));

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //region Parts Stuff
    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.parts.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.parts[i].setId(id + i + 1);
    }

    public AbstractBeachedCorpsePart[] getPartsList() {
        return new AbstractBeachedCorpsePart[]{};
    }

    public void poseParts() {}

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.neoforged.neoforge.entity.PartEntity<?>[] getParts() {
        return this.parts;
    }

    //endregion

}
