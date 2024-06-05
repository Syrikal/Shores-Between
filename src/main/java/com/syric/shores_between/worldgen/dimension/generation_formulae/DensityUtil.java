package com.syric.shores_between.worldgen.dimension.generation_formulae;

import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

import java.util.Arrays;

public class DensityUtil {

    public interface TwoDensityOperation {
        DensityFunction method(DensityFunction a, DensityFunction b);
    }

    //This function takes in a density function operation (e.g. 'max', 'min', 'add') and applies it to
    //all density functions listed.
    //e.g. applyAll(DensityFunctions::max, a, b, c, d) would find the max of those four.
    public static DensityFunction applyAll(TwoDensityOperation a, DensityFunction... functions) {
        return switch(functions.length) {
            case 0 -> DensityFunctions.zero();
            case 1 -> functions[0];
            case 2 -> a.method(functions[0], functions[1]);
            default -> a.method(functions[0], applyAll(a, Arrays.copyOfRange(functions, 1, functions.length)));
        };
    }

    //SmoothMax combines two values and adds a little bit more when they're similar, which fills in the seam a bit.
    public static DensityFunction smoothMax(DensityFunction a, DensityFunction b) {
        //Takes the max of five things
        return applyAll(DensityFunctions::max,
            //0.28(3a+b)
            DensityFunctions.mul(DensityFunctions.constant(0.28), DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(3), a), b)),
            //0.28(3b+a)
            DensityFunctions.mul(DensityFunctions.constant(0.28), DensityFunctions.add(DensityFunctions.mul(DensityFunctions.constant(3), b), a)),
            //0.58(a+b)
            DensityFunctions.mul(DensityFunctions.constant(0.58), DensityFunctions.add(a, b)),
            //a and b
            a,
            b
        );
    }

    //LessSmoothMax combines two values and adds a little bit more when they're similar, which fills in the seam a bit.
    public static DensityFunction lessSmoothMax(DensityFunction a, DensityFunction b) {
        //Takes the max of three things
        return applyAll(DensityFunctions::max,
                //0.58(a+b)
                DensityFunctions.mul(DensityFunctions.constant(0.58), DensityFunctions.add(a, b)),
                //a and b
                a,
                b
        );
    }

}
