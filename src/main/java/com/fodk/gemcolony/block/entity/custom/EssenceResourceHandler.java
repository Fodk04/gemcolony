package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.fluid.ModFluids;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class EssenceResourceHandler extends SnapshotJournal<EssenceResourceHandler.TankSnapshot> implements ResourceHandler<FluidResource> {

    public record TankSnapshot(
            int blueAmount,
            int yellowAmount,
            int whiteAmount,
            int pinkAmount
    ) {}

    private final EssenceTank blueTank;
    private final EssenceTank yellowTank;
    private final EssenceTank whiteTank;
    private final EssenceTank pinkTank;

    public EssenceResourceHandler(EssenceTank blueTank, EssenceTank yellowTank, EssenceTank whiteTank, EssenceTank pinkTank) {
        this.blueTank = blueTank;
        this.yellowTank = yellowTank;
        this.whiteTank = whiteTank;
        this.pinkTank = pinkTank;
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public FluidResource getResource(int index) {
        return switch (index) {
            case 0 -> FluidResource.of(ModFluids.BLUE_ESSENCE.get());
            case 1 -> FluidResource.of(ModFluids.YELLOW_ESSENCE.get());
            case 2 -> FluidResource.of(ModFluids.WHITE_ESSENCE.get());
            case 3 -> FluidResource.of(ModFluids.PINK_ESSENCE.get());
            default -> FluidResource.EMPTY;
        };
    }

    @Override
    public long getAmountAsLong(int index) {
        return switch (index) {
            case 0 -> blueTank.getAmount();
            case 1 -> yellowTank.getAmount();
            case 2 -> whiteTank.getAmount();
            case 3 -> pinkTank.getAmount();
            default -> 0;
        };
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return switch (index) {
            case 0 -> blueTank.getCapacity();
            case 1 -> yellowTank.getCapacity();
            case 2 -> whiteTank.getCapacity();
            case 3 -> pinkTank.getCapacity();
            default -> 0;
        };
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        if (resource.isEmpty()) {
            return false;
        }

        return switch (index) {
            case 0 -> resource.getFluid() == ModFluids.BLUE_ESSENCE.get();
            case 1 -> resource.getFluid() == ModFluids.YELLOW_ESSENCE.get();
            case 2 -> resource.getFluid() == ModFluids.WHITE_ESSENCE.get();
            case 3 -> resource.getFluid() == ModFluids.PINK_ESSENCE.get();
            default -> false;
        };
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (amount <= 0 || !isValid(index, resource)) {
            return 0;
        }

        EssenceTank tank = switch (index) {
            case 0 -> blueTank;
            case 1 -> yellowTank;
            case 2 -> whiteTank;
            case 3 -> pinkTank;
            default -> null;
        };

        if (tank == null) {
            return 0;
        }

        int inserted = Math.min(
                amount,
                tank.getCapacity() - tank.getAmount()
        );

        if (inserted <= 0) {
            return 0;
        }

        updateSnapshots(transaction);

        tank.addEssence(inserted);

        return inserted;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        // Injector Essence cannot currently be extracted.
        return 0;
    }

    @Override
    protected TankSnapshot createSnapshot() {
        return new TankSnapshot(
                blueTank.getAmount(),
                yellowTank.getAmount(),
                whiteTank.getAmount(),
                pinkTank.getAmount()
        );
    }

    @Override
    protected void revertToSnapshot(TankSnapshot snapshot) {
        blueTank.setAmount(snapshot.blueAmount());
        yellowTank.setAmount(snapshot.yellowAmount());
        whiteTank.setAmount(snapshot.whiteAmount());
        pinkTank.setAmount(snapshot.pinkAmount());
    }
}

