package com.syric.shores_between.entity.beached_corpses;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class EnderDragonCopy extends EnderDragon implements Enemy {
    public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(EnderDragonCopy.class, EntityDataSerializers.INT);
    public final double[][] positions = new double[64][3];
    public int posPointer = -1;
    private final EnderDragonPart[] subEntities;
    public final EnderDragonPart head;
    private final EnderDragonPart neck;
    private final EnderDragonPart body;
    private final EnderDragonPart tail1;
    private final EnderDragonPart tail2;
    private final EnderDragonPart tail3;
    private final EnderDragonPart wing1;
    private final EnderDragonPart wing2;
    public float flapTime;
    public boolean inWall;
    public float yRotA;
    private BlockPos fightOrigin = BlockPos.ZERO;
    private final EnderDragonPhaseManager phaseManager;

    public EnderDragonCopy(EntityType<? extends net.minecraft.world.entity.boss.enderdragon.EnderDragon> entityType, Level level) {
        super(EntityType.ENDER_DRAGON, level);
        this.head = new EnderDragonPart(this, "head", 1.0F, 1.0F);
        this.neck = new EnderDragonPart(this, "neck", 3.0F, 3.0F);
        this.body = new EnderDragonPart(this, "body", 5.0F, 3.0F);
        this.tail1 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
        this.tail2 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
        this.tail3 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
        this.wing1 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
        this.wing2 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
        this.subEntities = new EnderDragonPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.wing1, this.wing2};
        this.setHealth(this.getMaxHealth());
        this.noPhysics = true;
        this.noCulling = true;
        this.phaseManager = new EnderDragonPhaseManager(this);
        this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1); // Forge: Fix MC-158205: Make sure part ids are successors of parent mob id
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.subEntities.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.subEntities[i].setId(id + i + 1);
    }

    /**
     * Returns a double[3] array with movement offsets, used to calculate trailing tail/neck positions. [0] = yaw offset, [1] = y offset, [2] = unused, always 0. Parameters: buffer index offset, partial ticks.
     */
    public double[] getLatencyPos(int bufferIndexOffset, float partialTicks) {
        if (this.isDeadOrDying()) {
            partialTicks = 0.0F;
        }

        partialTicks = 1.0F - partialTicks;
        int i = this.posPointer - bufferIndexOffset & 63;
        int j = this.posPointer - bufferIndexOffset - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.positions[i][0];
        double d1 = Mth.wrapDegrees(this.positions[j][0] - d0);
        adouble[0] = d0 + d1 * (double) partialTicks;
        d0 = this.positions[i][1];
        d1 = this.positions[j][1] - d0;
        adouble[1] = d0 + d1 * (double) partialTicks;
        adouble[2] = Mth.lerp(partialTicks, this.positions[i][2], this.positions[j][2]);
        return adouble;
    }

    @Override
    public void aiStep() {
        this.processFlappingMovement();
        if (this.level().isClientSide) {
            this.setHealth(this.getHealth());
        }

        Vec3 movement = this.getDeltaMovement();
        float f9 = 0.2F / ((float) movement.horizontalDistance() * 10.0F + 1.0F);
        f9 *= (float) Math.pow(2.0, movement.y);
        if (this.phaseManager.getCurrentPhase().isSitting()) {
            this.flapTime += 0.1F;
        } else if (this.inWall) {
            this.flapTime += f9 * 0.5F;
        } else {
            this.flapTime += f9;
        }

        this.setYRot(Mth.wrapDegrees(this.getYRot()));
        if (this.posPointer < 0) {
            for (int i = 0; i < this.positions.length; i++) {
                this.positions[i][0] = this.getYRot();
                this.positions[i][1] = this.getY();
            }
        }

        if (++this.posPointer == this.positions.length) {
            this.posPointer = 0;
        }

        this.positions[this.posPointer][0] = this.getYRot();
        this.positions[this.posPointer][1] = this.getY();
        if (this.level().isClientSide) {
            if (this.lerpSteps > 0) {
                this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
                this.lerpSteps--;
            }

            this.phaseManager.getCurrentPhase().doClientTick();
        } else {
            DragonPhaseInstance dragonphaseinstance = this.phaseManager.getCurrentPhase();
            dragonphaseinstance.doServerTick();
            if (this.phaseManager.getCurrentPhase() != dragonphaseinstance) {
                dragonphaseinstance = this.phaseManager.getCurrentPhase();
                dragonphaseinstance.doServerTick();
            }

            Vec3 targetLocation = dragonphaseinstance.getFlyTargetLocation();
            if (targetLocation != null) {
                double delta_x = targetLocation.x - this.getX();
                double delta_y = targetLocation.y - this.getY();
                double delta_z = targetLocation.z - this.getZ();
                double dist_to_target_squared = delta_x * delta_x + delta_y * delta_y + delta_z * delta_z;
                float flySpeed = dragonphaseinstance.getFlySpeed();
                double horizontal_distance = Math.sqrt(delta_x * delta_x + delta_z * delta_z);
                if (horizontal_distance > 0.0) {
                    delta_y = Mth.clamp(delta_y / horizontal_distance, -flySpeed, flySpeed);
                }

                this.setDeltaMovement(this.getDeltaMovement().add(0.0, delta_y * 0.01, 0.0));
                this.setYRot(Mth.wrapDegrees(this.getYRot()));
                Vec3 normal_vec_to_target = targetLocation.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                Vec3 vec32 = new Vec3(
                        Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)),
                        this.getDeltaMovement().y,
                        -Mth.cos(this.getYRot() * (float) (Math.PI / 180.0))
                )
                        .normalize();
                float f5 = Math.max(((float) vec32.dot(normal_vec_to_target) + 0.5F) / 1.5F, 0.0F);
                if (Math.abs(delta_x) > 1.0E-5F || Math.abs(delta_z) > 1.0E-5F) {
                    float f6 = Mth.clamp(Mth.wrapDegrees(180.0F - (float) Mth.atan2(delta_x, delta_z) * (180.0F / (float) Math.PI) - this.getYRot()), -50.0F, 50.0F);
                    this.yRotA *= 0.8F;
                    this.yRotA = this.yRotA + f6 * dragonphaseinstance.getTurnSpeed();
                    this.setYRot(this.getYRot() + this.yRotA * 0.1F);
                }

                float f19 = (float) (2.0 / (dist_to_target_squared + 1.0));
                this.moveRelative(0.06F * (f5 * f19 + (1.0F - f19)), new Vec3(0.0, 0.0, -1.0));
                if (this.inWall) {
                    this.move(MoverType.SELF, this.getDeltaMovement().scale(0.8F));
                } else {
                    this.move(MoverType.SELF, this.getDeltaMovement());
                }

                Vec3 vec33 = this.getDeltaMovement().normalize();
                double d5 = 0.8 + 0.15 * (vec33.dot(vec32) + 1.0) / 2.0;
                this.setDeltaMovement(this.getDeltaMovement().multiply(d5, 0.91F, d5));
            }
        }

        this.yBodyRot = this.getYRot();
        Vec3[] old_part_positions = new Vec3[this.subEntities.length];

        for (int j = 0; j < this.subEntities.length; j++) {
            old_part_positions[j] = new Vec3(this.subEntities[j].getX(), this.subEntities[j].getY(), this.subEntities[j].getZ());
        }

        float f12 = (float) (this.getLatencyPos(5, 1.0F)[1] - this.getLatencyPos(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
        float f13 = Mth.cos(f12);
        float f = Mth.sin(f12);
        float y_rot_in_radians = this.getYRot() * (float) (Math.PI / 180.0);
        float sin_y_rot = Mth.sin(y_rot_in_radians);
        float cos_y_rot = Mth.cos(y_rot_in_radians);
        this.tickPart(this.body, sin_y_rot * 0.5F, 0.0, -cos_y_rot * 0.5F);
        this.tickPart(this.wing1, cos_y_rot * 4.5F, 2.0, sin_y_rot * 4.5F);
        this.tickPart(this.wing2, cos_y_rot * -4.5F, 2.0, sin_y_rot * -4.5F);

        float f2 = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0) - this.yRotA * 0.01F);
        float f16 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0) - this.yRotA * 0.01F);
        float f3 = this.getHeadYOffset();
        this.tickPart(this.head, f2 * 6.5F * f13, f3 + f * 6.5F, -f16 * 6.5F * f13);
        this.tickPart(this.neck, f2 * 5.5F * f13, f3 + f * 5.5F, -f16 * 5.5F * f13);
        double[] adouble = this.getLatencyPos(5, 1.0F);

        //Positions the tail
        for (int k = 0; k < 3; k++) {
            EnderDragonPart enderdragonpart = null;
            if (k == 0) {
                enderdragonpart = this.tail1;
            }

            if (k == 1) {
                enderdragonpart = this.tail2;
            }

            if (k == 2) {
                enderdragonpart = this.tail3;
            }

            double[] adouble1 = this.getLatencyPos(12 + k * 2, 1.0F);
            float f17 = this.getYRot() * (float) (Math.PI / 180.0) + this.rotWrap(adouble1[0] - adouble[0]) * (float) (Math.PI / 180.0);
            float f18 = Mth.sin(f17);
            float f20 = Mth.cos(f17);
            float f22 = (float) (k + 1) * 2.0F;
            this.tickPart(
                    enderdragonpart,
                    -(sin_y_rot * 1.5F + f18 * f22) * f13,
                    adouble1[1] - adouble[1] - (double) ((f22 + 1.5F) * f) + 1.5,
                    (cos_y_rot * 1.5F + f20 * f22) * f13
            );
        }

        for (int l = 0; l < this.subEntities.length; l++) {
            this.subEntities[l].xo = old_part_positions[l].x;
            this.subEntities[l].yo = old_part_positions[l].y;
            this.subEntities[l].zo = old_part_positions[l].z;
            this.subEntities[l].xOld = old_part_positions[l].x;
            this.subEntities[l].yOld = old_part_positions[l].y;
            this.subEntities[l].zOld = old_part_positions[l].z;
        }

    }

    private void tickPart(EnderDragonPart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ);
    }

    private float getHeadYOffset() {
        if (this.phaseManager.getCurrentPhase().isSitting()) {
            return -1.0F;
        } else {
            double[] adouble = this.getLatencyPos(5, 1.0F);
            double[] adouble1 = this.getLatencyPos(0, 1.0F);
            return (float) (adouble[1] - adouble1[1]);
        }
    }

    /**
     * Simplifies the value of a number by adding/subtracting 180 to the point that the number is between -180 and 180.
     */
    private float rotWrap(double angle) {
        return (float) Mth.wrapDegrees(angle);
    }

    public float getHeadPartYOffset(int partIndex, double[] spineEndOffsets, double[] headPartOffsets) {
        DragonPhaseInstance dragonphaseinstance = this.phaseManager.getCurrentPhase();
        EnderDragonPhase<? extends DragonPhaseInstance> enderdragonphase = dragonphaseinstance.getPhase();
        double d0;
        if (enderdragonphase == EnderDragonPhase.LANDING || enderdragonphase == EnderDragonPhase.TAKEOFF) {
            BlockPos blockpos = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.fightOrigin));
            double d1 = Math.max(Math.sqrt(blockpos.distToCenterSqr(this.position())) / 4.0, 1.0);
            d0 = (double) partIndex / d1;
        } else if (dragonphaseinstance.isSitting()) {
            d0 = partIndex;
        } else if (partIndex == 6) {
            d0 = 0.0;
        } else {
            d0 = headPartOffsets[1] - spineEndOffsets[1];
        }

        return (float) d0;
    }

    public Vec3 getHeadLookVector(float partialTicks) {
        DragonPhaseInstance dragonphaseinstance = this.phaseManager.getCurrentPhase();
        EnderDragonPhase<? extends DragonPhaseInstance> enderdragonphase = dragonphaseinstance.getPhase();
        Vec3 vec3;
        if (enderdragonphase == EnderDragonPhase.LANDING || enderdragonphase == EnderDragonPhase.TAKEOFF) {
            BlockPos blockpos = this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.fightOrigin));
            float f5 = Math.max((float) Math.sqrt(blockpos.distToCenterSqr(this.position())) / 4.0F, 1.0F);
            float f2 = 6.0F / f5;
            float f3 = this.getXRot();
            this.setXRot(-f2 * 1.5F * 5.0F);
            vec3 = this.getViewVector(partialTicks);
            this.setXRot(f3);
        } else if (dragonphaseinstance.isSitting()) {
            float f = this.getXRot();
            this.setXRot(-45.0F);
            vec3 = this.getViewVector(partialTicks);
            this.setXRot(f);
        } else {
            vec3 = this.getViewVector(partialTicks);
        }

        return vec3;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_PHASE.equals(key) && this.level().isClientSide) {
            this.phaseManager.setPhase(EnderDragonPhase.getById(this.getEntityData().get(DATA_PHASE)));
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.neoforged.neoforge.entity.PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        if (true) return; // Forge: Fix MC-158205: Moved into setId()
        EnderDragonPart[] aenderdragonpart = this.getSubEntities();

        for (int i = 0; i < aenderdragonpart.length; i++) {
            aenderdragonpart[i].setId(i + packet.getId());
        }
    }
}