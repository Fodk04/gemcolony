package com.fodk.gemcolony.construction;

import net.minecraft.world.level.block.Rotation;

import java.util.List;

public class Assemblies {

    public static final Assembly WORKSTATION = new Assembly(
            "workstation",
            "Workstation",
            List.of(
                    new AssemblyComponent(
                            Blueprints.WORKSTATION,
                            1,
                            0,
                            1,
                            Rotation.NONE
                    )
            ),
            1,
            1
    );
    public static final Assembly SHELL = new Assembly(
            "shell",
            "Shell",
            List.of(
                    new AssemblyComponent(
                            Blueprints.SHELL,
                            1,
                            0,
                            1,
                            Rotation.NONE
                    )
            ),
            1,
            1
    );
    public static final Assembly INCUBATOR = new Assembly(
            "incubator",
            "Incubator",
            List.of(
                    new AssemblyComponent(
                            Blueprints.INCUBATOR,
                            1,
                            1,
                            1,
                            Rotation.NONE
                    )
            ),
            1,
            1
    );
    public static final Assembly INJECTOR = new Assembly(
            "injector",
            "Injector",
            List.of(
                    // Main tower: 3 × 12 × 3
                    new AssemblyComponent(Blueprints.DRILL, 3, 0, 3, Rotation.NONE),
                    new AssemblyComponent(Blueprints.TANK, 3, 3, 3, Rotation.NONE),
                    new AssemblyComponent(Blueprints.TANK, 3, 6, 3, Rotation.NONE),
                    new AssemblyComponent(Blueprints.CRYSTAL, 3, 9, 3, Rotation.NONE),

                    // Legs
                    // WEST leg
                    new AssemblyComponent(
                            Blueprints.LEG,
                            1, 0, 3,
                            Rotation.COUNTERCLOCKWISE_90
                    ),

                    // EAST leg
                    new AssemblyComponent(
                            Blueprints.LEG,
                            5, 0, 3,
                            Rotation.CLOCKWISE_90
                    ),

                    // NORTH leg
                    new AssemblyComponent(
                            Blueprints.LEG,
                            3, 0, 1,
                            Rotation.NONE
                    ),

                    // SOUTH leg
                    new AssemblyComponent(
                            Blueprints.LEG,
                            3, 0, 5,
                            Rotation.CLOCKWISE_180
                    )
            ),
            3,
            3
    );

    public static Assembly getById(String id) {
        return switch (id) {
            case "workstation" -> WORKSTATION;
            case "shell" -> SHELL;
            case "incubator" -> INCUBATOR;
            case "injector" -> INJECTOR;
            default -> throw new IllegalArgumentException("Unknown assembly: " + id);
        };
    }
}
