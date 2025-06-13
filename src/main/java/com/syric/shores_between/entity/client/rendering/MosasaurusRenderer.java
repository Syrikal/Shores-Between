package com.syric.shores_between.entity.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.client.models.MosasaurusModel;
import com.syric.shores_between.entity.beached_corpses.MosasaurusEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MosasaurusRenderer extends MobRenderer<MosasaurusEntity, MosasaurusModel<MosasaurusEntity>> {
    public MosasaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new MosasaurusModel<>(context.bakeLayer(MosasaurusModel.LAYER_LOCATION)), 0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(MosasaurusEntity entity) {
        switch (entity.getState()) {
            case 1:
                return new ResourceLocation(ShoresBetween.MODID, "textures/entity/beached_corpse/mosasaurus/bloated.png");
            case 2:
                return new ResourceLocation(ShoresBetween.MODID, "textures/entity/beached_corpse/mosasaurus/skeleton.png");
            default:
                return new ResourceLocation(ShoresBetween.MODID, "textures/entity/beached_corpse/mosasaurus/corpse.png");
        }
    }

    @Override
    public void render(MosasaurusEntity p_entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.scale(1F, 1F, 1F);
        super.render(p_entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}