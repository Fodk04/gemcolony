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

public class NacreEntity extends StarterGemEntity{

    private static final Color darkSkin = new Color(71, 71, 71);
    private static final Color lightSkin = new Color(126, 125, 125);
    private static final Color darkOutfit = new Color(145, 145, 145);
    private static final Color lightOutfit = new Color(255, 255, 255);
    private static final Color darkInsignia = new Color(145, 145, 145);
    private static final Color lightInsignia = new Color(255, 255, 255);

    @Override
    public List<Assembly> getConstructableAssemblies() {
        return List.of(Assemblies.SHELL);
    }

    public NacreEntity(EntityType<? extends StarterGemEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        gemColor = ColorUtil.lerpColor(lightSkin, darkSkin, random.nextFloat());
        outfitColor = ColorUtil.lerpColor(lightOutfit, darkOutfit, random.nextFloat());
        insigniaColor = ColorUtil.lerpColor(lightInsignia, darkInsignia, random.nextFloat());
        super.generateAppearance(gemColor, getMaxOutfits(), outfitColor,
                getMaxInsignias(), insigniaColor,
                getMaxHairstyles(), hairColor,
                getMaxVisors(), visorColor);
    }

    @Override
    public Item getGemItem() {
        return ModItems.NACRE_GEM.get();
    }

    @Override
    public int getMaxOutfits() {
        return 3;
    }

    @Override
    public int getMaxInsignias() {
        return 3;
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
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (3 * qualityModifier);
    }

    @Override
    public String getGemTypeName() {
        return "Nacre";
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
