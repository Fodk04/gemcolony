package com.fodk.gemcolony.entity.custom.gem.ai;


import com.fodk.gemcolony.block.custom.DrillBlock;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.entity.custom.gem.base.GemBehavior;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.base.SupplyRequirement;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class InjectorManagerGoal extends Goal {
    private static final int SEARCH_RADIUS = 32;
    private static final int SEARCH_INTERVAL = 20;

    private final GemEntity gem;
    private BlockPos injectorPos;
    private int searchCooldown;

    public InjectorManagerGoal(GemEntity gem) {
        this.gem = gem;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (gem.getBehavior() != GemBehavior.WORK || gem.getWorkPos() == null) {
            clearInjectorSupplyRequest();
            return false;
        }

        if (gem.hasSupplyRequest()) {
            return false;
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }

        searchCooldown = SEARCH_INTERVAL;

        BlockPos candidatePos = findInjectorNeedingSupplies();

        if (candidatePos == null) {
            clearInjectorSupplyRequest();
            injectorPos = null;
            return false;
        }

        Level level = gem.level();

        if (!(level.getBlockEntity(candidatePos) instanceof InjectorBlockEntity injector)) {
            clearInjectorSupplyRequest();
            return false;
        }

        List<SupplyRequirement> requirements = getRequiredSupplies(injector);

        if (!requirements.isEmpty() && !hasRequiredSupplies(requirements)) {
            gem.requestSupplies(this, candidatePos, requirements);
            return false;
        }

        clearInjectorSupplyRequest();
        injectorPos = candidatePos;
        return true;
    }


    private List<SupplyRequirement> getRequiredSupplies(InjectorBlockEntity injector) {
        List<SupplyRequirement> requirements = new ArrayList<>();

        if (injector.needsGemSeed()) {
            requirements.add(SupplyRequirement.upTo(ModItems.GEM_SEED.get(), 16));
        }

        if (injector.needsChroma()) {
            requirements.add(SupplyRequirement.upTo(ModTags.Items.CHROMAS, ModItems.WHITE_CHROMA.get(), 16));
        }

        if (!injector.hasUsableEssence()) {
            requirements.add(SupplyRequirement.upTo(ModTags.Items.ESSENCE_BUCKETS, ModItems.WHITE_ESSENCE_BUCKET.get(), 6));
        }

        return requirements;
    }



    private boolean hasRequiredSupplies(List<SupplyRequirement> requirements) {
        for (SupplyRequirement requirement : requirements) {
            int held = 0;
            for (int slot = 0; slot < gem.getContainerSize(); slot++) {
                ItemStack stack = gem.getItem(slot);

                if (requirement.matches(stack)) {
                    held += stack.getCount();
                }
            }
            int needed = requirement.ifPossible() ? 1 : requirement.count();
            if (held < needed) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean canContinueToUse() {
        if (gem.getBehavior() != GemBehavior.WORK || gem.getWorkPos() == null) {
            return false;
        }

        if (injectorPos == null) {
            return false;
        }

        Level level = gem.level();

        if (!level.isLoaded(injectorPos)) {
            return false;
        }

        if (!(level.getBlockEntity(injectorPos) instanceof InjectorBlockEntity injector) || !injector.isComplete()) {
            return false;
        }

        if (gem.hasSupplyRequest()) {
            return false;
        }

        return needsSupplies(injector);
    }


    @Override
    public void start() {
        moveToInjector();
    }


    @Override
    public void tick() {
        if (injectorPos == null) {
            return;
        }

        if (gem.hasSupplyRequest() && gem.getSupplyRequestOwner() != this) {
            return;
        }

        Level level = gem.level();

        if (!level.isLoaded(injectorPos)) {
            return;
        }

        if (!(level.getBlockEntity(injectorPos) instanceof InjectorBlockEntity injector) || !injector.isComplete()) {
            return;
        }

        if (gem.hasSupplyRequest()) {
            return;
        }

        if (isTouchingInjector()) {
            gem.getNavigation().stop();
        } else {
            nudgeTowardInjector();

            if (!gem.getNavigation().isDone()) {
                return;
            }

            moveToInjector();
            return;
        }

        insertManagedItems(injector);
    }


    private void insertManagedItems(InjectorBlockEntity injector) {
        for (int slot = 0; slot < gem.getContainerSize(); slot++) {
            ItemStack stack = gem.getItem(slot);

            if (stack.isEmpty()) {
                continue;
            }
            int inserted = injector.insertManagedItems(stack);
            if (inserted <= 0) {
                continue;
            }
            stack.shrink(inserted);
            if (stack.isEmpty()) {
                gem.setItem(slot, ItemStack.EMPTY);
            }
        }

        gem.setChanged();
    }

    @Override
    public void stop() {
        clearInjectorSupplyRequest();

        injectorPos = null;
        searchCooldown = 0;
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

    @Nullable
    private BlockPos findInjectorNeedingSupplies() {
        Level level = gem.level();
        BlockPos origin = gem.blockPosition();

        BlockPos closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(
                origin.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS),
                origin.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos)) {
                continue;
            }

            if (!(level.getBlockEntity(pos) instanceof InjectorBlockEntity injector)) {
                continue;
            }

            if (!injector.isComplete() || !needsSupplies(injector)) {
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
        }

        return closest;
    }

    private boolean needsSupplies(InjectorBlockEntity injector) {
        return injector.needsGemSeed() || injector.needsChroma() || !injector.hasUsableEssence();
    }

    private void moveToInjector() {
        if (injectorPos == null) {
            return;
        }

        gem.getNavigation().moveTo(
                injectorPos.getX() + 0.5,
                injectorPos.getY() + 0.5,
                injectorPos.getZ() + 0.5,
                1.0
        );
    }

    private boolean isTouchingInjector() {
        if (injectorPos == null) {
            return false;
        }

        Level level = gem.level();

        if (!(level.getBlockEntity(injectorPos) instanceof InjectorBlockEntity injector)) {
            return false;
        }

        BlockPos center = injector.getBlockPos();

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS),
                center.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos)) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!(state.getBlock() instanceof DrillBlock)) {
                continue;
            }

            AABB blockBox = state.getCollisionShape(level, pos).bounds().move(pos);

            if (blockBox.getXsize() > 0
                    && blockBox.getYsize() > 0
                    && blockBox.getZsize() > 0
                    && blockBox.inflate(0.35).intersects(gem.getBoundingBox())) {
                return true;
            }
        }

        return false;
    }


    private void nudgeTowardInjector() {
        if (injectorPos == null) {
            return;
        }

        Level level = gem.level();
        AABB gemBox = gem.getBoundingBox();

        BlockPos center = injectorPos;
        Vec3 closestPoint = null;
        double closestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS),
                center.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos)) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!(state.getBlock() instanceof DrillBlock)) {
                continue;
            }

            AABB box = state.getCollisionShape(level, pos).bounds().move(pos);

            if (box.getXsize() <= 0 || box.getYsize() <= 0 || box.getZsize() <= 0) {
                continue;
            }

            double x = Mth.clamp(gemBox.getCenter().x, box.minX, box.maxX);
            double y = Mth.clamp(gemBox.getCenter().y, box.minY, box.maxY);
            double z = Mth.clamp(gemBox.getCenter().z, box.minZ, box.maxZ);

            Vec3 point = new Vec3(x, y, z);
            double distance = gem.position().distanceToSqr(point);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }

        if (closestPoint == null || closestDistance > 4.0) {
            return;
        }

        Vec3 gemCenter = gem.position().add(0, gem.getBbHeight() * 0.5, 0);
        Vec3 direction = closestPoint.subtract(gemCenter);

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

    private void clearInjectorSupplyRequest() {
        gem.clearSupplyRequest(this);
    }
}

