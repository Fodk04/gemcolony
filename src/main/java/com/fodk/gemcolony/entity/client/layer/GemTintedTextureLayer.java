package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.client.render.GemRenderTypes;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
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

public abstract class GemTintedTextureLayer<T extends GeoAnimatable, R extends GeoRenderState> extends GeoRenderLayer<T, Void, R> {

    int order;

    public GemTintedTextureLayer(GeoRenderer<T, Void, R> renderer, int order) {
        super(renderer);
        this.order = order;
    }

    protected abstract @Nullable Identifier getTextureResource(R renderState);
    protected abstract int getTintColor(R renderState);

    protected RenderType getRenderType(R renderState, Identifier texture){
        GemRenderState state = (GemRenderState) renderState;
        if(state.entityType == ModEntities.HOLOPEARL.get()) return GemRenderTypes.holopearl(texture);
        if(state.reformProgress < 1f) return GemRenderTypes.whiteEmissive(texture);
        return RenderTypes.entityCutout(texture);
    }
    protected Vec3 getReformScale(R renderState, float reformProgress, float beginToShow, float endShow){
        double scale = (reformProgress - beginToShow) / (endShow - beginToShow);
        scale = Math.clamp(scale, 0, 1);
        return new Vec3(scale, scale, scale);
    }
    protected float getReformCenter(R renderState){
        GemRenderState state = (GemRenderState) renderState;
        return state.reformCenter;
    }
    protected float getQualityModifier(R renderState){
        GemRenderState state = (GemRenderState) renderState;
        return state.qualityModifier;
    }
    protected float getModelSize(R renderState){
        GemRenderState state = (GemRenderState) renderState;
        return state.modelSize;
    }

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
            poseStack.translate(0, getReformCenter(renderState), 0);
            poseStack.scale((float)reformScale.x, (float)reformScale.y, (float)reformScale.z);
            poseStack.translate(0, -getReformCenter(renderState), 0);
            renderPassInfo.renderPosed(() -> model.render(renderPassInfo, vertexConsumer, packedLight, packedOverlay, myColor));
            poseStack.popPose();
        });
    }
}
