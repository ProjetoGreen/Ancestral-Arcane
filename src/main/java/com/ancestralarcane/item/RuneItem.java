package com.ancestralarcane.item;

import com.ancestralarcane.data.CustomDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneItem extends Item {

    public RuneItem(Properties properties) {
        super(properties);
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
        if (!data.contains("rune")) {
            return Component.translatable(this.getDescriptionId(stack)); // fallback
        }

        CompoundTag rune = data.getCompound("rune");
        int tier = rune.getInt("tier");
        int crude = rune.getInt("crude");
        int empty = rune.getInt("empty");
        boolean hasUpgrade = rune.contains("upgrade");
        String spell = rune.getString("spell");

        if (crude == 1) {
            String suffix = tier > 0 ? " " + toRoman(tier) : "";
            return Component.literal("Crude Rune" + suffix);
        }

        if (empty == 1) {
            String prefix = hasUpgrade ? "Upgraded Rune" : "Rune";
            String suffix = tier > 0 ? " " + toRoman(tier) : "";
            return Component.literal(prefix + suffix);
        }

        if (spell != null && !spell.isEmpty()) {
            int lvl = rune.getInt("lvl");
            String capitalizedSpell = spell.substring(0, 1).toUpperCase() + spell.substring(1).toLowerCase();
            capitalizedSpell = capitalizedSpell.replace("_", " ");

            String prefix = hasUpgrade ? "Upgraded " : "";
            String suffix = lvl > 0 ? " " + toRoman(lvl) : "";
            return Component.literal(prefix + capitalizedSpell + " Rune" + suffix);
        }

        return Component.translatable(this.getDescriptionId(stack));
    }
}
