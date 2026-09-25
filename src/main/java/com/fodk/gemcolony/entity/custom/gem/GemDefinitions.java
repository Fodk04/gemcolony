package com.fodk.gemcolony.entity.custom.gem;

import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.GemDefinition;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.Supplier;

public class GemDefinitions {

    public static final GemDefinition PERIDOT = new GemDefinition("peridot", "Peridot", ModItems.PERIDOT_GEM.get(), ModEntities.PERIDOT.get());
    public static final GemDefinition RUBY = new GemDefinition("ruby", "Ruby", ModItems.RUBY_GEM.get(), ModEntities.PERIDOT.get());
    public static final GemDefinition SAPPHIRE = new GemDefinition("sapphire", "Sapphire", ModItems.SAPPHIRE_GEM.get(), ModEntities.PERIDOT.get());

    public static final List<GemDefinition> ALL = List.of(
            PERIDOT,
            RUBY,
            SAPPHIRE
    );

    public static GemDefinition get(String id) {
        return ALL.stream()
                .filter(gem -> gem.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}