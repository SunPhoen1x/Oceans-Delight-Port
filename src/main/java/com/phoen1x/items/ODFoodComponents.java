package com.phoen1x.items;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public class ODFoodComponents {
    public static final int BRIEF_DURATION = 600;    // 30 seconds

    public static final FoodComponent TENTACLES = new FoodComponent.Builder()
            .nutrition(3)
            .saturationModifier(0.3f)
            .build();

    public static final ConsumableComponent TENTACLES_CONSUMABLE = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, BRIEF_DURATION, 0), 1.0f))
            .build();

    public static final FoodComponent CUT_TENTACLES = new FoodComponent.Builder()
            .nutrition(1)
            .saturationModifier(0.1f)
            .build();

    public static final FoodComponent SQUID_RINGS = new FoodComponent.Builder()
            .nutrition(5)
            .saturationModifier(0.5f)
            .build();

    public static final FoodComponent TENTACLE_ON_A_STICK = new FoodComponent.Builder()
            .nutrition(3)
            .saturationModifier(0.3f)
            .build();

    public static final FoodComponent BAKED_TENTACLE_ON_A_STICK = new FoodComponent.Builder()
            .nutrition(6)
            .saturationModifier(0.6f)
            .build();

    public static final FoodComponent GUARDIAN_TAIL = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.2f)
            .build();

    public static final FoodComponent COOKED_GUARDIAN_TAIL = new FoodComponent.Builder()
            .nutrition(6)
            .saturationModifier(0.4f)
            .build();

    public static final FoodComponent BOWL_OF_GUARDIAN_SOUP = new FoodComponent.Builder()
            .nutrition(15)
            .saturationModifier(0.8f)
            .build();

    public static final FoodComponent ELDER_GUARDIAN_SLAB = new FoodComponent.Builder()
            .nutrition(9)
            .saturationModifier(0.4f)
            .build();

    public static final FoodComponent ELDER_GUARDIAN_SLICE = new FoodComponent.Builder()
            .nutrition(1)
            .saturationModifier(0.1f)
            .build();

    public static final ConsumableComponent ELDER_GUARDIAN_SLICE_CONSUMABLE = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 3000, 0), 0.5f))
            .build();

    public static final FoodComponent COOKED_ELDER_GUARDIAN_SLICE = new FoodComponent.Builder()
            .nutrition(3)
            .saturationModifier(0.3f)
            .build();

    public static final FoodComponent ELDER_GUARDIAN_ROLL = new FoodComponent.Builder()
            .nutrition(7)
            .saturationModifier(0.6f)
            .build();

    public static final FoodComponent CABBAGE_WRAPPED_ELDER_GUARDIAN = new FoodComponent.Builder()
            .nutrition(15)
            .saturationModifier(1.0f)
            .build();


    public static final FoodComponent BRAISED_SEA_PICKLE = new FoodComponent.Builder()
            .nutrition(8)
            .saturationModifier(0.5f)
            .build();

    public static final FoodComponent STUFFED_COD = new FoodComponent.Builder()
            .nutrition(3)
            .saturationModifier(0.2f)
            .build();

    public static final FoodComponent COOKED_STUFFED_COD = new FoodComponent.Builder()
            .nutrition(6)
            .saturationModifier(0.8f)
            .build();

    public static final FoodComponent HONEY_FRIED_KELP = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(0.1f)
            .alwaysEdible()
            .build();

    public static final FoodComponent SEAGRASS_SALAD = new FoodComponent.Builder()
            .nutrition(2)
            .saturationModifier(0.4f)
            .build();

    public static final FoodComponent FUGU_SLICE = new FoodComponent.Builder()
            .nutrition(1)
            .saturationModifier(0.1f)
            .build();

    public static final FoodComponent FUGU_ROLL = new FoodComponent.Builder()
            .nutrition(7)
            .saturationModifier(0.6f)
            .build();
}