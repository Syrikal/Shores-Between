package com.syric.shores_between.util;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

public class DirectionUtil {

    public static Direction randomHorizontalDirection(RandomSource source) {
        Direction output = source.nextBoolean() ? Direction.NORTH : Direction.WEST;
        return source.nextBoolean() ? output : output.getOpposite();
    }

    public static Direction.Axis randomHorizontalAxis(RandomSource source) {
        return source.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
    }

    public static Direction[] HORIZONTAL_DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

}
