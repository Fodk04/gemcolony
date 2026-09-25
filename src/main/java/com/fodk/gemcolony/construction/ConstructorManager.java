package com.fodk.gemcolony.construction;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class ConstructorManager {

    private static final Map<UUID, Constructor> ACTIVE_CONSTRUCTORS = new java.util.HashMap<>();

    public static Constructor get(Player player) {
        return ACTIVE_CONSTRUCTORS.computeIfAbsent(
                player.getUUID(),
                uuid -> new Constructor()
        );
    }

    public static void remove(Player player) {
        ACTIVE_CONSTRUCTORS.remove(player.getUUID());
    }

    public static void beginPlacement(Player player, StarterGemEntity gem, Assembly assembly) {
        Constructor constructor = get(player);

        constructor.beginPlacement(
                assembly,
                gem,
                player
        );

        System.out.println(
                "Constructor placement started: "
                        + assembly.name()
                        + " at "
                        + constructor.getPlacementPos()
        );
    }
}
