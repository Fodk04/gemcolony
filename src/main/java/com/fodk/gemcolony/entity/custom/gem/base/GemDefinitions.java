package com.fodk.gemcolony.entity.custom.gem.base;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.*;

public class GemDefinitions {

    private static final Map<Identifier, GemDefinition> DEFINITIONS = new LinkedHashMap<>();

    private static final List<GemDefinition> CONDITIONAL_DEFINITIONS = new ArrayList<>();

    public static final Collection<GemDefinition> ALL = Collections.unmodifiableCollection(CONDITIONAL_DEFINITIONS);

    public static void register(GemDefinition definition) {
        Identifier id = definition.id();

        if (DEFINITIONS.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate gem definition: " + id);
        }

        DEFINITIONS.put(id, definition);
    }

    public static GemDefinition get(Identifier id) {
        return DEFINITIONS.get(id);
    }

    public static void RegisterGemDefinition(
            Identifier id,
            String name,
            List<Item> items,
            EntityType<? extends GemEntity> gem
    ) {
        GemDefinition definition = new GemDefinition(
                id,
                name,
                List.copyOf(items),
                gem
        );

        register(definition);
    }

    public static void RegisterGemDefinitionAndConditions(
            Identifier id,
            String name,
            List<Item> items,
            EntityType<? extends GemEntity> gem,
            float optimalTemperature,
            float optimalHumidity,
            float temperatureSigma,
            float humiditySigma,
            float weight
    ) {
        if (GemConditionsRegistry.get(id) != null) {
            throw new IllegalArgumentException("Duplicate gem conditions: " + id);
        }

        GemDefinition definition = new GemDefinition(
                id,
                name,
                List.copyOf(items),
                gem
        );

        GemConditions conditions = new GemConditions(
                id,
                optimalTemperature,
                optimalHumidity,
                temperatureSigma,
                humiditySigma,
                weight
        );

        register(definition);
        GemConditionsRegistry.register(conditions);

        CONDITIONAL_DEFINITIONS.add(definition);
    }
}