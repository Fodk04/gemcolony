package com.fodk.gemcolony.data;

import com.fodk.gemcolony.GemColony;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ConstructionProgressData extends SavedData {

    public static final Codec<ConstructionProgressData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                            ConstructionProgress.CODEC.listOf()
                                    .fieldOf("constructions")
                                    .forGetter(data -> data.constructions))
                    .apply(instance, ConstructionProgressData::new));

    public static final SavedDataType<ConstructionProgressData> TYPE =
            new SavedDataType<>(
                    Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "construction_progress"),
                    ConstructionProgressData::new,
                    CODEC
            );

    public static final int MAX_HELPERS = 4;

    private final List<ConstructionProgress> constructions;

    public ConstructionProgressData() {
        this(new ArrayList<>());
    }

    private ConstructionProgressData(List<ConstructionProgress> constructions) {
        this.constructions = new ArrayList<>(constructions);
    }

    public static ConstructionProgressData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    public int getProgress(ResourceKey<Level> dimension, BlockPos pos) {
        return constructions.stream()
                .filter(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos))
                .mapToInt(ConstructionProgress::progress)
                .findFirst()
                .orElse(0);
    }

    public void setProgress(ResourceKey<Level> dimension, BlockPos pos, int progress) {
        ConstructionProgress existing = getConstruction(dimension, pos);
        constructions.removeIf(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos));

        if (progress > 0) {
            constructions.add(new ConstructionProgress(
                    dimension,
                    pos.immutable(),
                    Math.min(progress, 200),
                    existing == null ? null : existing.leadStarter(),
                    existing == null ? List.of() : existing.helperStarters()
            ));
        }
        setDirty();
    }

    public void clearProgress(ResourceKey<Level> dimension, BlockPos pos) {
        constructions.removeIf(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos));
        setDirty();
    }


    public boolean tryClaim(ResourceKey<Level> dimension, BlockPos pos, UUID starterId) {
        ConstructionProgress entry = getConstruction(dimension, pos);

        if (entry == null) {
            constructions.add(new ConstructionProgress(dimension, pos.immutable(), 0, starterId, List.of()));
            setDirty();
            return true;
        }

        if (entry.progress() == 0 && entry.leadStarter() != null && !starterId.equals(entry.leadStarter())) {
            return false;
        }

        if (starterId.equals(entry.leadStarter()) || entry.helperStarters().contains(starterId)) {
            return true;
        }

        if (entry.leadStarter() == null) {
            List<UUID> helpers = new ArrayList<>(entry.helperStarters());

            if (!helpers.isEmpty()) {
                UUID newLead = helpers.remove(0);
                replaceAssignment(entry, newLead, helpers);
                entry = getConstruction(dimension, pos);
            }
        }

        if (entry.leadStarter() == null) {
            replaceAssignment(entry, starterId, entry.helperStarters());
            setDirty();
            return true;
        }

        if (entry.helperStarters().size() >= MAX_HELPERS) {
            return false;
        }

        List<UUID> helpers = new ArrayList<>(entry.helperStarters());
        helpers.add(starterId);
        replaceAssignment(entry, entry.leadStarter(), helpers);
        setDirty();
        return true;
    }

    public boolean isLead(ResourceKey<Level> dimension, BlockPos pos, UUID starterId) {
        ConstructionProgress entry = getConstruction(dimension, pos);
        return entry != null && starterId.equals(entry.leadStarter());
    }

    public boolean hasClaim(ResourceKey<Level> dimension, BlockPos pos, UUID starterId) {
        ConstructionProgress entry = getConstruction(dimension, pos);
        return entry != null && (starterId.equals(entry.leadStarter()) || entry.helperStarters().contains(starterId));
    }

    public void releaseClaim(ResourceKey<Level> dimension, BlockPos pos, UUID starterId) {
        ConstructionProgress entry = getConstruction(dimension, pos);

        if (entry == null || !hasClaim(dimension, pos, starterId)) {
            return;
        }

        List<UUID> helpers = new ArrayList<>(entry.helperStarters());
        UUID lead = entry.leadStarter();

        if (starterId.equals(lead)) {
            lead = helpers.isEmpty() ? null : helpers.remove(0);
        } else {
            helpers.remove(starterId);
        }

        replaceAssignment(entry, lead, helpers);
        removeIfEmpty(entry);
        setDirty();
    }

    private void replaceAssignment(ConstructionProgress oldEntry, UUID lead, List<UUID> helpers) {
        constructions.remove(oldEntry);
        constructions.add(new ConstructionProgress(
                oldEntry.dimension(),
                oldEntry.pos(),
                oldEntry.progress(),
                lead,
                List.copyOf(helpers)
        ));
    }

    private void removeIfEmpty(ConstructionProgress entry) {
        if (entry.progress() == 0 && entry.leadStarter() == null && entry.helperStarters().isEmpty()) {
            constructions.remove(entry);
        }
    }


    private ConstructionProgress getConstruction(ResourceKey<Level> dimension, BlockPos pos) {
        return constructions.stream()
                .filter(entry -> entry.dimension().equals(dimension) && entry.pos().equals(pos))
                .findFirst()
                .orElse(null);
    }

    public record ConstructionProgress(ResourceKey<Level> dimension, BlockPos pos, int progress, UUID leadStarter, List<UUID> helperStarters) {
        public static final Codec<ConstructionProgress> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                                ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(ConstructionProgress::dimension),
                                BlockPos.CODEC.fieldOf("pos").forGetter(ConstructionProgress::pos),
                                Codec.INT.fieldOf("progress").forGetter(ConstructionProgress::progress),
                                UUIDUtil.CODEC.optionalFieldOf("lead_starter").forGetter(entry -> Optional.ofNullable(entry.leadStarter())),
                                UUIDUtil.CODEC.listOf().optionalFieldOf("helper_starters", List.of()).forGetter(ConstructionProgress::helperStarters))
                        .apply(instance, (dimension, pos, progress, lead, helpers) ->
                                new ConstructionProgress(
                                        dimension,
                                        pos,
                                        progress,
                                        lead.orElse(null),
                                        helpers
                                )));
    }

    public void cleanupUnloadedStarters(ServerLevel level) {
        boolean changed = false;

        for (ConstructionProgress entry : new ArrayList<>(constructions)) {
            if (!entry.dimension().equals(level.dimension())) {
                continue;
            }

            List<UUID> helpers = new ArrayList<>(entry.helperStarters());
            helpers.removeIf(uuid -> level.getEntity(uuid) == null);
            UUID lead = entry.leadStarter();

            if (lead != null && level.getEntity(lead) == null) {
                lead = helpers.isEmpty() ? null : helpers.remove(0);
            }

            if (!java.util.Objects.equals(lead, entry.leadStarter()) || !helpers.equals(entry.helperStarters())) {
                replaceAssignment(entry, lead, helpers);
                changed = true;
            }

            ConstructionProgress updated = getConstruction(entry.dimension(), entry.pos());

            if (updated != null) {
                int before = constructions.size();
                removeIfEmpty(updated);
                if (constructions.size() != before) {
                    changed = true;
                }
            }
        }

        if (changed) {
            setDirty();
        }
    }
}