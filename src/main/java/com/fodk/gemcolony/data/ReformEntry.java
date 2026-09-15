package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record ReformEntry(UUID itemId, int remainingTicks) {

    public static final Codec<ReformEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UUIDUtil.CODEC.fieldOf("itemId").forGetter(ReformEntry::itemId),
            Codec.INT.fieldOf("remainingTicks").forGetter(ReformEntry::remainingTicks)
    ).apply(instance, ReformEntry::new));
}
