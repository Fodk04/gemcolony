package com.fodk.gemcolony.construction;

import java.util.List;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record Blueprint(
        String id,
        String name,
        int width,
        int height,
        int depth,

        double offsetX,
        double offsetY,
        double offsetZ,

        int constructionStages,
        Block block,
        List<ConstructionRequirement> requirements
) {}
