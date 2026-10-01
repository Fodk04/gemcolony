package com.fodk.gemcolony.data;

import com.fodk.gemcolony.GemColony;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(
                    NeoForgeRegistries.ATTACHMENT_TYPES,
                    GemColony.MOD_ID
            );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> BUBBLE_COLOR = ATTACHMENTS.register("bubble_color",
                    () -> AttachmentType.builder(() -> 0xFFFFFF)
                            .serialize(Codec.INT.fieldOf("color"))
                            .copyOnDeath()
                            .build()
            );

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
