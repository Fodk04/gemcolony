package com.fodk.gemcolony.entity.custom.gem.pearl;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.util.ColorUtil;
import com.fodk.gemcolony.util.GemCombatUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.UUID;

public class HolopearlEntity extends PearlEntity{

    private int lifetime = 200;
    public void setHoloTarget(LivingEntity target) {
        setTarget(target);
        if (target instanceof Mob mob) {
            mob.setTarget(this);
        }
    }

    public HolopearlEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide()) {
            lifetime--;

            if (lifetime <= 0) {
                discard();
            }
        }
    }

    public void copyAppearanceFrom(PearlEntity pearl) {
        SynchedEntityData synchedEntityData = pearl.getEntityData();

        entityData.set(VARIANT, synchedEntityData.get(VARIANT));

        entityData.set(GEM_COLOR, ColorUtil.colorToInt(new Color(203, 248, 255)));

        entityData.set(OUTFIT, synchedEntityData.get(OUTFIT));
        entityData.set(OUTFIT_COLOR, ColorUtil.colorToInt(new Color(71, 163, 222)));

        entityData.set(INSIGNIA, synchedEntityData.get(INSIGNIA));
        entityData.set(INSIGNIA_COLOR, ColorUtil.colorToInt(new Color(134, 222, 248)));

        entityData.set(HAIRSTYLE, synchedEntityData.get(HAIRSTYLE));
        entityData.set(HAIR_COLOR, ColorUtil.colorToInt(new Color(152, 219, 255)));

        entityData.set(VISOR, synchedEntityData.get(VISOR));
        entityData.set(VISOR_COLOR, ColorUtil.colorToInt(new Color(217, 239, 250)));

        entityData.set(GEM_PLACEMENT, synchedEntityData.get(GEM_PLACEMENT));
        entityData.set(WINGS, synchedEntityData.get(WINGS));
        entityData.set(MARKINGS, synchedEntityData.get(MARKINGS));

        setQuality(synchedEntityData.get(QUALITY));
        entityData.set(REFORM_PROGRESS, 0);
        entityData.set(OWNER_UUID, pearl.getOwnerUUID() == null ? "" : pearl.getOwnerUUID().toString());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));

        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(
                this,
                LivingEntity.class,
                16.0F,
                1.3D,
                1.5D,
                entity -> entity == this.getTarget()
        ));
    }

    @Override
    public void die(DamageSource source) {
        discard();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return hurtGemServer(level, source, amount);
    }
}
