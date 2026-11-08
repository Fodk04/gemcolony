package com.fodk.gemcolony.entity.custom.gem.variant;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GemVariantRegistry {

    private static final Map<Identifier, GemVariantData> VARIANTS = new LinkedHashMap<>();

    private static final Map<EntityType<?>, List<Identifier>> VARIANTS_BY_ENTITY = new LinkedHashMap<>();

    public static void register(Identifier id, EntityType<? extends GemEntity> entityType, GemVariantData data) {
        if (VARIANTS.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate gem variant ID: " + id);
        }

        VARIANTS.put(id, data);
        VARIANTS_BY_ENTITY.computeIfAbsent(entityType, key -> new ArrayList<>()).add(id);
    }

    @Nullable
    public static GemVariantData get(Identifier id) {
        return VARIANTS.get(id);
    }

    @Nullable
    public static GemVariantData get(GemEntity gem) {
        Identifier variantId = gem.getVariantId();

        if (variantId == null) {
            return null;
        }

        return get(variantId);
    }

    public static List<Identifier> getVariantIds(EntityType<?> entityType) {
        return VARIANTS_BY_ENTITY.getOrDefault(entityType, List.of());
    }
}
