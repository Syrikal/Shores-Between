package com.syric.shores_between.entity.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class MosasaurusAnimations {

    public static final AnimationDefinition ANIM_MOSA_SIDE = AnimationDefinition.Builder.withLength(0.0F)
            .addAnimation("MainBody", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -90.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.7538F, 9.8466F, -10.1511F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.982F, 12.3702F, -7.672F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.221F, 7.8599F, -0.8307F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.6671F, -3.9778F, 7.7736F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.7448F, 0.8735F, 4.933F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-5.0991F, -0.2253F, 2.2899F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 35.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(23.8864F, 15.5839F, -25.9629F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(1.6575F, -12.3914F, 44.8201F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 15.0F, -45.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition ANIM_MOSA_BACK = AnimationDefinition.Builder.withLength(0.0F)
            .addAnimation("MainBody", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -175.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.3774F, 1.936F, 7.2472F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, -12.5F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-1.8617F, 8.7943F, -30.1724F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.4502F, 13.6764F, -29.5784F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-4.9574F, -0.6518F, -82.4718F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-41.0271F, -16.5897F, 97.2089F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0854F, -0.6066F, -57.7665F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(12.0868F, -3.2113F, 79.6599F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

    public static final AnimationDefinition ANIM_MOSA_BELLY = AnimationDefinition.Builder.withLength(0.0F)
            .addAnimation("MainBody", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Neck", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(24.3372F, -4.6742F, 14.2955F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("jaw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5476F, 5.0378F, -0.4357F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-15.746F, 8.9401F, -6.7329F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail2", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 17.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Tail3", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(16.3636F, 18.9164F, -12.4154F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.2046F, 12.3071F, -10.2377F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightPectoral", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(-6.9471F, 12.7124F, 17.8293F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("LeftCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(6.1622F, -14.9416F, 5.1754F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("RightCaudal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(3.1339F, 17.2258F, 10.4748F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();

}
