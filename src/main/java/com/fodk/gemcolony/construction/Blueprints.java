package com.fodk.gemcolony.construction;

import com.fodk.gemcolony.block.ModBlocks;

public class Blueprints {

    public static final Blueprint WORKSTATION = new Blueprint(
            "workstation",
            "Workstation",
            3, 2, 3,
            -1,-1,-1,
            ModBlocks.CRYSTAL.get()
    );
    public static final Blueprint SHELL = new Blueprint(
            "shell",
            "Shell",
            3, 2, 3,
            -1,0,-1,
            ModBlocks.SHELL.get()
    );
    public static final Blueprint INCUBATOR = new Blueprint(
            "incubator",
            "Incubator",
            3, 3, 3,
            -1,-1,-1,
            ModBlocks.CRYSTAL.get()
    );
    public static final Blueprint DRILL = new Blueprint(
            "drill",
            "Drill",
            3, 3, 3,
            -1, -1, -1,
            ModBlocks.DRILL.get()
    );

    public static final Blueprint LEG = new Blueprint(
            "leg",
            "Leg",
            1, 3, 2,
            0, -1, -1,
            ModBlocks.LEG.get()
    );

    public static final Blueprint TANK = new Blueprint(
            "tank",
            "Tank",
            3, 3, 3,
            -1, -1, -1,
            ModBlocks.TANK.get()
    );

    public static final Blueprint CRYSTAL = new Blueprint(
            "crystal",
            "Crystal",
            3, 3, 3,
            -1, -1, -1,
            ModBlocks.CRYSTAL.get()
    );
}
