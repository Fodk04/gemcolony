package com.fodk.gemcolony.data;

import com.fodk.gemcolony.entity.custom.GemConditions;

import java.util.List;

public class GemConditionsRegistry {

    public static final GemConditions PERIDOT =
            new GemConditions(
                    "peridot",
                    0.8f,
                    0.8f,
                    0.3f,
                    0.3f,
                    1.0f
            );

    public static final GemConditions RUBY =
            new GemConditions(
                    "ruby",
                    0.5f,
                    0.5f,
                    0.3f,
                    0.3f,
                    1.0f
            );

    public static final GemConditions SAPPHIRE =
            new GemConditions(
                    "sapphire",
                    0.8f,
                    0.8f,
                    0.3f,
                    0.3f,
                    0.5f
            );

    public static final List<GemConditions> ALL = List.of(
            PERIDOT,
            RUBY,
            SAPPHIRE
    );
}
