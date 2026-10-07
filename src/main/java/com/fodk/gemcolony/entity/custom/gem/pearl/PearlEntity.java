package com.fodk.gemcolony.entity.custom.gem.pearl;

import com.fodk.gemcolony.entity.ModEntities;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.variant.PearlVariants;
import com.fodk.gemcolony.util.ColorUtil;
import com.fodk.gemcolony.util.GemVariantUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
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
    public int getMaxMarkings() {
        return 0;
    }

    @Override
    public int getMaxVisors() {
        return 0;
    }

    @Override
    public Color generateGemColor() {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));
        return pearlVariants.getSkinColor(random.nextFloat());
    }

    @Override
    public Color generateOutfitColor() {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));
        return pearlVariants.getOutfitColor(random.nextFloat());
    }

    @Override
    public Color generateInsigniaColor() {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));
        return pearlVariants.getInsigniaColor(random.nextFloat());
    }

    @Override
    public Color generateHairColor() {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));
        return pearlVariants.getHairColor(random.nextFloat());
    }

    @Override
    public Color generateMarkingsColor() {
        return Color.black;
    }

    @Override
    public Color generateVisorColor() {
        PearlVariants pearlVariants = GemVariantUtil.getById(PearlVariants.class, entityData.get(VARIANT));
        return pearlVariants.getVisorColor(random.nextFloat());
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (36 * qualityModifier);
    }

    //2 MIN
    @Override
    public int getReformTime(){
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

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(7, new AvoidEntityGoal<>(this, LivingEntity.class,
                16.0F,
                1.1D,
                1.2D,
                entity -> entity == this.getTarget()));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean hurt = hurtGemServer(level, source, amount);
        createHolopearl(level, source, hurt);
        return hurt;
    }

    void createHolopearl(ServerLevel level, DamageSource source, boolean hurt){
        if (hasAbility(GemAbility.HOLOPEARL) && level.getRandom().nextFloat() < 0.30f){
            if (hurt && source.getEntity() instanceof LivingEntity attacker) {
                HolopearlEntity holopearl = ModEntities.HOLOPEARL.get().create(level, null, new BlockPos(0, 10, 0), EntitySpawnReason.NATURAL, false, false);

                if (holopearl != null) {
                    holopearl.copyAppearanceFrom(this);
                    holopearl.setPos(position());
                    holopearl.setHoloTarget(attacker);

                    level.addFreshEntity(holopearl);
                }
            }
        }
    }
}
