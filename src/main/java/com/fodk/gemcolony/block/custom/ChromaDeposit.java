package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.util.ColorUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChromaDeposit extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {

    public static final MapCodec<ChromaDeposit> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    propertiesCodec(),
                    Codec.INT.fieldOf("colorIndex").forGetter(block -> block.colorIndex)
            ).apply(instance, ChromaDeposit::new)
    );

    public MapCodec<ChromaDeposit> codec() {
        return CODEC;
    }

    int colorIndex;
    int colorInt;

    public ChromaDeposit(Properties properties, int colorIndex) {
        super(properties
                .strength(1)
                .requiresCorrectToolForDrops()
                .sound(SoundType.AMETHYST)
                .lightLevel(state -> 7)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY));

        registerDefaultState(stateDefinition.any().setValue(FACE, AttachFace.FLOOR).setValue(WATERLOGGED, false));

        this.colorIndex = colorIndex;
        colorInt = ColorUtil.colorToInt(ColorUtil.colorFromIndex(colorIndex));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(8) == 0) {
            int randomParticleCount = 5 + random.nextInt(4);
            for(int i = 0; i < randomParticleCount; i++) {
                DustParticleOptions dustParticle = new DustParticleOptions(colorInt, 0.1f * random.nextInt(2, 8));

                float x = pos.getX() + 0.5f + (random.nextFloat() - 0.5f) * 0.6f;
                float y = pos.getY() + 0.5f + (random.nextFloat() - 0.5f) * 0.6f;
                float z = pos.getZ() + 0.5f + (random.nextFloat() - 0.5f) * 0.6f;

                level.addParticle(dustParticle, x, y, z, 1, 1, 1);
            }
        }
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return state.setValue(WATERLOGGED, waterlogged);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, WATERLOGGED);
    }

    private static final VoxelShape FLOOR_CEILING_SHAPE = Block.box(2, 0, 2, 14, 16, 14);
    private static final VoxelShape WALL_NS_SHAPE = Block.box(2, 2, 0, 14, 14, 16); // deep along Z
    private static final VoxelShape WALL_EW_SHAPE = Block.box(0, 2, 2, 16, 14, 14); // deep along X

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(FACE) != AttachFace.WALL) {
            return FLOOR_CEILING_SHAPE;
        }
        Direction facing = state.getValue(FACING);
        return (facing == Direction.NORTH || facing == Direction.SOUTH) ? WALL_NS_SHAPE : WALL_EW_SHAPE;
    }
}
