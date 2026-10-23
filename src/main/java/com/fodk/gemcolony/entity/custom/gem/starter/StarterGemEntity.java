package com.fodk.gemcolony.entity.custom.gem.starter;

import com.fodk.gemcolony.block.custom.ConstructedMultiblock;
import com.fodk.gemcolony.construction.Assembly;
import com.fodk.gemcolony.construction.ConstructionRequirement;
import com.fodk.gemcolony.data.ConstructionProgressData;
import com.fodk.gemcolony.entity.custom.gem.ai.ChestSupplyGoal;
import com.fodk.gemcolony.entity.custom.gem.ai.ConstructionGoal;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StarterGemEntity extends GemEntity {

    public abstract List<Assembly> getConstructableAssemblies();

    protected StarterGemEntity(EntityType<? extends StarterGemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public boolean canConstruct(Assembly assembly) {
        return getConstructableAssemblies().contains(assembly);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.ATTACK_SPEED, 0.0D);
    }

    @Override
    public double getBaseMaxHealth() {
        return 4;
    }

    @Override
    public double getBaseAttackDamage() {
        return 0;
    }

    @Override
    public double getBaseMovementSpeed() {
        return 0.2;
    }

    @Override
    public double getBaseAttackSpeed() {
        return 0;
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (6 * qualityModifier);
    }

    @Override
    public int getMaxOutfits() {
        return 3;
    }

    @Override
    public int getMaxInsignias() {
        return 3;
    }

    @Override
    public int getMaxHairstyles() {
        return 0;
    }

    @Override
    public int getMaxMarkings() {
        return 0;
    }

    @Override
    public int getMaxVisors() {
        return 0;
    }

    @Override
    public Color generateHairColor() {
        return Color.black;
    }

    @Override
    public Color generateMarkingsColor() {
        return Color.black;
    }

    @Override
    public Color generateVisorColor() {
        return Color.black;
    }

    //40 SEC
    @Override
    public int getReformTime(){
        return 800;
    }

    @Override
    public void assignOrigin(ServerLevel level, BlockPos pos) {
        String name = getGemTypeName();
        this.entityData.set(NAME, name);
        setCustomName(Component.literal(name));
    }

    @Override
    protected boolean canWork() {
        return true;
    }

    @Override
    protected String getWorkMessage() {
        return "construct nearby machines.";
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(11, new ConstructionGoal(this));
    }

    @Override
    public float getBaseModelSize() {
        return 0.4f;
    }

    public boolean tryBuildBlock(BlockPos pos) {
        Level level = level();

        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            return false;
        }

        BlockPos centerPos = multiblock.getConstructionCenter(level, pos, state);
        BlockState centerState = level.getBlockState(centerPos);

        if (centerState.getBlock() != state.getBlock()) {
            return false;
        }

        if (!multiblock.getConstructionCenter(level, centerPos, centerState).equals(centerPos)) {
            return false;
        }

        Property<?> property = centerState.getBlock().getStateDefinition().getProperty("construction_stage");

        if (!(property instanceof IntegerProperty stageProperty)) {
            return false;
        }

        int stage = centerState.getValue(stageProperty);

        if (stage >= multiblock.getMaxConstructionStage()) {
            return false;
        }

        ConstructionProgressData data = ConstructionProgressData.get(serverLevel);
        int progress = data.getProgress(level.dimension(), centerPos);

        // Pay the construction cost only when work first begins
        if (progress == 0) {
            Map<Item, Integer> required = new HashMap<>();

            for (ConstructionRequirement requirement : multiblock.getConstructionRequirements()) {
                required.merge(requirement.item(), requirement.count(), Integer::sum);
            }

            // Check all requirements before consuming anything
            for (Map.Entry<Item, Integer> entry : required.entrySet()) {
                int available = 0;

                for (int slot = 0; slot < getContainerSize(); slot++) {
                    ItemStack stack = getItem(slot);

                    if (stack.is(entry.getKey())) {
                        available += stack.getCount();
                    }
                }

                if (available < entry.getValue()) {
                    return false;
                }
            }

            // Consume the requirements once for this block
            for (Map.Entry<Item, Integer> entry : required.entrySet()) {
                int remaining = entry.getValue();

                for (int slot = 0; slot < getContainerSize() && remaining > 0; slot++) {
                    ItemStack stack = getItem(slot);

                    if (!stack.is(entry.getKey())) {
                        continue;
                    }

                    int taken = Math.min(remaining, stack.getCount());
                    removeItem(slot, taken);
                    remaining -= taken;
                }
            }

            setChanged();
        }

        progress++;
        data.setProgress(level.dimension(), centerPos, progress);

        if (progress >= multiblock.getProgressNeeded()) {
            multiblock.advanceConstruction(level, centerPos, stage);
            data.clearProgress(level.dimension(), centerPos);
        }

        return true;
    }

    public boolean canAffordConstruction(BlockPos pos) {
        Level level = level();

        if (!(level instanceof ServerLevel serverLevel)) {
            return false;
        }

        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof ConstructedMultiblock multiblock)) {
            return false;
        }

        BlockPos centerPos = multiblock.getConstructionCenter(level, pos, state);
        BlockState centerState = level.getBlockState(centerPos);

        if (centerState.getBlock() != state.getBlock()) {
            return false;
        }

        ConstructionProgressData data = ConstructionProgressData.get(serverLevel);

        // Construction has already been paid for; any starter can continue it.
        if (data.getProgress(level.dimension(), centerPos) > 0) {
            return true;
        }

        Map<Item, Integer> required = new HashMap<>();

        for (ConstructionRequirement requirement : multiblock.getConstructionRequirements()) {
            required.merge(requirement.item(), requirement.count(), Integer::sum);
        }

        for (Map.Entry<Item, Integer> entry : required.entrySet()) {
            int available = 0;

            for (int slot = 0; slot < getContainerSize(); slot++) {
                ItemStack stack = getItem(slot);

                if (stack.is(entry.getKey())) {
                    available += stack.getCount();
                }
            }

            if (available < entry.getValue()) {
                return false;
            }
        }

        return true;
    }

    private static final RawAnimation CONSTRUCT_RIGHT = RawAnimation.begin().thenPlay("Right_Building");
    private static final RawAnimation CONSTRUCT_LEFT = RawAnimation.begin().thenPlay("Left_Building");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);

        controllers.add(new AnimationController<>("Construct", 2, state -> PlayState.STOP)
                .triggerableAnim("construct_right", CONSTRUCT_RIGHT)
                .triggerableAnim("construct_left", CONSTRUCT_LEFT));
    }

    public void playRandomConstruct() {
        if (this.getRandom().nextBoolean()) {
            this.triggerAnim("Construct", "construct_right");
        } else {
            this.triggerAnim("Construct", "construct_left");
        }
    }
}
