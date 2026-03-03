package com.ancestralarcane.ui.screen;

import com.ancestralarcane.AncestralArcaneMod;
import com.ancestralarcane.ui.menu.ArcaneSmithingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArcaneSmithingScreen extends AbstractContainerScreen<ArcaneSmithingMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation
            .fromNamespaceAndPath(AncestralArcaneMod.MODID, "textures/gui/arcane_table/arcane_gui.png");

    public ArcaneSmithingScreen(ArcaneSmithingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        guiGraphics.drawString(this.font, "Level: " + this.menu.getBlockEntity().getTableLevel(), this.leftPos + 80,
                this.topPos + 8, 4210752, false);
    }
}
