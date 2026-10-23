package com.fodk.gemcolony.entity.custom.gem.ai;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import javax.annotation.Nullable;
import java.util.*;

public class ChestSupplyGoal extends Goal {
    private static final int SEARCH_RADIUS = 16;
    private static final int SEARCH_INTERVAL = 20;

    private final GemEntity gem;
    private BlockPos chestPos;
    private int searchCooldown;

    public ChestSupplyGoal(GemEntity gem) {
        this.gem = gem;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!gem.hasSupplyRequest()) {
            return false;
        }

        if (getMissingRequirements().isEmpty()) {
            gem.clearSupplyRequest();
            return false;
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }

        searchCooldown = SEARCH_INTERVAL;
        chestPos = findClosestSuitableChest();

        return chestPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (!gem.hasSupplyRequest() || chestPos == null) {
            return false;
        }

        Level level = gem.level();

        if (!level.isLoaded(chestPos)) {
            return false;
        }

        BlockState state = level.getBlockState(chestPos);

        if (!(state.getBlock() instanceof ChestBlock)) {
            chestPos = null;
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        moveToChest();
    }

    @Override
    public void tick() {
        if (chestPos == null) {
            return;
        }

        double distance = gem.distanceToSqr(
                chestPos.getX() + 0.5,
                chestPos.getY() + 0.5,
                chestPos.getZ() + 0.5
        );

        if (distance <= 2.25) {
            gem.getNavigation().stop();

            Level level = gem.level();
            BlockState state = level.getBlockState(chestPos);

            if (!(state.getBlock() instanceof ChestBlock chestBlock)) {
                chestPos = null;
                return;
            }

            Container chest = ChestBlock.getContainer(chestBlock, state, level, chestPos, true);

            if (chest == null || !containsRequirements(chest, getMissingRequirements())) {
                chestPos = null;
                gem.getNavigation().stop();
                return;
            }

            Map<Item, Integer> missing = getMissingRequirements();

            if (missing.isEmpty()) {
                gem.clearSupplyRequest();
                chestPos = null;
                return;
            }

            if (!canFitAllMissing(chest, missing)) {
                Integer depositSlot = findDepositSlot(chest);

                if (depositSlot == null) {
                    chestPos = null;
                    return;
                }

                if (!depositStack(chest, depositSlot)) {
                    chestPos = null;
                }

                return;
            }

            if (takeMissingItems(chest, missing)) {
                gem.clearSupplyRequest();
                chestPos = null;
            } else {
                chestPos = null;
            }

            return;
        }

        if (gem.getNavigation().isDone()) {
            moveToChest();
        }
    }

    @Override
    public void stop() {
        chestPos = null;
        gem.getNavigation().stop();
    }

    private void moveToChest() {
        if (chestPos == null) {
            return;
        }

        gem.getNavigation().moveTo(
                chestPos.getX() + 0.5,
                chestPos.getY() + 0.5,
                chestPos.getZ() + 0.5,
                1.0
        );
    }


    @Nullable
    private BlockPos findClosestSuitableChest() {
        Level level = gem.level();
        BlockPos origin = gem.getSupplyTargetPos();

        if (origin == null) {
            return null;
        }

        Map<Item, Integer> missing = getMissingRequirements();

        if (missing.isEmpty()) {
            return null;
        }

        BlockPos closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(
                origin.offset(-SEARCH_RADIUS, -SEARCH_RADIUS, -SEARCH_RADIUS),
                origin.offset(SEARCH_RADIUS, SEARCH_RADIUS, SEARCH_RADIUS))) {

            if (!level.isLoaded(pos)) {
                continue;
            }

            BlockState state = level.getBlockState(pos);

            if (!(state.getBlock() instanceof ChestBlock chestBlock)) {
                continue;
            }

            Container chest = ChestBlock.getContainer(
                    chestBlock, state, level, pos, true);

            if (chest == null || !containsRequirements(chest, missing)) {
                continue;
            }

            double distance = gem.distanceToSqr(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5);

            if (distance < closestDistance) {
                closestDistance = distance;
                closest = pos.immutable();
            }
        }

        return closest;
    }


    private boolean containsRequirements(Container chest, Map<Item, Integer> requirements) {
        for (var requirement : requirements.entrySet()) {
            Item requiredItem = requirement.getKey();
            int requiredCount = requirement.getValue();
            int foundCount = 0;

            for (int slot = 0; slot < chest.getContainerSize(); slot++) {
                ItemStack stack = chest.getItem(slot);

                if (stack.is(requiredItem)) {
                    foundCount += stack.getCount();
                }

                if (foundCount >= requiredCount) {
                    break;
                }
            }

            if (foundCount < requiredCount) {
                return false;
            }
        }

        return true;
    }

    private Map<Item, Integer> getMissingRequirements() {
        Map<Item, Integer> missing = new HashMap<>();

        for (var requirement : gem.getSupplyRequirements().entrySet()) {
            Item item = requirement.getKey();
            int required = requirement.getValue();
            int available = 0;

            for (int slot = 0; slot < gem.getContainerSize(); slot++) {
                ItemStack stack = gem.getItem(slot);

                if (stack.is(item)) {
                    available += stack.getCount();
                }
            }

            if (available < required) {
                missing.put(item, required - available);
            }
        }

        return missing;
    }

    private boolean canFitStack(Container container, ItemStack stack) {
        int remaining = stack.getCount();

        for (int slot = 0; slot < container.getContainerSize(); slot++) {
            ItemStack existing = container.getItem(slot);

            if (existing.isEmpty()) {
                remaining -= Math.min(remaining, stack.getMaxStackSize());
            } else if (ItemStack.isSameItemSameComponents(existing, stack)) {
                int capacity = Math.min(container.getMaxStackSize(), existing.getMaxStackSize()) - existing.getCount();

                remaining -= Math.min(remaining, Math.min(container.getMaxStackSize(), stack.getMaxStackSize()));
            }

            if (remaining <= 0) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    private Integer findDepositSlot(Container chest) {
        for (int slot = 0; slot < gem.getContainerSize(); slot++) {
            ItemStack stack = gem.getItem(slot);

            if (stack.isEmpty()) {
                continue;
            }

            // Never deposit a material required for this request.
            if (gem.getSupplyRequirements().containsKey(stack.getItem())) {
                continue;
            }

            if (canFitStack(chest, stack)) {
                return slot;
            }
        }

        return null;
    }

    private boolean canFitAllMissing(Container chest, Map<Item, Integer> missing) {
        List<ItemStack> simulatedInventory = new ArrayList<>();

        for (int slot = 0; slot < gem.getContainerSize(); slot++) {
            simulatedInventory.add(gem.getItem(slot).copy());
        }

        for (var entry : missing.entrySet()) {
            Item item = entry.getKey();
            int remaining = entry.getValue();

            for (int chestSlot = 0;
                 chestSlot < chest.getContainerSize() && remaining > 0;
                 chestSlot++) {

                ItemStack chestStack = chest.getItem(chestSlot);

                if (!chestStack.is(item)) {
                    continue;
                }

                int amount = Math.min(remaining, chestStack.getCount());
                ItemStack incoming = chestStack.copyWithCount(amount);

                for (ItemStack inventoryStack : simulatedInventory) {
                    if (incoming.isEmpty()) {
                        break;
                    }

                    if (inventoryStack.isEmpty() || !ItemStack.isSameItemSameComponents(inventoryStack, incoming)) {
                        continue;
                    }

                    int capacity = inventoryStack.getMaxStackSize() - inventoryStack.getCount();

                    int moved = Math.min(Math.max(0, capacity), incoming.getCount());

                    if (moved > 0) {
                        inventoryStack.grow(moved);
                        incoming.shrink(moved);
                    }
                }

                // Then, use empty slots.
                for (int inventorySlot = 0; inventorySlot < simulatedInventory.size() && !incoming.isEmpty(); inventorySlot++) {

                    if (!simulatedInventory.get(inventorySlot).isEmpty()) {
                        continue;
                    }

                    int moved = Math.min(
                            incoming.getCount(),
                            incoming.getMaxStackSize()
                    );

                    simulatedInventory.set(
                            inventorySlot,
                            incoming.copyWithCount(moved)
                    );

                    incoming.shrink(moved);
                }

                // The simulated inventory cannot accept everything.
                if (!incoming.isEmpty()) {
                    return false;
                }

                remaining -= amount;
            }

            // The chest doesn't contain enough of this item.
            if (remaining > 0) {
                return false;
            }
        }

        return true;
    }

    private boolean takeMissingItems(Container chest, Map<Item, Integer> missing) {
        if (!canFitAllMissing(chest, missing) || !containsRequirements(chest, missing)) {
            return false;
        }

        // Recheck the chest's actual contents before withdrawing anything.
        for (var entry : missing.entrySet()) {
            Item item = entry.getKey();
            int required = entry.getValue();
            int available = 0;

            for (int slot = 0; slot < chest.getContainerSize(); slot++) {
                ItemStack stack = chest.getItem(slot);

                if (stack.is(item)) {
                    available += stack.getCount();
                }

                if (available >= required) {
                    break;
                }
            }

            if (available < required) {
                return false;
            }
        }

        // All requested items fit and are available. Transfer them now.
        for (var entry : missing.entrySet()) {
            Item item = entry.getKey();
            int remaining = entry.getValue();

            for (int slot = 0; slot < chest.getContainerSize() && remaining > 0; slot++) {
                ItemStack chestStack = chest.getItem(slot);

                if (!chestStack.is(item)) {
                    continue;
                }

                int amount = Math.min(remaining, chestStack.getCount());
                ItemStack extracted = chestStack.copyWithCount(amount);

                chestStack.shrink(amount);
                chest.setItem(slot, chestStack.isEmpty() ? ItemStack.EMPTY : chestStack);

                // Merge into compatible stacks first.
                for (int gemSlot = 0;
                     gemSlot < gem.getContainerSize() && !extracted.isEmpty();
                     gemSlot++) {

                    ItemStack gemStack = gem.getItem(gemSlot);

                    if (gemStack.isEmpty()
                            || !ItemStack.isSameItemSameComponents(gemStack, extracted)) {
                        continue;
                    }

                    int capacity = gemStack.getMaxStackSize() - gemStack.getCount();
                    int moved = Math.min(Math.max(0, capacity), extracted.getCount());

                    if (moved > 0) {
                        gemStack.grow(moved);
                        extracted.shrink(moved);
                    }
                }

                // Put any remainder into an empty slot.
                for (int gemSlot = 0;
                     gemSlot < gem.getContainerSize() && !extracted.isEmpty();
                     gemSlot++) {

                    if (gem.getItem(gemSlot).isEmpty()) {
                        gem.setItem(gemSlot, extracted.copy());
                        extracted = ItemStack.EMPTY;
                    }
                }

                // The simulation should have guaranteed this cannot happen.
                if (!extracted.isEmpty()) {
                    return false;
                }

                remaining -= amount;
            }

            if (remaining > 0) {
                return false;
            }
        }

        gem.setChanged();
        chest.setChanged();
        return true;
    }

    private boolean depositStack(Container chest, int inventorySlot) {
        ItemStack stack = gem.getItem(inventorySlot);

        if (stack.isEmpty() || !canFitStack(chest, stack)) {
            return false;
        }

        ItemStack remaining = stack.copy();

        for (int slot = 0; slot < chest.getContainerSize() && !remaining.isEmpty(); slot++) {
            ItemStack existing = chest.getItem(slot);

            if (!existing.isEmpty() && ItemStack.isSameItemSameComponents(existing, remaining)) {
                int capacity = Math.min(chest.getMaxStackSize(), existing.getMaxStackSize()) - existing.getCount();
                int moved = Math.min(Math.max(0, capacity), remaining.getCount());

                if (moved > 0) {
                    existing.grow(moved);
                    remaining.shrink(moved);
                }
            }
        }

        for (int slot = 0; slot < chest.getContainerSize() && !remaining.isEmpty(); slot++) {
            if (!chest.getItem(slot).isEmpty()) {
                continue;
            }

            int moved = Math.min(remaining.getCount(), remaining.getMaxStackSize());
            chest.setItem(slot, remaining.copyWithCount(moved));
            remaining.shrink(moved);
        }

        if (!remaining.isEmpty()) {
            return false;
        }

        gem.setItem(inventorySlot, ItemStack.EMPTY);
        gem.setChanged();
        chest.setChanged();
        return true;
    }
}
