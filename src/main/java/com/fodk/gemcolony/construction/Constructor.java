package com.fodk.gemcolony.construction;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Constructor {

    private BlockPos placementPos;
    private Rotation placementRotation = Rotation.NONE;

    private StarterGemEntity gem;

    private Assembly selectedAssembly;

    public Assembly getSelectedAssembly() {
        return selectedAssembly;
    }

    public void setSelectedAssembly(Assembly assembly) {
        this.selectedAssembly = assembly;
    }

    public StarterGemEntity getGem() {
        return gem;
    }

    public boolean canBuild(StarterGemEntity gem) {
        return selectedAssembly != null && gem.canConstruct(selectedAssembly);
    }

    public boolean startConstruction(StarterGemEntity gem) {
        if (!canBuild(gem)) {
            return false;
        }
        return true;
    }

    public BlockPos getPlacementPos() {
        return placementPos;
    }

    public void setPlacementPos(BlockPos placementPos) {
        this.placementPos = placementPos;
    }

    public Rotation getPlacementRotation() {
        return placementRotation;
    }

    public void rotatePlacement() {
        placementRotation = placementRotation.getRotated(Rotation.CLOCKWISE_90);

        System.out.println("Constructor rotation: " + placementRotation);
    }

    public void raisePlacement() {
        if (placementPos != null) {
            placementPos = placementPos.above();
        }
    }

    public void lowerPlacement() {
        if (placementPos != null) {
            placementPos = placementPos.below();
        }
    }

    public void moveNorth() {
        if (placementPos != null) {
            placementPos = placementPos.north();
        }
    }

    public void moveSouth() {
        if (placementPos != null) {
            placementPos = placementPos.south();
        }
    }

    public void moveEast() {
        if (placementPos != null) {
            placementPos = placementPos.east();
        }
    }

    public void moveWest() {
        if (placementPos != null) {
            placementPos = placementPos.west();
        }
    }

    public void moveForward(Player player) {
        if (placementPos != null) {
            placementPos = placementPos.relative(player.getDirection());
        }
    }

    public void moveBackward(Player player) {
        if (placementPos != null) {
            placementPos = placementPos.relative(player.getDirection().getOpposite());
        }
    }

    public void moveLeft(Player player) {
        if (placementPos != null) {
            placementPos = placementPos.relative(player.getDirection().getCounterClockWise());
        }
    }

    public void moveRight(Player player) {
        if (placementPos != null) {
            placementPos = placementPos.relative(player.getDirection().getClockWise());
        }
    }

    public void beginPlacement(Assembly assembly, BlockPos position) {
        this.selectedAssembly = assembly;
        this.placementPos = position;
        this.placementRotation = Rotation.NONE;
    }

    public void beginPlacement(Assembly assembly, StarterGemEntity gem, Player player) {
        this.selectedAssembly = assembly;
        this.gem = gem;
        this.placementPos = player.blockPosition().relative(player.getDirection());
        this.placementRotation = Rotation.NONE;
    }

    public void cancelPlacement() {
        selectedAssembly = null;
        placementPos = null;
        placementRotation = Rotation.NONE;
    }

    public boolean isPlacing() {
        return selectedAssembly != null && placementPos != null;
    }


    public BlockPos rotatePosition(BlockPos relativePosition) {
        // Injector is currently built around a 7x7 footprint.
        // The center is between blocks 3 and 4.
        double centerX = 3.5;
        double centerZ = 3.5;

        double x = relativePosition.getX() - centerX;
        double z = relativePosition.getZ() - centerZ;

        return switch (placementRotation) {
            case CLOCKWISE_90 -> new BlockPos(
                    (int) Math.round(centerX - z),
                    relativePosition.getY(),
                    (int) Math.round(centerZ + x)
            );

            case CLOCKWISE_180 -> new BlockPos(
                    (int) Math.round(centerX - x),
                    relativePosition.getY(),
                    (int) Math.round(centerZ - z)
            );

            case COUNTERCLOCKWISE_90 -> new BlockPos(
                    (int) Math.round(centerX + z),
                    relativePosition.getY(),
                    (int) Math.round(centerZ - x)
            );

            default -> relativePosition;
        };
    }
}
