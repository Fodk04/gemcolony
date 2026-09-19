package com.fodk.gemcolony.entity.client.model;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public abstract class GemModel<T extends GemEntity> extends DefaultedEntityGeoModel<T> {

    public GemModel(Identifier model) {
        super(model);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/blank.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "gem");
    }
}
