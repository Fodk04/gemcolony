package com.fodk.gemcolony.entity.client.model;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.bubble.BubbleEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class BubbleModel extends GeoModel<BubbleEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "entity/bubble");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/bubble/bubble.png");
    }

    @Override
    public Identifier getAnimationResource(BubbleEntity animatable) {
        return null;
    }
}
