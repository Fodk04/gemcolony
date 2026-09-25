package com.fodk.gemcolony.entity.custom.gem.starter;

import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.construction.Blueprint;
import com.fodk.gemcolony.entity.custom.GemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class StarterGemEntity extends GemEntity {

    public abstract List<Assembly> getConstructableAssemblies();

    protected StarterGemEntity(EntityType<? extends StarterGemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public boolean canConstruct(Assembly assembly) {
        return getConstructableAssemblies().contains(assembly);
    }
}
