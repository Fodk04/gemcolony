package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class GemGuardGoal extends Goal {

    private final GemEntity gem;
    private final double speed;

    public GemGuardGoal(GemEntity gem, double speed) {
        this.gem = gem;
        this.speed = speed;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
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

        double distance = gem.distanceToSqr(workPos.getX() + 0.5D, workPos.getY(), workPos.getZ() + 0.5D);

        return distance > 1.5D * 1.5D;
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

        double distance = gem.distanceToSqr(workPos.getX() + 0.5D, workPos.getY(), workPos.getZ() + 0.5D);

        return distance > 1.5D * 1.5D;
    }

    @Override
    public void start() {
        BlockPos workPos = gem.getWorkPos();

        if (workPos != null) {
            gem.getNavigation().moveTo(workPos.getX() + 0.5D, workPos.getY(), workPos.getZ() + 0.5D, speed);
        }
    }

    @Override
    public void tick() {
        BlockPos workPos = gem.getWorkPos();

        if (workPos == null) {
            return;
        }

        double distance = gem.distanceToSqr(workPos.getX() + 0.5D, workPos.getY(), workPos.getZ() + 0.5D);
        if (distance <= 1.5D * 1.5D) {
            gem.getNavigation().stop();
            return;
        }

        gem.getNavigation().recomputePath();
        gem.getNavigation().moveTo(workPos.getX() + 0.5D, workPos.getY(), workPos.getZ() + 0.5D, speed);
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();
    }
}
