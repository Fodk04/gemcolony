package com.fodk.gemcolony.entity.client.screen;

import com.fodk.gemcolony.entity.custom.GemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum GemTabs {
    INVENTORY(Items.CHEST),
    APPEARANCE(Items.ARMOR_STAND),
    STATS(Items.BOOK),
    ABILITIES(null);

    private Item itemIcon;

    GemTabs(Item itemIcon) {
        this.itemIcon = itemIcon;
    }

    public ItemStack getIcon(GemEntity gemEntity){
        if(this == ABILITIES){
            return new ItemStack(gemEntity.getGemItem());
        }

        return new ItemStack(itemIcon);
    }
}
