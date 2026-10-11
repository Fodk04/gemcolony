package com.fodk.gemcolony.data;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class FusionRegistryData extends SavedData {

    public static final Codec<FusionRegistryData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    FusionData.CODEC.listOf()
                            .fieldOf("sessions")
                            .forGetter(data -> data.sessions))
            .apply(instance, FusionRegistryData::new));

    public static final SavedDataType<FusionRegistryData> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "fusion_registry"),
                    FusionRegistryData::new,
                    CODEC
            );

    private final List<FusionData> sessions;

    public FusionRegistryData() {
        this(new ArrayList<>());
    }

    private FusionRegistryData(List<FusionData> sessions) {
        this.sessions = new ArrayList<>(sessions);
    }

    public static FusionRegistryData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public List<FusionData> getSessions() {
        return List.copyOf(sessions);
    }

    public Optional<FusionData> getSession(UUID fusionId) {
        return sessions.stream()
                .filter(session -> session.fusionId().equals(fusionId))
                .findFirst();
    }

    public Optional<FusionData> getSessionForGem(UUID gemUUID) {
        return sessions.stream()
                .filter(session -> session.containsParticipant(gemUUID))
                .findFirst();
    }

    public boolean isGemInSession(UUID gemUUID) {
        return getSessionForGem(gemUUID).isPresent();
    }

    public Optional<FusionData> createSession(UUID firstGemUUID) {
        if (isGemInSession(firstGemUUID)) {
            return Optional.empty();
        }

        FusionData session = new FusionData(UUID.randomUUID(), List.of(firstGemUUID), FusionData.FusionState.GATHERING);

        sessions.add(session);
        setDirty();

        return Optional.of(session);
    }

    public boolean addParticipant(UUID fusionId, UUID gemUUID) {
        if (isGemInSession(gemUUID)) {
            return false;
        }

        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (session.state() != FusionData.FusionState.GATHERING) {
            return false;
        }

        replaceSession(session.withParticipant(gemUUID));
        return true;
    }

    public boolean updateState(UUID fusionId, FusionData.FusionState newState) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        replaceSession(existing.get().withState(newState));
        return true;
    }

    public boolean removeSession(UUID fusionId) {
        boolean removed = sessions.removeIf(session -> session.fusionId().equals(fusionId));

        if (removed) {
            setDirty();
        }

        return removed;
    }

    private void replaceSession(FusionData updated) {
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).fusionId().equals(updated.fusionId())) {
                sessions.set(i, updated);
                setDirty();
                return;
            }
        }
    }

    /**
     * Advances every gathering session's countdown by one server tick
     * Call this once per server tick for each ServerLevel.
     */
    public void tickSessions(ServerLevel level) {
        for (FusionData session : List.copyOf(sessions)) {
            if (session.state() != FusionData.FusionState.GATHERING) {
                continue;
            }

            int remainingTicks = session.remainingTicks() - 1;

            if (remainingTicks <= 0) {
                cancelSession(session.fusionId(), level);
            } else {
                replaceSession(session.withRemainingTicks(remainingTicks));
            }
        }
    }

    public boolean cancelSession(UUID fusionId, ServerLevel level) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        for (UUID participantUUID : session.participants()) {
            Entity entity = level.getEntity(participantUUID);

            if (entity instanceof GemEntity participant) {
                participant.setFusing(false);
            }
        }

        removeSession(fusionId);
        return true;
    }

    public List<UUID> removeParticipant(UUID fusionId, UUID gemUUID) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return List.of();
        }

        FusionData session = existing.get();

        if (!session.containsParticipant(gemUUID)) {
            return List.of();
        }

        List<UUID> remaining = session.participants().stream()
                .filter(uuid -> !uuid.equals(gemUUID))
                .toList();

        if (remaining.size() < 2) {
            removeSession(fusionId);
            return session.participants();
        }

        List<UUID> remainingReady = session.readyParticipants().stream()
                .filter(remaining::contains)
                .toList();

        replaceSession(new FusionData(
                session.fusionId(),
                remaining,
                session.state(),
                session.gatheringPosition(),
                remainingReady,
                session.remainingTicks()
        ));

        return List.of(gemUUID);
    }

    public Optional<UUID> getSessionIdForGem(UUID gemUUID) {
        return getSessionForGem(gemUUID).map(FusionData::fusionId);
    }

    public boolean setGatheringPosition(UUID fusionId, BlockPos position) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (session.state() != FusionData.FusionState.GATHERING || session.gatheringPosition().isPresent()) {
            return false;
        }

        replaceSession(session.withGatheringPosition(position));
        return true;
    }

    public boolean beginFusion(UUID fusionId) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (session.state() != FusionData.FusionState.READY || session.participants().size() < 2) {
            return false;
        }

        return updateState(fusionId, FusionData.FusionState.FUSING);
    }

    public boolean markParticipantReady(UUID fusionId, UUID gemUUID) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (session.state() != FusionData.FusionState.GATHERING || !session.containsParticipant(gemUUID)) {
            return false;
        }

        replaceSession(session.withParticipantReady(gemUUID));
        return true;
    }

    public boolean clearParticipantReady(UUID fusionId, UUID gemUUID) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (!session.isParticipantReady(gemUUID)) {
            return false;
        }

        FusionData updated = session.withoutParticipantReady(gemUUID);

        if (updated.state() == FusionData.FusionState.READY) {
            updated = updated.withState(FusionData.FusionState.GATHERING);
        }

        replaceSession(updated);
        return true;
    }

    public boolean markReadyIfEveryoneReady(UUID fusionId) {
        Optional<FusionData> existing = getSession(fusionId);

        if (existing.isEmpty()) {
            return false;
        }

        FusionData session = existing.get();

        if (session.state() != FusionData.FusionState.GATHERING || !session.areAllParticipantsReady()) {
            return false;
        }

        replaceSession(session.withState(FusionData.FusionState.READY));
        return true;
    }
}
