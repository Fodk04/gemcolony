package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.util.GemCombatUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GemAttackGoal extends MeleeAttackGoal {

    private final GemEntity gem;
    private int pathCheckCooldown;

    public GemAttackGoal(GemEntity gem, double speed) {
        super(gem, speed, true);
        this.gem = gem;
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() == GemBehavior.STAY) {
            return false;
        }

        LivingEntity target = gem.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (gem.getBehavior() == GemBehavior.STAY) {
            return false;
        }

        LivingEntity target = gem.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        if (!GemCombatUtil.canAttack(gem, target)) {
            return false;
        }

        if (pathCheckCooldown-- <= 0) {
            pathCheckCooldown = 10;

            if (!GemCombatUtil.canReachTarget(gem, target)) {
                return false;
            }
        }

        return super.canContinueToUse();
    }

    @Override
    public void stop() {
        super.stop();
        gem.setTarget(null);
    }

    @Override
    public void tick() {
        LivingEntity target = gem.getTarget();

        if (target == null || !target.isAlive()) {
            gem.getNavigation().stop();
            return;
        }

        gem.getNavigation().recomputePath();

        super.tick();
    }
}
