package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record FusionSelectionData(List<UUID> gemUUIDs) {

    public static final Codec<FusionSelectionData> CODEC = UUIDUtil.CODEC.listOf().xmap(FusionSelectionData::new, FusionSelectionData::gemUUIDs);

    public static final StreamCodec<RegistryFriendlyByteBuf, FusionSelectionData> STREAM_CODEC =
            new StreamCodec<>() {
                @Override
                public FusionSelectionData decode(RegistryFriendlyByteBuf buffer) {
                    int size = ByteBufCodecs.VAR_INT.decode(buffer);
                    List<UUID> uuids = new ArrayList<>(size);

                    for (int i = 0; i < size; i++) {
                        uuids.add(UUIDUtil.STREAM_CODEC.decode(buffer));
                    }

                    return new FusionSelectionData(uuids);
                }

                @Override
                public void encode(RegistryFriendlyByteBuf buffer, FusionSelectionData data) {
                    ByteBufCodecs.VAR_INT.encode(buffer, data.gemUUIDs().size());

                    for (UUID uuid : data.gemUUIDs()) {
                        UUIDUtil.STREAM_CODEC.encode(buffer, uuid);
                    }
                }
            };
}
