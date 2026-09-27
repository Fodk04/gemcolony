package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.entity.client.model.StarterGemModel;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class StarterGemRenderer extends GemRenderer<StarterGemEntity, GemRenderState>{

    public StarterGemRenderer(EntityRendererProvider.Context context) {
        super(context, new StarterGemModel(), "pebble");
    }
}