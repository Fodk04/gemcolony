package com.fodk.gemcolony.entity.custom.gem.base;

import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.item.ModItems;

import java.util.List;

public class GemDefinitions {

    public static final GemDefinition PERIDOT = new GemDefinition("peridot", "Peridot", List.of(ModItems.PERIDOT_GEM.get()), ModEntities.PERIDOT.get());
    public static final GemDefinition QUARTZ = new GemDefinition("quartz", "Quartz", List.of(
            ModItems.MILKY_QUARTZ_GEM.get(),
            ModItems.PHANTOM_QUARTZ_GEM.get(),
            ModItems.FLINT_QUARTZ_GEM.get(),
            ModItems.ONYX_QUARTZ_GEM.get(),
            ModItems.SMOKY_QUARTZ_GEM.get(),
            ModItems.CARNELIAN_QUARTZ_GEM.get(),
            ModItems.CHERT_QUARTZ_GEM.get(),
            ModItems.CITRINE.get(),
            ModItems.PRASEOLITE_QUARTZ_GEM.get(),
            ModItems.AVENTURINE_QUARTZ_GEM.get(),
            ModItems.ANGEL_AURA_QUARTZ_GEM.get(),
            ModItems.DUMORTIERITE_QUARTZ_GEM.get(),
            ModItems.BLUE_QUARTZ_GEM.get(),
            ModItems.AMETHYST_QUARTZ_GEM.get(),
            ModItems.CHERRY_QUARTZ_GEM.get(),
            ModItems.ROSE_QUARTZ_GEM.get()),
            ModEntities.QUARTZ.get());

    public static final List<GemDefinition> ALL = List.of(
            PERIDOT,
            QUARTZ
    );

    public static GemDefinition get(String id) {
        return ALL.stream()
                .filter(gem -> gem.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}