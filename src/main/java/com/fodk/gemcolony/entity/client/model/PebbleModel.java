package com.fodk.gemcolony.entity.client.model;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.starter.PebbleEntity;
import net.minecraft.resources.Identifier;

public class PebbleModel extends GemModel<PebbleEntity> {
	public PebbleModel() {
		super(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pebble"));
	}
}