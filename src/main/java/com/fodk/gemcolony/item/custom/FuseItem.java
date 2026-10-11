package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.data.FusionData;
import com.fodk.gemcolony.data.FusionRegistryData;
import com.fodk.gemcolony.data.FusionSelectionData;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FuseItem extends Item {

    private static final float gemMaxFusionDistance = 20.0f;

    public FuseItem(Properties properties) {
        super(properties);
    }

    public InteractionResult tryAddGem(ItemStack stack, Player player, GemEntity gem) {
        if (player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        FusionSelectionData selection = stack.getOrDefault(ModDataComponents.FUSION_SELECTION, new FusionSelectionData(List.of()));

        if (selection.gemUUIDs().contains(gem.getUUID())) {
            return InteractionResult.SUCCESS;
        }

        if (selection.gemUUIDs().size() >= 5) {
            player.sendOverlayMessage(Component.literal("You can fuse a maximum of 5 Gems."));
            return InteractionResult.SUCCESS;
        }

        List<UUID> selectedUUIDs = new ArrayList<>(selection.gemUUIDs());
        selectedUUIDs.add(gem.getUUID());

        stack.set(ModDataComponents.FUSION_SELECTION, new FusionSelectionData(selectedUUIDs));
        player.sendOverlayMessage(Component.literal("Gem selected (" + selectedUUIDs.size() + "/5)"));

        return InteractionResult.SUCCESS;
    }


    public InteractionResult tryFuse(ItemStack stack, Player player) {
        if (player.level().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        FusionSelectionData selection = stack.getOrDefault(ModDataComponents.FUSION_SELECTION, new FusionSelectionData(List.of()));

        List<UUID> uuids = selection.gemUUIDs();

        if (uuids.size() < 2) {
            player.sendOverlayMessage(Component.literal("You need at least 2 Gems to fuse."));
            return InteractionResult.FAIL;
        }

        List<GemEntity> gems = new ArrayList<>();

        for (UUID uuid : uuids) {
            Entity entity = player.level().getEntity(uuid);

            if (!(entity instanceof GemEntity gem) || !gem.isAlive()) {
                player.sendOverlayMessage(Component.literal("One of the selected Gems is no longer available."));
                return InteractionResult.FAIL;
            }

            gems.add(gem);
        }

        Level level = gems.get(0).level();

        for (GemEntity gem : gems) {
            if (gem.level() != level) {
                player.sendOverlayMessage(Component.literal("All Gems must be in the same world."));
                return InteractionResult.FAIL;
            }

            if (gem.isFusing()) {
                player.sendOverlayMessage(Component.literal("One of the selected Gems is already fusing."));
                return InteractionResult.FAIL;
            }
        }

        for (int i = 0; i < gems.size(); i++) {
            for (int j = i + 1; j < gems.size(); j++) {
                if (gems.get(i).distanceTo(gems.get(j)) > gemMaxFusionDistance) {
                    player.sendOverlayMessage(Component.literal("The selected Gems are too far apart."));
                    return InteractionResult.FAIL;
                }
            }
        }

        Class<? extends GemEntity> firstType = gems.get(0).getClass();

        for (GemEntity gem : gems) {
            if (gem.getClass() != firstType) {
                player.sendOverlayMessage(Component.literal("These Gems cannot fuse."));
                return InteractionResult.FAIL;
            }
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.FAIL;
        }

        FusionRegistryData registry = FusionRegistryData.get(serverLevel);

        // Create a session using the first selected Gem.
        FusionData session = registry.createSession(gems.get(0).getUUID()).orElse(null);

        if (session == null) {
            player.sendOverlayMessage(Component.literal("Could not start the fusion session."));
            return InteractionResult.FAIL;
        }

        gems.get(0).setFusing(true);

        // Add the remaining selected Gems to the same session.
        for (int i = 1; i < gems.size(); i++) {
            GemEntity gem = gems.get(i);

            if (!registry.addParticipant(session.fusionId(), gem.getUUID())) {
                // Roll back the session if any participant cannot join.
                registry.removeSession(session.fusionId());

                for (GemEntity participant : gems) {
                    participant.setFusing(false);
                }

                player.sendOverlayMessage(Component.literal("Could not add every Gem to the fusion session."));
                return InteractionResult.FAIL;
            }

            gem.setFusing(true);
        }

        player.sendOverlayMessage(Component.literal("Fusion started! Your Gems are gathering."));

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        tryFuse(stack, player);

        stack.remove(ModDataComponents.FUSION_SELECTION);

        return InteractionResult.SUCCESS;
    }
}
