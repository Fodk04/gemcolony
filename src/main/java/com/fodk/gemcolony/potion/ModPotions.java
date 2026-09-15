package com.fodk.gemcolony.potion;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, GemColony.MOD_ID);

    public static final Holder<Potion> SADNESS_POTION = POTIONS.register("sadness_potion",
            () -> new Potion("sadness_potion", new MobEffectInstance(ModEffects.SADNESS_EFFECT, 1200, 0)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
