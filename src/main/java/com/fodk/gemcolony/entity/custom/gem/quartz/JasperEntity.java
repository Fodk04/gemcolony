package com.fodk.gemcolony.entity.custom.gem.quartz;

import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.variant.JasperVariants;
import com.fodk.gemcolony.util.GemVariantUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.awt.*;

public class JasperEntity extends QuartzFamilyEntity {

    public JasperEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 90D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 6.5D)
                .add(Attributes.ATTACK_SPEED, 1.4D);
    }

    @Override
    public Item getGemItem() {
        return GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT)).getGemItem();
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
        return 1;
    }

    @Override
    public int getMaxVisors() {
        return 4;
    }

    @Override
    public Color generateGemColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return jasperVariant.getSkinColor(random.nextFloat());
    }

    @Override
    public Color generateOutfitColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return jasperVariant.getOutfitColor(random.nextFloat());
    }

    @Override
    public Color generateInsigniaColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return jasperVariant.getInsigniaColor(random.nextFloat());
    }

    @Override
    public Color generateHairColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return jasperVariant.getHairColor(random.nextFloat());
    }

    @Override
    public Color generateMarkingsColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return JasperVariants.getMarkingsColor(jasperVariant.getLightMarkings(), jasperVariant.getDarkMarkings(), random.nextFloat());
    }

    @Override
    public Color generateVisorColor() {
        JasperVariants jasperVariant = GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT));
        return jasperVariant.getVisorColor(random.nextFloat());
    }

    @Override
    protected int getInventorySize() {
        return 6;
    }

    //1.5 MIN
    @Override
    public int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(1800f * modifier);
    }

    @Override
    public String getGemTypeName() {
        return GemVariantUtil.getById(JasperVariants.class, entityData.get(VARIANT)).getName() + " Jasper";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.SHAPESHIFTER);
        addAbility(GemAbility.CHARGEBALL);
    }

    @Override
    public int getRandomVariant() {
        return random.nextInt(JasperVariants.values().length);
    }

    @Override
    public int getVariantFromChroma(int chromaIndex) {
        return GemVariantUtil.getVariantFromChromaColor(JasperVariants.class, chromaIndex);
    }
}
