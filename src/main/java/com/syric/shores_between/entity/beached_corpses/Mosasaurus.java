package com.syric.shores_between.entity.beached_corpses;

import com.syric.shores_between.ShoresBetween;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Mosasaurus extends AbstractBeachedCorpse {

    /**
     * To do:
     * - Bloated texture
     */

    private AbstractBeachedCorpsePart head_1; //0.6x0.6, 3.44 forward
    private AbstractBeachedCorpsePart head_2; //0.6x0.75, 2.69 forward
    private AbstractBeachedCorpsePart neck; //1x1.4, 2 forward
    private AbstractBeachedCorpsePart front_torso; //1.8x1.9, 0.69 forward
    private AbstractBeachedCorpsePart rear_torso;  //1.8x1.9, 0.69 back
    private AbstractBeachedCorpsePart tail_1; //1.13x1.31, 1.25 back, 0.25/0/-0.31 left
    private AbstractBeachedCorpsePart tail_2; //1.13x1.13, 2.63 back, 0.5/0.125/-0.63 left
//    private AbstractBeachedCorpsePart tail_3;
//    private AbstractBeachedCorpsePart tail_4;

    private static final Vec3[] offsets_belly = new Vec3[]{
            new Vec3(0, 0, -3.44), //head 1
            new Vec3(0, 0, -2.69), //head 2
            new Vec3(0, 0, -2), //neck
            new Vec3(0, 0, -0.69), //front_torso
            new Vec3(0, 0, 0.69), //rear_torso
            new Vec3(-0.25, 0, 1.25), //tail_1
            new Vec3(-0.5, 0, 2.63), //tail_2
//            new Vec3(), //tail_3
//            new Vec3() //tail_4
    };
    private static final Vec3[] offsets_side = new Vec3[]{};
    private static final Vec3[] offsets_back = new Vec3[]{};

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

    @Override
    public AbstractBeachedCorpsePart[] getPartsList() {
        this.head_1 = new AbstractBeachedCorpsePart(this, "head_1", 0.6F, 0.6F);
        this.head_2 = new AbstractBeachedCorpsePart(this, "head_2", 0.6F, 0.75F);
        this.neck = new AbstractBeachedCorpsePart(this, "neck", 1F, 1.4F);
        this.front_torso = new AbstractBeachedCorpsePart(this, "front_torso", 1.8F, 1.9F);
        this.rear_torso = new AbstractBeachedCorpsePart(this, "rear_torso", 1.8F, 1.9F);
        this.tail_1 = new AbstractBeachedCorpsePart(this, "tail_1", 1.13F, 1.31F);
        this.tail_2 = new AbstractBeachedCorpsePart(this, "tail_2", 1.13F, 1.13F);
//        ShoresBetween.LOGGER.debug("Created sub-parts for a mosasaurus");
        return new AbstractBeachedCorpsePart[]{
                this.head_1,
                this.head_2,
                this.neck,
                this.front_torso,
                this.rear_torso,
                this.tail_1,
                this.tail_2
        };
    }

    @Override
    public void poseParts() {
        ShoresBetween.LOGGER.debug("posing sub-parts for a mosasaurus");

        float angle = this.getYRot();
        Vec3[] part_positions;
        part_positions = switch (this.getCorpsePose()) {
            case 0 -> offsets_belly;
            case 1 -> offsets_belly;
            case 2 -> offsets_belly;
            default -> offsets_belly;
//            case 0 -> offsets_belly;
//            case 1 -> offsets_side;
//            case 2 -> offsets_back;
//            default -> offsets_belly;
        };

        Vec3[] oldPartPositions = new Vec3[this.getPartsList().length];
        for (int i = 0; i < this.getPartsList().length; i++) {
            oldPartPositions[i] = (new Vec3(this.getPartsList()[i].getX(), this.getPartsList()[i].getY(), this.getPartsList()[i].getZ()));
        }

        for (int i = 0; i < this.getPartsList().length; i++) {
            Vec3 offset = part_positions[i].yRot(angle);
            AbstractBeachedCorpsePart part = this.getPartsList()[i];
            Vec3 newPos = this.getPosition(0).add(offset);
            ShoresBetween.LOGGER.debug("Attempting to place part %s at position (%s, %s, %s)".formatted(part.name, newPos.x, newPos.y, newPos.z));
            part.moveTo(newPos);
            ShoresBetween.LOGGER.debug("Placed part %s at position (%s, %s, %s)".formatted(part.name, part.getX(), part.getY(), part.getZ()));
        }

        for (int l = 0; l < this.getPartsList().length; l++) {
            this.getPartsList()[l].xo = oldPartPositions[l].x;
            this.getPartsList()[l].yo = oldPartPositions[l].y;
            this.getPartsList()[l].zo = oldPartPositions[l].z;
            this.getPartsList()[l].xOld = oldPartPositions[l].x;
            this.getPartsList()[l].yOld = oldPartPositions[l].y;
            this.getPartsList()[l].zOld = oldPartPositions[l].z;
        }
    }

}
