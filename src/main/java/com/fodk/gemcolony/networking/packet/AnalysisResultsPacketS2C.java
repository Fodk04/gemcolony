package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.data.GemAnalysisResult;
import com.fodk.gemcolony.GemColony;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public record AnalysisResultsPacketS2C(int entityId, List<GemAnalysisResult> results) implements CustomPacketPayload {

    public static final Type<AnalysisResultsPacketS2C> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "analysis_results"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AnalysisResultsPacketS2C> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeVarInt(packet.entityId());

                        ByteBufCodecs.collection(
                                ArrayList::new,
                                GemAnalysisResult.STREAM_CODEC
                        ).encode(
                                buf,
                                new ArrayList<>(packet.results())
                        );
                    },

                    buf -> new AnalysisResultsPacketS2C(
                            buf.readVarInt(),

                            ByteBufCodecs.collection(
                                    ArrayList::new,
                                    GemAnalysisResult.STREAM_CODEC
                            ).decode(buf)
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
