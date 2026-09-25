package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.item.custom.EssenceType;
import com.fodk.gemcolony.util.ColorUtil;

public class EssenceTank {

    private static final int CAPACITY = 2_500;

    private final EssenceType type;
    private int amount = 0;

    public EssenceTank(EssenceType type) {
        this.type = type;
    }

    public EssenceType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public boolean isEmpty() {
        return amount <= 0;
    }

    public boolean isFull() {
        return amount >= CAPACITY;
    }

    public int addEssence(int amountToAdd) {
        if (amountToAdd <= 0 || isFull()) {
            return 0;
        }

        int amountAdded = Math.min(amountToAdd, CAPACITY - amount);

        amount += amountAdded;

        return amountAdded;
    }

    public int removeEssence(int amountToRemove) {
        if (amountToRemove <= 0 || isEmpty()) {
            return 0;
        }

        int amountRemoved = Math.min(
                amountToRemove,
                amount
        );

        amount -= amountRemoved;

        return amountRemoved;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(0, Math.min(amount, CAPACITY));
    }

    public void clear() {
        amount = 0;
    }
}

