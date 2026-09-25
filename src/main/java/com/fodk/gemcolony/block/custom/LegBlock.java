package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.block.state.GemColonyBlockStateProperties;
import com.fodk.gemcolony.util.VoxelShapeUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LegBlock extends Block implements ConstructedMultiblock{

    public static final EnumProperty<LegPart> PART = EnumProperty.create("part", LegPart.class);
    public static final IntegerProperty CONSTRUCTION_STAGE = GemColonyBlockStateProperties.CONSTRUCTION_STAGE;

    public LegBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
                .setValue(PART, LegPart.CENTER)
                .setValue(CONSTRUCTION_STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, PART, CONSTRUCTION_STAGE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == LegPart.CENTER
                ? RenderShape.MODEL
                : RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    private static final VoxelShape BOTTOM_SHAPE = Block.box(3, 0, 0, 13, 16, 5);
    private static final VoxelShape CENTER_SHAPE = Block.box(3, 0, 0, 13, 16, 5);
    private static final VoxelShape SIDE_BOTTOM_SHAPE = Block.box(3, 0, 11, 13, 16, 16);
    private static final VoxelShape SIDE_SHAPE = Block.box(3, 0, 5, 13, 16, 16);
    private static final VoxelShape SIDE_TOP_SHAPE = Block.box(0, 0, 5, 16, 16, 16);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(PART)) {
            case BOTTOM -> BOTTOM_SHAPE;
            case SIDE_BOTTOM -> SIDE_BOTTOM_SHAPE;
            case CENTER -> CENTER_SHAPE;
            case SIDE -> SIDE_SHAPE;
            case SIDE_TOP -> SIDE_TOP_SHAPE;
            default -> super.getShape(state, level, pos, context);
        };

        return VoxelShapeUtil.rotateShape(shape, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();

        BlockPos origin = context.getClickedPos();

        BlockPos[] positions = {
                origin.above(),
                origin.below(),
                origin.relative(facing),
                origin.relative(facing).above(),
                origin.relative(facing).below()
        };

        for (BlockPos pos : positions) {
            if (!context.getLevel().getBlockState(pos).canBeReplaced()) {
                return null;
            }
        }

        return this.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                .setValue(PART, LegPart.CENTER);
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
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            LegPart part = state.getValue(PART);
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            BlockPos centerPos = switch (part) {
                case CENTER -> pos;
                case TOP -> pos.below();
                case BOTTOM -> pos.above();
                case SIDE -> pos.relative(facing.getOpposite());
                case SIDE_TOP -> pos.relative(facing.getOpposite()).below();
                case SIDE_BOTTOM -> pos.relative(facing.getOpposite()).above();
            };

            BlockPos[] positions = {
                    centerPos,
                    centerPos.above(),
                    centerPos.below(),
                    centerPos.relative(facing),
                    centerPos.relative(facing).above(),
                    centerPos.relative(facing).below()
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
        LegPart part = state.getValue(PART);
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

        if (part == LegPart.CENTER) {
            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }

        BlockPos centerPos = switch (part) {
            case TOP -> pos.below();
            case BOTTOM -> pos.above();
            case SIDE -> pos.relative(facing.getOpposite());
            case SIDE_TOP -> pos.relative(facing.getOpposite()).below();
            case SIDE_BOTTOM -> pos.relative(facing.getOpposite()).above();
            case CENTER -> pos;
        };

        if (neighbourPos.equals(centerPos)) {
            if (neighbourState.getBlock() != this
                    || neighbourState.getValue(PART) != LegPart.CENTER
                    || neighbourState.getValue(BlockStateProperties.HORIZONTAL_FACING) != facing) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    public void placeStructure(Level level, BlockPos pos, BlockState state) {
        Direction right = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

        BlockPos[] positions = {
                pos.above(),
                pos.below(),
                pos.relative(right),
                pos.relative(right).above(),
                pos.relative(right).below()
        };

        LegPart[] parts = {
                LegPart.TOP,
                LegPart.BOTTOM,
                LegPart.SIDE,
                LegPart.SIDE_TOP,
                LegPart.SIDE_BOTTOM
        };

        for (int i = 0; i < positions.length; i++) {
            BlockPos partPos = positions[i];

            if (level.getBlockState(partPos).canBeReplaced()) {
                level.setBlock(
                        partPos,
                        state.setValue(PART, parts[i]),
                        3
                );
            }
        }
    }
}