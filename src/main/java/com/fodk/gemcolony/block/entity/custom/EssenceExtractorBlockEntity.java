package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.EssenceExtractorBlock;
import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EssenceExtractorBlockEntity extends BlockEntity implements Container {

    //5MIN
    private static final int EXTRACTION_INTERVAL = 6000;
    private int extractionTimer = 0;
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private static final int MINERAL_SHARD_SLOT = 0;

    public EssenceExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_EXTRACTOR_BE.get(), pos, state);
    }

    public ItemStack getMineralShard() {
        return items.get(MINERAL_SHARD_SLOT);
    }

    public void setMineralShard(ItemStack stack) {
        ItemStack shard = stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        setItem(MINERAL_SHARD_SLOT, shard);
        updateExtractorColor();
    }

    public ItemStack removeMineralShard() {
        ItemStack removed = removeItemNoUpdate(MINERAL_SHARD_SLOT);
        setChanged();
        updateExtractorColor();
        return removed;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, items);
        output.putInt("ExtractionTimer", extractionTimer);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, items);
        extractionTimer = input.getIntOr("ExtractionTimer", 0);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.get(MINERAL_SHARD_SLOT).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);

        if (!result.isEmpty()) {
            setChanged();
            updateExtractorColor();
        }

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);

        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
        updateExtractorColor();
    }

    public InteractionResult handleInteraction(Player player, ItemStack stack) {
        if (level == null || level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!getMineralShard().isEmpty()) {
            ItemStack removed = removeMineralShard();

            if (!player.getInventory().add(removed)) {
                player.drop(removed, false);
            }

            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

            return InteractionResult.SUCCESS;
        }

        if (!stack.is(ModTags.Items.MINERAL_SHARDS)) {
            return InteractionResult.PASS;
        }

        setMineralShard(stack);

        if (!player.isCreative()) {
            stack.shrink(1);
        }

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

        return InteractionResult.SUCCESS;
    }

    public void tick() {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (getMineralShard().isEmpty()) {
            extractionTimer = 0;
            return;
        }

        if (extractionTimer < EXTRACTION_INTERVAL) {
            extractionTimer++;
        }

        if (extractionTimer >= EXTRACTION_INTERVAL) {
            extractEssence();
        }
    }

    private void extractEssence() {
        if (level == null || getMineralShard().isEmpty()) {
            return;
        }

        Block essenceBlock;

        if (getMineralShard().is(ModItems.PINK_MINERAL_SHARDS.get())) {
            essenceBlock = ModBlocks.PINK_ESSENCE.get();
        } else if (getMineralShard().is(ModItems.BLUE_MINERAL_SHARDS.get())) {
            essenceBlock = ModBlocks.BLUE_ESSENCE.get();
        } else if (getMineralShard().is(ModItems.YELLOW_MINERAL_SHARDS.get())) {
            essenceBlock = ModBlocks.YELLOW_ESSENCE.get();
        } else if (getMineralShard().is(ModItems.WHITE_MINERAL_SHARDS.get())) {
            essenceBlock = ModBlocks.WHITE_ESSENCE.get();
        } else {
            return;
        }

        int[][] offsets = {
                {0, -1},
                {1, -1},
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1}
        };

        int start = level.getRandom().nextInt(offsets.length);

        for (int i = 0; i < offsets.length; i++) {
            int[] offset = offsets[(start + i) % offsets.length];
            BlockPos targetPos = worldPosition.offset(offset[0], 0, offset[1]);

            BlockState targetState = level.getBlockState(targetPos);

            boolean isFlowingEssence =
                    (targetState.is(ModBlocks.PINK_ESSENCE.get())
                            || targetState.is(ModBlocks.BLUE_ESSENCE.get())
                            || targetState.is(ModBlocks.YELLOW_ESSENCE.get())
                            || targetState.is(ModBlocks.WHITE_ESSENCE.get()))
                            && targetState.getValue(LiquidBlock.LEVEL) > 0;

            if (level.isEmptyBlock(targetPos) || isFlowingEssence) {
                level.setBlock(targetPos, essenceBlock.defaultBlockState(), 3);

                damageMineralShard();
                extractionTimer = 0;
                setChanged();

                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                return;
            }
        }

    }

    private void damageMineralShard() {
        ItemStack shard = getMineralShard();

        if (shard.isEmpty()) {
            return;
        }

        shard.setDamageValue(shard.getDamageValue() + 1);

        if (shard.getDamageValue() >= shard.getMaxDamage()) {
            removeMineralShard();
        } else {
            setChanged();
        }
    }

    private void updateExtractorColor() {
        if (level == null || level.isClientSide()) {
            return;
        }

        int color = 0;

        if (getMineralShard().is(ModItems.PINK_MINERAL_SHARDS.get())) {
            color = 1;
        } else if (getMineralShard().is(ModItems.BLUE_MINERAL_SHARDS.get())) {
            color = 2;
        } else if (getMineralShard().is(ModItems.YELLOW_MINERAL_SHARDS.get())) {
            color = 3;
        } else if (getMineralShard().is(ModItems.WHITE_MINERAL_SHARDS.get())) {
            color = 4;
        }

        BlockState state = getBlockState();
        if (state.getValue(EssenceExtractorBlock.COLOR) != color) {
            level.setBlock(worldPosition, state.setValue(EssenceExtractorBlock.COLOR, color), 3);
        }

        setChanged();
    }
}
