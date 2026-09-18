package com.fodk.gemcolony.networking;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.networking.packet.BubblingPacketC2S;
import com.fodk.gemcolony.networking.packet.SetAppearancePacketC2S;
import com.fodk.gemcolony.networking.packet.SetNicknamePacketC2S;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//handling packets from client to server
public class ClientPayloadHandler {
    //on the server

    public static void handleBubblingPacket(BubblingPacketC2S bubblingPacketC2S, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            ItemStack itemInHand = player.getMainHandItem();

            if(itemInHand.getItem() instanceof GemItem gemItem){
                if(!player.getCooldowns().isOnCooldown(itemInHand)){
                    player.getCooldowns().addCooldown(itemInHand, 50);
                    boolean bubbled = itemInHand.has(ModDataComponents.BUBBLED) ? itemInHand.get(ModDataComponents.BUBBLED) : false;
                    itemInHand.set(ModDataComponents.BUBBLED, !bubbled);
                }
            }
        });
    }

    // Called on the server when a client sends a nickname
    public static void handleSetNicknamePacket(SetNicknamePacketC2S packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            if (player.level().getEntity(packet.entityId()) instanceof GemEntity gem) {
                gem.setNickname(packet.nickname());
            }
        });
    }

    public static void handleSetAppearancePacket(SetAppearancePacketC2S packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            if (player.level().getEntity(packet.entityId()) instanceof GemEntity gem) {

                switch (packet.appearance()) {
                    case OUTFIT -> {
                        if (packet.value() >= 0 && packet.value() < gem.getMaxOutfits()) {
                            gem.setOutfit(packet.value());
                        }
                    }

                    case INSIGNIA -> {
                        if (packet.value() >= 0 && packet.value() < gem.getMaxInsignias()) {
                            gem.setInsignia(packet.value());
                        }
                    }

                    case HAIRSTYLE -> {
                        if (packet.value() >= 0 && packet.value() < gem.getMaxHairstyles()) {
                            gem.setHairstyle(packet.value());
                        }
                    }

                    case VISOR -> {
                        if (packet.value() >= 0 && packet.value() < gem.getMaxVisors()) {
                            gem.setVisor(packet.value());
                        }
                    }
                }
            }
        });
    }
}
