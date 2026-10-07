package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.fluid.ModFluids;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class ShellEssenceResourceHandler extends SnapshotJournal<ShellEssenceResourceHandler.TankSnapshot> implements ResourceHandler<FluidResource> {

    public record TankSnapshot(int whiteAmount) {
    }

    private final EssenceTank whiteTank;

    public ShellEssenceResourceHandler(EssenceTank whiteTank) {
        this.whiteTank = whiteTank;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        return index == 0
                ? FluidResource.of(ModFluids.WHITE_ESSENCE.get())
                : FluidResource.EMPTY;
    }

    @Override
    public long getAmountAsLong(int index) {
        return index == 0
                ? whiteTank.getAmount()
                : 0;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return index == 0
                ? whiteTank.getCapacity()
                : 0;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return index == 0
                && !resource.isEmpty()
                && resource.getFluid() == ModFluids.WHITE_ESSENCE.get();
    }

    @Override
    public int insert(
            int index,
            FluidResource resource,
            int amount,
            TransactionContext transaction
    ) {
        if (amount <= 0 || !isValid(index, resource)) {
            return 0;
        }

        int inserted = Math.min(
                amount,
                whiteTank.getCapacity() - whiteTank.getAmount()
        );

        if (inserted <= 0) {
            return 0;
        }

        updateSnapshots(transaction);
        whiteTank.addEssence(inserted);

        return inserted;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        return 0;
    }

    @Override
    protected TankSnapshot createSnapshot() {
        return new TankSnapshot(whiteTank.getAmount());
    }

    @Override
    protected void revertToSnapshot(TankSnapshot snapshot) {
        whiteTank.setAmount(snapshot.whiteAmount());
    }
}
