package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Rotation;

public record ConfirmConstructionPacketC2S(int gemEntityId, String assemblyId, BlockPos placementPos, Rotation placementRotation) implements CustomPacketPayload {

    public static final Type<ConfirmConstructionPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "confirm_construction"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConfirmConstructionPacketC2S> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeVarInt(packet.gemEntityId());
                        buf.writeUtf(packet.assemblyId());
                        buf.writeBlockPos(packet.placementPos());
                        buf.writeEnum(packet.placementRotation());
                    },
                    buf -> new ConfirmConstructionPacketC2S(
                            buf.readVarInt(),
                            buf.readUtf(),
                            buf.readBlockPos(),
                            buf.readEnum(Rotation.class)
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
