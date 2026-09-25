package com.fodk.gemcolony.fluid;

import net.neoforged.neoforge.fluids.BaseFlowingFluid;

public class EssenceFluid {

    public static class Source extends BaseFlowingFluid.Source {

        public Source(Properties properties) {
            super(properties);
        }
    }

    public static class Flowing extends BaseFlowingFluid.Flowing {

        public Flowing(Properties properties) {
            super(properties);
        }
    }

    private EssenceFluid() {
    }
}