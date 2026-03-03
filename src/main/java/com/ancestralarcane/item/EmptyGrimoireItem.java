package com.ancestralarcane.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class EmptyGrimoireItem extends Item {
    private final int tier;

    public EmptyGrimoireItem(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
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
        String suffix = tier > 0 ? " " + toRoman(tier) : "";
        return Component.translatable(this.getDescriptionId(stack)).append(suffix);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.ancestral_arcane.tier", toRoman(tier)));
        tooltipComponents.add(Component.literal("\u00A78(Empty)"));
    }
}
