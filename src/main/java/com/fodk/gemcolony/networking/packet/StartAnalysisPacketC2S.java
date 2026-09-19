package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record StartAnalysisPacketC2S(int entityId) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<StartAnalysisPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "start_analysis"));

    public static final StreamCodec<RegistryFriendlyByteBuf, StartAnalysisPacketC2S> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeVarInt(packet.entityId());
                    },
                    buf -> new StartAnalysisPacketC2S(
                            buf.readVarInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
