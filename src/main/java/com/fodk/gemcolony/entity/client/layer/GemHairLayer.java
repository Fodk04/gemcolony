package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.render.GemRenderTypes;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GemHairLayer<T extends GeoAnimatable, R extends GemRenderState> extends GemTintedTextureLayer<T, R> {
    private final String gemName;

    public GemHairLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer,order);
        this.gemName = gemName;
    }

    @Override
    protected @Nullable Identifier getTextureResource(R state) {
        if (state.hairstyle < 0) return null;
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/hairstyles/hair_" + state.hairstyle + ".png");
    }

    @Override
    protected int getTintColor(R state) {
        return state.hairColor;
    }

    @Override
    protected RenderType getRenderType(R state, Identifier texture) {
        if(state.reformProgress < 1f) return GemRenderTypes.whiteEmissive(texture);
        return RenderTypes.entityCutout(texture);
    }

    final float beginToShow = 0.7f;
    final float endShow = 0.85f;

    @Override
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow) {
        return super.getReformScale(renderState, renderState.reformProgress, this.beginToShow, this.endShow);
    }

    @Override
    protected float getReformCenter(R renderState) {
        return renderState.reformCenter;
    }

    @Override
    protected float getQualityModifier(R renderState) {
        return renderState.qualityModifier;
    }

    @Override
    protected float getModelSize(R renderState) {
        return renderState.modelSize;
    }
}