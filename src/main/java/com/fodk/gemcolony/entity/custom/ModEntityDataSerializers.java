package com.fodk.gemcolony.entity.custom;

import com.fodk.gemcolony.GemColony;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModEntityDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, GemColony.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<Integer>>> INT_LIST = SERIALIZERS.register(
            "int_list",
            () -> EntityDataSerializer.forValueType(ByteBufCodecs.VAR_INT.apply(ByteBufCodecs.list()))
    );

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
