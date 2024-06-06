//package com.syric.shores_between.worldgen.dimension.generation_formulae;
//
//import net.minecraft.core.HolderGetter;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.data.worldgen.BootstrapContext;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.levelgen.*;
//import net.minecraft.world.level.levelgen.placement.CaveSurface;
//import net.minecraft.world.level.levelgen.synth.BlendedNoise;
//import net.minecraft.world.level.levelgen.synth.NormalNoise;
//import quek.undergarden.registry.UGBiomes;
//import quek.undergarden.registry.UGBlocks;
//
//import java.util.List;
//
//import static com.syric.shores_between.registry.SBDimensions.BREACH_NOISE;
//
//public class UndergardenNoiseReference {
//
//        public static void bootstrapNoise(BootstrapContext<NoiseGeneratorSettings> context) {
//        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
//        HolderGetter<NormalNoise.NoiseParameters> noises = context.lookup(Registries.NOISE);
//        DensityFunction densityfunction = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_X);
//        DensityFunction densityfunction1 = NoiseRouterData.getFunction(functions, NoiseRouterData.SHIFT_Z);
//        context.register(BREACH_NOISE, new NoiseGeneratorSettings(
//                NoiseSettings.create(-64, 384, 1, 1),
//
//                Blocks.GRAVEL.defaultBlockState(),
//
//                Blocks.WATER.defaultBlockState(),
//
//
//
//                new NoiseRouter(
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.TEMPERATURE)),
//                        DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, noises.getOrThrow(Noises.VEGETATION)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.CONTINENTS),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.EROSION),
//                        DensityFunctions.rangeChoice(
//                                NoiseRouterData.getFunction(functions, NoiseRouterData.Y), 0.0, 32.0,
//                                DensityFunctions.constant(2.0),
//                                DensityFunctions.constant(-2.0)),
//                        NoiseRouterData.getFunction(functions, NoiseRouterData.RIDGES),
//                        DensityFunctions.zero(),
//                        DensityFunctions.mul(
//                                DensityFunctions.constant(0.64),
//                                DensityFunctions.interpolated(
//                                        DensityFunctions.blendDensity(
//                                                DensityFunctions.add(
//                                                        DensityFunctions.constant(2.5),
//                                                        DensityFunctions.mul(
//                                                                DensityFunctions.yClampedGradient(-8, 24, 0.0, 1.0),
//                                                                DensityFunctions.add(
//                                                                        DensityFunctions.constant(-2.5),
//                                                                        DensityFunctions.add(
//                                                                                DensityFunctions.constant(0.5),
//                                                                                DensityFunctions.mul(
//                                                                                        DensityFunctions.yClampedGradient(110, 128, 1.0, 0.0),
//                                                                                        DensityFunctions.add(
//                                                                                                DensityFunctions.constant(-0.5),
//                                                                                                BlendedNoise.createUnseeded(0.1, 0.3, 80.0, 60.0, 1.0)))))))))
//                        ).squeeze(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero(),
//                        DensityFunctions.zero()),
//
//
//
//                SurfaceRules.sequence(
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.verticalGradient("minecraft:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
//                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.not(
//                                        SurfaceRules.verticalGradient("minecraft:bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
//                                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.yBlockCheck(VerticalAnchor.belowTop(5), 0),
//                                SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.not(
//                                                SurfaceRules.yBlockCheck(VerticalAnchor.absolute(33), 0)),
//                                        SurfaceRules.state(UGBlocks.SEDIMENT.get().defaultBlockState()))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.BLOOD_MUSHROOM_BOG),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                        SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.SMOG_SPIRES),
//                                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                        SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.ASHEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.BARREN_ABYSS, UGBiomes.DEAD_SEA),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.NETHER_STATE_SELECTOR).key(), 0.0, 1.8),
//                                                SurfaceRules.state(UGBlocks.COARSE_DEEPSOIL.get().defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())),
//                                                SurfaceRules.state(UGBlocks.DEPTHROCK.get().defaultBlockState())))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST),
//                                SurfaceRules.ifTrue(
//                                        SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                        SurfaceRules.sequence(
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(noises.getOrThrow(Noises.POWDER_SNOW).key(), 0.45, 0.58),
//                                                        SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState())),
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR),
//                                                        SurfaceRules.state(UGBlocks.FROZEN_DEEPTURF_BLOCK.get().defaultBlockState()))))),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
//                                SurfaceRules.state(UGBlocks.DEEPTURF_BLOCK.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.stoneDepthCheck(0, true, 0, CaveSurface.FLOOR),
//                                SurfaceRules.state(UGBlocks.DEEPSOIL.get().defaultBlockState())),
//
//                        SurfaceRules.ifTrue(
//                                SurfaceRules.isBiome(UGBiomes.FROSTFIELDS, UGBiomes.ICY_SEA, UGBiomes.FROSTY_SMOGSTEM_FOREST),
//                                SurfaceRules.state(UGBlocks.SHIVERSTONE.get().defaultBlockState()))
//                ),
//
//
//
//                List.of(),
//                32,
//                false,
//                false,
//                false,
//                false));
//    }
//
//}
