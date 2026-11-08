package com.fodk.gemcolony.event;

import com.fodk.gemcolony.entity.custom.gem.base.GemDefinitions;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.variant.GemVariantData;
import com.fodk.gemcolony.entity.custom.gem.variant.GemVariantRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.Event;
import java.util.List;

public class RegisterGemDefinitionsEvent extends Event {

    public void RegisterGemDefinition(
            Identifier id,
            String name,
            List<Item> items,
            EntityType<? extends GemEntity> gem
    ) {
        GemDefinitions.RegisterGemDefinition(id, name, items, gem);
    }

    public void RegisterGemDefinitionAndConditions(
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
        GemDefinitions.RegisterGemDefinitionAndConditions(
                id,
                name,
                items,
                gem,
                optimalTemperature,
                optimalHumidity,
                temperatureSigma,
                humiditySigma,
                weight
        );
    }

    public void RegisterGemVariant(Identifier id, EntityType<? extends GemEntity> entityType, GemVariantData data) {
        GemVariantRegistry.register(id, entityType, data);
    }
}
