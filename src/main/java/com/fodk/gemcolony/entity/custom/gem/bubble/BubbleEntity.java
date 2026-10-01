package com.fodk.gemcolony.entity.custom.gem.bubble;

import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.fluid.ModFluids;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.GemItem;
import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class BubbleEntity extends Entity implements GeoAnimatable {

    private static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(BubbleEntity.class, EntityDataSerializers.ITEM_STACK);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int groundCheckTimer;
    private double groundY;
    private double floatTime;

    public BubbleEntity(EntityType<? extends BubbleEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ITEM, ItemStack.EMPTY);
    }

    public ItemStack getItem() {
        return entityData.get(ITEM);
    }

    public void setItem(ItemStack stack) {
        entityData.set(ITEM, stack.copy());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        input.read("Item", ItemStack.CODEC).ifPresent(stack ->
                entityData.set(ITEM, stack)
        );
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.store("Item", ItemStack.CODEC, getItem());
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // We'll add animations later.
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float amount) {
        ItemStack stack = getItem();

        if (!stack.isEmpty()) {
            spawnAtLocation(serverLevel, stack);
        }

        discard();

        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        if (!level().isClientSide() && player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {

                ServerPlayer.RespawnConfig respawn = serverPlayer.getRespawnConfig();

                if (respawn != null) {
                    MinecraftServer server = level().getServer();
                    ServerLevel respawnLevel =
                            server.getLevel(respawn.respawnData().dimension());

                    if (respawnLevel != null) {
                        BlockPos spawnPos = respawn.respawnData().pos();

                        double angle = level().getRandom().nextDouble() * Math.PI * 2.0;
                        double distance = 2.0 + level().getRandom().nextDouble() * 2.0;

                        double x = spawnPos.getX() + 0.5 + Math.cos(angle) * distance;
                        double z = spawnPos.getZ() + 0.5 + Math.sin(angle) * distance;
                        double y = 2 + spawnPos.getY() + level().getRandom().nextFloat() * 4f;

                        Vec3 start = Vec3.atCenterOf(spawnPos).add(0, 1.0, 0);
                        Vec3 target = new Vec3(x, y, z);

                        ClipContext clipContext = new ClipContext(
                                start,
                                target,
                                ClipContext.Block.COLLIDER,
                                ClipContext.Fluid.NONE,
                                this
                        );

                        BlockHitResult hit = respawnLevel.clip(clipContext);

                        if (hit.getType() == HitResult.Type.BLOCK) {
                            Vec3 direction = target.subtract(start).normalize();

                            Vec3 hitPosition = hit.getLocation().subtract(direction.scale(0.7));

                            x = hitPosition.x;
                            y = hitPosition.y;
                            z = hitPosition.z;
                        }

                        this.teleportTo(
                                respawnLevel,
                                x, y, z,
                                Set.of(),
                                getYRot(),
                                getXRot(),
                                true
                        );
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        if (!level().isClientSide()) {
            ItemStack stack = getItem();

            if (!stack.isEmpty()) {
                stack.set(ModDataComponents.BUBBLED, true);
                spawnAtLocation((ServerLevel) level(), stack);
            }

            discard();
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick() {
        ItemStack bubbledItem = getItem();
        if (bubbledItem.getItem() instanceof GemItem gemItem) {
            gemItem.tickInBubble(level(), bubbledItem);
        }

        floatTime += 0.05;

        Vec3 velocity = getDeltaMovement();

        // bobbing
        double floatVelocity = Math.sin(floatTime) * 0.01;

        velocity = new Vec3(
                velocity.x,
                velocity.y + floatVelocity,
                velocity.z
        );

        if (isInBuoyantFluid()) {
            BlockPos fluidPos = blockPosition();

            // Find the top of the fluid column.
            while (isBuoyantFluid(level().getFluidState(fluidPos.above()))) {
                fluidPos = fluidPos.above();
            }

            FluidState fluidState = level().getFluidState(fluidPos);

            double fluidSurface =
                    fluidPos.getY() + fluidState.getHeight(level(), fluidPos);

            double bubbleBottom = getY() - 0.3;

            double distanceToSurface = fluidSurface - bubbleBottom;

            double buoyancy = 0.04;

            if (distanceToSurface <= 1.0) {
                buoyancy = 0.0;
            }

            velocity = new Vec3(
                    velocity.x * 0.99,
                    velocity.y + buoyancy,
                    velocity.z * 0.99
            );
        }

        move(MoverType.SELF, velocity);

        if (!level().isClientSide()) {
            pushEntities();

            Vec3 currentVelocity = getDeltaMovement();

            setDeltaMovement(
                    currentVelocity.x * 0.90,
                    currentVelocity.y * 0.90,
                    currentVelocity.z * 0.90
            );
        }

        super.tick();
    }

    private void pushEntities() {
        for (Entity entity : level().getPushableEntities(this, getBoundingBox().inflate(0.2))) {
            if (!entity.isPushable()) {
                continue;
            }

            Vec3 difference = position().subtract(entity.position());
            double distance = difference.length();

            if (distance < 1.0E-4) {
                continue;
            }

            difference = difference.normalize();

            double strength = 0.05;
            Vec3 push = difference.scale(strength);

            setDeltaMovement(
                    getDeltaMovement().x + push.x,
                    getDeltaMovement().y + push.y,
                    getDeltaMovement().z + push.z
            );
        }
    }

    private boolean isInBuoyantFluid() {
        return isBuoyantFluid(level().getFluidState(blockPosition()));
    }

    private boolean isBuoyantFluid(FluidState fluidState) {
        Fluid fluid = fluidState.getType();

        return fluid == Fluids.WATER
                || fluid == ModFluids.BLUE_ESSENCE.get()
                || fluid == ModFluids.FLOWING_BLUE_ESSENCE.get()
                || fluid == ModFluids.YELLOW_ESSENCE.get()
                || fluid == ModFluids.FLOWING_YELLOW_ESSENCE.get()
                || fluid == ModFluids.WHITE_ESSENCE.get()
                || fluid == ModFluids.FLOWING_WHITE_ESSENCE.get()
                || fluid == ModFluids.PINK_ESSENCE.get()
                || fluid == ModFluids.FLOWING_PINK_ESSENCE.get();
    }
}
