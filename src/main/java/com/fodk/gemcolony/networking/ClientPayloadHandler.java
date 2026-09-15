package com.fodk.gemcolony.networking;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.networking.packet.BubblingPacketC2S;
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
}
