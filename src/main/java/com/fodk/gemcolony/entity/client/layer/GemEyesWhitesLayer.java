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

public class GemEyesWhitesLayer<T extends GeoAnimatable, R extends GemRenderState> extends GemTintedTextureLayer<T, R> {
    private final String gemName;

    public GemEyesWhitesLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer,order);
        this.gemName = gemName;
    }

    @Override
    protected @Nullable Identifier getTextureResource(R state) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/" + gemName + "_eyes_whites.png");
    }

    @Override
    protected int getTintColor(R state) {
        return ColorUtil.colorToInt(new Color(255, 255, 255));
    }

    final float beginToShow = 0.55f;
    final float endShow = 0.75f;

    @Override
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow) {
        return super.getReformScale(renderState, renderState.reformProgress, this.beginToShow, this.endShow);
    }
}