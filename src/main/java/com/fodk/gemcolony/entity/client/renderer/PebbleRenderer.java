package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.entity.client.model.PebbleModel;
import com.fodk.gemcolony.entity.client.model.PeridotModel;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.PebbleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PebbleRenderer extends GemRenderer<PebbleEntity, GemRenderState>{

    public PebbleRenderer(EntityRendererProvider.Context context) {
        super(context, new PebbleModel(), "pebble");
    }
}