package com.fodk.gemcolony.block.entity;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.entity.custom.GemSeedBlockEntity;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GemColony.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InjectorBlockEntity>> INJECTOR_BE =
            BLOCK_ENTITIES.register("injector",
                    () -> new BlockEntityType<>(
                            InjectorBlockEntity::new,
                            ModBlocks.DRILL.get()
                    ));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GemSeedBlockEntity>> GEM_SEED_BE =
            BLOCK_ENTITIES.register(
                    "gem_seed",
                    () -> new BlockEntityType<>(
                            GemSeedBlockEntity::new,
                            ModBlocks.GEM_SEED.get()
                    )
            );


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
