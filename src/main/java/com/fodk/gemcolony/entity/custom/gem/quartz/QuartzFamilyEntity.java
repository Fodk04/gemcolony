package com.fodk.gemcolony.entity.custom.gem.quartz;

import com.fodk.gemcolony.entity.custom.gem.ai.GemAttackGoal;
import com.fodk.gemcolony.entity.custom.gem.ai.GemGuardGoal;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class QuartzFamilyEntity extends GemEntity {

    public QuartzFamilyEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected boolean canWork() {
        return true;
    }

    @Override
    protected String getWorkMessage() {
        return "guard this area.";
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new GemAttackGoal(this, 1.1D));
        this.goalSelector.addGoal(11, new GemGuardGoal(this, 1.0D));
    }
}
