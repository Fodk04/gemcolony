package com.fodk.gemcolony.entity.client.render;

import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
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

    public static RenderType constructorGhost(boolean canPlace) {
        return RenderType.create(
                canPlace ? "constructor_ghost_green" : "constructor_ghost_red",
                RenderSetup
                        .builder(canPlace ? GemRenderPipelines.CONSTRUCTOR_GHOST : GemRenderPipelines.CONSTRUCTOR_GHOST_RED)
                        .withTexture("Sampler0", TextureAtlas.LOCATION_BLOCKS)
                        .useLightmap()
                        .createRenderSetup()
        );
    }

    private GemRenderTypes() {
    }
}
