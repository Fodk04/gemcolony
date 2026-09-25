package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.construction.*;
import com.fodk.gemcolony.entity.client.render.GemRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;

import java.util.ArrayList;
import java.util.List;

public class ConstructorRenderer {

    public static void render(SubmitCustomGeometryEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        Constructor constructor = ConstructorManager.get(minecraft.player);

        if (!constructor.isPlacing()) {
            return;
        }

        BlockPos pos = constructor.getPlacementPos();
        Assembly assembly = constructor.getSelectedAssembly();

        if (pos == null || assembly == null) {
            return;
        }

        for (AssemblyComponent component : assembly.components()) {

            Blueprint blueprint = component.blueprint();

            int x = component.x();
            int y = component.y();
            int z = component.z();

            int width = blueprint.width();
            int height = blueprint.height();
            int depth = blueprint.depth();

            int minX = x;
            int maxX = x + width;

            int minZ = z;
            int maxZ = z + depth;

            //apply the component's own rotation.
            switch (component.rotation()) {
                case CLOCKWISE_90 -> {
                    minX = x;
                    maxX = x + depth;
                    minZ = z - width + 1;
                    maxZ = z + 1;
                }

                case COUNTERCLOCKWISE_90 -> {
                    minX = x - depth + 1;
                    maxX = x + 1;
                    minZ = z;
                    maxZ = z + width;
                }

                case CLOCKWISE_180 -> {
                    minX = x - width + 1;
                    maxX = x + 1;
                    minZ = z - depth + 1;
                    maxZ = z + 1;
                }

                default -> {
                }
            }
            //rotate the entire component box around the assembly center.
            double centerX = assembly.centerX();
            double centerZ = assembly.centerZ();

            double rotatedMinX = Double.MAX_VALUE;
            double rotatedMaxX = -Double.MAX_VALUE;
            double rotatedMinZ = Double.MAX_VALUE;
            double rotatedMaxZ = -Double.MAX_VALUE;

            double[] xs = {minX, maxX};
            double[] zs = {minZ, maxZ};

            for (double cornerX : xs) {
                for (double cornerZ : zs) {

                    double relativeX = cornerX - centerX;
                    double relativeZ = cornerZ - centerZ;

                    double rotatedX;
                    double rotatedZ;

                    switch (constructor.getPlacementRotation()) {
                        case CLOCKWISE_90 -> {
                            rotatedX = centerX - relativeZ;
                            rotatedZ = centerZ + relativeX;
                        }

                        case CLOCKWISE_180 -> {
                            rotatedX = centerX - relativeX;
                            rotatedZ = centerZ - relativeZ;
                        }

                        case COUNTERCLOCKWISE_90 -> {
                            rotatedX = centerX + relativeZ;
                            rotatedZ = centerZ - relativeX;
                        }

                        default -> {
                            rotatedX = cornerX;
                            rotatedZ = cornerZ;
                        }
                    }

                    rotatedMinX = Math.min(rotatedMinX, rotatedX);
                    rotatedMaxX = Math.max(rotatedMaxX, rotatedX);
                    rotatedMinZ = Math.min(rotatedMinZ, rotatedZ);
                    rotatedMaxZ = Math.max(rotatedMaxZ, rotatedZ);
                }
            }

            AABB box = ConstructionPlacement.getComponentBox(
                    assembly,
                    component,
                    constructor.getPlacementRotation(),
                    pos
            );

            BlockState blockState = blueprint.block().defaultBlockState();

            if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                Direction facing = switch (component.rotation()) {
                    case CLOCKWISE_90 -> Direction.EAST;
                    case CLOCKWISE_180 -> Direction.SOUTH;
                    case COUNTERCLOCKWISE_90 -> Direction.WEST;
                    default -> Direction.NORTH;
                };

                facing = constructor.getPlacementRotation().rotate(facing);

                blockState = blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }

            BlockStateModel model =
                    minecraft.getModelManager()
                            .getBlockStateModelSet()
                            .get(blockState);

            List<BlockStateModelPart> parts = new ArrayList<>();
            RandomSource random = RandomSource.createThreadLocalInstance();

            random.setSeed(blockState.getSeed(pos));

            model.collectParts(
                    BlockAndTintGetter.EMPTY,
                    BlockPos.ZERO,
                    blockState,
                    random,
                    parts
            );

            PoseStack poseStack = event.getPoseStack();
            OrderedSubmitNodeCollector collector = event.getSubmitNodeCollector();

            Vec3 cameraPos = event.getLevelRenderState().cameraRenderState.pos;

            poseStack.pushPose();

            poseStack.translate(
                    pos.getX() - cameraPos.x,
                    pos.getY() - cameraPos.y,
                    pos.getZ() - cameraPos.z
            );

            AABB localBox = box.move(
                    -pos.getX(),
                    -pos.getY(),
                    -pos.getZ()
            );

            collector.submitShapeOutline(
                    poseStack,
                    Shapes.create(localBox),
                    RenderTypes.lines(),
                    0xFFFFFFFF,
                    1.0F,
                    false
            );

            BlockPos componentPos = ConstructionPlacement.rotatePosition(
                    BlockPos.ZERO,
                    component.x(),
                    component.y(),
                    component.z(),
                    constructor.getPlacementRotation(),
                    assembly.centerX(),
                    assembly.centerZ()
            );

            poseStack.translate(
                    componentPos.getX(),
                    componentPos.getY(),
                    componentPos.getZ()
            );

            collector.submitBlockModel(
                    poseStack,
                    GemRenderTypes.constructorGhost(),
                    List.copyOf(parts),
                    new int[0],
                    minecraft.level.getBrightness(LightLayer.BLOCK, pos) << 4
                            | minecraft.level.getBrightness(LightLayer.SKY, pos) << 20,
                    OverlayTexture.NO_OVERLAY,
                    0
            );

            poseStack.popPose();
        }
    }
}
