package com.fodk.gemcolony.entity.custom.gem.starter;

import com.fodk.gemcolony.construction.Assemblies;
import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;

public class NacreEntity extends StarterGemEntity{

    @Override
    public List<Assembly> getConstructableAssemblies() {
        return List.of(Assemblies.SHELL);
    }

    protected NacreEntity(EntityType<? extends StarterGemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Item getGemItem() {
        return ModItems.PEBBLE_GEM.get();
    }

    @Override
    public float getReformCenter() {
        return 0;
    }

    @Override
    public int getMaxOutfits() {
        return 0;
    }

    @Override
    public int getMaxInsignias() {
        return 0;
    }

    @Override
    public int getMaxHairstyles() {
        return 0;
    }

    @Override
    public int getMaxVisors() {
        return 0;
    }

    @Override
    protected int getInventorySize() {
        return 5;
    }

    @Override
    public String getGemTypeName() {
        return "Nacre";
    }

    @Override
    protected void initializeAbilities() {

    }

    @Override
    public float getModelSize() {
        return 1f;
    }
}
