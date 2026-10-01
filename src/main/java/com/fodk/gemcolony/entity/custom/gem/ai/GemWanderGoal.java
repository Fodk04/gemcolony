package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class GemWanderGoal extends RandomStrollGoal {

    private final GemEntity gem;

    public GemWanderGoal(GemEntity gem, double speed) {
        super(gem, speed);
        this.gem = gem;
        this.setInterval(30);
    }

    @Override
    public boolean canUse() {
        return gem.getBehavior() == GemBehavior.WANDER
                && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return gem.getBehavior() == GemBehavior.WANDER
                && super.canContinueToUse();
    }
}
