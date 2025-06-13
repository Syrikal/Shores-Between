package com.syric.shores_between.entity.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.syric.shores_between.ShoresBetween;
import com.syric.shores_between.entity.client.animations.MosasaurusAnimations;
import com.syric.shores_between.entity.beached_corpses.MosasaurusEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MosasaurusModel<T extends MosasaurusEntity> extends HierarchicalModel<T> {


    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ShoresBetween.MODID, "mosasaurus"), "main");
    private final ModelPart root;
    private final ModelPart MainBody;
    private final ModelPart Neck;
    private final ModelPart Head;
    private final ModelPart jaw;
    private final ModelPart Tail;
    private final ModelPart LeftCaudal;
    private final ModelPart RightCaudal;
    private final ModelPart Tail2;
    private final ModelPart Tail3;
    private final ModelPart RightPectoral;
    private final ModelPart LeftPectoral;

    public MosasaurusModel(ModelPart root) {
        this.root = root;
        this.MainBody = root.getChild("MainBody");
        this.Neck = this.MainBody.getChild("Neck");
        this.Head = this.Neck.getChild("Head");
        this.jaw = this.Head.getChild("jaw");
        this.Tail = this.MainBody.getChild("Tail");
        this.LeftCaudal = this.Tail.getChild("LeftCaudal");
        this.RightCaudal = this.Tail.getChild("RightCaudal");
        this.Tail2 = this.Tail.getChild("Tail2");
        this.Tail3 = this.Tail2.getChild("Tail3");
        this.RightPectoral = this.MainBody.getChild("RightPectoral");
        this.LeftPectoral = this.MainBody.getChild("LeftPectoral");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition MainBody = partdefinition.addOrReplaceChild("MainBody", CubeListBuilder.create().texOffs(22, 10).addBox(-14.0F, -15.0F, -25.0F, 28.0F, 30.0F, 50.0F, new CubeDeformation(0.0F))
                .texOffs(14, 93).addBox(-16.0F, -17.0F, -27.0F, 32.0F, 34.0F, 54.0F, new CubeDeformation(0.0F))
                .texOffs(374, 72).addBox(-12.0F, -12.9F, -20.0F, 24.0F, 26.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(394, 129).addBox(-8.0F, -13.0F, 6.0F, 16.0F, 22.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(389, 170).addBox(-1.5F, -13.0F, -17.0F, 3.0F, 3.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

        PartDefinition Neck = MainBody.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(241, 53).addBox(-8.0F, -1.0F, -19.0F, 16.0F, 20.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -17.0F));

        PartDefinition neckspine_r1 = Neck.addOrReplaceChild("neckspine_r1", CubeListBuilder.create().texOffs(401, 45).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -18.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(196, 10).addBox(-4.0F, -2.0F, -27.0F, 8.0F, 6.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -18.0F));

        PartDefinition upper_left_teeth_r1 = Head.addOrReplaceChild("upper_left_teeth_r1", CubeListBuilder.create().texOffs(219, 77).addBox(1.3F, -3.5F, -13.0F, 0.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.5F, -13.0F, 0.0F, 0.1047F, 0.0F));

        PartDefinition upper_right_teeth_r1 = Head.addOrReplaceChild("upper_right_teeth_r1", CubeListBuilder.create().texOffs(274, 77).addBox(-1.3F, -3.5F, -13.0F, 0.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.5F, -13.0F, 0.0F, -0.1047F, 0.0F));

        PartDefinition jaw = Head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(275, 11).addBox(-4.0F, -0.5F, -25.0F, 8.0F, 4.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, -2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition lower_left_teeth_r1 = jaw.addOrReplaceChild("lower_left_teeth_r1", CubeListBuilder.create().texOffs(219, 83).addBox(1.0F, -4.5F, -13.0F, 0.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, -11.0F, 0.0F, 0.1047F, 0.0F));

        PartDefinition lower_right_teeth_r1 = jaw.addOrReplaceChild("lower_right_teeth_r1", CubeListBuilder.create().texOffs(274, 83).addBox(-1.0F, -4.5F, -13.0F, 0.0F, 3.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.0F, -11.0F, 0.0F, -0.1047F, 0.0F));

        PartDefinition leftjaw_r1 = jaw.addOrReplaceChild("leftjaw_r1", CubeListBuilder.create().texOffs(356, 29).addBox(-3.0F, -2.5F, -14.0F, 2.0F, 4.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.0F, -11.0F, 0.0F, 0.1047F, 0.0F));

        PartDefinition rightjaw_r1 = jaw.addOrReplaceChild("rightjaw_r1", CubeListBuilder.create().texOffs(432, 29).addBox(1.0F, -2.5F, -14.0F, 2.0F, 4.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.0F, -11.0F, 0.0F, -0.1047F, 0.0F));

        PartDefinition Tail = MainBody.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(29, 185).addBox(-9.0F, -1.0F, 2.0F, 18.0F, 18.0F, 53.0F, new CubeDeformation(0.0F))
                .texOffs(18, 260).addBox(-12.0F, -3.0F, 0.0F, 24.0F, 24.0F, 58.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 18.0F));

        PartDefinition tail1spine_r1 = Tail.addOrReplaceChild("tail1spine_r1", CubeListBuilder.create().texOffs(364, 210).addBox(-1.0F, -3.8F, -34.1F, 3.0F, 3.0F, 57.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 3.5F, 31.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition LeftCaudal = Tail.addOrReplaceChild("LeftCaudal", CubeListBuilder.create(), PartPose.offset(7.0F, 7.0F, 41.0F));

        PartDefinition left_caudal_fin_r1 = LeftCaudal.addOrReplaceChild("left_caudal_fin_r1", CubeListBuilder.create().texOffs(207, 166).addBox(-2.0F, -1.0F, -4.0F, 24.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1972F, -0.3089F, 0.5184F));

        PartDefinition RightCaudal = Tail.addOrReplaceChild("RightCaudal", CubeListBuilder.create(), PartPose.offset(-7.0F, 7.0F, 41.0F));

        PartDefinition right_caudal_fin_r1 = RightCaudal.addOrReplaceChild("right_caudal_fin_r1", CubeListBuilder.create().texOffs(281, 166).addBox(-21.0F, -1.0F, -4.0F, 24.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1972F, 0.3089F, -0.5184F));

        PartDefinition Tail2 = Tail.addOrReplaceChild("Tail2", CubeListBuilder.create().texOffs(48, 352).addBox(-5.0F, -2.0F, -2.0F, 10.0F, 12.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 54.0F));

        PartDefinition tail_2_vertebral_processes_r1 = Tail2.addOrReplaceChild("tail_2_vertebral_processes_r1", CubeListBuilder.create().texOffs(331, 248).addBox(0.0F, -5.0F, -21.0F, 0.0F, 10.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 22.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition tail2spine_r1 = Tail2.addOrReplaceChild("tail2spine_r1", CubeListBuilder.create().texOffs(384, 274).addBox(-1.5F, -5.4F, 1.0F, 3.0F, 3.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -2.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition Tail3 = Tail2.addOrReplaceChild("Tail3", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 36.0F));

        PartDefinition tail_fin_vertebral_processes_r1 = Tail3.addOrReplaceChild("tail_fin_vertebral_processes_r1", CubeListBuilder.create().texOffs(312, 282).addBox(0.0F, -12.0F, 1.0F, 0.0F, 12.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -5.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition fin_spine_r1 = Tail3.addOrReplaceChild("fin_spine_r1", CubeListBuilder.create().texOffs(383, 319).addBox(-1.5F, -3.0F, -1.0F, 3.0F, 3.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition upper_tail_fin_r1 = Tail3.addOrReplaceChild("upper_tail_fin_r1", CubeListBuilder.create().texOffs(114, 415).addBox(-2.5F, -7.0F, -11.5F, 3.0F, 14.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.917F, 12.5081F, 0.2618F, 0.0F, 0.0F));

        PartDefinition lower_tail_fin_r1 = Tail3.addOrReplaceChild("lower_tail_fin_r1", CubeListBuilder.create().texOffs(46, 414).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 12.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition RightPectoral = MainBody.addOrReplaceChild("RightPectoral", CubeListBuilder.create(), PartPose.offset(-13.0F, 7.0F, -17.0F));

        PartDefinition right_pectoral_skeleton_r1 = RightPectoral.addOrReplaceChild("right_pectoral_skeleton_r1", CubeListBuilder.create().texOffs(283, 123).mirror().addBox(-20.0F, -1.0F, -4.0F, 22.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(283, 145).mirror().addBox(-20.0F, -2.0F, -4.0F, 22.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0541F, 0.3892F, -0.4904F));

        PartDefinition LeftPectoral = MainBody.addOrReplaceChild("LeftPectoral", CubeListBuilder.create(), PartPose.offset(13.0F, 7.0F, -17.0F));

        PartDefinition left_pectoral_skeleton_r1 = LeftPectoral.addOrReplaceChild("left_pectoral_skeleton_r1", CubeListBuilder.create().texOffs(209, 123).addBox(-2.0F, -1.0F, -4.0F, 22.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(209, 145).addBox(-2.0F, -2.0F, -4.0F, 22.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0541F, -0.3892F, 0.4904F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(MosasaurusEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.belly, MosasaurusAnimations.ANIM_MOSA_BELLY, ageInTicks, 2f);
        this.animate(entity.side, MosasaurusAnimations.ANIM_MOSA_SIDE, ageInTicks, 2f);
        this.animate(entity.back, MosasaurusAnimations.ANIM_MOSA_BACK, ageInTicks, 2f);
        this.animate(entity.bellysink, MosasaurusAnimations.ANIM_MOSA_SINK, ageInTicks, 1f);
        this.animate(entity.sidesink, MosasaurusAnimations.ANIM_MOSA_SINK_2, ageInTicks, 1f);
        this.animate(entity.backsink, MosasaurusAnimations.ANIM_MOSA_SINK, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        MainBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return root;
    }
}
