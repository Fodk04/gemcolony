package com.fodk.gemcolony.entity.custom.gem.quartz;

import com.fodk.gemcolony.entity.custom.GemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class QuartzFamilyEntity extends GemEntity {

    public QuartzFamilyEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }
}
