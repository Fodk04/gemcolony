package com.fodk.gemcolony.networking;

import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.construction.*;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.networking.packet.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

    public static void handleStartAnalysisPacket(StartAnalysisPacketC2S packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            if (player.level().getEntity(packet.entityId()) instanceof PeridotEntity peridot) {
                peridot.startAnalysis();
            }
        });
    }

    public static void handleAnalysisResultsPacket(AnalysisResultsPacketS2C packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level == null) {
                return;
            }

            if (Minecraft.getInstance().level.getEntity(packet.entityId()) instanceof PeridotEntity peridot) {

                peridot.setAnalysisResults(packet.results());
            }
        });
    }

    public static void handleConfirmConstructionPacket(ConfirmConstructionPacketC2S packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            Entity entity = player.level().getEntity(packet.gemEntityId());

            if (!(entity instanceof StarterGemEntity gem)) {
                return;
            }

            Assembly assembly = Assemblies.getById(packet.assemblyId());

            if (assembly == null) {
                return;
            }

            if (!gem.canConstruct(assembly)) {
                return;
            }

            if (!player.level().isLoaded(packet.placementPos())) {
                return;
            }

            if (!ConstructionPlacement.canPlaceAssembly(
                    player.level(),
                    assembly,
                    packet.placementPos(),
                    packet.placementRotation())) {
                return;
            }

            Level level = player.level();

            for (AssemblyComponent component : assembly.components()) {

                BlockPos componentPos = ConstructionPlacement.rotatePosition(
                        packet.placementPos(),
                        component.x(),
                        component.y(),
                        component.z(),
                        packet.placementRotation(),
                        assembly.centerX(),
                        assembly.centerZ()
                );

                Blueprint blueprint = component.blueprint();

                BlockState blockState = blueprint.block().defaultBlockState();

                if (blockState.getBlock() instanceof ConstructedMultiblock multiblock) {
                    blockState = multiblock.getConstructionState(level, componentPos, blockState);
                }

                if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {

                    Direction facing = switch (component.rotation()) {
                        case CLOCKWISE_90 -> Direction.EAST;
                        case CLOCKWISE_180 -> Direction.SOUTH;
                        case COUNTERCLOCKWISE_90 -> Direction.WEST;
                        default -> Direction.NORTH;
                    };

                    facing = packet.placementRotation().rotate(facing);

                    blockState = blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
                }

                level.setBlock(componentPos, blockState, 3);

                if (blockState.getBlock() instanceof ConstructedMultiblock multiblock) {
                    multiblock.placeStructure(level, componentPos, blockState);
                }
            }
        });
    }

    public static void handleCycleInjectorOrientationPacket(CycleInjectorOrientationPacketC2S packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            if (player.level().getBlockEntity(packet.pos()) instanceof InjectorBlockEntity injector) {

                injector.cycleInjectionOrientation();
            }
        });
    }
}
