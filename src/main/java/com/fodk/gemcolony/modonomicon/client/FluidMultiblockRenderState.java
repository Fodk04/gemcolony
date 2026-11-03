package com.fodk.gemcolony.modonomicon.client;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.klikli_dev.modonomicon.api.multiblock.Multiblock.SimulateResult;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public record FluidMultiblockRenderState(
        Multiblock multiblock,
        Collection<SimulateResult> simulateResults,
        Vec3i size,
        float rotationTime,
        Rotation facingRotation,
        @Nullable BlockPos highlightPos,
        Map<BlockPos, BlockEntity> blockEntityCache,
        Set<BlockEntity> erroredBlockEntities,
        RandomSource randomSource,
        int x0,
        int y0,
        int x1,
        int y1,
        float scale,
        @Nullable ScreenRectangle scissorArea,
        @Nullable ScreenRectangle bounds
) implements PictureInPictureRenderState {

    public FluidMultiblockRenderState(
            Multiblock multiblock,
            Collection<SimulateResult> simulateResults,
            Vec3i size,
            float rotationTime,
            Rotation facingRotation,
            @Nullable BlockPos highlightPos,
            Map<BlockPos, BlockEntity> blockEntityCache,
            Set<BlockEntity> erroredBlockEntities,
            RandomSource randomSource,
            int x0,
            int y0,
            int x1,
            int y1,
            float scale,
            @Nullable ScreenRectangle scissorArea
    ) {
        this(
                multiblock,
                simulateResults,
                size,
                rotationTime,
                facingRotation,
                highlightPos,
                blockEntityCache,
                erroredBlockEntities,
                randomSource,
                x0,
                y0,
                x1,
                y1,
                scale,
                scissorArea,
                PictureInPictureRenderState.getBounds(x0, y0, x1, y1, scissorArea)
        );
    }
}
