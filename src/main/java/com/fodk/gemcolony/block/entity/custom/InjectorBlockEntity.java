package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.DrillBlock;
import com.fodk.gemcolony.block.custom.InjectionOrientation;
import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.EssenceType;
import com.fodk.gemcolony.menu.InjectorMenu;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nullable;

public class InjectorBlockEntity extends BlockEntity implements Container {

    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private final EssenceTank blueTank = new EssenceTank(EssenceType.BLUE);
    private final EssenceTank yellowTank = new EssenceTank(EssenceType.YELLOW);
    private final EssenceTank whiteTank = new EssenceTank(EssenceType.WHITE);
    private final EssenceTank pinkTank = new EssenceTank(EssenceType.PINK);

    private record EssenceCost(
            int blue,
            int yellow,
            int white,
            int pink
    ) {
    }

    private final EssenceResourceHandler essenceHandler;

    private static final int SLOTS_PER_ROW = 3;
    private static final int SLOT_SPACING = 4;
    private static final int ROW_SPACING = 4;
    private static final int MIN_Y = -40;

    private InjectionOrientation injectionOrientation = InjectionOrientation.EAST_WEST;

    private boolean redstonePowered = false;

    public int getBlueEssenceAmount() {
        return blueTank.getAmount();
    }

    public int getYellowEssenceAmount() {
        return yellowTank.getAmount();
    }

    public int getWhiteEssenceAmount() {
        return whiteTank.getAmount();
    }

    public int getPinkEssenceAmount() {
        return pinkTank.getAmount();
    }

    public EssenceTank getBlueTank() {
        return blueTank;
    }

    public EssenceTank getYellowTank() {
        return yellowTank;
    }

    public EssenceTank getWhiteTank() {
        return whiteTank;
    }

    public EssenceTank getPinkTank() {
        return pinkTank;
    }

    public int getTotalEssenceAmount() {
        return blueTank.getAmount()
                + yellowTank.getAmount()
                + whiteTank.getAmount()
                + pinkTank.getAmount();
    }

    public int getTotalEssenceCapacity() {
        return blueTank.getCapacity()
                + yellowTank.getCapacity()
                + whiteTank.getCapacity()
                + pinkTank.getCapacity();
    }

    public InjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INJECTOR_BE.get(), pos, state);
        this.essenceHandler = new EssenceResourceHandler(
                blueTank,
                yellowTank,
                whiteTank,
                pinkTank
        );
    }

    public EssenceResourceHandler getEssenceHandler() {
        return essenceHandler;
    }

    public boolean isComplete() {
        if (level == null) {
            return false;
        }

        return DrillBlock.isInjectorComplete(level, worldPosition);
    }

    public InteractionResult handleInteraction(Player player, ItemStack stack) {
        if (level == null || level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        // Essence bucket/bo'ole interaction hehehe get it
        if (isEssenceBucket(stack) || isEssenceBottle(stack)) {
            int amount = isEssenceBucket(stack) ? 1000 : 250;
            Fluid fluid;

            if (stack.is(ModItems.BLUE_ESSENCE_BUCKET.get()) || stack.is(ModItems.BLUE_ESSENCE_BOTTLE.get())) {
                fluid = ModFluids.BLUE_ESSENCE.get();

            } else if (stack.is(ModItems.YELLOW_ESSENCE_BUCKET.get()) || stack.is(ModItems.YELLOW_ESSENCE_BOTTLE.get())) {
                fluid = ModFluids.YELLOW_ESSENCE.get();

            } else if (stack.is(ModItems.WHITE_ESSENCE_BUCKET.get()) || stack.is(ModItems.WHITE_ESSENCE_BOTTLE.get())) {
                fluid = ModFluids.WHITE_ESSENCE.get();

            } else {
                fluid = ModFluids.PINK_ESSENCE.get();
            }

            FluidResource resource = FluidResource.of(fluid);

            try (Transaction transaction = Transaction.openRoot()) {

                int inserted = essenceHandler.insert(getEssenceTankIndex(resource), resource, amount, transaction);

                if (inserted != amount) {
                    return InteractionResult.PASS;
                }

                transaction.commit();
            }

            ItemStack emptyContainer = amount == 1000 ? new ItemStack(Items.BUCKET) : new ItemStack(Items.GLASS_BOTTLE);

            if(!player.isCreative()){
                stack.shrink(1);

                if (stack.isEmpty()) {
                    player.setItemInHand(player.getUsedItemHand(), emptyContainer);
                } else if (!player.getInventory().add(emptyContainer)) {
                    player.drop(emptyContainer, false);
                }
            }

            syncEssence();

            level.playSound(null, worldPosition, amount == 1000 ? SoundEvents.BUCKET_EMPTY : SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;
        }

        // Normal Injector interaction
        if (isComplete()) {
            player.sendSystemMessage(Component.literal("Injector is incomplete."));

            return InteractionResult.SUCCESS;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (containerId, playerInventory, ignoredPlayer) ->
                                    new InjectorMenu(containerId, playerInventory, this), Component.literal("Injector")),
                    buffer -> {
                        buffer.writeInt(worldPosition.getX());
                        buffer.writeInt(worldPosition.getY());
                        buffer.writeInt(worldPosition.getZ());
                    }
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
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

        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return player.distanceToSqr(
                worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5
        ) <= 196.0;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, items);

        output.putInt("BlueEssence", blueTank.getAmount());
        output.putInt("YellowEssence", yellowTank.getAmount());
        output.putInt("WhiteEssence", whiteTank.getAmount());
        output.putInt("PinkEssence", pinkTank.getAmount());

        output.putString("InjectionOrientation", injectionOrientation.name());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, items);

        blueTank.setAmount(input.getIntOr("BlueEssence", 0));
        yellowTank.setAmount(input.getIntOr("YellowEssence", 0));
        whiteTank.setAmount(input.getIntOr("WhiteEssence", 0));
        pinkTank.setAmount(input.getIntOr("PinkEssence", 0));

        injectionOrientation = InjectionOrientation.valueOf(input.getStringOr("InjectionOrientation", InjectionOrientation.NORTH_SOUTH.name()));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);

        tag.putInt("BlueEssence", blueTank.getAmount());
        tag.putInt("YellowEssence", yellowTank.getAmount());
        tag.putInt("WhiteEssence", whiteTank.getAmount());
        tag.putInt("PinkEssence", pinkTank.getAmount());

        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ValueInput input) {
        super.onDataPacket(net, input);

        blueTank.setAmount(input.getIntOr("BlueEssence", 0));
        yellowTank.setAmount(input.getIntOr("YellowEssence", 0));
        whiteTank.setAmount(input.getIntOr("WhiteEssence", 0));
        pinkTank.setAmount(input.getIntOr("PinkEssence", 0));
    }

    private int getEssenceTankIndex(FluidResource resource) {

        if (resource.getFluid() == ModFluids.BLUE_ESSENCE.get()) {
            return 0;
        }

        if (resource.getFluid() == ModFluids.YELLOW_ESSENCE.get()) {
            return 1;
        }

        if (resource.getFluid() == ModFluids.WHITE_ESSENCE.get()) {
            return 2;
        }

        if (resource.getFluid() == ModFluids.PINK_ESSENCE.get()) {
            return 3;
        }

        return -1;
    }

    private boolean isEssenceBucket(ItemStack stack) {
        return stack.is(ModItems.BLUE_ESSENCE_BUCKET.get())
                || stack.is(ModItems.YELLOW_ESSENCE_BUCKET.get())
                || stack.is(ModItems.WHITE_ESSENCE_BUCKET.get())
                || stack.is(ModItems.PINK_ESSENCE_BUCKET.get());
    }

    private boolean isEssenceBottle(ItemStack stack) {
        return stack.is(ModItems.BLUE_ESSENCE_BOTTLE.get())
                || stack.is(ModItems.YELLOW_ESSENCE_BOTTLE.get())
                || stack.is(ModItems.WHITE_ESSENCE_BOTTLE.get())
                || stack.is(ModItems.PINK_ESSENCE_BOTTLE.get());
    }

    public int getEssenceColor() {
        int blueAmount = blueTank.getAmount();
        int yellowAmount = yellowTank.getAmount();
        int whiteAmount = whiteTank.getAmount();
        int pinkAmount = pinkTank.getAmount();

        int total = blueAmount
                + yellowAmount
                + whiteAmount
                + pinkAmount;

        if (total <= 0) {
            return 0xFFFFFF;
        }

        int red = (
                0x0A * blueAmount
                        + 0xDC * yellowAmount
                        + 0xFF * whiteAmount
                        + 0xD2 * pinkAmount
        ) / total;

        int green = (
                0x3C * blueAmount
                        + 0xFA * yellowAmount
                        + 0xFF * whiteAmount
                        + 0x14 * pinkAmount
        ) / total;

        int blue = (
                0xFF * blueAmount
                        + 0x00 * yellowAmount
                        + 0xFF * whiteAmount
                        + 0xC8 * pinkAmount
        ) / total;

        return (red << 16)
                | (green << 8)
                | blue;
    }

    private void syncEssence() {
        setChanged();

        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    3
            );
        }
    }

    public void handleRedstoneSignal(boolean powered) {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (powered && !redstonePowered) {
            redstonePowered = true;

            tryInject();
        } else if (!powered) {
            redstonePowered = false;
        }
    }

    private void tryInject() {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (!hasRequiredItems()) {
            return;
        }

        EssenceCost essenceCost = calculateEssenceCost();

        if (essenceCost == null) {
            return;
        }

        BlockPos injectionPos = findInjectionPosition();

        if (injectionPos == null) {
            return;
        }

        ItemStack chroma = items.get(0).copyWithCount(1);

        items.get(0).shrink(1);
        items.get(1).shrink(1);

        blueTank.removeEssence(essenceCost.blue());
        yellowTank.removeEssence(essenceCost.yellow());
        whiteTank.removeEssence(essenceCost.white());
        pinkTank.removeEssence(essenceCost.pink());

        syncEssence();

        level.setBlock(injectionPos, ModBlocks.GEM_SEED.get().defaultBlockState(), Block.UPDATE_ALL);

        if (level.getBlockEntity(injectionPos) instanceof GemSeedBlockEntity gemSeed) {
            gemSeed.setChroma(chroma);
            gemSeed.determineGem();
            gemSeed.initializeDrain();
        }

        setChanged();
    }

    private boolean hasRequiredItems() {
        return !items.get(0).isEmpty()
                && items.get(0).is(ModTags.Items.CHROMAS)
                && !items.get(1).isEmpty()
                && items.get(1).is(ModItems.GEM_SEED.get());
    }

    private @Nullable EssenceCost calculateEssenceCost() {
        int blue = blueTank.getAmount();
        int yellow = yellowTank.getAmount();
        int white = whiteTank.getAmount();
        int pink = pinkTank.getAmount();

        boolean blueActive = blue > 0;
        boolean yellowActive = yellow > 0;
        boolean whiteActive = white > 0;
        boolean pinkActive = pink > 0;

        while (true) {
            int activeTypes = 0;

            if (blueActive) {
                activeTypes++;
            }

            if (yellowActive) {
                activeTypes++;
            }

            if (whiteActive) {
                activeTypes++;
            }

            if (pinkActive) {
                activeTypes++;
            }

            if (activeTypes == 0) {
                return null;
            }

            int amountPerType = 1200 / activeTypes;

            boolean removedType = false;

            if (blueActive && blue < amountPerType) {
                blueActive = false;
                removedType = true;
            }

            if (yellowActive && yellow < amountPerType) {
                yellowActive = false;
                removedType = true;
            }

            if (whiteActive && white < amountPerType) {
                whiteActive = false;
                removedType = true;
            }

            if (pinkActive && pink < amountPerType) {
                pinkActive = false;
                removedType = true;
            }

            if (!removedType) {
                return new EssenceCost(
                        blueActive ? amountPerType : 0,
                        yellowActive ? amountPerType : 0,
                        whiteActive ? amountPerType : 0,
                        pinkActive ? amountPerType : 0
                );
            }
        }
    }

    private @Nullable BlockPos findInjectionPosition() {
        if (level == null) {
            return null;
        }

        int centerIndex = SLOTS_PER_ROW / 2;

        for (int y = worldPosition.getY(); y >= MIN_Y; y -= ROW_SPACING) {

            for (int slot = 0; slot < SLOTS_PER_ROW; slot++) {
                int offset = (slot - centerIndex) * SLOT_SPACING;

                int x = worldPosition.getX();
                int z = worldPosition.getZ();

                if (injectionOrientation == InjectionOrientation.NORTH_SOUTH) {
                    x += offset;
                } else {
                    z += offset;
                }

                BlockPos pos = new BlockPos(x, y, z);

                if (level.getBlockState(pos).is(ModTags.Blocks.GEM_DRAINABLES)) {
                    return pos;
                }
            }
        }

        return null;
    }

    public void setInjectionOrientation(InjectionOrientation orientation) {
        this.injectionOrientation = orientation;
    }

    public InjectionOrientation getInjectionOrientation() {
        return injectionOrientation;
    }

    public void cycleInjectionOrientation() {
        injectionOrientation = injectionOrientation == InjectionOrientation.NORTH_SOUTH ? InjectionOrientation.EAST_WEST : InjectionOrientation.NORTH_SOUTH;

        setChanged();
    }
}