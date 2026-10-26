package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumSet;

public class KindergartnerAnalysisGoal extends Goal {
    private final PeridotEntity gem;
    private final double speed;

    private boolean sampling = false;

    private int samplingTicks = 0;
    private static final int SAMPLING_DURATION = 20;

    public KindergartnerAnalysisGoal(PeridotEntity gem, double speed) {
        this.gem = gem;
        this.speed = speed;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return gem.isAnalysing();
    }

    @Override
    public boolean canContinueToUse() {
        return gem.isAnalysing();
    }

    @Override
    public void start() {
        sampling = false;
        chooseNewDestination();
    }

    @Override
    public void tick() {
        if (sampling) {
            samplingTicks++;

            if (samplingTicks >= SAMPLING_DURATION) {
                sampling = false;
                samplingTicks = 0;

                gem.sampleAnalysisPosition(gem.blockPosition());

                chooseNewDestination();
            }

            return;
        }

        if (gem.getNavigation().isDone()) {
            sampling = true;
            samplingTicks = 0;

            gem.getNavigation().stop();

            gem.getLookControl().setLookAt(
                    gem.getX(),
                    gem.getY(),
                    gem.getZ(),
                    30.0F,
                    30.0F
            );

            gem.triggerAnim("sampling_controller", "sample");
        }
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();sampling = false;}

    private void chooseNewDestination() {
        BlockPos center = gem.getAnalysisCenter();

        if (center == null) {
            return;
        }

        int radius = gem.getAnalysisRadius();

        int x = center.getX() + gem.getRandom().nextIntBetweenInclusive(-radius, radius);
        int z = center.getZ() + gem.getRandom().nextIntBetweenInclusive(-radius, radius);
        int y = gem.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

        BlockPos target = new BlockPos(x, y, z);

        gem.getNavigation().moveTo(
                target.getX(),
                target.getY(),
                target.getZ(),
                speed
        );

    }
}
