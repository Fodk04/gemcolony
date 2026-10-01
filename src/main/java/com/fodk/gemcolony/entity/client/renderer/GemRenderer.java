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
        withRenderLayer(new GemEyesLayer<>(this, name, 2));
        withRenderLayer(new GemEyesWhitesLayer<>(this, name, 3));
        withRenderLayer(new GemOutfitLayer<>(this, name, 4));
        withRenderLayer(new GemInsigniaLayer<>(this, name, 5));
        withRenderLayer(new GemHairLayer<>(this, name, 6));
        withRenderLayer(new GemVisorLayer<>(this, name, 7));
    }

    @Override
    public void extractRenderState(T entity, R state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        GemRenderState gemState = (GemRenderState) state;
        gemState.gemColor = entity.getEntityData().get(GemEntity.GEM_COLOR);
        gemState.outfit = entity.getEntityData().get(GemEntity.OUTFIT);
        gemState.outfitColor = entity.getEntityData().get(GemEntity.OUTFIT_COLOR);
        gemState.insignia = entity.getEntityData().get(GemEntity.INSIGNIA);
        gemState.insigniaColor = entity.getEntityData().get(GemEntity.INSIGNIA_COLOR);
        gemState.hairstyle = entity.getEntityData().get(GemEntity.HAIRSTYLE);
        gemState.hairColor = entity.getEntityData().get(GemEntity.HAIR_COLOR);
        gemState.gemPlacement = entity.getEntityData().get(GemEntity.GEM_PLACEMENT);
        gemState.visor = entity.getEntityData().get(GemEntity.VISOR);
        gemState.visorColor = entity.getEntityData().get(GemEntity.VISOR_COLOR);
        gemState.reformProgress = GemEntity.getReformProgressPercentage(entity.getEntityData().get(GemEntity.REFORM_PROGRESS));
        gemState.reformCenter = entity.getReformCenter();
        gemState.qualityModifier = entity.getEntityData().get(GemEntity.QUALITY) == 0 ? 0.8f : entity.getEntityData().get(GemEntity.QUALITY) == 2 ? 1.1f : 1f;
        gemState.modelSize = entity.getModelSize();
        gemState.shapeshift = entity.getEntityData().get(GemEntity.SHAPESHIFT);
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