package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.render.GemRenderTypes;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.util.ColorUtil;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.awt.*;

public class GemEyesLayer<T extends GeoAnimatable, R extends GemRenderState> extends GemTintedTextureLayer<T, R> {
    private final String gemName;

    public GemEyesLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer,order);
        this.gemName = gemName;
    }

    @Override
    protected @Nullable Identifier getTextureResource(R state) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/" + gemName + "_eyes.png");
    }

    @Override
    protected int getTintColor(R state) {
        return ColorUtil.multiplyColors(state.gemColor, ColorUtil.colorToInt(new Color(140, 140, 140)));
    }

    final float beginToShow = 0.55f;
    final float endShow = 0.75f;

    @Override
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow) {
        return super.getReformScale(renderState, renderState.reformProgress, this.beginToShow, this.endShow);
    }
}