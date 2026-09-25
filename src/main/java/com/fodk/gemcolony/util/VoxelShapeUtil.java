package com.fodk.gemcolony.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelShapeUtil {

    public static VoxelShape rotateShape(VoxelShape shape, Direction facing) {
        if (facing == Direction.NORTH) {
            return shape;
        }

        VoxelShape rotated = Shapes.empty();

        for (AABB box : shape.toAabbs()) {
            AABB rotatedBox = switch (facing) {
                case EAST -> new AABB(
                        1.0 - box.maxZ,
                        box.minY,
                        box.minX,
                        1.0 - box.minZ,
                        box.maxY,
                        box.maxX
                );

                case SOUTH -> new AABB(
                        1.0 - box.maxX,
                        box.minY,
                        1.0 - box.maxZ,
                        1.0 - box.minX,
                        box.maxY,
                        1.0 - box.minZ
                );

                case WEST -> new AABB(
                        box.minZ,
                        box.minY,
                        1.0 - box.maxX,
                        box.maxZ,
                        box.maxY,
                        1.0 - box.minX
                );

                default -> box;
            };

            rotated = Shapes.or(
                    rotated,
                    Shapes.create(rotatedBox)
            );
        }

        return rotated;
    }
}
