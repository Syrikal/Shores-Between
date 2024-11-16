package com.syric.shores_between.event;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.rendering.SBChestRenderer;
import com.syric.shores_between.registry.SBBlockEntities;
import com.syric.shores_between.registry.SBBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

@EventBusSubscriber(modid = ShoresBetween.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShoresBetweenClientEvents {

    @SubscribeEvent
    public static void registerBERs(EntityRenderersEvent.RegisterRenderers renderersEvent) {
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_SIGN.get(), SignRenderer::new);
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_HANGING_SIGN.get(), HangingSignRenderer::new);
        renderersEvent.registerBlockEntityRenderer(SBBlockEntities.SHORES_BETWEEN_CHEST.get(), SBChestRenderer::new);
    }


    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, tintGetter, pos, tint) ->
                    tintGetter != null && pos != null ?
                            getMistwoodLeavesColor(tintGetter, pos)
                            : FastColor.ARGB32.color(255, 42, 79, 52),
                SBBlocks.MISTWOOD_LEAVES.get());
        event.register(
                (state, tintGetter, pos, tint) ->
                        tintGetter != null && pos != null ?
                                getMistwoodMossColor(tintGetter, pos)
                                : FastColor.ARGB32.color(255, 75, 105, 83),
                SBBlocks.MISTWOOD_MOSS_BLOCK.get());
        event.register(
                (state, tintGetter, pos, tint) ->
                        tintGetter != null && pos != null ?
                                getMistwoodMossColor(tintGetter, pos)
                                : FastColor.ARGB32.color(255, 75, 105, 83),
                SBBlocks.MISTWOOD_MOSS_CARPET.get());
        event.register(
                (state, tintGetter, pos, tint) ->
                        tintGetter != null && pos != null ?
                                BiomeColors.getAverageGrassColor(tintGetter, pos)
                                : FastColor.ARGB32.color(255, 75, 105, 83),
                SBBlocks.OVERGROWN_SHALE.get());
        event.register(
                (state, tintGetter, pos, tint) ->
                        tintGetter != null && pos != null ?
                                BiomeColors.getAverageGrassColor(tintGetter, pos)
                                : FastColor.ARGB32.color(255, 75, 105, 83),
                SBBlocks.OVERGROWN_SHALE_SLAB.get());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        BlockColors bColors = event.getBlockColors();
        event.register((stack, tint) -> bColors.getColor(((BlockItem)stack.getItem()).getBlock().defaultBlockState(), null, null, 0), SBBlocks.MISTWOOD_LEAVES.get());
        event.register((stack, tint) -> bColors.getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, 0), SBBlocks.MISTWOOD_MOSS_BLOCK.get());
        event.register((stack, tint) -> bColors.getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, 0), SBBlocks.MISTWOOD_MOSS_CARPET.get());
        event.register((stack, tint) -> bColors.getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, 0), SBBlocks.OVERGROWN_SHALE.get());
        event.register((stack, tint) -> bColors.getColor(((BlockItem) stack.getItem()).getBlock().defaultBlockState(), null, null, 0), SBBlocks.OVERGROWN_SHALE_SLAB.get());
    }

    private static int getMistwoodLeavesColor(BlockAndTintGetter tintGetter, BlockPos pos) {
        Color fixedColor = new Color(42, 79, 52);
//        Color fixedColor = new Color(49, 11, 219);
        Color biomeColor = new Color(BiomeColors.getAverageFoliageColor(tintGetter, pos));

        //Fixed color per biome color. 1 is even, 2 is double fixed (less variable), 0.5 is double biome (more variable).
        double ratio = 0.5;

        int r = (int) ((fixedColor.getRed() * ratio + biomeColor.getRed()) / (1 + ratio));
        int g = (int) ((fixedColor.getGreen() * ratio + biomeColor.getGreen()) / (1 + ratio));
        int b = (int) ((fixedColor.getBlue() * ratio + biomeColor.getBlue()) / (1 + ratio));

        Color output = new Color(r, g, b);

        return output.getRGB();

    }

    private static int getMistwoodMossColor(BlockAndTintGetter tintGetter, BlockPos pos) {
        Color fixedColor = new Color(75, 105, 83);
        Color biomeGrassColor = new Color(BiomeColors.getAverageGrassColor(tintGetter, pos));
        Color biomeLeavesColor = new Color(BiomeColors.getAverageFoliageColor(tintGetter, pos));

        double fixedPortion = 0.4;
        double grassPortion = 0.6;
        double leavesPortion = 0;

        int r = (int) (fixedColor.getRed() * fixedPortion + biomeGrassColor.getRed() * grassPortion + biomeLeavesColor.getBlue() * leavesPortion);
        int g = (int) (fixedColor.getGreen() * fixedPortion + biomeGrassColor.getGreen() * grassPortion + biomeLeavesColor.getBlue() * leavesPortion);
        int b = (int) (fixedColor.getBlue() * fixedPortion + biomeGrassColor.getBlue() * grassPortion + biomeLeavesColor.getBlue() * leavesPortion);

        Color output = new Color(r, g, b);

        return output.getRGB();

    }

}
