package com.fodk.gemcolony.effect;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.util.ColorToIndex;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, GemColony.MOD_ID);

    public static final Holder<MobEffect> SADNESS_EFFECT = MOB_EFFECTS.register("sadness",
            () -> new MobEffect(MobEffectCategory.HARMFUL, ColorToIndex.colorToInt(Color.blue)) {}
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath("gemcolony", "effect.sadness_speed"), -0.15D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, Identifier.fromNamespaceAndPath("gemcolony", "effect.sadness_block_break_speed"), -0.4D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath("gemcolony", "effect.sadness_attack_speed"), -0.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.fromNamespaceAndPath("gemcolony", "effect.sadness_attack_damage"), -1.5D, AttributeModifier.Operation.ADD_VALUE));


    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
