package com.fodk.gemcolony.entity.custom.gem;

import com.fodk.gemcolony.entity.custom.GemEntity;
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

    Color darkSkin = new Color(0, 100, 0);
    Color lightSkin = new Color(0, 180,0);
    Color darkOutfit = new Color(20, 80, 0);
    Color lightOutfit = new Color(40, 160, 0);
    Color darkInsignia = new Color(75, 100, 0);
    Color lightInsignia = new Color(150, 200, 0);
    Color darkHair = new Color(150, 160, 0);
    Color lightHair = new Color(220, 240, 0);
    Color darkVisor = new Color(180, 180, 0);
    Color lightVisor = new Color(230, 230, 0);

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
        super.generateAppearance(gemColor, 3, outfitColor, 4, insigniaColor, 4, hairColor, 2, visorColor);
    }

    @Override
    protected Item getGemItem() {
        return ModItems.PERIDOT_GEM.get();
    }

    @Override
    public float getReformCenter() {
        return 1.15f;
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (30 * qualityModifier);
    }
}
