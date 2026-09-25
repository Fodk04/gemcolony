package com.fodk.gemcolony.block.entity.custom;

import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.data.GemAnalysisResult;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.GemDefinitions;
import com.fodk.gemcolony.tags.ModTags;
import com.fodk.gemcolony.util.GemEnvironmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.ArrayList;
import java.util.List;

public class GemSeedBlockEntity extends BlockEntity {

    private record TunnelPath(
            Direction direction,
            int distance
    ) {
    }

    private ItemStack chroma = ItemStack.EMPTY;
    private String selectedGemId = null;
    private int totalDrainable = 0;
    private int drained = 0;

    private static final int DRAIN_RADIUS = 2;
    private static final int MAX_TUNNEL_LENGTH = 32;

    public GemSeedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEM_SEED_BE.get(), pos, state);
    }

    public ItemStack getChroma() {
        return chroma;
    }

    public void setChroma(ItemStack chroma) {
        this.chroma = chroma.copyWithCount(1);
        setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        if (!chroma.isEmpty()) {
            output.store("Chroma", ItemStack.CODEC, chroma);
        }

        if (selectedGemId != null) {
            output.putString("SelectedGemId", selectedGemId);
        }

        output.putInt("TotalDrainable", totalDrainable);
        output.putInt("Drained", drained);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        chroma = input.read("Chroma", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        selectedGemId = input.getString("SelectedGemId").orElse(null);
        totalDrainable = input.getInt("TotalDrainable").orElse(0);
        drained = input.getInt("Drained").orElse(0);
    }

    public void initializeDrain() {
        totalDrainable = countDrainableBlocks();
        setChanged();
    }

    public void determineGem() {
        if (level == null) {
            return;
        }

        List<GemAnalysisResult> results = GemEnvironmentUtil.getTopThreeGemResults(level, worldPosition);

        if (results.isEmpty()) {
            return;
        }

        float roll = level.getRandom().nextFloat() * 100.0F;

        for (GemAnalysisResult result : results) {
            roll -= result.score();

            if (roll <= 0.0F) {
                selectedGemId = result.gemId();
                return;
            }
        }

        selectedGemId = results.get(results.size() - 1).gemId();
    }

    private int countDrainableBlocks() {
        if (level == null) {
            return 0;
        }

        int totalDrainable = 0;

        for (int x = -DRAIN_RADIUS; x <= DRAIN_RADIUS; x++) {
            for (int y = -DRAIN_RADIUS; y <= DRAIN_RADIUS; y++) {
                for (int z = -DRAIN_RADIUS; z <= DRAIN_RADIUS; z++) {

                    BlockPos pos = worldPosition.offset(x, y, z);

                    if (level.getBlockState(pos).is(ModTags.Blocks.GEM_DRAINABLES)) {

                        totalDrainable++;
                    }
                }
            }
        }

        return totalDrainable;
    }

    private int determineQuality(int totalDrainable, int drained) {
        if (totalDrainable <= 5) {
            return 0;
        }

        float drainagePercentage = (float) drained / totalDrainable * 100.0F;

        if (drainagePercentage <= 40.0F) {
            return 0;
        }

        if (drainagePercentage < 85.0F) {
            return level.getRandom().nextFloat() < 0.1F ? 0 : 1;
        }

        return level.getRandom().nextFloat() < 0.20F ? 2 : 1;
    }

    public void generateGem() {
        if (level == null || level.isClientSide()) {
            return;
        }

        int quality = determineQuality(totalDrainable, drained);

        GemEntity gem = GemDefinitions.get(selectedGemId).gem().create((ServerLevel) level, null, getBlockPos(), EntitySpawnReason.NATURAL, false, false);

        gem.setQuality(quality);
        int height = (int) Math.ceil(gem.getHitbox().getYsize());
        digTunnel(findShortestTunnel(height), height);

        level.addFreshEntity(gem);

        for(int y = height - 1; y >= 0; y--){
            BlockPos pos = worldPosition.above(y);
            level.removeBlock(pos, false);
        }
    }

    public void randomTick(RandomSource random) {
        if (level == null) {
            return;
        }

        List<BlockPos> drainableBlocks = new ArrayList<>();

        for (int x = -DRAIN_RADIUS; x <= DRAIN_RADIUS; x++) {
            for (int y = -DRAIN_RADIUS; y <= DRAIN_RADIUS; y++) {
                for (int z = -DRAIN_RADIUS; z <= DRAIN_RADIUS; z++) {

                    BlockPos pos = worldPosition.offset(x, y, z);

                    if (level.getBlockState(pos).is(ModTags.Blocks.GEM_DRAINABLES)) {

                        drainableBlocks.add(pos);
                    }
                }
            }
        }

        if (drainableBlocks.isEmpty()) {
            generateGem();
            return;
        }

        BlockPos target = drainableBlocks.get(random.nextInt(drainableBlocks.size()));

        level.setBlock(target, ModBlocks.DRAINED_STONE.get().defaultBlockState(), 3);
        drained++;
        setChanged();
    }

    private TunnelPath findShortestTunnel(int tunnelHeight) {
        if (level == null) {
            return null;
        }

        Direction[] directions = {
                Direction.NORTH,
                Direction.SOUTH,
                Direction.WEST,
                Direction.EAST
        };

        TunnelPath shortest = null;

        for (Direction direction : directions) {

            for (int distance = 1; distance <= MAX_TUNNEL_LENGTH; distance++) {

                BlockPos center = worldPosition.relative(direction, distance);
                BlockPos top = center.above(tunnelHeight - 1);

                // found an opening large enough for the Gem
                if (level.getBlockState(center).isAir() && level.getBlockState(top).isAir()) {

                    BlockPos skyCheck = center.above(tunnelHeight / 2);

                    if (level.canSeeSky(skyCheck)) {

                        if (shortest == null || distance < shortest.distance()) {

                            shortest = new TunnelPath(direction, distance);
                        }

                        break;
                    }
                }
            }
        }

        return shortest;
    }

    private void digTunnel(TunnelPath path, int tunnelHeight) {
        if (level == null || path == null) {
            return;
        }

        for (int distance = 1; distance < path.distance(); distance++) {

            BlockPos tunnelPos = worldPosition.relative(path.direction(), distance);

            for (int y = 0; y < tunnelHeight; y++) {

                BlockPos pos = tunnelPos.above(y);
                level.removeBlock(pos, false);
            }
        }
    }
}
