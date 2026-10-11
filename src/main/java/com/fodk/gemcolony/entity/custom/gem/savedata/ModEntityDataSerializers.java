package com.fodk.gemcolony.entity.custom.gem.savedata;

import com.fodk.gemcolony.GemColony;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModEntityDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, GemColony.MOD_ID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<Integer>>> INT_LIST = SERIALIZERS.register("int_list",
            () -> EntityDataSerializer.forValueType(ByteBufCodecs.VAR_INT.apply(ByteBufCodecs.list()))
    );

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<List<ItemStack>>> ITEM_STACK_LIST = SERIALIZERS.register("item_stack_list",
                    () -> EntityDataSerializer.forValueType(
                            new StreamCodec<RegistryFriendlyByteBuf, List<ItemStack>>() {
                                @Override
                                public List<ItemStack> decode(RegistryFriendlyByteBuf buffer) {
                                    int size = ByteBufCodecs.VAR_INT.decode(buffer);
                                    List<ItemStack> stacks = new ArrayList<>(size);

                                    for (int i = 0; i < size; i++) {
                                        stacks.add(ItemStack.STREAM_CODEC.decode(buffer));
                                    }

                                    return stacks;
                                }

                                @Override
                                public void encode(RegistryFriendlyByteBuf buffer, List<ItemStack> stacks) {
                                    ByteBufCodecs.VAR_INT.encode(buffer, stacks.size());

                                    for (ItemStack stack : stacks) {
                                        ItemStack.STREAM_CODEC.encode(buffer, stack);
                                    }
                                }
                            }
                    )
            );

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
