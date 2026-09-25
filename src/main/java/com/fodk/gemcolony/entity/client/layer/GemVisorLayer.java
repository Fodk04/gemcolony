package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class GemVisorLayer<T extends GeoAnimatable, R extends GemRenderState> extends GemTintedTextureLayer<T, R> {
    private final String gemName;

    public GemVisorLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer, order);
        this.gemName = gemName;
    }

    @Override
    protected @Nullable Identifier getTextureResource(R state) {
        if (state.visor < 0 || state.reformProgress < 1) return null;
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/visor/visor_" + state.visor + ".png");
    }

    @Override
    protected int getTintColor(R state) {
        return state.visorColor;
    }

    @Override
    protected RenderType getRenderType(R state, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow) {
        return new Vec3(1,1,1);
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
