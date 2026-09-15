package com.fodk.gemcolony.entity.client.render;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public final class GemRenderTypes {

    public static RenderType whiteEmissive(Identifier texture) {
        return RenderType.create(
                "gem_reform",
                RenderSetup
                        .builder(GemRenderPipelines.GEM_REFORM)
                        .withTexture("Sampler0", texture)
                        .createRenderSetup()
        );
    }

    private GemRenderTypes() {
    }
}
