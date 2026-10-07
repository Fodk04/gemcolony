package com.fodk.gemcolony.block.entity.client.renderer;

import com.fodk.gemcolony.block.custom.ShellBlock;
import com.fodk.gemcolony.block.entity.client.model.ShellModel;
import com.fodk.gemcolony.block.entity.client.renderstate.ShellRenderState;
import com.fodk.gemcolony.block.entity.custom.ShellBlockEntity;
import com.geckolib.renderer.GeoBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;


public class ShellRenderer extends GeoBlockRenderer<ShellBlockEntity, ShellRenderState> {

    public ShellRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new ShellModel());
    }

    @Override
    public AABB getRenderBoundingBox(ShellBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return new AABB(
                Vec3.atLowerCornerOf(pos).add(-1, 0, -1),
                Vec3.atLowerCornerOf(pos).add(2, 2, 2)
        );
    }

    @Override
    public ShellRenderState createRenderState() {
        return new ShellRenderState();
    }

    @Override
    public void extractRenderState(ShellBlockEntity blockEntity, ShellRenderState renderState, float partialTick, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay damageOverlayState) {
        super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, damageOverlayState);
    }

    @Override
    public void addRenderData(ShellBlockEntity blockEntity, Void relatedObject, ShellRenderState renderState, float partialTick) {
        renderState.addGeckolibData(ShellBlockEntity.DRAINING, blockEntity.isDraining());

        renderState.facing = blockEntity.getBlockState().getValue(ShellBlock.FACING);

        Minecraft.getInstance().getItemModelResolver().updateForTopItem(
                renderState.pearl,
                blockEntity.getItem(ShellBlockEntity.PEARL_SLOT),
                ItemDisplayContext.GROUND,
                blockEntity.getLevel(),
                null,
                0
        );
    }

    @Override
    public int getViewDistance() {
        return 32*32;
    }

    @Override
    public void submit(ShellRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(new Vec3(0.5D, 0.95D, 0.5D));
        if(state.facing == Direction.NORTH){
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
        }
        if(state.facing == Direction.SOUTH){
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
        }
        if(state.facing == Direction.EAST){
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        }
        if(state.facing == Direction.WEST){
            poseStack.mulPose(Axis.ZN.rotationDegrees(90));
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));

        state.pearl.submit(
                poseStack,
                submitNodeCollector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();

        super.submit(state, poseStack, submitNodeCollector, camera);
    }
}