package com.fodk.gemcolony.entity.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public record GemStateData(
        int reformProgress, int quality, boolean emerged, boolean cracked, String ownerUUID, List<ItemStack> inventory
) {
    public static final Codec<GemStateData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("reformProgress").forGetter(GemStateData::reformProgress),
            Codec.INT.fieldOf("quality").forGetter(GemStateData::quality),
            Codec.BOOL.fieldOf("emerged").forGetter(GemStateData::emerged),
            Codec.BOOL.fieldOf("cracked").forGetter(GemStateData::cracked),
            Codec.STRING.fieldOf("owner").forGetter(GemStateData::ownerUUID),
            ItemStack.OPTIONAL_CODEC.listOf().fieldOf("inventory").forGetter(GemStateData::inventory)

    ).apply(instance, GemStateData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GemStateData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, GemStateData::reformProgress,
                    ByteBufCodecs.VAR_INT, GemStateData::quality,
                    ByteBufCodecs.BOOL, GemStateData::emerged,
                    ByteBufCodecs.BOOL, GemStateData::cracked,
                    ByteBufCodecs.STRING_UTF8, GemStateData::ownerUUID,
                    ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()), GemStateData::inventory,
                    GemStateData::new
            );
}
