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
    public double getBaseMaxHealth() {
        return 80;
    }

    @Override
    public double getBaseAttackDamage() {
        return 6;
    }

    @Override
    public double getBaseMovementSpeed() {
        return 0.28;
    }

    @Override
    public double getBaseAttackSpeed() {
        return 1.4;
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
    public int getMaxMarkings() {
        return 0;
    }

    @Override
    public int getMaxVisors() {
        return 4;
    }

    @Override
    public Color generateGemColor() {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));
        return quartzVariant.getSkinColor(random.nextFloat());
    }

    @Override
    public Color generateOutfitColor() {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));
        return quartzVariant.getOutfitColor(random.nextFloat());
    }

    @Override
    public Color generateInsigniaColor() {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));
        return quartzVariant.getInsigniaColor(random.nextFloat());
    }

    @Override
    public Color generateHairColor() {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));
        return quartzVariant.getHairColor(random.nextFloat());
    }

    @Override
    public Color generateMarkingsColor() {
        return Color.black;
    }

    @Override
    public Color generateVisorColor() {
        QuartzVariants quartzVariant = GemVariantUtil.getById(QuartzVariants.class, entityData.get(VARIANT));
        return quartzVariant.getVisorColor(random.nextFloat());
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (20 * qualityModifier);
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
    public String getNameWithoutVariant() {
        return "Quartz";
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
