package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import java.util.ArrayList;
import java.util.Optional;


public record FusionData(UUID fusionId, List<UUID> participants, FusionState state, Optional<BlockPos> gatheringPosition, List<UUID> readyParticipants, int remainingTicks) {
    public static final int FUSION_TIMEOUT_SECONDS = 10;
    public static final int TICKS_PER_SECOND = 20;
    public static final int DEFAULT_REMAINING_TICKS = FUSION_TIMEOUT_SECONDS * TICKS_PER_SECOND;

    private static final Codec<UUID> UUID_CODEC =
            Codec.STRING.comapFlatMap(
                    value -> {
                        try {
                            return DataResult.success(UUID.fromString(value));
                        } catch (IllegalArgumentException exception) {
                            return DataResult.error(
                                    () -> "Invalid fusion UUID: " + value
                            );
                        }
                    },
                    UUID::toString
            );

    public static final Codec<FusionData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    UUID_CODEC.fieldOf("fusionId")
                            .forGetter(FusionData::fusionId),
                    UUID_CODEC.listOf()
                            .fieldOf("participants")
                            .forGetter(FusionData::participants),
                    FusionState.CODEC.fieldOf("state").forGetter(FusionData::state),
                    BlockPos.CODEC.optionalFieldOf("gatheringPosition").forGetter(FusionData::gatheringPosition),
                    UUID_CODEC.listOf()
                            .optionalFieldOf("readyParticipants", List.of())
                            .forGetter(FusionData::readyParticipants),
                    Codec.INT.optionalFieldOf("remainingTicks", DEFAULT_REMAINING_TICKS)
                            .forGetter(FusionData::remainingTicks))
                    .apply(instance, FusionData::new));

    public FusionData(UUID fusionId, List<UUID> participants, FusionState state) {
        this(fusionId, participants, state, Optional.empty(), List.of(), DEFAULT_REMAINING_TICKS);
    }

    public FusionData(UUID fusionId, List<UUID> participants, FusionState state, Optional<BlockPos> gatheringPosition) {
        this(fusionId, participants, state, gatheringPosition, List.of(), DEFAULT_REMAINING_TICKS);
    }

    public FusionData(UUID fusionId, List<UUID> participants, FusionState state, Optional<BlockPos> gatheringPosition, List<UUID> readyParticipants) {
        this(fusionId, participants, state, gatheringPosition, readyParticipants, DEFAULT_REMAINING_TICKS);
    }

    public FusionData {
        participants = List.copyOf(participants);
        gatheringPosition = gatheringPosition.map(BlockPos::immutable);

        // nly registered participants can be ready
        readyParticipants = readyParticipants.stream()
                .filter(participants::contains)
                .distinct()
                .toList();
    }

    public boolean containsParticipant(UUID gemUUID) {
        return participants.contains(gemUUID);
    }

    public boolean isParticipantReady(UUID gemUUID) {
        return readyParticipants.contains(gemUUID);
    }

    public boolean areAllParticipantsReady() {
        return participants.size() >= 2 && readyParticipants.containsAll(participants);
    }

    public FusionData withParticipant(UUID gemUUID) {
        if (participants.contains(gemUUID)) {
            return this;
        }

        List<UUID> updated = new ArrayList<>(participants);
        updated.add(gemUUID);

        return new FusionData(fusionId, updated, state, gatheringPosition, readyParticipants, remainingTicks);
    }

    public FusionData withState(FusionState newState) {
        return new FusionData(fusionId, participants, newState, gatheringPosition, readyParticipants, remainingTicks);
    }

    public FusionData withGatheringPosition(BlockPos position) {
        return new FusionData(fusionId, participants, state, Optional.of(position.immutable()), readyParticipants, remainingTicks);
    }

    public FusionData withParticipantReady(UUID gemUUID) {
        if (!participants.contains(gemUUID) || readyParticipants.contains(gemUUID)) {
            return this;
        }

        List<UUID> updated = new ArrayList<>(readyParticipants);
        updated.add(gemUUID);

        return new FusionData(fusionId, participants, state, gatheringPosition, updated, remainingTicks);
    }

    public FusionData withoutParticipantReady(UUID gemUUID) {
        if (!readyParticipants.contains(gemUUID)) {
            return this;
        }

        List<UUID> updated = new ArrayList<>(readyParticipants);
        updated.remove(gemUUID);

        return new FusionData(fusionId, participants, state, gatheringPosition, updated, remainingTicks);
    }

    public FusionData withRemainingTicks(int newRemainingTicks) {
        return new FusionData(fusionId, participants, state, gatheringPosition, readyParticipants, Math.max(0, newRemainingTicks));
    }

    public enum FusionState {
        GATHERING,
        READY,
        FUSING;

        public static final Codec<FusionState> CODEC =
                Codec.STRING.comapFlatMap(
                        value -> {
                            try {
                                return DataResult.success(
                                        FusionState.valueOf(value)
                                );
                            } catch (IllegalArgumentException exception) {
                                return DataResult.error(
                                        () -> "Unknown fusion state: " + value
                                );
                            }
                        },
                        FusionState::name
                );
    }
}
