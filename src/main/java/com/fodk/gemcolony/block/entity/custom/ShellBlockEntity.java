package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.custom.ShellBlock;
import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.block.entity.client.renderstate.ShellRenderState;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.ChromaItem;
import com.fodk.gemcolony.item.custom.EssenceType;
import com.fodk.gemcolony.menu.ShellMenu;
import com.fodk.gemcolony.tags.ModTags;
import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ShellBlockEntity extends BlockEntity implements Container, GeoBlockEntity {

    private final NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    private static final int CHROMA_SLOT = 0;
    private static final int GEM_SEED_SLOT = 1;
    public static final int PEARL_SLOT = 2;

    private final EssenceTank whiteTank = new EssenceTank(EssenceType.WHITE);

    private final ShellEssenceResourceHandler essenceHandler = new ShellEssenceResourceHandler(whiteTank);

    private static final int ESSENCE_COST = 750;

    private static final int DRAIN_RADIUS = 4;
    private static final int DRAIN_DEPTH = 5;
    private static final int BLOCKS_NEEDED = 50;

    private static final int WATER_RADIUS = 2;
    private static final int WATER_HEIGHT = 1;
    private static final int WATER_BLOCKS_NEEDED =
            (WATER_RADIUS * 2 + 1)
                    * (WATER_RADIUS * 2 + 1)
                    * WATER_HEIGHT;

    private static final int DRAIN_INTERVAL = 200;

    private boolean draining = false;
    public boolean isDraining() {
        return draining;
    }

    private int drainTimer = 0;
    private int drained = 0;
    private int waterDrained = 0;

    private int chromaColorIndex = 0;

    private final List<BlockPos> drainableBlocks = new ArrayList<>();
    private final List<BlockPos> waterBlocks = new ArrayList<>();

    public float getPearlProgress() {
        return Math.min(1.0F, (float) drained / BLOCKS_NEEDED);
    }

    public ShellBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHELL_BE.get(), pos, state);
    }

    public ShellEssenceResourceHandler getEssenceHandler() {
        return essenceHandler;
    }

    public EssenceTank getWhiteTank() {
        return whiteTank;
    }

    public int getWhiteEssenceAmount() {
        return whiteTank.getAmount();
    }

    public int getWhiteEssenceCapacity() {
        return whiteTank.getCapacity();
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
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
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        ContainerHelper.saveAllItems(output, items);

        output.putInt("WhiteEssence", whiteTank.getAmount());
        output.putBoolean("Draining", draining);
        output.putInt("DrainTimer", drainTimer);
        output.putInt("Drained", drained);
        output.putInt("WaterDrained", waterDrained);
        output.putInt("ChromaColorIndex", chromaColorIndex);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        ContainerHelper.loadAllItems(input, items);

        whiteTank.setAmount(input.getIntOr("WhiteEssence", 0));
        draining = input.getBooleanOr("Draining", false);
        drainTimer = input.getIntOr("DrainTimer", 0);
        drained = input.getIntOr("Drained", 0);
        waterDrained = input.getIntOr("WaterDrained", 0);
        chromaColorIndex = input.getIntOr("ChromaColorIndex", 0);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = saveWithoutMetadata(registries);

        tag.putInt("WhiteEssence", whiteTank.getAmount());
        tag.putBoolean("Draining", draining);

        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput input) {
        super.onDataPacket(net, input);

        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(input, items);

        whiteTank.setAmount(input.getIntOr("WhiteEssence", 0));
        draining = input.getBooleanOr("Draining", false);
    }

    public InteractionResult handleInteraction(Player player, ItemStack stack) {
        if (level == null || level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if(!items.get(PEARL_SLOT).isEmpty()){
            ItemStack pearl = items.get(PEARL_SLOT).copy();
            setItem(PEARL_SLOT, ItemStack.EMPTY);

            syncEssence();

            Vec3 dropPos = Vec3.atCenterOf(worldPosition);

            ItemEntity itemEntity = new ItemEntity(
                    level,
                    dropPos.x,
                    dropPos.y + 0.3,
                    dropPos.z,
                    pearl
            );

            level.addFreshEntity(itemEntity);

            return InteractionResult.SUCCESS;
        }

        if (isWhiteEssenceBucket(stack) || isWhiteEssenceBottle(stack)) {

            int amount = isWhiteEssenceBucket(stack) ? 1000 : 250;
            FluidResource resource = FluidResource.of(ModFluids.WHITE_ESSENCE.get());
            try (Transaction transaction = Transaction.openRoot()) {
                int inserted = essenceHandler.insert(0, resource, amount, transaction);

                if (inserted != amount) {
                    return InteractionResult.PASS;
                }

                transaction.commit();
            }

            ItemStack emptyContainer = amount == 1000
                            ? new ItemStack(Items.BUCKET)
                            : new ItemStack(Items.GLASS_BOTTLE);

            if (!player.isCreative()) {
                stack.shrink(1);

                if (stack.isEmpty()) {
                    player.setItemInHand(player.getUsedItemHand(), emptyContainer);
                } else if (!player.getInventory().add(emptyContainer)) {
                    player.drop(emptyContainer, false);
                }
            }

            syncEssence();

            level.playSound(
                    null,
                    worldPosition,
                    amount == 1000
                            ? SoundEvents.BUCKET_EMPTY
                            : SoundEvents.BOTTLE_EMPTY,
                    SoundSource.BLOCKS,
                    1.0F,
                    1.0F
            );

            return InteractionResult.SUCCESS;
        }

        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (containerId, playerInventory, ignoredPlayer) -> new ShellMenu(containerId, playerInventory, this),
                            Component.literal("Shell")
                    ),
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

    private boolean isWhiteEssenceBucket(ItemStack stack) {
        return stack.is(ModItems.WHITE_ESSENCE_BUCKET.get());
    }

    private boolean isWhiteEssenceBottle(ItemStack stack) {
        return stack.is(ModItems.WHITE_ESSENCE_BOTTLE.get());
    }

    //ATP more than essence
    private void syncEssence() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void tick() {
        if (level == null || level.isClientSide()) {
            return;
        }

        if (!draining) {
            tryStartDraining();
            return;
        }

        drainTimer++;

        if (drainTimer < DRAIN_INTERVAL) {
            return;
        }

        drainTimer = 0;

        drainBlock();
        drainWaterBlock();

        if (drained >= BLOCKS_NEEDED) {
            finishPearl();
        }
    }

    private void tryStartDraining() {
        if (level == null || level.isClientSide() || draining) {
            return;
        }

        if (items.get(0).isEmpty() || items.get(1).isEmpty()) {
            return;
        }

        if (!items.get(PEARL_SLOT).isEmpty()) {
            return;
        }

        if (whiteTank.getAmount() < ESSENCE_COST) {
            return;
        }

        ChromaItem chromaItem = (ChromaItem) items.get(CHROMA_SLOT).getItem();
        chromaColorIndex = chromaItem.colorIndex;
        items.get(0).shrink(1);
        items.get(1).shrink(1);
        whiteTank.setAmount(whiteTank.getAmount() - ESSENCE_COST);

        draining = true;
        drainTimer = 0;
        drained = 0;
        waterDrained = 0;

        drainableBlocks.clear();
        waterBlocks.clear();

        syncEssence();
    }

    private void drainBlock() {
        if (drained >= BLOCKS_NEEDED) {
            return;
        }

        if (drainableBlocks.isEmpty()) {
            findDrainableBlocks();
        }

        if (drainableBlocks.isEmpty()) {
            return;
        }

        BlockPos target = drainableBlocks.get(level.getRandom().nextInt(drainableBlocks.size()));

        if (!level.getBlockState(target).is(ModTags.Blocks.SHELL_DRAINABLE)) {
            drainableBlocks.remove(target);
            return;
        }

        level.setBlock(target, ModBlocks.DRAINED_STONE.get().defaultBlockState(), 3);

        drainableBlocks.remove(target);
        drained++;

        syncEssence();
    }

    private void findDrainableBlocks() {
        for (int x = -DRAIN_RADIUS; x <= DRAIN_RADIUS; x++) {
            for (int y = 1; y <= DRAIN_DEPTH; y++) {
                for (int z = -DRAIN_RADIUS; z <= DRAIN_RADIUS; z++) {

                    BlockPos pos = worldPosition.offset(x, -y, z);

                    if (level.getBlockState(pos).is(ModTags.Blocks.SHELL_DRAINABLE)) {
                        drainableBlocks.add(pos);
                    }
                }
            }
        }
    }

    private void drainWaterBlock() {
        if (waterDrained >= WATER_BLOCKS_NEEDED) {
            return;
        }

        if (waterBlocks.isEmpty()) {
            findWaterBlocks();
        }

        if (waterBlocks.isEmpty()) {
            return;
        }

        BlockPos target = waterBlocks.get(level.getRandom().nextInt(waterBlocks.size()));
        BlockState state = level.getBlockState(target);

        if (state.is(Blocks.WATER)) {
            level.setBlock(target, Blocks.AIR.defaultBlockState(), 3);
        } else if (state.getBlock() instanceof ShellBlock && state.getValue(ShellBlock.WATERLOGGED)) {
            BlockState drainedState = state.setValue(ShellBlock.WATERLOGGED, false);
            level.setBlock(target, drainedState, 3);
            if (ShellBlock.hasEnoughWaterNeighbors(level, target)) {
                level.setBlock(target, drainedState.setValue(ShellBlock.WATERLOGGED, true), 3);
            }
        } else {
            waterBlocks.remove(target);
            return;
        }

        waterBlocks.remove(target);
        waterDrained++;

        setChanged();
    }

    private void findWaterBlocks() {
        for (int x = -WATER_RADIUS; x <= WATER_RADIUS; x++) {
            for (int y = 0; y <= WATER_HEIGHT; y++) {
                for (int z = -WATER_RADIUS; z <= WATER_RADIUS; z++) {

                    BlockPos pos = worldPosition.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);

                    if (state.is(Blocks.WATER) || (state.getBlock() instanceof ShellBlock && state.getValue(ShellBlock.WATERLOGGED))) {
                        waterBlocks.add(pos);
                    }
                }
            }
        }
    }

    private void finishPearl() {
        int quality = determinePearlQuality();

        GemEntity gem = ModEntities.PEARL.get().create((ServerLevel) level, null, worldPosition, EntitySpawnReason.NATURAL, false, false);
        gem.initializeGemFromChroma(chromaColorIndex);
        gem.setQuality(quality);
        ItemStack pearlGemItem = new ItemStack(gem.getGemItem());
        pearlGemItem.set(ModDataComponents.GEM_SAVE_DATA, gem.toSaveData());
        pearlGemItem.set(ModDataComponents.REFORM_TIME, 0);
        pearlGemItem.set(ModDataComponents.REFORM_PROGRESS, GemEntity.maxReformProgress);

        gem.discard();

        items.set(PEARL_SLOT, pearlGemItem);
        draining = false;
        syncEssence();
    }

    private int determinePearlQuality() {
        if(waterDrained <= 9){
            return level.getRandom().nextFloat() <= 0.60 ? 0 : 1;
        }
        if(waterDrained <= 16){
            return level.getRandom().nextFloat() <= 0.60 ? 1 : 2;
        }else{
            return 2;
        }
    }

    //ANIM
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final DataTicket<Boolean> DRAINING = DataTicket.create("draining", Boolean.class);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(
                        "lid_controller",
                        0,
                        state -> {
                            boolean draining = state.renderState().getOrDefaultGeckolibData(ShellBlockEntity.DRAINING, false);

                            if (draining) {
                                return state.setAndContinue(
                                        RawAnimation.begin()
                                                .thenPlayAndHold("lid_close")
                                );
                            }

                            return state.setAndContinue(
                                    RawAnimation.begin()
                                            .thenPlayAndHold("lid_open")
                            );
                        }
                )
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
