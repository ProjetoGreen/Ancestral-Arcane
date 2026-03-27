package com.ancestralarcane.item;

import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class GrimoireSpellItem extends Item {
    private final String spellName;

    public GrimoireSpellItem(Properties properties, String spellName) {
        super(properties);
        this.spellName = spellName;
    }

    public String getSpellName() {
        return spellName;
    }

    private String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> number > 0 ? String.valueOf(number) : "";
        };
    }

    @Override
    public Component getName(ItemStack stack) {
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        int tier = 1;
        if (data.contains("grimoire")) {
            tier = data.getCompound("grimoire").getInt("tier");
        }

        String suffix = tier > 0 ? " " + toRoman(tier) : "";
        return Component.translatable(this.getDescriptionId(stack)).append(suffix);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        CompoundTag data = CustomDataUtil.getAncestralArcaneData(stack);
        int tier = 1;
        int uses = 0;
        if (data.contains("grimoire")) {
            CompoundTag grim = data.getCompound("grimoire");
            tier = grim.getInt("tier");
            uses = grim.contains("uses") ? grim.getInt("uses") : (tier == 5 ? 15 : tier == 4 ? 12 : tier == 3 ? 9 : tier == 2 ? 6 : 3);
        } else {
            // Fallback for empty grimoires if needed, though they usually aren't GrimoireSpellItems
            uses = 3;
        }
        tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.tier", toRoman(tier)));
        tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.uses_left", uses));
    }
}
