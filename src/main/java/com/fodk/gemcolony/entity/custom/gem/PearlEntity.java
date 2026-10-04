package com.fodk.gemcolony.entity.custom.gem;

import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.variant.PearlVariants;
import com.fodk.gemcolony.util.GemVariantUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.awt.*;

public class PearlEntity extends GemEntity {

    public PearlEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.ATTACK_SPEED, 1.4D);
    }

    @Override
    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));

        gemColor = pearlVariants.getSkinColor(random.nextFloat());
        outfitColor = pearlVariants.getOutfitColor(random.nextFloat());
        insigniaColor = pearlVariants.getInsigniaColor(random.nextFloat());
        hairColor = pearlVariants.getHairColor(random.nextFloat());
        visorColor = pearlVariants.getVisorColor(random.nextFloat());
        super.generateAppearance(gemColor, getMaxOutfits(), outfitColor,
                getMaxInsignias(), insigniaColor,
                getMaxHairstyles(), hairColor,
                getMaxVisors(), visorColor);
    }

    @Override
    public Item getGemItem() {
        return GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT)).getGemItem();
    }

    @Override
    public int getMaxOutfits() {
        return 5;
    }

    @Override
    public int getMaxInsignias() {
        return 5;
    }

    @Override
    public int getMaxHairstyles() {
        return 5;
    }

    @Override
    public int getMaxVisors() {
        return 0;
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (36 * qualityModifier);
    }

    //2 MIN
    @Override
    protected int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(2400f * modifier);
    }

    @Override
    public String getGemTypeName() {
        return GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT)).getName() + " Pearl";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.DANCER);
        addAbility(GemAbility.HOLOPEARL);
    }

    @Override
    public int getRandomVariant() {
        return random.nextInt(PearlVariants.values().length);
    }

    @Override
    public int getVariantFromChroma(int chromaIndex) {
        return GemVariantUtil.getVariantFromChromaColor(PearlVariants.class, chromaIndex);
    }
}
