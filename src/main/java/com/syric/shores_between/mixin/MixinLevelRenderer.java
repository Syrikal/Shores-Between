package com.syric.shores_between.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {

    //if in breach, redirect renderSky() to BreachSkyRenderer.renderSky()

}
