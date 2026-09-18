package com.fodk.gemcolony.entity.client.renderstate;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class GemRenderState extends EntityRenderState implements GeoRenderState {
    public int gemColor;
    public int outfit;
    public int outfitColor;
    public int hairstyle;
    public int hairColor;
    public int insignia;
    public int insigniaColor;
    public int gemPlacement;
    public int visor;
    public int visorColor;
    public float reformProgress;
    public float reformCenter;
    public float qualityModifier;

    //for screen
    public float previewPitch;
}
