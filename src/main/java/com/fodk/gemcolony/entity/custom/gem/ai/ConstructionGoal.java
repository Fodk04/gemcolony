package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import com.fodk.gemcolony.construction.ConstructionRequirement;
import com.fodk.gemcolony.data.ConstructionProgressData;
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

import java.util.*;

public class ConstructionGoal extends Goal {
    private static final int SEARCH_INTERVAL = 20;
    private static final int SEARCH_RADIUS = 32;

    private final StarterGemEntity gem;

    private BlockPos targetPos;
    private BlockPos controllerPos;
    private BlockPos approachPos;
    private BlockPos constructionCenterPos;

    private BlockPos claimedConstructionCenterPos;
    private boolean isConstructionLead;

    private int searchCooldown;
    private int particleCooldown;
    private int constructionAnimationCooldown;

    public ConstructionGoal(StarterGemEntity gem) {
        this.gem = gem;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() != GemBehavior.WORK || gem.getWorkPos() == null) {
            clearConstructionSupplyRequest();
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
        constructionCenterPos = null;
        targetPos = findClosestUnfinishedBlock();

        if (targetPos == null) {
            clearConstructionSupplyRequest();
            return false;
        }

        Level level = gem.level();
        BlockState state = level.getBlockState(targetPos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            clearConstructionSupplyRequest();
            targetPos = null;
            controllerPos = null;
            return false;
        }

        controllerPos = multiblock.getController(level, targetPos, state);
        constructionCenterPos = multiblock.getConstructionCenter(level, targetPos, state);

        if (controllerPos == null || constructionCenterPos == null) {
            releaseConstructionClaim();
            targetPos = null;
            controllerPos = null;
            approachPos = null;
            constructionCenterPos = null;
            return false;
        }

        constructionCenterPos = constructionCenterPos.immutable();

        if (!constructionCenterPos.equals(claimedConstructionCenterPos)) {
            releaseConstructionClaim();
            targetPos = null;
            controllerPos = null;
            approachPos = null;
            constructionCenterPos = null;
            return false;
        }

        approachPos = multiblock.getConstructionApproachPosition(level, targetPos, state);

        if (!gem.canAffordConstruction(targetPos)) {
            if (isConstructionLead) {
                Map<Item, Integer> required = new HashMap<>();

                for (ConstructionRequirement requirement : multiblock.getConstructionRequirements()) {
                    required.merge(requirement.item(), requirement.count(), Integer::sum);
                }

                if (!required.isEmpty()) {
                    gem.requestSupplies(this, controllerPos, required);
                }
            }

            return false;
        }

        clearConstructionSupplyRequest();
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

            ConstructionProgressData data = getConstructionData();

            if (data == null) {
                return;
            }

            isConstructionLead = data.isLead(gem.level().dimension(), claimedConstructionCenterPos, gem.getUUID());
            if (!isConstructionLead && data.getProgress(gem.level().dimension(), claimedConstructionCenterPos) == 0) {
                return;
            }

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

            int previousStage = getConstructionStage(targetPos);

            if (!gem.tryBuildBlock(targetPos) || isFinished(targetPos) || getConstructionStage(targetPos) != previousStage) {
                releaseConstructionClaim();
                targetPos = null;
                controllerPos = null;
                approachPos = null;
                constructionCenterPos = null;
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
        if (approachPos == null) {
            return false;
        }

        Level level = gem.level();
        BlockState state = level.getBlockState(approachPos);

        AABB targetBox = state.getCollisionShape(level, approachPos).bounds().move(approachPos);
        AABB gemBox = gem.getBoundingBox();

        return targetBox.inflate(0.2).intersects(gemBox);
    }

    @Override
    public void stop() {
        releaseConstructionClaim();
        clearConstructionSupplyRequest();

        targetPos = null;
        controllerPos = null;
        approachPos = null;
        constructionCenterPos = null;
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
        if (approachPos == null) {
            return;
        }

        gem.getNavigation().moveTo(
                approachPos.getX() + 0.5,
                approachPos.getY() + 0.5,
                approachPos.getZ() + 0.5,
                1.0
        );
    }

    private void nudgeTowardController() {
        if (approachPos == null) {
            return;
        }

        Vec3 targetCenter = new Vec3(
                approachPos.getX() + 0.5,
                approachPos.getY() + 0.5,
                approachPos.getZ() + 0.5
        );

        Vec3 gemCenter = gem.position().add(0, gem.getBbHeight() * 0.5, 0);
        Vec3 direction = targetCenter.subtract(gemCenter);

        double approachDistance = 2;

        if (direction.lengthSqr() > approachDistance * approachDistance) {
            return;
        }

        if (direction.lengthSqr() < 0.01) {
            return;
        }

        Vec3 movement = direction.normalize();

        float yaw = (float) (Math.atan2(movement.z, movement.x) * (180.0 / Math.PI)) - 90.0F;

        gem.setYRot(yaw);
        gem.setYBodyRot(yaw);
        gem.setYHeadRot(yaw);

        gem.move(MoverType.SELF, movement.scale(0.03));
    }


    private BlockPos findClosestUnfinishedBlock() {
        Level level = gem.level();
        BlockPos origin = gem.blockPosition();

        Map<BlockPos, Double> candidates = new HashMap<>();
        Map<BlockPos, BlockPos> candidateTargets = new HashMap<>();
        Map<BlockPos, BlockPos> candidateControllers = new HashMap<>();

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS),
                origin.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos) || !isUnfinished(pos)) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
                continue;
            }

            BlockPos controller = multiblock.getController(level, pos, state);

            if (controller == null || !level.isLoaded(controller)) {
                continue;
            }

            BlockPos constructionCenter = multiblock.getConstructionCenter(level, pos, state);

            if (constructionCenter == null) {
                continue;
            }

            constructionCenter = constructionCenter.immutable();
            BlockPos canonicalController = controller.immutable();

            double distance = gem.distanceToSqr(
                    constructionCenter.getX() + 0.5,
                    constructionCenter.getY() + 0.5,
                    constructionCenter.getZ() + 0.5
            );

            Double previousDistance = candidates.get(constructionCenter);

            if (previousDistance == null || distance < previousDistance) {
                candidates.put(constructionCenter, distance);
                candidateTargets.put(constructionCenter, pos.immutable());
                candidateControllers.put(constructionCenter, canonicalController);
            }
        }

        List<Map.Entry<BlockPos, Double>> orderedCandidates = new ArrayList<>(candidates.entrySet());
        orderedCandidates.sort(Map.Entry.comparingByValue());

        for (Map.Entry<BlockPos, Double> entry : orderedCandidates) {
            BlockPos constructionCenter = entry.getKey();
            BlockPos candidateTarget = candidateTargets.get(constructionCenter);
            BlockPos candidateController = candidateControllers.get(constructionCenter);

            if (!claimConstruction(constructionCenter)) {
                continue;
            }

            BlockState targetState = level.getBlockState(candidateTarget);
            if (!(targetState.getBlock() instanceof ConstructedMultiblock multiblock) || !isUnfinished(candidateTarget)) {
                releaseConstructionClaim();
                continue;
            }

            BlockPos actualCenter = multiblock.getConstructionCenter(level, candidateTarget, targetState);
            BlockPos actualController = multiblock.getController(level, candidateTarget, targetState);

            if (actualCenter == null || actualController == null || !actualCenter.equals(constructionCenter) || !actualController.equals(candidateController)) {
                releaseConstructionClaim();
                continue;
            }

            return candidateTarget;
        }

        releaseConstructionClaim();
        return null;
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


    //starter claim
    private ConstructionProgressData getConstructionData() {
        if (gem.level() instanceof ServerLevel serverLevel) {
            return ConstructionProgressData.get(serverLevel);
        }

        return null;
    }

    private void releaseConstructionClaim() {
        if (claimedConstructionCenterPos == null) {
            return;
        }
        ConstructionProgressData data = getConstructionData();
        if (data != null) {
            data.releaseClaim(gem.level().dimension(), claimedConstructionCenterPos, gem.getUUID());
        }

        claimedConstructionCenterPos = null;
        isConstructionLead = false;
    }

    private void clearConstructionSupplyRequest() {
        gem.clearSupplyRequest(this);
    }

    private boolean claimConstruction(BlockPos constructionCenterPos) {
        ConstructionProgressData data = getConstructionData();
        if (data == null) {
            return false;
        }

        if (constructionCenterPos.equals(claimedConstructionCenterPos)) {
            if (data.hasClaim(gem.level().dimension(), constructionCenterPos, gem.getUUID())) {
                isConstructionLead = data.isLead(gem.level().dimension(), constructionCenterPos, gem.getUUID());
                return true;
            }
            claimedConstructionCenterPos = null;
            isConstructionLead = false;
        }

        releaseConstructionClaim();

        if (!data.tryClaim(gem.level().dimension(), constructionCenterPos, gem.getUUID())) {
            return false;
        }

        claimedConstructionCenterPos = constructionCenterPos.immutable();
        isConstructionLead = data.isLead(gem.level().dimension(), constructionCenterPos, gem.getUUID());

        return true;
    }

    private int getConstructionStage(BlockPos pos) {
        if (pos == null) {
            return -1;
        }

        BlockState state = gem.level().getBlockState(pos);

        for (Property<?> property : state.getProperties()) {
            if (property.getName().equals("construction_stage") && property instanceof IntegerProperty stageProperty) {
                return state.getValue(stageProperty);
            }
        }

        return -1;
    }
}
