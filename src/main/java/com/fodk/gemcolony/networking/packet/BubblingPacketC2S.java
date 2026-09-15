package com.fodk.gemcolony.networking.packet;

import com.fodk.gemcolony.GemColony;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record BubblingPacketC2S() implements CustomPacketPayload {

    public static final Type<BubblingPacketC2S> TYPE = new Type<>(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "bubbling_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BubblingPacketC2S> STREAM_CODEC =
            StreamCodec.unit(new BubblingPacketC2S());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
