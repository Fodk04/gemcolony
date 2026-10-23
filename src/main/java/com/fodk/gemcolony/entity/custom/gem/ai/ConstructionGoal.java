package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import com.fodk.gemcolony.construction.ConstructionRequirement;
import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ConstructionGoal extends Goal {
    private static final int SEARCH_INTERVAL = 20;
    private static final int SEARCH_RADIUS = 32;

    private final StarterGemEntity gem;

    private BlockPos targetPos;
    private BlockPos controllerPos;
    private BlockPos approachPos;

    private int searchCooldown;
    private int particleCooldown;
    private int constructionAnimationCooldown;

    public ConstructionGoal(StarterGemEntity gem) {
        this.gem = gem;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() != GemBehavior.WORK || gem.getWorkPos() == null) {
            return false;
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }

        searchCooldown = SEARCH_INTERVAL;
        targetPos = null;
        controllerPos = null;
        approachPos = null;
        targetPos = findClosestUnfinishedBlock();

        if (targetPos == null) {
            return false;
        }

        Level level = gem.level();
        BlockState state = level.getBlockState(targetPos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            targetPos = null;
            controllerPos = null;
            return false;
        }

        controllerPos = multiblock.getController(level, targetPos, state);
        approachPos = multiblock.getConstructionApproachPosition(level, targetPos, state);

        if (controllerPos == null) {
            targetPos = null;
            approachPos = null;
            return false;
        }

        if (!gem.canAffordConstruction(targetPos)) {
            Map<Item, Integer> required = new HashMap<>();

            for (ConstructionRequirement requirement : multiblock.getConstructionRequirements()) {
                required.merge(requirement.item(), requirement.count(), Integer::sum);
            }

            if (!required.isEmpty()) {
                gem.requestSupplies(controllerPos, required);
            }

            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (gem.getBehavior() != GemBehavior.WORK || gem.getWorkPos() == null) {
            return false;
        }

        if (targetPos == null || controllerPos == null || isFinished(targetPos)) {
            return false;
        }

        Level level = gem.level();
        BlockState targetState = level.getBlockState(targetPos);

        if (!(targetState.getBlock() instanceof ConstructedMultiblock multiblock)) {
            return false;
        }

        BlockPos currentController = multiblock.getController(level, targetPos, targetState);

        if (currentController == null || !currentController.equals(controllerPos)) {
            return false;
        }

        return gem.distanceToSqr(
                controllerPos.getX() + 0.5,
                controllerPos.getY() + 0.5,
                controllerPos.getZ() + 0.5) <= (SEARCH_RADIUS * SEARCH_RADIUS * 4);
    }

    @Override
    public void start() {
        moveToTarget();
    }

    @Override
    public void tick() {
        if (targetPos == null) {
            return;
        }

        if (isTouchingController()) {
            gem.getNavigation().stop();

            if (particleCooldown <= 0) {
                spawnConstructionParticles();
                particleCooldown = 40;
            } else {
                particleCooldown--;
            }

            if (constructionAnimationCooldown <= 0) {
                gem.playRandomConstruct();
                constructionAnimationCooldown = 30;
            } else {
                constructionAnimationCooldown--;
            }

            if (!gem.tryBuildBlock(targetPos) || isFinished(targetPos)) {
                targetPos = null;
                controllerPos = null;
            }

            return;
        }

        nudgeTowardController();

        if (!gem.getNavigation().isDone()) {
            return;
        }

        moveToTarget();
    }

    private boolean isTouchingController() {
        if (controllerPos == null) {
            return false;
        }

        Level level = gem.level();
        BlockState state = level.getBlockState(controllerPos);

        AABB targetBox = state.getCollisionShape(level, controllerPos).bounds().move(controllerPos);

        AABB gemBox = gem.getBoundingBox();

        return targetBox.inflate(0.2).intersects(gemBox);
    }

    @Override
    public void stop() {
        targetPos = null;
        controllerPos = null;
        gem.getNavigation().stop();

        if (gem.getBehavior() == GemBehavior.WORK) {
            BlockPos workPos = gem.getWorkPos();

            if (workPos != null) {
                gem.getNavigation().moveTo(
                        workPos.getX() + 0.5,
                        workPos.getY() + 0.5,
                        workPos.getZ() + 0.5,
                        1.0
                );
            }
        }
    }

    private void moveToTarget() {
        if (controllerPos == null) {
            return;
        }

        gem.getNavigation().moveTo(
                controllerPos.getX() + 0.5,
                controllerPos.getY() + 0.5,
                controllerPos.getZ() + 0.5,
                1.0
        );
    }

    private void nudgeTowardController() {
        if (controllerPos == null) {
            return;
        }

        Vec3 targetCenter = new Vec3(
                controllerPos.getX() + 0.5,
                controllerPos.getY() + 0.5,
                controllerPos.getZ() + 0.5
        );

        Vec3 gemCenter = gem.position().add(0, gem.getBbHeight() * 0.5, 0);
        Vec3 direction = targetCenter.subtract(gemCenter);

        double approachDistance = 1.5;

        if (direction.lengthSqr() > approachDistance * approachDistance) {
            return;
        }

        if (direction.lengthSqr() < 0.01) {
            return;
        }

        gem.move(MoverType.SELF, direction.normalize().scale(0.03));
    }




    private BlockPos findClosestUnfinishedBlock() {
        Level level = gem.level();
        BlockPos origin = gem.blockPosition();

        BlockPos closest = null;
        BlockPos closestAffordable = null;

        double closestDistance = Double.MAX_VALUE;
        double closestAffordableDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS), origin.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos) || !isUnfinished(pos)) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!(state.getBlock() instanceof ConstructedMultiblock multiblock) || multiblock.getController(level, pos, state) == null) {
                continue;
            }

            double distance = gem.distanceToSqr(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5
            );

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = pos.immutable();
            }

            if (gem.canAffordConstruction(pos) && distance < closestAffordableDistance) {
                closestAffordableDistance = distance;
                closestAffordable = pos.immutable();
            }
        }

        return closestAffordable != null ? closestAffordable : closest;
    }

    private boolean isUnfinished(BlockPos pos) {
        BlockState state = gem.level().getBlockState(pos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            return false;
        }

        Property<?> property = state.getBlock().getStateDefinition().getProperty("construction_stage");

        return property instanceof IntegerProperty stageProperty && state.getValue(stageProperty) < multiblock.getMaxConstructionStage();
    }

    private boolean isFinished(BlockPos pos) {
        return !isUnfinished(pos);
    }

    private void spawnConstructionParticles() {
        Level level = gem.level();

        if (!(level instanceof ServerLevel serverLevel) || targetPos == null) {
            return;
        }

        BlockState state = level.getBlockState(targetPos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            return;
        }

        BlockPos center = multiblock.getConstructionCenter(level, targetPos, state);

        double x = center.getX() + 0.5;
        double y = center.getY() + 0.5;
        double z = center.getZ() + 0.5;

        BlockPos[] offsets = {
                new BlockPos(0, 1, 0),
                new BlockPos(0, -1, 0),
                new BlockPos(1, 0, 0),
                new BlockPos(-1, 0, 0),
                new BlockPos(0, 0, 1),
                new BlockPos(0, 0, -1)
        };

        for (BlockPos offset : offsets) {
            serverLevel.sendParticles(
                    ParticleTypes.POOF,
                    x + offset.getX(),
                    y + offset.getY(),
                    z + offset.getZ(),
                    2,
                    0.12, 0.12, 0.12,
                    0.01
            );
        }
    }
}
