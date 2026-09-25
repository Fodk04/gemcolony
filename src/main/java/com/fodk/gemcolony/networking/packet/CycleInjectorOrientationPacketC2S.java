package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CycleInjectorOrientationPacketC2S(BlockPos pos)
        implements CustomPacketPayload {

    public static final Type<CycleInjectorOrientationPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "cycle_injector_orientation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CycleInjectorOrientationPacketC2S> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeBlockPos(packet.pos());
                    },
                    buf -> new CycleInjectorOrientationPacketC2S(
                            buf.readBlockPos()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}