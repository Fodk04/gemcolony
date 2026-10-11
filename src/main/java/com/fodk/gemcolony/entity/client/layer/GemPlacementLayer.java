package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.client.render.GemRenderTypes;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.util.ColorUtil;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class GemPlacementLayer<T extends GeoAnimatable, R extends GeoRenderState> extends GeoRenderLayer<T, Void, R> {

    private final int order;
    private final String gemName;

    public GemPlacementLayer(GeoRenderer<T, Void, R> renderer, String gemName, int order) {
        super(renderer);
        this.order = order;
        this.gemName = gemName;
    }

    protected List<Integer> getGemPlacements(R renderState){
        GemRenderState state = (GemRenderState) renderState;

        return state.gemPlacements;
    }

    protected int getTintColor(R renderState){
        GemRenderState state = (GemRenderState) renderState;
        return state.gemColor;
    }

    protected RenderType getRenderType(R renderState, Identifier texture) {
        GemRenderState state = (GemRenderState) renderState;

        if (state.entityType == ModEntities.HOLOPEARL.get())
            return GemRenderTypes.holopearl(texture);

        if (state.reformProgress < 1f)
            return GemRenderTypes.whiteEmissive(texture);

        return GemRenderTypes.gemPlacement(texture);
    }

    protected Vec3 getReformScale(R renderState, float beginToShow, float endShow) {
        GemRenderState state = (GemRenderState) renderState;

        double scale = (state.reformProgress - beginToShow) / (endShow - beginToShow);
        scale = Math.clamp(scale, 0, 1);
        return new Vec3(scale, scale, scale);
    }

    protected float getReformCenter(R renderState) {
        return ((GemRenderState) renderState).reformCenter;
    }

    protected float getModelSize(R renderState) {
        return ((GemRenderState) renderState).modelSize;
    }

    protected @Nullable Identifier getTextureResource(R renderState, int placementIndex) {
        GemRenderState state = (GemRenderState) renderState;

        if(placementIndex < 0|| state.reformProgress < 1) return  null;
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/entity/gems/" + gemName + "/placements/gem_" + placementIndex + ".png");
    }

    final float beginToShow = 0.55f;
    final float endShow = 0.75f;

    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        if (!renderPassInfo.willRender())
            return;

        R renderState = renderPassInfo.renderState();
        List<Integer> placements = getGemPlacements(renderState);

        if (placements == null || placements.isEmpty())
            return;

        BakedGeoModel model = renderPassInfo.model();
        int packedLight = renderPassInfo.packedLight();
        int packedOverlay = renderPassInfo.packedOverlay();
        int tint = getTintColor(renderState);

        Vec3 reformScale = getReformScale(renderState, this.beginToShow, this.endShow);
        float scale = getModelSize(renderState);
        float reformCenter = getReformCenter(renderState) / scale;

        for (int placementIndex : placements) {
            Identifier texture = getTextureResource(renderState, placementIndex);

            if (texture == null)
                continue;

            RenderType renderType = getRenderType(renderState, texture);

            renderTasks.order(order).submitCustomGeometry(
                    renderPassInfo.poseStack(),
                    renderType,
                    (pose, vertexConsumer) -> {
                        PoseStack poseStack = renderPassInfo.poseStack();
                        poseStack.pushPose();
                        poseStack.last().set(pose);

                        poseStack.scale(scale, scale, scale);
                        poseStack.translate(0, reformCenter, 0);
                        poseStack.scale((float) reformScale.x, (float) reformScale.y, (float) reformScale.z);
                        poseStack.translate(0, -reformCenter, 0);

                        renderPassInfo.renderPosed(
                                () -> model.render(
                                        renderPassInfo,
                                        vertexConsumer,
                                        packedLight,
                                        packedOverlay,
                                        tint
                                )
                        );

                        poseStack.popPose();
                    }
            );
        }
    }
}
