package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.block.entity.ModBlockEntities;
import com.fodk.gemcolony.block.entity.custom.ShellBlockEntity;
import com.fodk.gemcolony.util.VoxelShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class ShellBlock extends BaseEntityBlock implements ConstructedMultiblock, SimpleWaterloggedBlock {

    public static final EnumProperty<ShellPart> PART = EnumProperty.create("part", ShellPart.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ShellBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, ShellPart.CENTER)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private static final VoxelShape TOP_SHAPE_IDLE = Block.box(0, 0, 0, 16, 1, 16);
    private static final VoxelShape TOP_CENTER_SHAPE_IDLE = Block.box(0, 0, 0, 16, 1, 1);
    private static final VoxelShape TOP_SHAPE_DRAINING = Block.box(0, 0, 0, 16, 9, 16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockPos centerPos = getCenterPos(pos, state.getValue(PART));

        if (level.getBlockEntity(centerPos) instanceof ShellBlockEntity shell) {
            if (!shell.isDraining()) {
                return switch (state.getValue(PART)) {
                    case BOTTOM_EAST, BOTTOM_SOUTH_EAST, BOTTOM_SOUTH_WEST, BOTTOM_SOUTH, BOTTOM_NORTH_WEST,
                         BOTTOM_NORTH_EAST, BOTTOM_NORTH, BOTTOM_WEST -> BOTTOM_SHAPE;
                    case TOP_SOUTH_WEST, TOP_SOUTH_EAST, TOP_SOUTH, TOP_NORTH_WEST, TOP_NORTH_EAST,
                         TOP_NORTH, TOP_WEST, TOP_EAST -> TOP_SHAPE_IDLE;
                    case TOP_CENTER-> VoxelShapeUtil.rotateShape(TOP_CENTER_SHAPE_IDLE, state.getValue(FACING));
                    default -> super.getShape(state, level, pos, context);
                };
            }
        }

        return switch (state.getValue(PART)) {
            case BOTTOM_EAST, BOTTOM_SOUTH_EAST, BOTTOM_SOUTH_WEST, BOTTOM_SOUTH, BOTTOM_NORTH_WEST,
                 BOTTOM_NORTH_EAST, BOTTOM_NORTH, BOTTOM_WEST -> BOTTOM_SHAPE;
            case TOP_CENTER, TOP_SOUTH_WEST, TOP_SOUTH_EAST, TOP_SOUTH, TOP_NORTH_WEST, TOP_NORTH_EAST,
                 TOP_NORTH, TOP_WEST, TOP_EAST -> TOP_SHAPE_DRAINING;
            default -> super.getShape(state, level, pos, context);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING, WATERLOGGED);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos center = context.getClickedPos();

        BlockPos[] positions = {
                center.north().west(),
                center.north(),
                center.north().east(),

                center.west(),
                center.east(),

                center.south().west(),
                center.south(),
                center.south().east(),

                center.above().north().west(),
                center.above().north(),
                center.above().north().east(),

                center.above().west(),
                center.above(),
                center.above().east(),

                center.above().south().west(),
                center.above().south(),
                center.above().south().east()
        };

        for (BlockPos pos : positions) {
            if (!context.getLevel().getBlockState(pos).canBeReplaced()) {
                return null;
            }
        }

        boolean waterlogged = context.getLevel().getFluidState(center).getType() == Fluids.WATER;

        return this.defaultBlockState()
                .setValue(PART, ShellPart.CENTER)
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
        if (blockState.getValue(PART) != ShellPart.CENTER) {
            return null;
        }

        return new ShellBlockEntity(blockPos, blockState);
    }

    @Override
    public void placeStructure(Level level, BlockPos pos, BlockState state) {

        BlockPos[] positions = {
                // Bottom layer
                pos.north().west(),
                pos.north(),
                pos.north().east(),

                pos.west(),
                pos,
                pos.east(),

                pos.south().west(),
                pos.south(),
                pos.south().east(),

                // Top layer
                pos.above().north().west(),
                pos.above().north(),
                pos.above().north().east(),

                pos.above().west(),
                pos.above(),
                pos.above().east(),

                pos.above().south().west(),
                pos.above().south(),
                pos.above().south().east()
        };

        ShellPart[] parts = {
                // Bottom layer
                ShellPart.BOTTOM_NORTH_WEST,
                ShellPart.BOTTOM_NORTH,
                ShellPart.BOTTOM_NORTH_EAST,

                ShellPart.BOTTOM_WEST,
                ShellPart.CENTER,
                ShellPart.BOTTOM_EAST,

                ShellPart.BOTTOM_SOUTH_WEST,
                ShellPart.BOTTOM_SOUTH,
                ShellPart.BOTTOM_SOUTH_EAST,

                // Top layer
                ShellPart.TOP_NORTH_WEST,
                ShellPart.TOP_NORTH,
                ShellPart.TOP_NORTH_EAST,

                ShellPart.TOP_WEST,
                ShellPart.TOP_CENTER,
                ShellPart.TOP_EAST,

                ShellPart.TOP_SOUTH_WEST,
                ShellPart.TOP_SOUTH,
                ShellPart.TOP_SOUTH_EAST
        };

        for (int i = 0; i < positions.length; i++) {
            BlockPos partPos = positions[i];

            boolean waterlogged;

            if (parts[i] == ShellPart.CENTER) {
                // The center may already have been placed by the constructor
                waterlogged = state.getValue(WATERLOGGED);
            } else {
                // these positions havent been placed yet, so we can
                // check whether water currently occupies them
                waterlogged = level.getFluidState(partPos).getType() == Fluids.WATER;
            }

            level.setBlock(partPos, state.setValue(PART, parts[i]).setValue(WATERLOGGED, waterlogged), 3);
        }
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            ShellPart part = state.getValue(PART);

            BlockPos centerPos = switch (part) {
                case CENTER -> pos;

                // Bottom layer
                case BOTTOM_NORTH_WEST -> pos.south().east();
                case BOTTOM_NORTH -> pos.south();
                case BOTTOM_NORTH_EAST -> pos.south().west();

                case BOTTOM_WEST -> pos.east();
                case BOTTOM_EAST -> pos.west();

                case BOTTOM_SOUTH_WEST -> pos.north().east();
                case BOTTOM_SOUTH -> pos.north();
                case BOTTOM_SOUTH_EAST -> pos.north().west();

                // Top layer
                case TOP_NORTH_WEST -> pos.south().east().below();
                case TOP_NORTH -> pos.south().below();
                case TOP_NORTH_EAST -> pos.south().west().below();

                case TOP_WEST -> pos.east().below();
                case TOP_CENTER -> pos.below();
                case TOP_EAST -> pos.west().below();

                case TOP_SOUTH_WEST -> pos.north().east().below();
                case TOP_SOUTH -> pos.north().below();
                case TOP_SOUTH_EAST -> pos.north().west().below();
            };

            BlockPos[] positions = {
                    // Bottom layer
                    centerPos.north().west(),
                    centerPos.north(),
                    centerPos.north().east(),

                    centerPos.west(),
                    centerPos,
                    centerPos.east(),

                    centerPos.south().west(),
                    centerPos.south(),
                    centerPos.south().east(),

                    // Top layer
                    centerPos.above().north().west(),
                    centerPos.above().north(),
                    centerPos.above().north().east(),

                    centerPos.above().west(),
                    centerPos.above(),
                    centerPos.above().east(),

                    centerPos.above().south().west(),
                    centerPos.above().south(),
                    centerPos.above().south().east()
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
        if (!state.getValue(WATERLOGGED) && hasEnoughWaterNeighbors(level, pos)) {
            return state.setValue(WATERLOGGED, true);
        }

        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        ShellPart part = state.getValue(PART);

        if (part == ShellPart.CENTER) {
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
            // Bottom layer
            case BOTTOM_NORTH_WEST -> pos.south().east();
            case BOTTOM_NORTH -> pos.south();
            case BOTTOM_NORTH_EAST -> pos.south().west();

            case BOTTOM_WEST -> pos.east();
            case BOTTOM_EAST -> pos.west();

            case BOTTOM_SOUTH_WEST -> pos.north().east();
            case BOTTOM_SOUTH -> pos.north();
            case BOTTOM_SOUTH_EAST -> pos.north().west();

            // Top layer
            case TOP_NORTH_WEST -> pos.south().east().below();
            case TOP_NORTH -> pos.south().below();
            case TOP_NORTH_EAST -> pos.south().west().below();

            case TOP_WEST -> pos.east().below();
            case TOP_CENTER -> pos.below();
            case TOP_EAST -> pos.west().below();

            case TOP_SOUTH_WEST -> pos.north().east().below();
            case TOP_SOUTH -> pos.north().below();
            case TOP_SOUTH_EAST -> pos.north().west().below();

            case CENTER -> pos;
        };

        if (neighbourPos.equals(centerPos)) {
            if (neighbourState.getBlock() != this
                    || neighbourState.getValue(PART) != ShellPart.CENTER) {
                return Blocks.AIR.defaultBlockState();
            }
        }

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

    @Override
    public boolean canPlace(Level level, BlockPos pos, BlockState state) {
        BlockPos[] positions = {
                // Bottom layer
                pos.north().west(),
                pos.north(),
                pos.north().east(),

                pos.west(),
                pos,
                pos.east(),

                pos.south().west(),
                pos.south(),
                pos.south().east(),

                // Top layer
                pos.above().north().west(),
                pos.above().north(),
                pos.above().north().east(),

                pos.above().west(),
                pos.above(),
                pos.above().east(),

                pos.above().south().west(),
                pos.above().south(),
                pos.above().south().east()
        };

        for (BlockPos blockPos : positions) {
            if (!level.getBlockState(blockPos).canBeReplaced()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        BlockPos centerPos = getCenterPos(pos, state.getValue(PART));

        if (level.getBlockEntity(centerPos) instanceof ShellBlockEntity shell) {
            return shell.handleInteraction(player, ItemStack.EMPTY);
        }

        return InteractionResult.PASS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos centerPos = getCenterPos(pos, state.getValue(PART));

        if (level.getBlockEntity(centerPos) instanceof ShellBlockEntity shell) {
            return shell.handleInteraction(player, stack);
        }

        return InteractionResult.PASS;
    }

    public BlockPos getCenterPos(BlockPos pos, ShellPart part) {
        return switch (part) {
            case CENTER -> pos;

            // Bottom layer
            case BOTTOM_NORTH_WEST -> pos.south().east();
            case BOTTOM_NORTH -> pos.south();
            case BOTTOM_NORTH_EAST -> pos.south().west();

            case BOTTOM_WEST -> pos.east();
            case BOTTOM_EAST -> pos.west();

            case BOTTOM_SOUTH_WEST -> pos.north().east();
            case BOTTOM_SOUTH -> pos.north();
            case BOTTOM_SOUTH_EAST -> pos.north().west();

            // Top layer
            case TOP_NORTH_WEST -> pos.south().east().below();
            case TOP_NORTH -> pos.south().below();
            case TOP_NORTH_EAST -> pos.south().west().below();

            case TOP_WEST -> pos.east().below();
            case TOP_CENTER -> pos.below();
            case TOP_EAST -> pos.west().below();

            case TOP_SOUTH_WEST -> pos.north().east().below();
            case TOP_SOUTH -> pos.north().below();
            case TOP_SOUTH_EAST -> pos.north().west().below();
        };
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(type, ModBlockEntities.SHELL_BE.get(),
                (level1, pos, state1, blockEntity) -> blockEntity.tick()
        );
    }

    //waterlogging is hard fr fr TODO fix waterlogging
    @Override
    public boolean canPlaceLiquid(@Nullable LivingEntity user, BlockGetter level, BlockPos pos, BlockState state, Fluid type) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(user, level, pos, state, type);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public ItemStack pickupBlock(@Nullable LivingEntity user, LevelAccessor level, BlockPos pos, BlockState state) {
        return SimpleWaterloggedBlock.super.pickupBlock(user, level, pos, state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return super.getCollisionShape(state, level, pos, context);
    }

    public static boolean hasEnoughWaterNeighbors(BlockGetter level, BlockPos pos) {
        int waterNeighbours = 0;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighbourPos = pos.relative(direction);

            if (level.getFluidState(neighbourPos).is(Fluids.WATER)) {
                waterNeighbours++;
            }
        }

        return waterNeighbours >= 2;
    }
}
