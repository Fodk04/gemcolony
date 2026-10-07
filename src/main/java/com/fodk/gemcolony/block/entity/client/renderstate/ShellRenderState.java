package com.fodk.gemcolony.block.entity.client.renderstate;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class ShellRenderState extends BlockEntityRenderState implements GeoRenderState {
    public ItemStackRenderState pearl = new ItemStackRenderState();
    public Direction facing = Direction.NORTH;
}
