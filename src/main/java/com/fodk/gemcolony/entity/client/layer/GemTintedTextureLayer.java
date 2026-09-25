package com.fodk.gemcolony.entity.client.layer;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public abstract class GemTintedTextureLayer<T extends GeoAnimatable, R extends GeoRenderState> extends GeoRenderLayer<T, Void, R> {

    int order;

    public GemTintedTextureLayer(GeoRenderer<T, Void, R> renderer, int order) {
        super(renderer);
        this.order = order;
    }

    protected abstract @Nullable Identifier getTextureResource(R renderState);
    protected abstract int getTintColor(R renderState);
    protected abstract RenderType getRenderType(R renderState, Identifier texture);
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow){
        double scale = (reformProgress - beginToShow) / (endShow - beginToShow);
        scale = Math.clamp(scale, 0, 1);
        return new Vec3(scale, scale, scale);
    }
    protected abstract float getReformCenter(R renderState);
    protected abstract float getQualityModifier(R renderState);
    protected abstract float getModelSize(R renderState);

    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        if (!renderPassInfo.willRender()) return;

        R renderState = renderPassInfo.renderState();
        Identifier texture = getTextureResource(renderState);
        if (texture == null) return;

        RenderType renderType = getRenderType(renderState, texture);

        int packedLight = renderPassInfo.packedLight();
        int packedOverlay = renderPassInfo.packedOverlay();
        int myColor = getTintColor(renderState);
        BakedGeoModel model = renderPassInfo.model();
        Vec3 reformScale = getReformScale(renderState, 1, 0, 1);
        float scale = getQualityModifier(renderState) * getModelSize(renderState);
        Vec3 qualityScale = new Vec3(scale, scale,scale) ;

        renderTasks.order(order).submitCustomGeometry(renderPassInfo.poseStack(), renderType, (pose, vertexConsumer) -> {
            PoseStack poseStack = renderPassInfo.poseStack();
            poseStack.pushPose();
            poseStack.last().set(pose);
            poseStack.scale((float)qualityScale.x, (float)qualityScale.y, (float)qualityScale.z);
            poseStack.translate(0, 1, 0);
            poseStack.scale((float)reformScale.x, (float)reformScale.y, (float)reformScale.z);
            poseStack.translate(0, -1, 0);
            renderPassInfo.renderPosed(() -> model.render(renderPassInfo, vertexConsumer, packedLight, packedOverlay, myColor));
            poseStack.popPose();
        });
    }
}
