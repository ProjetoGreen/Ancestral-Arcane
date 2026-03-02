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
            return Component.literal("Crude Rune Tier " + tier);
        }

        if (empty == 1) {
            String prefix = hasUpgrade ? "Upgraded Rune Tier " : "Rune Tier ";
            return Component.literal(prefix + tier);
        }

        if (spell != null && !spell.isEmpty()) {
            int lvl = rune.getInt("lvl");
            String capitalizedSpell = spell.substring(0, 1).toUpperCase() + spell.substring(1).toLowerCase();
            capitalizedSpell = capitalizedSpell.replace("_", " ");

            String prefix = hasUpgrade ? "Upgraded " : "";
            return Component.literal(prefix + "Level " + lvl + " " + capitalizedSpell + " Rune");
        }

        return Component.translatable(this.getDescriptionId(stack));
    }
}
