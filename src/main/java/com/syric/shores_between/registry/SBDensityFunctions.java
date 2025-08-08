package com.syric.shores_between.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.syric.shores_between.ShoresBetween;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.syric.shores_between.ShoresBetween.MODID;

public class SBDensityFunctions {

    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, MODID);

    public static void register(IEventBus bus) {
        DENSITY_FUNCTION_TYPES.register(bus);
    }

    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> SINE = DENSITY_FUNCTION_TYPES.register("sine_function", () -> Sine.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> COSINE = DENSITY_FUNCTION_TYPES.register("cosine_function", () -> Cosine.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> GET_X = DENSITY_FUNCTION_TYPES.register("get_x_function", () -> GetX.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> GET_Y = DENSITY_FUNCTION_TYPES.register("get_y_function", () -> GetY.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> GET_Z = DENSITY_FUNCTION_TYPES.register("get_z_function", () -> GetZ.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> DIAGONAL_STRETCH = DENSITY_FUNCTION_TYPES.register("diagonal_stretch", () -> DiagonalStretch.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> SHIFTED_NOISE = DENSITY_FUNCTION_TYPES.register("sb_shifted_noise", () -> ShiftedNoise.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> SAMPLE_AT_Y = DENSITY_FUNCTION_TYPES.register("sample_at_y", () -> SampleAtGivenY.DATA_CODEC);
    public static final DeferredHolder<MapCodec<? extends DensityFunction>, MapCodec<? extends DensityFunction>> DIVIDE = DENSITY_FUNCTION_TYPES.register("divide", () -> Divide.DATA_CODEC);

    public static ResourceKey<MapCodec<? extends DensityFunction>> registerKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION_TYPE, new ResourceLocation(ShoresBetween.MODID, name));
    }

    public record Sine(DensityFunction input, double period) implements DensityFunction {

        private static final MapCodec<Sine> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(x -> x.input),
                                NOISE_VALUE_CODEC.fieldOf("period").forGetter(x -> x.period)
                        )
                        .apply(instance, Sine::new)
        );
        public static final KeyDispatchDataCodec<Sine> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return StrictMath.sin(input.compute(context) * 2 * Math.PI / period);
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new Sine(this.input.mapAll(visitor), this.period));
        }

        @Override
        public double minValue() {
            return -1;
        }

        @Override
        public double maxValue() {
            return 1;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record Cosine(DensityFunction input, double period) implements DensityFunction {

        private static final MapCodec<Cosine> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(x -> x.input),
                                NOISE_VALUE_CODEC.fieldOf("period").forGetter(x -> x.period)
                        )
                        .apply(instance, Cosine::new)
        );
        public static final KeyDispatchDataCodec<Cosine> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return StrictMath.cos(input.compute(context) * 2 * Math.PI / period);
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new Cosine(this.input.mapAll(visitor), this.period));
        }

        @Override
        public double minValue() {
            return -1;
        }

        @Override
        public double maxValue() {
            return 1;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record GetX() implements DensityFunction {

        private static final MapCodec<GetX> DATA_CODEC = MapCodec.unit(new GetX());
        public static final KeyDispatchDataCodec<GetX> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return context.blockX();
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new GetX());
        }

        @Override
        public double minValue() {
            return -30000000;
        }

        @Override
        public double maxValue() {
            return 30000000;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record GetY() implements DensityFunction {

        private static final MapCodec<GetY> DATA_CODEC = MapCodec.unit(new GetY());
        public static final KeyDispatchDataCodec<GetY> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return context.blockY();
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new GetY());
        }

        @Override
        public double minValue() {
            return -30000000;
        }

        @Override
        public double maxValue() {
            return 30000000;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record GetZ() implements DensityFunction {

        private static final MapCodec<GetZ> DATA_CODEC = MapCodec.unit(new GetZ());
        public static final KeyDispatchDataCodec<GetZ> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return context.blockZ();
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new GetZ());
        }

        @Override
        public double minValue() {
            return -30000000;
        }

        @Override
        public double maxValue() {
            return 30000000;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record DiagonalStretch(DensityFunction input, double axis_x, double axis_z, double ratio) implements DensityFunction {

        private static final MapCodec<DiagonalStretch> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(x -> x.input),
                                NOISE_VALUE_CODEC.fieldOf("axis_x").forGetter(x -> x.axis_x),
                                NOISE_VALUE_CODEC.fieldOf("axis_z").forGetter(x -> x.axis_z),
                                NOISE_VALUE_CODEC.fieldOf("ratio").forGetter(x -> x.ratio)
                        )
                        .apply(instance, DiagonalStretch::new)
        );
        public static final KeyDispatchDataCodec<DiagonalStretch> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            double multiplier = (1 - (1 / ratio)) * (context.blockX() * axis_x + context.blockZ() * axis_z) / (axis_x * axis_x + axis_z * axis_z);
            double modified_x = multiplier * axis_x + (1 / ratio) * context.blockX();
            double modified_z = multiplier * axis_z + (1 / ratio) * context.blockZ();
            DensityFunction.FunctionContext new_context = new SinglePointContext((int) modified_x, context.blockY(), (int) modified_z);
            return input.compute(new_context);
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new DiagonalStretch(this.input.mapAll(visitor), this.axis_x, this.axis_z, this.ratio));
        }

        @Override
        public double minValue() {
            return this.input.minValue();
        }

        @Override
        public double maxValue() {
            return this.input.maxValue();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record ShiftedNoise(
            DensityFunction shiftX, DensityFunction shiftY, DensityFunction shiftZ, double xzScale, double yScale, DensityFunction.NoiseHolder noise
    ) implements DensityFunction {
        private static final MapCodec<ShiftedNoise> DATA_CODEC = RecordCodecBuilder.mapCodec(
                p_208943_ -> p_208943_.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_x").forGetter(ShiftedNoise::shiftX),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_y").forGetter(ShiftedNoise::shiftY),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_z").forGetter(ShiftedNoise::shiftZ),
                                Codec.DOUBLE.fieldOf("xz_scale").forGetter(ShiftedNoise::xzScale),
                                Codec.DOUBLE.fieldOf("y_scale").forGetter(ShiftedNoise::yScale),
                                DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(ShiftedNoise::noise)
                        )
                        .apply(p_208943_, ShiftedNoise::new)
        );
        public static final KeyDispatchDataCodec<ShiftedNoise> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            double d0 = (double) context.blockX() * this.xzScale + this.shiftX.compute(context);
            double d1 = (double) context.blockY() * this.yScale + this.shiftY.compute(context);
            double d2 = (double) context.blockZ() * this.xzScale + this.shiftZ.compute(context);
            return this.noise.getValue(d0, d1, d2);
        }

        @Override
        public void fillArray(double[] array, DensityFunction.ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(DensityFunction.Visitor visitor) {
            return visitor.apply(
                    new ShiftedNoise(
                            this.shiftX.mapAll(visitor),
                            this.shiftY.mapAll(visitor),
                            this.shiftZ.mapAll(visitor),
                            this.xzScale,
                            this.yScale,
                            visitor.visitNoise(this.noise)
                    )
            );
        }

        @Override
        public double minValue() {
            return -this.maxValue();
        }

        @Override
        public double maxValue() {
            return this.noise.maxValue();
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record SampleAtGivenY(DensityFunction input, DensityFunction y) implements DensityFunction {

        private static final MapCodec<SampleAtGivenY> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(x -> x.input),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("y").forGetter(x -> x.y)
                        )
                        .apply(instance, SampleAtGivenY::new)
        );
        public static final KeyDispatchDataCodec<SampleAtGivenY> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            DensityFunction.FunctionContext new_context = new SinglePointContext(context.blockX(), (int) y.compute(context), context.blockZ());
            return input.compute(new_context);
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new SampleAtGivenY(this.input.mapAll(visitor), this.y.mapAll(visitor)));
        }

        @Override
        public double minValue() {
            return -1;
        }

        @Override
        public double maxValue() {
            return 1;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public record Divide(DensityFunction numerator, DensityFunction denominator) implements DensityFunction {

        private static final MapCodec<Divide> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("numerator").forGetter(x -> x.numerator),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("denominator").forGetter(x -> x.denominator)
                        )
                        .apply(instance, Divide::new)
        );
        public static final KeyDispatchDataCodec<Divide> CODEC = makeCodec(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            double d = denominator.compute(context);
            if (d == 0) {
                return numerator.compute(context) > 0 ? 100000 : -100000;
            } else {
                return Math.clamp(numerator.compute(context) / denominator.compute(context), -100000, 100000);
            }
        }

        @Override
        public void fillArray(double[] array, ContextProvider contextProvider) {
            contextProvider.fillAllDirectly(array, this);
        }

        @Override
        public DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new Divide(this.numerator.mapAll(visitor), this.denominator.mapAll(visitor)));
        }

        @Override
        public double minValue() {
            return -100000;
        }

        @Override
        public double maxValue() {
            return 100000;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public static DensityFunction shiftedNoise(
            int shiftX, int shiftY, int shiftZ, double xzScale, double yScale, Holder<NormalNoise.NoiseParameters> noiseData
    ) {
        return new ShiftedNoise(DensityFunctions.constant(shiftX), DensityFunctions.constant(shiftY), DensityFunctions.constant(shiftZ), xzScale, yScale, new DensityFunction.NoiseHolder(noiseData));
    }

    static <O> KeyDispatchDataCodec<O> makeCodec(MapCodec<O> mapCodec) {
        return KeyDispatchDataCodec.of(mapCodec);
    }

    static final Codec<Double> NOISE_VALUE_CODEC = Codec.doubleRange(-1000000.0, 1000000.0);

}
