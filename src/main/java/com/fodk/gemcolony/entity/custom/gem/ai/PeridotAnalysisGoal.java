package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animation.AnimationController;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumSet;

public class PeridotAnalysisGoal extends Goal {
    private final PeridotEntity peridot;
    private final double speed;

    private boolean sampling = false;

    private int samplingTicks = 0;
    private static final int SAMPLING_DURATION = 20;

    public PeridotAnalysisGoal(PeridotEntity peridot, double speed) {
        this.peridot = peridot;
        this.speed = speed;

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return peridot.isAnalysing();
    }

    @Override
    public boolean canContinueToUse() {
        return peridot.isAnalysing();
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

                peridot.sampleAnalysisPosition(
                        peridot.blockPosition()
                );

                chooseNewDestination();
            }

            return;
        }

        if (peridot.getNavigation().isDone()) {
            sampling = true;
            samplingTicks = 0;

            peridot.getNavigation().stop();

            peridot.triggerAnim("sampling_controller", "sample");
        }
    }

    @Override
    public void stop() {peridot.getNavigation().stop();sampling = false;}

    private void chooseNewDestination() {
        BlockPos center = peridot.getAnalysisCenter();

        if (center == null) {
            return;
        }

        int radius = peridot.getAnalysisRadius();

        int x = center.getX() + peridot.getRandom().nextIntBetweenInclusive(-radius, radius);

        int z = center.getZ() + peridot.getRandom().nextIntBetweenInclusive(-radius, radius);

        int y = peridot.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

        BlockPos target = new BlockPos(x, y, z);

        peridot.getNavigation().moveTo(
                target.getX(),
                target.getY(),
                target.getZ(),
                speed
        );

    }
}
