package com.fodk.gemcolony.entity.client.model;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.starter.StarterGemEntity;
import net.minecraft.resources.Identifier;

public class StarterGemModel<T extends StarterGemEntity> extends GemModel<T> {

	public StarterGemModel() {
		super(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "starter_gem"));
	}
}