package com.fodk.gemcolony.entity.custom.gem.starter;

import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.construction.Blueprint;
import com.fodk.gemcolony.data.FacetRegistryData;
import com.fodk.gemcolony.entity.custom.GemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
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

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.ATTACK_SPEED, 0.0D);
    }

    @Override
    public void assignOrigin(ServerLevel level, BlockPos pos) {
        String name = getGemTypeName();
        this.entityData.set(NAME, name);
        setCustomName(Component.literal(name));
    }
}
