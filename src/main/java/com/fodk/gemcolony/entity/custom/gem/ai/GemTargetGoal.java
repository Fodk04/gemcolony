package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.util.GemCombatUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class GemTargetGoal extends Goal {

    private final GemEntity gem;
    private final double radius;

    public GemTargetGoal(GemEntity gem, double radius) {
        this.gem = gem;
        this.radius = radius;

        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        if (gem.getAggroTarget() != null && gem.getAggroTarget().isAlive()) {
            return false;
        }

        LivingEntity target = gem.level()
                .getEntitiesOfClass(LivingEntity.class, gem.getBoundingBox().inflate(radius), GemCombatUtil::isNaturallyHostile)
                .stream()
                .filter(entity -> GemCombatUtil.canReachTarget(gem, entity))
                .min((a, b) -> Double.compare(gem.distanceToSqr(a), gem.distanceToSqr(b)))
                .orElse(null);

        if (target == null) {
            return false;
        }

        GemCombatUtil.setAggroTarget(gem, target);
        return false;
    }
}
