package com.fodk.gemcolony.construction;

import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class ConstructionPlacement {

    public static BlockPos rotatePosition(BlockPos origin, int x, int y, int z, Rotation rotation, double centerX, double centerZ) {

        double relativeX = x - centerX;
        double relativeZ = z - centerZ;

        double rotatedX;
        double rotatedZ;

        switch (rotation) {
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
                rotatedX = x;
                rotatedZ = z;
            }
        }

        return origin.offset(
                (int) Math.round(rotatedX),
                y,
                (int) Math.round(rotatedZ)
        );
    }

    public static boolean canPlaceAssembly(Level level, Assembly assembly, BlockPos origin, Rotation assemblyRotation) {
        for (AssemblyComponent component : assembly.components()) {

            BlockPos componentPos = rotatePosition(
                    origin,
                    component.x(),
                    component.y(),
                    component.z(),
                    assemblyRotation,
                    assembly.centerX(),
                    assembly.centerZ()
            );

            BlockState blockState = component.blueprint().block().defaultBlockState();

            if (blockState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {

                Direction facing = switch (component.rotation()) {
                    case CLOCKWISE_90 -> Direction.EAST;
                    case CLOCKWISE_180 -> Direction.SOUTH;
                    case COUNTERCLOCKWISE_90 -> Direction.WEST;
                    default -> Direction.NORTH;
                };

                facing = assemblyRotation.rotate(facing);
                blockState = blockState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }

            if (blockState.getBlock() instanceof ConstructedMultiblock multiblock) {

                blockState = multiblock.getConstructionState(level, componentPos, blockState);
                if (!multiblock.canPlace(level, componentPos, blockState)) {
                    return false;
                }
            } else {
                if (!level.getBlockState(componentPos).canBeReplaced()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static AABB getComponentBox(Assembly assembly, AssemblyComponent component, Rotation assemblyRotation, BlockPos origin) {
        Blueprint blueprint = component.blueprint();

        double minX = blueprint.offsetX();
        double minY = blueprint.offsetY();
        double minZ = blueprint.offsetZ();

        double maxX = minX + blueprint.width();
        double maxY = minY + blueprint.height();
        double maxZ = minZ + blueprint.depth();

        // Apply the componentown rotation around its anchor
        double rotatedMinX = Double.MAX_VALUE;
        double rotatedMaxX = -Double.MAX_VALUE;
        double rotatedMinZ = Double.MAX_VALUE;
        double rotatedMaxZ = -Double.MAX_VALUE;

        double[] xs = {minX, maxX};
        double[] zs = {minZ, maxZ};

        for (double x : xs) {
            for (double z : zs) {

                double rotatedX;
                double rotatedZ;

                double pivotX = 0.5;
                double pivotZ = 0.5;

                double relativeX = x - pivotX;
                double relativeZ = z - pivotZ;

                switch (component.rotation()) {
                    case CLOCKWISE_90 -> {
                        rotatedX = pivotX - relativeZ;
                        rotatedZ = pivotZ + relativeX;
                    }

                    case CLOCKWISE_180 -> {
                        rotatedX = pivotX - relativeX;
                        rotatedZ = pivotZ - relativeZ;
                    }

                    case COUNTERCLOCKWISE_90 -> {
                        rotatedX = pivotX + relativeZ;
                        rotatedZ = pivotZ - relativeX;
                    }

                    default -> {
                        rotatedX = x;
                        rotatedZ = z;
                    }
                }

                rotatedMinX = Math.min(rotatedMinX, rotatedX);
                rotatedMaxX = Math.max(rotatedMaxX, rotatedX);
                rotatedMinZ = Math.min(rotatedMinZ, rotatedZ);
                rotatedMaxZ = Math.max(rotatedMaxZ, rotatedZ);
            }
        }

        // put the component at its assembly anchor
        double componentX = component.x();
        double componentZ = component.z();

        rotatedMinX += componentX;
        rotatedMaxX += componentX;
        rotatedMinZ += componentZ;
        rotatedMaxZ += componentZ;

        // rotate the entire component around the assembly center
        double centerX = assembly.centerX() + 0.5;
        double centerZ = assembly.centerZ() + 0.5;

        double finalMinX = Double.MAX_VALUE;
        double finalMaxX = -Double.MAX_VALUE;
        double finalMinZ = Double.MAX_VALUE;
        double finalMaxZ = -Double.MAX_VALUE;

        double[] componentXs = {rotatedMinX, rotatedMaxX};
        double[] componentZs = {rotatedMinZ, rotatedMaxZ};

        for (double x : componentXs) {
            for (double z : componentZs) {

                double relativeX = x - centerX;
                double relativeZ = z - centerZ;

                double rotatedX;
                double rotatedZ;

                switch (assemblyRotation) {
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
                        rotatedX = x;
                        rotatedZ = z;
                    }
                }

                finalMinX = Math.min(finalMinX, rotatedX);
                finalMaxX = Math.max(finalMaxX, rotatedX);
                finalMinZ = Math.min(finalMinZ, rotatedZ);
                finalMaxZ = Math.max(finalMaxZ, rotatedZ);
            }
        }

        return new AABB(
                origin.getX() + finalMinX,
                origin.getY() + component.y() + minY,
                origin.getZ() + finalMinZ,
                origin.getX() + finalMaxX,
                origin.getY() + component.y() + maxY,
                origin.getZ() + finalMaxZ
        );
    }
}
