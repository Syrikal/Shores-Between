package com.syric.shores_between.mixin;

import com.google.common.base.Supplier;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.registry.SBDimensions;
import com.syric.shores_between.worldgen.dimension.BreachFluidProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * This code was written by Apollo, the dev of Tectonic. Thanks for the help!
 * */
@Mixin(value = NoiseBasedChunkGenerator.class, priority = 1500)
public class MixinNoiseBasedChunkGenerator {
	@WrapOperation(
		method = "<init>",
		at = @At(
			value = "INVOKE",
			target = "Lcom/google/common/base/Suppliers;memoize(Lcom/google/common/base/Supplier;)Lcom/google/common/base/Supplier;"
		)
	)
	private Supplier<Aquifer.FluidPicker> init(Supplier<Aquifer.FluidPicker> supplier, Operation<Supplier<Aquifer.FluidPicker>> operation, BiomeSource biomeSource, Holder<NoiseGeneratorSettings> holder) {
//		ShoresBetween.LOGGER.debug("Running the mixin!");
		if (holder.is(SBDimensions.BREACH_NOISE)) {
			return operation.call((Supplier<Aquifer.FluidPicker>) () -> BreachFluidProvider.breachFluidPicker(holder.value()));
		} else {
			return operation.call(supplier);
		}
	}
}