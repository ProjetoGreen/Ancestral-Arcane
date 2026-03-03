package com.ancestralarcane.ui.hud;

import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChargeBarOverlay implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null)
            return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof com.ancestralarcane.item.WandItem)) {
            stack = player.getOffhandItem();
        }
        if (!(stack.getItem() instanceof com.ancestralarcane.item.WandItem))
            return;

        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        if (!data.contains("rune"))
            return;
        CompoundTag rune = data.getCompound("rune");
        int lvl = rune.getInt("lvl");
        if (lvl <= 0)
            return;

        int charges = rune.getInt("charges");
        int cap = lvl * 10;

        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        int barX = (width - 182) / 2;
        int barY = height - 50;

        int useTicks = player.getUseItemRemainingTicks();
        float progress = 0;
        if (player.isUsingItem() && player.getUseItem() == stack) {
            int duration = stack.getItem().getUseDuration(stack, player);
            int used = duration - useTicks;

            int baseTicks = data.contains("cast_time_base") ? data.getInt("cast_time_base") : 20;
            float effTicks = baseTicks;
            progress = Math.min(1.0f, used / effTicks);
        }

        guiGraphics.fill(barX, barY, barX + 182, barY + 5, 0xAA000000);

        if (progress > 0) {
            int fill = (int) (progress * 182);
            int color = 0xFF00FFFF;
            if (progress >= 1.0f)
                color = 0xFF00FF00;
            else if (progress >= 0.85f)
                color = 0xFFFFFF00;
            else if (progress >= 0.60f)
                color = 0xFFFF8800;

            guiGraphics.fill(barX, barY, barX + fill, barY + 5, color);
        }

        guiGraphics.fill(barX + (int) (182 * 0.60f), barY, barX + (int) (182 * 0.60f) + 1, barY + 5, 0xFFFFFFFF);
        guiGraphics.fill(barX + (int) (182 * 0.85f), barY, barX + (int) (182 * 0.85f) + 1, barY + 5, 0xFFFFFFFF);

        String text = "SPELL LVL: " + lvl + " | Charges: " + charges + "/" + cap;
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            text = "COOLDOWN";
        }
        guiGraphics.drawString(mc.font, text, barX + 2, barY - 10, 0xFFFFFF, true);
    }
}
