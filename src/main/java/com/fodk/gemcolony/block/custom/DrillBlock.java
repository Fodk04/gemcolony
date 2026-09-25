package com.fodk.gemcolony.block.custom;
import com.fodk.gemcolony.block.ModBlocks;
import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.block.state.GemColonyBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class DrillBlock extends BaseEntityBlock implements ConstructedMultiblock {

    public static final EnumProperty<DrillPart> PART = EnumProperty.create("part", DrillPart.class);
    public static final IntegerProperty CONSTRUCTION_STAGE = GemColonyBlockStateProperties.CONSTRUCTION_STAGE;

    public DrillBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, DrillPart.CENTER)
                .setValue(CONSTRUCTION_STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, CONSTRUCTION_STAGE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == DrillPart.CENTER
                ? RenderShape.MODEL
                : RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    private static final VoxelShape BOTTOM_SHAPE = Block.box(4, 0, 4, 12, 16, 12);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(PART)) {
            case BOTTOM -> BOTTOM_SHAPE;
            default -> super.getShape(state, level, pos, context);
        };

        return shape;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos origin = context.getClickedPos();

        BlockPos top = origin.above();

        BlockPos[] positions = {
                top.north().west(),
                top.north(),
                top.north().east(),

                top.west(),
                top,
                top.east(),

                top.south().west(),
                top.south(),
                top.south().east(),

                origin.below()
        };

        for (BlockPos pos : positions) {
            if (!context.getLevel().getBlockState(pos).canBeReplaced()) {
                return null;
            }
        }

        return this.defaultBlockState()
                .setValue(PART, DrillPart.CENTER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide()) {
            return;
        }

        placeStructure(level, pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(PART) != DrillPart.CENTER) {
            return null;
        }

        return new InjectorBlockEntity(blockPos, blockState);
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            DrillPart part = state.getValue(PART);

            BlockPos centerPos = switch (part) {
                case CENTER -> pos;

                case TOP_NORTH_WEST -> pos.south().east().below();
                case TOP_NORTH -> pos.south().below();
                case TOP_NORTH_EAST -> pos.south().west().below();

                case TOP_WEST -> pos.east().below();
                case TOP_CENTER -> pos.below();
                case TOP_EAST -> pos.west().below();

                case TOP_SOUTH_WEST -> pos.north().east().below();
                case TOP_SOUTH -> pos.north().below();
                case TOP_SOUTH_EAST -> pos.north().west().below();

                case BOTTOM -> pos.above();
            };

            BlockPos top = centerPos.above();

            BlockPos[] positions = {
                    top.north().west(),
                    top.north(),
                    top.north().east(),

                    top.west(),
                    top,
                    top.east(),

                    top.south().west(),
                    top.south(),
                    top.south().east(),

                    centerPos,
                    centerPos.below()
            };

            for (BlockPos blockPos : positions) {
                if (level.getBlockState(blockPos).getBlock() == this) {
                    level.setBlock(
                            blockPos,
                            Blocks.AIR.defaultBlockState(),
                            3
                    );
                }
            }
        }

        super.destroy(level, pos, state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        DrillPart part = state.getValue(PART);

        if (part == DrillPart.CENTER) {
            return super.updateShape(
                    state,
                    level,
                    ticks,
                    pos,
                    directionToNeighbour,
                    neighbourPos,
                    neighbourState,
                    random
            );
        }

        BlockPos centerPos = switch (part) {
            case TOP_NORTH_WEST -> pos.south().east();
            case TOP_NORTH -> pos.south();
            case TOP_NORTH_EAST -> pos.south().west();

            case TOP_WEST -> pos.east();
            case TOP_CENTER -> pos.below();
            case TOP_EAST -> pos.west();

            case TOP_SOUTH_WEST -> pos.north().east();
            case TOP_SOUTH -> pos.north();
            case TOP_SOUTH_EAST -> pos.north().west();

            case BOTTOM -> pos.above();

            case CENTER -> pos;
        };

        if (neighbourPos.equals(centerPos)) {
            DrillPart requiredPart = switch (part) {
                case TOP_NORTH_WEST,
                     TOP_NORTH,
                     TOP_NORTH_EAST,
                     TOP_WEST,
                     TOP_EAST,
                     TOP_SOUTH_WEST,
                     TOP_SOUTH,
                     TOP_SOUTH_EAST -> DrillPart.TOP_CENTER;

                case TOP_CENTER, BOTTOM -> DrillPart.CENTER;

                case CENTER -> DrillPart.CENTER;
            };

            if (neighbourState.getBlock() != this
                    || neighbourState.getValue(PART) != requiredPart) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public void placeStructure(Level level, BlockPos pos, BlockState state) {
        BlockPos top = pos.above();

        BlockPos[] positions = {
                top.north().west(),
                top.north(),
                top.north().east(),

                top.west(),
                top,
                top.east(),

                top.south().west(),
                top.south(),
                top.south().east(),

                pos.below()
        };

        DrillPart[] parts = {
                DrillPart.TOP_NORTH_WEST,
                DrillPart.TOP_NORTH,
                DrillPart.TOP_NORTH_EAST,

                DrillPart.TOP_WEST,
                DrillPart.TOP_CENTER,
                DrillPart.TOP_EAST,

                DrillPart.TOP_SOUTH_WEST,
                DrillPart.TOP_SOUTH,
                DrillPart.TOP_SOUTH_EAST,

                DrillPart.BOTTOM
        };

        for (int i = 0; i < positions.length; i++) {
            level.setBlock(
                    positions[i],
                    state.setValue(PART, parts[i]),
                    3
            );
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        DrillPart part = state.getValue(PART);

        BlockPos centerPos = getCenterPos(pos, part);

        if (level.getBlockEntity(centerPos) instanceof InjectorBlockEntity injector) {
            return injector.handleInteraction(player, player.getMainHandItem());
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos centerPos = getCenterPos(pos, state.getValue(PART));

        if (level.getBlockEntity(centerPos) instanceof InjectorBlockEntity injector) {

            InteractionResult result = injector.handleInteraction(player, stack);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean isInjectorComplete(Level level, BlockPos drillPos) {
        BlockState drillState = level.getBlockState(drillPos);

        // Drill must be finished
        if (!drillState.is(ModBlocks.DRILL.get())
                || drillState.getValue(CONSTRUCTION_STAGE) != 1) {
            return false;
        }

        // Bottom tank
        BlockState bottomTank = level.getBlockState(drillPos.above(3));

        if (!isFinishedTank(bottomTank)) {
            return false;
        }

        // Top tank
        BlockState topTank = level.getBlockState(drillPos.above(6));

        if (!isFinishedTank(topTank)) {
            return false;
        }

        // Crystal
        BlockState crystal = level.getBlockState(drillPos.above(9));

        if (!isFinishedCrystal(crystal)) {
            return false;
        }

        // Legs intentionally ignored for now
        return true;
    }

    private static boolean isFinishedTank(BlockState state) {
        return state.is(ModBlocks.TANK.get())
                && state.getValue(TankBlock.PART) == TankPart.CENTER
                && state.getValue(TankBlock.CONSTRUCTION_STAGE) == 1;
    }

    private static boolean isFinishedCrystal(BlockState state) {
        return state.is(ModBlocks.CRYSTAL.get())
                && state.getValue(CrystalBlock.PART) == CrystalPart.CENTER
                && state.getValue(CrystalBlock.CONSTRUCTION_STAGE) == 1;
    }

    public static BlockPos getCenterPos(BlockPos pos, DrillPart part) {
        return switch (part) {
            case CENTER -> pos;

            case TOP_NORTH_WEST -> pos.south().east().below();
            case TOP_NORTH -> pos.south().below();
            case TOP_NORTH_EAST -> pos.south().west().below();

            case TOP_WEST -> pos.east().below();
            case TOP_CENTER -> pos.below();
            case TOP_EAST -> pos.west().below();

            case TOP_SOUTH_WEST -> pos.north().east().below();
            case TOP_SOUTH -> pos.north().below();
            case TOP_SOUTH_EAST -> pos.north().west().below();

            case BOTTOM -> pos.above();
        };
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);

        if (level.isClientSide()) {
            return;
        }

        DrillPart part = state.getValue(PART);
        BlockPos centerPos = getCenterPos(pos, part);

        if (level.getBlockEntity(centerPos) instanceof InjectorBlockEntity injector) {

            injector.handleRedstoneSignal(level.hasNeighborSignal(pos));
        }
    }

}