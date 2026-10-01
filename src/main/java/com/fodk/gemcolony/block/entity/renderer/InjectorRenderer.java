package com.fodk.gemcolony.block.entity.renderer;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public class InjectorRenderer implements BlockEntityRenderer<InjectorBlockEntity, InjectorRenderState> {

    private static final Identifier ESSENCE_TEXTURE = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/block/essence_still.png");

    private static final RenderType ESSENCE_RENDER_TYPE =
            RenderType.create(
                    "injector_essence",
                    RenderSetup.builder(RenderPipelines.TRANSLUCENT_BLOCK)
                            .useLightmap()
                            .withTexture("Sampler0", ESSENCE_TEXTURE)
                            .createRenderSetup()
            );

    public InjectorRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public InjectorRenderState createRenderState() {
        return new InjectorRenderState();
    }

    @Override
    public AABB getRenderBoundingBox(InjectorBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();

        return new AABB(
                pos.getX() - 1,
                pos.getY(),
                pos.getZ() - 1,
                pos.getX() + 2,
                pos.getY() + 8,
                pos.getZ() + 2
        );
    }

    @Override
    public void submit(InjectorRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        if (!state.isComplete){
            return;
        }
        if (state.essenceAmount <= 0) {
            return;
        }

        float fill = Math.min(state.essenceAmount / (float) state.essenceCapacity, 1.0F);

        poseStack.pushPose();

        // Center of the 3x3 tank, relative to the Drill block entity.
        poseStack.translate(0, 2.0, 0);

        collector.submitCustomGeometry(
                poseStack,
                ESSENCE_RENDER_TYPE,
                (pose, vertexConsumer) -> {
                    int color = 0xFF000000 | state.essenceColor;

                    float minX = -0.9375F;
                    float maxX = 1.9375F;
                    float minY = 0.0625F;
                    float maxY = 5.9375F * fill;
                    float minZ = -0.9375F;
                    float maxZ = 1.9375F;

                    Matrix4f matrix = pose.pose();

                    addCube(
                            vertexConsumer,
                            matrix,
                            minX, minY, minZ,
                            maxX, maxY, maxZ,
                            color,
                            state.lightCoords
                    );
                }
        );

        poseStack.popPose();
    }

    @Override
    public void extractRenderState(InjectorBlockEntity blockEntity, InjectorRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderState.extractBase(blockEntity, state, breakProgress);
        state.essenceAmount = blockEntity.getTotalEssenceAmount();
        state.essenceColor = blockEntity.getEssenceColor();
        state.essenceCapacity = blockEntity.getTotalEssenceCapacity();
        state.isComplete = blockEntity.isComplete();
    }

    private static void addCube(
            VertexConsumer consumer,
            Matrix4f matrix,
            float minX, float minY, float minZ,
            float maxX, float maxY, float maxZ,
            int color,
            int light
    ) {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // TOP
        addFace(consumer, matrix,
                minX, maxY, minZ,
                minX, maxY, maxZ,
                maxX, maxY, maxZ,
                maxX, maxY, minZ,
                r, g, b, a, light);

        // BOTTOM
        addFace(consumer, matrix,
                minX, minY, minZ,
                maxX, minY, minZ,
                maxX, minY, maxZ,
                minX, minY, maxZ,
                r, g, b, a, light);

        // NORTH (-Z)
        addFace(consumer, matrix,
                minX, minY, minZ,
                minX, maxY, minZ,
                maxX, maxY, minZ,
                maxX, minY, minZ,
                r, g, b, a, light);

        // SOUTH (+Z)
        addFace(consumer, matrix,
                maxX, minY, maxZ,
                maxX, maxY, maxZ,
                minX, maxY, maxZ,
                minX, minY, maxZ,
                r, g, b, a, light);

        // WEST (-X)
        addFace(consumer, matrix,
                minX, minY, maxZ,
                minX, maxY, maxZ,
                minX, maxY, minZ,
                minX, minY, minZ,
                r, g, b, a, light);

        // EAST (+X)
        addFace(consumer, matrix,
                maxX, minY, minZ,
                maxX, maxY, minZ,
                maxX, maxY, maxZ,
                maxX, minY, maxZ,
                r, g, b, a, light);
    }

    private static void addFace(
            VertexConsumer consumer,
            Matrix4f matrix,
            float x1, float y1, float z1,
            float x2, float y2, float z2,
            float x3, float y3, float z3,
            float x4, float y4, float z4,
            int r, int g, int b, int a, int light) {
        vertex(consumer, matrix, x1, y1, z1, 0.0F, 0.0F, r, g, b, a, light);
        vertex(consumer, matrix, x2, y2, z2, 1.0F, 0.0F, r, g, b, a, light);
        vertex(consumer, matrix, x3, y3, z3, 1.0F, 1.0F, r, g, b, a, light);
        vertex(consumer, matrix, x4, y4, z4, 0.0F, 1.0F, r, g, b, a, light);
    }

    private static void vertex(
            VertexConsumer consumer,
            Matrix4f matrix,
            float x, float y, float z,
            float u, float v,
            int r, int g, int b, int a,
            int light
    ) {
        consumer.addVertex(matrix, x, y, z)
                .setColor(r, g, b, a)
                .setUv(u, v)
                .setLight(light);
    }
}
