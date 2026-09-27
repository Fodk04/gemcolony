package com.fodk.gemcolony.entity.client.model;


import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import net.minecraft.resources.Identifier;

public class QuartzModel extends GemModel<QuartzEntity> {
	public QuartzModel() {
		super(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quartz"));
	}
}