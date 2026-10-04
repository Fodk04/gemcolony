package com.fodk.gemcolony.worldgen.blockstateprovider;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStateProviders {

    public static final DeferredRegister<BlockStateProviderType<?>> STATE_PROVIDERS = DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, GemColony.MOD_ID);

    public static final DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<RandomHorizontalFacingProvider>> RANDOM_HORIZONTAL_FACING = STATE_PROVIDERS.register(
            "random_horizontal_facing",
            () -> new BlockStateProviderType<>(RandomHorizontalFacingProvider.CODEC));

    public static final DeferredHolder<BlockStateProviderType<?>, BlockStateProviderType<RandomFullRotationProvider>> RANDOM_FULL_ROTATION = STATE_PROVIDERS.register(
            "random_full_rotation",
            () -> new BlockStateProviderType<>(RandomFullRotationProvider.CODEC));

    public static void register(IEventBus eventBus){
        STATE_PROVIDERS.register(eventBus);
    }
}
