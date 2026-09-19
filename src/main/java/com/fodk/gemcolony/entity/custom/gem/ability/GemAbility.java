package com.fodk.gemcolony.entity.custom.gem.ability;

import com.mojang.serialization.Codec;

public enum GemAbility {

    CRYOKINESIS(0, "Cryokinesis", "Controls ice and snow."),
    PYROKINESIS(1, "Pyrokinesis", "Controls heat and fire."),
    FERROKINESIS(2, "Ferrokinesis", "Controls magnetic metals."),
    ELECTROKINESIS(3, "Electrokinesis", "Controls electricity."),
    CHLOROKINESIS(4, "Chlorokinesis", "Controls plants."),
    HYDROKINESIS(5, "Hydrokinesis", "Controls water."),

    TERRAFORMER(6, "Terraformer", "Terraforms the environment for easier colonisation."),
    KINDERGARTNER(7, "Kindergartner", "Analyses humidity, temperature and substrate to tell you what gems could emerge from injecting at this location."),
    BUILDER(8, "Builder", "Builds your structure schematic as long as you provide the resources."),
    MINER(9, "Miner", "Sets up a mining quarry to collect useful resources for colonisation."),
    WEAPONSMITH(10, "Weaponsmith", "Creates gem weapons from chroma and different resources."),
    COMMANDER(11, "Commander", "Terrifies other gems into doing their tasks instead of lazying."),
    GENERAL(12, "General", "Gives your soldiers strategy and tactics for crushing enemies. "),
    CONSTRUCTOR(13, "Constructor", "Builds the base of all gem technology to get your colony started."),
    LAWYER(14, "Lawyer", "uuhhh"),

    SHAPESHIFTER(15, "Shapeshifter", "Changes the gem's light projection into other creatures."),
    CHARGEBALL(16, "Chargeball", "Curls up into a rapidly spinning ball before charging into enemies."),
    GEM_WEAPON(17, "Gem Weapon", "This gem can wield a gem weapon."),
    FUTURE_VISION(18, "Future Vision", "You are fortunate for a while after seeing your future."),
    HOLOPEARL(19, "Holopearl", "Creates a holographic replica as a decoy."),

    PLAYMATE(20, "Playmate", "Makes gems around her happier so that they dont rebel."),
    DANCER(21, "Dancer", "Dances to the sound of music.");

    private final int id;
    private final String name;
    private final String description;

    GemAbility(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static GemAbility fromId(int id) {
        for (GemAbility ability : values()) {
            if (ability.id == id) {
                return ability;
            }
        }

        throw new IllegalArgumentException("Unknown gem ability id: " + id);
    }

    public static final Codec<GemAbility> CODEC = Codec.STRING.xmap(GemAbility::valueOf, GemAbility::name);
}
