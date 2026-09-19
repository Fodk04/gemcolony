package com.fodk.gemcolony.entity.custom;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.data.FacetRegistryData;
import com.fodk.gemcolony.data.ModDataComponents;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.menu.GemMenu;
import com.fodk.gemcolony.sound.ModSounds;
import com.fodk.gemcolony.util.ColorUtil;
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
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class GemEntity extends Monster implements GeoEntity, Container, MenuProvider {

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
    public static final EntityDataAccessor<Integer> VISOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> VISOR_COLOR = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> REFORM_PROGRESS = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> EMERGED = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> OWNER_UUID = SynchedEntityData.defineId(GemEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<List<Integer>> ABILITIES = SynchedEntityData.defineId(GemEntity.class, ModEntityDataSerializers.INT_LIST.get());

    private NonNullList<ItemStack> inventory;

    private final Set<GemAbility> abilities = new HashSet<>();

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
        entityData.define(VISOR, -1);
        entityData.define(VISOR_COLOR, 0xFFFFFF);
        entityData.define(REFORM_PROGRESS, maxReformProgress);
        entityData.define(EMERGED, false);
        entityData.define(OWNER_UUID, "");
        entityData.define(ABILITIES, List.of());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (QUALITY.equals(key)) {
            int size = getInventorySize();

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
        if (spawnReason != EntitySpawnReason.SPAWN_ITEM_USE) {
            assignOrigin(level.getLevel(), blockPosition());
            generateAppearance(Color.BLACK, 0, Color.BLACK, 0, Color.BLACK, 0, Color.BLACK, 0, Color.BLACK);
            entityData.set(EMERGED, true);
            //for testing
            entityData.set(QUALITY, random.nextInt(3));
            applyQualityModifiers();
            initializeAbilities();
            entityData.set(REFORM_PROGRESS, 0);
            this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);
        }

        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    public void initializeGem(int reformProgress){
        applyQualityModifiers();
        UpdateDisplayedName();
        entityData.set(REFORM_PROGRESS, reformProgress);
    }

    public void assignOrigin(ServerLevel level, BlockPos pos) {
        int regionX = Math.floorDiv(pos.getX(), 1024);
        int regionZ = Math.floorDiv(pos.getZ(), 1024);

        FacetRegistryData.FacetAssignment assignment =
                FacetRegistryData.get(level).assignGem(level.dimension(), regionX, regionZ);

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

    public void applyQualityModifiers() {
        int quality = entityData.get(QUALITY);
        double healthMultiplier = switch (quality) {
            case 0 -> -0.25D;
            case 2 -> 0.40D;
            default -> 0;
        };
        double damageMultiplier = switch (quality) {
            case 0 -> -0.25D;
            case 2 -> 0.20D;
            default -> 0;
        };

        this.getAttribute(Attributes.MAX_HEALTH).addOrReplacePermanentModifier(
                new AttributeModifier(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_health"), healthMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );
        this.getAttribute(Attributes.ATTACK_DAMAGE).addOrReplacePermanentModifier(
                new AttributeModifier(Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "quality_damage"), damageMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
        );

        this.setHealth(this.getMaxHealth()); // sync current HP to the new max, since changing MAX_HEALTH doesn't auto-heal
    }

    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        this.entityData.set(GEM_COLOR, ColorUtil.colorToInt(gemColor));
        this.entityData.set(OUTFIT, random.nextInt(maxOutfits));
        this.entityData.set(OUTFIT_COLOR, ColorUtil.colorToInt(outfitColor));
        this.entityData.set(INSIGNIA, random.nextInt(maxInsignias));
        this.entityData.set(INSIGNIA_COLOR, ColorUtil.colorToInt(insigniaColor));
        this.entityData.set(HAIRSTYLE, random.nextInt(maxHairstyles));
        this.entityData.set(HAIR_COLOR, ColorUtil.colorToInt(hairColor));
        this.entityData.set(VISOR, random.nextInt(maxVisors));
        this.entityData.set(VISOR_COLOR, ColorUtil.colorToInt(visorColor));
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
                entityData.get(VARIANT), entityData.get(WINGS), entityData.get(MARKINGS),
                entityData.get(VISOR), entityData.get(VISOR_COLOR)
        );
    }

    public GemStateData toSavedState(){
        return new GemStateData(
                entityData.get(REFORM_PROGRESS), entityData.get(QUALITY), entityData.get(EMERGED),
                entityData.get(CRACKED), entityData.get(OWNER_UUID), List.copyOf(inventory), List.copyOf(abilities)
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
        entityData.set(VISOR, data.visor());
        entityData.set(VISOR_COLOR, data.visorColor());
    }

    public void applySavedState(GemStateData data){
        entityData.set(REFORM_PROGRESS, data.reformProgress());
        entityData.set(QUALITY, data.quality());
        entityData.set(EMERGED, data.emerged());
        entityData.set(CRACKED, data.cracked());
        entityData.set(OWNER_UUID, data.ownerUUID());
        this.inventory = NonNullList.withSize(getInventorySize(), ItemStack.EMPTY);

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
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.read("GemData", GemSaveData.CODEC).ifPresent(this::applySaveData);
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

    public static final int maxReformProgress = 140;

    @Override
    public void die(DamageSource source) {
        level().addParticle(ParticleTypes.EXPLOSION_EMITTER, blockPosition().getX(), blockPosition().getY() + 2d, blockPosition().getZ(), 0, 0, 0);
        if(!level().isClientSide()){
            ItemStack gem = new ItemStack(getGemItem());
            gem.set(ModDataComponents.GEM_SAVE_DATA, toSaveData());
            gem.set(ModDataComponents.REFORM_TIME, getReformTime());
            gem.set(ModDataComponents.REFORM_PROGRESS, maxReformProgress);

            playSound(ModSounds.GEM_POOF.get(),
                    2.0F,
                    1.0F - (0.4F * random.nextFloat())
            );

            spawnAtLocation((ServerLevel) this.level(), gem).setUnlimitedLifetime();
        }
        super.die(source);
    }

    public static float getReformProgressPercentage(int reformProgress){
        return 1f - (float)reformProgress/(float)maxReformProgress;
    }

    public abstract float getReformCenter();

    protected int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(200f * modifier);
    }

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
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        if(customizeGemOutfitAndInsignia(player, hand)){
            return InteractionResult.SUCCESS;
        }

        if(!level().isClientSide() && player instanceof ServerPlayer serverPlayer){
            serverPlayer.openMenu(this, buffer -> buffer.writeInt(this.getId()));
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private boolean customizeGemOutfitAndInsignia(Player player, InteractionHand hand){
        ItemStack usedItem = player.getItemInHand(hand);
        boolean usedColors = false;

        if(usedItem.is(ItemTags.DYES)){
            usedColors = usedItem.is(ItemTags.DYES);
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
            usedColors = usedItem.is(ItemTags.WOOL_CARPETS);

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
    public abstract int getMaxVisors();

    protected abstract int getInventorySize();

    @Override
    public int getContainerSize() {
        return inventory.size();
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

    public String getCurrentName(){
        return getCustomName().getString();
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

    protected void initializeAbilities() {
    }
}
