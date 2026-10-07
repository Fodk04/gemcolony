package com.fodk.gemcolony.entity.custom.gem.starter;

import com.fodk.gemcolony.construction.Assemblies;
import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.util.ColorUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class PebbleEntity extends StarterGemEntity{

    private static final Color darkSkin = new Color(133, 93, 122);
    private static final Color lightSkin = new Color(184, 130, 172);
    private static final Color darkOutfit = new Color(177, 71, 147);
    private static final Color lightOutfit = new Color(237, 124, 214);
    private static final Color darkInsignia = new Color(159, 11, 115);
    private static final Color lightInsignia = new Color(216, 5, 154);

    @Override
    public List<Assembly> getConstructableAssemblies() {
        return List.of(Assemblies.WORKSTATION);
    }

    public PebbleEntity(EntityType<? extends StarterGemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Color generateGemColor() {
        return ColorUtil.lerpColor(lightSkin, darkSkin, random.nextFloat());
    }

    @Override
    public Color generateOutfitColor() {
        return ColorUtil.lerpColor(lightOutfit, darkOutfit, random.nextFloat());
    }

    @Override
    public Color generateInsigniaColor() {
        return ColorUtil.lerpColor(lightInsignia, darkInsignia, random.nextFloat());
    }

    @Override
    public Item getGemItem() {
        return ModItems.PEBBLE_GEM.get();
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (3 * qualityModifier);
    }

    @Override
    public String getGemTypeName() {
        return "Pebble";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.CONSTRUCTOR);
    }

    @Override
    public float getModelSize() {
        return 0.4f;
    }
}
