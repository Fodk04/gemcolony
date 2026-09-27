package com.fodk.gemcolony.entity.client.screen.ability;

import com.fodk.gemcolony.data.GemAnalysisResult;
import com.fodk.gemcolony.entity.client.screen.GemButton;
import com.fodk.gemcolony.entity.client.screen.GemScreen;
import com.fodk.gemcolony.entity.custom.GemDefinition;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.GemDefinitions;
import com.fodk.gemcolony.entity.custom.gem.PeridotEntity;
import com.fodk.gemcolony.networking.packet.StartAnalysisPacketC2S;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class KindergartnerUI implements GemAbilityUI {

    private GemScreen screen;
    private GemButton gemButton;
    private PeridotEntity peridot;
    private int textColor;

    @Override
    public void init(GemScreen screen, int textColor, int gemColor) {
        this.screen = screen;
        this.peridot = (PeridotEntity) screen.getMenu().getGemEntity();
        this.textColor =textColor;

        int x = screen.getLeftPos() + 105;
        int y = screen.getTopPos() + 80;

        gemButton = new GemButton(
                x,
                y,
                60,
                16,
                Component.literal("Analyse"),
                textColor,
                gemColor,
                () -> {
                    ClientPacketDistributor.sendToServer(new StartAnalysisPacketC2S(screen.getMenu().getGemEntity().getId()));
                }
        );

        screen.addAbilityWidget(gemButton);
    }

    private static final long CYCLE_TIME_MS = 1000;
    private long lastCycleTime = System.currentTimeMillis();
    private int currentCycleIndex = 0;

    @Override
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (peridot == null || gemButton == null) {
            return;
        }

        gemButton.active = !peridot.isAnalysing();

        gemButton.setMessage(peridot.isAnalysing()
                        ? Component.literal("Analysing...")
                        : Component.literal("Analyse")
        );

        if (peridot.isAnalysing()) {
            float progress = peridot.getAnalysisProgress();

            int barX = screen.getLeftPos() + 105;
            int barY = screen.getTopPos() + 100;
            int barWidth = 60;
            int barHeight = 6;

            graphics.fill(
                    barX,
                    barY,
                    barX + barWidth,
                    barY + barHeight,
                    0xFF202020
            );

            graphics.fill(
                    barX,
                    barY,
                    barX + (int) (barWidth * progress),
                    barY + barHeight,
                    0xFF70A000
            );
        }

        if (!peridot.isAnalysing() && peridot.hasAnalysisResults()) {
            int resultX = 150;
            int resultY = 130;

            graphics.pose().pushMatrix();
            graphics.pose().translate(screen.getLeftPos(), screen.getTopPos());
            graphics.pose().scale(0.75f, 0.75f);

            graphics.text(
                    screen.getFont(),
                    Component.literal("Results:"),
                    resultX,
                    resultY,
                    textColor,
                    false
            );
            graphics.pose().popMatrix();


            graphics.pose().pushMatrix();
            graphics.pose().translate(screen.getLeftPos(), screen.getTopPos());
            graphics.pose().scale(0.65f, 0.65f);

            for (int i = 0; i < peridot.getAnalysisResults().size(); i++) {
                GemAnalysisResult result = peridot.getAnalysisResults().get(i);
                GemDefinition currentGemDefinition = GemDefinitions.get(result.gemId());

                graphics.text(
                        screen.getFont(),
                        Component.literal(currentGemDefinition.id() + " " + String.format("%.1f%%", result.score())),
                        resultX + 18,
                        resultY + 12 + i * 10 + 24,
                        textColor,
                        false
                );
            }
            graphics.pose().popMatrix();


            graphics.pose().pushMatrix();
            graphics.pose().translate(screen.getLeftPos(), screen.getTopPos());
            graphics.pose().scale(0.45f, 0.45f);

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastCycleTime >= CYCLE_TIME_MS) {
                currentCycleIndex++;
                if(currentCycleIndex >= 100){
                    currentCycleIndex = 0;
                }
                lastCycleTime = currentTime;
            }

            for (int i = 0; i < peridot.getAnalysisResults().size(); i++) {
                GemAnalysisResult result = peridot.getAnalysisResults().get(i);
                GemDefinition currentGemDefinition = GemDefinitions.get(result.gemId());

                if(currentGemDefinition.items().size() == 1){
                    graphics.item(new ItemStack(currentGemDefinition.items().get(0)), resultX + 64, resultY + i * 16 + 106);
                }else{
                    graphics.item(new ItemStack(currentGemDefinition.items().get(currentCycleIndex % currentGemDefinition.items().size())), resultX + 64, resultY + i * 16 + 106);
                }
            }
            graphics.pose().popMatrix();
        }
    }

    @Override
    public void clear() {
        if (gemButton != null) {
            gemButton.visible = false;
        }

        screen = null;
        peridot = null;
    }
}
