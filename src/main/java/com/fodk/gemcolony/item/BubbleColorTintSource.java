package com.fodk.gemcolony.item;

import com.fodk.gemcolony.data.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record BubbleColorTintSource() implements ItemTintSource {

    public static final BubbleColorTintSource INSTANCE = new BubbleColorTintSource();

    public static final MapCodec<BubbleColorTintSource> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public int calculate(ItemStack stack, ClientLevel level, LivingEntity owner) {
        return 0xFF000000 | stack.getOrDefault(ModDataComponents.BUBBLE_COLOR, 0xFFFFFF);
    }

    @Override
    public MapCodec<BubbleColorTintSource> type() {
        return MAP_CODEC;
    }
}
