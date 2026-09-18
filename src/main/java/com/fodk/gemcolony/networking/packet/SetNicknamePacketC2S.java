package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SetNicknamePacketC2S(int entityId, String nickname) implements CustomPacketPayload {

    public static final Type<SetNicknamePacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "set_nickname"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetNicknamePacketC2S> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeVarInt(packet.entityId());
                        buf.writeUtf(packet.nickname(), 32);
                    },
                    buf -> new SetNicknamePacketC2S(
                            buf.readVarInt(),
                            buf.readUtf(32)
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
