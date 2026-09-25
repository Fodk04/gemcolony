package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.block.entity.custom.InjectorBlockEntity;
import com.fodk.gemcolony.block.state.GemColonyBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CrystalBlock extends Block implements ConstructedMultiblock {

    public static final EnumProperty<CrystalPart> PART = EnumProperty.create("part", CrystalPart.class);
    public static final IntegerProperty CONSTRUCTION_STAGE = GemColonyBlockStateProperties.CONSTRUCTION_STAGE;

    public CrystalBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(PART, CrystalPart.CENTER)
                        .setValue(CONSTRUCTION_STAGE, 0));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == CrystalPart.CENTER
                ? RenderShape.MODEL
                : RenderShape.INVISIBLE;
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    private static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 3, 16, 16, 16);
    private static final VoxelShape NW_SHAPE = Block.box(3, 0, 3, 16, 16, 16);
    private static final VoxelShape WEST_SHAPE = Block.box(3, 0, 0, 16, 16, 16);
    private static final VoxelShape SW_SHAPE = Block.box(3, 0, 0, 16, 16, 13);
    private static final VoxelShape NE_SHAPE = Block.box(0, 0, 3, 13, 16, 16);
    private static final VoxelShape EAST_SHAPE = Block.box(0, 0, 0, 13, 16, 16);
    private static final VoxelShape SE_SHAPE = Block.box(0, 0, 0, 13, 16, 13);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 0, 16, 16, 13);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(PART)) {
            case BOTTOM_NORTH -> NORTH_SHAPE;
            case MIDDLE_NORTH -> NORTH_SHAPE;
            case TOP_NORTH -> NORTH_SHAPE;
            case BOTTOM_NORTH_EAST -> NE_SHAPE;
            case MIDDLE_NORTH_EAST -> NE_SHAPE;
            case TOP_NORTH_EAST -> NE_SHAPE;
            case BOTTOM_NORTH_WEST -> NW_SHAPE;
            case MIDDLE_NORTH_WEST -> NW_SHAPE;
            case TOP_NORTH_WEST -> NW_SHAPE;
            case BOTTOM_EAST -> EAST_SHAPE;
            case MIDDLE_EAST -> EAST_SHAPE;
            case TOP_EAST -> EAST_SHAPE;
            case BOTTOM_WEST -> WEST_SHAPE;
            case MIDDLE_WEST -> WEST_SHAPE;
            case TOP_WEST -> WEST_SHAPE;
            case BOTTOM_SOUTH_EAST -> SE_SHAPE;
            case MIDDLE_SOUTH_EAST -> SE_SHAPE;
            case TOP_SOUTH_EAST -> SE_SHAPE;
            case BOTTOM_SOUTH_WEST -> SW_SHAPE;
            case MIDDLE_SOUTH_WEST -> SW_SHAPE;
            case TOP_SOUTH_WEST -> SW_SHAPE;
            case BOTTOM_SOUTH -> SOUTH_SHAPE;
            case MIDDLE_SOUTH -> SOUTH_SHAPE;
            case TOP_SOUTH -> SOUTH_SHAPE;
            default -> super.getShape(state, level, pos, context);
        };

        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, CONSTRUCTION_STAGE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos centerPos = context.getClickedPos();

        for (int y = -1; y <= 1; y++) {
            for (int z = -1; z <= 1; z++) {
                for (int x = -1; x <= 1; x++) {

                    BlockPos pos = centerPos.offset(x, y, z);

                    if (!context.getLevel()
                            .getBlockState(pos)
                            .canBeReplaced()) {
                        return null;
                    }
                }
            }
        }

        return this.defaultBlockState().setValue(PART, CrystalPart.CENTER);
    }

    @Override
    public BlockState getConstructionState(Level level, BlockPos pos, BlockState state) {
        return state.setValue(PART, CrystalPart.CENTER);
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

        CrystalPart[] parts = {

                // TOP
                CrystalPart.TOP_NORTH_WEST,
                CrystalPart.TOP_NORTH,
                CrystalPart.TOP_NORTH_EAST,
                CrystalPart.TOP_WEST,
                CrystalPart.TOP_CENTER,
                CrystalPart.TOP_EAST,
                CrystalPart.TOP_SOUTH_WEST,
                CrystalPart.TOP_SOUTH,
                CrystalPart.TOP_SOUTH_EAST,

                // MIDDLE
                CrystalPart.MIDDLE_NORTH_WEST,
                CrystalPart.MIDDLE_NORTH,
                CrystalPart.MIDDLE_NORTH_EAST,
                CrystalPart.MIDDLE_WEST,
                CrystalPart.MIDDLE_EAST,
                CrystalPart.MIDDLE_SOUTH_WEST,
                CrystalPart.MIDDLE_SOUTH,
                CrystalPart.MIDDLE_SOUTH_EAST,

                // BOTTOM
                CrystalPart.BOTTOM_NORTH_WEST,
                CrystalPart.BOTTOM_NORTH,
                CrystalPart.BOTTOM_NORTH_EAST,
                CrystalPart.BOTTOM_WEST,
                CrystalPart.BOTTOM_CENTER,
                CrystalPart.BOTTOM_EAST,
                CrystalPart.BOTTOM_SOUTH_WEST,
                CrystalPart.BOTTOM_SOUTH,
                CrystalPart.BOTTOM_SOUTH_EAST
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
    public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
        super.destroy(level, pos, state);

        CrystalPart part = state.getValue(PART);

        BlockPos centerPos = getCenterPos(pos, part);

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
        CrystalPart part = state.getValue(PART);

        if (part == CrystalPart.CENTER) {
            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
        }

        BlockPos centerPos = getCenterPos(pos, part);

        if (neighbourPos.equals(centerPos)) {
            if (neighbourState.getBlock() != this
                    || neighbourState.getValue(PART) != CrystalPart.CENTER) {

                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    private static BlockPos getCenterPos(BlockPos pos, CrystalPart part) {
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

    private static BlockPos getInjectorDrillPos(BlockPos crystalCenter) {
        return crystalCenter.below(9);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockPos crystalCenter = getCenterPos(pos, state.getValue(PART));

            BlockPos drillPos = getInjectorDrillPos(crystalCenter);

            if (level.getBlockEntity(drillPos) instanceof InjectorBlockEntity injector) {
                return injector.handleInteraction(player, player.getMainHandItem());
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos crystalCenter = getCenterPos(pos, state.getValue(PART));

        BlockPos drillPos = getInjectorDrillPos(crystalCenter);

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

        CrystalPart part = state.getValue(PART);

        BlockPos crystalCenter = getCenterPos(pos, part);
        BlockPos drillPos = getInjectorDrillPos(crystalCenter);

        if (level.getBlockEntity(drillPos) instanceof InjectorBlockEntity injector) {

            injector.handleRedstoneSignal(level.hasNeighborSignal(pos));
        }
    }
}