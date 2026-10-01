package com.fodk.gemcolony.util;

import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.UUID;

public final class GemCombatUtil {

    private GemCombatUtil() {
    }

    public static boolean canAttack(GemEntity gem, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (target == gem) {
            return false;
        }

        UUID ownerUUID = gem.getOwnerUUID();

        // Never attack the owner.
        if (target instanceof Player player
                && ownerUUID != null
                && ownerUUID.equals(player.getUUID())) {
            return false;
        }

        // Never attack another Gem owned by the same player.
        if (target instanceof GemEntity targetGem) {
            if (ownerUUID != null
                    && ownerUUID.equals(targetGem.getOwnerUUID())) {
                return false;
            }
        }

        // Everything else can be attacked if the Gem has been aggroed.
        return true;
    }

    public static void setAggroTarget(GemEntity gem, LivingEntity target) {
        if (canAttack(gem, target)) {
            gem.setAggroTarget(target);
            System.out.println("Gem aggro target: " + target.getName().getString());
        }
    }

    public static boolean isNaturallyHostile(LivingEntity target) {
        return target.getType().getCategory() == MobCategory.MONSTER;
    }

    public static void alertOwnerGems(Player owner, LivingEntity target) {
        if (owner.level().isClientSide()) {
            return;
        }

        for (GemEntity gem : owner.level().getEntitiesOfClass(GemEntity.class, owner.getBoundingBox().inflate(32.0D))) {
            if (owner.getUUID().equals(gem.getOwnerUUID())) {
                setAggroTarget(gem, target);
            }
        }
    }

    public static void alertAlliedGems(GemEntity attackedGem, LivingEntity target, UUID ownerUUID) {
        if (attackedGem.level().isClientSide()) {
            return;
        }

        for (GemEntity gem : attackedGem.level().getEntitiesOfClass(GemEntity.class, attackedGem.getBoundingBox().inflate(32.0D))) {
            if (gem == attackedGem) {
                continue;
            }

            if (!ownerUUID.equals(gem.getOwnerUUID())) {
                continue;
            }

            setAggroTarget(gem, target);
        }
    }

    public static boolean canReachTarget(GemEntity gem, LivingEntity target) {
        Path path = gem.getNavigation().createPath(target, 0);

        return path != null && path.canReach();
    }
}
