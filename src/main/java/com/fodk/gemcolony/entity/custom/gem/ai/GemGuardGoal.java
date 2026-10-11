package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;


public class GemGuardGoal extends Goal {

    private static final double MAX_GUARD_DISTANCE = 2.0D;

    private final GemEntity gem;
    private final double speed;

    public GemGuardGoal(GemEntity gem, double speed) {
        this.gem = gem;
        this.speed = speed;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private double distanceToWorkPosSqr(BlockPos workPos) {
        double targetX = workPos.getX() + 0.5D;
        double targetZ = workPos.getZ() + 0.5D;

        double dx = gem.getX() - targetX;
        double dz = gem.getZ() - targetZ;

        return dx * dx + dz * dz;
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() != GemBehavior.WORK) {
            return false;
        }

        BlockPos workPos = gem.getWorkPos();

        if (workPos == null) {
            return false;
        }

        return distanceToWorkPosSqr(workPos) > MAX_GUARD_DISTANCE * MAX_GUARD_DISTANCE;
    }

    @Override
    public boolean canContinueToUse() {
        if (gem.getBehavior() != GemBehavior.WORK) {
            return false;
        }

        BlockPos workPos = gem.getWorkPos();

        if (workPos == null) {
            return false;
        }

        return distanceToWorkPosSqr(workPos) > MAX_GUARD_DISTANCE * MAX_GUARD_DISTANCE;
    }

    @Override
    public void start() {
        moveToWorkPos();
    }

    @Override
    public void tick() {
        BlockPos workPos = gem.getWorkPos();

        if (workPos == null) {
            return;
        }

        if (distanceToWorkPosSqr(workPos) <= MAX_GUARD_DISTANCE * MAX_GUARD_DISTANCE) {
            gem.getNavigation().stop();
            return;
        }

        gem.getNavigation().recomputePath();
        moveToWorkPos();
    }

    private void moveToWorkPos() {
        BlockPos workPos = gem.getWorkPos();

        if (workPos == null) {
            return;
        }

        gem.getNavigation().moveTo(
                workPos.getX() + 0.5D,
                workPos.getY(),
                workPos.getZ() + 0.5D,
                speed
        );
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();
    }
}
