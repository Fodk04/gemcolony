package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.entity.client.model.PeridotModel;
import com.fodk.gemcolony.entity.client.model.QuartzModel;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.entity.custom.gem.quartz.QuartzEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class QuartzRenderer extends GemRenderer<QuartzEntity, GemRenderState>{

    public QuartzRenderer(EntityRendererProvider.Context context) {
        super(context, new QuartzModel(), "quartz");
    }
}