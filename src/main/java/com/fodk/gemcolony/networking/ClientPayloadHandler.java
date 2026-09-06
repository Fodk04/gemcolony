package com.fodk.gemcolony.networking;

import com.fodk.gemcolony.networking.packet.TestPacketC2S;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//handling packets from client to server
public class ClientPayloadHandler {
    //on the server

    public static void handleTestPacket(TestPacketC2S testPacketC2S, IPayloadContext iPayloadContext) {
        EntityTypes.COW.spawn(((ServerLevel) iPayloadContext.player().level()), iPayloadContext.player().getOnPos(), EntitySpawnReason.TRIGGERED);
    }
}
