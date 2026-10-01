package com.fodk.gemcolony.entity.client.renderstate;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class BubbleRenderState extends EntityRenderState implements GeoRenderState {
    public ItemStackRenderState item = new ItemStackRenderState();
    public float yRot;
}
