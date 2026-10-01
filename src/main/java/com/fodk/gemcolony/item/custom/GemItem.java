package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.base.GemRisingItemEntity;
import com.fodk.gemcolony.entity.custom.gem.bubble.BubbleEntity;
import com.fodk.gemcolony.entity.custom.gem.savedata.GemSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public class GemItem extends Item {

    private final String name;
    public final EntityType<? extends GemEntity> entityType;
    private int variant = -1;

    public GemItem(Properties properties, String name, EntityType<? extends GemEntity> entityType) {
        super(properties.fireResistant().stacksTo(1));
        this.name = name;
        this.entityType = entityType;
    }

    public GemItem(Properties properties, String name, EntityType<? extends GemEntity> entityType, int variant) {
        super(properties.fireResistant().stacksTo(1));
        this.name = name;
        this.entityType = entityType;
        this.variant = variant;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if(!level.isClientSide()){
            if(!context.getItemInHand().has(ModDataComponents.GEM_SAVE_DATA)){
                BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
                GemEntity gem = entityType.create((ServerLevel) level, null, pos, EntitySpawnReason.NATURAL, false, false);
                gem.initializeGem(variant);
                level.addFreshEntity(gem);

                if(!context.getPlayer().isCreative()){
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            if(context.getItemInHand().has(ModDataComponents.BUBBLED)){
                BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
                BubbleEntity bubble = ModEntities.BUBBLE.get().create((ServerLevel) level, null, pos, EntitySpawnReason.NATURAL, false, false);
                ItemStack gem = context.getItemInHand().copy();
                gem.remove(ModDataComponents.BUBBLED);
                bubble.setItem(gem);
                level.addFreshEntity(bubble);

                if(!context.getPlayer().isCreative()){
                    context.getItemInHand().shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        if(itemStack.has(ModDataComponents.GEM_SAVE_DATA)){
            GemSaveData gemSaveData = itemStack.get(ModDataComponents.GEM_SAVE_DATA);
            String fullName;

            if(gemSaveData.gemAppearanceData().nickname().isBlank()){
                fullName = gemSaveData.gemAppearanceData().name();
            }else{
                fullName = name + " " + gemSaveData.gemAppearanceData().nickname();
            }
            builder.accept(Component.literal(fullName));
        }
    }

    int periodicCheck = 30;
    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if(!level.isClientSide() && itemStack.has(ModDataComponents.GEM_SAVE_DATA)){
            long gameTime = level.getGameTime();
            if(gameTime % periodicCheck == 0){
                int reformTime = itemStack.has(ModDataComponents.REFORM_TIME) ? itemStack.get(ModDataComponents.REFORM_TIME) : 0;
                boolean bubbled = itemStack.has(ModDataComponents.BUBBLED) ? itemStack.get(ModDataComponents.BUBBLED) : false;
                if(reformTime <= 0){
                    if(!bubbled){
                        GemRisingItemEntity risingEntity = new GemRisingItemEntity(ModEntities.GEM_RISING_ITEM.get(), level);
                        risingEntity.setPos(owner.position());
                        risingEntity.setItem(itemStack.copy());// carries the GEM_SAVE_DATA/REFORM_PROGRESS components across intact
                        risingEntity.setNeverPickUp();
                        level.addFreshEntity(risingEntity);
                        itemStack.shrink(1);
                    }
                }else{
                    reformTime -= periodicCheck;
                    itemStack.set(ModDataComponents.REFORM_TIME, reformTime);
                }
            }
        }
        //TO FIX
        //GEM DUPPING !!
        //HERE

        /*if (!itemStack.has(ModDataComponents.GEM_SAVE_DATA)) return;

        UUID itemId = itemStack.get(ModDataComponents.GEM_ITEM_ID);
        if (itemId == null) {
            itemId = UUID.randomUUID();
            itemStack.set(ModDataComponents.GEM_ITEM_ID, itemId); // one-time write, forever after this is immutable
        }

        boolean completed = ReformRegistryData.get(level).tickDown(itemId, itemStack.get(ModDataComponents.REFORM_TIME));

        if (itemStack.getOrDefault(ModDataComponents.BUBBLED, false)) return;

        if (completed) {
            GemRisingItemEntity risingEntity = new GemRisingItemEntity(ModEntities.GEM_RISING_ITEM.get(), level);
            risingEntity.setPos(owner.position());
            risingEntity.setItem(itemStack.copy());
            risingEntity.setNeverPickUp();
            level.addFreshEntity(risingEntity);
            itemStack.shrink(1);
        }*/

        super.inventoryTick(itemStack, level, owner, slot);
    }

    public void tickReformation(ItemEntity itemEntity) {
        ItemStack item = itemEntity.getItem();
        Level level = itemEntity.level();

        if(!level.isClientSide() && item.has(ModDataComponents.GEM_SAVE_DATA)){
            int reformTime = item.has(ModDataComponents.REFORM_TIME) ? item.get(ModDataComponents.REFORM_TIME) : 0;
            boolean bubbled = item.has(ModDataComponents.BUBBLED) ? item.get(ModDataComponents.BUBBLED) : false;
            if(reformTime <= 0 && !bubbled){
                GemRisingItemEntity risingEntity = new GemRisingItemEntity(ModEntities.GEM_RISING_ITEM.get(), level);
                risingEntity.setPos(itemEntity.position());
                risingEntity.setItem(item.copy());
                risingEntity.setNeverPickUp();// carries the GEM_SAVE_DATA/REFORM_PROGRESS components across intact
                level.addFreshEntity(risingEntity);
                itemEntity.discard();
            }else{
                reformTime--;
                item.set(ModDataComponents.REFORM_TIME, reformTime);
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || oldStack.getItem() != newStack.getItem();
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return super.onDroppedByPlayer(item, player);
    }

    public void tickInBubble(Level level, ItemStack itemStack){
        if(!level.isClientSide() && itemStack.has(ModDataComponents.GEM_SAVE_DATA)){
            long gameTime = level.getGameTime();
            if(gameTime % periodicCheck == 0){
                int reformTime = itemStack.has(ModDataComponents.REFORM_TIME) ? itemStack.get(ModDataComponents.REFORM_TIME) : 0;
                if(reformTime > 0){
                    reformTime -= periodicCheck;
                    itemStack.set(ModDataComponents.REFORM_TIME, reformTime);
                }
            }
        }
    }
}
