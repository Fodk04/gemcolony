package com.fodk.gemcolony.block.entity.client.model;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.block.entity.custom.ShellBlockEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;

public class ShellModel extends GeoModel<ShellBlockEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "block/shell");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/block/shell.png");
    }

    @Override
    public Identifier getAnimationResource(ShellBlockEntity animatable) {
        return Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "shell");
    }
}
