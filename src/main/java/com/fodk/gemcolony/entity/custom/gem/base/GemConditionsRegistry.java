package com.fodk.gemcolony.entity.custom.gem.base;

import net.minecraft.resources.Identifier;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GemConditionsRegistry {

    private static final Map<Identifier, GemConditions> CONDITIONS = new LinkedHashMap<>();

    public static final Collection<GemConditions> ALL =
            Collections.unmodifiableCollection(CONDITIONS.values());

    public static void register(GemConditions conditions) {
        Identifier id = conditions.id();

        if (CONDITIONS.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate gem conditions: " + id);
        }

        CONDITIONS.put(id, conditions);
    }

    public static GemConditions get(Identifier id) {
        return CONDITIONS.get(id);
    }
}
