package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.client.model.BubbleModel;
import com.fodk.gemcolony.entity.client.renderstate.BubbleRenderState;
import com.fodk.gemcolony.entity.custom.gem.bubble.BubbleEntity;
import com.geckolib.renderer.GeoEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class BubbleRenderer extends GeoEntityRenderer<BubbleEntity, BubbleRenderState> {

    private final ItemModelResolver itemModelResolver;

    public BubbleRenderer(EntityRendererProvider.Context context) {
        super(context, new BubbleModel());
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public BubbleRenderState createRenderState(BubbleEntity animatable, @Nullable Void relatedObject) {
        return new BubbleRenderState();
    }

    @Override
    public void extractRenderState(BubbleEntity entity, BubbleRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        this.itemModelResolver.updateForNonLiving(
                state.item,
                entity.getItem(),
                ItemDisplayContext.GROUND,
                entity
        );

        state.yRot = entity.getYRot();
    }

    @Override
    public RenderType getRenderType(BubbleRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }

    @Override
    public void submit(BubbleRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();

        poseStack.translate(0, 0.2F, 0);
        poseStack.scale(1F, 1F, 1F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-state.yRot));

        state.item.submit(
                poseStack,
                submitNodeCollector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor
        );

        poseStack.popPose();

        poseStack.scale(0.8F, 0.8F, 0.8F);

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public int getRenderColor(BubbleEntity animatable, @Nullable Void relatedObject, float partialTick) {
        ItemStack stack = animatable.getItem();

        if (stack.has(ModDataComponents.BUBBLE_COLOR)) {
            return 0xFF000000 | stack.get(ModDataComponents.BUBBLE_COLOR);
        }

        return 0xFFFFFFFF;
    }
}
