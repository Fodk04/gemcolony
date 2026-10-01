package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GemStayGoal extends Goal {

    private final GemEntity gem;

    public GemStayGoal(GemEntity gem) {
        this.gem = gem;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return gem.getBehavior() == GemBehavior.STAY;
    }

    @Override
    public boolean canContinueToUse() {
        return gem.getBehavior() == GemBehavior.STAY;
    }

    @Override
    public void start() {
        gem.getNavigation().stop();
    }

    @Override
    public void tick() {
        gem.getNavigation().stop();
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();
    }
}
