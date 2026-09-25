package com.fodk.gemcolony.block.custom;

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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class TankBlock extends Block implements ConstructedMultiblock {

    public static final EnumProperty<TankPart> PART = EnumProperty.create("part", TankPart.class);
    public static final EnumProperty<TankHalf> HALF = EnumProperty.create("half", TankHalf.class);
    public static final IntegerProperty CONSTRUCTION_STAGE = GemColonyBlockStateProperties.CONSTRUCTION_STAGE;

    public TankBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                .setValue(PART, TankPart.CENTER)
                .setValue(HALF, TankHalf.TANK_BOTTOM)
                .setValue(CONSTRUCTION_STAGE, 0));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == TankPart.CENTER
                ? RenderShape.MODEL
                : RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos centerPos = context.getClickedPos();

        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos pos = centerPos.offset(x, y, z);

                    if (!context.getLevel().getBlockState(pos).canBeReplaced()) {
                        return null;
                    }
                }
            }
        }

        TankHalf half = context.getLevel()
                .getBlockState(centerPos.below(3)).is(this)
                ? TankHalf.TANK_TOP
                : TankHalf.TANK_BOTTOM;

        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite())
                .setValue(PART, TankPart.CENTER)
                .setValue(HALF, half);
    }

    @Override
    public BlockState getConstructionState(Level level, BlockPos pos, BlockState state) {
        TankHalf half = level.getBlockState(pos.below(3)).is(this)
                ? TankHalf.TANK_TOP
                : TankHalf.TANK_BOTTOM;

        return state.setValue(HALF, half);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, PART, HALF, CONSTRUCTION_STAGE);
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
    public void placeStructure(Level level, BlockPos pos, BlockState state) {
        BlockPos top = pos.above();
        BlockPos bottom = pos.below();

        BlockPos[] positions = {
                // TOP
                top.north().west(),
                top.north(),
                top.north().east(),
                top.west(),
                top,
                top.east(),
                top.south().west(),
                top.south(),
                top.south().east(),

                // MIDDLE
                pos.north().west(),
                pos.north(),
                pos.north().east(),
                pos.west(),
                pos.east(),
                pos.south().west(),
                pos.south(),
                pos.south().east(),

                // BOTTOM
                bottom.north().west(),
                bottom.north(),
                bottom.north().east(),
                bottom.west(),
                bottom,
                bottom.east(),
                bottom.south().west(),
                bottom.south(),
                bottom.south().east()
        };

        TankPart[] parts = {
                // TOP
                TankPart.TOP_NORTH_WEST,
                TankPart.TOP_NORTH,
                TankPart.TOP_NORTH_EAST,
                TankPart.TOP_WEST,
                TankPart.TOP_CENTER,
                TankPart.TOP_EAST,
                TankPart.TOP_SOUTH_WEST,
                TankPart.TOP_SOUTH,
                TankPart.TOP_SOUTH_EAST,

                // MIDDLE
                TankPart.MIDDLE_NORTH_WEST,
                TankPart.MIDDLE_NORTH,
                TankPart.MIDDLE_NORTH_EAST,
                TankPart.MIDDLE_WEST,
                TankPart.MIDDLE_EAST,
                TankPart.MIDDLE_SOUTH_WEST,
                TankPart.MIDDLE_SOUTH,
                TankPart.MIDDLE_SOUTH_EAST,

                // BOTTOM
                TankPart.BOTTOM_NORTH_WEST,
                TankPart.BOTTOM_NORTH,
                TankPart.BOTTOM_NORTH_EAST,
                TankPart.BOTTOM_WEST,
                TankPart.BOTTOM_CENTER,
                TankPart.BOTTOM_EAST,
                TankPart.BOTTOM_SOUTH_WEST,
                TankPart.BOTTOM_SOUTH,
                TankPart.BOTTOM_SOUTH_EAST
        };

        for (int i = 0; i < positions.length; i++) {
            level.setBlock(
                    positions[i],
                    state.setValue(PART, parts[i]),
                    3
            );
        }
    }

    private static BlockPos getCenterPos(BlockPos pos, TankPart part) {
        return switch (part) {
            case CENTER -> pos;

            // TOP
            case TOP_NORTH_WEST -> pos.below().south().east();
            case TOP_NORTH -> pos.below().south();
            case TOP_NORTH_EAST -> pos.below().south().west();
            case TOP_WEST -> pos.below().east();
            case TOP_CENTER -> pos.below();
            case TOP_EAST -> pos.below().west();
            case TOP_SOUTH_WEST -> pos.below().north().east();
            case TOP_SOUTH -> pos.below().north();
            case TOP_SOUTH_EAST -> pos.below().north().west();

            // MIDDLE
            case MIDDLE_NORTH_WEST -> pos.south().east();
            case MIDDLE_NORTH -> pos.south();
            case MIDDLE_NORTH_EAST -> pos.south().west();
            case MIDDLE_WEST -> pos.east();
            case MIDDLE_EAST -> pos.west();
            case MIDDLE_SOUTH_WEST -> pos.north().east();
            case MIDDLE_SOUTH -> pos.north();
            case MIDDLE_SOUTH_EAST -> pos.north().west();

            // BOTTOM
            case BOTTOM_NORTH_WEST -> pos.above().south().east();
            case BOTTOM_NORTH -> pos.above().south();
            case BOTTOM_NORTH_EAST -> pos.above().south().west();
            case BOTTOM_WEST -> pos.above().east();
            case BOTTOM_CENTER -> pos.above();
            case BOTTOM_EAST -> pos.above().west();
            case BOTTOM_SOUTH_WEST -> pos.above().north().east();
            case BOTTOM_SOUTH -> pos.above().north();
            case BOTTOM_SOUTH_EAST -> pos.above().north().west();
        };
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);

        TankPart part = state.getValue(PART);

        BlockPos centerPos = getCenterPos(pos, part);

        TankHalf half = state.getValue(HALF);

        BlockPos otherCenterPos = half == TankHalf.TANK_BOTTOM
                ? centerPos.above(3)
                : centerPos.below(3);

        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos targetPos = otherCenterPos.offset(x, y, z);

                    if (level.getBlockState(targetPos).getBlock() == this) {
                        level.setBlock(
                                targetPos,
                                Blocks.AIR.defaultBlockState(),
                                3
                        );
                    }
                }
            }
        }

        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos targetPos = centerPos.offset(x, y, z);

                    if (level.getBlockState(targetPos).getBlock() == this) {
                        level.destroyBlock(targetPos, false);
                    }
                }
            }
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        TankPart part = state.getValue(PART);

        if (part == TankPart.CENTER) {
            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }

        BlockPos centerPos = getCenterPos(pos, part);

        if (neighbourPos.equals(centerPos)) {
            if (neighbourState.getBlock() != this
                    || neighbourState.getValue(PART) != TankPart.CENTER) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    private static BlockPos getInjectorDrillPos(BlockPos tankPos, TankHalf half) {
        return half == TankHalf.TANK_BOTTOM
                ? tankPos.below(3)
                : tankPos.below(6);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockPos tankCenter = getCenterPos(pos, state.getValue(PART));

            TankHalf half = level.getBlockState(tankCenter).getValue(HALF);

            BlockPos drillPos = getInjectorDrillPos(tankCenter, half);

            if (level.getBlockEntity(drillPos) instanceof InjectorBlockEntity injector) {
                return injector.handleInteraction(player, player.getMainHandItem());
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos tankCenter = getCenterPos(pos, state.getValue(PART));

        TankHalf half = level.getBlockState(tankCenter).getValue(HALF);

        BlockPos drillPos = getInjectorDrillPos(tankCenter, half);

        if (level.getBlockEntity(drillPos) instanceof InjectorBlockEntity injector) {

            InteractionResult result = injector.handleInteraction(player, stack);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);

        if (level.isClientSide()) {
            return;
        }

        TankPart part = state.getValue(PART);
        BlockPos tankCenter = getCenterPos(pos, part);

        BlockState tankCenterState = level.getBlockState(tankCenter);

        if (!tankCenterState.is(this)) {
            return;
        }

        TankHalf half = tankCenterState.getValue(HALF);

        BlockPos drillPos = getInjectorDrillPos(tankCenter, half);

        if (level.getBlockEntity(drillPos) instanceof InjectorBlockEntity injector) {

            injector.handleRedstoneSignal(level.hasNeighborSignal(pos));
        }
    }
}
