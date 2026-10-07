package com.fodk.gemcolony.entity.client.renderer;

import com.fodk.gemcolony.entity.client.layer.*;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.custom.gem.ability.ShapeshiftForm;
import com.fodk.gemcolony.entity.custom.gem.base.GemEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public abstract class GemRenderer<T extends GemEntity, R extends GemRenderState> extends GeoEntityRenderer<T, R> {

    private final Map<ShapeshiftForm, EntityModel<?>> shapeshiftModels = new EnumMap<>(ShapeshiftForm.class);

    public GemRenderer(EntityRendererProvider.Context context, GeoModel<T> model, String name) {
        super(context, model);
        for (ShapeshiftForm form : ShapeshiftForm.values()) {
            shapeshiftModels.put(form, form.createModel(context.getModelSet()));
        }
        withRenderLayer(new GemBodyLayer<>(this, name, 1));
        withRenderLayer(new GemMarkingsLayer<>(this, name, 2));
        withRenderLayer(new GemEyesLayer<>(this, name, 3));
        withRenderLayer(new GemEyesWhitesLayer<>(this, name, 4));
        withRenderLayer(new GemOutfitLayer<>(this, name, 5));
        withRenderLayer(new GemInsigniaLayer<>(this, name, 6));
        withRenderLayer(new GemHairLayer<>(this, name, 7));
        withRenderLayer(new GemVisorLayer<>(this, name, 8));
    }

    @Override
    public void extractRenderState(T entity, R state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        GemRenderState gemState = (GemRenderState) state;
        SynchedEntityData synchedEntityData = entity.getEntityData();

        gemState.gemColor = synchedEntityData.get(GemEntity.GEM_COLOR);
        gemState.outfit = synchedEntityData.get(GemEntity.OUTFIT);
        gemState.outfitColor = synchedEntityData.get(GemEntity.OUTFIT_COLOR);
        gemState.insignia = synchedEntityData.get(GemEntity.INSIGNIA);
        gemState.insigniaColor = synchedEntityData.get(GemEntity.INSIGNIA_COLOR);
        gemState.hairstyle = synchedEntityData.get(GemEntity.HAIRSTYLE);
        gemState.hairColor = synchedEntityData.get(GemEntity.HAIR_COLOR);
        gemState.gemPlacement = synchedEntityData.get(GemEntity.GEM_PLACEMENT);
        gemState.visor = synchedEntityData.get(GemEntity.VISOR);
        gemState.visorColor = synchedEntityData.get(GemEntity.VISOR_COLOR);
        gemState.markings = synchedEntityData.get(GemEntity.MARKINGS);
        gemState.markingsColor = synchedEntityData.get(GemEntity.MARKINGS_COLOR);
        gemState.reformProgress = GemEntity.getReformProgressPercentage(entity.getEntityData().get(GemEntity.REFORM_PROGRESS));
        gemState.reformCenter = entity.getReformCenter();
        gemState.qualityModifier = synchedEntityData.get(GemEntity.QUALITY) == 0 ? 0.8f : synchedEntityData.get(GemEntity.QUALITY) == 2 ? 1.2f : 1f;
        gemState.modelSize = entity.getModelSize();
        gemState.shapeshift = synchedEntityData.get(GemEntity.SHAPESHIFT);
    }

    @Override
    public R createRenderState(T animatable, @Nullable Void relatedObject) {
        return (R) new GemRenderState();
    }

    @Override
    public void adjustRenderPose(RenderPassInfo<R> renderPassInfo) {
        super.adjustRenderPose(renderPassInfo);

        GemRenderState state = (GemRenderState) renderPassInfo.renderState();
        PoseStack poseStack = renderPassInfo.poseStack();

        float pivotY = state.reformCenter;

        poseStack.translate(0, pivotY, 0);

        poseStack.mulPose(Axis.XP.rotationDegrees(state.previewPitch));

        poseStack.translate(0, -pivotY, 0);
    }

    private Identifier getShapeshiftTexture(ShapeshiftForm form) {
        return switch (form) {
            case COW -> Identifier.withDefaultNamespace("textures/entity/cow/cow.png");
            case ZOMBIE -> Identifier.withDefaultNamespace("textures/entity/zombie/zombie.png");
        };
    }
}