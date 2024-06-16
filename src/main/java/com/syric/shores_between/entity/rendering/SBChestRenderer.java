package com.syric.shores_between.entity.rendering;

import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.block.SBChestBlock;
import com.syric.shores_between.entity.SBChestBlockEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.ChestType;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.client.renderer.Sheets.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SBChestRenderer extends ChestRenderer<SBChestBlockEntity> {
    public static final Material DRIFTWOOD_CHEST_LOCATION = chestMaterial("driftwood");
    public static final Material DRIFTWOOD_CHEST_LOCATION_LEFT = chestMaterial("driftwood_left");
    public static final Material DRIFTWOOD_CHEST_LOCATION_RIGHT = chestMaterial("driftwood_right");
    public static final Material MISTWOOD_CHEST_LOCATION = chestMaterial("mistwood");
    public static final Material MISTWOOD_CHEST_LOCATION_LEFT = chestMaterial("mistwood_left");
    public static final Material MISTWOOD_CHEST_LOCATION_RIGHT = chestMaterial("mistwood_right");
    public static final Material ANCIENT_CHEST_LOCATION = chestMaterial("ancient");
    public static final Material ANCIENT_CHEST_LOCATION_LEFT = chestMaterial("ancient_left");
    public static final Material ANCIENT_CHEST_LOCATION_RIGHT = chestMaterial("ancient_right");
    
    public SBChestRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    protected Material getMaterial(SBChestBlockEntity blockEntity, ChestType chestType) {
        String chestMaterial = ((SBChestBlock) blockEntity.getBlockState().getBlock()).material;
        return switch (chestMaterial) {
            case "driftwood" -> chooseMaterial(chestType, DRIFTWOOD_CHEST_LOCATION, DRIFTWOOD_CHEST_LOCATION_LEFT, DRIFTWOOD_CHEST_LOCATION_RIGHT);
            case "mistwood" -> chooseMaterial(chestType, MISTWOOD_CHEST_LOCATION, MISTWOOD_CHEST_LOCATION_LEFT, MISTWOOD_CHEST_LOCATION_RIGHT);
            case "ancient" -> chooseMaterial(chestType, ANCIENT_CHEST_LOCATION, ANCIENT_CHEST_LOCATION_LEFT, ANCIENT_CHEST_LOCATION_RIGHT);
            default -> chooseMaterial(chestType, CHEST_LOCATION, CHEST_LOCATION_LEFT, CHEST_LOCATION_RIGHT);
        };
    }

    private static Material chooseMaterial(ChestType pChestType, Material pDoubleMaterial, Material pLeftMaterial, Material pRightMaterial) {
        return switch (pChestType) {
            case LEFT -> pLeftMaterial;
            case RIGHT -> pRightMaterial;
            default -> pDoubleMaterial;
        };
    }

    private static Material chestMaterial(String pChestName) {
        return new Material(CHEST_SHEET, new ResourceLocation(ShoresBetween.MODID, "entity/chest/" + pChestName));
    }
    
}
