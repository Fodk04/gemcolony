package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.util.GemCombatUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.level.pathfinder.Path;

import java.util.Comparator;
import java.util.EnumSet;

public class GemTargetGoal extends TargetGoal {

    private final GemEntity gem;
    private final double radius;

    public GemTargetGoal(GemEntity gem, double radius) {
        super(gem, false);
        this.gem = gem;
        this.radius = radius;
    }

    @Override
    public boolean canUse() {
        if (gem.getTarget() != null && gem.getTarget().isAlive()) {
            return false;
        }

        LivingEntity target = gem.level()
                .getEntitiesOfClass(LivingEntity.class, gem.getBoundingBox().inflate(radius),
                        entity -> GemCombatUtil.isNaturallyHostile(entity)
                                && GemCombatUtil.canAttack(gem, entity)
                                && GemCombatUtil.canReachTarget(gem, entity))
                .stream()
                .min(Comparator.comparingDouble(gem::distanceToSqr))
                .orElse(null);

        if (target == null) {
            return false;
        }

        this.targetMob = target;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.targetMob;

        return target != null
                && target.isAlive()
                && GemCombatUtil.canAttack(gem, target)
                && gem.distanceToSqr(target) <= radius * radius;
    }

    @Override
    public void start() {
        gem.setTarget(this.targetMob);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();

        if (gem.getTarget() == this.targetMob) {
            gem.setTarget(null);
        }

        this.targetMob = null;
    }
}
