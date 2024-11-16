package com.syric.shores_between.worldgen.feature.rocks.boulder_decoration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.feature.rocks.PlaceTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UndercutBoulderDecorator extends BoulderDecorator {

    public static final MapCodec<UndercutBoulderDecorator> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                    Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(x -> x.probability),
                    Codec.floatRange(0.0F, 5.0F).fieldOf("size_multiplier").forGetter(x -> x.size_multiplier)
            )
                    .apply(builder, UndercutBoulderDecorator::new)
    );
    private final float probability;
    private final float size_multiplier;

    public UndercutBoulderDecorator(float probability, float size_multiplier) {
        this.probability = probability;
        this.size_multiplier = size_multiplier;
    }

    @Override
    public BoulderDecoratorType<?> type() {
        return BoulderDecoratorType.UNDERCUT.get();
    }

    @Override
    public void place(Context context, HashMap<BlockPos, PlaceTypes.PlaceType> placeTypeMap, HashMap<BlockPos, BlockState> placementMap, Set<BlockPos> undercutSet, HashMap<BlockPos, BlockState> decorationPlacementMap) {
        BlockPos origin = context.origin().below(2);

        //Don't place if the boulder's underwater
        if (context.origin().getY() < 64) {
            return;
        }

        //Obtain downhill direction
        Vec3 downhillDirectionVector = new Vec3(0, 0, 0);
        boolean goodDownhillVector = false;
        int attempts = 0;
        int size = 2;
        while (!goodDownhillVector) {
            downhillDirectionVector = getDownhillDirection(context.level(), origin, size);
            attempts++;
            if (downhillDirectionVector.x == 0 && downhillDirectionVector.z == 0) {
                size *= 2;
            } else {
                goodDownhillVector = true;
            }
            if (attempts > 4) {
                downhillDirectionVector = new Vec3(1, 0, 0);
                goodDownhillVector = true;
            }
        }
//        ShoresBetween.LOGGER.debug("Found vector pointing downhill: " + downhillDirectionVector);


        //Angle to rotate by
        double yaw_temp = Math.atan(downhillDirectionVector.z / downhillDirectionVector.x) - Math.PI / 2;
        if (downhillDirectionVector.x < 0) {
            yaw_temp += Math.PI;
        }
        double yaw = yaw_temp;
//        ShoresBetween.LOGGER.debug("Rotating by " + yaw / (Math.PI) + "pi radians");

        //Come up with a bounding box in that direction
        Vec3 startingCircleCenter = origin.getCenter();
        Vec3 downhillCircleCenter = startingCircleCenter.add(downhillDirectionVector.normalize().scale(20*size_multiplier));
        float downhillCircleRadius = 20 * size_multiplier;
        float startingCircleRadius = 6;

        //Only place if it's water downhill
        BlockPos checkForWaterPos = new BlockPos((int) downhillCircleCenter.x, 62, (int) downhillCircleCenter.z);
        if (!context.isFluidSource(checkForWaterPos)) {
            return;
        }

//        ShoresBetween.LOGGER.debug("Starting circle at " + startingCircleCenter);
//        ShoresBetween.LOGGER.debug("Downhill circle at " + downhillCircleCenter);

        Vec3i downhillCorner1 = new Vec3i(
                (int) (downhillCircleCenter.x + downhillCircleRadius),
                (int) (downhillCircleCenter.y + 4),
                (int) (downhillCircleCenter.z + downhillCircleRadius)
        );
        Vec3i downhillCorner2 = new Vec3i(
                (int) (downhillCircleCenter.x - downhillCircleRadius),
                (int) (downhillCircleCenter.y - 5),
                (int) (downhillCircleCenter.z - downhillCircleRadius)
        );
        Vec3i startingCorner1 = new Vec3i(
                (int) (startingCircleCenter.x + startingCircleRadius),
                (int) (startingCircleCenter.y + 2),
                (int) (startingCircleCenter.z + startingCircleRadius)
        );
        Vec3i startingCorner2 = new Vec3i(
                (int) (startingCircleCenter.x - startingCircleRadius),
                (int) (startingCircleCenter.y - 2),
                (int) (startingCircleCenter.z - startingCircleRadius)
        );

        BoundingBox downhillBox = BoundingBox.fromCorners(downhillCorner1, downhillCorner2);
        BoundingBox startingBox = BoundingBox.fromCorners(startingCorner1, startingCorner2);

        Optional<BoundingBox> finalBox = BoundingBox.encapsulatingBoxes(List.of(downhillBox, startingBox));

        //For every block in that bounding box:
        finalBox.ifPresent(boundingBox -> BlockPos.betweenClosedStream(boundingBox)
                .filter(pos -> {
                    //Generate a vector from the origin to that block
                    Vec3 relative_position = pos.getCenter().subtract(origin.getCenter());
//                    ShoresBetween.LOGGER.debug("Relative position: " + relative_position);

                    //Rotate it appropriately
                    Vec3 rotated = relative_position.yRot((float) yaw);
//                    ShoresBetween.LOGGER.debug("Rotated position: " + rotated);

                    //Check if it's within the function
                    double output = sdUnevenCapsule(rotated.x, rotated.z, size_multiplier);
                    output *= output < 0 ? 0.2 : 0.6;
                    return output < rotated.y;
                })
                .forEach(pos -> undercutSet.add(pos.immutable())));
    }

    private double sdUnevenCapsule(double x, double z, double size_multiplier) {
        double h = 20 * size_multiplier;
        double r1 = 2;
        double r2 = h / 2;
        x = Math.abs(x);
        double b = (r1 - r2) / h;
        double a = Math.sqrt(1.0 - b * b);
        double k = -b*x + a*z;
        if (k < 0.0) {
            return (length(x, z) - r1);
        } else if (k > a * h) {
            return  (length(x, z-h) - r2);
        } else {
            return a*x + b*z - r1;
        }
    }

    private double length(double a, double b) {
        return Math.sqrt(a*a + b*b);
    }

    private Vec3 getDownhillDirection(LevelSimulatedReader level, BlockPos pos, int radius) {
        float xSlope = (float) (getHeightAtOffset(level, pos, radius, Direction.EAST) - getHeightAtOffset(level, pos, radius, Direction.WEST)) / (2 * radius);
        float zSlope = (float) (getHeightAtOffset(level, pos, radius, Direction.SOUTH) - getHeightAtOffset(level, pos, radius, Direction.NORTH)) / (2 * radius);
        return new Vec3(-xSlope, 0, -zSlope);
    }

    private int getHeightAtOffset(LevelSimulatedReader level, BlockPos pos, int radius, Direction direction) {
        BlockPos relativePos = pos.relative(direction, radius);
        return level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, relativePos).getY();
    }

}
