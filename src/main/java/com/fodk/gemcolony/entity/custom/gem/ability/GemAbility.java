package com.fodk.gemcolony.entity.custom.gem.ability;

import com.mojang.serialization.Codec;

public enum GemAbility {

    CRYOKINESIS("Cryokinesis"),
    PYROKINESIS("Pyrokinesis"),
    FERROKINESIS("Ferrokinesis"),
    ELECTROKINESIS("Electrokinesis"),
    CHLOROKINESIS("Chlorokinesis"),
    HYDROKINESIS("Hydrokinesis"),

    TERRAFORMER("Terraformer"),
    KINDERGARTNER("Kindergartner"),
    BUILDER("Builder"),
    MINER("Miner"),
    WEAPONSMITH("Weaponsmith"),
    COMMANDER("Commander"),
    GENERAL("General"),
    CONSTRUCTOR("Constructor"),
    LAWYER("Lawyer"),

    SHAPESHIFTER("Shapeshifter"),
    CHARGEBALL("Chargeball"),
    GEM_WEAPON("Gem Weapon"),
    FUTURE_VISION("Future Vision"),
    HOLOPEARL("Holopearl"),

    PLAYMATE("Playmate"),
    DANCER("Dancer");

    private final String name;

    GemAbility(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final Codec<GemAbility> CODEC = Codec.STRING.xmap(GemAbility::valueOf, GemAbility::name);
}
