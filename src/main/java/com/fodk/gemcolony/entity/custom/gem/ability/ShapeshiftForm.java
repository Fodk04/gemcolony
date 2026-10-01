package com.fodk.gemcolony.entity.custom.gem.ability;

import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;

import java.util.function.Function;

public enum ShapeshiftForm {

    COW(
            "cow",
            EntityTypes.COW,
            ModelLayers.COW,
            modelSet -> new CowModel(modelSet.bakeLayer(ModelLayers.COW)),
            gemState -> new LivingEntityRenderState()
    ) {
        @Override
        public void submit(EntityModel<?> model, GemRenderState gemState, PoseStack poseStack, SubmitNodeCollector renderTasks, Identifier texture) {
            LivingEntityRenderState state = new LivingEntityRenderState();

            renderTasks.submitModel(
                    (EntityModel<LivingEntityRenderState>) model,
                    state,
                    poseStack,
                    texture,
                    15728880,
                    OverlayTexture.NO_OVERLAY,
                    0,
                    null
            );
        }
    },

    ZOMBIE(
            "zombie",
            EntityTypes.ZOMBIE,
            ModelLayers.ZOMBIE,
            modelSet -> new ZombieModel<>(modelSet.bakeLayer(ModelLayers.ZOMBIE)),
            gemState -> new ZombieRenderState()
    ) {
        @Override
        public void submit(EntityModel<?> model, GemRenderState gemState, PoseStack poseStack, SubmitNodeCollector renderTasks, Identifier texture) {
            ZombieRenderState state = new ZombieRenderState();

            renderTasks.submitModel(
                    (EntityModel<ZombieRenderState>) model,
                    state,
                    poseStack,
                    texture,
                    15728880,
                    OverlayTexture.NO_OVERLAY,
                    0,
                    null
            );
        }
    };

    private final String id;
    private final EntityType<?> entityType;
    private final ModelLayerLocation modelLayer;
    private final Function<EntityModelSet, EntityModel<?>> modelFactory;
    private final Function<GemRenderState, EntityRenderState> renderStateFactory;

    ShapeshiftForm(String id, EntityType<?> entityType, ModelLayerLocation modelLayer, Function<EntityModelSet, EntityModel<?>> modelFactory, Function<GemRenderState, EntityRenderState> renderStateFactory) {
        this.id = id;
        this.entityType = entityType;
        this.modelLayer = modelLayer;
        this.modelFactory = modelFactory;
        this.renderStateFactory = renderStateFactory;
    }

    public String getId() {
        return id;
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public ModelLayerLocation getModelLayer() {
        return modelLayer;
    }

    public static ShapeshiftForm fromId(String id) {
        for (ShapeshiftForm form : values()) {
            if (form.id.equals(id)) {
                return form;
            }
        }

        return null;
    }

    public static boolean isValid(String id) {
        return fromId(id) != null;
    }

    public EntityModel<?> createModel(EntityModelSet modelSet) {
        return modelFactory.apply(modelSet);
    }

    public EntityRenderState createRenderState(GemRenderState gemState) {
        return renderStateFactory.apply(gemState);
    }

    public abstract void submit(EntityModel<?> model, GemRenderState gemState, PoseStack poseStack, SubmitNodeCollector renderTasks, Identifier texture);
}
