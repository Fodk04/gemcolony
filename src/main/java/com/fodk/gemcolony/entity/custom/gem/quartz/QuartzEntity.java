package com.fodk.gemcolony.entity.custom.gem.quartz;

import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.variant.QuartzVariants;
import com.fodk.gemcolony.util.ColorUtil;
import com.fodk.gemcolony.util.GemVariantUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.awt.*;

public class QuartzEntity extends QuartzFamilyEntity {

    public QuartzEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.ATTACK_SPEED, 1.4D);
    }

    @Override
    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));

        gemColor = quartzVariant.getSkinColor(random.nextFloat());
        outfitColor = quartzVariant.getOutfitColor(random.nextFloat());
        insigniaColor = quartzVariant.getInsigniaColor(random.nextFloat());
        hairColor = quartzVariant.getHairColor(random.nextFloat());
        visorColor = quartzVariant.getVisorColor(random.nextFloat());
        super.generateAppearance(gemColor, getMaxOutfits(), outfitColor,
                getMaxInsignias(), insigniaColor,
                getMaxHairstyles(), hairColor,
                getMaxVisors(), visorColor);
    }

    @Override
    public Item getGemItem() {
        return GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT)).getGemItem();
    }

    @Override
    public int getMaxOutfits() {
        return 1;
    }

    @Override
    public int getMaxInsignias() {
        return 2;
    }

    @Override
    public int getMaxHairstyles() {
        return 15;
    }

    @Override
    public int getMaxVisors() {
        return 4;
    }

    @Override
    protected int getInventorySize() {
        return 6;
    }

    //1 MIN
    @Override
    public int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(1200f * modifier);
    }

    @Override
    public String getGemTypeName() {
        return GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT)).getName() + " Quartz";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.SHAPESHIFTER);
        addAbility(GemAbility.CHARGEBALL);
    }

    @Override
    public int getRandomVariant() {
        return random.nextInt(QuartzVariants.values().length);
    }

    @Override
    public int getVariantFromChroma(int chromaIndex) {
        return GemVariantUtil.getVariantFromChromaColor(QuartzVariants.class, chromaIndex);
    }
}
