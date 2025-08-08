package com.syric.shores_between.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.worldgen.dimension.BreachFluidProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Aquifer.NoiseBasedAquifer.class)
public class MixinNoiseBasedAquifer {

    @Shadow @Final private Aquifer.FluidPicker globalFluidPicker;

    @Shadow protected boolean shouldScheduleFluidUpdate;

    @WrapOperation(
            method = "computeSubstance",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0)
    )
    private boolean replaceLava(BlockState aquiferFluidStatus, Block lava, Operation<Boolean> isOperation, @Cancellable CallbackInfoReturnable<BlockState> cir) {
        if (aquiferFluidStatus.is(Blocks.PEARLESCENT_FROGLIGHT)) {
            this.shouldScheduleFluidUpdate = false;
            cir.setReturnValue(Blocks.PEARLESCENT_FROGLIGHT.defaultBlockState());
        }
        return isOperation.call(aquiferFluidStatus, lava);
    }

}
