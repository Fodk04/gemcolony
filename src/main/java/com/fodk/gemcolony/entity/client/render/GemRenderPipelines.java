package com.fodk.gemcolony.entity.client.render;

import com.fodk.gemcolony.GemColony;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class GemRenderPipelines {

    public static final RenderPipeline GEM_REFORM = RenderPipeline
            .builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pipeline/gem_reform"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "core/gem_reform"))
            .withShaderDefine("EMISSIVE")
            .build();

    public static final RenderPipeline CONSTRUCTOR_GHOST = RenderPipeline
            .builder(RenderPipelines.BLOCK_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pipeline/constructor_ghost"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "core/constructor_ghost"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .build();

    public static final RenderPipeline CONSTRUCTOR_GHOST_RED = RenderPipeline
            .builder(RenderPipelines.BLOCK_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pipeline/constructor_ghost_red"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "core/constructor_ghost_red"))
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .build();

    private GemRenderPipelines() {
    }
}
