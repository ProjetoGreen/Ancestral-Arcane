package com.dntr.ui.screen;

import com.dntr.DntrMod;
import com.dntr.network.TableActionC2SPacket;
import com.dntr.ui.menu.ArcaneSmithingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public class ArcaneSmithingScreen extends AbstractContainerScreen<ArcaneSmithingMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation
            .withDefaultNamespace("textures/gui/container/generic_54.png"); // placeholder texture

    public ArcaneSmithingScreen(ArcaneSmithingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        // Add buttons
        this.addRenderableWidget(Button.builder(Component.literal("Upg Lvl"), b -> send(0))
                .bounds(this.leftPos + 80, this.topPos + 18, 50, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Inscribe"), b -> send(3))
                .bounds(this.leftPos + 80, this.topPos + 40, 50, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Bind"), b -> send(5))
                .bounds(this.leftPos + 80, this.topPos + 62, 40, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Unbind"), b -> send(6))
                .bounds(this.leftPos + 125, this.topPos + 62, 45, 20).build());
    }

    private void send(int action) {
        PacketDistributor.sendToServer(new TableActionC2SPacket(action));
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
        guiGraphics.drawString(this.font, "Level: " + this.menu.getBlockEntity().getTableLevel(), this.leftPos + 8,
                this.topPos + 8, 4210752, false);
    }
}
