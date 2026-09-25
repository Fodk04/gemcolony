package com.fodk.gemcolony.construction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
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

            AABB box = getComponentBox(
                    assembly,
                    component,
                    assemblyRotation,
                    origin
            );

            int minX = (int) Math.floor(box.minX);
            int maxX = (int) Math.ceil(box.maxX);

            int minY = (int) Math.floor(box.minY);
            int maxY = (int) Math.ceil(box.maxY);

            int minZ = (int) Math.floor(box.minZ);
            int maxZ = (int) Math.ceil(box.maxZ);

            for (int x = minX; x < maxX; x++) {
                for (int y = minY; y < maxY; y++) {
                    for (int z = minZ; z < maxZ; z++) {

                        BlockPos blockPos = new BlockPos(x, y, z);

                        if (!level.getBlockState(blockPos).canBeReplaced()) {
                            return false;
                        }
                    }
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
