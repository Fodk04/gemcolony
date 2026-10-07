package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GemFollowGoal extends Goal {

    private final GemEntity gem;
    private final double speed;
    private final float startDistance;
    private final float stopDistance;

    private Entity owner;

    public GemFollowGoal(GemEntity gem, double speed, float startDistance, float stopDistance) {
        this.gem = gem;
        this.speed = speed;
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() != GemBehavior.FOLLOW) {
            return false;
        }

        owner = gem.getOwnerEntity();

        if (owner == null || !owner.isAlive()) {
            return false;
        }

        return gem.distanceToSqr(owner) > startDistance * startDistance;
    }

    @Override
    public boolean canContinueToUse() {
        if (gem.getBehavior() != GemBehavior.FOLLOW) {
            return false;
        }

        return owner != null && owner.isAlive();
    }

    @Override
    public void start() {
        gem.getNavigation().moveTo(owner, speed);
    }

    @Override
    public void tick() {
        if (owner == null) {
            return;
        }

        double distance = gem.distanceToSqr(owner);

        if (distance > 64.0 * 64.0) {
            Vec3 start = owner.position();

            Vec3 end = new Vec3(owner.getX(), gem.level().getMinY(), owner.getZ());
            BlockHitResult hit = gem.level().clip(
                    new ClipContext(
                            start,
                            end,
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.ANY,
                            gem
                    )
            );

            if (hit.getType() == HitResult.Type.BLOCK) {
                Vec3 hitPos = hit.getLocation();

                gem.teleportTo(owner.getX(), hitPos.y + 1, owner.getZ());
            } else {
                gem.teleportTo(owner.getX(), owner.getY(), owner.getZ());
            }

            gem.getNavigation().stop();
            return;
        }

        gem.getLookControl().setLookAt(owner, 10.0F, gem.getMaxHeadXRot());

        if (distance <= stopDistance * stopDistance) {
            gem.getNavigation().stop();
            return;
        }

        gem.getNavigation().moveTo(owner, speed);
    }

    @Override
    public void stop() {
        gem.getNavigation().stop();
        owner = null;
    }
}
