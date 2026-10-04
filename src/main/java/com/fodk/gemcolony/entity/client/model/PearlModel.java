package com.fodk.gemcolony.entity.client.model;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.PearlEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import net.minecraft.resources.Identifier;

public class PearlModel extends GemModel<PearlEntity> {
	public PearlModel() {
		super(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "pearl"));
	}
}