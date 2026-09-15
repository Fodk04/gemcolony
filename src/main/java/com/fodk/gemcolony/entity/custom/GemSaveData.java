package com.fodk.gemcolony.entity.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record GemSaveData(
        GemAppearanceData gemAppearanceData,
        GemStateData gemStateData
) {
    public static final Codec<GemSaveData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GemAppearanceData.CODEC.fieldOf("gemAppearance").forGetter(GemSaveData::gemAppearanceData),
            GemStateData.CODEC.fieldOf("gemState").forGetter(GemSaveData::gemStateData)
    ).apply(instance, GemSaveData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemSaveData> STREAM_CODEC =
            StreamCodec.composite(
                    GemAppearanceData.STREAM_CODEC,
                    GemSaveData::gemAppearanceData,

                    GemStateData.STREAM_CODEC,
                    GemSaveData::gemStateData,

                    GemSaveData::new
            );
}

