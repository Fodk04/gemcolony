package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GemMarkingsLayer<T extends GeoAnimatable, R extends GemRenderState> extends GemTintedTextureLayer<T, R> {
    private final String gemName;

    public GemMarkingsLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer, order);
        this.gemName = gemName;
    }

    @Override
    protected @Nullable Identifier getTextureResource(R state) {
        if (state.markings < 0) return null;
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/markings/markings_" + state.markings + ".png");
    }

    @Override
    protected int getTintColor(R state) {
        return state.markingsColor;
    }

    final float beginToShow = 0.55f;
    final float endShow = 0.75f;

    @Override
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow) {
        return super.getReformScale(renderState, renderState.reformProgress, this.beginToShow, this.endShow);
    }
}