package com.syric.shores_between.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import javax.annotation.Nullable;

public class BreachSkyRenderer {

    public void renderBreachSky(Matrix4f pProjectionMatrix, Matrix4f pFrustrumMatrix, float pPartialTick, Camera pCamera, boolean pIsFoggy, Runnable pSkyFogSetup,
                                ClientLevel level, Minecraft minecraft, @Nullable VertexBuffer skyBuffer, PoseStack posestack, @Nullable VertexBuffer starBuffer, @Nullable VertexBuffer darkBuffer) {

        //Sky coloring
        Vec3 skyColor = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), pPartialTick);
        float skyColorX = (float) skyColor.x;
        float skyColorY = (float) skyColor.y;
        float skyColorZ = (float) skyColor.z;

        //Possibly also sunrise/sunset?
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(skyColorX, skyColorY, skyColorZ, 1.0F);
        ShaderInstance shaderinstance = RenderSystem.getShader();
        skyBuffer.bind();
        skyBuffer.drawWithShader(posestack.last().pose(), pFrustrumMatrix, shaderinstance);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();

        //Sunrise/sunset coloring
        //Currently removed. Eventually, perhaps adapt it to render the sky brighter when looking at the sun's position?
//        float[] afloat = level.effects().getSunriseColor(level.getTimeOfDay(pPartialTick), pPartialTick);
//        if (afloat != null) {
//            RenderSystem.setShader(GameRenderer::getPositionColorShader);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            posestack.pushPose();
//            posestack.mulPose(Axis.XP.rotationDegrees(90.0F));
//            float f3 = Mth.sin(level.getSunAngle(pPartialTick)) < 0.0F ? 180.0F : 0.0F;
//            posestack.mulPose(Axis.ZP.rotationDegrees(f3));
//            posestack.mulPose(Axis.ZP.rotationDegrees(90.0F));
//            float f4 = afloat[0];
//            float f5 = afloat[1];
//            float f6 = afloat[2];
//            Matrix4f matrix4f = posestack.last().pose();
//            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
//            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
//            int i = 16; //No idea why this is here
//
//            for (int j = 0; j <= 16; j++) {
//                float f7 = (float) j * (float) (Math.PI * 2) / 16.0F;
//                float f8 = Mth.sin(f7);
//                float f9 = Mth.cos(f7);
//                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3])
//                        .color(afloat[0], afloat[1], afloat[2], 0.0F)
//                        .endVertex();
//            }
//
//            BufferUploader.drawWithShader(bufferbuilder.end());
//            posestack.popPose();
//        }

        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
        );
        posestack.pushPose();

        //Rain
        float f11 = 1.0F - level.getRainLevel(pPartialTick);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
        posestack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        posestack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(pPartialTick) * 360.0F));
        Matrix4f matrix4f1 = posestack.last().pose();

        //Sun
//        float f12 = 30.0F;
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0, SUN_LOCATION);
//        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
//        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
//        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
//        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
//        BufferUploader.drawWithShader(bufferbuilder.end());

        //Moon
//        f12 = 20.0F;
//        RenderSystem.setShaderTexture(0, MOON_LOCATION);
//        int k = level.getMoonPhase();
//        int l = k % 4;
//        int i1 = k / 4 % 2;
//        float f13 = (float) (l + 0) / 4.0F;
//        float f14 = (float) (i1 + 0) / 2.0F;
//        float f15 = (float) (l + 1) / 4.0F;
//        float f16 = (float) (i1 + 1) / 2.0F;
//        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
//        bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
//        bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
//        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
//        BufferUploader.drawWithShader(bufferbuilder.end());

        //Stars
        float f10 = level.getStarBrightness(pPartialTick) * f11;
        if (f10 > 0.0F) {
            RenderSystem.setShaderColor(f10, f10, f10, f10);
            FogRenderer.setupNoFog();
            starBuffer.bind();
            starBuffer.drawWithShader(posestack.last().pose(), pFrustrumMatrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
            pSkyFogSetup.run();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        posestack.popPose();


        //Something about being underground/underwater
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
        double d0 = minecraft.player.getEyePosition(pPartialTick).y - level.getLevelData().getHorizonHeight(level);
        if (d0 < 0.0) {
            posestack.pushPose();
            posestack.translate(0.0F, 12.0F, 0.0F);
            darkBuffer.bind();
            darkBuffer.drawWithShader(posestack.last().pose(), pFrustrumMatrix, shaderinstance);
            VertexBuffer.unbind();
            posestack.popPose();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
    }
}
