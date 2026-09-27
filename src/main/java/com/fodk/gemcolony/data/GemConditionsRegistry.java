package com.fodk.gemcolony.data;

import com.fodk.gemcolony.entity.custom.GemConditions;

import java.util.List;

public class GemConditionsRegistry {

    public static final GemConditions PERIDOT =
            new GemConditions(
                    "peridot",
                    0.8f,
                    1.2f,
                    0.3f,
                    0.3f,
                    1.0f
            );

    public static final GemConditions QUARTZ =
            new GemConditions(
                    "quartz",
                    1f,
                    1f,
                    0.3f,
                    0.3f,
                    1.0f
            );

    public static final List<GemConditions> ALL = List.of(
            PERIDOT,
            QUARTZ
    );
}
