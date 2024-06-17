package com.syric.shores_between.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SBFoodItem extends Item {
    private final SoundEvent sound;
    private final UseAnim animation;
    private final Integer useTicks;


    public SBFoodItem(Properties pProperties, @Nullable SoundEvent sound, @Nullable UseAnim anim, @Nullable Integer useTicks) {
        super(pProperties);
        this.sound = sound;
        this.animation = anim;
        this.useTicks = useTicks;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return useTicks == null ? super.getUseDuration(pStack) : useTicks;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return animation == null ? super.getUseAnimation(pStack) : animation;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return sound == null ? super.getDrinkingSound() : sound;
    }

    @Override
    public SoundEvent getEatingSound() {
        return sound == null ? super.getEatingSound() : sound;
    }
}
