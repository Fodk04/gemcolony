package com.fodk.gemcolony.entity.client.model;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public class PeridotModel extends GemModel<PeridotEntity> {
	public PeridotModel() {
		super(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "peridot"));
	}
}