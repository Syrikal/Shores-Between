package com.syric.shores_between.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class SBFoods {

    public static final FoodProperties CRAB = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 0), 0.2F)
            .build();
    public static final FoodProperties COOKED_CRAB = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();
    public static final FoodProperties LOBSTER = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 0), 0.2F)
            .build();
    public static final FoodProperties COOKED_LOBSTER = new FoodProperties.Builder().nutrition(10).saturationModifier(0.8F).build();
    public static final FoodProperties ODD_FISH = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 100, 0), 0.2F)
            .build();
    public static final FoodProperties SEAGULL_EGG = new FoodProperties.Builder().nutrition(4).saturationModifier(0.8F).fast().build();
    public static final FoodProperties OYSTER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2F).fast()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0), 0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60, 0), 0.1F)
            .build();
    public static final FoodProperties COOKED_OYSTER = new FoodProperties.Builder().nutrition(5).saturationModifier(0.4F).fast().build();
    public static final FoodProperties BARNACLE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties WRITHING_TENDRILS = new FoodProperties.Builder().nutrition(2).saturationModifier(4.0F).fast()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 2), 0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 300, 0), 0.3F)
            .build();
    public static final FoodProperties ROTTEN_FISH = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 0), 0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 150, 0), 0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300, 1), 1.0F)
            .build();
    public static final FoodProperties STRANGE_MEAT = new FoodProperties.Builder().nutrition(4).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.6F)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 150, 0), 0.1F)
            .build();


}
