package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.entity.client.model.PeridotModel;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.EntityType;

public class PeridotRenderer extends GemRenderer<PeridotEntity, GemRenderState>{

    public PeridotRenderer(EntityRendererProvider.Context context) {
        super(context, new PeridotModel(), "peridot");
    }
}