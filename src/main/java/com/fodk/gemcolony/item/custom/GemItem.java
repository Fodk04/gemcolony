package com.fodk.gemcolony.item.custom;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.data.ReformRegistryData;
import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.GemRisingItemEntity;
import com.fodk.gemcolony.entity.custom.GemSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class GemItem extends Item {

    private final String name;
    public final EntityType<? extends GemEntity> entityType;

    public GemItem(Properties properties, String name, EntityType<? extends GemEntity> entityType) {
        super(properties.fireResistant().stacksTo(1));
        this.name = name;
        this.entityType = entityType;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return InteractionResult.SUCCESS;
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
}
