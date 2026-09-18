package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.networking.AppearanceChange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SetAppearancePacketC2S(int entityId, AppearanceChange appearance, int value) implements CustomPacketPayload {

    public static final Type<SetAppearancePacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "set_appearance"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetAppearancePacketC2S> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeVarInt(packet.entityId());
                        buf.writeEnum(packet.appearance());
                        buf.writeVarInt(packet.value());
                    },
                    buf -> new SetAppearancePacketC2S(
                            buf.readVarInt(),
                            buf.readEnum(AppearanceChange.class),
                            buf.readVarInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
