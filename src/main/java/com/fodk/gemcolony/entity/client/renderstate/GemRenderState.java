package com.fodk.gemcolony.entity.client.renderstate;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GemRenderState extends LivingEntityRenderState implements GeoRenderState {
    public int gemColor;
    public int outfit;
    public int outfitColor;
    public int hairstyle;
    public int hairColor;
    public int insignia;
    public int insigniaColor;
    public List<Integer> gemPlacements;
    public int visor;
    public int visorColor;
    public int markings;
    public int markingsColor;
    public float reformProgress;
    public float reformCenter;
    public float qualityModifier;

    //model size to size up/down blockbench scale
    public float modelSize;

    //for screen
    public float previewPitch;

    //shapeshift
    public String shapeshift;

    //fusion or not
    public boolean isFusion;

    public List<ItemStack> supplyDisplayItems = List.of();
    public ItemStackRenderState supplyItemRenderState = new ItemStackRenderState();
    public float height;
}
