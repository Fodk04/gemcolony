package com.fodk.gemcolony.construction;

import com.fodk.gemcolony.block.entity.savedata.ConstructionSiteData;

public class ConstructionSite {

    private final Assembly assembly;
    private int currentComponent;
    private int currentStage;

    public ConstructionSite(Assembly assembly) {
        this.assembly = assembly;
        this.currentComponent = 0;
        this.currentStage = 0;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public String getAssemblyId() {
        return assembly.id();
    }

    public AssemblyComponent getCurrentComponent() {
        if (isComplete()) {
            return null;
        }

        return assembly.components().get(currentComponent);
    }

    public int getCurrentComponentIndex() {
        return currentComponent;
    }

    public void setCurrentComponent(int component) {
        this.currentComponent = component;
    }

    public void setCurrentStage(int stage) {
        this.currentStage = stage;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void advanceStage() {
        currentStage++;
    }

    public void advanceComponent() {
        if (isCurrentComponentComplete()) {
            currentComponent++;
            currentStage = 0;
        }
    }

    public Blueprint getCurrentBlueprint() {
        if (isComplete()) {
            return null;
        }

        return getCurrentComponent().blueprint();
    }

    public boolean canAdvanceComponent() {
        return isCurrentComponentComplete();
    }

    public boolean isComplete() {
        return currentComponent >= assembly.components().size();
    }

    public boolean isCurrentComponentComplete() {
        if (isComplete()) {
            return false;
        }

        AssemblyComponent component = getCurrentComponent();
        return currentStage >= component.blueprint().constructionStages();
    }

    public ConstructionSiteData toSaveData() {
        return new ConstructionSiteData(
                assembly.id(),
                currentComponent,
                currentStage
        );
    }
}