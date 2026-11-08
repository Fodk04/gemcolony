package com.fodk.gemcolony.entity.custom.gem.quartz;

import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.placement.GemPlacement;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

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
    public double getBaseMaxHealth() {
        return 90;
    }

    @Override
    public double getBaseAttackDamage() {
        return 6.5;
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
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (20 * qualityModifier);
    }

    //1.5 MIN
    @Override
    public int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(1800f * modifier);
    }

    @Override
    public String getNameWithoutVariant() {
        return "Jasper";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.SHAPESHIFTER);
        addAbility(GemAbility.CHARGEBALL);
    }

    private static final int NAVY_OUTFIT = 1;
    @Override
    protected void appearancePostProcess() {
        if(GemPlacement.values()[getGemPlacement()] == GemPlacement.NAVEL){
            setOutfit(NAVY_OUTFIT);
        }
    }
}
