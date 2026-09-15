package com.fodk.gemcolony.entity.custom;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.item.custom.GemItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GemRisingItemEntity extends ItemEntity {

    public GemRisingItemEntity(EntityType<? extends ItemEntity> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();
        ItemStack item = getItem();
        Level level = level();
        GemItem gemItem = (GemItem) item.getItem();
        int reformProgress = item.has(ModDataComponents.REFORM_PROGRESS) ? item.get(ModDataComponents.REFORM_PROGRESS) : 0;
        if(!level.isClientSide()){
            if(GemEntity.getReformProgressPercentage(reformProgress) >= 0.55f){
                GemEntity gem = gemItem.entityType.create((ServerLevel) level, null, blockPosition(), EntitySpawnReason.SPAWN_ITEM_USE, false, false);
                gem.applySaveData(item.get(ModDataComponents.GEM_SAVE_DATA));
                gem.initializeGem(reformProgress);
                gem.setPos(position());
                level.addFreshEntity(gem);
                discard();
            }else{
                reformProgress--;
                item.set(ModDataComponents.REFORM_PROGRESS, reformProgress);
                setDeltaMovement(Vec3.ZERO);
                move(MoverType.SELF, new Vec3(0, 0.02d,0));
            }
        }
    }

}
