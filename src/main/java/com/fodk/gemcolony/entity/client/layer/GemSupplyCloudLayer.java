package com.fodk.gemcolony.entity.client.layer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GemSupplyCloudLayer<T extends GemEntity, R extends GeoRenderState> extends GeoRenderLayer<T, Void, R> {

    private static final Identifier CLOUD_TEXTURE = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/message_cloud.png");
    int order;

    public GemSupplyCloudLayer(GeoRenderer<T, Void, R> renderer, int order) {
        super(renderer);
        this.order = order;
    }


    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        if (!renderPassInfo.willRender()) {
            return;
        }

        GemRenderState renderState = (GemRenderState) renderPassInfo.renderState();

        if(renderState.supplyDisplayItems.isEmpty()){
            return;
        }

        renderTasks.order(order).submitCustomGeometry(
                renderPassInfo.poseStack(),
                RenderTypes.entityTranslucentEmissive(CLOUD_TEXTURE),
                (pose, vertexConsumer) -> {
                    PoseStack poseStack = renderPassInfo.poseStack();
                    poseStack.pushPose();
                    poseStack.last().set(pose);

                    float bodyYaw = renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_BODY_YAW, 0f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(bodyYaw - 180f));

                    poseStack.translate(0.0F, renderState.height + renderState.modelSize * 0.4f, 0.0F);
                    poseStack.mulPose(renderPassInfo.cameraState().orientation);
                    poseStack.scale(renderState.modelSize * 0.8f, renderState.modelSize * 0.8f, renderState.modelSize * 0.8f);

                    PoseStack.Pose currentPose = poseStack.last();
                    int light = 0xF000F0;

                    vertexConsumer.addVertex(currentPose, -0.5F, -0.5F, 0.0F)
                            .setColor(255, 255, 255, 255)
                            .setUv(0.0F, 1.0F)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(light)
                            .setNormal(0.0F, 0.0F, -1.0F);

                    vertexConsumer.addVertex(currentPose, 0.5F, -0.5F, 0.0F)
                            .setColor(255, 255, 255, 255)
                            .setUv(1.0F, 1.0F)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(light)
                            .setNormal(0.0F, 0.0F, -1.0F);

                    vertexConsumer.addVertex(currentPose, 0.5F, 0.5F, 0.0F)
                            .setColor(255, 255, 255, 255)
                            .setUv(1.0F, 0.0F)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(light)
                            .setNormal(0.0F, 0.0F, -1.0F);

                    vertexConsumer.addVertex(currentPose, -0.5F, 0.5F, 0.0F)
                            .setColor(255, 255, 255, 255)
                            .setUv(0.0F, 0.0F)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(light)
                            .setNormal(0.0F, 0.0F, -1.0F);

                    poseStack.popPose();
                }
        );


        PoseStack itemPoseStack = new PoseStack();
        itemPoseStack.last().set(renderPassInfo.poseStack().last());

        float bodyYaw = renderState.getOrDefaultGeckolibData(DataTickets.ENTITY_BODY_YAW, 0f);
        itemPoseStack.mulPose(Axis.YP.rotationDegrees(bodyYaw - 180f));

        itemPoseStack.translate(0.0F, renderState.height + renderState.modelSize * 0.4f, 0.0F);
        itemPoseStack.mulPose(renderPassInfo.cameraState().orientation);
        itemPoseStack.scale(
                renderState.modelSize * 0.8f,
                renderState.modelSize * 0.8f,
                renderState.modelSize * 0.8f
        );
        itemPoseStack.translate(0.0F, 0.0F, 0.02F);
        itemPoseStack.scale(0.55F, 0.55F, 0.55F);

        renderState.supplyItemRenderState.submit(
                itemPoseStack,
                renderTasks,
                0xF000F0,
                OverlayTexture.NO_OVERLAY,
                0
        );
    }

}

