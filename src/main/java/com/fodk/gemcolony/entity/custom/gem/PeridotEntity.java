package com.fodk.gemcolony.entity.custom.gem;

import com.fodk.gemcolony.entity.custom.GemEntity;
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

public class PeridotEntity extends GemEntity {

    private static final Color darkSkin = new Color(0, 100, 0);
    private static final Color lightSkin = new Color(0, 180,0);
    private static final Color darkOutfit = new Color(20, 80, 0);
    private static final Color lightOutfit = new Color(40, 160, 0);
    private static final Color darkInsignia = new Color(75, 100, 0);
    private static final Color lightInsignia = new Color(150, 200, 0);
    private static final Color darkHair = new Color(150, 160, 0);
    private static final Color lightHair = new Color(220, 240, 0);
    private static final Color darkVisor = new Color(180, 180, 0);
    private static final Color lightVisor = new Color(230, 230, 0);

    public PeridotEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_SPEED, 1.4D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        gemColor = ColorUtil.lerpColor(lightSkin, darkSkin, random.nextFloat());
        outfitColor = ColorUtil.lerpColor(lightOutfit, darkOutfit, random.nextFloat());
        insigniaColor = ColorUtil.lerpColor(lightInsignia, darkInsignia, random.nextFloat());
        hairColor = ColorUtil.lerpColor(lightHair, darkHair, random.nextFloat());
        visorColor = ColorUtil.lerpColor(lightVisor, darkVisor, random.nextFloat());
        super.generateAppearance(gemColor, getMaxOutfits(), outfitColor,
                getMaxInsignias(), insigniaColor,
                getMaxHairstyles(), hairColor,
                getMaxVisors(), visorColor);
    }

    @Override
    public Item getGemItem() {
        return ModItems.PERIDOT_GEM.get();
    }

    @Override
    public float getReformCenter() {
        return 1.15f;
    }

    @Override
    public int getMaxOutfits() {
        return 3;
    }

    @Override
    public int getMaxInsignias() {
        return 4;
    }

    @Override
    public int getMaxHairstyles() {
        return 4;
    }

    @Override
    public int getMaxVisors() {
        return 2;
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (30 * qualityModifier);
    }

    @Override
    protected String getGemTypeName() {
        return "Peridot";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.KINDERGARTNER);
        addAbility(GemAbility.FERROKINESIS);
    }

}
