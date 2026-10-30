package com.fodk.gemcolony.modonomicon.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class GemEntityPreviewRegistry {

    private static final Map<String, Function<Level, Entity>> FACTORIES = new HashMap<>();

    private GemEntityPreviewRegistry() {
    }

    public static void register(String id, Function<Level, Entity> factory) {
        FACTORIES.put(id, factory);
    }

    public static Entity create(String id, Level level) {
        Function<Level, Entity> factory = FACTORIES.get(id);

        if (factory == null) {
            throw new IllegalArgumentException("Unknown Gem Colony entity preview: " + id);
        }

        Entity entity = factory.apply(level);

        if (entity == null) {
            throw new IllegalStateException("Entity preview factory returned null: " + id);
        }

        return entity;
    }

    public static boolean hasFactory(String id) {
        return FACTORIES.containsKey(id);
    }
}
