package com.fodk.gemcolony.entity.client.screen;

import com.fodk.gemcolony.GemColony;
import com.fodk.gemcolony.entity.client.renderstate.GemRenderState;
import com.fodk.gemcolony.entity.client.screen.ability.KindergartnerUI;
import com.fodk.gemcolony.entity.custom.GemEntity;
import com.fodk.gemcolony.entity.custom.gem.ability.GemAbility;
import com.fodk.gemcolony.entity.client.screen.ability.GemAbilityUI;
import com.fodk.gemcolony.entity.client.screen.ability.GenericAbilityUI;
import com.fodk.gemcolony.menu.GemMenu;
import com.fodk.gemcolony.util.ColorUtil;
import com.geckolib.constant.DataTickets;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.Items;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class GemScreen extends AbstractContainerScreen<GemMenu> {

    private static final Identifier[] BACKGROUNDS = {
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_1.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_2.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_3.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_4.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_5.png"),
            Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_inventory_6.png")
    };

    private static final Identifier APPEARANCE_BACKGROUND = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_appearance.png");
    private static final Identifier APPEARANCE_MODEL_VIEW = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_appearance_model_view.png");
    private static final Identifier STATS_BACKGROUND = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_appearance.png");
    private static final Identifier ABILITIES_BACKGROUND = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_appearance.png");

    private static final Identifier TAB_SELECTED = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_tab_selected.png");
    private static final Identifier TAB_UNSELECTED = Identifier.fromNamespaceAndPath(GemColony.MOD_ID, "textures/gui/gem_tab_unselected.png");

    private final List<GemButton> gemButtons = new ArrayList<>();

    private GemTabs selectedTab = GemTabs.INVENTORY;
    private GemAbility selectedAbility = null;
    private GemAbilityUI selectedAbilityUI;

    private EditBox nameBox;

    private Button outfitLeft;
    private Button outfitRight;

    private Button insigniaLeft;
    private Button insigniaRight;

    private Button hairstyleLeft;
    private Button hairstyleRight;

    private Button visorLeft;
    private Button visorRight;

    private static final int APPEARANCE_TYPE_Y_OFFSET = 52;
    private static final int APPEARANCE_TYPE_X_OFFSET = 5;
    private static final int APPEARANCE_BUTTONS_Y_OFFSET = 19;
    private static final int APPEARANCE_BUTTONS_X_OFFSET = -14;
    private static final int STAT_LABEL_Y_OFFSET = 4;

    private float modelRotation = 0f;
    private float modelPitch = 0f;
    private boolean draggingModel = false;

    int textColor;
    int gemColor;

    public GemScreen(GemMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, getGuiHeight(menu));
        setInventorySlotsActive(true);
        gemColor = menu.getGemEntity().getGemColor();
        textColor = ColorUtil.getContrastingTextColor(gemColor);
    }

    public int getLeftPos() {
        return this.leftPos;
    }

    public int getTopPos() {
        return this.topPos;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractBackground(graphics, mouseX, mouseY, partialTick);

        //INVENTORY
        if(selectedTab == GemTabs.INVENTORY){
            int gemSlots = menu.getGemEntity().getContainerSize();
            int rows = (gemSlots + 8) / 9;

            Identifier background = BACKGROUNDS[rows - 1];

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    background,
                    this.leftPos,
                    this.topPos,
                    0,
                    0,
                    this.imageWidth,
                    this.imageHeight,
                    256,
                    256,
                    gemColor
            );

            int usedColumns = gemSlots % 9;
            if(usedColumns != 0){
                int unusedX = this.leftPos + 8 + usedColumns * 18;
                int unusedY = this.topPos + 18 + (rows - 1) * 18;
                int unusedWidth = (9 - usedColumns) * 18;

                graphics.fill(
                        unusedX - 1,
                        unusedY - 1,
                        unusedX + unusedWidth,
                        unusedY + 18,
                        ColorUtil.multiplyColors(gemColor, ColorUtil.colorToInt(new Color(198, 198, 198)))
                );
            }
        }
        //APPEARANCE
        if(selectedTab == GemTabs.APPEARANCE){
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    APPEARANCE_BACKGROUND,
                    this.leftPos,
                    this.topPos,
                    0,
                    0,
                    this.imageWidth,
                    this.imageHeight,
                    256,
                    256,
                    gemColor
            );

            int modelViewX = this.leftPos + 86;
            int modelViewY = this.topPos + 18;

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    APPEARANCE_MODEL_VIEW,
                    modelViewX,
                    modelViewY,
                    0,
                    0,
                    84,
                    102,
                    84,
                    102,
                    ColorUtil.colorToInt(new Color(100, 100, 100))
            );

            drawGemModel(graphics, partialTick);

            drawSelector(
                    graphics,
                    "Outfit",
                    menu.getGemEntity().getOutfit(),
                    APPEARANCE_TYPE_Y_OFFSET,
                    textColor
            );

            drawSelector(
                    graphics,
                    "Insignia",
                    menu.getGemEntity().getInsignia(),
                    20 + APPEARANCE_TYPE_Y_OFFSET,
                    textColor
            );

            drawSelector(
                    graphics,
                    "Hairstyle",
                    menu.getGemEntity().getHairstyle(),
                    40 + APPEARANCE_TYPE_Y_OFFSET,
                    textColor
            );

            drawSelector(
                    graphics,
                    "Visor",
                    menu.getGemEntity().getVisor(),
                    60 + APPEARANCE_TYPE_Y_OFFSET,
                    textColor
            );

            drawNameEditBox(graphics);
        }
        //STATS
        if(selectedTab == GemTabs.STATS){
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    STATS_BACKGROUND,
                    this.leftPos,
                    this.topPos,
                    0,
                    0,
                    this.imageWidth,
                    this.imageHeight,
                    256,
                    256,
                    gemColor
            );

            drawStatsIcons(graphics);

            drawStatLabel(graphics, "Quality", 25, textColor);
            drawStatLabel(graphics, "Max Health", 45, textColor);
            drawStatLabel(graphics, "Attack Damage", 65, textColor);
            drawStatLabel(graphics, "Movement Speed", 85, textColor);
            drawStatLabel(graphics, "Attack Speed", 105, textColor);

            drawStatValue(
                    graphics,
                    getQualityName(menu.getGemEntity()),
                    -10,
                    25,
                    textColor
            );

            drawStatValue(
                    graphics,
                    String.format("%.0f", menu.getGemEntity().getAttributeValue(Attributes.MAX_HEALTH)),
                    0,
                    45,
                    textColor
            );

            drawStatValue(
                    graphics,
                    String.format("%.1f", menu.getGemEntity().getAttributeValue(Attributes.ATTACK_DAMAGE)),
                    0,
                    65,
                    textColor
            );

            drawStatValue(
                    graphics,
                    String.format("%.2f", menu.getGemEntity().getAttributeValue(Attributes.MOVEMENT_SPEED)),
                    0,
                    85,
                    textColor
            );

            drawStatValue(
                    graphics,
                    String.format("%.2f", menu.getGemEntity().getAttributeValue(Attributes.ATTACK_SPEED)),
                    0,
                    105,
                    textColor
            );
        }
        //ABILITIES
        if(selectedTab == GemTabs.ABILITIES){
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    ABILITIES_BACKGROUND,
                    this.leftPos,
                    this.topPos,
                    0,
                    0,
                    this.imageWidth,
                    this.imageHeight,
                    256,
                    256,
                    gemColor
            );

            drawSelectedAbility(graphics);
            if (selectedAbilityUI != null) {
                selectedAbilityUI.render(
                        graphics,
                        mouseX,
                        mouseY,
                        partialTick
                );
            }
        }

        for(int i = 0; i < GemTabs.values().length; i++){
            drawTab(graphics, GemTabs.values()[i], this.leftPos - 31, this.topPos + 20 + i * 26, gemColor);
        }
    }

    private void setInventorySlotsActive(boolean active) {
        for (Slot slot : this.menu.slots) {
            if (slot instanceof GemSlot gemSlot) {
                gemSlot.setActive(active);
            }
        }
    }

    private void drawTab(GuiGraphicsExtractor graphics, GemTabs gemTab, int x, int y, int gemColor){
        boolean selected = gemTab == selectedTab;

        Identifier tabTexture = selected ? TAB_SELECTED : TAB_UNSELECTED;
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                tabTexture,
                x,
                y,
                0,
                0,
                32,
                26,
                32,
                26,
                gemColor
        );

        ItemStack icon = gemTab.getIcon(menu.getGemEntity());
        graphics.item(icon, x + 11, y + 5);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {

        int modelX = this.leftPos + 86;
        int modelY = this.topPos + 18;

        if (selectedTab == GemTabs.APPEARANCE
                && event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT
                && event.x() >= modelX
                && event.x() < modelX + 84
                && event.y() >= modelY
                && event.y() < modelY + 102) {

            draggingModel = true;

            return true;
        }

        for(int i = 0; i < GemTabs.values().length; i++){
            int tabX = this.leftPos - 31;
            int tabY = this.topPos + 20 + i * 26;

            if(event.x() >= tabX && event.x() < tabX + 32 && event.y() >= tabY && event.y() < tabY + 26){
                selectedTab = GemTabs.values()[i];
                setInventorySlotsActive(selectedTab == GemTabs.INVENTORY);
                setAppearanceWidgetsVisible(selectedTab == GemTabs.APPEARANCE);
                setAbilityWidgetsVisible(selectedTab == GemTabs.ABILITIES);
                return true;
            }
        }

        if (nameBox != null && !nameBox.isMouseOver(event.x(), event.y()) && nameBox.isFocused()) {
            this.setFocused(null);
            nameBox.setValue(menu.getGemEntity().getCurrentName());
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (selectedTab == GemTabs.APPEARANCE && draggingModel && event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {

            modelRotation += (float) dx;
            modelPitch += (float) dy / 10f;
            modelPitch = Mth.clamp(modelPitch, -20f, 20f);

            return true;
        }

        return super.mouseDragged(event, dx, dy);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            draggingModel = false;
        }

        return super.mouseReleased(event);
    }

    private void setAppearanceWidgetsVisible(boolean visible) {
        nameBox.visible = visible;

        outfitLeft.visible = visible;
        outfitRight.visible = visible;

        insigniaLeft.visible = visible;
        insigniaRight.visible = visible;

        hairstyleLeft.visible = visible;
        hairstyleRight.visible = visible;

        visorLeft.visible = visible;
        visorRight.visible = visible;
    }

    private static int getGuiHeight(GemMenu menu) {
        int rows = (menu.getGemEntity().getContainerSize() + 8) / 9;

        return 166 + (rows - 1) * 18;
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {

        if (selectedTab == GemTabs.INVENTORY) {
            int gemRows = (menu.getGemEntity().getContainerSize() + 8) / 9;

            int inventoryLabelY = 18 + gemRows * 18 + 12 - 10;

            graphics.text(
                    this.font,
                    this.title,
                    this.titleLabelX,
                    this.titleLabelY,
                    textColor,
                    false
            );

            graphics.text(
                    this.font,
                    this.playerInventoryTitle,
                    this.inventoryLabelX,
                    inventoryLabelY,
                    textColor,
                    false
            );
        }
        if(selectedTab == GemTabs.APPEARANCE){
            graphics.text(
                    this.font,
                    Component.literal("Appearance"),
                    this.titleLabelX,
                    this.titleLabelY,
                    textColor,
                    false
            );


        }
        if(selectedTab == GemTabs.STATS){
            graphics.text(
                    this.font,
                    Component.literal("Stats"),
                    this.titleLabelX,
                    this.titleLabelY,
                    textColor,
                    false
            );
        }
        if(selectedTab == GemTabs.ABILITIES){
            graphics.text(
                    this.font,
                    Component.literal("Abilities"),
                    this.titleLabelX,
                    this.titleLabelY,
                    textColor,
                    false
            );
        }
    }

    private Button createArrowButton(int x, int y, boolean right, Runnable onPress) {
        return Button.builder(Component.literal(right ? "▶" : "◀"), button -> onPress.run()).bounds(x + APPEARANCE_BUTTONS_X_OFFSET, y + APPEARANCE_BUTTONS_Y_OFFSET, 8, 9).build();
    }

    @Override
    protected void init() {
        super.init();

        nameBox = new EditBox(
                this.font,
                this.leftPos + 8,
                this.topPos + 25,
                70,
                16,
                Component.literal("Gem Name")
        );

        nameBox.setBordered(false);
        nameBox.setCanLoseFocus(true);
        nameBox.setMaxLength(32);
        nameBox.setValue(menu.getGemEntity().getCurrentName());
        nameBox.setTextColor(textColor);
        nameBox.setTextShadow(false);

        outfitLeft = createArrowButton(
                this.leftPos + 65,
                this.topPos + 32,
                false,
                () -> menu.changeOutfit(-1)
        );

        outfitRight = createArrowButton(
                this.leftPos + 91,
                this.topPos + 32,
                true,
                () -> menu.changeOutfit(1)
        );

        insigniaLeft = createArrowButton(
                this.leftPos + 65,
                this.topPos + 52,
                false,
                () -> menu.changeInsignia(-1)
        );

        insigniaRight = createArrowButton(
                this.leftPos + 91,
                this.topPos + 52,
                true,
                () -> menu.changeInsignia(1)
        );

        hairstyleLeft = createArrowButton(
                this.leftPos + 65,
                this.topPos + 72,
                false,
                () -> menu.changeHairstyle(-1)
        );

        hairstyleRight = createArrowButton(
                this.leftPos + 91,
                this.topPos + 72,
                true,
                () -> menu.changeHairstyle(1)
        );

        visorLeft = createArrowButton(
                this.leftPos + 65,
                this.topPos + 92,
                false,
                () -> menu.changeVisor(-1)
        );

        visorRight = createArrowButton(
                this.leftPos + 91,
                this.topPos + 92,
                true,
                () -> menu.changeVisor(1)
        );

        setAppearanceWidgetsVisible(selectedTab == GemTabs.APPEARANCE);

        this.addRenderableWidget(nameBox);
        this.addRenderableWidget(outfitLeft);
        this.addRenderableWidget(outfitRight);

        this.addRenderableWidget(insigniaLeft);
        this.addRenderableWidget(insigniaRight);

        this.addRenderableWidget(hairstyleLeft);
        this.addRenderableWidget(hairstyleRight);

        this.addRenderableWidget(visorLeft);
        this.addRenderableWidget(visorRight);

        createAbilityButtons();
        setAbilityWidgetsVisible(selectedTab == GemTabs.ABILITIES);
    }

    private void drawSelector(GuiGraphicsExtractor graphics, String label, int value, int y, int textColor) {
        graphics.text(
                this.font,
                Component.literal(label),
                this.leftPos + GemScreen.APPEARANCE_TYPE_X_OFFSET,
                this.topPos + y,
                textColor,
                false
        );

        graphics.text(
                this.font,
                Component.literal(String.valueOf(value + 1)),
                this.leftPos + GemScreen.APPEARANCE_TYPE_X_OFFSET + 58,
                this.topPos + y,
                textColor,
                false
        );
    }

    private void drawNameEditBox(GuiGraphicsExtractor graphics){
        int nameX = this.leftPos + 5;
        int nameY = this.topPos + 22;
        int nameWidth = 77;
        int nameHeight = 14;

        int borderColor = ColorUtil.multiplyColors(
                gemColor,
                ColorUtil.colorToInt(new Color(60, 60, 60))
        );

        // Background
        graphics.fill(
                nameX,
                nameY,
                nameX + nameWidth,
                nameY + nameHeight,
                ColorUtil.multiplyColors(
                        gemColor,
                        ColorUtil.colorToInt(new Color(170, 170, 170))
                )
        );

        // Border
        graphics.fill(
                nameX,
                nameY,
                nameX + nameWidth,
                nameY + 1,
                borderColor
        );

        graphics.fill(
                nameX,
                nameY + nameHeight - 1,
                nameX + nameWidth,
                nameY + nameHeight,
                borderColor
        );

        graphics.fill(
                nameX,
                nameY,
                nameX + 1,
                nameY + nameHeight,
                borderColor
        );

        graphics.fill(
                nameX + nameWidth - 1,
                nameY,
                nameX + nameWidth,
                nameY + nameHeight,
                borderColor
        );
    }

    private void drawStatsIcons(GuiGraphicsExtractor graphics) {
        graphics.item(
                new ItemStack(Items.NETHER_STAR),
                this.leftPos + 8,
                this.topPos + 25
        );

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                Identifier.withDefaultNamespace("hud/heart/full"),
                this.leftPos + 8,
                this.topPos + 45,
                16,
                16
        );

        graphics.item(
                new ItemStack(Items.DIAMOND_SWORD),
                this.leftPos + 8,
                this.topPos + 65
        );

        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                Identifier.withDefaultNamespace("mob_effect/speed"),
                this.leftPos + 8,
                this.topPos + 85,
                16,
                16
        );

        graphics.item(
                new ItemStack(Items.FEATHER),
                this.leftPos + 8,
                this.topPos + 105
        );
    }

    private void drawStatLabel(GuiGraphicsExtractor graphics, String label, int y, int textColor) {
        graphics.text(
                this.font,
                Component.literal(label),
                this.leftPos + 30,
                this.topPos + y + STAT_LABEL_Y_OFFSET,
                textColor,
                false
        );
    }

    private void drawStatValue(GuiGraphicsExtractor graphics, String value, int x, int y, int textColor) {
        graphics.text(
                this.font,
                Component.literal(value).withStyle(ChatFormatting.BOLD),
                this.leftPos + 120 + x,
                this.topPos + y + STAT_LABEL_Y_OFFSET,
                textColor,
                false
        );
    }

    private String getQualityName(GemEntity gem) {
        return switch (gem.getEntityData().get(GemEntity.QUALITY)) {
            case 0 -> "Off-color";
            case 1 -> "Average";
            case 2 -> "Perfect";
            default -> "Unknown";
        };
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (nameBox != null && nameBox.isFocused()) {
            if (event.key() == GLFW.GLFW_KEY_ENTER) {
                String nickname = nameBox.getValue().trim();
                this.setFocused(null);
                menu.sendNickname(nickname);

                return true;
            }

            if (this.minecraft.options.keyInventory.matches(event)) {
                return true;
            }
        }

        return super.keyPressed(event);
    }

    private void drawGemModel(GuiGraphicsExtractor graphics, float partialTick) {
        GemEntity gem = menu.getGemEntity();

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        GemRenderState renderState = (GemRenderState) dispatcher.extractEntity(gem, partialTick);

        renderState.previewPitch = modelPitch;

        // Force the GUI preview to ignore the gem's world rotation
        renderState.addGeckolibData(DataTickets.ENTITY_BODY_YAW, modelRotation);

        int modelX = this.leftPos + 86;
        int modelY = this.topPos + 18;

        graphics.entity(
                renderState,
                30.0f,
                new Vector3f(0.0f, 1.5f, 0.0f),
                new Quaternionf()
                        .rotateX((float) Math.PI),
                null,
                modelX,
                modelY,
                modelX + 84,
                modelY + 102
        );
    }

    private void createAbilityButtons() {
        gemButtons.clear();

        GemEntity gem = menu.getGemEntity();

        int x = this.leftPos + 8;
        int y = this.topPos + 25;

        for (GemAbility ability : gem.getAbilities()) {

            GemButton button = new GemButton(
                    x,
                    y,
                    80,
                    20,
                    Component.literal(ability.getName()),
                    textColor,
                    gemColor,
                    () -> selectAbility(ability)
            );

            gemButtons.add(button);
            this.addRenderableWidget(button);

            y += 26;
        }
    }

    private void selectAbility(GemAbility ability) {
        if (selectedAbilityUI != null) {
            selectedAbilityUI.clear();
        }

        selectedAbility = ability;

        selectedAbilityUI = switch (ability) {
            case KINDERGARTNER -> new KindergartnerUI();
            default -> new GenericAbilityUI();
        };

        selectedAbilityUI.init(this, textColor, gemColor);
    }

    private void setAbilityWidgetsVisible(boolean visible) {
        for (GemButton button : gemButtons) {
            button.visible = visible;
        }
    }

    private void drawSelectedAbility(GuiGraphicsExtractor graphics) {
        if (selectedAbility == null) {
            return;
        }

        graphics.pose().pushMatrix();

        graphics.pose().scale(0.75f, 0.75f);

        graphics.text(
                this.font,
                Component.literal(selectedAbility.getName())
                        .withStyle(ChatFormatting.BOLD),
                (int) ((this.leftPos + 105) / 0.75f),
                (int) ((this.topPos + 30) / 0.75f),
                textColor,
                false
        );

        graphics.pose().popMatrix();

        // Description
        Component description = Component.literal(selectedAbility.getDescription());

        float scale = 0.55f;

        graphics.pose().pushMatrix();

        graphics.pose().scale(scale, scale);

        graphics.textWithWordWrap(
                this.font,
                description,
                (int) ((this.leftPos + 105) / scale),
                (int) ((this.topPos + 48) / scale),
                (int) (65 / scale),
                textColor
        );

        graphics.pose().popMatrix();
    }

    public void addAbilityWidget(GemButton gemButton) {
        gemButtons.add(gemButton);
        this.addRenderableWidget(gemButton);
    }
}
