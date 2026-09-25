package com.fodk.gemcolony.construction;

import java.util.List;

public record Assembly(
        String id,
        String name,
        List<AssemblyComponent> components,
        double centerX,
        double centerZ
) {
}