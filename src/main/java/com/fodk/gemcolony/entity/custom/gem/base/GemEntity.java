package com.fodk.gemcolony.entity.custom.gem.base;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.data.FacetRegistryData;
import com.fodk.gemcolony.data.FusionData;
import com.fodk.gemcolony.data.FusionRegistryData;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.ai.*;
import com.fodk.gemcolony.entity.custom.gem.placement.GemPlacement;
import com.fodk.gemcolony.entity.custom.gem.savedata.GemAppearanceData;
import com.fodk.gemcolony.entity.custom.gem.savedata.GemSaveData;
import com.fodk.gemcolony.entity.custom.gem.savedata.GemStateData;
import com.fodk.gemcolony.entity.custom.gem.savedata.ModEntityDataSerializers;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.item.custom.FuseItem;
import com.fodk.gemcolony.item.custom.GemItem;
import com.fodk.gemcolony.menu.GemMenu;
import com.fodk.gemcolony.sound.ModSounds;
import com.fodk.gemcolony.util.ColorUtil;
import com.fodk.gemcolony.util.GemCombatUtil;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.constant.DefaultAnimations;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.awt.*;
import java.util.*;
import java.util.List;


public abstract class GemEntity extends Monster implements GeoEntity, Container, MenuProvider, NeutralMob {

    public static final EntityDataAccessor<String> NAME = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> NICKNAME = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> GEM_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> OUTFIT = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> INSIGNIA = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HAIRSTYLE = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> OUTFIT_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> INSIGNIA_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HAIR_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GEM_PLACEMENT = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> QUALITY = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> CRACKED = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> WINGS = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MARKINGS = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MARKINGS_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VISOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VISOR_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> REFORM_PROGRESS = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> EMERGED = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<List<Integer>> ABILITIES = SynchedEntityData.defineId(GemEntity.class, ModEntityDataSerializers.INT_LIST.get());
    public static final EntityDataAccessor<Integer> BEHAVIOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<String> SHAPESHIFT = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<List<ItemStack>> FUSION_COMPONENTS = SynchedEntityData.defineId(GemEntity.class, ModEntityDataSerializers.ITEM_STACK_LIST.get());
    public static final EntityDataAccessor<Boolean> FUSING = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.BOOLEAN);

    private NonNullList<ItemStack> inventory;
    @Nullable
    private BlockPos workPos;
    private final Set<GemAbility> abilities = new HashSet<>();

    private long persistentAngerEndTime = NeutralMob.NO_ANGER_END_TIME;
    @Nullable
    private EntityReference<LivingEntity> persistentAngerTarget;

    public GemEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        xpReward = 0;
        this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(NAME, "");
        entityData.define(NICKNAME, "");
        entityData.define(GEM_COLOR,0xFFFFFF);
        entityData.define(OUTFIT, -1);
        entityData.define(INSIGNIA, -1);
        entityData.define(HAIRSTYLE, -1);
        entityData.define(OUTFIT_COLOR, 0xFFFFFF);
        entityData.define(INSIGNIA_COLOR, 0xFFFFFF);
        entityData.define(HAIR_COLOR, 0xFFFFFF);
        entityData.define(GEM_PLACEMENT, -1);
        entityData.define(QUALITY, 1);
        entityData.define(CRACKED, false);
        entityData.define(VARIANT, -1);
        entityData.define(WINGS, -1);
        entityData.define(MARKINGS, -1);
        entityData.define(MARKINGS_COLOR, 0xFFFFFF);
        entityData.define(VISOR, -1);
        entityData.define(VISOR_COLOR, 0xFFFFFF);
        entityData.define(REFORM_PROGRESS, MAX_REFORM_PROGRESS);
        entityData.define(EMERGED, false);
        entityData.define(OWNER_UUID, "");
        entityData.define(ABILITIES, List.of());
        entityData.define(BEHAVIOR, GemBehavior.WANDER.ordinal());
        entityData.define(SHAPESHIFT, "");
        entityData.define(FUSION_COMPONENTS, List.of());
        entityData.define(FUSING, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (QUALITY.equals(key) || FUSION_COMPONENTS.equals(key)) {
            refreshDimensions();

            if (level().isClientSide()) {
                applyQualityModifiers();
            }

            int size = getContainerSize();

            if (inventory.size() != size) {
                NonNullList<ItemStack> newInventory =
                        NonNullList.withSize(size, ItemStack.EMPTY);

                int copySize = Math.min(inventory.size(), newInventory.size());

                for (int i = 0; i < copySize; i++) {
                    newInventory.set(i, inventory.get(i));
                }

                inventory = newInventory;
            }
        }

        if (ABILITIES.equals(key)) {
            abilities.clear();

            for (int id : entityData.get(ABILITIES)) {
                abilities.add(GemAbility.fromId(id));
            }
        }
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData) {
        if (spawnReason == EntitySpawnReason.COMMAND) {
            setVariant(getRandomVariant());
            assignOrigin(level.getLevel(), blockPosition());
            generateAppearance(generateGemColor(), getMaxOutfits(), generateOutfitColor(), getMaxInsignias(), generateInsigniaColor(), getMaxHairstyles(), generateHairColor(), getMaxMarkings(), generateMarkingsColor(), getMaxVisors(), generateVisorColor());
            entityData.set(EMERGED, true);
            setQuality(random.nextInt(3));
            initializeAbilities();
            entityData.set(REFORM_PROGRESS, 0);
            this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
            entityData.set(GEM_PLACEMENT, GemPlacement.getRandomGemPlacement().ordinal());
        }

        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    public int getRandomVariant(){
        return -1;
    }

    public void setVariant(int variant){
        entityData.set(VARIANT, variant);
    }

    public int getVariantFromChroma(int chromaIndex){
        return -1;
    }

    public void initializeGem(int variant){
        setVariant(variant);
        assignOrigin((ServerLevel) level(), blockPosition());
        generateAppearance(generateGemColor(), getMaxOutfits(), generateOutfitColor(), getMaxInsignias(), generateInsigniaColor(), getMaxHairstyles(), generateHairColor(), getMaxMarkings(), generateMarkingsColor(), getMaxVisors(), generateVisorColor());
        entityData.set(EMERGED, true);
        setQuality(random.nextInt(3));
        initializeAbilities();
        entityData.set(REFORM_PROGRESS, 0);
        this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
        entityData.set(GEM_PLACEMENT, GemPlacement.getRandomGemPlacement().ordinal());
    }

    public void initializeGemFromChroma(int chromaIndex){
        setVariant(getVariantFromChroma(chromaIndex));
        assignOrigin((ServerLevel) level(), blockPosition());
        generateAppearance(generateGemColor(), getMaxOutfits(), generateOutfitColor(), getMaxInsignias(), generateInsigniaColor(), getMaxHairstyles(), generateHairColor(), getMaxMarkings(), generateMarkingsColor(), getMaxVisors(), generateVisorColor());
        entityData.set(EMERGED, true);
        setQuality(random.nextInt(3));
        initializeAbilities();
        entityData.set(REFORM_PROGRESS, 0);
        this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
        entityData.set(GEM_PLACEMENT, GemPlacement.getRandomGemPlacement().ordinal());
    }

    public void initializeGemFromReform(int reformProgress){
        applyQualityModifiers();
        UpdateDisplayedName();
        entityData.set(REFORM_PROGRESS, reformProgress);
    }

    public void initializeSameGemFusion(){
        setVariant(0);
        generateAppearance(mixComponentsGemColors(), getMaxOutfits(), mixComponentsOutfitColors(), getMaxInsignias(), mixComponentsInsigniaColors(), getMaxHairstyles(), mixComponentsHairColors(), getMaxMarkings(), mixComponentsMarkingsColors(), getMaxVisors(), mixComponentsVisorColors());
        setQuality(getFusionQuality());
        initializeAbilities();
        entityData.set(REFORM_PROGRESS, (int)(MAX_REFORM_PROGRESS * (1 - getPercentageToReform())));
        entityData.set(BEHAVIOR, GemBehavior.FOLLOW.ordinal());

        this.inventory = NonNullList.create();
        for (ItemStack fusionComponent : getFusionComponents()) {
            GemSaveData saveData = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA);
            if (saveData == null) {
                continue;
            }

            for (ItemStack stack : saveData.gemStateData().inventory()) {
                this.inventory.add(stack.copy());
            }
        }

        String name = "Mega " + getNameWithoutVariant();
        this.entityData.set(NAME, name);
        setCustomName(Component.literal(name));
        this.entityData.set(OWNER_UUID, getFusionComponents().get(0).get(ModDataComponents.GEM_SAVE_DATA).gemStateData().ownerUUID());
    }

    public void setQuality(int quality){
        entityData.set(QUALITY, quality);
        applyQualityModifiers();
    }

    public void assignOrigin(ServerLevel level, BlockPos pos) {
        int regionX = Math.floorDiv(pos.getX(), 1024);
        int regionZ = Math.floorDiv(pos.getZ(), 1024);

        FacetRegistryData.FacetAssignment assignment = FacetRegistryData.get(level).assignGem(level.dimension(), regionX, regionZ);

        String facetSuffix = level.dimension() == Level.NETHER ? "N" : level.dimension() == Level.END ? "E" : "";
        String cut = computeCutString(assignment.gemCountInRegion());

        String name = getGemTypeName() + " Facet-" + assignment.facetNumber() + facetSuffix + " Cut-" + cut;
        this.entityData.set(NAME, name);
        setCustomName(Component.literal(name));
    }

    public void setNickname(String nickname) {
        entityData.set(NICKNAME, nickname);
        UpdateDisplayedName();
    }

    private void UpdateDisplayedName(){
        if(entityData.get(NICKNAME).isBlank()){
            setCustomName(Component.literal(entityData.get(NAME)));
        }else{
            setCustomName(Component.literal(entityData.get(NICKNAME)));
        }
    }

    private static String computeCutString(int gemCountInRegion) {
        int index0 = gemCountInRegion - 1;
        int letterIndex = index0 / 9;
        int number = (index0 % 9) + 1;
        char letter = (char) ('A' + letterIndex);
        return number + "X" + letter;
    }

    public abstract double getBaseMaxHealth();
    public abstract double getBaseAttackDamage();
    public abstract double getBaseMovementSpeed();
    public abstract double getBaseAttackSpeed();

    public void applyQualityModifiers() {
        updateKnockbackResistance();

        getAttribute(Attributes.MAX_HEALTH).removeModifier(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_health"));
        getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_damage"));

        if (!getFusionComponents().isEmpty()) {
            applyFusionStats();
            setHealth(getMaxHealth());
            return;
        }

        int quality = entityData.get(QUALITY);

        double healthMultiplier = switch (quality) {
            case 0 -> -0.25D;
            case 2 -> 0.40D;
            default -> 0.0D;
        };

        double damageMultiplier = switch (quality) {
            case 0 -> -0.25D;
            case 2 -> 0.20D;
            default -> 0.0D;
        };

        getAttribute(Attributes.MAX_HEALTH).addOrReplacePermanentModifier(
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_health"),
                        healthMultiplier,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        getAttribute(Attributes.ATTACK_DAMAGE).addOrReplacePermanentModifier(
                new AttributeModifier(
                        Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_damage"),
                        damageMultiplier,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                )
        );

        setHealth(getMaxHealth());
    }

    private void updateKnockbackResistance() {
        double sizeMultiplier = getDefaultDimensions(Pose.STANDING).width() / super.getDefaultDimensions(Pose.STANDING).width();

        getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Math.clamp((sizeMultiplier - 1.0) * 0.5, 0.0, 1.0));
    }

    private static double getQualityHealthMultiplier(int quality) {
        return switch (quality) {
            case 0 -> 0.75D;
            case 2 -> 1.40D;
            default -> 1.0D;
        };
    }

    private static double getQualityDamageMultiplier(int quality) {
        return switch (quality) {
            case 0 -> 0.75D;
            case 2 -> 1.20D;
            default -> 1.0D;
        };
    }

    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxMarkings, Color markingsColor, int maxVisors, Color visorColor) {
        int outfitIndex = maxOutfits > 0 ? random.nextInt(maxOutfits) : -1;
        int insigniaIndex = maxInsignias > 0 ? random.nextInt(maxInsignias) : -1;
        int hairstyleIndex = maxHairstyles > 0 ? random.nextInt(maxHairstyles) : -1;
        int markingsIndex = maxMarkings > 0 ? random.nextInt(maxMarkings) : -1;

        int visorIndex;
        if(alwaysHasVisor()){
            // always "TRY" to set a visor
            visorIndex = maxVisors > 0 ? random.nextInt(maxVisors) : -1;
        }else{
            if(random.nextFloat() < 0.10f){
                // 10% chance it has a visor
                visorIndex = maxVisors > 0 ? random.nextInt(maxVisors) : -1;
            }else{
                // 90% chance it doesnt have a visor
                visorIndex = -1;
            }
        }

        this.entityData.set(GEM_COLOR, ColorUtil.colorToInt(gemColor));
        this.entityData.set(OUTFIT, outfitIndex);
        this.entityData.set(OUTFIT_COLOR, ColorUtil.colorToInt(outfitColor));
        this.entityData.set(INSIGNIA, insigniaIndex);
        this.entityData.set(INSIGNIA_COLOR, ColorUtil.colorToInt(insigniaColor));
        this.entityData.set(HAIRSTYLE, hairstyleIndex);
        this.entityData.set(HAIR_COLOR, ColorUtil.colorToInt(hairColor));
        this.entityData.set(MARKINGS, markingsIndex);
        this.entityData.set(MARKINGS_COLOR, ColorUtil.colorToInt(markingsColor));
        this.entityData.set(VISOR, visorIndex);
        this.entityData.set(VISOR_COLOR, ColorUtil.colorToInt(visorColor));
    }

    protected boolean alwaysHasVisor(){
        return false;
    }

    public GemSaveData toSaveData() {
        return new GemSaveData(toSavedAppearance(),toSavedState());
    }

    public GemAppearanceData toSavedAppearance(){
        return new GemAppearanceData(
                entityData.get(NAME), entityData.get(NICKNAME),
                entityData.get(GEM_COLOR), entityData.get(OUTFIT), entityData.get(OUTFIT_COLOR),
                entityData.get(INSIGNIA), entityData.get(INSIGNIA_COLOR),
                entityData.get(HAIRSTYLE), entityData.get(HAIR_COLOR), entityData.get(GEM_PLACEMENT),
                entityData.get(VARIANT), entityData.get(WINGS), entityData.get(MARKINGS), entityData.get(MARKINGS_COLOR),
                entityData.get(VISOR), entityData.get(VISOR_COLOR)
        );
    }

    public GemStateData toSavedState(){
        return new GemStateData(
                entityData.get(REFORM_PROGRESS), entityData.get(QUALITY), entityData.get(EMERGED),
                entityData.get(CRACKED), entityData.get(OWNER_UUID), entityData.get(BEHAVIOR), workPos,
                List.copyOf(inventory), List.copyOf(abilities), entityData.get(SHAPESHIFT),
                List.copyOf(getFusionComponents()), entityData.get(FUSING)
        );
    }

    public void applySaveData(GemSaveData data) {
        applySavedAppearance(data.gemAppearanceData());
        applySavedState(data.gemStateData());
    }

    public void applySavedAppearance(GemAppearanceData data){
        entityData.set(NAME, data.name());
        entityData.set(NICKNAME, data.nickname());
        entityData.set(GEM_COLOR, data.color());
        entityData.set(OUTFIT, data.outfit());
        entityData.set(OUTFIT_COLOR, data.outfitColor());
        entityData.set(INSIGNIA, data.insignia());
        entityData.set(INSIGNIA_COLOR, data.insigniaColor());
        entityData.set(HAIRSTYLE, data.hairstyle());
        entityData.set(HAIR_COLOR, data.hairColor());
        entityData.set(GEM_PLACEMENT, data.gemPlacement());
        entityData.set(VARIANT, data.variant());
        entityData.set(WINGS, data.wings());
        entityData.set(MARKINGS, data.markings());
        entityData.set(MARKINGS_COLOR, data.markingsColor());
        entityData.set(VISOR, data.visor());
        entityData.set(VISOR_COLOR, data.visorColor());
    }

    public void applySavedState(GemStateData data){
        entityData.set(REFORM_PROGRESS, data.reformProgress());
        entityData.set(QUALITY, data.quality());
        entityData.set(EMERGED, data.emerged());
        entityData.set(CRACKED, data.cracked());
        entityData.set(OWNER_UUID, data.ownerUUID());
        entityData.set(BEHAVIOR, data.behavior());
        entityData.set(SHAPESHIFT, data.shapeshift());
        this.workPos = data.workPos();

        List<ItemStack> copies = new ArrayList<>();
        for (ItemStack stack : data.fusionComponents()) {
            copies.add(stack.copy());
        }
        entityData.set(FUSION_COMPONENTS, copies);
        entityData.set(FUSING, data.fusing());

        this.inventory = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
        int copySize = Math.min(inventory.size(), data.inventory().size());
        for (int i = 0; i < copySize; i++) {
            inventory.set(i, data.inventory().get(i).copy());
        }

        abilities.clear();
        abilities.addAll(data.abilities());
        syncAbilities();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("GemData", GemSaveData.CODEC, this.toSaveData());
        addPersistentAngerSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("GemData", GemSaveData.CODEC).ifPresent(this::applySaveData);
        readPersistentAngerSaveData(level(), input);
    }

    public abstract Item getGemItem();

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static final int MAX_REFORM_PROGRESS = 140;

    @Override
    public void die(DamageSource source) {
        level().addParticle(ParticleTypes.EXPLOSION_EMITTER, blockPosition().getX(), blockPosition().getY() + 2d, blockPosition().getZ(), 0, 0, 0);
        if(!level().isClientSide()){
            playSound(ModSounds.GEM_POOF.get(),
                    2.0F,
                    1.0F - (0.4F * random.nextFloat())
            );
            if(getFusionComponents().isEmpty()){
                killGem();
            }else{
                killFusion();
            }
            if(getOwnerEntity() instanceof Player player){
                player.sendSystemMessage(Component.literal(getCurrentName() + " was poofed at location x:" + (int) position().x + " /y:" + (int) position().y + " /z:" + (int) position().z));
            }
        }
        super.die(source);
    }

    void killGem(){
        ItemStack gem = new ItemStack(getGemItem());
        gem.set(ModDataComponents.GEM_SAVE_DATA, toSaveData());
        gem.set(ModDataComponents.REFORM_TIME, getReformTime());
        gem.set(ModDataComponents.REFORM_PROGRESS, MAX_REFORM_PROGRESS);

        spawnAtLocation((ServerLevel) this.level(), gem);
    }


    void killFusion() {
        int inventoryOffset = 0;

        for (ItemStack fusionComponent : getFusionComponents()) {
            GemSaveData saveData = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA);

            if (saveData == null) {
                continue;
            }

            GemStateData oldState = saveData.gemStateData();
            int componentInventorySize = oldState.inventory().size();

            // copy this component's section of the fused inventory
            List<ItemStack> componentInventory = new ArrayList<>();

            for (int i = 0; i < componentInventorySize; i++) {
                int fusedSlot = inventoryOffset + i;

                componentInventory.add(fusedSlot < inventory.size() ? inventory.get(fusedSlot).copy() : ItemStack.EMPTY);
            }

            inventoryOffset += componentInventorySize;

            // keep every other saved property of the original gem
            GemStateData updatedState = new GemStateData(
                    oldState.reformProgress(),
                    oldState.quality(),
                    oldState.emerged(),
                    oldState.cracked(),
                    oldState.ownerUUID(),
                    oldState.behavior(),
                    oldState.workPos(),
                    componentInventory,
                    oldState.abilities(),
                    oldState.shapeshift(),
                    oldState.fusionComponents(),
                    oldState.fusing()
            );

            // Drop drop a copy containing the updated saved state
            ItemStack updatedComponent = fusionComponent.copy();
            updatedComponent.set(ModDataComponents.GEM_SAVE_DATA, new GemSaveData(saveData.gemAppearanceData(), updatedState));

            spawnAtLocation((ServerLevel) level(), updatedComponent);
        }
    }


    public boolean unfuse(ServerLevel serverLevel) {
        List<ItemStack> components = getFusionComponents();

        if (components.isEmpty()) {
            return false;
        }

        List<GemEntity> spawnedGems = new ArrayList<>();
        int inventoryOffset = 0;

        for (ItemStack component : components) {
            GemSaveData saveData = component.get(ModDataComponents.GEM_SAVE_DATA);

            if (saveData == null) {
                for (GemEntity spawned : spawnedGems) {
                    spawned.discard();
                }
                return false;
            }

            GemStateData oldState = saveData.gemStateData();
            int componentInventorySize = oldState.inventory().size();

            List<ItemStack> componentInventory = new ArrayList<>();

            for (int i = 0; i < componentInventorySize; i++) {
                int fusedSlot = inventoryOffset + i;

                componentInventory.add(fusedSlot < inventory.size() ? inventory.get(fusedSlot).copy() : ItemStack.EMPTY);
            }

            inventoryOffset += componentInventorySize;

            GemStateData updatedState = new GemStateData(
                    oldState.reformProgress(),
                    oldState.quality(),
                    oldState.emerged(),
                    oldState.cracked(),
                    oldState.ownerUUID(),
                    oldState.behavior(),
                    oldState.workPos(),
                    componentInventory,
                    oldState.abilities(),
                    oldState.shapeshift(),
                    oldState.fusionComponents(),
                    oldState.fusing()
            );

            GemSaveData updatedSaveData = new GemSaveData(saveData.gemAppearanceData(), updatedState);

            if (!(component.getItem() instanceof GemItem gemItem)) {
                for (GemEntity spawned : spawnedGems) {
                    spawned.discard();
                }
                return false;
            }

            GemEntity gem = gemItem.entityType.create(serverLevel, EntitySpawnReason.TRIGGERED);

            if (gem == null) {
                for (GemEntity spawned : spawnedGems) {
                    spawned.discard();
                }
                return false;
            }

            ItemStack updatedComponent = component.copy();
            updatedComponent.set(ModDataComponents.GEM_SAVE_DATA, updatedSaveData);

            gem.applySaveData(updatedSaveData);
            gem.initializeGemFromReform(20);

            gem.setPos(
                    getX() + (random.nextDouble() - 0.5) * 2.0,
                    getY(),
                    getZ() + (random.nextDouble() - 0.5) * 2.0
            );

            if (!serverLevel.addFreshEntity(gem)) {
                for (GemEntity spawned : spawnedGems) {
                    spawned.discard();
                }
                return false;
            }

            spawnedGems.add(gem);
        }

        discard();
        return true;
    }

    public static float getReformProgressPercentage(int reformProgress){
        return 1f - (float)reformProgress/(float) MAX_REFORM_PROGRESS;
    }

    public static float getPercentageToReform(){
        return 0.55f;
    }

    public float getReformCenter(){
        return (float) (getHitbox().getYsize() / 2f);
    }

    public abstract int getReformTime();

    @Override
    public void tick() {
        super.tick();
        int reformProgress = entityData.get(REFORM_PROGRESS);
        if(getReformProgressPercentage(reformProgress) >= 1f){
            setNoGravity(false);
        }else{
            entityData.set(REFORM_PROGRESS, reformProgress - 1);
            setNoGravity(true);
            setDeltaMovement(new Vec3(0, -0.01, 0));
        }

        if (!level().isClientSide() && getTarget() == null && getPersistentAngerTarget() != null) {
            LivingEntity target = EntityReference.getLivingEntity(getPersistentAngerTarget(), level());

            if (target != null) {
                setTarget(target);
            }
        }

        if (!level().isClientSide() && level() instanceof ServerLevel serverLevel) {
            updatePersistentAnger(serverLevel, true);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        // Unowned to claimed
        if (!isOwned()) {
            if (!level().isClientSide()) {
                setOwner(player);
                Component message = Component.literal(getGemTypeName())
                        .withStyle(style -> style.withColor(getGemColor()).withoutShadow())
                        .append(Component.literal(" is now yours!"));

                ((ServerPlayer) player).sendOverlayMessage(message);

                ((ServerLevel) level()).sendParticles(
                        ParticleTypes.HEART,
                        getX(),
                        getY() + 1.0,
                        getZ(),
                        5,
                        0.3,
                        0.4,
                        0.3,
                        0.0
                );

                setBehavior(GemBehavior.STAY);
            }

            return InteractionResult.SUCCESS;
        }

        // Owned by this player then normal interaction
        UUID ownerUUID = getOwnerUUID();

        if (ownerUUID != null && ownerUUID.equals(player.getUUID())) {
            return handleOwnedInteraction(player, hand);
        }

        // Owned by somebody/something else then do nothing
        return InteractionResult.PASS;
    }

    private InteractionResult handleOwnedInteraction(Player player, InteractionHand hand) {

        // FUSION ITEM
        if (player.getItemInHand(hand).is(ModItems.FUSE.get())) {
            if (player.isShiftKeyDown() && !getFusionComponents().isEmpty()) {
                if (!level().isClientSide() && level() instanceof ServerLevel serverLevel) {
                    unfuse(serverLevel);
                }
                return InteractionResult.SUCCESS;
            }

            if (getFusionComponents().isEmpty() && player.getItemInHand(hand).getItem() instanceof FuseItem fuseItem) {
                return fuseItem.tryAddGem(player.getItemInHand(hand), player, this);
            }
        }

        if(customizeGemOutfitAndInsignia(player, hand)){
            return InteractionResult.SUCCESS;
        }

        if (player.isShiftKeyDown()) {
            if (!level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                GemBehavior behavior = cycleBehavior();

                Component message = Component.literal(getCurrentName())
                        .withStyle(style -> style.withColor(getGemColor()).withoutShadow())
                        .append(Component.literal(" will "));

                switch (behavior) {
                    case FOLLOW -> message = message.copy()
                            .append(Component.literal("follow you."));
                    case STAY -> message = message.copy()
                            .append(Component.literal("stay here."));
                    case WANDER -> message = message.copy()
                            .append(Component.literal("wander."));
                    case WORK -> message = message.copy()
                            .append(Component.literal(getWorkMessage()));
                }

                serverPlayer.sendOverlayMessage(message);
            }

            return InteractionResult.SUCCESS;
        }else if(!level().isClientSide() && player instanceof ServerPlayer serverPlayer){
            serverPlayer.openMenu(this, buffer -> buffer.writeInt(this.getId()));
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    private boolean customizeGemOutfitAndInsignia(Player player, InteractionHand hand){
        ItemStack usedItem = player.getItemInHand(hand);
        boolean usedColors = false;

        if(usedItem.is(ItemTags.DYES)){
            usedColors = true;
            int color = usedItem.get(DataComponents.DYE).getTextureDiffuseColor();
            if(!level().isClientSide()){
                if(player.isShiftKeyDown()){
                    color = ColorUtil.mixColorInts(getInsigniaColor(), color, 0.2f);
                    setInsigniaColor(color);
                }else{
                    color = ColorUtil.mixColorInts(getOutfitColor(), color, 0.2f);
                    setOutfitColor(color);
                }
                if(!player.isCreative() && random.nextInt(4) == 0){
                    usedItem.shrink(1);
                }
            }
        }
        if(usedItem.is(ItemTags.WOOL_CARPETS)){
            usedColors = true;

            if(usedItem.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof WoolCarpetBlock woolCarpetBlock){
                int color = woolCarpetBlock.getColor().getTextureDiffuseColor();
                if(!level().isClientSide()){
                    if(player.isShiftKeyDown()){
                        setInsigniaColor(color);
                    }else{
                        setOutfitColor(color);
                    }
                    if(!player.isCreative()){
                        usedItem.shrink(1);
                    }
                }
            }
        }
        return usedColors;
    }

    public int getGemColor(){
        return entityData.get(GEM_COLOR);
    }

    void setOutfitColor(int color){
        entityData.set(OUTFIT_COLOR, color);
    }

    void setInsigniaColor(int color){
        entityData.set(INSIGNIA_COLOR, color);
    }

    int getOutfitColor(){
        return entityData.get(OUTFIT_COLOR);
    }

    int getInsigniaColor(){
        return entityData.get(INSIGNIA_COLOR);
    }

    public int getOutfit(){
        return entityData.get(OUTFIT);
    }

    public int getInsignia(){
        return entityData.get(INSIGNIA);
    }

    public int getVisor(){
        return entityData.get(VISOR);
    }

    public int getHairstyle(){
        return entityData.get(HAIRSTYLE);
    }

    public void setOutfit(int outfit){
        entityData.set(OUTFIT, outfit);
    }
    public void setInsignia(int insignia){
        entityData.set(INSIGNIA, insignia);
    }
    public void setVisor(int visor){
        entityData.set(VISOR, visor);
    }
    public void setHairstyle(int hairstyle){
        entityData.set(HAIRSTYLE, hairstyle);
    }

    public abstract int getMaxOutfits();
    public abstract int getMaxInsignias();
    public abstract int getMaxHairstyles();
    public abstract int getMaxMarkings();
    public abstract int getMaxVisors();

    public abstract Color generateGemColor();
    public abstract Color generateOutfitColor();
    public abstract Color generateInsigniaColor();
    public abstract Color generateHairColor();
    public abstract Color generateMarkingsColor();
    public abstract Color generateVisorColor();

    protected abstract int getInventorySize();

    @Override
    public int getContainerSize() {
        if (getFusionComponents().isEmpty()) {
            return getInventorySize();
        }
        int totalSize = 0;
        for (ItemStack component : getFusionComponents()) {
            GemSaveData saveData = component.get(ModDataComponents.GEM_SAVE_DATA);
            if (saveData != null) {
                totalSize += saveData.gemStateData().inventory().size();
            }
        }
        return totalSize;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(inventory, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return !isRemoved() && player.distanceToSqr(this) <= 64.0D;
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new GemMenu(containerId, inventory, this);
    }

    public abstract String getGemTypeName();
    public String getNameWithoutVariant(){
        return getGemTypeName();
    }

    public String getCurrentName(){
        return getCustomName().getString();
    }

    public boolean isCustomisable(){
        return false;
    }

    public Set<GemAbility> getAbilities() {
        return Collections.unmodifiableSet(abilities);
    }

    public boolean hasAbility(GemAbility ability) {
        return abilities.contains(ability);
    }

    public void addAbility(GemAbility ability) {
        if (abilities.add(ability)) {
            syncAbilities();
        }
    }

    public void removeAbility(GemAbility ability) {
        if (abilities.remove(ability)) {
            syncAbilities();
        }
    }

    private void syncAbilities() {
        entityData.set(ABILITIES, abilities.stream().map(GemAbility::getId).toList());
    }

    protected abstract void initializeAbilities();

    public float getModelSize(){
        if(getFusionComponents().isEmpty()){
            return getBaseModelSize() * getQualityScale();
        }
        else {
            return getFusionComponents().size() / 1.5f * getQualityScale() * getBaseModelSize();
        }
    }

    public float getBaseModelSize(){
        return 1f;
    }

    public float getQualityScale(){
        int quality = entityData.get(QUALITY);
        return quality == 0 ? 0.8f : quality == 2 ? 1.15f : 1f;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions base = super.getDefaultDimensions(pose);
        if(getFusionComponents().isEmpty()){
            return base.scale(getQualityScale());
        }
        else {
            return base.scale(getFusionComponents().size() / 1.5f * getQualityScale());
        }
    }

    //OWNERSHIP

    public boolean isOwned() {
        return !entityData.get(OWNER_UUID).isEmpty();
    }

    public void setOwner(Entity owner) {
        entityData.set(OWNER_UUID, owner.getUUID().toString());
    }

    public void clearOwner() {
        entityData.set(OWNER_UUID, "");
    }

    @Nullable
    public UUID getOwnerUUID() {
        String uuid = entityData.get(OWNER_UUID);

        if (uuid.isEmpty()) {
            return null;
        }

        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    public Entity getOwnerEntity() {
        UUID ownerUUID = getOwnerUUID();

        if (ownerUUID == null || level() == null) {
            return null;
        }

        return level().getEntity(ownerUUID);
    }

    //BASIC AI GOALS ?

    public GemBehavior getBehavior() {
        return GemBehavior.values()[entityData.get(BEHAVIOR)];
    }

    public void setBehavior(GemBehavior behavior) {
        entityData.set(BEHAVIOR, behavior.ordinal());
    }

    public GemBehavior cycleBehavior() {
        GemBehavior[] behaviors = GemBehavior.values();

        int currentIndex = entityData.get(BEHAVIOR);
        int nextIndex = (currentIndex + 1) % behaviors.length;

        GemBehavior nextBehavior = behaviors[nextIndex];

        if (nextBehavior == GemBehavior.WORK && !canWork()) {
            nextIndex = (nextIndex + 1) % behaviors.length;
            nextBehavior = behaviors[nextIndex];
        }

        setBehavior(nextBehavior);

        if (nextBehavior == GemBehavior.WORK) {
            setWorkPos(blockPosition());
        }

        return nextBehavior;
    }

    protected boolean canWork() {
        return false;
    }

    protected String getWorkMessage(){
        return "";
    }

    @Nullable
    public BlockPos getWorkPos() {
        return workPos;
    }

    public void setWorkPos(BlockPos workPos) {
        this.workPos = workPos;
    }

    public void clearWorkPos() {
        this.workPos = null;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.targetSelector.addGoal(2, new GemTargetGoal(this, 16.0D));
        this.goalSelector.addGoal(3, new GemFuseGoal(this, 1.1D));

        this.goalSelector.addGoal(4, new GemStayGoal(this));
        this.goalSelector.addGoal(6, new GemFollowGoal(this, 1.1D, 7.0F, 5.5F));

        this.goalSelector.addGoal(10, new GemWanderGoal(this, 1.0D));
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        return hurtGemServer(level, source, amount);
    }

    protected boolean hurtGemServer(ServerLevel level, DamageSource source, float amount) {
        boolean hurt = super.hurtServer(level, source, amount);

        if (hurt && source.getEntity() instanceof LivingEntity attacker) {
            GemCombatUtil.setTargetIfAllowed(this, attacker);

            UUID ownerUUID = getOwnerUUID();

            if (ownerUUID != null) {
                GemCombatUtil.alertAlliedGems(this, attacker, ownerUUID);
            }
        }

        return hurt;
    }

    @Override
    public long getPersistentAngerEndTime() {
        return persistentAngerEndTime;
    }

    @Override
    public void setPersistentAngerEndTime(long time) {
        this.persistentAngerEndTime = time;
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        setTimeToRemainAngry(20L * 60L * 5L);
    }

    //TODO shapeshifting
    public boolean isShapeshifted() {
        return !entityData.get(SHAPESHIFT).isEmpty();
    }

    public String getShapeshift() {
        return entityData.get(SHAPESHIFT);
    }

    public void setShapeshift(String shapeshift) {
        entityData.set(SHAPESHIFT, shapeshift);
    }

    public void clearShapeshift() {
        entityData.set(SHAPESHIFT, "");
    }

    //FUSION
    public List<ItemStack> getFusionComponents() {
        return entityData.get(FUSION_COMPONENTS);
    }

    public void setFusionComponents(List<ItemStack> fusionComponents) {
        List<ItemStack> copies = new ArrayList<>();

        for (ItemStack stack : fusionComponents) {
            copies.add(stack.copy());
        }

        entityData.set(FUSION_COMPONENTS, copies);
    }

    private Color mixComponentsGemColors(){
        int gemColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(gemColor == 0){
                gemColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().color();
            }else{
                gemColor = ColorUtil.mixColorInts(gemColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().color(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(gemColor);
    }

    private Color mixComponentsOutfitColors(){
        int outfitColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(outfitColor == 0){
                outfitColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().outfitColor();
            }else{
                outfitColor = ColorUtil.mixColorInts(outfitColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().outfitColor(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(outfitColor);
    }

    private Color mixComponentsInsigniaColors(){
        int insigniaColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(insigniaColor == 0){
                insigniaColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().insigniaColor();
            }else{
                insigniaColor = ColorUtil.mixColorInts(insigniaColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().insigniaColor(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(insigniaColor);
    }

    private Color mixComponentsHairColors(){
        int hairColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(hairColor == 0){
                hairColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().hairColor();
            }else{
                hairColor = ColorUtil.mixColorInts(hairColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().hairColor(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(hairColor);
    }

    private Color mixComponentsMarkingsColors(){
        int markingsColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(markingsColor == 0){
                markingsColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().markingsColor();
            }else{
                markingsColor = ColorUtil.mixColorInts(markingsColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().markingsColor(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(markingsColor);
    }

    private Color mixComponentsVisorColors(){
        int visorColor = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            if(visorColor == 0){
                visorColor = fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().visorColor();
            }else{
                visorColor = ColorUtil.mixColorInts(visorColor, fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemAppearanceData().visorColor(), 0.5f);
            }
        }
        return ColorUtil.colorFromInt(visorColor);
    }

    private int getFusionQuality(){
        int quality = 0;
        for(ItemStack fusionComponent : getFusionComponents()){
            quality += fusionComponent.get(ModDataComponents.GEM_SAVE_DATA).gemStateData().quality();
        }
        return quality / getFusionComponents().size();
    }

    private double calculateFusionMaxHealth() {
        double total = 0.0D;

        for (ItemStack component : getFusionComponents()) {
            GemSaveData saveData =
                    component.get(ModDataComponents.GEM_SAVE_DATA);

            if (saveData == null) {
                continue;
            }

            int quality = saveData.gemStateData().quality();

            total += getBaseMaxHealth()
                    * getQualityHealthMultiplier(quality);
        }

        return total;
    }

    private double calculateFusionAttackDamage() {
        double total = 0.0D;

        for (ItemStack component : getFusionComponents()) {
            GemSaveData saveData =
                    component.get(ModDataComponents.GEM_SAVE_DATA);

            if (saveData == null) {
                continue;
            }

            int quality = saveData.gemStateData().quality();

            total += getBaseAttackDamage()
                    * getQualityDamageMultiplier(quality)
                    * 0.5D;
        }

        return total;
    }

    private double calculateFusionMovementSpeed() {
        return getBaseMovementSpeed();
    }

    private double calculateFusionAttackSpeed() {
        return getBaseAttackSpeed();
    }

    private void applyFusionStats() {
        if (getFusionComponents().isEmpty()) {
            return;
        }

        getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateFusionMaxHealth());

        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(calculateFusionAttackDamage());

        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(calculateFusionMovementSpeed());

        getAttribute(Attributes.ATTACK_SPEED).setBaseValue(calculateFusionAttackSpeed());
    }

    public boolean isFusing() {
        return entityData.get(FUSING);
    }

    public void setFusing(boolean fusing) {
        entityData.set(FUSING, fusing);
    }

    public boolean completeSameGemFusion(ServerLevel serverLevel, UUID fusionId) {
        FusionRegistryData registry = FusionRegistryData.get(serverLevel);

        // Claim the transformation so only one participant can execute it.
        if (!registry.beginFusion(fusionId)) {
            return false;
        }

        FusionData session = registry.getSession(fusionId).orElse(null);

        if (session == null || session.participants().size() < 2) {
            return false;
        }

        List<GemEntity> participants = new ArrayList<>();

        for (UUID uuid : session.participants()) {
            Entity entity = serverLevel.getEntity(uuid);

            if (!(entity instanceof GemEntity participant)
                    || !participant.isAlive()
                    || participant.getClass() != getClass()) {
                // We have claimed the session, so don't leave it stuck in FUSING.
                registry.removeSession(fusionId);
                for (UUID participantUUID : session.participants()) {
                    Entity remaining = serverLevel.getEntity(participantUUID);
                    if (remaining instanceof GemEntity gem) {
                        gem.setFusing(false);
                    }
                }
                return false;
            }

            participants.add(participant);
        }

        List<ItemStack> components = new ArrayList<>();

        double x = 0.0D;
        double y = 0.0D;
        double z = 0.0D;

        for (GemEntity participant : participants) {
            ItemStack component = new ItemStack(participant.getGemItem());
            component.set(ModDataComponents.GEM_SAVE_DATA, participant.toSaveData());
            component.set(ModDataComponents.REFORM_TIME, participant.getReformTime());
            component.set(ModDataComponents.REFORM_PROGRESS, GemEntity.MAX_REFORM_PROGRESS);

            components.add(component);

            x += participant.getX();
            y += participant.getY();
            z += participant.getZ();
        }

        x /= participants.size();
        y /= participants.size();
        z /= participants.size();

        GemEntity fusedGem = (GemEntity) getType().create(serverLevel, EntitySpawnReason.TRIGGERED);

        if (fusedGem == null) {
            registry.removeSession(fusionId);
            for (GemEntity participant : participants) {
                participant.setFusing(false);
            }
            return false;
        }

        fusedGem.setPos(x, y, z);
        fusedGem.setFusionComponents(components);
        fusedGem.initializeSameGemFusion();

        if (!serverLevel.addFreshEntity(fusedGem)) {
            registry.removeSession(fusionId);
            for (GemEntity participant : participants) {
                participant.setFusing(false);
            }
            return false;
        }

        // remove originals only after the fused entity has spawned successfully
        registry.removeSession(fusionId);

        for (GemEntity participant : participants) {
            participant.setFusing(false);
            participant.discard();
        }

        return true;
    }
}
