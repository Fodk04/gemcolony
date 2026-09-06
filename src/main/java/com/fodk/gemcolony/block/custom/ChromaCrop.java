package com.fodk.gemcolony.block.custom;

import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;

public class ChromaCrop extends NetherWartBlock {

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final VoxelShape[] SHAPES = Block.boxes(3, age ->
            Block.box(7 - (5f/(float)MAX_AGE * (float)age), 0, 7 - (5f/(float)MAX_AGE * (float)age), 9 + (5f/(float)MAX_AGE * (float)age), 1 + age * 5, 9 + (5f/(float)MAX_AGE * (float)age)));

    public ChromaCrop(Properties properties) {
        super(properties
                .randomTicks()
                .instabreak()
                .sound(SoundType.AMETHYST)
                .lightLevel(state -> 3)
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(AGE)];
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData) {
        return new ItemStack(ModItems.CHROMA_SEED.get());
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModTags.Blocks.CHROMA_PLANTABLE);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return (Integer)state.getValue(AGE) <= MAX_AGE;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = (Integer)state.getValue(AGE);
        boolean grown = false;

        if (age < MAX_AGE && CommonHooks.canCropGrow(level, pos, state, random.nextInt(10) == 0)) {
            state = (BlockState)state.setValue(AGE, age + 1);
            level.setBlock(pos, state, 2);
            CommonHooks.fireCropGrowPost(level, pos, state);
            grown = true;
        }
        if(age == MAX_AGE){
            Block growthBlock = level.getBlockState(pos.below(1)).getBlock();
            level.setBlockAndUpdate(pos, BlockToColoredChroma.depositFromGrowthBlock(growthBlock).get().defaultBlockState());
            grown = true;
        }
        if(grown){
            int randomParticleCount = 4 + level.getRandom().nextInt(4);
            for(int i = 0; i < randomParticleCount; i++) {
                ServerLevel serverLevel = (ServerLevel) level;

                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, 1,
                        0.15f, 0.15, 0.15, 0.01);
            }
        }
    }


}
