package com.fodk.gemcolony.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReformRegistryData extends SavedData {

    public static final Codec<ReformRegistryData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ReformEntry.CODEC.listOf().fieldOf("entries").forGetter(data -> data.entries)
    ).apply(instance, ReformRegistryData::new));

    public static final SavedDataType<ReformRegistryData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath("gemcolony", "reform_registry"),
            ReformRegistryData::new,
            CODEC
    );

    private final List<ReformEntry> entries;

    public ReformRegistryData() { this(new ArrayList<>()); }
    private ReformRegistryData(List<ReformEntry> entries) { this.entries = new ArrayList<>(entries); }

    public static ReformRegistryData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    /** Returns true if this reform just completed this tick. */
    public boolean tickDown(UUID itemId, int startingTicksIfNew) {
        for (int i = 0; i < entries.size(); i++) {
            ReformEntry entry = entries.get(i);
            if (entry.itemId().equals(itemId)) {
                int remaining = entry.remainingTicks() - 1;
                setDirty();
                if (remaining <= 0) {
                    entries.remove(i);
                    return true;
                }
                entries.set(i, new ReformEntry(itemId, remaining));
                return false;
            }
        }
        // not tracked yet — register it fresh
        entries.add(new ReformEntry(itemId, startingTicksIfNew - 1));
        setDirty();
        return false;
    }

    public void remove(UUID itemId) {
        entries.removeIf(e -> e.itemId().equals(itemId));
        setDirty();
    }
}
