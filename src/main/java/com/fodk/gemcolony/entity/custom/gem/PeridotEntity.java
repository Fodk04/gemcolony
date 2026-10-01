package com.fodk.gemcolony.entity.custom.gem;

import com.fodk.gemcolony.data.GemAnalysisResult;
import com.fodk.gemcolony.data.GemConditionsRegistry;
import com.fodk.gemcolony.entity.custom.gem.base.GemConditions;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.savedata.PeridotAnalysisData;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.custom.gem.ai.PeridotAnalysisGoal;
import com.fodk.gemcolony.item.ModItems;
import com.fodk.gemcolony.networking.packet.AnalysisResultsPacketS2C;
import com.fodk.gemcolony.util.ColorUtil;
import com.fodk.gemcolony.util.GemEnvironmentUtil;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PeridotEntity extends GemEntity {

    private static final Color darkSkin = new Color(88, 172, 57);
    private static final Color lightSkin = new Color(90, 255, 117);
    private static final Color darkOutfit = new Color(20, 42, 39);
    private static final Color lightOutfit = new Color(22, 156, 87);
    private static final Color darkInsignia = new Color(165, 150, 79);
    private static final Color lightInsignia = new Color(251, 233, 147);
    private static final Color darkHair = new Color(60, 91, 3);
    private static final Color lightHair = new Color(234, 254, 159);
    private static final Color darkVisor = new Color(198, 198, 106);
    private static final Color lightVisor = new Color(255, 255, 168);

    private static final EntityDataAccessor<Boolean> ANALYSING = SynchedEntityData.defineId(PeridotEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ANALYSIS_TICKS = SynchedEntityData.defineId(PeridotEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANALYSIS_DURATION = SynchedEntityData.defineId(PeridotEntity.class, EntityDataSerializers.INT);

    private static final int BASE_ANALYSIS_DURATION = 200;
    private static final int ANALYSIS_RADIUS = 7;

    private BlockPos analysisCenter;
    private float analysisTemperatureTotal = 0.0f;
    private float analysisHumidityTotal = 0.0f;
    private int analysisSamples = 0;
    private List<GemAnalysisResult> analysisResults = new ArrayList<>();

    public boolean isAnalysing() {
        return entityData.get(ANALYSING);
    }

    public int getAnalysisTicks() {
        return entityData.get(ANALYSIS_TICKS);
    }

    public int getAnalysisDuration() {
        return entityData.get(ANALYSIS_DURATION);
    }

    public BlockPos getAnalysisCenter() {
        return analysisCenter;
    }

    public int getAnalysisRadius() {
        return ANALYSIS_RADIUS;
    }

    public float getAnalysisProgress() {
        int duration = getAnalysisDuration();

        if (duration <= 0) {
            return 0.0f;
        }

        return (float) getAnalysisTicks() / duration;
    }

    public List<GemAnalysisResult> getAnalysisResults() {
        return Collections.unmodifiableList(analysisResults);
    }

    public void setAnalysisResults(List<GemAnalysisResult> results) {
        this.analysisResults = new ArrayList<>(results);
    }

    public boolean hasAnalysisResults() {
        return !analysisResults.isEmpty();
    }

    private int calculateAnalysisDuration() {
        return switch (entityData.get(QUALITY)) {
            case 0 -> BASE_ANALYSIS_DURATION * 3 / 2;
            case 1 -> BASE_ANALYSIS_DURATION;
            case 2 -> BASE_ANALYSIS_DURATION * 2 / 3;
            default -> BASE_ANALYSIS_DURATION;
        };
    }

    public PeridotEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ATTACK_SPEED, 1.4D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(ANALYSING, false);
        entityData.define(ANALYSIS_TICKS, 0);
        entityData.define(ANALYSIS_DURATION, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new PeridotAnalysisGoal(this, 1));
    }

    @Override
    protected void generateAppearance(Color gemColor, int maxOutfits, Color outfitColor, int maxInsignias, Color insigniaColor, int maxHairstyles, Color hairColor, int maxVisors, Color visorColor) {
        gemColor = ColorUtil.lerpColor(lightSkin, darkSkin, random.nextFloat());
        outfitColor = ColorUtil.lerpColor(lightOutfit, darkOutfit, random.nextFloat());
        insigniaColor = ColorUtil.lerpColor(lightInsignia, darkInsignia, random.nextFloat());
        hairColor = ColorUtil.lerpColor(lightHair, darkHair, random.nextFloat());
        visorColor = ColorUtil.lerpColor(lightVisor, darkVisor, random.nextFloat());
        super.generateAppearance(gemColor, getMaxOutfits(), outfitColor,
                getMaxInsignias(), insigniaColor,
                getMaxHairstyles(), hairColor,
                getMaxVisors(), visorColor);
    }

    @Override
    public Item getGemItem() {
        return ModItems.PERIDOT_GEM.get();
    }

    @Override
    public int getMaxOutfits() {
        return 3;
    }

    @Override
    public int getMaxInsignias() {
        return 4;
    }

    @Override
    public int getMaxHairstyles() {
        return 4;
    }

    @Override
    public int getMaxVisors() {
        return 2;
    }

    @Override
    protected boolean alwaysHasVisor() {
        return true;
    }

    @Override
    protected int getInventorySize() {
        float qualityModifier = entityData.get(QUALITY) == 0 ? 0.5f : entityData.get(QUALITY) == 2 ? 1.5f : 1f;
        return (int) (30 * qualityModifier);
    }

    //3 MIN
    @Override
    protected int getReformTime(){
        float modifier = entityData.get(QUALITY) == 0 ? 0.9f : entityData.get(QUALITY) == 1 ? 1f : 1.1f;
        return (int)(3600f * modifier);
    }

    @Override
    public String getGemTypeName() {
        return "Peridot";
    }

    @Override
    protected void initializeAbilities() {
        addAbility(GemAbility.KINDERGARTNER);
        addAbility(GemAbility.FERROKINESIS);
    }

    public void startAnalysis() {
        if (isAnalysing()) {
            return;
        }

        resetAnalysisSamples();
        analysisResults.clear();

        entityData.set(ANALYSING, true);

        entityData.set(ANALYSIS_TICKS, 0);
        entityData.set(ANALYSIS_DURATION, calculateAnalysisDuration());
        analysisCenter = blockPosition();
    }

    private void finishAnalysis() {
        analysisResults = getTopThreeGemResults();

        for (ServerPlayer player : ((ServerLevel) level()).players()) {
            PacketDistributor.sendToPlayer(player, new AnalysisResultsPacketS2C(getId(), analysisResults));
        }

        entityData.set(ANALYSING, false);

        entityData.set(ANALYSIS_TICKS, 0);
        entityData.set(ANALYSIS_DURATION, 0);
        analysisCenter = null;
    }

    private void resetAnalysisSamples() {
        analysisTemperatureTotal = 0.0f;
        analysisHumidityTotal = 0.0f;
        analysisSamples = 0;
    }

    public void sampleAnalysisPosition(BlockPos pos) {
        analysisTemperatureTotal += GemEnvironmentUtil.getTemperature(level(), pos);
        analysisHumidityTotal += GemEnvironmentUtil.getHumidity(level(), pos);
        analysisSamples++;
    }


    private float getAverageAnalysisTemperature() {
        if (analysisSamples == 0) {
            return 0.0f;
        }

        return analysisTemperatureTotal / analysisSamples;
    }

    private float getAverageAnalysisHumidity() {
        if (analysisSamples == 0) {
            return 0.0f;
        }

        return analysisHumidityTotal / analysisSamples;
    }

    private List<GemAnalysisResult> calculateGemScores() {
        float temperature = getAverageAnalysisTemperature();
        float humidity = getAverageAnalysisHumidity();

        List<GemAnalysisResult> results = new ArrayList<GemAnalysisResult>();

        for (GemConditions conditions : GemConditionsRegistry.ALL) {
            float score = GemEnvironmentUtil.getGemScore(temperature, humidity, conditions);

            results.add(new GemAnalysisResult(conditions.gemId(), score));
        }

        return results;
    }

    private List<GemAnalysisResult> getTopThreeGemResults() {
        List<GemAnalysisResult> results = calculateGemScores();

        float totalScore = 0.0f;

        for (GemAnalysisResult result : results) {
            totalScore += result.score();
        }

        if (totalScore <= 0.0f) {
            return results.subList(0, Math.min(3, results.size()));
        }

        List<GemAnalysisResult> normalizedResults = new ArrayList<>();

        for (GemAnalysisResult result : results) {
            float percentage = result.score() / totalScore * 100.0f;

            normalizedResults.add(new GemAnalysisResult(result.gemId(), percentage));
        }

        normalizedResults.sort(Comparator.comparingDouble(GemAnalysisResult::score).reversed());

        return normalizedResults.subList(0, Math.min(3, normalizedResults.size()));
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide() && isAnalysing()) {
            entityData.set(ANALYSIS_TICKS, entityData.get(ANALYSIS_TICKS) + 1);

            if (entityData.get(ANALYSIS_TICKS) >= entityData.get(ANALYSIS_DURATION)) {
                finishAnalysis();
            }
        }
    }


    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        PeridotAnalysisData analysisData = new PeridotAnalysisData(
                isAnalysing(),
                entityData.get(ANALYSIS_TICKS),
                entityData.get(ANALYSIS_DURATION),
                analysisCenter != null ? analysisCenter.getX() : 0,
                analysisCenter != null ? analysisCenter.getY() : 0,
                analysisCenter != null ? analysisCenter.getZ() : 0,
                analysisTemperatureTotal,
                analysisHumidityTotal,
                analysisSamples,
                analysisResults
        );

        output.store("PeridotAnalysisData", PeridotAnalysisData.CODEC, analysisData);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        input.read("PeridotAnalysisData", PeridotAnalysisData.CODEC).ifPresent(this::applyAnalysisData);
    }

    private void applyAnalysisData(PeridotAnalysisData data) {
        if (data.analysing()) {
            entityData.set(ANALYSING, true);
        } else {
            entityData.set(ANALYSING, false);
        }

        entityData.set(ANALYSIS_TICKS, data.analysisTicks());
        entityData.set(ANALYSIS_DURATION, data.analysisDuration());

        analysisCenter = data.analysing()
                ? new BlockPos(data.centerX(), data.centerY(), data.centerZ())
                : null;

        analysisTemperatureTotal = data.temperatureTotal();
        analysisHumidityTotal = data.humidityTotal();
        analysisSamples = data.samples();
        analysisResults = new ArrayList<>(data.results());
    }

    private static final RawAnimation SAMPLE_ANIMATION = RawAnimation.begin().thenPlay("misc.sample");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);

        controllers.add(new AnimationController<>("sampling_controller", 0,
                state -> PlayState.STOP).triggerableAnim("sample", SAMPLE_ANIMATION));
    }

    @Override
    public float getModelSize() {
        return 0.9f;
    }
}
